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
<title>QAM - Scorecard Report</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/Comrad_icon.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link rel="stylesheet" href="/resources/demos/style.css" />

<!-- CSS for Bootstrap -->
<!-- JQuery -->

<!-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.2.2/css/buttons.dataTables.min.css"/>


<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/2.6.0/jszip.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.html5.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.print.min.js"></script>

<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.2.2/js/dataTables.buttons.min.js"></script> 
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.2.2/js/buttons.html5.min.js"></script> -->


<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.1/jszip.js"></script>
    <link href="https://nightly.datatables.net/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
    <script src="https://nightly.datatables.net/js/jquery.dataTables.js"></script>

    <link href="https://nightly.datatables.net/buttons/css/buttons.dataTables.css?_=82057b89834dd191a8e620fb781c34b2.css" rel="stylesheet" type="text/css" />
<script src="https://nightly.datatables.net/buttons/js/dataTables.buttons.js?_=82057b89834dd191a8e620fb781c34b2"></script>
<script src="https://nightly.datatables.net/buttons/js/buttons.html5.js?_=82057b89834dd191a8e620fb781c34b2"></script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.print.min.js"></script>

<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.1.2/js/dataTables.buttons.min.js"></script>


<script type="text/javascript" src="https://cdn.datatables.net/colreorder/1.3.1/js/dataTables.colReorder.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/fixedcolumns/3.2.1/js/dataTables.fixedColumns.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/fixedheader/3.1.1/js/dataTables.fixedHeader.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/keytable/2.1.1/js/dataTables.keyTable.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.0.2/js/dataTables.responsive.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/rowreorder/1.1.1/js/dataTables.rowReorder.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/scroller/1.4.1/js/dataTables.scroller.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/select/1.1.2/js/dataTables.select.min.js"></script>


<!-- <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.1.2/js/buttons.colVis.min.js"></script>
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
 -->


