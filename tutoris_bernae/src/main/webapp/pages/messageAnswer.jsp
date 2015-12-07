<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />
<div class="welcome">
		<c:if test="${not empty submitMessage}">
			<div class="message">${submitMessage}</div>
		</c:if>
<h1>Inbox</h1>
<fieldset>
    <div class="container-flex">
        <div class="col-1-4 module no-pad">
            <c:forEach items="${messages}" var="messages">
                <a href="/tutoris_baernae/messageInboxShow?messageId=${messages.id}">
                    <div class="message-preview">
                        <span class="message-sender"><c:if test="${!messages.wasRead}"><i class="fa fa-exclamation"></i></c:if> ${messages.sender.firstName}</span>
                        <span class="message-subject">${messages.messageSubject}</span>
                    </div>
                </a>
            </c:forEach>
        </div>
        <div class="flex-item" style="width:50%;">
            <form:form method="post" modelAttribute="messageForm" action="messageSubmit" id="messageForm" cssClass="form-horizontal"  autocomplete="off">
                <fieldset>
                    <c:set var="receiverErrors"><form:errors path="receiver"/></c:set>
                    
                    <div class="control-group<c:if test="${not empty receiverErrors}"> error</c:if>">
                        <label class="control-label" for="field-receiver"> To: ${messageReceiver}</label>
                        <div class="controls">
                            <form:input path="receiver" type="hidden" value = "${messageForm.receiver}" />
                        </div>
                    </div>
                    
                	<c:set var="messageSubjectErrors"><form:errors path="messageSubject"/></c:set>
                    <div class="control-group<c:if test="${not empty messageSubjectErrors}"> error</c:if>">
                            <label class="control-label" for="field-messageSubject">Subject</label>
                            <div class="controls">
                            <form:select path="messageSubject" id="messageSubject">
		  					<form:option value="Discuss tutorship details">Discuss tutorship details</form:option>
		  					<form:option value="${messageForm.messageSubject}">${messageForm.messageSubject}</form:option>
		  					</form:select>
                            <form:errors path="messageSubject" cssClass="help-inline" element="span"/>
                        </div>
                    </div>
                    
                    <c:set var="messageTextErrors"><form:errors path="messageText"/></c:set>
                    <div class="control-group<c:if test="${not empty messageTextErrors}"> error</c:if>">
                            <label class="control-label" for="field-messageText">Message</label>
                            <div class="controls">
                            <form:textarea path="messageText" type="text" id="field-messageText" tabindex="1" placeholder="Text" autofocus="true"/>
                            <form:errors path="messageText" cssClass="help-inline" element="span"/>
                        </div>
                    </div>
                        
                    <div class="form-actions">
                        <button type="submit" tabindex="4" class="btn btn-primary">Send</button>
                        <sec:authorize access="hasRole('ROLE_TUTOR')">
                            <button type="submit" tabindex="2" name="offerTutorShip" value="true" class="btn btn-primary">Offer Tutorship</button>
   						</sec:authorize>   
                    </div>
                </fieldset>
            </form:form>
        </div>
        <c:if test = "${not empty answeredMessage}">
        <div class="col-1-4 module no-pad">
            <div class="module bottom-border">From: ${answeredMessage.sender.firstName}</div>
            <div class="module message-subject bottom-border">Subject: ${answeredMessage.messageSubject}</div>
            <div class="module message-preview no-border"><p><pre>${answeredMessage.messageText}</pre><p></div>
        </div>
        </c:if>
    </div>
</fieldset>
</div>
<c:import url="template/footer.jsp" />
