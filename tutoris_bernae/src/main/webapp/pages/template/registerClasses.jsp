<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

		<label class="control-label">List of Classes</label>	
		<%@ include file="../listing/classes.jsp" %>

		<label class="control-label">Add another class</label>			
		<%@ include file="../dropdowns/classes.jsp" %>
		<button type="submit" name="addClass" value="true">Add New Class</button>
