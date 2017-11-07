<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>ADDA - Download</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css"
	rel="stylesheet" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/adda_ico.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/button.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.33.3/es6-shim.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/systemjs/0.19.20/system-polyfills.js"></script>
<script
	src="https://code.angularjs.org/2.0.0-beta.6/angular2-polyfills.js"></script>
<script src="https://code.angularjs.org/tools/system.js"></script>
<script src="https://code.angularjs.org/tools/typescript.js"></script>
<script src="https://code.angularjs.org/2.0.0-beta.6/Rx.js"></script>
<script src="https://code.angularjs.org/2.0.0-beta.6/angular2.dev.js"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<table id="mid">
		<tr>
			<td
				style="background-color: #327a89; width: 30%; vertical-align: top; padding: 0px 25px">
				<div style="color: #fff">
					<!-- <ul style="border-bottom: solid #fff 2px" type="square">
						<li><h2
								style="color: #fff; font-family: 'Rubik', sans-serif;">Description</h2></li>
					</ul> -->
					<p style="text-align: justify; font-family: 'Rubik', sans-serif;">
						<br></br> <br></br>From the Base Year to Option Year 3 Rural Area
						Percentage line plot, we would like to see the change trend of the
						rural area percentage of all combined EPs and GPROs and the
						difference among reporting options (Claims, Registry, EHR, QCDR
						and GPROWI)
					</p>
				</div>
			</td>
			<td style="vertical-align: top">

				<div id="updates" class="boxed">

					<div class="content">
						<div class="table-users" style="width: 80%">
							<div class="header">Create your account</div>
							<!-- 	<div class="container">  -->
								
								    <form:form method="POST" modelAttribute="userForm" class="form-signin">
								        <spring:bind path="username">
								            <div class="form-group ${status.error ? 'has-error' : ''}">
								                <form:input type="text" path="username" class="form-control" placeholder="Username"
								                            autofocus="true"></form:input>
								                <form:errors path="username"></form:errors>
								            </div>
								        </spring:bind>
								
								        <spring:bind path="password">
								            <div class="form-group ${status.error ? 'has-error' : ''}">
								                <form:input type="password" path="password" class="form-control" placeholder="Password"></form:input>
								                <form:errors path="password"></form:errors>
								            </div>
								        </spring:bind>
								
								        <spring:bind path="passwordConfirm">
								            <div class="form-group ${status.error ? 'has-error' : ''}">
								                <form:input type="password" path="passwordConfirm" class="form-control"
								                            placeholder="Confirm your password"></form:input>
								                <form:errors path="passwordConfirm"></form:errors>
								            </div>
								        </spring:bind>
								        
								        <spring:bind path="name">
								            <div class="form-group ${status.error ? 'has-error' : ''}">
								                <form:input type="text" path="name" class="form-control" placeholder="name"
								                            autofocus="true"></form:input>
								                <form:errors path="name"></form:errors>
								            </div>
								        </spring:bind>
								        
								        <spring:bind path="email">
								            <div class="form-group ${status.error ? 'has-error' : ''}">
								                <form:input type="text" path="email" class="form-control" placeholder="email"
								                            autofocus="true"></form:input>
								                <form:errors path="email"></form:errors>
								            </div>
								        </spring:bind>
								        <form:select path="rolesList" id="role">
												<c:forEach var="role" items="${allRoles}">
													<option value="${role.id}">${role.name}</option>
												</c:forEach>
										</form:select> 
										
										<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
								    </form:form>
								
								<!-- </div> -->
						</div>
					</div>
				</div>

			</td>
		</tr>
	</table>
	<jsp:include page="footer.jsp"></jsp:include>
	<script type="text/javascript">
	var h;
	h=screen.height-357;
	document.getElementById('mid').style.minHeight=h+'px';
	$(document).ready(function () {
				 $('.nav > li').eq(3).addClass('active');			 
	});	
	</script>
</body>
</html>
