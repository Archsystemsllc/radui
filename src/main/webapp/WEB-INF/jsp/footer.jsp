<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Footer</title>
<%-- <link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet"> --%>
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/Comrad_icon.png">
<link href="${pageContext.request.contextPath}/resources/css/main.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/responsive.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/prettyPhoto.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/animate.min.css"
	rel="stylesheet">
<%-- <link href="${pageContext.request.contextPath}/resources/css/login.css"
	rel="stylesheet"> --%>
<link href="https://fonts.googleapis.com/css?family=Architects+Daughter"
	rel="stylesheet">
</head>
<body>
	<footer id="footer" class="midnight-blue">
	<div class="container">
		<div class="row">
			<div class="col-md-6">
			
			<p>Internet browsers compatible with CRAD are: &nbsp;
			1. Internet Explorer (Version 8.0 or higher) &nbsp;
			2. Chrome <br/>
			Few versions of IE 11.0 have issues with CRAD functionality.&nbsp;
			Recommended browser to use is chrome
			</p>
				
			</div>
			<div class="col-md-6">
				<ul class="pull-right" style="margin-top: 14px">
					<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/dashboard" title="Home Page">Home</a></li>
					<sec:authorize access="hasAuthority('Administrator') or hasAuthority('Quality Manager') or hasAuthority('MAC Admin') or hasAuthority('MAC User')  or hasAuthority('CMS User')">
									 <li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/privacy" title="Privacy, Security and Disclaimer Page">Privacy,Security and Disclaimer</a></li> 
					</sec:authorize>
					<li><a href="mailto:qamadmin@archsystemsinc.com" title="Contact Us Page">Contact Us</a></li>
				</ul>
			</div>
		</div>
	</div>
	</footer>
</body>
</html>