<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true"%>

<c:import url="template/header.jsp" />

<c:import url="template/function.jsp" />
<h1>Sign Up Here!</h1>
<form:form method="post" modelAttribute="tutorForm" action="submitastutor" id="tutorForm" cssClass="form-horizontal"  autocomplete="off">
    <fieldset>
        <legend>Enter Your Information</legend>
	<form:input path="userId" type="hidden" value="${tutorForm.userId}"/>
             <c:import url="template/StudyList.jsp" />
            <c:import url="template/classList.jsp" />
        <c:set var="feeErrors"><form:errors path="fee"/></c:set>
        <div class="control-group<c:if test="${not empty feeErrors}"> error</c:if>">
            <label class="control-label" for="field-fee">Fee</label>
            <div class="controls">
                <form:input path="fee" id="field-fee" tabindex="2" maxlength="35" placeholder="Fee"/>
                <form:errors path="fee" cssClass="help-inline" element="span"/>
            </div>
        </div>
        <c:set var="bioErrors"><form:errors path="bio"/></c:set>
        <div class="control-group<c:if test="${not empty bioErrors}"> error</c:if>">
            <label class="control-label" for="field-bio">Bio</label>
            <div class="controls">
                <form:input path="bio" id="field-bio" tabindex="3" maxlength="350" placeholder="Bio"/>
                <form:errors path="bio" cssClass="help-inline" element="span"/>
            </div>
        </div>

           <br>
           <br>
       		        
        <div class="form-actions">
            <button type="submit" name = "save" value = "true" class="btn btn-primary">Sign up</button>
            <button type="button" class="btn">Cancel</button>
         </div>
           
    </fieldset>
</form:form>


	<c:if test="${page_error != null }">
        <div class="alert alert-error">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <h4>Error!</h4>
                ${page_error}
        </div>
    </c:if>


<c:import url="template/footer.jsp" />
