<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>QAM - ScoreCard</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css"
	rel="stylesheet" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/adda_ico.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/button.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/js/radjavascript.js" />
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
<link rel="stylesheet" href="/resources/demos/style.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.33.3/es6-shim.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/systemjs/0.19.20/system-polyfills.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- CSS for Bootstrap -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet"></link>
<!-- JQuery -->
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">
$(document).ready(function () {

 	$('#filterFromDateString').datepicker({
		maxDate: 0
	}); 

 	$('#filterToDateString').datepicker({
		maxDate: 0
	}); 

 	
/* 
 	 $('#back').click(function(e) {	
 		 window.history.back();
     }); 
 	 */
	
});
</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<table id="mid">
	<form:form method="POST" modelAttribute="scorecard"
								class="form-signin"
								action="${pageContext.request.contextPath}/admin/scorecardfilter"
								id="scorecardfilterForm">
		<tr>
			<td style="vertical-align: top">

				<div id="updates" class="boxed">

					<div class="content">

						<div class="table-users" style="width: 80%">
							<div class="header">List of Scorecards</div>
							
								<%-- <c:if test="${ScoreCardFilter == true}">
										
										
								<div class="row " style="margin-top: 10px">
									<div class="col-lg-12 col-lg-offset-1 form-container">
										<div class="row">
										<h2>Score Card Search Filters</h2>
											<div class="col-lg-4 form-group">
												<label for="name">Status:</label>
												<form:select class="form-control" id="callResult"
													path="callResult" title="Select Call Result">
													<form:option value="ALL" label="ALL" />
													<form:option value="Pass" />
													<form:option value="Fail" />
												</form:select>

											</div>
											<div class="col-lg-4 form-group">
												<label for="name"> QM Name/QM ID:</label>
												<form:input type="text" class="form-control"
													id="qamFullName" name="qamFullName" path="qamFullName"/>

											</div>
										</div>
										<div class="row">
											<div class="col-lg-4 form-group">
												<label for="name"> Jurisdiction:</label>
												<form:select path="jurId" class="form-control required"
													id="jurId" required="true">
													<form:option value="ALL" label="ALL" />
													<form:options items="${jurisMapEdit}" />
												</form:select>
											</div>
											<div class="col-lg-4 form-group">
												<label for="name">ScoreCard Type:</label>
												<form:select class="form-control" id="scorecardType"
													path="scorecardType" title="Select Score Card Type">
													<form:option value="ALL" label="ALL" />
													<form:option value="Scoreable" />
													<form:option value="Non-Scoreable" />
													<form:option value="Does Not Count" />
												</form:select>

											</div>
										</div>
										<div class="row">
											<div class="col-lg-4 form-group">
												<label for="name"> From Date:</label>

												<form:input type="text" class="form-control"
													path="filterFromDateString" />
											</div>
											<div class="col-lg-4 form-group">
												<label for="email"> To Date:</label>
												<form:input type="text" class="form-control"
													path="filterToDateString" />
											</div>
										</div>

									</div>
								</div>
								<div class="row ">
								<div class="col-lg-12 col-lg-offset-1 form-container">
									<table
										style="border-collapse: separate; border-spacing: 2px; valign: middle"
										id='table1'>
										<tr>
											<td><span><button class="btn btn-primary"
														id="filter" type="submit">Filter</button></span> <span><button
														class="btn btn-primary" id="reset" type="reset">Reset</button></span></td>
										</tr>
									</table>
									</div>
								</div>
								</c:if> --%>
								<c:if test="${ReportFlag == true}" style="margin-top: 10px">
								<div class="row"  style="margin-top: 10px">
								<div class="col-lg-12 col-lg-offset-1 form-container">
									<span><a href="${pageContext.request.contextPath}/admin/getMacJurisReportFromSession"><button class="btn btn-primary" id="back" type="button" >Back</button></a></span>
										
									</div>
								</div>
								</c:if>
								<div class="row " style="margin-top: 10px">
									<!--  Section 1 -->
									<div class="col-lg-12 col-lg-offset-1 form-container">
									
									<div class="row">
			                            <div class="col-lg-10 form-group">

										<display:table class="display hover stripe cell-border "
											id="row"
											name="${sessionScope.SESSION_SCOPE_SCORECARDS_MAP.values()}"
											keepStatus="true" clearStatus="${storepage == 'clear'}"
											style="width:95%;font-size:95%;" export="true" pagesize="15"
											sort="list"
											requestURI="${pageContext.request.contextPath}/admin/scorecardlist">
											<display:column property="macCallReferenceNumber"
												title="MAC Call Reference ID" sortable="true"
												style="text-align:center;" />
											<display:column property="qamFullName" title="QM Name/ID"
												sortable="true" style="text-align:center;" />
											<display:column property="qamStartdateTime"
												title="QM Start Date/Time" sortable="true"
												style="text-align:center;" />
											<display:column property="scorecardType"
												title="Scorecard Type" sortable="true"
												style="text-align:center;" />
											<display:column property="callResult" title="Status"
												sortable="true" style="text-align:center;" />
											<display:column property="jurisdictionName"
												title="Jurisdiction" sortable="true"
												style="text-align:center;" />
											<display:column title="Actions" style="text-align:center;"
												media="html">
												<span><a class="action-icons c-edit"
													href="${pageContext.request.contextPath}/admin/edit-scorecard/${row.id}"
													title="Edit">Edit</a></span>
												<span><a class="action-icons c-approve"
													href="${pageContext.request.contextPath}/admin/new-scorecard"
													title="Create">Create</a></span>
											</display:column>
											<display:setProperty name="export.excel.filename"
												value="ScorecardReport.xls" />
											<display:setProperty name="export.csv.filename"
												value="ScorecardReport.csv" />
											<display:setProperty name="export.pdf.filename"
												value="ScorecardReport.pdf" />
											<display:setProperty name="export.pdf" value="true" />

										</display:table>
										<c:if
											test="${fn:length(sessionScope.SESSION_SCOPE_SCORECARDS_MAP.values()) eq 0}">
											<span><a class="action-icons c-approve"
												href="${pageContext.request.contextPath}/admin/new-scorecard"
												title="Create">Create</a></span>
										</c:if>
										</div>
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


	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
