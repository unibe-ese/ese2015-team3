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
<fieldset>
<div class="grid">
	<div class="col-1-3">
		<div>
		<div class="module">
		<c:forEach items="${messages}" var="messages">
		<p><c:if test = "${!messages.wasRead}">
			Unread! 
			</c:if>
		${messages.sender.username},${messages.messageSubject.messageSubjectName},
		<a href="/tutoris_baernae/messageInboxShow?messageId=${messages.id}"> open </a>  
		</p>
		</c:forEach>
		</div>
		</div>
	</div>
	<div class="col-1-3">
		<c:if test = "${not empty selectedMessage}">
		<div>
		 
		<div class="module">From:${selectedMessage.sender.username}</div>
		<div class="module">Subject:${selectedMessage.messageSubject.messageSubjectName}</div>
	 	<div class="module"><p>${selectedMessage.messageText}<br>
	 		<a href="/tutoris_baernae${selectedMessage.messageSubject.actionBaseLink}${selectedMessage.sender.id}"> confirm /tutoris_baernae/${selectedMessage.messageSubject.actionBaseLink}${selectedMessage.sender.id} </a><p></div>
		<a href="/tutoris_baernae/messageInboxAnswer?messageId=${selectedMessage.id}"> Answer </a>  
		
		</c:if>
		</div>
	</div>
	<div class="col-1-3"></div>
</div>
<fieldset>
</div>
<c:import url="template/footer.jsp" />