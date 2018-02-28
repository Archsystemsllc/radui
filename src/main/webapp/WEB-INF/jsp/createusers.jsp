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
	
<script type="text/javascript">

$(function(){
	$("select#organizationLookupId").change(function(){
		alert($(this).val());
		 var selectedOrgnization = $(this).val();
		 if(selectedOrgnization==1) {	
			 $('#macId,#jurId,#pccId').attr("required",false);
			 $("#macIdBlock,#jurIdBlock,#pccIdBlock").hide();	
			 
			 	
		 } else if(selectedOrgnization==2) {	
			 $('#macId,#jurId,#pccId').attr("required",false);
			 $("#macIdBlock,#jurIdBlock,#pccIdBlock").hide();
			 	
		 } else if(selectedOrgnization==3) {	
			 $('#macId,#jurId,#pccId').attr("required",true);
			 $("#macIdBlock,#jurIdBlock,#pccIdBlock").show();
			 	
		 }
	    
	});

});
</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div>
		<table id="mid">
			<%-- 		<form:form method="POST" modelAttribute="reportsForm" class="form-signin" action="${pageContext.request.contextPath}/admin/getMacJurisReport" id="reportsForm">
 --%>
 <form:form method="POST" modelAttribute="userForm" action="${pageContext.request.contextPath}/admin/createUser" class="form-signin">
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

									<div class="row"  style="margin-top: 10px">
										<div class="col-sm-6 col-md-offset-1 form-group">
											<label for="userName"> User Name:</label> 
											 <spring:bind path="userName">
											 <div class="form-group ${status.error ? 'has-error' : ''}">
								                <form:input type="text" path="userName" class="form-control required" placeholder=""
								                            autofocus="true"></form:input>
								                <form:errors path="userName"></form:errors>
								            </div>
								            </spring:bind>
										</div>
										
										<div class="col-sm-6 col-md-offset-1 form-group">
											<label for="password"> Password:</label> 
											 <spring:bind path="password">
											 <div class="form-group ${status.error ? 'has-error' : ''}">
								                <form:input type="password" path="password" class="form-control required" placeholder=""></form:input>
								                <form:errors path="password"></form:errors>
								            </div>
								            </spring:bind>
										</div>
										
										<div class="col-sm-6 col-md-offset-1 form-group">
											<label for="passwordConfirm"> Confirm your password:</label> 
											<spring:bind path="passwordConfirm">
											<div class="form-control ${status.error ? 'has-error' : ''}">
								                <form:input type="password" path="passwordConfirm" class="form-control"
								                            placeholder=""></form:input>
								                <form:errors path="passwordConfirm"></form:errors>
								            </div>
								            </spring:bind>
										</div>
										<div class="col-sm-6 col-md-offset-1 form-group">
											<label for="firstName"> First Name:</label> 
											<spring:bind path="firstName">
											<div class="form-control ${status.error ? 'has-error' : ''}">
								                <form:input type="text" path="firstName" class="form-control" placeholder=""
								                            autofocus="true"></form:input>
								                <form:errors path="firstName"></form:errors>
								            </div>
								            </spring:bind>
										</div>
										<div class="col-sm-6 col-md-offset-1 form-group">
											<label for="middleName"> Middle Name:</label> 
											 <div class="form-control ${status.error ? 'has-error' : ''}">
								                <form:input type="text" path="middleName" class="form-control" placeholder=""
								                            autofocus="true"></form:input>
								                <form:errors path="middleName"></form:errors>
								            </div>
										</div>
										
										<div class="col-sm-6 col-md-offset-1 form-group">
											<label for="lastName"> Last Name:</label> 
											 <div class="form-control required ${status.error ? 'has-error' : ''}">
								                <form:input type="text" path="lastName" class="form-control" placeholder=""
								                            autofocus="true"></form:input>
								                <form:errors path="lastName"></form:errors>
								            </div>
										</div>
										
										<div class="col-sm-6 col-md-offset-1 form-group">
											<label for="emailId"> Email Id:</label> 
											 <div class="form-control required ${status.error ? 'has-error' : ''}">
								                <form:input type="text" path="emailId" class="form-control" placeholder=""
								                            autofocus="true"></form:input>
								                <form:errors path="emailId"></form:errors>
								            </div>
										</div>
										<div class="col-md-6 col-md-offset-1 form-container">
											<label for="name"> Role:</label>
											<form:select path="role.id"
													class="form-control required" id="roleId" required="true">
													<option value="" label="--- Select Role---" />
													<form:options items="${roleIds}" />
												</form:select>
											 
										</div>
										<div class="col-md-6 col-md-offset-1 form-container">
											<label for="organization"> Organization:</label> <form:select path="organizationLookup.id"
													class="form-control required" id="organizationLookupId" required="true">
													<option value="" label="--- Select Role---" />
													<form:options items="${orgIds}" />
												</form:select>
										</div>
										<div class="col-md-6 col-md-offset-1 form-container" id="macIdBlock">
										<label for="name"> MAC:</label>
										
										<form:select path="macId"
													class="form-control required" id="macId" required="true">
													<option value="" label="--- Select MAC---" />
													<form:options items="${macIds}" />
												</form:select>
										</div>
										<div class="col-md-6 col-md-offset-1 form-container" id="jurIdBlock">
													<label for="email"> Jurisdiction:</label>
													<form:select path="jurId"
													class="form-control required" id="jurId" required="true">
													<option value="" label="--- Select Jurisidiction---" />
													<form:options items="${jurIds}" />
												</form:select>
										</div>
										<div class="col-md-6 col-md-offset-1 form-container" style ="padding-bottom: 40px;" id="pccIdBlock">
										<label for="pccLocation"> PCC Location:</label>
										<form:select path="pccId"
													class="form-control required" id="pccId" required="true">
													<option value="" label="--- Select PCC Location---" />
													<form:options items="${pccIds}" />
													
											
												</form:select>
										</div>

										<form:input type="hidden" path="createdBy" value="${pageContext.request.userPrincipal.name}" style ="padding-top: 40px;"/>
										
										<div class="col-md-8 col-md-offset-1 form-container">
											<button class=" btn btn-primary">Submit</button>
										</div>
									
										</div>
									</div>
								</div>
							</div>
						</div>
				</td>
			</tr>

					</form:form>
		</table>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