<script type="text/javascript">
$(document).ready(function() {		
	var messageOnTop = 'MAC:${reportsForm.macName}'+'  '+'Jurisdiction:${reportsForm.jurisdictionName}\n'
	+'Program:${reportsForm.programName}'+'  '+'PCC/Location:${reportsForm.pccLocationName}\n'
	+'Report From Date:${reportsForm.fromDateString}'+'  '+'Report To Date:${reportsForm.toDateString}';
	var reportTitle = '${ReportTitle}';	

	var allScorecardData =eval('${scoreCardList}');	

	var allScoreCardDataTable = $('#allScoreCardId').DataTable( {
		"aaData": allScorecardData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "totalCount"},
		{ "mData": "scorableCount"},
		{ "mData": "scorablePass"},
		{ "mData": "scorablePassPercent"},
		{ "mData": "scorableFail"},
		{ "mData": "scorableFailPercent"},
		{ "mData": "nonScorableCount"},
		{ "mData": "nonScorablePercent"},
		{ "mData": "doesNotCount_Number"},
		{ "mData": "doesNotCount_Percent"}		
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/ALL'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 5
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 7
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 9
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 11,
           className: 'dt-body-left'
		   }	
		 ], 
		 dom: 'Bfrtip',	
	     buttons: [
	         {
	             extend: 'copy',
	             title: reportTitle,
			     customize: includeSubtotalsAll,
	             className: 'copyButton'
	         },
	         {
	             extend: 'excelHtml5',
	             title: reportTitle,
	             messageTop: messageOnTop,
	             filename: 'Scorecard Report',
	             customizeData: includeSubtotalsAll
 		      },
	         {  
			     extend: 'pdf',
			     title: reportTitle,
		     	 customize: function(doc) {
		     		doc.defaultStyle.fontSize = 7;
		     		doc.styles.tableHeader.fontSize = 7; 
		       	 },     
			     customizeData: includeSubtotalsAll,
	             className: 'pdfButton'		       
	         }	        
	     ],
	      drawCallback: addSubtotalsAll,
		  "paging" : false,
		  "pageLength" : 100,
		  "ordering" : true,
		  "language": {
		      "emptyTable": "No data available"
		    }
	});
	
	allScoreCardDataTable.columns.adjust().draw();

	var allPassScorecardData =eval('${allPassScoreCardList}');
	var allPassScorecardDataTable = $('#allPassScorecardDTId').DataTable( {
		"aaData": allPassScorecardData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "totalCount"},
		{ "mData": "scorableCount"},
		{ "mData": "scorablePass"},
		{ "mData": "scorablePassPercent"},
		{ "mData": "nonScorableCount"},
		{ "mData": "nonScorablePercent"},
		{ "mData": "doesNotCount_Number"},
		{ "mData": "doesNotCount_Percent"}
		
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/ScoreablePass'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 5
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 7
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
	        "targets" : 9
		   }
		 ], 
		 dom: 'B<"clear">lfrtip',	
	     buttons: [
	         {
	             extend: 'copy',
	             title: reportTitle,
			     customize: includeSubtotalsPassScoreCards
	         },
	         {
	             extend: 'excel',
	             title: reportTitle,			     
	             messageOnTop: messageOnTop,
	             customizeData: includeSubtotalsPassScoreCards
 		      },
	         {
	             
			     extend: 'pdf',
			     title: reportTitle,
			     	customize: function(doc) {
			     		doc.defaultStyle.fontSize = 7;
			     		doc.styles.tableHeader.fontSize = 7; 
			       	},     
			        customizeData: includeSubtotalsPassScoreCards			       
	         }	        
	     ],
	      drawCallback: addSubtotalsPassScoreCards,
		  "paging" : true,
		  "pageLength" : 20,
		  "ordering" : true,
		  "language": {
		      "emptyTable": "No data available"
		    }
	});
	allPassScorecardDataTable.columns.adjust().draw();

	//Fail Scorecard Data Table Code
	var allFailScorecardData =eval('${allFailScorecardList}');
	var failScorecardDataTable = $('#allFailScorecardDTId').DataTable( {
		"aaData": allFailScorecardData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "totalCount"},
		{ "mData": "scorableCount"},
		{ "mData": "scorableFail"},
		{ "mData": "scorableFailPercent"},
		{ "mData": "nonScorableCount"},
		{ "mData": "nonScorablePercent"},
		{ "mData": "doesNotCount_Number"},
		{ "mData": "doesNotCount_Percent"}	
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/ScoreableFail'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 5
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 7
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 9
		   }
		 ], 
		 dom: 'B<"clear">lfrtip',	
	     buttons: [
	         {
	             extend: 'copy',
	             title: reportTitle,
			     customize: includeSubtotalsFailScoreCards
	         },
	         {
	             extend: 'excel',
	             customizeData: includeSubtotalsFailScoreCards,
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
			        customizeData: includeSubtotalsFailScoreCards			       
	         }	        
	     ],
	      drawCallback: addSubtotalsFailScoreCards,
		  "paging" : true,
		  "pageLength" : 20,
		  "ordering" : true,
		  "responsive": true,
		  "bAutoWidth": true,
		  "language": {
		      "emptyTable": "No data available"
		    }
	});

	failScorecardDataTable.columns.adjust().draw();

	//Scorecard Data Table Code
	var scoreableReportData =eval('${scoreableReportList}');
	var scoreableReportDataTable = $('#scoreableReportDTId').DataTable( {
		"aaData": scoreableReportData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "totalCount"},
		{ "mData": "scorableCount"},
		{ "mData": "scorablePass"},
		{ "mData": "scorablePassPercent"},
		{ "mData": "scorableFail"},
		{ "mData": "scorableFailPercent"}
	
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/ScoreableOnly'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 5
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 7
		   }	
		 ], 
		 dom: 'B<"clear">lfrtip',	
	     buttons: [
	         {
	             extend: 'copy',
	             title: reportTitle,
			     customize: includeSubtotalsScoreable
	         },
	         {
	             extend: 'excel',
	             customizeData: includeSubtotalsScoreable,
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
			        customizeData: includeSubtotalsScoreable			       
	         }	        
	     ],
	      drawCallback: addSubtotalsScoreable,
		  "paging" : true,
		  "pageLength" : 20,
		  "ordering" : true,
		  "language": {
		      "emptyTable": "No data available"
		    }
	});

	scoreableReportDataTable.columns.adjust().draw();
	

	//Scorecard Pass Data Table Code
	var scoreablePassReportData =eval('${scoreablePassReportList}');
	var scoreablePassReportDataTable = $('#scoreablePassReportDTId').DataTable( {
		"aaData": scoreablePassReportData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "totalCount"},
		{ "mData": "scorableCount"},
		{ "mData": "scorablePass"},
		{ "mData": "scorablePassPercent"}
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/ScoreablePass'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 5
		   }	
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
	      drawCallback: addSubtotalsAll,
		  "paging" : true,
		  "pageLength" : 20,
		  "ordering" : true,
		  "language": {
		      "emptyTable": "No data available"
		    }
	});
	scoreablePassReportDataTable.columns.adjust().draw();
	

	//Scorecard Fail Data Table Code
	var scoreableFailReportData =eval('${scoreableFailReportList}');
	var scoreableFailReportTable = $('#scoreableFailReportDTId').DataTable( {
		"aaData": scoreableFailReportData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "totalCount"},
		{ "mData": "scorableCount"},
		{ "mData": "scorableFail"},
		{ "mData": "scorableFailPercent"}
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/ScoreableFail'>"+data+"</a></span>";
				return linkData;
	        },
		   "targets" : 0
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 5
		   }	
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
	      drawCallback: addSubtotalsAll,
		  "paging" : true,
		  "pageLength" : 20,
		  "ordering" : true,
		  "language": {
		      "emptyTable": "No data available"
		    }
	});

	scoreableFailReportTable.columns.adjust().draw();
	
	//Non-Scoreable Data Table Code
	var nonScoreableData =eval('${nonScoreableList}');
	var nonScoreableDataTable = $('#nonScoreableDTId').DataTable( {
		"aaData": nonScoreableData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "totalCount"},
		{ "mData": "nonScorableCount"},
		{ "mData": "nonScorablePercent"}
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/Non-Scoreable'>"+data+"</a></span>";
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
		   }	
		 ], 
		 dom: 'B<"clear">lfrtip',
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
		  "pageLength" : 20,
		  "ordering" : true,
		  "language": {
		      "emptyTable": "No data available"
		    }
	});
	nonScoreableDataTable.columns.adjust().draw();	

	//Does Not Count Data Table Code
	var doesNotCountData =eval('${doesNotCountList}');
	var doesNotCountDataTable = $('#doesNotCountDTId').DataTable( {
		"aaData": doesNotCountData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "totalCount"},
		{ "mData": "doesNotCount_Number"},
		{ "mData": "doesNotCount_Percent"}
		],		
	    "columnDefs": [ 
	        { 
	           "render" : function(data, type, row) {
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/Does Not Count'>"+data+"</a></span>";
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
		   }	
		 ], 
		 dom: 'B<"clear">lfrtip',
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
		  "pageLength" : 20,
		  "ordering" : true,
		  "language": {
		      "emptyTable": "No data available"
		    }
	});
	doesNotCountDataTable.columns.adjust().draw();
	
	// Example: add subtotals by letter of first name
	function addSubtotalsAll(settings){
	  var api = this.api(),
	      rows = api.rows({ page: 'current' }),
	      cols = api.columns({ page: 'current' }),
	      last = null,
	      next = null,	       
	      agg  = {},
	      scoreable_sum_colNum = 2;
	      scoreable_sum_colNum = 3;
	      scoreable_pass_sum_colNum = 4;
	      scoreable_pass_percent_colNum = 5;
	      scoreable_fail_count_sum_colNum = 6;
	      scoreable_fail_percent_sum_colNum = 7;
	      non_scoreable_count_sum_colNum = 8;
	      non_scoreable_percent_sum_colNum = 9;
	      doesn_not_count_sum_colNum = 10;
	      doesn_not_count_percent_colNum = 11;

	 var totalRows = api .column( 4 ) .data() . count();
	  
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
	           var scoreableTotalCouunt = api.column( 2 ).data().reduce( function (a, b) {
	                   return intVal(a) + intVal(b);
	               }, 0 );


	    	// Total over all pages for Scoreable Count
	            var scoreableCountTotal = api.column( 3 ).data().reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
	 
	       // Total over all pages for Scoreable Pass Count
	            var scoreablePassCountTotal = api.column( 4 ).data().reduce( function (a, b) {
                 return intVal(a) + intVal(b);
             }, 0 );

		      
		   // Average over all pages for Scoreable Pass
		   var scoreablePassPercentAverage = scoreablePassCountTotal / scoreableCountTotal * 100;
		   var scoreablePassPercentAverageHtml = scoreablePassPercentAverage.toFixed(3)  +"%";
		
		   // Total over all pages for Scoreable Fail Count
		        var scoreableFailCountTotal = api.column( 6 ).data().reduce( function (a, b) {
                 return intVal(a) + intVal(b);
             }, 0 );

		  // Average over all pages for Scoreable Pass
		        var scoreableFailPercentAverage = scoreableFailCountTotal / scoreableCountTotal * 100;
		
		  // Total over all pages for Non Scoreable Count
		        var nonScoreableCountTotal = api.column( 8 ).data().reduce( function (a, b) {
                 return intVal(a) + intVal(b);
             }, 0 );

		  // Total over all pages for Does Not Count
		        var doesNotCountTotal = api.column( 10 ).data().reduce( function (a, b) {
                 return intVal(a) + intVal(b);
             }, 0 );

		  // Average over all pages for Scoreable Pass
		        var nonScoreablePercentAverage = nonScoreableCountTotal /(scoreableCountTotal + nonScoreableCountTotal + doesNotCountTotal) * 100;
		
		  // Average over all pages for Scoreable Pass
		        var doesNotCountPercentAverage = doesNotCountTotal /(scoreableCountTotal + nonScoreableCountTotal + doesNotCountTotal) * 100;
		       
		 var $subtotal = $('<tr></tr>', {'class': 'subtotal'});
	     
	      cols.header().each(function(el, colNum){
	        var $td = $('<td></td>');
	        if (colNum === 1) {
		          $td.text( "Total And Average" );
		    } else if (colNum === 2) {
	          $td.text(scoreableTotalCouunt );
	        } else if (colNum === 3) {
	          $td.text(scoreableCountTotal );
	        } else if (colNum === 4) {
	          $td.text(scoreablePassCountTotal );
	        } else if (colNum === 5) {
	          $td.text(scoreablePassPercentAverage.toFixed(2)  +"%" );
	        } else if (colNum === 6) {
	          $td.text(scoreableFailCountTotal );
	        } else if (colNum === 7) {
	          $td.text(scoreableFailPercentAverage.toFixed(2)  +"%" );
	        } else if (colNum === 8) {
	          $td.text(nonScoreableCountTotal );
	        } else if (colNum === 9) {
	          $td.text(nonScoreablePercentAverage.toFixed(2)  +"%" );
	        } else if (colNum === 10) {
	          $td.text(doesNotCountTotal );
	        } else if (colNum === 11) {
	          $td.text(doesNotCountPercentAverage.toFixed(2) +"%" );
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


	//All Pass Score Cards -- allPassScorecardDTId
	function addSubtotalsPassScoreCards(settings){
		  var api = this.api(),
		      rows = api.rows({ page: 'current' }),
		      cols = api.columns({ page: 'current' }),
		      last = null,
		      next = null,
		       
		      agg  = {};
		      

		 var totalRows = api .column( 4 ) .data() . count();
			     
		
		  
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
		           var scoreableTotalCouunt = api.column( 2 ).data().reduce( function (a, b) {
		                   return intVal(a) + intVal(b);
		               }, 0 );


		    	// Total over all pages for Scoreable Count
		            var scoreableCountTotal = api.column( 3 ).data().reduce( function (a, b) {
		                    return intVal(a) + intVal(b);
		                }, 0 );
		 
		       // Total over all pages for Scoreable Pass Count
		            var scoreablePassCountTotal = api.column( 4 ).data().reduce( function (a, b) {
	                 return intVal(a) + intVal(b);
	             }, 0 );

			      
			   // Average over all pages for Scoreable Pass
			   var scoreablePassPercentAverage = scoreablePassCountTotal / scoreableCountTotal * 100;
			   var scoreablePassPercentAverageHtml = scoreablePassPercentAverage.toFixed(3)  +"%";
			
			   
			  // Total over all pages for Non Scoreable Count
			        var nonScoreableCountTotal = api.column( 8 ).data().reduce( function (a, b) {
	                 return intVal(a) + intVal(b);
	             }, 0 );

			  // Total over all pages for Does Not Count
			        var doesNotCountTotal = api.column( 10 ).data().reduce( function (a, b) {
	                 return intVal(a) + intVal(b);
	             }, 0 );

			  // Average over all pages for Scoreable Pass
			        var nonScoreablePercentAverage = nonScoreableCountTotal /(scoreableCountTotal + nonScoreableCountTotal + doesNotCountTotal) * 100;
			
			  // Average over all pages for Scoreable Pass
			        var doesNotCountPercentAverage = doesNotCountTotal /(scoreableCountTotal + nonScoreableCountTotal + doesNotCountTotal) * 100;
			       
			 var $subtotal = $('<tr></tr>', {'class': 'subtotal'});
		     
		      cols.header().each(function(el, colNum){
		        var $td = $('<td></td>');
		        if (colNum === 1) {
			          $td.text( "Total And Average" );
			    } else if (colNum === 2) {
		          $td.text(scoreableTotalCouunt );
		        } else if (colNum === 3) {
		          $td.text(scoreableCountTotal );
		        } else if (colNum === 4) {
		          $td.text(scoreablePassCountTotal );
		        } else if (colNum === 5) {
		          $td.text(scoreablePassPercentAverage.toFixed(2)  +"%" );
		        }  else if (colNum === 6) {
		          $td.text(nonScoreableCountTotal );
		        } else if (colNum === 7) {
		          $td.text(nonScoreablePercentAverage.toFixed(2)  +"%" );
		        } else if (colNum === 8) {
		          $td.text(doesNotCountTotal );
		        } else if (colNum === 9) {
		          $td.text(doesNotCountPercentAverage.toFixed(2) +"%" );
		        }
		        $subtotal.append($td);
		       
		      });
		     
		      $(rows.nodes()).eq(rowNum).after($subtotal );
		    }
		  
		  });
		  
		}

		// for output formatting
		function includeSubtotalsPassScoreCards( data, button, exportObject){
		  var classList = button.className.split(' ');
		  
		  // COPY
		  if (classList.includes('buttons-copy')){
		    
		    data = $('#allPassScorecardDTId').toTSV();
		    exportObject.str = data;
		    exportObject.rows = $('#allPassScorecardDTId').find('tr').length - 1;  // did not include the footer
		    
		  } 
		  // CSV
		  else if (classList.includes('buttons-csv')){
		    
		    data = $('#allPassScorecardDTId').toCSV();
		    
		  }
		  // EXCEL/PDF/PRINT
		  else if (classList.includes('buttons-excel') || classList.includes('buttons-pdf') ||  classList.includes('buttons-print')){
			 
		    // data is actually the object to use for EXCEL/PDF/PRINT
		    var subtotals = [];
		    $('#allPassScorecardDTId tr.subtotal').each(function(){
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

		//All Fail Score Cards -- allFailScorecardDTId
		function addSubtotalsFailScoreCards(settings){
			  var api = this.api(),
			      rows = api.rows({ page: 'current' }),
			      cols = api.columns({ page: 'current' }),
			      last = null,
			      next = null,
			       
			      agg  = {};
			      

			 var totalRows = api .column( 4 ) .data() . count();
				     
			
			  
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
			           var scoreableTotalCouunt = api.column( 2 ).data().reduce( function (a, b) {
			                   return intVal(a) + intVal(b);
			               }, 0 );


			    	// Total over all pages for Scoreable Count
			            var scoreableCountTotal = api.column( 3 ).data().reduce( function (a, b) {
			                    return intVal(a) + intVal(b);
			                }, 0 );
			 
			         // Total over all pages for Scoreable Fail Count
				        var scoreableFailCountTotal = api.column( 6 ).data().reduce( function (a, b) {
		                 return intVal(a) + intVal(b);
		             }, 0 );

				  // Average over all pages for Scoreable Pass
				        var scoreableFailPercentAverage = scoreableFailCountTotal / scoreableCountTotal * 100;
				
				   
				  // Total over all pages for Non Scoreable Count
				        var nonScoreableCountTotal = api.column( 8 ).data().reduce( function (a, b) {
		                 return intVal(a) + intVal(b);
		             }, 0 );

				  // Total over all pages for Does Not Count
				        var doesNotCountTotal = api.column( 10 ).data().reduce( function (a, b) {
		                 return intVal(a) + intVal(b);
		             }, 0 );

				  // Average over all pages for Scoreable Pass
				        var nonScoreablePercentAverage = nonScoreableCountTotal /(scoreableCountTotal + nonScoreableCountTotal + doesNotCountTotal) * 100;
				
				  // Average over all pages for Scoreable Pass
				        var doesNotCountPercentAverage = doesNotCountTotal /(scoreableCountTotal + nonScoreableCountTotal + doesNotCountTotal) * 100;
				       
				 var $subtotal = $('<tr></tr>', {'class': 'subtotal'});
			     
			      cols.header().each(function(el, colNum){
			        var $td = $('<td></td>');
			        if (colNum === 1) {
				          $td.text( "Total And Average" );
				    } else if (colNum === 2) {
			          $td.text(scoreableTotalCouunt );
			        } else if (colNum === 3) {
			          $td.text(scoreableCountTotal );
			        } else if (colNum === 4) {
			          $td.text(scoreableFailCountTotal );
			        } else if (colNum === 5) {
			          $td.text(scoreableFailPercentAverage.toFixed(2)  +"%" );
			        }  else if (colNum === 6) {
			          $td.text(nonScoreableCountTotal );
			        } else if (colNum === 7) {
			          $td.text(nonScoreablePercentAverage.toFixed(2)  +"%" );
			        } else if (colNum === 8) {
			          $td.text(doesNotCountTotal );
			        } else if (colNum === 9) {
			          $td.text(doesNotCountPercentAverage.toFixed(2) +"%" );
			        }
			        $subtotal.append($td);
			       
			      });
			     
			      $(rows.nodes()).eq(rowNum).after($subtotal );
			    }
			  
			  });
			  
			}

			// for output formatting
			function includeSubtotalsFailScoreCards( data, button, exportObject){
			  var classList = button.className.split(' ');
			  
			  // COPY
			  if (classList.includes('buttons-copy')){
			    
			    data = $('#allFailScorecardDTId').toTSV();
			    exportObject.str = data;
			    exportObject.rows = $('#allFailScorecardDTId').find('tr').length - 1;  // did not include the footer
			    
			  } 
			  // CSV
			  else if (classList.includes('buttons-csv')){
			    
			    data = $('#allFailScorecardDTId').toCSV();
			    
			  }
			  // EXCEL/PDF/PRINT
			  else if (classList.includes('buttons-excel') || classList.includes('buttons-pdf') ||  classList.includes('buttons-print')){
				 
			    // data is actually the object to use for EXCEL/PDF/PRINT
			    var subtotals = [];
			    $('#allFailScorecardDTId tr.subtotal').each(function(){
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


			//All Scoreable Score Cards -- scoreableReportDTId
			function addSubtotalsScoreable(settings){
				  var api = this.api(),
				      rows = api.rows({ page: 'current' }),
				      cols = api.columns({ page: 'current' }),
				      last = null,
				      next = null,
				       
				      agg  = {};
				      

				 var totalRows = api .column( 4 ) .data() . count();
					     
				
				  
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
				           var scoreableTotalCouunt = api.column( 2 ).data().reduce( function (a, b) {
				                   return intVal(a) + intVal(b);
				               }, 0 );


				    	// Total over all pages for Scoreable Count
				            var scoreableCountTotal = api.column( 3 ).data().reduce( function (a, b) {
				                    return intVal(a) + intVal(b);
				                }, 0 );

				         // Total over all pages for Scoreable Pass Count
				            var scoreablePassCountTotal = api.column( 4 ).data().reduce( function (a, b) {
			                 return intVal(a) + intVal(b);
			             }, 0 );

					      
					   // Average over all pages for Scoreable Pass
					   var scoreablePassPercentAverage = scoreablePassCountTotal / scoreableCountTotal * 100;
					   var scoreablePassPercentAverageHtml = scoreablePassPercentAverage.toFixed(3)  +"%";
					
				 
				         // Total over all pages for Scoreable Fail Count
					        var scoreableFailCountTotal = api.column( 6 ).data().reduce( function (a, b) {
			                 return intVal(a) + intVal(b);
			             }, 0 );

					  // Average over all pages for Scoreable Pass
					        var scoreableFailPercentAverage = scoreableFailCountTotal / scoreableCountTotal * 100;
					
					   
					  // Total over all pages for Non Scoreable Count
					        var nonScoreableCountTotal = api.column( 8 ).data().reduce( function (a, b) {
			                 return intVal(a) + intVal(b);
			             }, 0 );

					  // Total over all pages for Does Not Count
					        var doesNotCountTotal = api.column( 10 ).data().reduce( function (a, b) {
			                 return intVal(a) + intVal(b);
			             }, 0 );

					  // Average over all pages for Scoreable Pass
					        var nonScoreablePercentAverage = nonScoreableCountTotal /(scoreableCountTotal + nonScoreableCountTotal + doesNotCountTotal) * 100;
					
					  // Average over all pages for Scoreable Pass
					        var doesNotCountPercentAverage = doesNotCountTotal /(scoreableCountTotal + nonScoreableCountTotal + doesNotCountTotal) * 100;
					       
					 var $subtotal = $('<tr></tr>', {'class': 'subtotal'});
				     
				      cols.header().each(function(el, colNum){
				        var $td = $('<td></td>');
				        if (colNum === 1) {
					          $td.text( "Total And Average" );
					    } else if (colNum === 2) {
				          $td.text(scoreableTotalCouunt );
				        } else if (colNum === 3) {
				          $td.text(scoreableCountTotal );
				        } else if (colNum === 4) {
				          $td.text(scoreablePassCountTotal );
				        } else if (colNum === 5) {
				          $td.text(scoreablePassPercentAverage.toFixed(2)  +"%" );
				        } else if (colNum === 6) {
				          $td.text(scoreableFailCountTotal );
				        } else if (colNum === 7) {
				          $td.text(scoreableFailPercentAverage.toFixed(2)  +"%" );
				        }  
				        $subtotal.append($td);
				       
				      });
				     
				      $(rows.nodes()).eq(rowNum).after($subtotal );
				    }
				  
				  });
				  
				}

				// for output formatting
				function includeSubtotalsScoreable( data, button, exportObject){
				  var classList = button.className.split(' ');
				  
				  // COPY
				  if (classList.includes('buttons-copy')){
				    
				    data = $('#scoreableReportDTId').toTSV();
				    exportObject.str = data;
				    exportObject.rows = $('#scoreableReportDTId').find('tr').length - 1;  // did not include the footer
				    
				  } 
				  // CSV
				  else if (classList.includes('buttons-csv')){
				    
				    data = $('#scoreableReportDTId').toCSV();
				    
				  }
				  // EXCEL/PDF/PRINT
				  else if (classList.includes('buttons-excel') || classList.includes('buttons-pdf') ||  classList.includes('buttons-print')){
					 
				    // data is actually the object to use for EXCEL/PDF/PRINT
				    var subtotals = [];
				    $('#scoreableReportDTId tr.subtotal').each(function(){
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
		

	// Used so currency numbers can be added w/o dollar signs in the way
	function parseCurrency(val){
	  val += '';
	  return parseFloat( val.replace(/[$,]/g,'') );
	}

	// Used to display currency again
	function formatCurrency(val) {
	  return parseCurrency(val).toLocaleString('US', {
	    'style': 'currency',
	    'currency': 'USD'
	  });
	}


	jQuery.fn.toCSV = function() {
	  var valueDelim = '"',       // could have a delim option
	      $tableStack = $(this),
	      returnStack = [];
	  
	  function wrapText(text){
	    return [valueDelim, text.replace(/"/g,'""'), valueDelim].join('');
	  }
	  
	  $tableStack.each(function(){
	    var $table = $(this), rows = [];
	    
	    // Iterate over rows
	    // could customize this for showing header, footer, etc
	    $table.find('thead,tbody').find('tr').each(function(){
	      var $row = $(this), row = [];
	      
	      // Iterate over cells
	      $row.find('th,td').each(function(){
	        var $cell = $(this), colspan = $cell.prop('colSpan');
	        
	        // handle cells that span multiple columns
	        if ( colspan && colspan > 1 ){
	          for (var i=1; i<colspan; i++){
	            row[ row.length ] = '""';
	          }
	        }
	        row[ row.length ] = wrapText( $cell.text() );
	        
	      });
	      
	      rows[ rows.length ] = row.join(',');
	    });
	    
	    returnStack[ returnStack.length ] = rows.join('\r\n');
	  });
	  
	  return returnStack.length == 1 ? returnStack[0] : returnStack;
	  
	};

	// Tab Separated Values
	jQuery.fn.toTSV = function() {
	  var valueDelim = '',       // could have a delim option
	      $tableStack = $(this),
	      returnStack = [];
	  
	  $tableStack.each(function(){
	    var $table = $(this), rows = [];
	    
	    // Iterate over rows
	    // could customize this for showing header, footer, etc
	    $table.find('thead,tbody').find('tr').each(function(){
	      var $row = $(this), row = [];
	      
	      // Iterate over cells
	      $row.find('th,td').each(function(){
	        var $cell = $(this), colspan = $cell.prop('colSpan');
	        
	        // handle cells that span multiple columns
	        if ( colspan && colspan > 1 ){
	          for (var i=1; i<colspan; i++){
	            row[ row.length ] = '';
	          }
	        }
	        row[ row.length ] = $cell.text();
	        
	      });
	      
	      rows[ rows.length ] = row.join('\t');
	    });
	    
	    returnStack[ returnStack.length ] = rows.join('\r\n');
	  });
	  
	  return returnStack.length == 1 ? returnStack[0] : returnStack;
	  
	};
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
			                                <label for="name"> Program:</label>
			                                <label for="name"> ${reportsForm.programName}</label>
			                            </div>
			                            <div class="col-lg-4 form-group">
			                                <label for="name"> PCC/Location:</label>
			                                <label for="name"> ${reportsForm.pccLocationName}</label>
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
										            <th style="text-align: left">Total Count</th>
										            <th style="text-align: left">Scoreable Count</th>
										            <th style="text-align: left">Scoreable Pass Count</th>
										            <th style="text-align: left">Scoreable Pass Percent</th> 
										            <th style="text-align: left">Scoreable Fail Count</th>
										            <th style="text-align: left">Scoreable Fail Percent</th>
										            <th style="text-align: left">Non-Scoreable Count</th>										            
										            <th style="text-align: left">Non-Scoreable Percent</th> 
										            <th style="text-align: left">Does Not Count</th>
										            <th style="text-align: left">Does Not Count Percent</th>
										            
										          										           
										        </tr>
										    </thead>
						                    <tbody style="text-align: left">  
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
										            <th style="text-align: left">Total Count</th>
										            <th style="text-align: left">Scoreable Count</th>
										            <th style="text-align: left">Scoreable Pass Count</th>
										            <th style="text-align: left">Scoreable Pass Percent</th> 
										            <th style="text-align: left">Non-Scoreable Count</th>										            
										            <th style="text-align: left">Non-Scoreable Percent</th> 
										            <th style="text-align: left">Does Not Count</th>
										            <th style="text-align: left">Does Not Count Percent</th>	
										            							          										           
										        </tr>
										    </thead>
						                    <tbody style="text-align: left">  
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
										            <th style="text-align: left">Total Count</th>
										            <th style="text-align: left">Scoreable Count</th>
										            <th style="text-align: left">Scoreable Fail Count</th>
										            <th style="text-align: left">Scoreable Fail Percent</th>
										            <th style="text-align: left">Non-Scoreable Count</th>										            
										            <th style="text-align: left">Non-Scoreable Percent</th> 
										            <th style="text-align: left">Does Not Count</th>
										            <th style="text-align: left">Does Not Count Percent</th>				           
										        </tr>
										    </thead>
						                    <tbody style="text-align: left">  
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
										            <th style="text-align: left">Total Count</th>
										            <th style="text-align: left">Scoreable Count</th>
										            <th style="text-align: left">Scoreable Pass Count</th>
										            <th style="text-align: left">Scoreable Pass Percent</th>										            
										            <th style="text-align: left">Scoreable Fail Count</th> 
										            <th style="text-align: left">Scoreable Fail Percent</th>				           
										        </tr>
										    </thead>
						                    <tbody style="text-align: left">  
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
										            <th style="text-align: left">Total Count</th>
										            <th style="text-align: left">Scoreable Count</th>
										            <th style="text-align: left">Scoreable Pass Count</th>
										            <th style="text-align: left">Scoreable Pass Percent</th>				           
										        </tr>
										    </thead>
						                    <tbody style="text-align: left">  
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
										            <th style="text-align: left">Total Count</th>
										            <th style="text-align: left">Scoreable Count</th>
										            <th style="text-align: left">Scoreable Fail Count</th>
										            <th style="text-align: left">Scoreable Fail Percent</th>
										            			           
										        </tr>
										    </thead>
						                    <tbody style="text-align: left">  
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
										            <th style="text-align: left">Total Count</th>
										            <th style="text-align: left">Non-Scoreable Count</th>
										            <th style="text-align: left">Non-Scoreable Percent</th>
										           	           
										        </tr>
										    </thead>
						                    <tbody style="text-align: left">  
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
										            <th style="text-align: left">Total Count</th>
										            <th style="text-align: left">Does Not Count</th>
										            <th style="text-align: left">Does Not Count Percent</th>
										                      
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