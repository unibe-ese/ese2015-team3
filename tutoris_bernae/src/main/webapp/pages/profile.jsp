<%@page import="org.sample.model.User"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />

<h1>Profile</h1>

<div class="grid">
    <fieldset>
    <div class="col-2-3">
        <div class="col-1-3"><i class="fa fa-image fa-5x"></i></div>
        <div class="col-2-3">
            <div>
                <div class="col-1-3">Username:</div>
                <div class="col-2-3">${user.username}</div>
            </div>
            <div>
                <div class="col-1-3">First Name:</div>
                <div class="col-2-3">${user.firstName}</div>
            </div>
            <div>
                <div class="col-1-3">Last Name:</div>
                <div class="col-2-3">${user.lastName}</div>
            </div>
            <c:if test="${tutor.averageGrade} != null">
            <div>
                <div class="col-1-3">Average Grade:</div>
                <div class="col-2-3">${tutor.averageGrade}</div>
            </div>
            </c:if>
            
            <sec:authorize access="hasRole('ROLE_TUTOR')">
            <div>
                <div class="col-1-3">Fee:</div>
                <div class="col-2-3">${tutor.fee}</div>
            </div>
            </sec:authorize>
        </div>
    </div>
            
    <sec:authorize access="hasRole('ROLE_TUTOR')">        
    <div class="col-1-3">
        <div class="module classes">
            <div class="col-2-3"><b>Classes</b></div>
            <div class=" col-1-3"><b>Grade</b></div>
        <c:forEach items="${tutor.completedClasses}" var="completedClasses">
            <div class="col-2-3">${completedClasses.classes.name}</div>
            <div class="col-1-3">${completedClasses.grade}</div>
        </c:forEach>
        </div>
    </div>
    <div class="col-2-3">
        <div>Biography:</div>
        <div class="module">${tutor.bio}</div>
    </div>
    </sec:authorize>
    </fieldset>
</div>


<c:if test="${page_error != null }">
	<div class="alert alert-error">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<h4>Error!</h4>
		${page_error}
	</div>
</c:if>


<c:import url="template/footer.jsp" />
