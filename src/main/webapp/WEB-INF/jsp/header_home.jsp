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
			
		</div> --%> <!--/.top-bar--> 
		<nav class="navbar navbar-inverse" role="banner">
	<div class="container">
		<div class="navbar-header" style="min-height: 110px;">
			
			<div class="navbar-brand">
				<div class="row">
					<!--<a class="navbar-brand" href="page0.html">-->
					<div class="col-sm-8">
						<a href="#" ><img
							src="${pageContext.request.contextPath}/resources/images/CRAD LOGO.png"
							alt="CMS logo" height="100px" title="CRAD Logo"
							style="display: block; margin: 0 auto; width: 150px"></a>
					</div>					
				</div>
			</div>
			<%-- <div class="navbar-brand" style="display: inline">
									<!--<a class="navbar-brand" href="page0.html">-->
									
								<a href="#" ><img
										src="${pageContext.request.contextPath}/resources/images/CRAD LOGO.png"
										alt="CMS logo" height="100px" style="width: 300px;align-content: center" title="CRAD Logo"></a>

								</div> --%>
		</div>

	</div>
	<!--/.container--> 
	</nav>
	<!--/nav--> 
	</header>
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