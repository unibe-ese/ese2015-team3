<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<body>
	<h2>Spring MVC file upload example</h2>

		<span>The upload was successful. This will be your profile picture:</span></br>
		<img src="/tutoris_baernae/img/profile_pics/${fileName}" alt="profile_pic" height="150px" width="150px" /> </br>
		
	<!-- FileName : "
	<strong> ${fileName} </strong>" - Uploaded Successful. -->
	
	<c:redirect url="/profile" />

</body>
</html>