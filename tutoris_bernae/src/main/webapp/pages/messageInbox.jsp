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
    <h1>Inbox</h1>
    <div class="container-flex">
        <div class="col-1-4 module no-pad">
            <c:forEach items="${messages}" var="messages">
                <a href="/tutoris_baernae/messageInboxShow?messageId=${messages.id}">
                    <div class="message-preview">
                        <span class="message-sender"><c:if test="${!messages.wasRead}"><i class="fa fa-exclamation"></i></c:if> ${messages.sender.username}</span>
                        <span class="message-subject">${messages.messageSubject}</span>
                    </div>
                </a>
            </c:forEach>
        </div>
        <div class="col-2-3 module no-pad">
            <c:if test = "${not empty selectedMessage}">
		<div class="module bottom-border">From:${selectedMessage.sender.username}</div>
		<div class="module bottom-border">Subject:${selectedMessage.messageSubject}</div>
	 	<div class="module no-border messagetext"><p>${selectedMessage.messageText}<p></div>
                <button type="button" onclick="location.href='/tutoris_baernae/messageInboxAnswer?messageId=${selectedMessage.id}'" class="button btn btn-primary">Answer</button>
            </c:if>
            <c:if test="${empty selectedMessage}">
                <div class="module no-border">No message selected</div>
            </c:if>
        </div>
    </div>
    
</fieldset>

</div>
<c:import url="template/footer.jsp" />