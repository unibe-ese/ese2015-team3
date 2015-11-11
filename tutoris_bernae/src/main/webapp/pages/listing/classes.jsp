<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${not empty classList}">
	<table>
	    <thead>
	        <tr>
	            <th>Class name</th>
	            <th>Grade</th>
	            <th></th>
	        </tr>
	    </thead>
	    <tbody name="classesList">
	        <c:forEach var="class" items="${classList}" varStatus="i" >
	            <tr>    	                
<!--	                <td><c:out value="${class.name}"/></td>
	                <td><c:out value="${class.grade}" /></td> -->

	                <td><form:input path="classList[${i.index}].name" id="name${i.index}" /></td>
	                <td><form:input path="classList[${i.index}].grade" id="grade${i.index}" /></td>
	                
	                <td><button type="submit" name="removeClass" value="${i.index}">remove it</button></td>
	            </tr>
	        </c:forEach>
	    </tbody>
	</table>
</c:if>