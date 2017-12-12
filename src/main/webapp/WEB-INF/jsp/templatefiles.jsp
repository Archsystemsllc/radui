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
							<div class="header">Download excel screen</div>
							<table>
								<colgroup>
									<col width="30%"></col>
									<col width="30%"></col>
									<col width="40%"></col>
								</colgroup>
								<tbody>
									<tr>
										<th>Title</th>
										<th>Description</th>
										<th>Action</th>
									</tr>
									<c:forEach var="template" items="${templateFiles}">
										<tr>
											<td>
												<p>${template.uploadedTitle}</p>
											</td>
											<td style="text-align: center">
												<p>${template.uploadedDescription}</p>
											</td>
											<td style="text-align: right">
												<!-- <input type=button href="/admin/download-document/${document.id}"  value='Download'> -->
												<a
												href="${pageContext.request.contextPath}/admin/download-template/${template.id}"><button
														class="button arrow">Download</button></a> <!--<input type=button onClick="http://localhost:8484/admin/files/delete-document/${document.id}"  value='Delete'>-->
												<a
												href="${pageContext.request.contextPath}/admin/delete-template/${template.id}" onclick="return confirm('Are you sure you want to delete this item?');"><button
														class="button phone">Delete</button></a>

											</td>
										</tr>
									</c:forEach>
									<!-- <tr>
						<td >
							<p>Hypothesis2: <a href="http://www.archsystemsinc.com/">Hypothesis2</a></p>
						</td>
						<td>						     
						</td>
						<td>
						    <input type=button onClick="location.href='file:///C:/Suganthi/Test/ornamental1/index1.html'"  value='Download'>		
						</td>
					</tr> -->

									<tr>
										<td></td>
										<td></td>
										<td style="text-align: right"><a
											href="${pageContext.request.contextPath}/admin/download-templates"><button
													class="button arrow">Download All</button></a> <a
											href="${pageContext.request.contextPath}/admin/delete-templates" onclick="return confirm('Are you sure you want to delete this item?');"><button
													class="button phone">Delete All</button></a>
											<button class="button search1" onclick="addFile()">Add</button>
											<!-- <input type=button onClick="location.href='file:///C:/Suganthi/Test/ornamental1/index1.html'"  value='Download All'> -->
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<c:if test="${not empty fileuploaderror}">
							<div class="successblock">
								<spring:message code="${fileuploaderror}"></spring:message>
							</div>
						</c:if>
						<div class="container" id="addTemplate"
							style="margin: auto; width: 30%; display: none">
							<form:form
								action="${pageContext.request.contextPath}/admin/new-template/"
								modelAttribute="templateFile" enctype="multipart/form-data"
								method="post">
								<p>
									Title:<br />
									<form:input type="text" path="uploadedTitle" size="40" />
								</p>
								<p>
									Description:<br />
									<form:input type="text" path="uploadedDescription" size="40" />
								</p>
								<p>
									Please specify a file, or a set of files:<br />
									<form:input type="file" path="uploadedFile" />
								</p>
								<div style="margin: auto; width: 30%">
									<button style="margin: auto; width: 100%"
										class="btn btn-primary" type="submit" value="Send">Submit</button>
								</div>
							</form:form>
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
				 $('.nav > li').eq(2).addClass('active');			 
	});	
	</script>
	<script>
	function addFile(){
		var x = document.getElementById('addTemplate');
	    if (x.style.display === 'none') {
	        x.style.display = 'block';
	    } else {
	        x.style.display = 'none';
	    }
	
	}
	</script>
</body>
</html>
