<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />


<h1>USER</h1>


   <table>
    <div>
    	<div>Id: ${user.id}</div>
        <div>First Name: ${user.firstName}</div>
        <div>Last Name: ${user.lastName}</div>
		<div>Email: ${user.email}</div>
        <div>Team: ${user.team.teamName}</div>
    </div>
</table>  

<c:import url="template/footer.jsp" />
