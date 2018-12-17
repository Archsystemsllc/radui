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
<title>QAM - Compliance Report</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/Comrad_icon.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link rel="stylesheet" href="/resources/demos/style.css" />

<!-- CSS for Bootstrap -->

<!-- JQuery -->
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.2.2/css/buttons.dataTables.min.css"/>


<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.html5.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.print.min.js"></script>


<script type="text/javascript">
$(document).ready(function() {		
	
	var messageOnTop = 'MAC:${reportsForm.macName},Jurisdiction:${reportsForm.jurisdictionName},'
		+'Report From Date:${reportsForm.fromDateString},Report To Date:${reportsForm.toDateString}';
	var reportTitle = '${ReportTitle}';	
		
	//Compliance Data Table Code
	var complianceReportData =eval('${complianceReportList}');
	var complianceReportDataTable = $('#complianceReportDTId').DataTable( {
		"aaData": complianceReportData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "monthYear"},
		{ "mData": "complianceStatus"},
		],
		"columnDefs": [ 	       
		   {
               "targets": [ 3 ],
               className: 'dt-body-left'
           }
		 ], 
		 dom: 'Bfrtip',
	     buttons: [
	         {
	             extend: 'copy',
	             messageTop: messageOnTop,
	             title: reportTitle
	         },
	         {
	             extend: 'excel',
	             messageTop: messageOnTop,
	             title: reportTitle
	         },
	         {
	             extend: 'pdf',
	             messageTop: messageOnTop,
	             title: reportTitle,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	         }	        
	     ],
	     "paging" : false,
		  "pageLength" : 100,
		  "ordering" : true,
		  "language": {
		      "emptyTable": "No data available"
		    }
	});
	complianceReportDataTable.columns.adjust().draw();	
	
});



</script>
<style>
.subtotal td {
  font-weight:bold;
  background: lightslategray;
}
</style>

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
								 <form:hidden path="pccLocationId" />
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
								 
								 			
								<div class="table-users" style="width: 98%">
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
										        </tr>
										    </thead>
						                    <tbody style="text-align: left">  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if>
				                     
				                    </div>
					                </div>
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