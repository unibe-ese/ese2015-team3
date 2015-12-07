<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<table>
    <thead>
        <tr>
            <th>Course name</th>
            <th></th>
        </tr>
    </thead>
    <tbody id="studyCourseListContainer">
    	<form:errors path="studyCourseList" cssClass="help-inline" element="span"/>
        <c:forEach items="${tutorForm.studyCourseList}" varStatus="i" begin="0" >
            <tr class="studyCourse">
               <td><form:select path="studyCourseList[${i.index}]" value="${tutorForm.studyCourseList[i.index].id}">
				  	<form:options items="${allCourses}" itemLabel="name" itemValue="id"/>
			       	</form:select>
		       	</td>    

                <td><button type="submit" name="removeStudyCourse" value="${i.index}">remove it</button></td>
            </tr>
        </c:forEach>
        
    </tbody>
</table>
<button type="submit" name="addStudyCourse" value="true" class="button">Add New Course</button>