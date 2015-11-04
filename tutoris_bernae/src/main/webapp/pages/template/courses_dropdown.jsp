<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<label class="control-label" for="field-studyCourse">Study Course</label>	
	
<c:set var="studyCourseErrors"><form:errors path="studyCourse"/></c:set>
<c:choose>
	<c:when test="${not empty studyCourseErrors}">error while rendering study courses</c:when>
	<c:otherwise>
		<div class="control-group">
			<div class="controls">
				<form:select path="studyCourse" required="false">
					<form:options items="${studyCourseList}" itemValue="id" itemLabel="name"/>
				</form:select>
				<form:errors path="studyCourse" cssClass="help-inline" element="span"/>
			</div>
		</div>
	</c:otherwise>
</c:choose>

	<%@ include file="autocomplete.jsp" %>

