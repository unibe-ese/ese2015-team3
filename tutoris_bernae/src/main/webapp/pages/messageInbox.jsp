<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />
<div class="welcome">
		<c:if test="${not empty submitMessage}">
			<div class="message">${submitMessage}</div>
		</c:if>
	<a href="/tutoris_baernae/messageNew"> New </a>  
	<c:if test = "${not empty selectedMessage}">
	<p>${selectedMessage.sender.username}<p>
	<p>${selectedMessage.messageSubject}<p>
	<p>${selectedMessage.messageText}<p>
	<a href="/tutoris_baernae/messageInboxAnswer?messageId=${selectedMessage.id}"> Answer </a>  
	</c:if>
	
		 <c:forEach items="${messages}" var="messages">
		 <p><c:if test = "${!messages.wasRead}">
	Unread! 
	</c:if>${messages.sender.username},${messages.messageSubject},
		 <a href="/tutoris_baernae/messageInboxShow?messageId=${messages.id}"> open </a>  
		 </p>
		 </c:forEach>
</div>
<c:import url="template/footer.jsp" />