<%@page import="org.sample.model.User"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />


<h1>Search Results</h1>
<div>
    Filters: <c:forEach items="${searchCriteria}" var="searchCriteria">${searchCriteria} </c:forEach>
</div>
<table class="sortable">
    <thead>
        <tr>
            <td>Username</td>
            <td>Fee</td>
            <td>Average Grade</td>
            <td>Rating</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${tutors}" var="tutors">
        <tr>
            <td><a href="/tutoris_baernae/view?tutorId=${tutors.id}"><c:out value="${tutors.student.username}"></c:out></a></td>
            <td>${tutors.fee}</td>
            <td><c:out value="${tutors.averageGrade}"/></td>
            <td>***</td>
        </tr>
        
        </c:forEach>
    </tbody>
</table>

<button type="button" class="button btn" onclick="location.href='/tutoris_baernae/findTutor'">Search again</button>

<c:if test="${page_error != null}">
    <div class="alert alert-error">
        <button type="button" class="button close" data-dismiss="alert">&times;</button>
        <h4>Error!</h4>
        ${page_error}
    </div>
</c:if>
    
<c:import url="template/footer.jsp" />