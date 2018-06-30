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
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/Comrad_icon.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link rel="stylesheet" href="/resources/demos/style.css" />

<!-- CSS for Bootstrap -->




<!-- JQuery -->



<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/autofill/2.1.1/css/autoFill.dataTables.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.1.2/css/buttons.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/colreorder/1.3.1/css/colReorder.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/fixedcolumns/3.2.1/css/fixedColumns.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/fixedheader/3.1.1/css/fixedHeader.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/keytable/2.1.1/css/keyTable.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/responsive/2.0.2/css/responsive.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/rowreorder/1.1.1/css/rowReorder.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/scroller/1.4.1/css/scroller.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/select/1.1.2/css/select.dataTables.min.css"/>
 
<script type="text/javascript" src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
<script type="text/javascript" src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/autofill/2.1.1/js/dataTables.autoFill.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.1.2/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.1.2/js/buttons.colVis.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.1.2/js/buttons.flash.min.js"></script>
<script type="text/javascript" src="http://vol7ron.github.io/assets/js/buttons.html5.js"></script>
<script type="text/javascript" src="http://vol7ron.github.io/assets/js/buttons.flash.js"></script>
<script type="text/javascript" src="http://vol7ron.github.io/assets/js/buttons.print.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/colreorder/1.3.1/js/dataTables.colReorder.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/fixedcolumns/3.2.1/js/dataTables.fixedColumns.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/fixedheader/3.1.1/js/dataTables.fixedHeader.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/keytable/2.1.1/js/dataTables.keyTable.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.0.2/js/dataTables.responsive.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/rowreorder/1.1.1/js/dataTables.rowReorder.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/scroller/1.4.1/js/dataTables.scroller.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/select/1.1.2/js/dataTables.select.min.js"></script>


