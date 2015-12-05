<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true"%>

<c:import url="template/header.jsp" />

<c:import url="template/function.jsp" />
<h1>Edit profile!</h1>
<form:form method="post" modelAttribute="tutorEditForm" action="editTutorSubmit" id="editForm" cssClass="form-mc"  autocomplete="off">
    <fieldset>
        <legend>You can alter any field</legend>
	<form:input path="userId" type="hidden" value="${user.id}"/>
        <form:input path="tutorId" type="hidden" value="${user.tutor.id}"/>
        
        <div class="mc-column">
            <c:set var="firstNameErrors"><form:errors path="firstName"/></c:set>
            <div class="control-group<c:if test="${not empty firstNameErrors}"> error</c:if>">
                <label class="control-label" for="field-firstName">First Name</label>
                <div class="controls">
                <form:input path="firstName" id="field-firstName" tabindex="1" maxlength="35" value="${user.firstName}"/>
                <form:errors path="firstName" cssClass="help-inline" element="span"/>
                </div>
            </div>
            <c:set var="emailErrors"><form:errors path="email"/></c:set>
            <div class="control-group<c:if test="${not empty emailErrors}"> error</c:if>">
                <label class="control-label" for="field-email">Email</label>
                <div class="controls">
                <form:input path="email" id="field-email" tabindex="3" maxlength="45" value="${user.email}"/>
                <form:errors path="email" cssClass="help-inline" element="span"/>
                </div>
            </div>
            <c:set var="feeErrors"><form:errors path="fee"/></c:set>
            <div class="control-group<c:if test="${not empty feeErrors}"> error</c:if>">
                <label class="control-label" for="field-fee">Fee</label>
                <div class="controls">
                <form:input type="number" path="fee" id="field-fee" tabindex="6" maxlength="35" placeholder="Fee"/>
                <form:errors path="fee" cssClass="help-inline" element="span"/>
                </div>
            </div>
        </div>
        
        <div class="mc-column">
            
            <c:set var="lastNameErrors"><form:errors path="lastName"/></c:set>
            <div class="control-group<c:if test="${not empty lastNameErrors}"> error</c:if>">
                <label class="control-label" for="field-lastName">Last Name</label>
                <div class="controls">
                <form:input path="lastName" id="field-lastName" tabindex="2" maxlength="35" value="${user.lastName}"/>
                <form:errors path="lastName" cssClass="help-inline" element="span"/>
                </div>
            </div>
            <c:set var="passwordErrors"><form:errors path="password"/></c:set>
            <div class="control-group<c:if test="${not empty passwordErrors}"> error</c:if>">
                <label class="control-label" for="field-password">Password <span class="hint" content="Password between 8-14 characters. At least 1 uppercase letter, 1 digit, 1 special character.">?</span></label>
                <div class="controls">
                <form:input type="password" path="password" id="field-password" tabindex="4" maxlength="35" placeholder="Password"/>
                <form:errors path="password" cssClass="help-inline" element="span"/>
                </div>
            </div>
            <c:set var="bioErrors"><form:errors path="bio"/></c:set>
            <div class="control-group<c:if test="${not empty bioErrors}"> error</c:if>">
                <label class="control-label" for="field-bio">Bio</label>
                <div class="controls">
                <form:textarea path="bio" id="field-bio" tabindex="7" maxlength="350" placeholder="Bio"/>
                <form:errors path="bio" cssClass="help-inline" element="span"/>
                </div>
            </div>
        </div>
        
<table>
                <thead>
                    <tr>
                        <th>Course name</th>
                        <th>Faculty</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody id="studyCourseListContainer">
                	<form:errors path="studyCourseList" cssClass="help-inline" element="span"/>
                    <c:forEach items="${tutorEditForm.studyCourseList}" varStatus="i" begin="0" >
                        <tr class="studyCourse">
                           <td><form:select path="studyCourseList[${i.index}]" value="${tutorEditForm.studyCourseList[i.index].id}">
					  	<form:options items="${allCourses}" itemLabel="name" itemValue="id"/>
				       	</form:select></td>    

                            <td><button type="submit" name="removeStudyCourse" value="${i.index}">remove it</button></td>
                        </tr>
                    </c:forEach>
                    
                </tbody>
            </table>
            <button type="submit" name="addStudyCourse" value="true">Add New Course</button>
<table>
                <thead>
                    <tr>
                        <th>Class name</th>
                        <th>Grade</th>
                    </tr>
                </thead>
                <tbody id="classListContainer">
      				
      				<c:if test="${not empty classListError}">${classListError}</c:if>
                    <c:forEach items="${tutorEditForm.classList}" var="classes" varStatus="i" begin="0" >
                        <tr class="classList">    
                        <td><form:select path="classList[${i.index}].classes" id="classesId${i.index}" value="${tutorEditForm.classList[i.index].classes.id}">
					  	<form:options items="${allClasses}" itemLabel="name" itemValue="id"/>
				       	</form:select></td>
                            <td><form:select path="classList[${i.index}].grade" id="grade${i.index}" value="${tutorEditForm.classList[i.index].grade}">
					  		<form:option value = "4"> 4.00 </form:option>
					  		<form:option value = "4.50"> 4.50 </form:option>
					  		<form:option value = "5"> 5.00 </form:option>
					  		<form:option value = "5.50"> 5.50 </form:option>
					  		<form:option value = "6.00"> 6.00 </form:option>
					  		</form:select></td>
                            <td><button type="submit" name="removeClass" value="${i.index}">remove it</button></td>
                            </tr>
                    </c:forEach>
                </tbody>
            </table>
           
            <button type="submit" name="addClass" value="true">Add New Class</button><br>
            <form:errors path="classList" cssClass="help-inline" element="span"/>

		<br>
		<c:if test="${page_error != null }">
	        <div class="alert alert-error">
	            <button type="button" class="close" data-dismiss="alert">&times;</button>
	            <h4>Error!</h4>
	                ${page_error}
	        </div>
	    </c:if>
       <br>
       		        
        <div class="form-actions">
            <button type="submit" name = "save" value = "true" class="button btn btn-primary">Submit changes</button>
            <button type="reset" class="button btn" onclick="window.history.back();">Cancel</button>
         </div>
           
    </fieldset>
</form:form>

<c:import url="template/footer.jsp" />
