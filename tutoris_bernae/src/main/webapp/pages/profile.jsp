<%@page import="org.sample.model.User"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/header.jsp" />


<h1>View the userprofile from: ${user.firstName }</h1>

<form:form method="post" modelAttribute="signupForm" action="create"
	id="signupForm" cssClass="form-horizontal" autocomplete="off">
	<fieldset>
		<legend>View the userprofile from: ${user.firstName}</legend>

		<p>Lastname: ${user.lastName }</p>
		<p>UserId: ${user.id }</p>
		<p>Team: ${user.teamName}</p>

		<p>Email: ${user.email}</p>


		<div class="form-actions">
			<button type="submit" class="btn btn-primary">Sign up</button>
			<button type="button" class="btn">Cancel</button>
			<button type="submit" class="btn">Go to teams</button>
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
