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
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />

<link rel="stylesheet" href="/resources/demos/style.css" />
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" />
<!-- CSS for Bootstrap -->
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"></link>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- JQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>

<script src="https://cdn.datatables.net/buttons/1.5.1/js/dataTables.buttons.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.flash.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
<script src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.html5.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.print.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.colVis.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {	
	
	var reportTitle = '${ReportTitle}';

	var allScorecardData =eval('${scoreCardList}');
	var allScoreCardDataTable = $('#allScoreCardId').DataTable( {
		"aaData": allScorecardData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "scorableCount"},
		{ "mData": "scorablePass"},
		{ "mData": "scorablePassPercent"},
		{ "mData": "scorableFail"},
		{ "mData": "scorableFailPercent"},
		{ "mData": "nonScorableCount"},
		{ "mData": "nonScorablePercent"},
		{ "mData": "doesNotCount_Number"},
		{ "mData": "doesNotCount_Percent"},
		{ "mData": "qamStartDate"},
		{ "mData": "qamEndDate"}
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/ALL'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   }	
		 ], 
		 dom: '<lif<t>pB>',
	     buttons: [
	         {
	             extend: 'copyHtml5',
	             messageTop: reportTitle,
	             title: reportTitle
	         },
	         {
	             extend: 'excelHtml5',
	             messageTop: reportTitle,
	             title: reportTitle
	         },
	         {
	             extend: 'pdfHtml5',
	             messageTop: reportTitle,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	             title: reportTitle
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 10,
		  "ordering" : true,
	});

	

	var allPassScorecardData =eval('${allPassScoreCardList}');
	var allPassScorecardDataTable = $('#allPassScorecardDTId').DataTable( {
		"aaData": allPassScorecardData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "scorableCount"},
		{ "mData": "scorablePass"},
		{ "mData": "scorablePassPercent"},
		{ "mData": "nonScorableCount"},
		{ "mData": "nonScorablePercent"},
		{ "mData": "doesNotCount_Number"},
		{ "mData": "doesNotCount_Percent"},
		{ "mData": "qamStartDate"},
		{ "mData": "qamEndDate"}
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/PASS'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   }	
		 ], 
		 dom: '<lif<t>pB>',
	     buttons: [
	         {
	             extend: 'copyHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'excelHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'pdfHtml5',
	             messageTop: reportTitle,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 10,
		  "ordering" : true,
	});

	//Fail Scorecard Data Table Code
	var allFailScorecardData =eval('${allFailScorecardList}');
	var failScorecardDataTable = $('#allFailScorecardDTId').DataTable( {
		"aaData": allFailScorecardData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "scorableFail"},
		{ "mData": "scorableFailPercent"},
		{ "mData": "nonScorableCount"},
		{ "mData": "nonScorablePercent"},
		{ "mData": "doesNotCount_Number"},
		{ "mData": "doesNotCount_Percent"},
		{ "mData": "qamStartDate"},
		{ "mData": "qamEndDate"}
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/PASS'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   }	
		 ], 
		 dom: '<lif<t>pB>',
	     buttons: [
	         {
	             extend: 'copyHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'excelHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'pdfHtml5',
	             messageTop: reportTitle,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 10,
		  "ordering" : true,
	});

	//Scorecard Data Table Code
	var scoreableReportData =eval('${scoreableReportList}');
	var scoreableReportDataTable = $('#scoreableReportDTId').DataTable( {
		"aaData": scoreableReportData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "scorableCount"},
		{ "mData": "scorablePass"},
		{ "mData": "scorablePassPercent"},
		{ "mData": "scorableFail"},
		{ "mData": "scorableFailPercent"},
		{ "mData": "qamStartDate"},
		{ "mData": "qamEndDate"}
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/ScoreableOnly'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   }	
		 ], 
		 dom: '<lif<t>pB>',
	     buttons: [
	         {
	             extend: 'copyHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'excelHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'pdfHtml5',
	             messageTop: reportTitle,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 10,
		  "ordering" : true,
	});

	//Scorecard Pass Data Table Code
	var scoreablePassReportData =eval('${scoreablePassReportList}');
	var scoreablePassReportDataTable = $('#scoreablePassReportDTId').DataTable( {
		"aaData": scoreablePassReportData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "scorableCount"},
		{ "mData": "scorablePass"},
		{ "mData": "scorablePassPercent"},
		{ "mData": "qamStartDate"},
		{ "mData": "qamEndDate"}
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/ScoreablePass'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   }	
		 ], 
		 dom: '<lif<t>pB>',
	     buttons: [
	         {
	             extend: 'copyHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'excelHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'pdfHtml5',
	             messageTop: reportTitle,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 10,
		  "ordering" : true,
	});


	//Scorecard Fail Data Table Code
	var scoreableFailReportData =eval('${scoreableFailReportList}');
	var scoreableFailReportTable = $('#scoreableFailReportDTId').DataTable( {
		"aaData": scoreableFailReportData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "scorableCount"},
		{ "mData": "scorableFail"},
		{ "mData": "scorableFailPercent"},
		{ "mData": "qamStartDate"},
		{ "mData": "qamEndDate"}
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/PASS'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   }	
		 ], 
		 dom: '<lif<t>pB>',
	     buttons: [
	         {
	             extend: 'copyHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'excelHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'pdfHtml5',
	             messageTop: reportTitle,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 10,
		  "ordering" : true,
	});


	//Non-Scoreable Data Table Code
	var nonScoreableData =eval('${nonScoreableList}');
	var nonScoreableDataTable = $('#nonScoreableDTId').DataTable( {
		"aaData": nonScoreableData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "nonScorableCount"},
		{ "mData": "nonScorablePercent"},
		{ "mData": "qamStartDate"},
		{ "mData": "qamEndDate"}
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/PASS'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   }	
		 ], 
		 dom: '<lif<t>pB>',
	     buttons: [
	         {
	             extend: 'copyHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'excelHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'pdfHtml5',
	             messageTop: reportTitle,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 10,
		  "ordering" : true,
	});

	//Does Not Count Data Table Code
	var doesNotCountData =eval('${doesNotCountList}');
	var doesNotCountDataTable = $('#doesNotCountDTId').DataTable( {
		"aaData": doesNotCountData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "doesNotCount_Number"},
		{ "mData": "doesNotCount_Percent"},
		{ "mData": "qamStartDate"},
		{ "mData": "qamEndDate"}
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/PASS'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   }	
		 ], 
		 dom: '<lif<t>pB>',
	     buttons: [
	         {
	             extend: 'copyHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'excelHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'pdfHtml5',
	             messageTop: reportTitle,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 10,
		  "ordering" : true,
	});

	//Compliance Data Table Code
	var complianceReportData =eval('${complianceReportList}');
	var complianceReportDataTable = $('#complianceReportDTId').DataTable( {
		"aaData": complianceReportData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "monthYear"},
		{ "mData": "complianceStatus"},
		{ "mData": "qamStartDate"},
		{ "mData": "qamEndDate"}
		],
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/PASS'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   }	
		 ], 
		 dom: '<lif<t>pB>',
	     buttons: [
	         {
	             extend: 'copyHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'excelHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'pdfHtml5',
	             messageTop: reportTitle,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 10,
		  "ordering" : true,
	});

	//Compliance Data Table Code
	var rebuttalReportData =eval('${rebuttalReportList}');
	var rebuttalReportDataTable = $('#rebuttalReportDTId').DataTable( {
		"aaData": rebuttalReportData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "scorableCount"},
		{ "mData": "qamStartDate"},
		{ "mData": "qamEndDate"}
		],
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/PASS'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   }	
		 ], 
		 dom: '<lif<t>pB>',
	     buttons: [
	         {
	             extend: 'copyHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'excelHtml5',
	             messageTop: reportTitle
	         },
	         {
	             extend: 'pdfHtml5',
	             messageTop: reportTitle,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 10,
		  "ordering" : true,
	});
	

});
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
								 
								 			
								<div class="table-users" style="width: 95%">
									<div class="header">Report Results Screen</div>
									
								<div class="row " style="margin-top: 10px">
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
				                </div>
									
								
								 <div class="row" id="allScoreCardMainDiv">	
					             <div class="col-lg-12 col-lg-offset-1 form-container">
					                
					                    <h2>${ReportTitle}</h2> 
					                  	
				                   <c:if test="${AllScoreCardReport_All == true}">
				                        
				               
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="allScoreCardDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="allScoreCardId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										            <th style="text-align: left">Scoreable Count</th>
										            <th style="text-align: left">Scoreable Pass Count</th>
										            <th style="text-align: left">Scoreable Pass Percent</th> 
										            <th style="text-align: left">Scoreable Fail Count</th>
										            <th style="text-align: left">Scoreable Fail Percent</th>
										            <th style="text-align: left">Non-Scoreable Count</th>										            
										            <th style="text-align: left">Non-Scoreable Percent</th> 
										            <th style="text-align: left">Does Not Count</th>
										            <th style="text-align: left">Does Not Count Percent</th>
										             <th style="text-align: left">QAM Start Date</th>
										            <th style="text-align: left">QAM End Date</th>		
										          										           
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if>
				                     
				                   <c:if test="${AllScoreCardReport_Pass == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="allPassScorecardDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="allPassScorecardDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										            <th style="text-align: left">Scoreable Count</th>
										            <th style="text-align: left">Scoreable Pass Count</th>
										            <th style="text-align: left">Scoreable Pass Percent</th> 
										            <th style="text-align: left">Non-Scoreable Count</th>										            
										            <th style="text-align: left">Non-Scoreable Percent</th> 
										            <th style="text-align: left">Does Not Count</th>
										            <th style="text-align: left">Does Not Count Percent</th>	
										             <th style="text-align: left">QAM Start Date</th>
										            <th style="text-align: left">QAM End Date</th>											          										           
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if>
				                     
				                     
				                  <c:if test="${AllScoreCardReport_Fail == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="allFailScorecardDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="allFailScorecardDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										            <th style="text-align: left">Scoreable Fail Count</th>
										            <th style="text-align: left">Scoreable Fail Percent</th>
										            <th style="text-align: left">Non-Scoreable Count</th>										            
										            <th style="text-align: left">Non-Scoreable Percent</th> 
										            <th style="text-align: left">Does Not Count</th>
										            <th style="text-align: left">Does Not Count Percent</th>	
										             <th style="text-align: left">QAM Start Date</th>
										            <th style="text-align: left">QAM End Date</th>											          										           
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if>
				                     
				                     <c:if test="${ScoreableReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="scoreableReportDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="scoreableReportDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										            <th style="text-align: left">Scoreable Count</th>
										            <th style="text-align: left">Scoreable Pass Count</th>
										            <th style="text-align: left">Scoreable Pass Percent</th>										            
										            <th style="text-align: left">Scoreable Fail Count</th> 
										            <th style="text-align: left">Scoreable Fail Percent</th>
										             <th style="text-align: left">QAM Start Date</th>
										            <th style="text-align: left">QAM End Date</th>		
										            								          										           
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if>
				                     
				                   <c:if test="${ScoreablePassReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="scoreablePassReportDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="scoreablePassReportDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										            <th style="text-align: left">Scoreable Count</th>
										            <th style="text-align: left">Scoreable Pass Count</th>
										            <th style="text-align: left">Scoreable Pass Percent</th>
										             <th style="text-align: left">QAM Start Date</th>
										            <th style="text-align: left">QAM End Date</th>												            
										            
										            								          										           
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if>
				                     
				                      <c:if test="${ScoreableFailReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="scoreableFailReportDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="scoreableFailReportDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										            <th style="text-align: left">Scoreable Count</th>
										            <th style="text-align: left">Scoreable Fail Count</th>
										            <th style="text-align: left">Scoreable Fail Percent</th>
										             <th style="text-align: left">QAM Start Date</th>
										            <th style="text-align: left">QAM End Date</th>												            
										            
										            								          										           
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if>
				                     
				                      <c:if test="${NonScoreableReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="nonScoreableDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="nonScoreableDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										            <th style="text-align: left">Non-Scoreable Count</th>
										            <th style="text-align: left">Non-Scoreable Percent</th>
										             <th style="text-align: left">QAM Start Date</th>
										            <th style="text-align: left">QAM End Date</th>		
										            								          										           
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if>
				                     
				                     <c:if test="${DoesNotCountReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="doesNotCountDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="doesNotCountDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										            <th style="text-align: left">Does Not Count</th>
										            <th style="text-align: left">Does Not Count Percent</th>
										             <th style="text-align: left">QAM Start Date</th>
										            <th style="text-align: left">QAM End Date</th>		
										            								          										           
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if>
				                     
				                     <c:if test="${ComplianceReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="complianceReportDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="complianceReportDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										            <th style="text-align: left">Month, Year</th>
										            <th style="text-align: left">Compliance Status</th>	
										             <th style="text-align: left">QAM Start Date</th>
										            <th style="text-align: left">QAM End Date</th>							           
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if>
				                     
				                    <c:if test="${RebuttalReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   	<div class="row" id="rebuttalReportDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="rebuttalReportDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										            <th style="text-align: left">Number of Rebuttals</th>
										            <th style="text-align: left">QAM Start Date</th>
										            <th style="text-align: left">QAM End Date</th>										                 
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if>
					                
					            </div>  <!-- Main Row Div -->
													
						
						</div> <!-- Content Div -->
					</div>
				</td>
			</tr>
		</form:form>
	</table>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>