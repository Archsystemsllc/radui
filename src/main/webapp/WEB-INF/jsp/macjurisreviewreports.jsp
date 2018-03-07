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
<title>QAM - Scorecard</title>
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


</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>

	<table id="mid">
		<form:form method="POST" modelAttribute="reportsForm" class="form-signin" action="${pageContext.request.contextPath}/${SS_USER_FOLDER}/goBackMacJurisReport" id="reportsForm" >
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">
					
						
						<div class="content">
						
						 <form:hidden path="macId" />
								 <form:hidden path="jurisId" />
								 <form:hidden path="programId" />
								 <form:hidden path="loc" />
								 <form:hidden path="fromDate" />
								 <form:hidden path="toDate" />
								 <form:hidden path="mainReportSelect" />
								 <form:hidden path="scoreCardType" />
								 <form:hidden path="callResult" />
								 <form:hidden path="fromDateString" />
								 <form:hidden path="toDateString" />
								  <form:hidden path="complianceReportType" />
								  <form:hidden path="callCategoryType" />
								  <form:hidden path="rebuttalStatus" />
								 
								 			
								<div class="table-users" style="width: 80%">
									<div class="header">Report Results Screen</div>
									<br/>
									<div class="col-lg-12 col-lg-offset-1 form-container">
				                    <%-- <h2>"${reportName}"</h2>  --%>
				                    <!-- <p> Please provide your feedback below: </p> -->
				                    
				                    <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <button class="btn btn-primary" id="create"  type="submit">Back</button>
				                            </div>
				                           
				                        </div>
				                   
				                    <div class="row">
			                            <div class="col-lg-4 form-group">
			                                <label for="name"> MAC:</label>
			                                <label for="name"> ${reportsForm.macName}</label>
			                            </div>
			                            <div class="col-lg-4 form-group">
			                                <label for="name"> Jurisdiction:</label>
			                                <label for="name"> ${reportsForm.jurisdictionName}</label>
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-lg-4 form-group">
			                                <label for="name"> Report From Date:</label>
			                                 <label for="name"> ${reportsForm.fromDateString}</label>
			                            </div>
			                            <div class="col-lg-4 form-group">
			                                <label for="name"> Report To Date:</label>
			                                 <label for="name"> ${reportsForm.toDateString}</label>
			                            </div>
			                        </div>
				                    
				                </div>
									
								
								 	
					             <div class="row" >
					                <div class="col-md-14 col-md-offset-1 form-container">
					                    <h2>${ReportTitle}</h2> 
					                  	
				                        <c:if test="${AllScoreCardReport_All == true}">
				                         <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list" >
													
													<display:column title="MAC" sortable="true" style="text-align:center;" media="html">
														<span><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/AlL" >${row.macName}</a></span>
													</display:column>
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;" media="pdf csv excel"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													
													<display:column property="scorableCount" title="Scoreable Count" sortable="true" style="text-align:center;"/>
													<display:column property="scorablePass" title="Scoreable Pass Count" sortable="true" style="text-align:center;"/>													
												
													<display:column title="Scoreable Pass Percent" sortable="true" style="text-align:center;" >
														${row.scorablePassPercent}%
													</display:column>												
													<display:column property="scorableFail" title="Scoreable Fail Count" sortable="true" style="text-align:center;"/>													
													
													<display:column title="Scoreable Fail Percent" sortable="true" style="text-align:center;" >
														${row.scorableFailPercent}%
													</display:column>	
													<display:column property="nonScorableCount" title="Non Scoreable Count" sortable="true" style="text-align:center;"/>													
													
													<display:column title="Non Scoreable Percent" sortable="true" style="text-align:center;" >
														${row.nonScorablePercent}%
													</display:column>
													<display:column property="doesNotCount_Number" title="Does Not Count" sortable="true" style="text-align:center;"/>
													<display:column title="Does Not Count Percent" sortable="true" style="text-align:center;" >
														${row.doesNotCount_Percent}%
													</display:column>	
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:setProperty name="export.excel.filename" value="ScorecardReport.xls" />
													<display:setProperty name="export.pdf.filename" value="ScorecardReport.pdf" />
													<display:setProperty name="export.csv.filename" value="ScorecardReport.csv" />
													<display:setProperty name="export.pdf" value="true" />
														
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
				                        </c:if>
				                        <c:if test="${AllScoreCardReport_Pass == true}">
				                         <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list" >
													
													<display:column title="MAC" sortable="true" style="text-align:center;" media="html">
														<span><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/AlL" >${row.macName}</a></span>
													</display:column>
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;" media="pdf csv excel"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													
													<display:column property="scorableCount" title="Scoreable Count" sortable="true" style="text-align:center;"/>
													<display:column property="scorablePass" title="Scoreable Pass Count" sortable="true" style="text-align:center;"/>													
												
													<display:column title="Scoreable Pass Percent" sortable="true" style="text-align:center;" >
														${row.scorablePassPercent}%
													</display:column>												
													
													<display:column property="nonScorableCount" title="Non Scoreable Count" sortable="true" style="text-align:center;"/>													
													
													<display:column title="Non Scoreable Percent" sortable="true" style="text-align:center;" >
														${row.nonScorablePercent}%
													</display:column>
													<display:column property="doesNotCount_Number" title="Does Not Count" sortable="true" style="text-align:center;"/>
													<display:column title="Does Not Count Percent" sortable="true" style="text-align:center;" >
														${row.doesNotCount_Percent}%
													</display:column>	
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:setProperty name="export.excel.filename" value="ScorecardReport.xls" />
													<display:setProperty name="export.pdf.filename" value="ScorecardReport.pdf" />
													<display:setProperty name="export.csv.filename" value="ScorecardReport.csv" />
													<display:setProperty name="export.pdf" value="true" />
														
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
				                        </c:if>
				                        <c:if test="${AllScoreCardReport_Fail == true}">
				                         <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list" >
													
													<display:column title="MAC" sortable="true" style="text-align:center;" media="html">
														<span><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/AlL" >${row.macName}</a></span>
													</display:column>
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;" media="pdf csv excel"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													
													<display:column property="scorableCount" title="Scoreable Count" sortable="true" style="text-align:center;"/>
																			
													<display:column property="scorableFail" title="Scoreable Fail Count" sortable="true" style="text-align:center;"/>													
													
													<display:column title="Scoreable Fail Percent" sortable="true" style="text-align:center;" >
														${row.scorableFailPercent}%
													</display:column>	
													<display:column property="nonScorableCount" title="Non Scoreable Count" sortable="true" style="text-align:center;"/>													
													
													<display:column title="Non Scoreable Percent" sortable="true" style="text-align:center;" >
														${row.nonScorablePercent}%
													</display:column>
													<display:column property="doesNotCount_Number" title="Does Not Count" sortable="true" style="text-align:center;"/>
													<display:column title="Does Not Count Percent" sortable="true" style="text-align:center;" >
														${row.doesNotCount_Percent}%
													</display:column>	
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:setProperty name="export.excel.filename" value="ScorecardReport.xls" />
													<display:setProperty name="export.pdf.filename" value="ScorecardReport.pdf" />
													<display:setProperty name="export.csv.filename" value="ScorecardReport.csv" />
													<display:setProperty name="export.pdf" value="true" />
														
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
				                        </c:if>
				                        <c:if test="${ScoreableReport == true}">
				                         <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column title="MAC" sortable="true" style="text-align:center;" media="html">
														<span><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/ScoreableOnly" >${row.macName}</a></span>
													</display:column>
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;" media="pdf csv excel"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													
													<display:column property="scorableCount" title="Scoreable Count" sortable="true" style="text-align:center;"/>
													<display:column property="scorablePass" title="Scoreable Pass Count" sortable="true" style="text-align:center;"/>													
													<display:column title="Scoreable Pass Percent" sortable="true" style="text-align:center;" >
														${row.scorablePassPercent}%
													</display:column>
													<display:column property="scorableFail" title="Scoreable Fail Count" sortable="true" style="text-align:center;"/>													
													<display:column title="Scoreable Fail Percent" sortable="true" style="text-align:center;" >
														${row.scorableFailPercent}%
													</display:column>	
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:setProperty name="export.excel.filename" value="ScorecardReport.xls" />
													<display:setProperty name="export.pdf.filename" value="ScorecardReport.pdf" />
													<display:setProperty name="export.csv.filename" value="ScorecardReport.csv" />
													<display:setProperty name="export.pdf" value="true" />
															
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
				                        </c:if>
				                        <c:if test="${ScoreablePassReport == true}">
				                        <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column title="MAC" sortable="true" style="text-align:center;" media="html">
														<span><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/ScoreablePass" >${row.macName}</a></span>
													</display:column>
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;" media="pdf csv excel"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													
													<display:column property="scorableCount" title="Scoreable Count" sortable="true" style="text-align:center;"/>
													<display:column property="scorablePass" title="Scoreable Pass Count" sortable="true" style="text-align:center;"/>			
													<display:column title="Scoreable Pass Percent" sortable="true" style="text-align:center;" >
														${row.scorablePassPercent}%
													</display:column>
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:setProperty name="export.excel.filename" value="ScorecardReport.xls" />
													<display:setProperty name="export.pdf.filename" value="ScorecardReport.pdf" />
													<display:setProperty name="export.csv.filename" value="ScorecardReport.csv" />
													<display:setProperty name="export.pdf" value="true" />
														
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
				                        </c:if>
				                        <c:if test="${ScoreableFailReport == true}">
				                        <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column title="MAC" sortable="true" style="text-align:center;" media="html">
														<span><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/ScoreableFail" >${row.macName}</a></span>
													</display:column>
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;" media="pdf csv excel"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													
													<display:column property="scorableCount" title="Scoreable Count" sortable="true" style="text-align:center;"/>
													<display:column property="scorableFail" title="Scoreable Fail Count" sortable="true" style="text-align:center;"/>	
													<display:column title="Scoreable Fail Percent" sortable="true" style="text-align:center;" >
														${row.scorableFailPercent}%
													</display:column>	
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:setProperty name="export.excel.filename" value="ScorecardReport.xls" />
													<display:setProperty name="export.pdf.filename" value="ScorecardReport.pdf" />
													<display:setProperty name="export.csv.filename" value="ScorecardReport.csv" />
													<display:setProperty name="export.pdf" value="true" />
															
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
				                        </c:if>
				                        <c:if test="${NonScoreableReport == true}">
				                         <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column title="MAC" sortable="true" style="text-align:center;" media="html">
														<span><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/Non-Scoreable" >${row.macName}</a></span>
													</display:column>
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;" media="pdf csv excel"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													
													<display:column property="nonScorableCount" title="Non Scoreable Count" sortable="true" style="text-align:center;"/>
													<display:column title="Non Scoreable Percent" sortable="true" style="text-align:center;" >
														${row.nonScorablePercent}%
													</display:column>	
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:setProperty name="export.excel.filename" value="ScorecardReport.xls" />
													<display:setProperty name="export.pdf.filename" value="ScorecardReport.pdf" />
													<display:setProperty name="export.csv.filename" value="ScorecardReport.csv" />
													<display:setProperty name="export.pdf" value="true" />
														
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
				                        </c:if>
				                         <c:if test="${DoesNotCountReport == true}">
				                         <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${MAC_JURIS_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column title="MAC" sortable="true" style="text-align:center;" media="html">
														<span><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/${row.macName}/${row.jurisdictionName}/DoesNotCount" >${row.macName}</a></span>
													</display:column>
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;" media="pdf csv excel"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													
													
													<display:column property="doesNotCount_Number" title="Does Not Count" sortable="true" style="text-align:center;"/>
													<display:column title="Does Not Count Percent" sortable="true" style="text-align:center;" >
														${row.doesNotCount_Percent}%
													</display:column>	
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:setProperty name="export.excel.filename" value="ScorecardReport.xls" />
													<display:setProperty name="export.pdf.filename" value="ScorecardReport.pdf" />
													<display:setProperty name="export.csv.filename" value="ScorecardReport.csv" />
													<display:setProperty name="export.pdf" value="true" />
														
												</display:table>
												<c:if test="${fn:length(MAC_JURIS_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
				                        </c:if>
				                        <c:if test="${ComplianceReport == true}">
				                        <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${COMPLIANCE_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													
													<display:column property="monthYear" title="Month, Year" sortable="true" style="text-align:center;"/>
													<display:column property="complianceStatus" title="Compliance Status" sortable="true" style="text-align:center;"/>
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:setProperty name="export.excel.filename" value="ComplianceReport.xls" />
													<display:setProperty name="export.pdf.filename" value="ComplianceReport.pdf" />
													<display:setProperty name="export.csv.filename" value="ComplianceReport.csv" />
													<display:setProperty name="export.pdf" value="true" />
														
												</display:table>
												<c:if test="${fn:length(COMPLIANCE_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
				                        </c:if>
				                        <c:if test="${RebuttalReport == true}">
				                        <div class="row">
				                            <div class="col-sm-12 form-group">
				                                <display:table class="display hover stripe cell-border " id="row" name="${REBUTTAL_REPORT.values()}" 
													requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
													<display:column title="MAC" sortable="true" style="text-align:center;" media="html">
														<span><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/rebuttal-report-drilldown/${row.macName}/${row.jurisdictionName}/${reportsForm.callCategoryType}/${reportsForm.rebuttalStatus}" >${row.macName}</a></span>
													</display:column>
													<display:column property="macName" title="MAC" sortable="true" style="text-align:center;" media="pdf excel csv"/>
													<display:column property="jurisdictionName" title="Jurisdiction" sortable="true" style="text-align:center;"/>
													
													<display:column property="scorableCount" title="Number of Rebuttals" sortable="true" style="text-align:center;"/>
													<display:column property="qamStartDate" title="QAM Start Date" sortable="true" style="text-align:center;"/>
													<display:column property="qamEndDate" title="QAM End Date" sortable="true" style="text-align:center;"/>
													<display:setProperty name="export.excel.filename" value="Rebuttal.xls" />
													<display:setProperty name="export.pdf.filename" value="Rebuttal.pdf" />
													<display:setProperty name="export.csv.filename" value="Rebuttal.csv" />
													<display:setProperty name="export.pdf" value="true" />
													
														
												</display:table>
												<c:if test="${fn:length(REBUTTAL_REPORT.values()) eq 0}">
												   <span>No Data Found</span>	
												</c:if>	
				                            </div>		                           
				                        </div>  
				                        </c:if>
					                </div>
					            </div>  <!-- Main Row Div -->
								</div> 						
						
						</div> <!-- Content Div -->
					</div>
				</td>
			</tr>
		</form:form>
	</table>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>