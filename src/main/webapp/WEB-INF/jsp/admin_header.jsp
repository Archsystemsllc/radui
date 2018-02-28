<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Header</title>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="https://fonts.googleapis.com/css?family=Rubik"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/main.css"
	rel="stylesheet" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/adda_ico.png">
<%-- <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet"> --%>
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
<script type="text/javascript">
    var timeoutHandle = null;
    function startTimer(timeoutCount) {
        if (timeoutCount == 0) {
            window.location.href = '${pageContext.request.contextPath}/logout';
        } else if (timeoutCount < 600) {
        	var minutes = Math.floor((timeoutCount * 3000)/1000 / 60);
        	var seconds = (timeoutCount * 3000)/1000 - (minutes *60);
            document.getElementById('sessionTimer').innerHTML = 'Session Timeout: ' +minutes +':'+seconds;
        }
        timeoutHandle = setTimeout(function () { startTimer(timeoutCount-1);}, '3000');
    }
    function refreshTimer() {
        killTimer(timeoutHandle);
        startTimer(3);
    }
  </script>

</head>
<body id="b" onload="startTimer(600)">
	<header id="header"> <nav class="navbar navbar-inverse"
		role="banner">
	<div>
		<div class="row">
		<div id="sessionTimer" align="right" style="padding-right: 30px; color: red" ></div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table>
					<tr>
						<td rowspan="3"
							style="padding-bottom: 0px; border: 0px; vertical-align: top">
							<div class="navbar-header">


								<div class="navbar-brand" style="display: inline">
									<!--<a class="navbar-brand" href="page0.html">-->
									<a href="https://www.cms.gov/" target="_blank"><img
										src="${pageContext.request.contextPath}/resources/images/logo.png"
										alt="CMS logo" height="100px" style="float: left; width: 60%" title="Click on CMS Logo to navigate to CMS webpage"></a>

								</div>

							</div>
						</td>
						<td style="border: 0px;"><div class="social">
								Welcome ${pageContext.request.userPrincipal.name} | <a
									href="${pageContext.request.contextPath}/logout">Logout</a>
							</div></td>
					</tr>
					<tr>
						<td style="float: right; border: 0px"><button aria-label="zoom in button"
								title="increase font size" class="btn btn-primary btn-xs"
								onclick="increaseFontSizeBy1px()">
								<i class="fa fa-search-plus"></i>
							</button>
							<button aria-label="zoom out button" title="decrease font size" class="btn btn-primary btn-xs"
								onclick="decreaseFontSizeBy1px()">
								<i class="fa fa-search-minus"></i>
							</button></td>
					</tr>
					<tr>
						<td
							style="vertical-align: bottom; border: 0px; padding: 0px">
							<div class="collapse navbar-collapse navbar-right">
								<ul class="nav navbar-nav" style="font-weight: bold" id="navlist">
									<li style="margin-left: 0px"><a class="active" href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/dashboard">Home</a></li>
									<sec:authorize access="hasAuthority('Administrator') or hasAuthority('Quality Manager') or hasAuthority('Quality Monitor') or hasAuthority('MAC Admin') or hasAuthority('MAC User')">
										<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/scorecardlist">Scorecard</a></li>
									</sec:authorize>
									<sec:authorize access="hasAuthority('Administrator') or hasAuthority('Quality Manager') or hasAuthority('MAC Admin') or hasAuthority('MAC User')  or hasAuthority('CMS User')">
										<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/reports">Reports</a></li>
									</sec:authorize>
									<sec:authorize access="hasAuthority('Administrator') or hasAuthority('MAC User')">
										<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/csrlist">CSR</a></li>
									</sec:authorize>
									<sec:authorize access="hasAuthority('Administrator') or hasAuthority('Quality Manager') or hasAuthority('MAC Admin') or hasAuthority('MAC User')  or hasAuthority('CMS User') or hasAuthority('Quality Monitor')">
										<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/rebuttallist">Rebuttal</a></li>
									</sec:authorize>
								
									<sec:authorize access="hasAuthority('Administrator') or hasAuthority('MAC Admin')">
									<li class="dropdown">
										<a class="dropdown-toggle" type="button" data-toggle="dropdown" href="#">User Management</a>
										<span class="caret"></span>
										<ul class="dropdown-menu">
										<sec:authorize access="hasAuthority('Administrator') or hasAuthority('MAC Admin')">
									      	<li><a href="${pageContext.request.contextPath}/admin/createusers">Create Users</a></li>
									     </sec:authorize>
									      <li><a href="${pageContext.request.contextPath}/admin/listofusers">List Users</a></li>
									    </ul>
									</li>
									</sec:authorize>
									
									<li><a href="${pageContext.request.contextPath}/admin/myaccount">My Account</a></li>	
									<li class="anchor"><a href="#">Help Guide</a></li>									
									
									<li>&nbsp; &nbsp;</li>			
								</ul>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<!--/.container--> </nav> <!--/nav--> </header>

	<script>
		function increaseFontSizeBy1px() {
			var font = parseInt($('#b').css('font-size'));
			if (font < 22)
				font++;
			document.getElementById('b').style.fontSize = font + "px";
		}
		function decreaseFontSizeBy1px() {
			var font = parseInt($('#b').css('font-size'));
			if (font > 10)
				font = font - 1;
			document.getElementById('b').style.fontSize = font + "px";
		}
	</script>

</body>
</html>