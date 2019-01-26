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
	var messageOnTop = 'MAC:${reportsForm.macName}'+'  '+'Jurisdiction:${reportsForm.jurisdictionName}\n'
	+'Report From Date:${reportsForm.fromDateStringMonthYear}'+'  '+'Report To Date:${reportsForm.toDateStringMonthYear}';
	var reportTitle = '${ReportTitle}\n'+messageOnTop;
	

		/* $('#qaspReportListRow > thead').prepend('<tr media="all"><th colspan="1" style="text-align: left">Month</th><th colspan="2" style="text-align: left"># of QAM Scorecards Completed</th><th colspan="2" style="text-align: left"># of QAM Scorecards Passed</th><th colspan="2" style="text-align: left"># of QAM Scorecards Failed</th></tr>');
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
	    "columnDefs": [ 	        
		   {
               "targets": [ 6 ],
               className: 'dt-body-left'
           }
		 ], 
		 dom: 'Bfrtip',	
	     buttons: [
	         {
	             extend: 'copy',
	             messageTop: messageOnTop,
	             title: reportTitle,
			     customize: includeSubtotals
	         },
	         {
	             extend: 'excel',
	             messageTop: messageOnTop,
	             title: reportTitle,
			     customize: function (xlsx) {
			         /* console.log(xlsx);
			         //alert("test");
			         var sheet = xlsx.xl.worksheets['sheet1.xml'];
			         //alert("Test1");
			         var downrows = 3;
			         var clRow = $('row', sheet);
			         //update Row
			         clRow.each(function () {
			             var attr = $(this).attr('r');
			             var ind = parseInt(attr);
			             ind = ind + downrows;
			             $(this).attr("r",ind);
			         });
			  
			         // Update  row > c
			         $('row c ', sheet).each(function () {
			             var attr = $(this).attr('r');
			             var pre = attr.substring(0, 1);
			             var ind = parseInt(attr.substring(1, attr.length));
			             ind = ind + downrows;
			             $(this).attr("r", pre + ind);
			         });
			  
			         function Addrow(index,data) {
			             msg='<row r="'+index+'">'
			             for(i=0;i<data.length;i++){
			                 var key=data[i].k;
			                 var value=data[i].v;
			                 msg += '<c t="inlineStr" r="' + key + index + '" s="42">';
			                 msg += '<is>';
			                 msg +=  '<t>'+value+'</t>';
			                 msg+=  '</is>';
			                 msg+='</c>';
			             }
			             msg += '</row>';
			             return msg;
			         }
			  
			         //insert
			         var r1 = Addrow(1, [{ k: 'A', v: 'ColA' }, { k: 'B', v: '' }, { k: 'C', v: '' }]);
			         var r2 = Addrow(2, [{ k: 'A', v: '' }, { k: 'B', v: 'ColB' }, { k: 'C', v: '' }]);
			         var r3 = Addrow(3, [{ k: 'A', v: '' }, { k: 'B', v: '' }, { k: 'C', v: 'ColC' }]); */
			         
			         //sheet.childNodes[0].childNodes[1].innerHTML = sheet.childNodes[0].childNodes[1].innerHTML +r1 + r2 + r3;
			     }
	            
 		      },
	         {
	             
 		    	 extend: 'pdf',
			     title: reportTitle,
			     	customize: function(doc) {
			     		doc.defaultStyle.fontSize = 10;
			     		doc.styles.tableHeader.fontSize = 10; 
			       	},     
			        customizeData: includeSubtotals			       
	         	        
	         }	        
	     ],
	      drawCallback: addSubtotals,
		  "paging" : false,
		  "pageLength" : 100,
		  "searching": false,
		  "ordering" : true,
		  "language": {
		      "emptyTable": "No data available"
		    }		     
	});
	qaspScorecardDataTable.columns.adjust().draw(); 

	// Example: add subtotals by letter of first name
	
	function addSubtotals(settings){
	  var api = this.api(),
	      rows = api.rows({ page: 'current' }),
	      cols = api.columns({ page: 'current' }),
	      last = null,
	      next = null,	       
	      agg  = {};
	      var totalScoreCards ;
	  	  var totalPassScoreCards ;
	  	  var totalFailScoreCards;
	  	  var finalAverage;

	  	  

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
           var scoreableCountTotal = api.column( 1 ).data().reduce( function (a, b) {
                   return intVal(a) + intVal(b);
               }, 0 );
        
           // Total over all pages for Scoreable Pass Count
           var scoreableHhhCountTotal = api.column( 2 ).data().reduce( function (a, b) {
	                  return intVal(a) + intVal(b);
	       }, 0 );
	       
	     	// Total over all pages for Scoreable Fail Count
	        var scoreablePassCountTotal = api.column( 3 ).data().reduce( function (a, b) {
	                  return intVal(a) + intVal(b);
	              }, 0 );

	       // Total over all pages for Scoreable Fail Count
	        var scoreableHhhPassCountTotal = api.column( 4 ).data().reduce( function (a, b) {
	                  return intVal(a) + intVal(b);
	              }, 0 );
            
	     	// Total over all pages for Non Scoreable Count
	        var scoreableFailCountTotal = api.column( 5).data().reduce( function (a, b) {
	                  return intVal(a) + intVal(b);
	              }, 0 );

	       

	        // Average over all pages for Scoreable Pass
	        var scoreableFailHhhCountTotal = api.column( 6 ).data().reduce( function (a, b) {
	                return intVal(a) + intVal(b);
	            }, 0) ;
	
	      
	        
			totalScoreCards = scoreableCountTotal + scoreableHhhCountTotal;
			totalPassScoreCards = scoreablePassCountTotal + scoreableHhhPassCountTotal;
			totalFailScoreCards = scoreableFailCountTotal + scoreableFailHhhCountTotal;
			
	       

	     // Update footer
	      if(totalScoreCards == 0) {
	    	 finalAverage = 'Not Available In Selected Dates';
		  } else {
			  var average = (totalPassScoreCards / totalScoreCards) * 100;
			  finalAverage = average.toFixed(0) +"%";

		  } 
		  var $subtotal = $('<tr></tr>', {'class': 'subtotal'});
		  
	      cols.header().each(function(el, colNum){
	        var $td = $('<td align="left"></td>');
	        if (colNum === 0) {
		          $td.text( ["Sub-Totals"].join('') );
		    } else if (colNum === 1) {
	          $td.text(scoreableCountTotal );
	        } else if (colNum === 2) {
	          $td.text(scoreableHhhCountTotal );
	        } else if (colNum === 3) {
	          $td.text(scoreablePassCountTotal);
	        } else if (colNum === 4) {
	          $td.text(scoreableHhhPassCountTotal );
	        } else if (colNum === 5) {
	          $td.text(scoreableFailCountTotal);
	        } else if (colNum === 6) {
	          $td.text(scoreableFailHhhCountTotal );
	        } 
	        $subtotal.append($td);
	        
	       
	      });
	     
	      $(rows.nodes()).eq(rowNum).after($subtotal );
	      
	     $('#qaspReportDTId > tbody:last').append('<tr class="subtotal" ><td >Summary </td><td align="right">Total Completed</td><td align="left">'+totalScoreCards+'</td><td align="right">Total Passed</td><td align="left">'+totalPassScoreCards+'</td><td align="right">Total Failed</td><td align="left">'+totalFailScoreCards+'</td></tr>'
				  +'<tr class="subtotal" ><td ></td><td></td><td align="right">Average</td><td align="left">'+finalAverage+'</td><td ></td><td ></td><td ></td></tr>');    
	    
	    }
	  });
	  
	  
	}

	// for output formatting
	function includeSubtotals( data, button, exportObject){

	  var classList = button.className.split(' ');
	  
	  // COPY
	  if (classList.includes('buttons-copy')){
	    
	    data = $('#qaspReportDTId').toTSV();
	    exportObject.str = data;
	    exportObject.rows = $('#qaspReportDTId').find('tr').length - 1;  // did not include the footer
	    
	  } 
	  // CSV
	  else if (classList.includes('buttons-csv')){
	    
	    data = $('#qaspReportDTId').toCSV();
	    
	  }
	  // EXCEL/PDF/PRINT
	  else if (classList.includes('buttons-excel') || classList.includes('buttons-pdf') ||  classList.includes('buttons-print')){

		  var headers = [];
		  $('#qaspReportDTId th.header').each(function(){
			  var $row = $(this),
	          row_index = $row.index(), 
	          row = $row.find('td,th').map(function(){return $(this).text();}).get();
			  headers[ headers.length ] = {rowNum: row_index, data: row };
		  });


		  for (var i=0, n=headers.length; i<n; i++){
		      var header = headers[i];
		      data.body.splice(headers.rowNum, 0, header.data);
		    }
	    // data is actually the object to use for EXCEL/PDF/PRINT
	    var subtotals = [];
	    $('#qaspReportDTId tr.subtotal').each(function(){
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
			                                <label for="name"> Report From Date:</label>
			                                 <label for="name"> ${reportsForm.fromDateStringMonthYear}</label>
			                            </div>
			                            <div class="col-lg-4 form-group">
			                                <label for="name"> Report To Date:</label>
			                                 <label for="name"> ${reportsForm.toDateStringMonthYear}</label>
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
										            <th style="text-align: center" colspan="7" class="header">QASP Report</th>									            
										            									                 
										        </tr>
										        <tr>
										            <th style="text-align: center" colspan="7" class="header"></th>									            
										            									                 
										        </tr>
										         <tr>
										         <th style="text-align: left" >Month</th>
										        	<th style="text-align: left"># of QAM Scorecards Completed(A/B)</th>
										            <th style="text-align: left"># of QAM Scorecards Completed(HHH)</th>
										            <th style="text-align: left"># of QAM Scorecards Passed(A/B)</th>
										            <th style="text-align: left"># of QAM Scorecards Passed(HHH)</th>
										            <th style="text-align: left"># of QAM Scorecards Failed(A/B)</th>
										            <th style="text-align: left"># of QAM Scorecards Failed(HHH)</th>
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