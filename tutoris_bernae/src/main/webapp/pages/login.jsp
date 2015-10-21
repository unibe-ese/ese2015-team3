<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />
	<div id="login-box">

	<form name='loginForm'
		  action="<c:url value='/j_spring_security_check' />" method='POST' cssClass="form-horizontal"  autocomplete="off">
    <fieldset>

     	<div id="login-box">

		<h1>Login with Username and Password</h1>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		<form name='loginForm'
		  action="<c:url value='/j_spring_security_check' />" method='POST'>

		<table>

			<div class="control-group">
            <label class="control-label" for="field-username">Username</label>
            <div class="controls">
                <input path="username"type="text" name="username" id="field-username" tabindex="3" maxlength="35" placeholder="Username"/>
                <errors path="username" cssClass="help-inline" element="span"/>
            </div>
        </div>
        			<div class="control-group">
            <label class="control-label" for="field-password">Password</label>
            <div class="controls">
                <input path="password" type="password" name="password" id="field-password" tabindex="3" maxlength="35" placeholder="Password"/>
                <errors path="password" cssClass="help-inline" element="span"/>
            </div>
        </div>

	        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Sign up</button>
            <button type="button" class="btn">Cancel</button>
           
        </div>
		  </table>

		  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />

		</form>
	</div>
           
           
        </div>

    </fieldset>
</form>
	</div>

</body>
</html>