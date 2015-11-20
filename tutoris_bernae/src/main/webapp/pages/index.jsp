<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />
<fieldset>
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
					Welcome!
				</h2>
			</c:if>
		<div class="grid">
		    <div class="col-1-2">
				Are you having some troubles with one of your exams?<br>
				Would you like to get some help from somebody who already passed it?<br>
				Have a look at our website!<br>
				There's a number of tutors eager to lend you a helping hand.<br>
				Are you not convinced?<br>
				Just read the feedback of other students like you.<br>
				<br>
				Don't lose time, sign up and contact a tutor!
			</div>
		    <div class="col-1-2">
		    	<img src="img/students.jpg">
		    </div>
		</div>
	</div>
</fieldset>
<c:import url="template/footer.jsp" />