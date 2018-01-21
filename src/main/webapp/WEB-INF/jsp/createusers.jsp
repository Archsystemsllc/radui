<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>QAM - CreateUsers</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css"
	rel="stylesheet" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/adda_ico.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/button.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui-timepicker-addon.css" />

<link rel="stylesheet" href="/resources/demos/style.css" />
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>
<link rel="stylesheet"
	href="https://jqueryvalidation.org/files/demo/css/screen.css"></link>

<!-- CSS for Bootstrap -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet"></link>
<!-- JQuery -->
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.33.3/es6-shim.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/systemjs/0.19.20/system-polyfills.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-ui-timepicker-addon.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.16.0/jquery.validate.min.js"></script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div>
		<table id="mid">
			<%-- 		<form:form method="POST" modelAttribute="reportsForm" class="form-signin" action="${pageContext.request.contextPath}/admin/getMacJurisReport" id="reportsForm">
 --%>
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">

							<div class="table-users" style="width: 80%">
								<div class="header">Create User</div>
								<div class="row">


									<table
										style="border-collapse: separate; border-spacing: 2px; valign: middle"
										id='table1'>
										<tr>
										</tr>
									</table>

									<div class="row">
										<div class="col-sm-8 col-md-offset-1 form-group">
											<label for="name"> First Name:</label> <input type="text"
												class="form-control" />
										</div>
										<div class="col-sm-8 col-md-offset-1 form-group">
											<label for="email"> Last Name:</label> <input type="text"
												class="form-control" />
										</div>
										<div class="col-md-8 col-md-offset-1 form-container">
											<label for="name"> Role:</label> <select path="roleId"
													class="form-control required" id="roleId" required="true">
													<option value="" label="--- Select MAC---" />
													<%-- <options items="${macIdMap}" /> --%>
												</select>
										</div>
										<div class="col-md-8 col-md-offset-1 form-container">
											<label for="organization"> Organization:</label> <select
													path="organizationId" class="form-control required" id="organizationId"
													required="true">
													<option value="" label="--- Select MAC---" />
												</select>
										</div>
										<div class="col-md-8 col-md-offset-1 form-container">
										<label for="name"> MAC:</label> <select path="macId"
													class="form-control required" id="macId" required="true">
													<option value="" label="--- Select MAC---" />
												</select>
										</div>
										<div class="col-md-8 col-md-offset-1 form-container">
																						<label for="email"> Jurisdiction:</label> <select
													path="jurisId" class="form-control required" id="jurisId"
													required="true">
													<option value="" label="--- Select Jurisdiction---" />
												</select>
										</div>
										<div class="col-md-8 col-md-offset-1 form-container" style ="padding-bottom: 40px;">
										<label for="pccLocation"> PCC Location:</label> <select
													path="pccLocationId" class="form-control required" id="pccLocationId"
													required="true">
													<option value="" label="--- Select PCC Location---" />
												</select>
										</div>



										</div>
									</div>
								</div>
							</div>
						</div>
				</td>
			</tr>

			<%-- 		</form:form> --%>
		</table>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
