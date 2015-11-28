<%@page import="org.sample.model.StudyCourse"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true"%>

<c:import url="template/header.jsp" />

<c:import url="template/function.jsp" />

<h1>Search Tutor</h1>

<form:form method="POST" modelAttribute="searchForm" action="submitSearch" id="searchForm" cssClass="form-mc" autocomplete="off">
    <fieldset>
        <legend>Enter search criteria</legend>

        <div class="mc-column">
            <c:set var="studyCourseErrors"><form:errors path="studyCourse"/></c:set>
            <div class="control-group<c:if test="${not empty studyCourseIdErrors}">error</c:if>">
                <label class="control-label" for="field-studyCourse">Study Course</label>
                <div class="controls">
                    <form:select path="studyCourse" required="false">
                        <form:option value="0">-- no course selected --</form:option>
                        <form:options items="${studyCourseList}" itemValue="id" itemLabel="name"/>
                    </form:select>
                    <form:errors path="studyCourse" cssClass="help-inline" element="span"/>
                </div>
            </div>
            <c:set var="feeErrors"><form:errors path="fee"/></c:set>
            <div class="control-group<c:if test="${not empty feeErrors}">error</c:if>"> 
                <label class="control-label" for="field-fee">Fee</label>
                <div class="controls">
                    <form:input path="fee" id="field-fee" tabindex="3" maxlength="35" placeholder="Fee"/>
                    <form:errors path="fee" cssClass="help-inline" element="span"/>
                </div>
            </div>
        </div>
        
        <div class="mc-column">
            <c:set var="classesErrors"><form:errors path="classes"/></c:set>
            <div class="control-group<c:if test="${not empty classesErrors}">error</c:if>">
                <label class="control-label" for="field-classes">Class</label>
                <div class="controls">
                    <form:select path="classes" required="false">
                        <form:option value="0">-- no class selected --</form:option>
                        <form:options items="${classesList}" itemLabel="name" itemValue="id"/>
                    </form:select>
                    <form:errors path="classes" cssClass="help-inline" element="span"/>
                </div>
            </div> 
        </div>
        
        <div class="form-actions">
            <button type="submit" class="button btn btn-primary">Search</button>
        </div>
            
    </fieldset>
</form:form>

<c:import url="template/footer.jsp" />
