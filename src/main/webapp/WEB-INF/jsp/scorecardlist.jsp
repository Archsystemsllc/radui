<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>QAM - Scorecard List</title>
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

<style type="text/css">
	.red {  color:#cd2026;  }
</style>

<script type="text/javascript">
	function resetFields() {
		
		
		$('#macId').prop('selectedIndex',0);
		$('#jurId').prop('selectedIndex',0);
		$('#jurisIdReportSearchString').find('option:selected').removeAttr('selected');
		$('#callResult').prop('selectedIndex',0);
		$('#scorecardType').prop('selectedIndex',0);
		$('#qamFullName').val("");
		$('#filterFromDateString').val("");
		$('#filterToDateString').val("");
		
	}
	$(document).ready(function() {

		//Required Fields Logic
		$('.required').each(function(){
		       $(this).prev('label').after("<span class='red'><strong>*</strong></span>");
		});

		var userRole = $('#userRole').val();		
		var maxAllowedDate = 0;	
		if ((userRole == "MAC Admin") || (userRole == "MAC User") ){
			var maxAllowedDate = new Date();			
			var day = maxAllowedDate.getDate();
			
			if(day < 16) {
				maxAllowedDate.setMonth(maxAllowedDate.getMonth() - 1);				
			}
			
			maxAllowedDate.setDate(15);			
		}
		
		 $('#filterFromDateString').datepicker({
			maxDate : maxAllowedDate,
    		onSelect: function(selected) {
    			$("#filterToDateString").datepicker("option","minDate", selected)
    		}
		});

		$('#filterToDateString').datepicker({
			maxDate : maxAllowedDate,
    		onSelect: function(selected) {
    			$("#filterFromDateString").datepicker("option","maxDate", selected)
    		}
		});
		
		$("select#macId").change(function(){

			var userRole = $('#userRole').val();			
			if ((userRole != "MAC Admin") && (userRole != "MAC User")){
			
			var macIdValue = $(this).val();
            $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectJuris",                    
                    {macId: $(this).val(), multipleInput: false}, function(data){
               
                 $("#jurId").get(0).options.length = 0;	           
      	      	 $("#jurId").get(0).options[0] = new Option("ALL", "ALL");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#jurId").get(0).options[$("#jurId").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
			}
        });


		$("#jurId").change(function(){

			var userRole = $('#userRole').val();			
			if ((userRole != "MAC Admin") && (userRole != "MAC User")){

				var macIdValue = $('#macId').val();
				
				var selectedJurisdiction =""; 
				
				
				$("#jurId :selected").each(function() {
					 selectedJurisdiction+=($(this).attr('value'))+",";
				}); 
				
	            $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectProgram",{macId: macIdValue,jurisId: selectedJurisdiction}, function(data){
	                
	                 $("#programId").get(0).options.length = 0;	           
	      	      	 $("#programId").get(0).options[0] = new Option("---Select Program---", "");
	      	      	 $("#programId").get(0).options[1] = new Option("ALL", "0");
	      	  	    	$.each(data, function (key,obj) {
	      	  	    		$("#programId").get(0).options[$("#programId").get(0).options.length] = new Option(obj, key);
	      	  	    		
	      	  	    	});  	   
	               });
			} 
        });

		//Back Button Functionality
		$('#back').click(function(e) {	
			window.location.href = "${pageContext.request.contextPath}/${SS_USER_FOLDER}/getMacJurisReportFromSession";			
	    }); 

		var scoreCardType = $("select#scorecardType").val();
        if (scoreCardType=="Scoreable") {           
	           	$('#callResultDiv').show();
	           	$("#callResult").attr('required',true);
        } else if (scoreCardType=="Non-Scoreable") {           	
	           	$('#callResultDiv').hide();
	           	$("#callResult").removeAttr('required'); 
        } else if (scoreCardType=="Does Not Count") {                        	
        		$('#callResultDiv').hide();
        		$("#callResult").removeAttr('required'); 
        }

		
	});
</script>

