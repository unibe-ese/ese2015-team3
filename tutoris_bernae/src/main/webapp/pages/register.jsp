<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true"%>

<c:import url="template/header.jsp" />

<c:import url="template/function.jsp" />
<h1>Sign Up Here!</h1>
<form:form method="post" modelAttribute="registerForm" action="submit" id="registerForm" cssClass="form-mc"  autocomplete="off">
    <fieldset>
        <legend>Enter Your Information</legend>
        <div class="mc-column">
            <c:set var="emailErrors"><form:errors path="email"/></c:set>
            <div class="control-group<c:if test="${not empty emailErrors}"> error</c:if>">
                <label class="control-label" for="field-email">Email</label>
                <div class="controls">
                <form:input path="email" id="field-email" tabindex="1" maxlength="45" placeholder="Email"/>
                <form:errors path="email" cssClass="help-inline" element="span"/>
                </div>
            </div>
            
            <c:set var="firstNameErrors"><form:errors path="firstName"/></c:set>
            <div class="control-group<c:if test="${not empty firstNameErrors}"> error</c:if>">
                <label class="control-label" for="field-firstName">First Name</label>
                <div class="controls">
                <form:input path="firstName" id="field-firstName" tabindex="3" maxlength="35" placeholder="First Name"/>
                <form:errors path="firstName" cssClass="help-inline" element="span"/>
                </div>
            </div>
        
            <c:set var="passwordErrors"><form:errors path="password"/></c:set>
            <div class="control-group<c:if test="${not empty passwordErrors}"> error</c:if>">
                <label class="control-label" for="field-password">Password <span class="hint">?</span></label>
                <div class="controls">
                <form:input type="password" path="password" id="field-password" tabindex="5" maxlength="35" placeholder="Password"/>
                <form:errors path="password" cssClass="help-inline" element="span"/>
                </div>
            </div>
        </div>    
         
        <div class="mc-column">
            <c:set var="usernameErrors"><form:errors path="username"/></c:set>
            <div class="control-group<c:if test="${not empty usernameErrors}"> error</c:if>">
                <label class="control-label" for="field-username">Username</label>
                <div class="controls">
                <form:input path="username" id="field-username" tabindex="2" maxlength="35" placeholder="Username"/>
                <form:errors path="username" cssClass="help-inline" element="span"/>
                </div>
            </div>
        
            <c:set var="lastNameErrors"><form:errors path="lastName"/></c:set>
            <div class="control-group<c:if test="${not empty lastNameErrors}"> error</c:if>">
                <label class="control-label" for="field-lastName">Last Name</label>
                <div class="controls">
                <form:input path="lastName" id="field-lastName" tabindex="4" maxlength="35" placeholder="Last Name"/>
                <form:errors path="lastName" cssClass="help-inline" element="span"/>
                </div>
            </div>
        </div>
        
        <br>
		<c:if test="${page_error != null }">
	        <div class="alert alert-error">
	            <h4>Error!</h4>
	                ${page_error}
	        </div>
	    </c:if>
        <br>
       		        
        <div class="form-actions">
			<button type="submit" class="button btn btn-primary">Sign up</button>
            <button type="submit" name = "registerastutor" value = "true" class="button btn btn-primary">Sign up and register as Tutor</button>
            <button type="reset" class="button btn" onclick="window.history.back();">Cancel</button>
         </div>
           
    </fieldset>
</form:form>

<c:import url="template/footer.jsp" />
