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
<title>QAM - Summary Report</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/Comrad_icon.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link rel="stylesheet" href="/resources/demos/style.css" />

<!-- CSS for Bootstrap -->
<!-- JQuery -->

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css"/>
<!-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.2.2/css/buttons.dataTables.min.css"/> -->


<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
<<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.html5.min.js"></script> 
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.print.min.js"></script>
<!--    <script src="https://cdn.datatables.net/buttons/1.2.2/js/buttons.html5.js"></script> -->
<script type="text/javascript">
$(document).ready(function() {		
	var messageOnTop = 'MAC:${reportsForm.macName},Jurisdiction:${reportsForm.jurisdictionName},'
	+'Program:${reportsForm.programName},PCC/Location:${reportsForm.pccLocationName},'
	+'Report From Date:${reportsForm.fromDateString},Report To Date:${reportsForm.toDateString}';
	var reportTitle = '${ReportTitle}';	

	//Scorecard Data Table Code
	var scoreableReportData =eval('${scoreableReportList}');
	var scoreableReportDataTable = $('#scoreableReportDTId').DataTable( {
		"aaData": scoreableReportData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "program"},
		{ "mData": "scorableCount"},
		{ "mData": "scorablePassPercent"},
		{ "mData": "scorablePass"},
		{ "mData": "scorableFailPercent"},
		{ "mData": "scorableFail"},		
		{ "mData": "callDuration"},
		{ "mData": "macName"},
	
		],		
	    "columnDefs": [ 
	    	{ 
	           "render" : function(data, type, row) {
	        	   var linkData = "";	        	  
	        	   if(data != "zTotals" && row.program == null ) {
						linkData = data ;
		        	} else if (data=="zTotals" && row.program == null) {
		        		linkData = "Grand Total";
				     } else {
				    	 linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/"+row.program+"/ALL'>"+data+"</a></span>";
					 }
				
				return linkData;
		        },
			   "targets" : 0
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 4
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 6
		   },
		   { 
	           "render" : function(data, type, row) {
	        	   var sortData = row.macName+"_"+row.jurisdictionName+"_";	        	  
	        	   if(row.program == null) {
	        		   sortData += "ZZZZ";
		        	} else {
		        		sortData += row.program;
				     } 
				
				return sortData;
		        },
			   "targets" : 9,
			   "visible": false
		   }		
		 ], 
		 dom: 'Bfrtip',	
	     buttons: [
	         {
	             extend: 'copy',
	             title: reportTitle,	   
	             exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8]
	             }
	         },
	         {
	             extend: 'excel',
	           
	             title: reportTitle,			     
	             messageTop: messageOnTop,	
	             filename: 'QAM Summary Report',	   
	             exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8]
	             }
	             
	            
 		      },
 		     {  
 			     extend: 'pdfHtml5',
 			     exportOptions: {
 	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8]
 	             }, 	                     
 			     customize: function (doc) {
 			    	 var programs = scoreableReportDataTable.column(2).data().toArray();
 			    	 var list = 9;
 			    	for (var i = 0; i < programs.length; i++) {
 		               if (programs[i] == null) {
 			               if(i<programs.length-1) {
 			            	   for(var j=0; j<list; j++) {
 					            	  doc.content[2].table.body[i+1][j].fillColor = '#CEE9F5';
 						       } 
 				           } else {
 				        	   for(var j=0; j<list; j++) {
 					            	  doc.content[2].table.body[i+1][j].fillColor = '#58FAD0';
 						       } 
 					       } 
 			              
 		                 
 		               }
 		             }
 		             
 			     },
 			     title: reportTitle,
 				 messageTop: messageOnTop 	    	 	
	         }	        
	     ],
	     
	     "paging" : false,
		  "searching": false,
		  "sorting" : false,
		  "pageLength" : 100,		 
		  "language": {
		      "emptyTable": "No data available"
		    },
		    "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {		    
	               if ( aData.macName != "zTotals" && aData.program == null ) {
	                   $('td', nRow).css('background-color', '#CEE9F5');
	               } else if ( aData.macName == "zTotals" && aData.program == null ) {
	            	   $('td', nRow).css('background-color', '#58FAD0');
	               }               
	           }
	});

	// Sort by columns 1 and 2 and redraw
	scoreableReportDataTable
	    .order( [9, 'asc' ] )
	    .draw();	

	scoreableReportDataTable.columns.adjust().draw();	

	/* //Scorecard Pass Data Table Code
	var scoreablePassReportData =eval('${scoreablePassReportList}');
	var scoreablePassReportDataTable = $('#scoreablePassReportDTId').DataTable( {
		"aaData": scoreablePassReportData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "program"},
		{ "mData": "totalCount"},
		{ "mData": "scorableCount"},
		{ "mData": "scorablePass"},
		{ "mData": "scorablePassPercent"}
		],		
	    "columnDefs": [ 
	    	{ 
	           "render" : function(data, type, row) {
	        	   var linkData = "";	        	  
	        	   if(data != "zTotals" && row.program == null ) {
						linkData = data ;
		        	} else if (data=="zTotals" && row.program == null) {
		        		linkData = "Grand Total";
				     } else {
				    	 linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/"+row.program+"/ALL'>"+data+"</a></span>";
					 }
				
				return linkData;
		        },
			   "targets" : 0
		   },
		   { 
	           "render" : function(data, type, row) {
	        	   var linkData = "";	        	  
	        	   if(row.macName != "zTotals" && data == null ) {
						linkData = "Sub Total" ;
		        	} else {
				    	 linkData = data;
					 }
				
				return linkData;
		        },
			   "targets" : 2
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 6
		   },
		   { 
	           "render" : function(data, type, row) {
	        	   var sortData = row.macName+"_"+row.jurisdictionName+"_";	        	  
	        	   if(row.program == null) {
	        		   sortData += "ZZZZ";
		        	} else {
		        		sortData += row.program;
				     } 
				
				return sortData;
		        },
			   "targets" : 7,
			   "visible": false
		   }		
		 ], 
		 dom: 'Bfrtip',	
	     buttons: [
	         {
	             extend: 'copy',
	             title: reportTitle,
 			     exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6]
	             }
	         },
	         {
	             extend: 'excel',
	          
	             title: reportTitle,			     
	             messageTop: messageOnTop,
	             filename: 'Scorecard - Scoreable Pass Report',	   
 			     exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6]
	             }
	             
	            
 		      },
 		     {  
 			     extend: 'pdfHtml5',
 			     exportOptions: {
 	                    columns: [ 0, 1, 2, 3, 4, 5, 6]
 	             },  
 	                     
 			     customize: function (doc) {
 			    	 var programs = scoreablePassReportDataTable.column(2).data().toArray();
 			    	 var list = 7;
 		             for (var i = 0; i < programs.length; i++) {
 		               if (programs[i] == null) {
 			               if(i<programs.length-1) {
 			            	   for(var j=0; j<list; j++) {
 					            	  doc.content[2].table.body[i+1][j].fillColor = '#CEE9F5';
 						       } 
 				           } else {
 				        	   for(var j=0; j<list; j++) {
 					            	  doc.content[2].table.body[i+1][j].fillColor = '#58FAD0';
 						       } 
 					       }
 		               }
 		             }
 			     },
 			     title: reportTitle,
 				 messageTop: messageOnTop	
 	    	 	
 	         }	  
	     ],
	    
	     "paging" : false,
		  "searching": false,
		  "sorting" : false,
		  "pageLength" : 100,		 
		  "language": {
		      "emptyTable": "No data available"
		    },
	    "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {		    
               if ( aData.macName != "zTotals" && aData.program == null ) {
                   $('td', nRow).css('background-color', '#CEE9F5');
               } else if ( aData.macName == "zTotals" && aData.program == null ) {
            	   $('td', nRow).css('background-color', '#58FAD0');
               }               
           }
	});

	// Sort by columns 1 and 2 and redraw
	scoreablePassReportDataTable.order( [ 7, 'asc' ] ).draw();
	scoreablePassReportDataTable.columns.adjust().draw();
	

	//Scorecard Fail Data Table Code
	var scoreableFailReportData =eval('${scoreableFailReportList}');
	var scoreableFailReportTable = $('#scoreableFailReportDTId').DataTable( {
		"aaData": scoreableFailReportData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "program"},
		{ "mData": "totalCount"},
		{ "mData": "scorableCount"},
		{ "mData": "scorableFail"},
		{ "mData": "scorableFailPercent"}
		],		
	    "columnDefs": [ 
	    	{ 
	           "render" : function(data, type, row) {
	        	   var linkData = "";	        	  
	        	   if(data != "zTotals" && row.program == null ) {
						linkData = data ;
		        	} else if (data=="zTotals" && row.program == null) {
		        		linkData = "Grand Total";
				     } else {
				    	 linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/"+row.program+"/ALL'>"+data+"</a></span>";
					 }
				
				return linkData;
		        },
			   "targets" : 0
		   },
		   { 
	           "render" : function(data, type, row) {
	        	   var linkData = "";	        	  
	        	   if(row.macName != "zTotals" && data == null ) {
						linkData = "Sub Total" ;
		        	} else {
				    	 linkData = data;
					 }
				
				return linkData;
		        },
			   "targets" : 2
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 6
		   },
		   { 
	           "render" : function(data, type, row) {
	        	   var sortData = row.macName+"_"+row.jurisdictionName+"_";	        	  
	        	   if(row.program == null) {
	        		   sortData += "ZZZZ";
		        	} else {
		        		sortData += row.program;
				     } 
				
				return sortData;
		        },
			   "targets" : 7,
			   "visible": false
		   }			
		 ], 
		 dom: 'Bfrtip',	
	     buttons: [
	         {
	             extend: 'copy',
	             title: reportTitle,
	             exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6]
	             }
	         },
	         {
	             extend: 'excel',	            
	             title: reportTitle,			     
	             messageTop: messageOnTop,
	             filename: 'Scorecard - Scoreable Fail Report',	   
 			     exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6]
	             }
	             
	            
 		      },
  		     {  
  			     extend: 'pdfHtml5',
  			     exportOptions: {
  	                    columns: [ 0, 1, 2, 3, 4, 5, 6]
  	             },  
  	                     
  			     customize: function (doc) {
  			    	 var programs = scoreableFailReportTable.column(2).data().toArray();
  			    	 var list = 7;
  		             for (var i = 0; i < programs.length; i++) {
  		               if (programs[i] == null) {
  			               if(i<programs.length-1) {
  			            	   for(var j=0; j<list; j++) {
  					            	  doc.content[2].table.body[i+1][j].fillColor = '#CEE9F5';
  						       } 
  				           } else {
  				        	   for(var j=0; j<list; j++) {
  					            	  doc.content[2].table.body[i+1][j].fillColor = '#58FAD0';
  						       } 
  					       }
  		               }
  		             }
  			     },
  			     title: reportTitle,
  				 messageTop: messageOnTop	
  	    	 	
  	         }	  
	     ],
	     
	     "paging" : false,
		  "searching": false,
		  "sorting" : false,
		  "pageLength" : 100,		 
		  "language": {
		      "emptyTable": "No data available"
		    },
		    "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {		    
	               if ( aData.macName != "zTotals" && aData.program == null ) {
	                   $('td', nRow).css('background-color', '#CEE9F5');
	               } else if ( aData.macName == "zTotals" && aData.program == null ) {
	            	   $('td', nRow).css('background-color', '#58FAD0');
	               }               
	           }
	});
	// Sort by columns 1 and 2 and redraw
	scoreableFailReportTable.order( [ 7, 'asc' ] ).draw();
	scoreableFailReportTable.columns.adjust().draw(); */
	
});

