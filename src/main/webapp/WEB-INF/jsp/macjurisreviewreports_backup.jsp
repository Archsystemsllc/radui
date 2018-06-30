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
<script src="https://cdn.datatables.net/rowgroup/1.0.2/js/dataTables.rowGroup.min.js"></script>


<script type="text/javascript">
$(document).ready(function() {	

	// Example: add subtotals by letter of first name
	function addSubtotals(settings){
	  var api = this.api(),
	      rows = api.rows({ page: 'current' }),
	      cols = api.columns({ page: 'current' }),
	      last = null,
	      next = null,
	      agg  = {},
	      sum_colNum = 5;
	  
	 /*  // only generate subtotals on initial display and when first column is sorted, but not other columns
	  if ( api.order().length && api.order()[0][0] !== 0 )
	    return; */
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
            .column( 2 )
            .data()
            .reduce( function (a, b) {
                return intVal(a) + intVal(b);
            }, 0 );
		
        var $subtotal = $('<tr></tr>', {'class': 'subtotal'});
        cols.header().each(function(el, colNum){
	        var $td = $('<td></td>');
	        if (colNum === 2) {
	          $td.text( scoreableCountTotal );
	        } else if (colNum === 0) {
	          $td.text( ["Total and Average"].join('') );
	        }
	        $subtotal.append($td);
	      });
       
        $('#allScoreCardId').find( 'tbody' ).append( $subtotal).draw();
	  
	}

	// for output formatting
	function includeSubtotals( data, button, exportObject){

	  var classList = button.className.split(' ');
	  alert(classList);
	  // COPY
	  if (classList.includes('buttons-copy')){
	    
	    data = $('#allScoreCardId').toTSV();
	    exportObject.str = data;
	    exportObject.rows = $('#allScoreCardId').find('tr').length - 1;  // did not include the footer
	    
	  } 
	  // CSV
	  else if (classList.includes('buttons-excel')){
	    
	    data = $('#allScoreCardId').toCSV();
	    
	  } 
	  // PDF/PRINT
	  else if (classList.includes('buttons-pdf') ||  classList.includes('buttons-print')){
	    
	    // data is actually the object to use for PDF/PRINT
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

	
	var reportTitle = '${ReportTitle}';
	var messageOnTop = 'MAC:${reportsForm.macName}'+'  '+'Jurisdiction:${reportsForm.jurisdictionName}\n'
	+'Report From Date:${reportsForm.fromDateString}'+'  '+'Report To Date:${reportsForm.toDateString}';

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
		{ "mData": "qamEndDate"},
		{ "mData": "monthYear"},
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
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 8
		   },
		   { 
	           "render" : function(data, type, row) {
				var linkData = data +"%";
				return linkData;
	        },
		   "targets" : 10
		   }		
		 ], 
		 dom: '<lif<t>pB>',
	     buttons: [
	         {
	             extend: 'copyHtml5',
	             messageTop: messageOnTop,
	             title: reportTitle,
	             footer: true
	         },
	         {
	             extend: 'excelHtml5',
	             messageTop: messageOnTop,
	             title: reportTitle,
	             footer: true,
	            
 		      },
	         {
	             extend: 'pdfHtml5',
	             messageTop: messageOnTop,
	             orientation : 'landscape',
	             pageSize : 'LEGAL',
	             title: reportTitle,
	             footer: true
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 20,
		  "ordering" : true,
		  rowGroup: {
	            startRender: null,
	            endRender: function ( rows, group ) {
	                var salaryAvg = rows
	                    .data()
	                    .pluck(5)
	                    .reduce( function (a, b) {
	                        return a + b;
	                    }, 0) / rows.count();
	                salaryAvg = $.fn.dataTable.render.number(',', '.', 0, '$').display( salaryAvg );
	 
	                var ageAvg = rows
	                    .data()
	                    .pluck(3)
	                    .reduce( function (a, b) {
	                        return a + b*1;
	                    }, 0) / rows.count();
	 
	                return $('<tr/>')
	                    .append( '<td colspan="3">Averages for '+group+'</td>' )
	                    .append( '<td>'+ageAvg.toFixed(0)+'</td>' )
	                    .append( '<td/>' )
	                    .append( '<td>'+salaryAvg+'</td>' );
	            },
	            dataSrc: 12
	        }
	        /* ,
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
	                .column( 2 )
	                .data()
	                .reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
	 
	            // Update footer
	            $( api.column( 2 ).footer() ).html( scoreableCountTotal );

	            // Total over all pages for Scoreable Pass Count
	            var scoreablePassCountTotal = api
                .column( 3 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
 
		        // Update footer
		        $( api.column( 3 ).footer() ).html( scoreablePassCountTotal );

		        // Average over all pages for Scoreable Pass
		        var scoreablePassPercentAverage = scoreablePassCountTotal / scoreableCountTotal * 100;
		        var scoreablePassPercentAverageHtml = scoreablePassPercentAverage.toFixed(2)  +"%";
		
		        // Update footer
		        $( api.column( 4 ).footer() ).html(scoreablePassPercentAverageHtml);

		     	// Total over all pages for Scoreable Fail Count
		        var scoreableFailCountTotal = api
                .column( 5 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
 
		        // Update footer
		        $( api.column( 5 ).footer() ).html( scoreableFailCountTotal );

		        // Average over all pages for Scoreable Pass
		        var scoreableFailPercentAverage = scoreableFailCountTotal / scoreableCountTotal * 100;
		
		        // Update footer
		        $( api.column( 6 ).footer() ).html(scoreableFailPercentAverage.toFixed(2)  +"%");

		     	// Total over all pages for Non Scoreable Count
		        var nonScoreableCountTotal = api
                .column( 7 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );

		        // Total over all pages for Does Not Count
		        var doesNotCountTotal = api
                .column( 9 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
 
		        // Update footer
		        $( api.column( 7 ).footer() ).html( nonScoreableCountTotal );

		        // Average over all pages for Scoreable Pass
		        var nonScoreablePercentAverage = nonScoreableCountTotal /(scoreableCountTotal + nonScoreableCountTotal + doesNotCountTotal) * 100;
		
		        // Update footer
		        $( api.column( 8 ).footer() ).html(nonScoreablePercentAverage.toFixed(2)  +"%");

		   
 
		        // Update footer
		        $( api.column( 9 ).footer() ).html( doesNotCountTotal );

		        // Average over all pages for Scoreable Pass
		        var doesNotCountPercentAverage = doesNotCountTotal /(scoreableCountTotal + nonScoreableCountTotal + doesNotCountTotal) * 100;
		        // Update footer
		        $( api.column( 10 ).footer() ).html(doesNotCountPercentAverage.toFixed(2) +"%");

	        } */
	});
	
	allScoreCardDataTable.columns.adjust().draw();
	
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
				var linkData = data +"%";
				return linkData;
	        },
	        "targets" : 8
		   }
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
	             messageTop: messageOnTop,
	             title: reportTitle,
	             orientation : 'landscape',
	             pageSize : 'LEGAL'
	         }	        
	     ],
		  "paging" : true,
		  "pageLength" : 20,
		  "ordering" : true,
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
	                .column( 2 )
	                .data()
	                .reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
	 
	            // Update footer
	            $( api.column( 2 ).footer() ).html( scoreableCountTotal );

	            // Total over all pages for Scoreable Pass Count
	            var scoreablePassCountTotal = api
              .column( 3 )
              .data()
              .reduce( function (a, b) {
                  return intVal(a) + intVal(b);
              }, 0 );

		        // Update footer
		        $( api.column( 3 ).footer() ).html( scoreablePassCountTotal );

		        // Average over all pages for Scoreable Pass
		        var scoreablePassPercentAverage = api
		            .column( 4 )
		            .data()
		            .reduce( function (a, b) {
		                return intVal(a) + intVal(b);
		            }, 0) / totalRows;
		
		        // Update footer
		        $( api.column( 4 ).footer() ).html(scoreablePassPercentAverage.toFixed(0) +"%");

		     	// Total over all pages for Scoreable Fail Count
		        var scoreableFailCountTotal = api
              .column( 5 )
              .data()
              .reduce( function (a, b) {
                  return intVal(a) + intVal(b);
              }, 0 );

		        // Update footer
		        $( api.column( 5 ).footer() ).html( scoreableFailCountTotal );

		        // Average over all pages for Scoreable Pass
		        var scoreableFailPercentAverage = api
		            .column( 6 )
		            .data()
		            .reduce( function (a, b) {
		                return intVal(a) + intVal(b);
		            }, 0) / totalRows;
		
		        // Update footer
		        $( api.column( 6 ).footer() ).html(scoreableFailPercentAverage.toFixed(0) +"%");

		     	// Total over all pages for Non Scoreable Count
		        var nonScoreableCountTotal = api
              .column( 7 )
              .data()
              .reduce( function (a, b) {
                  return intVal(a) + intVal(b);
              }, 0 );

		        // Update footer
		        $( api.column( 7 ).footer() ).html( nonScoreableCountTotal );

		        // Average over all pages for Scoreable Pass
		        var nonScoreablePercentAverage = api
		            .column( 8 )
		            .data()
		            .reduce( function (a, b) {
		                return intVal(a) + intVal(b);
		            }, 0) / totalRows;
		
		        // Update footer
		        $( api.column( 8 ).footer() ).html(nonScoreablePercentAverage.toFixed(0) +"%");

		     // Total over all pages for Does Not Count
		        var doesNotCountTotal = api
              .column( 9 )
              .data()
              .reduce( function (a, b) {
                  return intVal(a) + intVal(b);
              }, 0 );

		        // Update footer
		        $( api.column( 9 ).footer() ).html( doesNotCountTotal );

		        // Average over all pages for Scoreable Pass
		        var doesNotCountPercentAverage = api
		            .column( 10 )
		            .data()
		            .reduce( function (a, b) {
		                return intVal(a) + intVal(b);
		            }, 0) / totalRows;
		
		        // Update footer
		        $( api.column( 10 ).footer() ).html(doesNotCountPercentAverage.toFixed(0) +"%");

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
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/ScoreableFail'>"+data+"</a></span>";
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
		  "pageLength" : 20,
		  "ordering" : true,
	});

	failScorecardDataTable.columns.adjust().draw();

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
	});

	scoreableReportDataTable.columns.adjust().draw();
	

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
	});
	scoreablePassReportDataTable.columns.adjust().draw();
	

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
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/ScoreableFail'>"+data+"</a></span>";
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
		  "pageLength" : 20,
		  "ordering" : true,
	});

	scoreableFailReportTable.columns.adjust().draw();
	
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
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/Non-Scoreable'>"+data+"</a></span>";
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
		  "pageLength" : 20,
		  "ordering" : true,
	});
	nonScoreableDataTable.columns.adjust().draw();	

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
				var linkData = "<span><a href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/mac-jur-report-drilldown/"+data+"/"+row.jurisdictionName+"/Does Not Count'>"+data+"</a></span>";
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
		  "pageLength" : 20,
		  "ordering" : true,
	});
	doesNotCountDataTable.columns.adjust().draw();
	
	//Compliance Data Table Code
	var complianceReportData =eval('${complianceReportList}');
	var complianceReportDataTable = $('#complianceReportDTId').DataTable( {
		"aaData": complianceReportData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "monthYear"},
		{ "mData": "complianceStatus"}
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
	complianceReportDataTable.columns.adjust().draw();
	
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
		  "searching": false,		 
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
		    
		  }
		     
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
					                  	
				                   <c:if test="${AllScoreCardReport_All == true}">
				                        
				               
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="allScoreCardDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="allScoreCardId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: center">MAC</th>
										            <th style="text-align: center">Jurisdiction</th>
										            <th style="text-align: center">Scoreable Count</th>
										            <th style="text-align: center">Scoreable Pass Count</th>
										            <th style="text-align: center">Scoreable Pass Percent</th> 
										            <th style="text-align: center">Scoreable Fail Count</th>
										            <th style="text-align: center">Scoreable Fail Percent</th>
										            <th style="text-align: center">Non-Scoreable Count</th>										            
										            <th style="text-align: center">Non-Scoreable Percent</th> 
										            <th style="text-align: center">Does Not Count</th>
										            <th style="text-align: center">Does Not Count Percent</th>
										            <th style="text-align: center">QAM Start Date</th>
										            <th style="text-align: center">QAM End Date</th>
										            <th style="text-align: center">QAM End Date</th>		
										          										           
										        </tr>
										    </thead>
						                    <tbody style="text-align: center">  
						                    </tbody>
							              <!--  <tfoot>
									            <tr>
									            	<th></th>
									                <th  style="text-align:right">Total and Average:</th>
									                <th></th>
									                <th></th>
									                <th></th>
									                <th></th>
									                <th></th>
									                <th></th>
									                <th></th>
									                <th></th>
									                <th></th>
									                <th>NA</th>
									                <th>NA</th>
									            </tr>
									        </tfoot> -->
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
										            <th style="text-align: center">MAC</th>
										            <th style="text-align: center">Jurisdiction</th>
										            <th style="text-align: center">Scoreable Count</th>
										            <th style="text-align: center">Scoreable Pass Count</th>
										            <th style="text-align: center">Scoreable Pass Percent</th> 
										            <th style="text-align: center">Non-Scoreable Count</th>										            
										            <th style="text-align: center">Non-Scoreable Percent</th> 
										            <th style="text-align: center">Does Not Count</th>
										            <th style="text-align: center">Does Not Count Percent</th>	
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
				                     
				                     
				                  <c:if test="${AllScoreCardReport_Fail == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="allFailScorecardDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="allFailScorecardDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: center">MAC</th>
										            <th style="text-align: center">Jurisdiction</th>
										            <th style="text-align: center">Scoreable Fail Count</th>
										            <th style="text-align: center">Scoreable Fail Percent</th>
										            <th style="text-align: center">Non-Scoreable Count</th>										            
										            <th style="text-align: center">Non-Scoreable Percent</th> 
										            <th style="text-align: center">Does Not Count</th>
										            <th style="text-align: center">Does Not Count Percent</th>	
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
				                     
				                     <c:if test="${ScoreableReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="scoreableReportDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="scoreableReportDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: center">MAC</th>
										            <th style="text-align: center">Jurisdiction</th>
										            <th style="text-align: center">Scoreable Count</th>
										            <th style="text-align: center">Scoreable Pass Count</th>
										            <th style="text-align: center">Scoreable Pass Percent</th>										            
										            <th style="text-align: center">Scoreable Fail Count</th> 
										            <th style="text-align: center">Scoreable Fail Percent</th>
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
				                     
				                   <c:if test="${ScoreablePassReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="scoreablePassReportDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="scoreablePassReportDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: center">MAC</th>
										            <th style="text-align: center">Jurisdiction</th>
										            <th style="text-align: center">Scoreable Count</th>
										            <th style="text-align: center">Scoreable Pass Count</th>
										            <th style="text-align: center">Scoreable Pass Percent</th>
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
				                     
				                      <c:if test="${ScoreableFailReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="scoreableFailReportDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="scoreableFailReportDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: center">MAC</th>
										            <th style="text-align: center">Jurisdiction</th>
										            <th style="text-align: center">Scoreable Count</th>
										            <th style="text-align: center">Scoreable Fail Count</th>
										            <th style="text-align: center">Scoreable Fail Percent</th>
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
				                     
				                      <c:if test="${NonScoreableReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="nonScoreableDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="nonScoreableDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: center">MAC</th>
										            <th style="text-align: center">Jurisdiction</th>
										            <th style="text-align: center">Non-Scoreable Count</th>
										            <th style="text-align: center">Non-Scoreable Percent</th>
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
				                     
				                     <c:if test="${DoesNotCountReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="doesNotCountDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="doesNotCountDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: center">MAC</th>
										            <th style="text-align: center">Jurisdiction</th>
										            <th style="text-align: center">Does Not Count</th>
										            <th style="text-align: center">Does Not Count Percent</th>
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
				                     
				                     <c:if test="${ComplianceReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="complianceReportDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="complianceReportDTId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: center">MAC</th>
										            <th style="text-align: center">Jurisdiction</th>
										            <th style="text-align: center">Month, Year</th>
										            <th style="text-align: center">Compliance Status</th>										           		           
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
				                     
				                     <c:if test="${QaspReport == true}">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   	<div class="row" id="qaspReportDiv">
			                            <div class="col-lg-10 form-group">			                            	
			                           
			                            <%-- <display:table class="display hover stripe cell-border " id="qaspReportListRow" name="qaspReportList" 
											requestURI="${pageContext.request.contextPath}/${SS_USER_FOLDER}/getMacJurisReport" style="width:95%;font-size:85%;" export="true" pagesize="25" sort="list" varTotals="myTotalValue">
											<display:column property="monthYear" title="" sortable="true" style="text-align:center;"/>
											<display:column property="scorableCount" title="A/B " sortable="true" style="text-align:center;" total="true"/>
											<display:column property="hhhScorableCount" title="HHH" sortable="true" style="text-align:center;" total="true"/>
											<display:column property="scorablePass" title="A/B " sortable="true" style="text-align:center;" total="true"/>
											<display:column property="hhhScorablePass" title="HHH" sortable="true" style="text-align:center;" total="true"/>
											<display:column property="scorableFail" title="A/B " sortable="true" style="text-align:center;" total="true"/>
											<display:column property="hhhScorableFail" title="HHH" sortable="true" style="text-align:center;" total="true"/>
											<display:caption media="html">
										        <strong>QASP Report</strong>
										      </display:caption>
										      <display:caption media="excel pdf csv">QASP Report</display:caption>
											<display:footer media="pdf html csv excel  ">
											<tr>
											 <td style="text-align: center">SubTotals</td>
											   <td style="text-align: center"><fmt:formatNumber type = "number" maxIntegerDigits = "3" value = "${myTotalValue.column2}" /> </td>
											   <td style="text-align: center"><fmt:formatNumber type = "number" maxIntegerDigits = "3" value = "${myTotalValue.column3}" /></td>
											   <td style="text-align: center"><fmt:formatNumber type = "number" maxIntegerDigits = "3" value = "${myTotalValue.column4}" /></td>
											   <td style="text-align: center"><fmt:formatNumber type = "number" maxIntegerDigits = "3" value = "${myTotalValue.column5}" /></td>
											   <td style="text-align: center"><fmt:formatNumber type = "number" maxIntegerDigits = "3" value = "${myTotalValue.column6}" /></td>
											   <td style="text-align: center"><fmt:formatNumber type = "number" maxIntegerDigits = "3" value = "${myTotalValue.column7}" /></td>
											 </tr>
											 <tr>
											 <td style="text-align: center">Totals</td>
											   <td style="text-align: center" colspan="2"><fmt:formatNumber type = "number" maxIntegerDigits = "3" value = "${myTotalValue.column2 + myTotalValue.column3}" /></td>
											   <td style="text-align: center" colspan="2"><fmt:formatNumber type = "number" maxIntegerDigits = "3" value = "${myTotalValue.column4 + myTotalValue.column5}" /></td>
											   <td style="text-align: center" colspan="2"><fmt:formatNumber type = "number" maxIntegerDigits = "3" value = "${myTotalValue.column6 + myTotalValue.column7}" /></td>
											  
											 </tr>
											 <tr>
											 <td style="text-align: center" colspan="7">
											 	Average Quality Rate: ${(myTotalValue.column4 + myTotalValue.column5)/(myTotalValue.column2 + myTotalValue.column3)*100} %
											 </td>
											 </tr>
											</display:footer>
											
											<display:setProperty name="export.pdf.include_header" value="true" />
											<display:setProperty name="export.excel.include_header" value="true" />
											<display:setProperty name="export.csv.include_header" value="true" />
											<display:setProperty name="export.pdf" value="true" />
											<display:setProperty name="export.excel.filename" value="QASP_Report.xls" />
											<display:setProperty name="export.pdf.filename" value="QASP_Report.pdf" />
											<display:setProperty name="export.csv.filename" value="QASP_Report.csv" />
											
											<display:setProperty name="decorator.media.html"  value="org.displaytag.sample.decorators.HtmlTotalWrapper" />
										    <display:setProperty name="decorator.media.pdf"   value="org.displaytag.sample.decorators.ItextTotalWrapper" />
										    <display:setProperty name="decorator.media.rtf"   value="org.displaytag.sample.decorators.ItextTotalWrapper" />
										    <display:setProperty name="decorator.media.excel" value="org.displaytag.sample.decorators.HssfTotalWrapper" />
											  <display:setProperty name="export.pdf.filename" value="QASP_Report.pdf"/>
											
											  <display:setProperty name="export.rtf.filename" value="QASP_Report.rtf"/>
											  
											  <display:setProperty name="export.excel.filename" value="QASP_Report.xls"/>
											 
											
										</display:table> --%>
			                            			                        
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