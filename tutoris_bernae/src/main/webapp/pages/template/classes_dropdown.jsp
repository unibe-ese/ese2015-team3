<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<label class="control-label" for="field-classList">List of Classes</label>	
	
<c:set var="classListErrors"><form:errors path="classList"/></c:set>
<c:choose>
	<c:when test="${not empty classListErrors}">error while rendering all classes</c:when>
	<c:otherwise>
		<div class="control-group">
			<div class="controls">
				<form:select path="classList" required="false">
					<form:options items="${classList}" itemValue="id" itemLabel="name"/>
				</form:select>
				<form:errors path="classList" cssClass="help-inline" element="span"/>
			</div>
		</div>
	</c:otherwise>
</c:choose>