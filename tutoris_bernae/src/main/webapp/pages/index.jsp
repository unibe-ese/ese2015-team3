<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />
<div class="welcome">
		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>
		
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h2>
				Welcome ${pageContext.request.userPrincipal.name} !
			</h2>
		</c:if>
		
		<c:if test="${pageContext.request.userPrincipal.name == null}">
			<h2>
				Welcome! Please Register or login
			</h2>
		</c:if>
</div>
<c:import url="template/footer.jsp" />