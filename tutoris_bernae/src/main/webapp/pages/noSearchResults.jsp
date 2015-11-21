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
No Tutors found :(
</div>
<button type="button" class="button btn" onclick="location.href='/tutoris_baernae/findTutor'">Search again</button>

<c:if test="${page_error != null}">
    <div class="alert alert-error">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <h4>Error!</h4>
        ${page_error}
    </div>
</c:if>
    
<c:import url="template/footer.jsp" />