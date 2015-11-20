<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form method="post" action="fileuploadpage?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">

	<!-- <form:errors path="*" cssClass="errorblock" element="div" /> -->

	Please select a file to upload : <input type="file" name="file" />
	<input type="submit" value="Upload"/>
	
	<!-- <span><form:errors path="file" cssClass="error" /></span> -->

</form:form>