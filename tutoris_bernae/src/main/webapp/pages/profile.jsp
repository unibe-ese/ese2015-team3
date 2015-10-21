<%@page import="org.sample.model.User"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />

<h1>View the userprofile from: ${user.firstName }</h1>

<form:form method="post" modelAttribute="signupForm" action="create"
	id="signupForm" cssClass="form-horizontal" autocomplete="off">
	<fieldset>
		
		<p>FirstName: ${user.firstName}</p>
		<p>Lastname: ${user.lastName }</p>
		

		<p>Email: ${user.email}</p>


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
