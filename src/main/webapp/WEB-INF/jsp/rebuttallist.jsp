<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>QAM - Rebuttal List</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/adda_ico.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/button.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/radjavascript.js" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
<link rel="stylesheet" href="/resources/demos/style.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.33.3/es6-shim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/systemjs/0.19.20/system-polyfills.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- CSS for Bootstrap -->
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"></link>
<!-- JQuery -->
 <script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
		<table id="mid">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">

							<div class="table-users" style="width: 80%">
								<div class="header">Rebuttal List</div>							
      
                                 <form action="scorecardsave" method="post" enctype="multipart/form-data">
                                 <!--  Section 1 -->
                                 <table style="border-collapse: separate; border-spacing: 2px; valign: middle"
												id='table1'>
									<tr>
										<td>
										<!-- <span><button class="btn btn-primary" id="filter" type="submit">Filter</button></span> 
										<span><button class="btn btn-primary" id="reset" type="reset">Reset</button></span> -->
										<sec:authorize access="hasAuthority('Administrator') or hasAuthority('MAC Admin') or hasAuthority('MAC User')">
										<span><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/new-rebuttal" title="New">
											<button class="btn btn-primary" id="addRebuttal" type="button">Add Rebuttal</button></a></span> 
										</sec:authorize>
									</td>
									
									</tr>
								</table>
								<table style="border-collapse: separate; border-spacing: 2px;" id='table1'>
									<tr><td colspan="3" width="95%" >
					<div class="form_grid_12">
					<display:table class="display hover stripe cell-border " id="rebuttalRow" name="${sessionScope.SESSION_SCOPE_REBUTTAL_MAP.values()}" 
							  style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list" requestURI="${pageContext.request.contextPath}/${SS_USER_FOLDER}/rebuttallist">
							<display:column property="macName" title="MAC" sortable="true" style="text-align:center;"/>
							<display:column property="macCallReferenceNumber" title="MAC Call Reference ID" sortable="true" style="text-align:center;"/>
							<display:column property="qamFullName" title="QM Name/ID" sortable="true" style="text-align:center;"/>
							<display:column property="macPCCNameTempValue" title="PCC/Location" sortable="true" style="text-align:center;"/>
							<display:column property="datePosted" title="Date Posted" sortable="true" style="text-align:center;"/>
							<display:column property="callDate" title="Reporting Month" sortable="true" style="text-align:center;"/>
							<display:column property="rebuttalStatus" title="Status" sortable="true" style="text-align:center;"/>
							<display:column property="rebuttalResult" title="Result" sortable="true" style="text-align:center;"/>
							
							<<display:column title="Actions" style="text-align:center;">
							<span><a class="action-icons c-pending"	href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/view-rebuttal/${rebuttalRow.id}" title="View Rebuttal List">View</a></span>
							<sec:authorize access="hasAuthority('Administrator') or hasAuthority('Quality Manager') or hasAuthority('MAC Admin') or hasAuthority('MAC User')">
								<span><a class="action-icons c-edit" href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/edit-rebuttal/${rebuttalRow.id}" title="Edit Rebuttal List">Edit</a></span>
							</sec:authorize>
											
							</display:column>
							<display:setProperty name="export.excel.filename"	value="RebuttalReport.xls" />
							<display:setProperty name="export.csv.filename"	value="RebuttalReport.csv" />
							<display:setProperty name="export.pdf.filename"	value="RebuttalReport.pdf" />
							<display:setProperty name="export.pdf" value="true" />
							
						</display:table>
						<c:if test="${fn:length(sessionScope.SESSION_SCOPE_REBUTTAL_MAP.values()) eq 0}">
							
						</c:if>	
						</div>
						</td>
						</tr>
						</table>
						</form>
						</div>
						</div>
						</div></td></tr></table>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
