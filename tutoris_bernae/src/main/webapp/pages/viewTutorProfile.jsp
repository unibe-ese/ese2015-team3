<%@page import="org.sample.model.User"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/header.jsp" />
<c:import url="template/function.jsp" />

<h1>${tutor.student.username}</h1>
<fieldset>

<div class="grid">
    <div class="col-2-3">
        <div class="col-1-3"><i class="fa fa-image fa-5x"></i></div>
        <div class="col-2-3">
            <div>
                <div class="col-1-3">Username:</div>
                <div class="col-2-3">${tutor.student.username}</div>
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
            <div>
                <div class="col-1-3">Fee:</div>
                <div class="col-2-3">${tutor.fee}</div>
            </div>
            <div>
                <button type="button" onclick="location.href='/tutoris_baernae/messageNewTo?receiver=${tutor.student.username}'"><i class="fa fa-send"></i> Contact</button>
            </div>
            <br>            
        </div>
        
        <div>
            <div>Biography:</div>
            <div class="module">${tutor.bio}</div>
        </div>
    </div>
    <div class="col-1-3">
        <div class="module classes">
            <div class="col-2-3"><b>Class</b></div>
            <div class=" col-1-3"><b>Grade</b></div>
        <c:forEach items="${tutor.completedClasses}" var="completedclasses">
            <div class="col-2-3">${completedclasses.classes.name}</div>
            <div class=" col-1-3"> ${completedclasses.grade}</div>
            <div class="col-1-3"> </div>
        </c:forEach>
        </div>
    </div>    
</div>
</fieldset>



	<c:if test="${page_error != null }">
		<div class="alert alert-error">
			<h4>Error!</h4>
			${page_error}
		</div>
	</c:if>


<c:import url="template/footer.jsp" />
