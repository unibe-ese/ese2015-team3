<%@page import="org.sample.model.User"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />

<%-- Check if the profile displayed now belongs to the currently logged in user. --%>
<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_TUTOR')">
	<sec:authentication property="principal.username" var="principalName"/>
	<c:if test="${principalName == user.email}" var="isUserPrincipal"/>
</sec:authorize>


<h1>Profile</h1>

<<<<<<< HEAD
<div class="grid">
    <fieldset>   
    <div class="col-2-3">
        <div class="col-1-3">
        	<div class="col-1-1">
				<img id="profile_picture" src="/tutoris_baernae/img/profile_pics/${user.profilePicture}" alt="profile_pic" />
			</div>
			<c:if test="${isUserPrincipal}">
				<div class="col-2-2">
					<a href="/tutoris_baernae/fileupload" class="button">Edit profile picture</a></br>
				</div>
			</c:if>
		</div>
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
            
            
            <sec:authorize access="hasRole('ROLE_TUTOR')">
            <div>
                <div class="col-1-3">Fee:</div>
                <div class="col-2-3">${tutor.fee}</div>
            </div>
            <c:if test="${tutor.averageGrade} != null">
            <div>
                <div class="col-1-3">Average Grade:</div>
                <div class="col-2-3">${tutor.averageGrade}</div>
            </div>
            </c:if>
            <div>
                <div class="col-1-3">Tutorships via this page:</div>
                <div class="col-2-3">${tutor.confirmedTutorShips}</div>
            </div>
            </sec:authorize>
        </div>
=======
<div class="container-flex no-border">
    <div class="col-1-4 no-border">
        <img id="profile_picture" src="/tutoris_baernae/img/profile_pics/${user.profilePicture}" alt="profile_pic" />   
        <c:if test="${isUserPrincipal}">
            <a href="/tutoris_baernae/fileupload" class="button">Edit profile picture</a><br>
            <a href="/tutoris_baernae/edit" class="button">Edit profile</a>
        </c:if>
>>>>>>> 3c1a1a63db6c4ca9cd424a93d55dc28cba838dcc
    </div>
    <div class="col-1-8 no-border">
        First Name: <br>
        <c:if test="${isUserPrincipal}">
            Last Name: <br>
            Email: <br>
        </c:if>
        <sec:authorize access="hasRole('ROLE_TUTOR')">
            Fee: <br>
            Average Grade: <br>
        </sec:authorize>
    </div>
    <div class="flex-item no-border">
        ${user.firstName}<br>
        <c:if test="${isUserPrincipal}">
            ${user.lastName} <br>
            ${user.email} <br>
        </c:if>
        <sec:authorize access="hasRole('ROLE_TUTOR')">
            ${tutor.fee} CHF <br>
            ${tutor.averageGrade}<br>
        </sec:authorize>
    </div>
        
    <sec:authorize access="hasRole('ROLE_TUTOR')">
    <div class="col-1-3 module">
        <div class="col-2-3"><b>Class</b></div>
        <div class="col-1-3"><b>Grade</b></div>
        <c:forEach items="${tutor.completedClasses}" var="completedClasses">
            <div class="col-2-3">${completedClasses.classes.name}</div>
            <div class="col-1-3">${completedClasses.grade}</div>
        </c:forEach>
    </div>
    <div class="col-1-2">
        Biography: 
    </div>
    <div class="col-1-2">
        Feedback:
    </div>
    <div class="flex-item module"><pre>${tutor.bio}</pre></div>
    <div class="module" style="margin-left: 1em; width: 50%;">TODO</div>
    </sec:authorize>
</div>

<c:if test="${page_error != null }">
	<div class="alert alert-error">
		<h4>Error!</h4>
		${page_error}
	</div>
</c:if>


<c:import url="template/footer.jsp" />
