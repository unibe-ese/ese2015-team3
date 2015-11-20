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
		<a href="/tutoris_baernae/messageNew"> New </a>  
		</div>

		<div class="module">
		<c:forEach items="${messages}" var="messages">
		<p><c:if test = "${!messages.wasRead}">
			Unread! 
			</c:if>
		${messages.sender.username},${messages.messageSubject},
		<a href="/tutoris_baernae/messageInboxShow?messageId=${messages.id}"> open </a>  
		</p>
		</c:forEach>
		</div>
		</div>
	</div>
		<div class="col-1-3">
		<div>
		<form:form method="post" modelAttribute="messageForm" action="messageSubmit" id="messageForm" cssClass="form-horizontal"  autocomplete="off">
    <fieldset>
    <c:set var="receiverErrors"><form:errors path="receiver"/></c:set>
        <div class="control-group<c:if test="${not empty receiverErrors}"> error</c:if>">
            <label class="control-label" for="field-receiver"> To: </label>

            <div class="controls">
                <form:input path="receiver" id="field-receiver" tabindex="1" maxlength="45" placeholder="receiver"/>
                <form:errors path="receiver" cssClass="help-inline" element="span"/>
            </div>
        </div>
        <c:set var="messageSubjectErrors"><form:errors path="messageSubject"/></c:set>
        <div class="control-group<c:if test="${not empty messageSubjectErrors}"> error</c:if>">
            <label class="control-label" for="field-messageSubject">Subject</label>
            <div class="controls">
                <form:input path="messageSubject" id="field-messageSubject" tabindex="2" maxlength="35" placeholder="Subject"/>
                <form:errors path="messageSubject" cssClass="help-inline" element="span"/>
            </div>
        </div>
        <c:set var="messageTextErrors"><form:errors path="messageText"/></c:set>
        <div class="control-group<c:if test="${not empty messageTextErrors}"> error</c:if>">
            <label class="control-label" for="field-messageText">Message</label>
            <div class="controls">
                <form:textarea path="messageText" type = "text" id="field-messageText" tabindex="3" maxlength="35" placeholder="Text"/>
                <form:errors path="messageText" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Send</button>
         </div>
    </fieldset>
</form:form>
		</div>
	</div>
	<div class="col-1-3">
		<c:if test = "${not empty answeredMessage}">
		<div> 
		<div class="module">From:${answeredMessage.sender.username}</div>
		<div class="module">Subject:${answeredMessage.messageSubject}</div>
	 	<div class="module"><p>${answeredMessage.messageText}<p></div>
		</c:if>
		</div>
	</div>
	<div class="col-1-3"></div>
</div>
<fieldset>
</div>
<c:import url="template/footer.jsp" />