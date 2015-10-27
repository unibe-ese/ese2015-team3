<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML>
<!--
	Verti by HTML5 UP
	html5up.net | @n33co
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->

<html>
	<head>
		<title>ESE 2015</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<!--[if lte IE 8]><script src="/tutoris_bernae/css/ie/html5shiv.js"></script><![endif]-->
		<script src="/tutoris_baernae/js/jquery.min.js"></script> 
		<script src="/tutoris_baernae/js/jquery.dropotron.min.js"></script>
		<script src="/tutoris_baernae/js/skel.min.js"></script>
		<script src="/tutoris_baernae/js/skel-layers.min.js"></script>
		<script src="/tutoris_baernae/js/init.js"></script>
		
		<noscript>
			<link rel="stylesheet" href="/tutoris_baernae/css/skel.css" />
			<link rel="stylesheet" href="/tutoris_baernae/css/style.css" />
			<link rel="stylesheet" href="/tutoris_baernae/css/style-desktop.css" />
		</noscript>
		<!--[if lte IE 8]><link rel="stylesheet" href="/tutoris_bernae/css/ie/v8.css" /><![endif]-->
	</head>
	<body onload='document.loginForm.username.focus();' class="no-sidebar">
	


		<!-- Header -->
			<div id="header-wrapper">
				<header id="header" class="container">
				
					<!-- Logo -->
						<div id="logo">
							<h1><a href="/tutoris_baernae/">ESE 2015</a></h1>
							<span>by Team 3</span>
						</div>
					
					<!-- Nav -->
						<nav id="nav">
							<ul>
								<li><a href="/tutoris_baernae/">Welcome</a></li>
								<li><a href="#">About</a></li>
								
								<li><a href="#">Find Tutor</a></li>
								<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_TUTOR')">
								<li>	<h1> <c:if test="${pageContext.request.userPrincipal.name != null}">
								User : ${pageContext.request.userPrincipal.name}</h1></c:if> </li>
								<li class="current">		
								<c:if test="${pageContext.request.userPrincipal.name != null}">
							<h1>
			 <a
					href="javascript:formSubmit()">Logout</a>
			</h1></li>
			<li class="current">		
								
							<h1>
			 <a
					href="/tutoris_baernae/profile">Profile</a>
			</h1></li>
		</c:if></sec:authorize>
								<sec:authorize access="!hasAnyRole('ROLE_USER','ROLE_TUTOR')">
								<li class="current">		
								
							<h1>
			 <a
					href="/tutoris_baernae/login">Login</a>
			</h1></li>
						<li class="current">		
								
							<h1>
			 <a
					href="/tutoris_baernae/register">Register</a>
			</h1></li>
		</sec:authorize>
							</ul>
						</nav>
					
				</header>
			</div>
		
		<!-- Main -->
			<div id="main-wrapper">
				<div class="container">
					<div id="content">

						<!-- Content -->
							<article>