<script type="text/javascript">
$(document).ready(function() {		
	
	var reportTitle = '${ReportTitle}';
	var messageOnTop = 'MAC:${reportsForm.macName}'+'  '+'Jurisdiction:${reportsForm.jurisdictionName}\n'
	+'Report From Date:${reportsForm.fromDateString}'+'  '+'Report To Date:${reportsForm.toDateString}';

		/* $('#qaspReportListRow > thead').prepend('<tr media="all"><th colspan="1" style="text-align: center">Month</th><th colspan="2" style="text-align: center"># of QAM Scorecards Completed</th><th colspan="2" style="text-align: center"># of QAM Scorecards Passed</th><th colspan="2" style="text-align: center"># of QAM Scorecards Failed</th></tr>');
	 */
	var qaspScorecardData =eval('${qaspReportList}');
	var qaspScorecardDataTable = $('#qaspReportDTId').DataTable( {
		"aaData": qaspScorecardData,
		"aoColumns": [
		{ "mData": "monthYear"},
		{ "mData": "scorableCount"},
		{ "mData": "hhhScorableCount"},
		{ "mData": "scorablePass"},
		{ "mData": "hhhScorablePass"},
		{ "mData": "scorableFail"},
		{ "mData": "hhhScorableFail"}
		],		
		 dom: '<lif<t>pB>',
	     buttons: [
	         {
	             extend: 'copyHtml5',
	             footer: true,
	             messageTop: messageOnTop,
	             title: reportTitle	            
	         },
	         {
	             extend: 'excelHtml5',
	             footer: true,
	             messageTop: messageOnTop,
	             title: reportTitle	             
	         },
	         {
	             extend: 'pdfHtml5',
	             footer: true,
	             stripNewlines: false,
	             messageTop: messageOnTop,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	             title: reportTitle
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 20,
		  "ordering" : true,
		  "searching": false/* ,		 
		  "footerCallback": function ( row, data, start, end, display ) {
	            var api = this.api(), data;
	 
	            // Remove the formatting to get integer data for summation
	            var intVal = function ( i ) {
	                return typeof i === 'string' ?
	                    i.replace(/[\$,]/g, '')*1 :
	                    typeof i === 'number' ?
	                        i : 0;
	            };
	            var totalRows = api .column( 4 ) .data() . count();

	            // Total over all pages for Scoreable Count
	            var scoreableCountTotal = api
	                .column( 1 )
	                .data()
	                .reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
	 
	            // Update footer
	           $( api.column( 1 ).footer() ).html( scoreableCountTotal );

	            // Total over all pages for Scoreable Pass Count
	            var scoreableHhhCountTotal = api
            .column( 2 )
		              .data()
		              .reduce( function (a, b) {
		                  return intVal(a) + intVal(b);
		              }, 0 );

		        // Update footer
		        $( api.column( 2 ).footer() ).html( scoreableHhhCountTotal );

		       
		     	// Total over all pages for Scoreable Fail Count
		        var scoreablePassCountTotal = api
		              .column( 3 )
		              .data()
		              .reduce( function (a, b) {
		                  return intVal(a) + intVal(b);
		              }, 0 );

		        // Update footer
		       $( api.column( 3 ).footer() ).html( scoreablePassCountTotal );

		     // Total over all pages for Scoreable Fail Count
		        var scoreableHhhPassCountTotal = api
		              .column( 4 )
		              .data()
		              .reduce( function (a, b) {
		                  return intVal(a) + intVal(b);
		              }, 0 );

		        // Update footer
		        $( api.column( 4 ).footer() ).html( scoreableHhhPassCountTotal );

		        
		     	// Total over all pages for Non Scoreable Count
		        var scoreableFailCountTotal = api
		              .column( 5)
		              .data()
		              .reduce( function (a, b) {
		                  return intVal(a) + intVal(b);
		              }, 0 );

		        // Update footer
		        $( api.column( 5 ).footer() ).html( scoreableFailCountTotal );

		        // Average over all pages for Scoreable Pass
		        var scoreableFailHhhCountTotal = api
		            .column( 6 )
		            .data()
		            .reduce( function (a, b) {
		                return intVal(a) + intVal(b);
		            }, 0) ;
		
		        // Update footer
		        $( api.column( 6).footer() ).html(scoreableFailHhhCountTotal);
		        
				var totalScoreCards = scoreableCountTotal + scoreableHhhCountTotal;
				var totalPassScoreCards = scoreablePassCountTotal + scoreableHhhPassCountTotal;
				var totalFailScoreCards = scoreableFailCountTotal + scoreableFailHhhCountTotal;
				
		        $('tr:eq(2) th:eq(1)', api.table().footer()).html(totalScoreCards);
		        $('tr:eq(2) th:eq(2)', api.table().footer()).html(totalPassScoreCards);
		        $('tr:eq(2) th:eq(3)', api.table().footer()).html(totalFailScoreCards);

		     // Update footer
		     if(totalScoreCards == 0) {
		    	 $('tr:eq(3) th:eq(0)', api.table().footer()).html('Average Quality Rate: Not Available In Selected Dates');
			  } else {
				  var average = (totalPassScoreCards / totalScoreCards) * 100;
			      $('tr:eq(3) th:eq(0)', api.table().footer()).html('Average Quality Rate: '+average.toFixed(0) +"%");

			  }
		    
		  } */
		     
	});
	qaspScorecardDataTable.columns.adjust().draw(); 

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
					                  	
				                   
				                     <c:if test="${QaspReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   	<div class="row" id="qaspReportDiv">
			                            <div class="col-lg-10 form-group">			                            	
			                           
			                           
			                            			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="qaspReportDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: center" rowspan="2">Month</th>
										            <th style="text-align: center" colspan="2"># of QAM Scorecards Completed</th>
										            <th style="text-align: center" colspan="2"># of QAM Scorecards Passed</th>
										            <th style="text-align: center" colspan="2"># of QAM Scorecards Failed</th>
										            									                 
										        </tr>
										         <tr>
										        	<th style="text-align: center">A/B</th>
										            <th style="text-align: center">HHH</th>
										            <th style="text-align: center">A/B</th>
										            <th style="text-align: center">HHH</th>
										            <th style="text-align: center">A/B</th>
										            <th style="text-align: center">HHH</th>
										        </tr>
										    </thead>
						                    <tbody style="text-align: center">  
						                    </tbody>
						                    <tfoot >
								            <tr >
								            
								             <th colspan="1" style="text-align:right">Sub-Totals:</th>
								                <th style="text-align: center"></th>
								                <th style="text-align: center"></th>
								                <th style="text-align: center"></th>
								                <th style="text-align: center"></th>
								                <th style="text-align: center"></th>
								                <th style="text-align: center"></th> 
								               
								            </tr>
								            <tr>
								                <th colspan="1" style="text-align:right"></th>
								                <th style="text-align: center" colspan="2">Total Completed</th>
								                <th style="text-align: center" colspan="2">Total Passed</th>
								                <th style="text-align: center" colspan="2">Total Failed</th>
								            </tr>
								            <tr>
								                <th colspan="1" style="text-align:right">TOTALS</th>
								                <th style="text-align: center" colspan="2"></th>
								                <th style="text-align: center" colspan="2"></th>
								                <th style="text-align: center" colspan="2"></th>
								            </tr>
								            <tr>
								                <th colspan="7" style="text-align:center"></th>								                
								            </tr> 
								        </tfoot>
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