<script type="text/javascript">
$(document).ready(function(){
	var data =eval('${scoreCardList}');
	
	var role = $('#userRole').val();
	
	var reportTitle = 'Scorecard List';
	var messageOnTop = 'Scorecard List From Date:${scorecard.filterFromDateString}'+'  '+'Scorecard List To Date:${scorecard.filterToDateString}';
	var reportSearchString = '${ReportSearchString}';
	var scoreCardListTable = $('#scoreCardLists').DataTable( {
	"aaData": data,
	"aoColumns": [
	{ "mData": "macName"},
	{ "mData": "jurisdictionName"},
	{ "mData": "macCallReferenceNumber"},	
	{ "mData": function (data, type, dataToSet) {
		var returnData='';
		if (role == 'MAC Admin' || role == 'MAC User') {
			returnData = data.qamId
		} else {
			returnData = data.qamFullName
		}
        return returnData;
    }},
    { "mData": "callMonitoringDateString"},
	{ "mData": "qamStartdateTimeString"},
	{ "mData": "scorecardType"},
	{ "mData": "finalScoreCardStatus"},
	{ "mData": "id"}
	],
    "columnDefs": [ 
        { 
           "render" : function(data, type, row) {
			var linkData = "";
			if (reportSearchString !=null && reportSearchString !='') {
				
				linkData = "<span><a class='action-icons c-pending'	href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/view-scorecard/"+data+"/"+reportSearchString+"' title='View'>View</a></span>";
				
				
			} else {
				linkData = "<span><a class='action-icons c-pending'	href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/view-scorecard/"+data+"/null' title='View'>View</a></span>";
			}
			if (role == 'Administrator' || role == 'Quality Manager' || role == 'Quality Monitor' ) {
				if (reportSearchString !=null && reportSearchString !='') {
					
					linkData = linkData+ "<span><a class='action-icons c-edit'	href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/edit-scorecard/"+data+"/"+reportSearchString+"' title='Edit'>Edit</a></span>";
				} else {					
					linkData = linkData+ "<span><a class='action-icons c-edit'	href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/edit-scorecard/"+data+"/null' title='Edit'>Edit</a></span>";	
				}
			}
                       
            return linkData;		
             
        },
	   "targets" : 8
	   },
	 ],
	 //dom: 'Bfrtip',
	 //dom: '<"top"i>fprtl<"bottom"B><"clear">',
	dom: 'Bfrtip',
     buttons: [
         {
             extend: 'copyHtml5',
             exportOptions: {
                 columns: [ 0, 1, 2, 3, 4, 5, 6, 7]
             },
             messageTop: messageOnTop,
             title: reportTitle,
         },
         {
             extend: 'excelHtml5',
             exportOptions: {
                 columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
             },
             messageTop: messageOnTop,
             title: reportTitle,
         },
         {
             extend: 'pdfHtml5',
             exportOptions: {
                 columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
             },
             orientation : 'landscape',
             pageSize : 'LEGAL',
             messageTop: messageOnTop,
             title: reportTitle
         }
         //,'colvis'
     ],
	  "paging" : true,
	  "pageLength" : 20,
	   "ordering" : true,
	});
});
</script>
<script type="text/javascript">
$(function(){
	$("select#scorecardType").change(function(){
		 var scoreCardType = $(this).val();
		 if (scoreCardType=="Scoreable") {           
	           	$('#callResultDiv').show();
	           	$("#callResult").attr('required',true);
          } else if (scoreCardType=="Non-Scoreable") {           	
	           	$('#callResultDiv').hide();
	           	$("#callResult").removeAttr('required'); 
          } else if (scoreCardType=="Does Not Count") {                        	
          		$('#callResultDiv').hide();
          		$("#callResult").removeAttr('required'); 
          }
   });
	
});
</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div role="main">
	<table id="mid">
		<form:form method="POST" modelAttribute="scorecard" class="form-signin"	action="${pageContext.request.contextPath}/${SS_USER_FOLDER}/scorecardlist/false"
			id="scorecardfilterForm">
			<tr>
				<td style="vertical-align: top" >

					<div id="updates" class="boxed">
						<div class="table-users" style="width: 99%">
						<div class="container" style="width: 99%">

							
								<div class="header">List of Scorecards</div>
							<!-- 	Removed Blank Space - 508 -->
							 <input type="hidden" id="userRole" value='${SS_LOGGED_IN_USER_ROLE}'/>
								<c:if test="${ScoreCardFilter == true}">
									
									<div class="row " style="margin-top: 10px">
										<div class="col-md-12 col-md-offset-0 form-container">
											<div class="row">
											<div class="col-md-8 col-md-offset-0 form-group">
												<h2>Scorecard Search Filters</h2>
												</div>
											</div>
											<div class="row">
											
												<div class="col-md-4 col-md-offset-1 form-group">
													<label for="name"> MAC:</label>
													<form:select path="macId" class="form-control required"
														required="true" title="Select one Medicare Administrative Contractor ID from the List">
														<form:option value="0" label="ALL" />
														<form:options items="${macIdMapEdit}" />
													</form:select>
												</div>
												<div class="col-md-4 col-md-offset-0 form-group">
													<label for="name"> Jurisdiction:</label>
													<form:select path="jurisIdReportSearchString" class="form-control required"
														id="jurId" required="true" title="Select one or multiple Jurisdiction from the List" multiple="true">
														<form:option value="ALL" label="ALL" />
														<form:options items="${jurisMapEdit}" />
													</form:select>
												</div>
											</div>
											<div class="row" id="programPccLocDiv">
					                            <div class="col-md-4 col-md-offset-1 form-group">
					                                <label for="name"> Program:</label>
												<form:select path="programId" class="form-control required" id="programId" required="true" title="Select one Program from the List">
												   <form:option value="0" label="ALL"/>
												   <form:options items="${programMapEdit}" />
												</form:select> 	
					                            </div>
					                            <sec:authorize access="hasAuthority('Administrator') or hasAuthority('Quality Manager') or hasAuthority('Quality Monitor') or hasAuthority('CMS User')">
											
					                           <div class="col-md-4 col-md-offset-0 form-group">
													<label for="name"> QM Name:</label>
													<form:input type="text" class="form-control"
														id="qamFullName" name="qamFullName" path="qamFullName" title="Enter Qualtiy Monitor Name/ID" />
												
												</div>
					                            </sec:authorize>
					                        </div>
											
											<div class="row">
												<sec:authorize access="hasAuthority('Administrator') or hasAuthority('Quality Manager') or hasAuthority('CMS User')">
												<div class="col-md-4 col-md-offset-1 form-group">
													<label for="name">Scorecard Type:</label>
													<form:select class="form-control  required" id="scorecardType"
														path="scorecardType" title="Select one Score Card Type from the List" required="true" >
														<form:option value="ALL" label="ALL" />
														<form:option value="Scoreable" />
														<form:option value="Non-Scoreable" />
														<form:option value="Does Not Count" />
													</form:select>
												</div>
												<div class="col-md-4 col-md-offset-0 form-group">
													<label for="name">Status:</label>
													<form:select class="form-control  required" id="callResult"
														path="callResult" title="Select one Status from the List" required="true" >
														<form:option value="ALL" label="ALL" />
														<form:option value="Pass" />
														<form:option value="Fail" />
													</form:select>
												</div>
												</sec:authorize>
												<sec:authorize access="hasAuthority('MAC User')  or hasAuthority('MAC Admin')">
												
												<div class="col-md-4 col-md-offset-1 form-group">
													<label for="name">Status:</label>
													<form:select class="form-control  required" id="callResult"
														path="callResult" title="Select one Status from the List" required="true" >
														<form:option value="ALL" label="ALL" />
														<form:option value="Pass" />
														<form:option value="Fail" />
													</form:select>
												</div>
												</sec:authorize>
											</div>
											
											<div class="row">
												<div class="col-md-4 col-md-offset-1 form-group">
													<label for="name"> From Date:</label>

													<form:input type="text" class="form-control"
														path="filterFromDateString" title="Choose From Date from the Calendar" readonly="true" />
												</div>
												<div class="col-md-4 col-md-offset-0 form-group">
													<label for="email"> To Date:</label>
													<form:input type="text" class="form-control"
														path="filterToDateString" title="Choose To Date from the Calendar" readonly="true" />
												</div>
											</div>
											<div class="row ">
												<div class="col-md-6 col-md-offset-1 form-group">
											
													<span><button class="btn btn-primary" id="filter" type="submit" title="Select Filter button to Filter the results">Filter</button></span> 
													<span><button class="btn btn-primary" onclick="resetFields();" type="button" title="Select Reset button to Reset the results">Reset</button></span>
													<sec:authorize access="hasAuthority('Administrator') or hasAuthority('Quality Monitor') or hasAuthority('Quality Manager') ">
													<span><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/new-scorecard" title="Select Add Scorecard button to add new scorecard"><button class="btn btn-primary" id="addScorecard" type="button">Add Scorecard</button></a></span> 
													</sec:authorize>
												</div>
										
											</div>
										</div>
									</div>
									
							</c:if>
							<c:if test="${ReportFlag == true}">
							<div class="row " style="margin-top: 10px">
								<div class="col-md-12 col-md-offset-0 form-container">
									<div class="col-md-6 col-md-offset-0 form-group">
										<button class="btn btn-primary" id="back" type="button">Back</button>
									</div>
								</div>
								<br/>
							</div>
							</c:if>		
								
							<div class="row" id="scorecardlistdatatablediv">
					                <div class="col-md-12 col-md-offset-0 form-container">
					                   
					                    <!-- <p> Please provide your feedback below: </p> -->	
					                  <div class="row" style="width:90%;">			                   
					                   <c:if test="${not empty success}">
					                 	<div class="successblock" ><spring:message code="${success}"></spring:message>
					                    </div>
					                 </c:if>
					                  <c:if test="${not empty error}">
					                 	<div class="errorblock" ><spring:message code="${success}"></spring:message>
					                    </div>
					                 </c:if>
					                
					           			</div>
				               
				                         
				                 <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl table table-bordered" id="scoreCardLists" style="width: 100%">
							      	<thead>
								        <tr>
								            <th style="text-align: left">MAC</th>
								            <th style="text-align: left">Jurisdiction</th>
								            <th style="text-align: left">MAC Call Reference ID</th>
								            <th style="text-align: left">QM Name/QM ID</th>
								            <th style="text-align: left">Call Monitoring Date</th>
								            <th style="text-align: left">QM Start Date/Time</th> 
								            <th style="text-align: left">Scorecard Type</th>
								            <th style="text-align: left">Status</th>
								            <th style="text-align: left">Actions</th>
								           
								        </tr>
								    </thead>
				                    <tbody>  
				                    </tbody>
				                   
				                </table> 
				                                
				                    
				                </div>
				            </div>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</form:form>
	</table>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>