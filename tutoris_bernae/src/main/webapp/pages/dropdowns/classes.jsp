<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	
<c:set var="classesListErrors"><form:errors path="classesList"/></c:set>
<c:choose>
	<c:when test="${not empty classesListErrors}">error while rendering all classes</c:when>
	<c:otherwise>
		<div class="control-group">
			<div class="controls">
				<form:select path="selectedClassId" required="false" multiple="false">
					<form:option value="0" label="--- Select a class ---"/>
					<form:options items="${classesList}" itemValue="id" itemLabel="name" />
				</form:select>
				<form:errors path="classesList" cssClass="help-inline" element="span"/>
			</div>
		</div>
	</c:otherwise>
</c:choose>