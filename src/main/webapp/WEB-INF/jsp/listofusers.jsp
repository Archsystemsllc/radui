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
<title>QAM - listofUsers</title>
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
	href="//cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css"></link>

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
	<script
	src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>

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

							<div class="panel panel-primary table-users" style="width: 75%; box-shadow: 3px 3px 0 rgba(0, 0, 0, 0.1); border: 1px solid #327a81;">
								<div class="panel-heading" style="background-color: #327a81;">List of Users</div>
								<div class="panel-body" style="background-color: white;">
<form:form method="GET" modelAttribute="userFilterForm" action="${pageContext.request.contextPath}/admin/userFilter">

									<table
										style="border-collapse: separate; border-spacing: 2px; valign: middle"
										id='table1'>
										<tr>
										</tr>
									</table>

									<div class="row">
										<div class="col-sm-3 form-group">
											<label for="lastName"> Last Name:</label> 
											<form:input type="text" path="lastName" class="form-control" />
										</div>
										<div class="col-md-3 col-md-offset-1 form-container">
											<label for="roleId"> Role:</label> 
											<form:select path="roleId"
													class="form-control" id="roleId" required="true">
													<option value="" label="--- Select Role---" />
													<form:options items="${roleIds}" />
												</form:select>
										</div>
										<div class="col-md-3 col-md-offset-1 form-container">
											<label for="organizationId"> Organization:</label>
											
											 <form:select path="orgId"
													 class="form-control" id="organizationId"
													required="true">
													<option value="" label="--- Select Org---" />
													<form:options items="${orgIds}" />
												</form:select>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-3 form-group">
											<button class=" btn btn-primary">Submit</button>
										</div>
									</div>
									</form:form>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-2">
								</div>
								<div class="col-md-8">
									<table id="usersTable">
										<thead>
											<tr>
												<td>
													FirstName
												</td>
												<td>
													MiddleName
												</td>
												<td>
													LastName
												</td>
												<td>
													Organization
												</td>
												<td>
													Role
												</td>
												<td>
													User Access
												</td>
												<td>
													Action
												</td>
											</tr>
										</thead>
										 <tbody>
					                        <c:forEach var="user" items="${users}">
					                            <tr>
					                               <%--  <td><a class="${linkcolor }">${user.id}</a></td> --%>
					                                <td><a class="${linkcolor }">${user.firstName}</a></td>
					                                 <td><a class="${linkcolor }">${user.middleName}</a></td>
					                                  <td><a class="${linkcolor }">${user.lastName}</a></td>
					                                <td><a class="${linkcolor }">${user.organizationLookup.organizationName}</a></td>
					                                 <td><a class="${linkcolor }">${user.role.roleName}</a></td>
					                                <td><a class="${linkcolor }">${user.status == 1? "Active":"Inactive"}</a></td>
					                                <td>
					                                    <span><a class="action-icons c-edit" href="${pageContext.request.contextPath}/admin/edit-user/${user.id}" title="Edit">Edit</a></span>
					                                    <span><a class="action-icons c-delete" href="${pageContext.request.contextPath}/admin/delete-user/${user.id}" title="Delete" onclick="return confirm('Are you sure you want to delete this item?');">Delete</a></span>
					                                </td>
					                            </tr>
					                        </c:forEach>
					                        </tbody>
									</table>
								</div>
								<div class="col-md-2">
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
<head>
	<script>
	$(document).ready(function() {
		$("#usersTable").DataTable();
	});
	</script>
</head>
</html>
