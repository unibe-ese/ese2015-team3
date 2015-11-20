<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true"%>

<c:import url="template/header.jsp" />

<c:import url="template/function.jsp" />

<h1>Add a new profile picture</h1>

	<form:form method="post" action="fileuploadpage?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">

		<!-- <form:errors path="*" cssClass="errorblock" element="div" /> -->

		Please select a file to upload :
		</br>
		<input type="file" name="file" />	
		</br>
		<input type="submit" value="Upload"/>
		<button type="reset" class="button btn" onclick="window.history.back();">Cancel</button>
		
		
		<!-- <span><form:errors path="file" cssClass="error" /></span> -->

	</form:form>

<c:import url="template/footer.jsp" />