<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>Log in - PQRS Interactive Map</title>
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
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
<link href="https://fonts.googleapis.com/css?family=Architects+Daughter" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- [if lt IE 9] -->
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<!--     [endif] -->
</head>

<body>
	<jsp:include page="admin_header.jsp" />
	<div class="container">
		<div class="row">
			<div class="col-xs-8">
				<br>
				<table>
					<tr>
						<td><img
							src="${pageContext.request.contextPath}/resources/images/big-data-theree.png"
							style="height: 80%; width: 80%"></img></td>
					</tr>
					<tr>
						<td><br><br><br></td>
					</tr>
					<tr>
						<td style="font-family: 'Architects Daughter', cursive; font-size:60px">Welcome, Administrator!
						<td>
					<tr>
				</table>
			</div>
			<div class="col-xs-4">
				<div id="log_in" style="padding-bottom: 100px">
					<div class="login">
						<div class="login-screen">
							<form method="POST" action="${contextPath}/login"
								class="form-signin">
								<h2 style="text-align: center;">ADMIN</h2>
								<br>
								<h4 class='app-title'>Login</h4>
								<div class="form-group ${error != null ? 'has-error' : ''}">
									<span>${message}</span> <input name="username" type="text"
										class="form-control" placeholder="Username" autofocus="true"/>
									<input name="password" type="password" class="form-control"
										placeholder="Password" /> <span>${error}</span> <input
										type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
								</div>
								<button class="btn btn-lg btn-primary btn-block" type="submit">Log
									In</button>
								<%-- <h4 class="text-center">
							<a href="${contextPath}/registration">Create an account</a>
						</h4> --%>


							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />

	<!-- /container -->
	<!-- <section id="services" class="service-item">
		<div class="container">
			<div class="center wow fadeInDown">
				<h2>Our Service</h2>

			</div>

			<div class="row">

				<div class="col-sm-12 col-md-12">
					<body>
						<table border="1" width="100%">
							<tr>
								<td>
									<table border="1" width="100%">
										<tr>
											<th>Name</th>
											<th>Salary</th>
										</tr>
										<tr>
											<td>Ramesh Raman</td>
											<td>5000</td>
										</tr>
										<tr>
											<td>Shabbir Hussein</td>
											<td>7000</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</body>
				</div>
			</div>
			/.row
		</div>
		/.container
	</section>
	/#services -->


	<!--/#footer-->

	<!-- <script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.prettyPhoto.js"></script>
	<script src="js/jquery.isotope.min.js"></script>
	<script src="js/main.js"></script>
	<script src="js/wow.min.js"></script> -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
