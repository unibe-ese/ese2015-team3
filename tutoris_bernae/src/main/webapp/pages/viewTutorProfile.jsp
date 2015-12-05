<%@page import="org.sample.model.User"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />

<h1>${tutor.student.firstName}</h1>

<div class="container-flex no-border">
    <div class="col-1-4 no-border">
        <img id="profile_picture" src="/tutoris_baernae/img/profile_pics/${user.profilePicture}" alt="profile_pic" />   
    </div>
    <div class="col-1-8 no-border">
        First Name: <br>
        Fee: <br>
        Average Grade: <br>
        <a href="/tutoris_baernae/messageNewTo?receiver=${tutor.student.id}" class="button" ><i class="fa fa-send"></i> Contact</a>
    </div>
    <div class="flex-item no-border">
        ${tutor.student.firstName}<br>
        ${tutor.fee} CHF <br>
        ${tutor.averageGrade}<br>
    </div>
        
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
</div>

	<c:if test="${page_error != null }">
		<div class="alert alert-error">
			<h4>Error!</h4>
			${page_error}
		</div>
	</c:if>


<c:import url="template/footer.jsp" />
