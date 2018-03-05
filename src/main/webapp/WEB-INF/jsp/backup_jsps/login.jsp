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

<title>Log in - ADDA</title>
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
<link href="https://fonts.googleapis.com/css?family=Architects+Daughter"
	rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--   [if lt IE 9]
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    [endif] -->
</head>

<body id="b"  style="height:100%">
	<%-- <jsp:include page="header_home.jsp" /> --%>
	<div class="container" id="mid">
		<div class="row">
			<div class="col-xs-12">
				<div id="log_in">
					<div class="login">
						<div class="login-screen">
							<form method="POST" action="${contextPath}/login"
								class="form-signin">
								<!-- <h2 style="text-align: center;">PQRS - Interactive Map</h2>
								<br> -->
								<h1 class='app-title'>Login<br> <br>
								</h1>
								<div class="form-group ${error != null ? 'has-error' : ''}">
									<span>${message}</span>
									<!-- <div class="input-group"><span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span> -->
									<label for="username">Username</label>
									<input style="margin: 0px" name="username" id="username" type="text"
										class="form-control" placeholder="Username" autofocus="true" title="Please fill out this field"/>
									<!-- </div> -->
									<br>
									<!-- <div class="input-group"><span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span> -->
									<label for="password">Password</label>
									<input style="margin: 0px" name="password" id="password" type="password"
										class="form-control" placeholder="Password" title="Please fill out this field"/>
									<!-- </div> -->
									<span>${error}</span> <input type="hidden"
										name="${_csrf.parameterName}" value="${_csrf.token}" /> <br>
								</div>
								<button class="btn btn-lg btn-primary btn-block" type="submit" title="Login Button">Log
									In</button>
								<%-- <h4 class="text-center">
							<a href="${contextPath}/registration">Create an account</a>
						</h4> --%>
							</form>
						</div>
						<div>
					</div>
					
					</div>
			<div style="padding-left:352px;padding-bottom:20px;">If you are having problems logging in email us at <a href="mailto:qam@cms.hhs.gov">qam@cms.hhs.gov</a>.</div>

<div style="padding-left:224px;padding-bottom:20px;">Disclaimer: This web site is not hosted on a Centers for Medicare & Medicaid Services (CMS) web server.</div>


<div style="padding-bottom:20px;border:1px solid black;padding-leftt:10px;"><b>NOTICE - By logging into this system:</b>
<ul style="padding-top:10px;padding-right:5px;">
<li>You are accessing a U.S. Government information system. This information system is provided for U.S. Government-authorized use only.</li>
<li>Unauthorized or improper use of this system may result in disciplinary action, as well as civil and criminal penalties.</li>
<li>By using this information system, you understand and consent to the following;
 <ul>
 <li>You have no reasonable expectation of privacy regarding any communication or data transiting or stored on this information system. At any time, and for any lawful Government purpose, the Government may monitor, intercept, and search and seize any communication or data transiting or stored on this information system.</li>
 <li>Any communication or data transiting or stored in this information system may be disclosed or used for any lawful Government purpose.</li>
</ul>
<li>Authorized use of the system is limited to functions described in CMS IOM 100-09. Users must adhere to CMS Information Security Policies, Standards, and Procedures.</li> 
	</ul>
	</div>			
				</div>
			</div>
		</div>
		<script type="text/javascript">
	var h;
	h=screen.height-330;
	/* alert(document.getElementById('mid').style.height); */
	document.getElementById('mid').style.minHeight=h+'px';
	</script>
	</div>
	<%-- <jsp:include page="footer.jsp" /> --%>

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
