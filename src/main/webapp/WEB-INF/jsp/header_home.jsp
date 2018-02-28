<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css?family=Rubik"
	rel="stylesheet">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/adda_ico.png">
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
<link href="${pageContext.request.contextPath}/resources/css/login.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Architects+Daughter"
	rel="stylesheet">
</head>
<body id="b">
	<header id="header"> <%-- <div class="top-bar">
			<div class="container">
				<div class="row">
					<div class="col-sm-6 col-xs-4">
						<div class="top-number">
							<p>
								<i class="fa fa-phone"></i> +1 (410) 277-9781
							</p>
						</div>
					</div>
					<div class="col-sm-6 col-xs-8">
						  <div class="social">
						  <p style="color:#fff">Welcome ${pageContext.request.userPrincipal.name}| <a href="${pageContext.request.contextPath}" >Logout</a></p>
<!--                             <ul class="social-share">
                                <li><a title="facebook" target="_blank" href="https://www.facebook.com/archsystemsllc"><i class="fa fa-facebook"></i></a></li>
                                <li><a title="twitter" target="_blank" href="https://twitter.com/ArchsystemsMD"><i class="fa fa-twitter"></i></a></li>
                                <li><a title="linkedin" target="_blank" href="https://www.linkedin.com/company/arch-systems-llc"><i class="fa fa-linkedin"></i></a></li>
                                <li><a title="youtube" target="_blank" href="https://www.youtube.com/channel/UCCuPoi5quC8DaAoYc2_nq9A"><i class="fa fa-youtube"></i></a></li> 
                                <li><a title="google+" target="_blank" href="https://plus.google.com/107095612689266148490"><i class="fa fa-google-plus"></i></a></li>
                                <li><a href="#"><i class="fa fa-dribbble"></i></a></li>
                                <li><a href="#"><i class="fa fa-skype"></i></a></li>
                            </ul>  -->
                            <div class="search">
                                <form role="form">
                                    <input type="text" class="search-form" autocomplete="off" placeholder="Search">
                                    <i class="fa fa-search"></i>
                                </form>
                           </div>
                       </div> 
					</div>
				</div>
			</div>
			<!--/.container-->
		</div> --%> <!--/.top-bar--> <nav class="navbar navbar-inverse"
		role="banner">
	<div class="container">
		<div class="navbar-header" style="min-height: 110px;">
			<!-- 	<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button> -->
			<div class="navbar-brand">
				<div class="row">
					<!--<a class="navbar-brand" href="page0.html">-->
					<div class="col-sm-8">
						<a href="https://www.cms.gov/" target="_blank"><img
							src="${pageContext.request.contextPath}/resources/images/logo.png"
							alt="CMS logo" height="100px" title="Click on CMS Logo navigates to CMS Webpage"
							style="display: block; margin: 0 auto; width: 80%"></a>
					</div>
					<%-- <div class="col-sm-8">
						<img
							src="${pageContext.request.contextPath}/resources/images/logo.png"
							alt="logo" height="100px"
							style="display: block; margin: auto; width: 100%;">
					</div> --%>
					<!-- <div class="col-sm-2">
						<div style="text-align:right;float: right; padding-top: 60px; height: 100px">
							<button class="btn btn-primary btn-xs"
								onclick="increaseFontSizeBy1px()">
								<i class="fa fa-search-plus"></i>
							</button>
							<button class="btn btn-primary btn-xs"
								onclick="decreaseFontSizeBy1px()">
								<i class="fa fa-search-minus"></i>
							</button>
						</div>
					</div> -->
				</div>
			</div>
		</div>
		<div class="collapse navbar-collapse navbar-right">
		<%-- 	<ul class="nav navbar-nav" style="padding-top:80px">
				<li class="active"><a
					href="${pageContext.request.contextPath}/login">Home</a></li>
				<li><a href="${pageContext.request.contextPath}/admin/dashboard">Hypothesis</a></li>
				<li><a href="#">Contact Us</a></li>
			</ul> --%>
		</div>
	</div>
	<!--/.container--> </nav> <!--/nav--> </header>
	<script>
		function increaseFontSizeBy1px() {
			var font = parseInt($('#b').css('font-size'));
	
			font++;
			document.getElementById('b').style.fontSize = font + "px";
		}
		function decreaseFontSizeBy1px() {
			var font = parseInt($('#b').css('font-size'));
	
			font = font - 1;
			document.getElementById('b').style.fontSize = font + "px";
		}
	</script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</body>
</html>