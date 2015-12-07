<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true"%>

<c:import url="template/customized_header/auto_refresh_header.jsp" />
<c:import url="template/function.jsp" />

<h1>PayPal Payment Success</h1>
		<div>Thank you for your payment via PayPal. You will be redirected to the homescreen shortly...</div></br>
		<div>Please click <a href="/tutoris_baernae/">here</a> if the redirection failed.</div>


</body>
</html>