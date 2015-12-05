<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>
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
		<script src="/tutoris_baernae/js/sorttable.js"></script>
                <script src="/tutoris_baernae/js/highlight.js"></script>
                <script src="/tutoris_baernae/js/tooltip.js"></script>
		<noscript>
			<link rel="stylesheet" href="/tutoris_baernae/css/skel.css" />
                        <link rel="stylesheet" href="/tutoris_baernae/css/main.css"/>
			<link rel="stylesheet" href="/tutoris_baernae/css/style.css" />
			<link rel="stylesheet" href="/tutoris_baernae/css/style-desktop.css" />
                        <link rel="stylesheet" href="/tutoris_baernae/css/font-awesome.min.css" />
		</noscript>
		<!--[if lte IE 8]><link rel="stylesheet" href="/tutoris_bernae/css/ie/v8.css" /><![endif]-->
	</head>
	<body onload='document.loginForm.username.focus();' class="no-sidebar">
	


		<!-- Header -->
			<div id="header-wrapper" style="background-image:url(img/bg.jpg)">
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
								
								<li><a href="/tutoris_baernae/findTutor">Find Tutor</a></li>
                                                                
                                                        <%-- AUTHORIZED --%>			
                                                        <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_TUTOR')" var="loggedIn">
                                                                    <li class="current">
                                                                        <h1><a href="#"><c:if test="${loggedIn}"><i class="fa fa-user"></i> : ${sessionScope.loggedInUser.firstName}</a></h1></c:if>
                                                                    <ul>
                                                                        <li>
                                                                            <a href="/tutoris_baernae/profile"><i class="fa fa-user"></i> View Profile</a>
                                                                        </li>
                                                                        <li>
                                                                            <a href="/tutoris_baernae/edit"><i class="fa fa-pencil"></i> Edit Profile</a>
                                                                        </li>
                                                                        <li><hr></li>
                                                                        <li>
                                                                            <a href="javascript:formSubmit()"><i class="fa fa-sign-out"></i> Logout</a>
                                                                        </li>
                                                                    </ul>
                                                                    </li>
								<c:if test="${loggedIn}">
                                                                <li class="current">
                                                                    <h1><a href="/tutoris_baernae/messageInbox"><i class="fa fa-envelope"></i> Inbox</a></h1>
                                                                </li>
                                                                </c:if>
                                                                </sec:authorize>
                                                                
                                                        <%-- NOT AUTHORIZED --%>
                                                                <sec:authorize access="!hasAnyRole('ROLE_USER','ROLE_TUTOR')">
								<li class="current">
                                                                    <h1><a href="/tutoris_baernae/login">Login</a></h1>
                                                                </li>
                                                                <li class="current">
                                                                    <h1><a href="/tutoris_baernae/register">Register</a></h1>
                                                                </li>
                                                                </sec:authorize>
							</ul>
						</nav>
                                                <script language="javascript">setPage()</script>
				</header>
			</div>
		
		<!-- Main -->
			<div id="main-wrapper">
				<div class="container">
					<div id="content">

						<!-- Content -->
							<article>
							    							