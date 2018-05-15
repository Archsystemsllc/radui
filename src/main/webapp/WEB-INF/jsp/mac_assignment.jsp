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

<script type="text/javascript">
$(document).ready(function() {	
	
	
	var reportTitle = '${ReportTitle}';

	var allScorecardData =eval('${MAC_ASSIGNMENT_REPORT}');
	var pccContactPerson = '${pccContactPersonMap}';
	alert(pccContactPerson);
	var allScoreCardDataTable = $('#allScoreCardId').DataTable( {
		"aaData": allScorecardData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "program"},
		{ "mData": "plannedCalls"},
		{ "mData": "createdMethod"},
		{ "mData": "assignedCalls"},
		{ "mData": "qmName"}
		],	
		 "columnDefs": [ 
		        { 
		           "render" : function(data, type, row) {
					var linkData = "<span><input type='text'></input></span>";
					return linkData;
		        },
			   "targets" : 5
			   },
			   { 
				   "render" : function(data, type, row) {
						
						var linkData = '<span><select><option value="">Select</option>';
						//alert(pccContactPerson);
						$.each(pccContactPerson, function (key,obj) {
							linkData += '<option value="'+key+'">'+obj+'</option>';
		  	  	    			  	  	    		
		  	  	    	});  	   
		  	  	    	linkData +="</select></span>"
		  	  	    	return linkData;
			        },
				   "targets" : 6
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
		  "paging" : false,
		  "pageLength" : 40,
		  "ordering" : true,
	});


	$("select#macIdK").change(function(){
		var userRole = $('#userRole').val();
		
		if ((userRole != "MAC Admin") && (userRole != "MAC User")){
			
	        $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectJuris",                    
	                {macId: $(this).val(), multipleInput: false}, function(data){
	           
	             $("#jurisdictionK").get(0).options.length = 0;	           
	  	      	 $("#jurisdictionK").get(0).options[0] = new Option("---Select Jurisdiction---", "");
	  	  	    	$.each(data, function (key,obj) {
	  	  	    		$("#jurisdictionK").get(0).options[$("#jurisdictionK").get(0).options.length] = new Option(obj, key);
	  	  	    		
	  	  	    });  	   
           });
		}
    });
	

	$('button[id=save]').click(function() {	
		alert("Inside Submit"+allScoreCardDataTable);
        //var data = allScoreCardDataTable.$('input, select').serialize();
        var data = allScoreCardDataTable.rows().data();
        
		 data.each(function (value, index) {
		     console.log('Data in index: ' + index + ' is: ' + value.macName + value[5]);
		     
		    
		 }); 

		 console.log(allScoreCardDataTable.cell(0,5).nodes().to$().find('input').val())
	     console.log(allScoreCardDataTable.cell(1,6).nodes().to$().find('select').val())  
        return false;
    } );

	
	
});


</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>

	<table id="mid">
		<form:form method="POST" modelAttribute="reportsForm" class="form-signin" action="#" id="reportsForm" >
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
									<div class="header">MAC Assignment Screen</div>
									
								<div class="row " style="margin-top: 10px">
									<div class="col-lg-12 col-lg-offset-1 form-container">
				                    <%-- <h2>"${reportName}"</h2>  --%>
				                    <!-- <p> Please provide your feedback below: </p> -->
				                    
				                   
				                	</div>
				                </div>
									
								
								 <div class="row" id="allScoreCardMainDiv">	
					             <div class="col-lg-12 col-lg-offset-1 form-container">
					                
					                    <h2>${ReportTitle}</h2> 
					                  	
				                   
				                        
				               
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   <div class="row" id="allScoreCardDiv">
			                            <div class="col-lg-10 form-group">
			                        
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="allScoreCardId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">MAC</th>
										            <th style="text-align: left">Jurisdiction</th>
										            <th style="text-align: left">Progam</th>
										            <th style="text-align: left">Planned</th>
										            <th style="text-align: left">Created</th> 
										            <th style="text-align: left">Assigned</th>
										            <th style="text-align: left">QM Name</th>
										           
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
						                </table> 
						                </div>
						                </div>
				                     
				                     
				                  </div>
				                  </div>
					                
					            </div>  <!-- Main Row Div -->
									 <table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td>
										<span><button class="btn btn-primary" id="save" type="button"  title="Select Save button to Save Scorecard details">Save & Continue</button></span>
									
									
										<span><button class="btn btn-primary" id="submit" type="button" title="Select update button to update Scorecard details">Submit</button></span>
									
									<span><button class="btn btn-primary" id="close2" type="button" title="Select Close button to navigate to Scorecard List ">Close</button></span></td>
							       </tr>
							</table>				
						
						</div> <!-- Content Div -->
					</div>
				</td>
			</tr>
		</form:form>
	</table>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>