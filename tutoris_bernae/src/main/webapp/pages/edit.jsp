<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true"%>

<c:import url="template/header.jsp" />

<c:import url="template/function.jsp" />
<h1>Edit profile!</h1>
<form:form method="post" modelAttribute="editForm" action="submitEdit" id="editForm" cssClass="form-horizontal"  autocomplete="off">
    <fieldset>
        <legend>You can alter any field</legend>
		<form:input path="userId" type="hidden" value="${user.id}"/>
        <c:set var="emailErrors"><form:errors path="email"/></c:set>
        <div class="control-group<c:if test="${not empty emailErrors}"> error</c:if>">
            <label class="control-label" for="field-email">Email</label>

            <div class="controls">
                <form:input path="email" id="field-email" tabindex="1" maxlength="45" value="${user.email}"/>
                <form:errors path="email" cssClass="help-inline" element="span"/>
            </div>
        </div>
        <c:set var="firstNameErrors"><form:errors path="firstName"/></c:set>
        <div class="control-group<c:if test="${not empty firstNameErrors}"> error</c:if>">
            <label class="control-label" for="field-firstName">First Name</label>
            <div class="controls">
                <form:input path="firstName" id="field-firstName" tabindex="2" maxlength="35" value="${user.firstName}"/>
                <form:errors path="firstName" cssClass="help-inline" element="span"/>
            </div>
        </div>
        <c:set var="lastNameErrors"><form:errors path="lastName"/></c:set>
        <div class="control-group<c:if test="${not empty lastNameErrors}"> error</c:if>">
            <label class="control-label" for="field-lastName">Last Name</label>
            <div class="controls">
                <form:input path="lastName" id="field-lastName" tabindex="3" maxlength="35" value="${user.lastName}"/>
                <form:errors path="lastName" cssClass="help-inline" element="span"/>
            </div>
        </div>
         <c:set var="usernameErrors"><form:errors path="username"/></c:set>
        <div class="control-group<c:if test="${not empty usernameErrors}"> error</c:if>">
            <label class="control-label" for="field-username">Username</label>
            <div class="controls">
                <form:input path="username" id="field-username" tabindex="3" maxlength="35" value="${user.username}"/>
                <form:errors path="username" cssClass="help-inline" element="span"/>
            </div>
        </div>
        <c:set var="passwordErrors"><form:errors path="password"/></c:set>
        <div class="control-group<c:if test="${not empty passwordErrors}"> error</c:if>">
            <label class="control-label" for="field-password">Password <span class="hint" content="Password between 8-14 characters. At least 1 uppercase letter, 1 digit, 1 special character.">?</span></label>
            <div class="controls">
                <form:input type="password" path="password" id="field-password" tabindex="3" maxlength="35" placeholder="Password"/>
                <form:errors path="password" cssClass="help-inline" element="span"/>
            </div>
        </div>
           <br>
           <br>
       		        
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Submit changes</button>
            <button type="reset" class="btn">Cancel</button>
         </div>
           
    </fieldset>
</form:form>
<a href="/tutoris_baernae/upgrade">Upgrade to Tutor</a>


	<c:if test="${page_error != null }">
        <div class="alert alert-error">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <h4>Error!</h4>
                ${page_error}
        </div>
    </c:if>


<c:import url="template/footer.jsp" />
