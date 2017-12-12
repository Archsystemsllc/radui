<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Header</title>
<link href="https://fonts.googleapis.com/css?family=Rubik"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/main.css"
	rel="stylesheet" />
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
	href="${pageContext.request.contextPath}/resources/css/font-awesome.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/animate.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css"
	rel="stylesheet">
<%-- <link href="${pageContext.request.contextPath}/resources/css/login.css"
	rel="stylesheet"> --%>
<link href="https://fonts.googleapis.com/css?family=Architects+Daughter"
	rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- [if lt IE 9] -->
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<!--     [endif] -->
</head>
<body id="b">
	<header id="header">  <nav class="navbar navbar-inverse"
		role="banner">
	<div class="container">
		<table height="110px">
			<tr>
				<td rowspan="3"
					style="padding-bottom: 0px; border: 0px; vertical-align: top">
					<div class="navbar-header">
						

						<div class="navbar-brand" style="display: inline">
							<!--<a class="navbar-brand" href="page0.html">-->
								<a href="https://www.cms.gov/" target="_blank"><img
								src="${pageContext.request.contextPath}/resources/images/logo.png"
								alt="logo" height="100px" style="float: left; width: 60%"></a>
							
						</div>

					</div>
				</td>
				<td style="border: 0px;"><div class="social">
						Welcome ${pageContext.request.userPrincipal.name} | <a
							href="${pageContext.request.contextPath}/logout">Logout</a>
					</div></td>
			</tr>
			<tr>
				<td style="float: right; border: 0px"><button title="increase font size"
						class="btn btn-primary btn-xs" onclick="increaseFontSizeBy1px()">
						<i class="fa fa-search-plus"></i>
					</button>
					<button title="decrease font size" class="btn btn-primary btn-xs"
						onclick="decreaseFontSizeBy1px()">
						<i class="fa fa-search-minus"></i>
					</button> 
				</td>
			</tr>
			<tr>
				<td width="70%"
					style="vertical-align: bottom; border: 0px; padding: 0px">
					<div class="collapse navbar-collapse navbar-right">
						<ul class="nav navbar-nav" style="font-weight: bold">
							<li style="margin-left: 0px"> <a href="${pageContext.request.contextPath}/admin/dashboard">Home</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/csrlist">CSR</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/templates">QAM Data Analytics</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/users">Rebuttals</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/contactus">My Account</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/download-helpdoc">Help Guide</a></li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<!--/.container--> </nav> <!--/nav--> </header>

	<script>
		function increaseFontSizeBy1px() {
			var font = parseInt($('#b').css('font-size'));
	        if(font<22)
			font++;
			document.getElementById('b').style.fontSize = font + "px";
		}
		function decreaseFontSizeBy1px() {
			var font = parseInt($('#b').css('font-size'));
	        if(font>10)
			font = font - 1;
			document.getElementById('b').style.fontSize = font + "px";
		}
		
	</script>
	
</body>
</html>