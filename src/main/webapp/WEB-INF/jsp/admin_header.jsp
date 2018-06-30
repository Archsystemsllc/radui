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
	href="${pageContext.request.contextPath}/resources/images/logo.png">
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
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
        } else if (timeoutCount <= 600) {
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
						<td style="border: 0px;"><div class="social" style="color:black">
								Welcome ${pageContext.request.userPrincipal.name} | <a
									href="${pageContext.request.contextPath}/logout" title="Logout button">Logout</a>			
									</div>
							
						</td>
					</tr>
					<tr>
						<td style="float: right; border: 0px"><button aria-label="zoom in"
								title="increase font size" class="btn btn-primary btn-xs"
								onclick="increaseFontSizeBy1px()">
								<i class="fa fa-search-plus"></i>
							</button>
							<button aria-label="zoom out" title="decrease font size" class="btn btn-primary btn-xs"
								onclick="decreaseFontSizeBy1px()">
								<i class="fa fa-search-minus"></i>
							</button>
							
						</td>
					</tr>
					<tr>
						<td style="float: right; border: 0px">							
							<div id="sessionTimer" align="right" style="color: #000080"></div>
						</td>
					</tr>
					</table>
					<table>
					<tr>
						<td
							style="vertical-align: bottom; border: 0px; padding: 0px">
							<div class="collapse navbar-collapse">
								<ul class="nav navbar-nav" style="font-weight: bold" id="navlist">
									<li><a class="${menu_highlight == 'home' ? 'active' : ''}" href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/dashboard" title="Home Page">Home</a></li>
									<sec:authorize access="hasAuthority('Administrator') or hasAuthority('Quality Manager') or hasAuthority('Quality Monitor') or hasAuthority('MAC Admin') or hasAuthority('MAC User') ">
										<li><a class="${menu_highlight == 'scorecard' ? 'active' : ''}" href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/scorecardlist/false" title="Scorecard Page">Scorecard</a></li>
									</sec:authorize>
									<!--  Menu for the Reports -->
									<sec:authorize access="hasAuthority('Administrator') or hasAuthority('CMS User')">
									<li class="dropdown">
										<a class="dropdown-toggle ${menu_highlight == 'reports' ? 'active' : ''}" type="button" data-toggle="dropdown" href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/reports" title="Reports Page">Reports</a>
										<%-- <sec:authorize access="hasAuthority('Administrator') or hasAuthority('CMS User')">
										<span class="caret"></span>
										<ul class="dropdown-menu">										
									      	<li><a href="${pageContext.request.contextPath}/resources/documents/contractorinfo.docx" title="Create Users Page">Contractor Information</a></li>									     								
									    </ul>
									    </sec:authorize>	 --%>
									</li>
									</sec:authorize>
									<!-- Menu for the Reports -->
									<sec:authorize access="hasAuthority('Administrator') or hasAuthority('MAC User')  or hasAuthority('MAC Admin')">									
										<li class="dropdown"><a class="${menu_highlight == 'csr' ? 'active' : ''}" href="#" title="Forms Upload Page">Forms Upload</a>
										<span class="caret"></span>
										<ul class="dropdown-menu">		
											<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/csrlist" title="Customer Service Representative List Upload Page">CSR List Upload</a></li>								
									      	<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/qamenvironmentform" title="Upload Environemental Change Control Form">Envrionmental Change Control Form</a></li>	
									      	<%-- <li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/systemissueform" title="Upload System Issue Form">System Issue Form</a></li>									       --%>
									    </ul>
										</li>
									</sec:authorize>
									<sec:authorize access="hasAuthority('Administrator') or hasAuthority('Quality Manager') or hasAuthority('MAC Admin') or hasAuthority('MAC User')  or hasAuthority('CMS User')">
										<li><a class="${menu_highlight == 'rebuttal' ? 'active' : ''}" href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/rebuttallist/false" title="Rebuttal Page">Rebuttal</a></li>
									</sec:authorize>
								
									<sec:authorize access="hasAuthority('Administrator') or hasAuthority('MAC Admin') or hasAuthority('Quality Manager') or hasAuthority('Quality Monitor')">
									<li class="dropdown">
										<a class="dropdown-toggle ${menu_highlight == 'user_management' ? 'active' : ''}" type="button" data-toggle="dropdown" href="#" title="User Management Page">User Management</a>
										<span class="caret"></span>
										<ul class="dropdown-menu">
										<sec:authorize access="hasAuthority('Administrator') or hasAuthority('MAC Admin')">
									      	<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/createusers" title="Create Users Page">Create Users</a></li>
									     
									      <li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/listofusers" title="List of Users Page">List Users</a></li>
									    </sec:authorize>
									    <sec:authorize access="hasAuthority('Administrator') or hasAuthority('Quality Manager') or hasAuthority('Quality Monitor')">
									      	<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/macassignmentlist" title="MAC Assignment List">MAC Assignment</a></li>
									     </sec:authorize>
									      <sec:authorize access="hasAuthority('Administrator') ">
									      	<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/macinfolist" title="MAC Info List">MAC List</a></li>
									      	<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/jurisdictionlist" title="MAC Info List">Jurisdiction List</a></li>
									      	<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/programlist" title="MAC Info List">Program List</a></li>
									      	<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/locationlist" title="MAC Info List">Location List</a></li>
									      		<li><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/macmappinglist" title="MAC Mapping">MAC Mapping</a></li>
									     </sec:authorize>
									    </ul>
									</li>
									</sec:authorize>
									
									<li><a class="${menu_highlight == 'my_account' ? 'active' : ''}" href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/myaccount" title="My Account Page">My Account</a></li>	
									<li><a class="${menu_highlight == 'resources' ? 'active' : ''}" href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/resources" title="Resources">Resources</a></li>									
								<!-- <li>&nbsp; &nbsp;</li>	 -->	
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