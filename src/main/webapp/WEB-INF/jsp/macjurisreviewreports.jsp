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
<title>QAM - ScoreCard</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/adda_ico.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />


<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/button.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui-timepicker-addon.css" />

<link rel="stylesheet" href="/resources/demos/style.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>
<link rel="stylesheet" href="https://jqueryvalidation.org/files/demo/css/screen.css"></link>

<!-- CSS for Bootstrap -->
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"></link>
<!-- JQuery -->
 <script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.33.3/es6-shim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/systemjs/0.19.20/system-polyfills.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery-ui-timepicker-addon.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.16.0/jquery.validate.min.js"></script>

<script type="text/javascript">
function goBack() {
    window.history.back();
}

</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>

	<table id="mid">
		<form:form method="POST" modelAttribute="reportsForm" class="form-signin" action="${pageContext.request.contextPath}/admin/goBackMacJurisReport" id="reportsForm" >
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">
					
						
						<div class="content">
							<c:if test="${AllScoreCardReport == true}">
								<div class="table-users" style="width: 100%">
									<div class="header">QAM MAC By Jurisdiction Report</div>
									
								 <form:hidden path="macId" />
								 <form:hidden path="jurisId" />
								 <form:hidden path="programId" />
								 <form:hidden path="loc" />
								 <form:hidden path="fromDate" />
								 <form:hidden path="toDate" />
								 <form:hidden path="mainReportSelect" />
								 <form:hidden path="scoreCardType" />
								 <form:hidden path="callResult" />
								 	
					             <div class="row" >
					                <div class="col-md-14 col-md-offset-1 form-container">
					                    <h2>${ReportTitle}</h2> 
					                  	<div class="row">
				                            <div class="col-sm-12 form-group">
				                                <button class="btn btn-primary" id="create"  onclick="history.go(-1);">Back</button>
				                            </div>
				                           
				                        </div>
				                         <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:column title="Scoreable Count" sortable="true" style="text-align:center;">
														<span><a href="${pageContext.request.contextPath}/admin/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/ScoreableOnly" >${row.scorableCount}</a></span>
													</display:column>
													<display:column title="Scoreable Pass Count" sortable="true" style="text-align:center;">
														<span><a href="${pageContext.request.contextPath}/admin/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/ScoreablePass" title="Edit">${row.scorablePass}</a></span>
													</display:column>
													<display:column property="scorablePassPercent" title="Scoreable Pass Percent" sortable="true" style="text-align:center;"/>
													<display:column title="Scoreable Fail Count" sortable="true" style="text-align:center;">
														<span><a href="${pageContext.request.contextPath}/admin/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/ScoreableFail" >${row.scorableFail}</a></span>
													</display:column>
													<display:column property="scorableFailPercent" title="Scoreable Fail Percent" sortable="true" style="text-align:center;"/>
													<display:column title="Non Scoreable Count" sortable="true" style="text-align:center;">
														<span><a href="${pageContext.request.contextPath}/admin/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/Non-Scoreable" >${row.nonScorableCount}</a></span>
													</display:column>
													<display:column property="nonScorablePercent" title="Non Scoreable Percent" sortable="true" style="text-align:center;"/>
													<display:setProperty name="export.excel.filename" value="TestReport.xls" />
													<display:setProperty name="export.xml.filename" value="TestReport.xml" />
													<display:setProperty name="export.csv.filename" value="TestReport.csv" />
														
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
					                </div>
					            </div>  <!-- Main Row Div -->
								</div> 
							</c:if>
							
							<c:if test="${ScoreableReport == true}">
								<div class="table-users" style="width: 100%">
									<div class="header">QAM MAC By Jurisdiction - Scoreable Pass and Fail Report</div>
					             <div class="row" >
					                <div class="col-md-14 col-md-offset-1 form-container">
					                    <h2>Report</h2> 
					                    <!-- <p> Please provide your feedback below: </p> -->
				                         <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:column title="Scoreable Count" sortable="true" style="text-align:center;">
														<span><a href="${pageContext.request.contextPath}/admin/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/ScoreableOnly" >${row.scorableCount}</a></span>
													</display:column>
													<display:column title="Scoreable Pass Count" sortable="true" style="text-align:center;">
														<span><a href="${pageContext.request.contextPath}/admin/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/ScoreablePass" title="Edit">${row.scorablePass}</a></span>
													</display:column>
													<display:column property="scorablePassPercent" title="Scoreable Pass Percent" sortable="true" style="text-align:center;"/>
													<display:column title="Scoreable Fail Count" sortable="true" style="text-align:center;">
														<span><a href="${pageContext.request.contextPath}/admin/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/ScoreableFail" >${row.scorableFail}</a></span>
													</display:column>
													<display:column property="scorableFailPercent" title="Scoreable Fail Percent" sortable="true" style="text-align:center;"/>
															
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
					                </div>
					            </div>  <!-- Main Row Div -->
								</div> 
							</c:if>
							
							<c:if test="${ScoreablePassReport == true}">
								<div class="table-users" style="width: 100%">
									<div class="header">QAM MAC By Jurisdiction - Scoreable Pass Report</div>
					             <div class="row" >
					                <div class="col-md-14 col-md-offset-1 form-container">
					                    <h2>Report</h2> 
					                    <!-- <p> Please provide your feedback below: </p> -->
				                         <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:column title="Scoreable Count" sortable="true" style="text-align:center;">
														<span><a href="${pageContext.request.contextPath}/admin/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/ScoreableOnly" >${row.scorableCount}</a></span>
													</display:column>
													<display:column title="Scoreable Pass Count" sortable="true" style="text-align:center;">
														<span><a href="${pageContext.request.contextPath}/admin/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/ScoreablePass" title="Edit">${row.scorablePass}</a></span>
													</display:column>
													<display:column property="scorablePassPercent" title="Scoreable Pass Percent" sortable="true" style="text-align:center;"/>
														
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
					                </div>
					            </div>  <!-- Main Row Div -->
								</div> 
							</c:if>
							
							<c:if test="${ScoreableFailReport == true}">
								<div class="table-users" style="width: 100%">
									<div class="header">QAM MAC By Jurisdiction - Scoreable Fail Report</div>
					             <div class="row" >
					                <div class="col-md-14 col-md-offset-1 form-container">
					                    <h2>Report</h2> 
					                    <!-- <p> Please provide your feedback below: </p> -->
				                         <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:column title="Scoreable Count" sortable="true" style="text-align:center;">
														<span><a href="${pageContext.request.contextPath}/admin/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/ScoreableOnly" >${row.scorableCount}</a></span>
													</display:column>
														<display:column title="Scoreable Fail Count" sortable="true" style="text-align:center;">
														<span><a href="${pageContext.request.contextPath}/admin/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/ScoreableFail" >${row.scorableFail}</a></span>
													</display:column>
													<display:column property="scorableFailPercent" title="Scoreable Fail Percent" sortable="true" style="text-align:center;"/>
															
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
					                </div>
					            </div>  <!-- Main Row Div -->
								</div> 
							</c:if>
							
							<c:if test="${NonScoreable == true}">
								<div class="table-users" style="width: 100%">
									<div class="header">QAM MAC By Jurisdiction - Non-Scoreable Report</div>
					             <div class="row" >
					                <div class="col-md-14 col-md-offset-1 form-container">
					                    <h2>Report</h2> 
					                    <!-- <p> Please provide your feedback below: </p> -->
				                         <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:column title="Non Scoreable Count" sortable="true" style="text-align:center;">
														<span><a href="${pageContext.request.contextPath}/admin/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/Non-Scoreable" >${row.nonScorableCount}</a></span>
													</display:column>
													<display:column property="nonScorablePercent" title="Non Scoreable Percent" sortable="true" style="text-align:center;"/>
														
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
					                </div>
					            </div>  <!-- Main Row Div -->
								</div> 
							</c:if>
							
							<c:if test="${ComplianceReport == true}">
								<div class="table-users" style="width: 100%">
									<div class="header">QAM MAC By Jurisdiction Report</div>
					             <div class="row" >
					                <div class="col-md-14 col-md-offset-1 form-container">
					                    <h2>Report</h2> 
					                    <!-- <p> Please provide your feedback below: </p> -->
				                         <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:column property="nonScorableCount" title="Does Not Count" sortable="true" style="text-align:center;"/>
													<display:column property="nonScorablePercent" title="Does Not Count Percent" sortable="true" style="text-align:center;"/>
														
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
					                </div>
					            </div>  <!-- Main Row Div -->
								</div> 
							</c:if>
							
							<c:if test="${RebuttalReport == true}">
								<div class="table-users" style="width: 100%">
									<div class="header">QAM MAC By Jurisdiction Report</div>
					             <div class="row" >
					                <div class="col-md-14 col-md-offset-1 form-container">
					                    <h2>Report</h2> 
					                    <!-- <p> Please provide your feedback below: </p> -->
				                         <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:column property="scorableCount" title="Scoreable Count" sortable="true" style="text-align:center;"/>
													<display:column property="scorablePass" title="Scoreable Pass" sortable="true" style="text-align:center;"/>
													<display:column property="scorablePassPercent" title="Scoreable Pass Percent" sortable="true" style="text-align:center;"/>
													<display:column property="scorableFail" title="Scoreable Fail" sortable="true" style="text-align:center;"/>
													<display:column property="scorableFailPercent" title="Scoreable Fail Percent" sortable="true" style="text-align:center;"/>
													<display:column property="nonScorableCount" title="Non Scoreable Count" sortable="true" style="text-align:center;"/>
													<display:column property="nonScorablePercent" title="Non Scoreable Percent" sortable="true" style="text-align:center;"/>
														
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
					                </div>
					            </div>  <!-- Main Row Div -->
								</div> 
							</c:if>
						</div> <!-- Content Div -->
					</div>
				</td>
			</tr>
		</form:form>
	</table>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>