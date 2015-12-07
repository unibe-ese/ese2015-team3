<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />
<form:form method="post" modelAttribute="ratingForm" action="submitRating" id="ratingForm" cssClass="form-mc"  autocomplete="off">
    <fieldset>
        <legend><h1>Write your feedback</h1></legend>
        <form:input path="ratedTutorId" type="hidden" value="${ratingForm.ratedTutorId}"/>
        <div class="mc-column">
            <c:set var="ratingErrors"><form:errors path="rating"/></c:set>
            <div class="control-group<c:if test="${not empty ratingErrors}"> error</c:if>">
                <label class="control-label" for="field-rating">Rating (from 1 to 5)</label>
                <div class="controls">
                <form:input type="number" value = "${ratingForm.rating}" path="rating" id="field-rating" tabindex="1" max="5" min="1" placeholder="rating" autofocus="true"/>
                <form:errors path="rating" cssClass="help-inline" element="span"/>
                </div>
            </div>
            <c:set var="feedbackErrors"><form:errors path="feedback"/></c:set>
            <div class="control-group<c:if test="${not empty feedbackErrors}"> error</c:if>">
                <label class="control-label" for="field-feedback">Feedback</label>
                <div class="controls">
                <form:input path="feedback" id="field-feedback" tabindex="3" maxlength="45" placeholder="feedback"/>
                <form:errors path="feedback" cssClass="help-inline" element="span"/>
                </div>
            </div>
            
        </div>    
       		        
        <div class="form-actions">
            <button tabindex="5" type="submit" class="button btn btn-primary">Send Rating</button>
         </div>
           
    </fieldset>
</form:form>

<c:import url="template/footer.jsp" />