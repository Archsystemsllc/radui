<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>PQRS Interactive Map Data Analysis</title>
<!-- <style>
.username.ng-valid {
	background-color: lightgreen;
}

.username.ng-dirty.ng-invalid-required {
	background-color: red;
}

.username.ng-dirty.ng-invalid-minlength {
	background-color: yellow;
}

.email.ng-valid {
	background-color: lightgreen;
}

.email.ng-dirty.ng-invalid-required {
	background-color: red;
}

.email.ng-dirty.ng-invalid-email {
	background-color: yellow;
}
</style> -->
<!-- <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"> -->
<%-- <link href="<c:url value='/resources/static/css/app.css' />"
	rel="stylesheet"></link> --%>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/common.css"
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
<%-- <link href="${pageContext.request.contextPath}/resources/css/table.css"
	rel="stylesheet"> --%>
</head>
<body ng-app="myApp" class="ng-cloak">
	<jsp:include page="header.jsp" />
	
	<div ng-controller="DemoController as dctrl">
	
	</div>
	<div class="container" style="min-height: 600px;">
		<h2 align="center">Welcome to PQRS Interactive Map Data Analysis
			Reporting Application</h2>
		<div class="row">
			<div class="col-sm-4 col-xs-12">ADDA ADDA ADDA ADDA ADDA ADDA
				ADDA ADDA ADDA ADDAADDA ADDA ADDA ADDA ADDAADDA ADDA ADDA ADDA
				ADDAADDA ADDA ADDA ADDA ADDAADDA ADDA ADDA ADDA ADDAADDA ADDA ADDA
				ADDA ADDA</div>
			<div class="col-sm-8 col-xs-12">

				<div class="generic-container"
					ng-controller="ReportingController as ctrl">
					<div class="panel panel-default">

						<div class="panel panel-default">
							<!-- Default panel contents -->
							<div class="panel-heading">
								<span class="lead">List of Users </span>
							</div>
							<div class="tablecontainer">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>ID.</th>
											<th>DataAnalysisDescription</th>
											<th>DataAnalysisName</th>
										</tr>
									</thead>
									<tbody>
										<tr ng-repeat="u in ctrl.dataAnalysis">
											<td><span ng-bind="u.id"></span></td>
											<td><span ng-bind="u.dataAnalysisDescription"></span></td>
											<td><span ng-bind="u.dataAnalysisName"></span></td>

										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>

					<script
						src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
					<script src="<c:url value='/resources/static/js/app.js' />"></script>
					<script
						src="<c:url value='/resources/static/js/service/reporting_service.js' />"></script>
					<script
						src="<c:url value='/resources/static/js/controller/reporting_controller.js' />"></script>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