</script>
<style>
.subtotal td {
  font-weight:bold;
  background: none;
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
									<div class="header">QAM Summary Reports</div>
									
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
			                            <c:if test="${reportsForm.jurisdictionName != ''}">
			                            <div class="col-lg-4 form-group">
			                                <label for="name"> Jurisdiction:</label>
			                                <label for="name"> ${reportsForm.jurisdictionName}</label>
			                            </div>
			                            </c:if>
			                        </div>
			                         <div class="row">
			                         <c:if test="${reportsForm.programName != ''}">
			                            <div class="col-lg-4 form-group">
			                                <label for="name"> Program:</label>
			                                <label for="name"> ${reportsForm.programName}</label>
			                            </div>
			                            </c:if>
			                            <c:if test="${reportsForm.pccLocationName != ''}">
			                            <div class="col-lg-4 form-group">
			                                <label for="name"> PCC/Location:</label>
			                                <label for="name"> ${reportsForm.pccLocationName}</label>
			                            </div>
			                            </c:if>
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
					                  	
				                   
				                     <c:if test="${SummaryScoreableReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="scoreableReportDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="scoreableReportDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										             <th style="text-align: left">Program</th>
										            <th style="text-align: left">Scoreable Count</th>
										             <th style="text-align: left">Scoreable Pass Percent</th>	
										            <th style="text-align: left">Scoreable Pass Count</th>
										           	<th style="text-align: left">Scoreable Fail Percent</th>									            
										            <th style="text-align: left">Scoreable Fail Count</th>										            
										            <th style="text-align: left">Avg Call Duration</th>					           
										        </tr>
										    </thead>
						                    <tbody style="text-align: left">  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if>
				                     
				                   <%-- <c:if test="${SummaryScoreablePassReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="scoreablePassReportDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="scoreablePassReportDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										             <th style="text-align: left">Program</th>
										            <th style="text-align: left">Total Count</th>
										            <th style="text-align: left">Scoreable Count</th>
										            <th style="text-align: left">Scoreable Pass Count</th>
										            <th style="text-align: left">Scoreable Pass Percent</th>
										             <th style="text-align: left">Call Duration</th>				           
										        </tr>
										    </thead>
						                    <tbody style="text-align: left">  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if>
				                     
				                      <c:if test="${SummaryScoreableFailReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="scoreableFailReportDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="scoreableFailReportDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										             <th style="text-align: left">Program</th>
										            <th style="text-align: left">Total Count</th>
										            <th style="text-align: left">Scoreable Count</th>
										            <th style="text-align: left">Scoreable Fail Count</th>
										            <th style="text-align: left">Scoreable Fail Percent</th>	
										             <th style="text-align: left">Call Duration</th>									            			           
										        </tr>
										    </thead>
						                    <tbody style="text-align: left">  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     </c:if> --%>
				                   
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