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

	var allScorecardData =eval('${scoreCardList}');
	var allScoreCardDataTable = $('#allScoreCardId').DataTable( {
		"aaData": allScorecardData,
		"aoColumns": [
		{ "mData": "macCallReferenceNumber"},
		{ "mData": "callCategoryName"},
		{ "mData": "callDuration"},
		{ "mData": "finalCallResult"}
		],		
		dom: 'B<"clear">lfrtip',	
	     buttons: [
	         {
	             extend: 'copy',
	             title: reportTitle,
			     customize: includeSubtotalsAll
	         },
	         {
	             extend: 'excel',
	             customizeData: includeSubtotalsAll,
	             title: reportTitle,			     
	             messageOnTop: messageOnTop,
	             
	            
		      },
	         {
	             
			     extend: 'pdf',
			     title: reportTitle,
			     	customize: function(doc) {
			     		doc.defaultStyle.fontSize = 7;
			     		doc.styles.tableHeader.fontSize = 7; 
			       	},     
			        customizeData: includeSubtotalsAll			       
	         }	        
	     ],
	    // drawCallback: addSubtotalsAll,
		  "paging" : true,
		  "pageLength" : 20,
		  "ordering" : true,
		  "language": {
		      "emptyTable": "No data available"
		}
	       
	});
	
	allScoreCardDataTable.columns.adjust().draw();
	
	//Rebuttal Data Table Code
	var rebuttalReportData =eval('${rebuttalReportList}');
	var callCatTypeVar = $('#callCategoryType').val();
	var rebuttalStatusVar = $('#rebuttalStatus').val();
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
	        	var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/rebuttal-report-drilldown/"+data+"/"+row.jurisdictionName+"/"+callCatTypeVar+"/"+rebuttalStatusVar+"'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   }	
		 ], 
		 dom: '<lif<t>pB>',
	     buttons: [
	         {
	             extend: 'copyHtml5',
	             messageTop: messageOnTop,
	             title: reportTitle
	         },
	         {
	             extend: 'excelHtml5',
	             messageTop: messageOnTop,
	             title: reportTitle
	         },
	         {
	             extend: 'pdfHtml5',
	             messageTop: messageOnTop,
	             title: reportTitle,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 10,
		  "ordering" : true,
	});
	rebuttalReportDataTable.columns.adjust().draw();

	// Example: add subtotals by letter of first name
	function addSubtotalsAll(settings){
	  var api = this.api(),
	      rows = api.rows({ page: 'current' }),
	      cols = api.columns({ page: 'current' }),
	      last = null,
	      next = null,
	       
	      agg  = {};
	     
	      
	 var totalRows = api .column( 3 ) .data() . count();
	  
	  // only generate subtotals on initial display and when first column is sorted, but not other columns
	  if ( api.order().length && api.order()[0][0] !== 0 )
	    return;
	  
	  api.column(0, {page: 'current'}).data().each(function( text, rowNum, stack ){
	    var current_row = rows.data()[rowNum];
	    var next_rowNum = rowNum + 1;
	   
	    var intVal = function ( i ) {
            return typeof i === 'string' ?
                i.replace(/[\$,]/g, '')*1 :
                typeof i === 'number' ?
                    i : 0;
        };
        
	   if (rowNum===totalRows-1){

			// Total over all pages for Scoreable Count
	           var scoreableTotalCouunt = api.column( 3 ).data().reduce( function (a, b) {
	                   return intVal(a) + intVal(b);
	               }, 0 );
		      
		   // Average over all pages for Scoreable Pass
		   var callDurationAverage = scoreableTotalCouunt / totalRows * 100;
		  
		        
		 var $subtotal = $('<tr></tr>', {'class': 'subtotal'});
	     
	      cols.header().each(function(el, colNum){
	        var $td = $('<td></td>');
	        if (colNum === 1) {
		          $td.text( "Average" );
		    } else if (colNum === 2) {
	          $td.text(""  );
	        } else if (colNum === 3) {
	          $td.text(callDurationAverage );
	        } else if (colNum === 4) {
	          $td.text("" );
	        } 
	        $subtotal.append($td);
	       
	      });
	     
	      $(rows.nodes()).eq(rowNum).after($subtotal );
	    }
	  
	  });
	  
	}

	// for output formatting
	function includeSubtotalsAll( data, button, exportObject){
	  var classList = button.className.split(' ');
	  
	  // COPY
	  if (classList.includes('buttons-copy')){
	    
	    data = $('#allScoreCardId').toTSV();
	    exportObject.str = data;
	    exportObject.rows = $('#allScoreCardId').find('tr').length - 1;  // did not include the footer
	    
	  } 
	  // CSV
	  else if (classList.includes('buttons-csv')){
	    
	    data = $('#allScoreCardId').toCSV();
	    
	  }
	  // EXCEL/PDF/PRINT
	  else if (classList.includes('buttons-excel') || classList.includes('buttons-pdf') ||  classList.includes('buttons-print')){
		 
	    // data is actually the object to use for EXCEL/PDF/PRINT
	    var subtotals = [];
	    $('#allScoreCardId tr.subtotal').each(function(){
	      var $row = $(this),
	          row_index = $row.index(), 
	          row = $row.find('td,th').map(function(){return $(this).text();}).get();
	      subtotals[ subtotals.length ] = {rowNum: row_index, data: row };
	    });
	    
	    for (var i=0, n=subtotals.length; i<n; i++){
	      var subtotal = subtotals[i];
	      data.body.splice(subtotal.rowNum, 0, subtotal.data);
	    }
	  }
	  
	  return data;
	}
	

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
		<form:form method="POST" modelAttribute="reportsForm" class="form-signin" action="${pageContext.request.contextPath}/${SS_USER_FOLDER}/goBackMacAdminReports" id="reportsForm" >
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
									<div class="header">MAC Admin Report Results Screen</div>
									
								<div class="row " style="margin-top: 10px">
									<div class="col-lg-12 col-lg-offset-1 form-container">				                    
				                    
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
			                                <label for="name"> Program:</label>
			                                <label for="name"> ${reportsForm.programName}</label>
			                            </div>
			                            <div class="col-lg-4 form-group">
			                                
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
										            <th style="text-align: center">MAC Reference Number</th>
										            <th style="text-align: center">Call Category</th>
										            <th style="text-align: center">Call Duration</th>
										            <th style="text-align: center">Call Status</th>
										            	
										          										           
										        </tr>
										    </thead>
						                    <tbody style="text-align: center">  
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
										            <th style="text-align: center">MAC</th>
										            <th style="text-align: center">Jurisdiction</th>
										            <th style="text-align: center">Number of Rebuttals</th>
										            <th style="text-align: center">QAM Start Date</th>
										            <th style="text-align: center">QAM End Date</th>										                 
										        </tr>
										    </thead>
						                    <tbody style="text-align: center">  
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