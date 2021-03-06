<%@page import="org.sample.model.StudyCourse"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true"%>

<c:import url="template/header.jsp" />

<c:import url="template/function.jsp" />

<h1>Search Tutor</h1>

<div class="container-flex ">
    <div class="flex-item no-border">
        <form:form method="POST" modelAttribute="searchForm" action="submitSearch" id="searchForm" cssClass="form-mc" autocomplete="off">
            <fieldset>
                <legend>Enter search criteria</legend>
                <c:set var="studyCourseErrors"><form:errors path="studyCourse"/></c:set>
                <div class="control-group<c:if test="${not empty studyCourseIdErrors}">error</c:if>">
                        <label class="control-label" for="field-studyCourse">Study Course</label>
                        <div class="controls">
                        <form:select path="studyCourse" required="false" tabindex="1">
                            <form:option value="0">-- no course selected --</form:option>
                            <form:options items="${studyCourseList}" itemValue="id" itemLabel="name"/>
                        </form:select>
                        <form:errors path="studyCourse" cssClass="help-inline" element="span"/>
                    </div>
                </div>
                <c:set var="classesErrors"><form:errors path="classes"/></c:set>
                <div class="control-group<c:if test="${not empty classesErrors}">error</c:if>">
                        <label class="control-label" for="field-classes">Class</label>
                        <div class="controls">
                        <form:select path="classes" required="false" tabindex="2">
                            <form:option value="0">-- no class selected --</form:option>
                            <form:options items="${classesList}" itemLabel="name" itemValue="id"/>
                        </form:select>
                        <form:errors path="classes" cssClass="help-inline" element="span"/>
                    </div>
                </div> 
                <c:set var="feeErrors"><form:errors path="fee"/></c:set>
                <div class="control-group<c:if test="${not empty feeErrors}">error</c:if>"> 
                        <label class="control-label" for="field-fee">Fee</label>
                        <div class="controls">
                        <form:input path="fee" type="number" id="field-fee" tabindex="3" maxlength="35" min="1" max="999" placeholder="Fee"/>
                        <form:errors path="fee" cssClass="help-inline" element="span"/>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" class="button btn btn-primary">Search</button>
                </div>

            </fieldset>
        </form:form>
    </div>
    <c:if test="${not empty noTutor}"><br>
        <div class="col-2-3">
            <br>
            <h1>Search Results</h1>
            <div class="module no-border">${noTutor}</div>
        </div>
    </c:if>
    <c:if test="${not empty tutors}">
        <div class="col-2-3">
            <br>
            <h1>Search Results</h1>
            <table class="sortable">
                <thead>
                    <tr>
                        <td>Name</td>
                        <td>Fee</td>
                        <td>Average Grade</td>
                        <td>Rating</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${tutors}" var="tutors">
                        <tr>
                            <td><a href="/tutoris_baernae/view?tutorId=${tutors.id}"><c:out value="${tutors.student.firstName}"></c:out></a></td>
                            <td>${tutors.fee}</td>
                            <td><c:out value="${tutors.averageGrade}"/></td>
                            <td><c:out value="${tutors.averageRating}"/></td>
                        </tr>

                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<c:import url="template/footer.jsp" />
