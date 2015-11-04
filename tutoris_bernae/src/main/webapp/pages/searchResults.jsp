<%@page import="org.sample.model.User"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />

<table class="sortable">
    <thead>
        <tr>
            <td>Username</td>
            <td>Class</td>
            <td>Grade</td>
            <td>Rating</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${tutors}" var="tutors">
        <tr>
            <td><a href="/tutoris_baernae/view=${tutors.id}"><c:out value="${tutors.student.username}"></c:out></a></td>
            <td><c:out value="${classe}"/></td>
            <td><c:out value="${grade}"/></td>
            <td>***</td>
        </tr>
        </c:forEach>
    </tbody>
</table>

<c:if test="${page_error != null}">
    <div class="alert alert-error">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <h4>Error!</h4>
        ${page_error}
    </div>
</c:if>
    
<c:import url="template/footer.jsp" />