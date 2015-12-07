<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true"%>

<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />

<a href="${link}"><img alt="Paypal payment" border="0" src="https://www.paypalobjects.com/en_US/CH/i/btn/btn_paynowCC_LG.gif"></a>
	
<c:import url="template/footer.jsp" />