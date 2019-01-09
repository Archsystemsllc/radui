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
<title>MAC Assignment Report</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/Comrad_icon.png" />
<link rel="stylesheet" href="/resources/demos/style.css" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.2.2/css/buttons.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />


<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/jquery-1.11.1.min.js"></script> 
<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.min.js"></script>


<script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.html5.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.print.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {	
	$("#dialog-confirm").hide();
	
	var reportTitle = 'MAC Assignment For: ${currentMonthYear}';
	
	var macAssignmentData =eval('${MAC_ASSIGNMENT_REPORT}');
	var pccContactPerson = '${pccContactPersonMap}';

	var action = $('#action').val();

	var buttonCommon = {
	        exportOptions: {
	            format: {
	                body: function ( data, row, column, node ) {
	                	 return $(data).is("div") ?
	                                $(data).find('input').val():
	                                data;
	                }
	            }
	        }
	    };

	
	
	var macAssignmentDataTable = $('#macAssignmentTableId').DataTable( {
		"aaData": macAssignmentData,
		"aoColumns": [
		{ "mData": "macName"},
		{ "mData": "jurisdictionName"},
		{ "mData": "programName"},
		{ "mData": "createdMethod"},
		{ "mData": "plannedCalls"},
		{ "mData": "macJurisdictionProgramCompleted"},		
		{ "mData": "assignedCallsForCindy"},
		{ "mData": "assignedCallsForLydia"},
		{ "mData": "assignedCallsForJaneene"}
		],	
		 "columnDefs": [ 
		        { 
		           "render" : function(data, type, row) {
		        	   var linkData = "";
			        if(data != null) {		
				        if(action == "view") {
				        	linkData = "<div><input readonly type='text' value='"+data+"' size='6'></input></div>";	
					    } else {
					    	linkData = "<div><input type='text' class='numbersOnly' id='row_"+row.id+"' name='row_"+row.id+"' value='"+data+"' size='6'></input></div>";	
						}		        
				        	
				    } else {
				    		linkData = "<div><input type='text' class='numbersOnly' value='' readonly size='6'></input></div>";
					}					
					return linkData;
		        },
			   "targets" : 6
			   },
			   { 
		           "render" : function(data, type, row) {
		        	   var linkData = "";
			        if(data != null) {				        
			        	 if(action == "view") {
					        	linkData = "<div><input readonly type='text' value='"+data+"' size='6'></input></div>";	
						    } else {
						    	linkData = "<div><input type='text' class='numbersOnly' id='row_"+row.id+"'  name='row_"+row.id+"'  value='"+data+"' size='6'></input></div>";	
							}		   
				    } else {
				    		linkData = "<div><input type='text' class='numbersOnly' value='' readonly size='6'></input></div>";
					}					
					return linkData;
		        },
			   "targets" : 7
			   },
			  
			   { 
		           "render" : function(data, type, row) {
		        	   var linkData = "";
			        if(data != null) {				        
			        	 if(action == "view") {
					        	linkData = "<div><input readonly type='text' value='"+data+"' size='6'></input></div>";	
						    } else {
						    	linkData = "<div><input type='text' class='numbersOnly' id='row_"+row.id+"'  name='row_"+row.id+"'  value='"+data+"' size='6'></input></div>";	
							}		   
				    } else {
				    		linkData = "<div><input type='text' class='numbersOnly' value='' readonly size='6'></input></div>";
					}					
					return linkData;
		        },
			   "targets" : 8
			   }
			 ],	   
		dom: 'Bfrtip',	
	    
	     buttons: [
	            $.extend( true, {}, buttonCommon, {
	                extend: 'copyHtml5',	               
		            title: reportTitle
	            } ),
	            $.extend( true, {}, buttonCommon, {
	                extend: 'excelHtml5',	                
		            title: reportTitle
	            } ),
	            $.extend( true, {}, buttonCommon, {
	                extend: 'pdfHtml5',
	                title: reportTitle,	                
	            } )
	        ],
	        
		  "paging" : false,
		  "pageLength" : 100,
		  "ordering" : true		   
	});

	$('button[id=save]').click(function() {	
        var data = macAssignmentDataTable.rows().data();
        var eachRecord = "";
        var finalDataSet = new Array();
       
        var monthYear = "${currentMonthYear}";
      
	
		data.each(function (value, index) {
		
			eachRecord = "";
			
			 var idValue = value.id;
			 if(idValue == "") {
				 idValue = "NoId";
			 }

			 var rifkinCindyInputValue = macAssignmentDataTable.cell(index,6).nodes().to$().find('input').val();
			
			 if(rifkinCindyInputValue == "") {
				 rifkinCindyInputValue = "NoInput";
			 }

			 var riveraLydiaInputValue = macAssignmentDataTable.cell(index,7).nodes().to$().find('input').val();
			
			 if(riveraLydiaInputValue == "") {
				 riveraLydiaInputValue = "NoInput";
			 }

			 var galtinJaneeneInputValue = macAssignmentDataTable.cell(index,8).nodes().to$().find('input').val();
			
			 if(galtinJaneeneInputValue == "") {
				 galtinJaneeneInputValue = "NoInput";
			 }
			
		     eachRecord = value.macName +","+value.jurisdictionName
		     				+","+value.programName+","+idValue+","+rifkinCindyInputValue
		     				+","+riveraLydiaInputValue+"," +galtinJaneeneInputValue
		     				+"," +value.macId+"," +value.jurisdictionId+"," +value.programId;		    
		     
		     finalDataSet[index] = eachRecord;
		 }); 

		 /*  $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/save-macassignmentlist",                    
	             {monthNumber: monthYear, finalDataSet: finalDataSet}, function(){
	            	
	         
	     })
	     .complete(function() { $("#reportsForm").submit(); }); */

		 $.ajax({ 
			      type: "GET",
			      dataType: "json",
			      data : {monthNumber: monthYear, finalDataSet: finalDataSet},
			      url : "${pageContext.request.contextPath}/${SS_USER_FOLDER}/save-macassignmentlist",			      
			     success: function(){	 
			    	
			    	 window.location.href = "${pageContext.request.contextPath}/${SS_USER_FOLDER}/macassignmentlist";
			     },
			     failure: function () {
			    	 alert("Update Failed, Contact Administrator");
			     },
			     complete: function () {
			    	 
			    	 window.location.href = "${pageContext.request.contextPath}/${SS_USER_FOLDER}/macassignmentlist";
				 }
		});
		
   });
 
		
	jQuery('.numbersOnly').keyup(function () { 
	    this.value = this.value.replace(/[^0-9\.]/g,'');
	});
	
	//Back Button Functionality
	$('#close1,#close2').click(function(e) {	
		 e.preventDefault();		
          $("#dialog-confirm" ).dialog({
              resizable: false,
              height: "auto",
              width: 400,
              modal: true,	              
              buttons: {
                "Yes": function() {
              		$( this ).dialog("close");
              		window.location.href = "${pageContext.request.contextPath}/${SS_USER_FOLDER}/macassignmentlist";
                },
                Cancel: function() {                    
                	$( this ).dialog("close"); 
                }
              }
        });
     }); 
});
</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div id="dialog-confirm" title="Close MAC Assignment Confirmation?">
  		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Do you want to close the MAC Assignment Screen without saving?</p>
	</div>
	<table id="mid">
		<form:form method="POST" modelAttribute="macAssignmentObjectForm" class="form-signin" action="${pageContext.request.contextPath}/${SS_USER_FOLDER}/save-macassignmentlist" id="reportsForm" >
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">					
						
						<div class="content">
								 			
								<div class="table-users" style="width: 98%">
									<div class="header">MAC Assignment Screen</div>
									 <input type="hidden" id="action" value='${action}'/>
									
								<div class="row " style="margin-top: 10px">
									<div class="col-lg-12 col-lg-offset-1 form-container">
				                	</div>
				                </div>
								
								<div class="row" id="allScoreCardMainDiv">	
					             <div class="col-lg-12 col-lg-offset-1 form-container">					                
					                    <h2>MAC Assignment for: ${currentMonthYear}</h2> 
					                    			                   
				                   <div class="row" id="allScoreCardDiv">
			                            <div class="col-lg-10 form-group">
			                           <c:if test="${action != 'view'}">
				                   <table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td>
										<span><button class="btn btn-primary" id="save" type="button"  title="Select Save button to Save MAC Asiignment details">Save & Continue</button></span>
									
									
										<!-- <span><button class="btn btn-primary" id="confirm" type="button" title="Select update button to update Scorecard details">Submit</button></span>
									 -->
									<span><button class="btn btn-primary" id="close1" type="button" title="Select Close button to navigate to MAC Assignment List ">Close</button></span></td>
							       </tr>
								</table>				
					                </c:if>
			                        	
			                            <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="macAssignmentTableId" style="width: 95%">
						                    <thead>
										        <tr>
										            <th style="text-align: left" rowspan="2">MAC</th>
										            <th style="text-align: left" rowspan="2">Jurisdiction</th>
										            <th style="text-align: left" rowspan="2">Progam</th>
										             <th style="text-align: left" rowspan="2">Created</th> 
										            <th style="text-align: left" rowspan="2">Planned</th>
										           
						                           		<th style="text-align: left" rowspan="2">Completed</th>
						                       
										           
										            <th style="text-align: center" colspan="3">Assigned Calls</th>
										            
										           
										        </tr>
										        <tr>
										        	<th style="text-align: left">Rifkin,Cindy</th>
										            <th style="text-align: left">Rivera,Lydia</th>										            
										            <th style="text-align: left">Galtin,Janeene</th>
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
						                </table> 
						                <c:if test="${action == 'view'}">
			                            	
			                           </c:if>
						               
						                </div>
						                </div>
				                     
				                     
				                  </div>
				                  </div>
				                  <c:if test="${action != 'view'}">
				                   <table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td>
										<span><button class="btn btn-primary" id="save" type="button"  title="Select Save button to Save MAC Asiignment details">Save & Continue</button></span>
									
									
										<!-- <span><button class="btn btn-primary" id="confirm" type="button" title="Select update button to update Scorecard details">Submit</button></span>
									 -->
									<span><button class="btn btn-primary" id="close2" type="button" title="Select Close button to navigate to MAC Assignment List ">Close</button></span></td>
							       </tr>
								</table>				
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