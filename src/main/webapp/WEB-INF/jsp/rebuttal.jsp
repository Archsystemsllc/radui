 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>QAM - ScoreCard</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/adda_ico.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/button.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui-timepicker-addon.css" />

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>

<!-- CSS for Bootstrap -->
<!-- <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"></link> -->
<!-- JQuery -->
 <script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.33.3/es6-shim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/systemjs/0.19.20/system-polyfills.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery-ui-timepicker-addon.js"></script>


<script type="text/javascript">

	$(document).ready(function () {

		$("#dialog-confirm").hide();
		$("#complete-confirm").hide();
		$("#failureReasonsDiv").hide();
		$("#accuracyCallFailureReasonDiv").hide();
		$("#completenessCallFailureReasonDiv").hide();
		$("#privacyCallFailureReasonDiv").hide();
		$("#customerSkillsCallFailureReasonDiv").hide();
		$("#rebuttalResult").hide();
		
    	var username="qamadmin";
  	   	var password="123456";

    	$('#alertMsg').text('');

    	var id=${rebuttal.id};
		
    	var failureBlockDisplay = false;

    	//alert("reason::"+$("#accuracyCallFailureReason").val())

    	if (id != 0) {
    		              
    		if($("#accuracyCallFailureReason").val() != null ) {
				failureBlockDisplay = true;				
				$("#accuracyCallFailureReasonDiv").show();
			}

			if( $("#completenessCallFailureReason").val() != null) {
				failureBlockDisplay = true;				
				$("#completenessCallFailureReasonDiv").show();
			}

			if( $("#privacyCallFailureReason").val() != null) {
				failureBlockDisplay = true;
				$("#privacyCallFailureReasonDiv").show();
			}

			if($("#customerSkillsCallFailureReason").val() != null) {
				failureBlockDisplay = true;
				$("#customerSkillsCallFailureReasonDiv").show();
			}

			if(failureBlockDisplay == true) {
				$("#failureReasonsDiv").show();
			}
    	}
    	
    	var completeFormFlag =  $('#rebuttalCompleteFlag').val();
		//alert("Rebuttal Form Flag:"+completeFormFlag);
    	if(completeFormFlag == "Yes") {
        	//alert("Inside Disabe");
    		$("#rebuttalForm :input").prop("disabled", true);
        }
    	
    	$("#csrFullName" ).autocomplete({
    		source: function(request, response) {
    			var autocompleteContext = request.term;
    			var selectedMac = $('select[name=macId]').val();
    			var selectedJurisdiction = $('#jurId :selected').text();
    			var selectedProgram = $('#programId :selected').text();
    			$.ajax({
	    			url:"${WEB_SERVICE_URL}csrListNames",
	    			contentType: "application/json; charset=utf-8",
	    			headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
	    			dataType: "json",
	    			data: { term:autocompleteContext, macIdS: selectedMac, jurisdictionS: selectedJurisdiction,programS: selectedProgram},
    				success: function(data) {
    					response(data);
    				}
    			});
    		}
    	});
	});

	$(function(){
		$("select#macReferenceId").change(function(){
            $.getJSON("${pageContext.request.contextPath}/admin/selectScoreCardFromMacRef",                    
                    {scoreCardId: $(this).val()}, function(data){     

                    var failureBlockDisplay = false;                
              		
              		$("#csrFullName").val(data.csrFullName);
              		
              		$("#failureReason").val(data.failReason);
              		$("#qamFullName").val(data.qamFullName);
              		$("#callTime").val(data.callTime);
              		$("#callDate").val(data.callMonitoringDate);
              		
              		$("#macCallReferenceNumber").val(data.macCallReferenceNumber);
              		$("#macId").val(data.macId);
              		$("#jurisId").val(data.jurId);
              		$("#programId").val(data.programId);

					if(data.accuracyCallFailureReason != "") {
						failureBlockDisplay = true;
						$("#accuracyCallFailureReason").val(data.accuracyCallFailureReason);
						$("#accuracyCallFailureReasonDiv").show();
					}

					if(data.completenessCallFailureReason != "") {
						failureBlockDisplay = true;
						$("#completenessCallFailureReason").val(data.completenessCallFailureReason);
						$("#completenessCallFailureReasonDiv").show();
					}

					if(data.privacyCallFailureReason != "") {
						failureBlockDisplay = true;
						$("#privacyCallFailureReason").val(data.privacyCallFailureReason);
						$("#privacyCallFailureReasonDiv").show();
					}

					if(data.customerSkillsCallFailureReason != "") {
						failureBlockDisplay = true;
						$("#customerSkillsCallFailureReason").val(data.customerSkillsCallFailureReason);
						$("#customerSkillsCallFailureReasonDiv").show();
					}

					if(failureBlockDisplay == true) {
						$("#failureReasonsDiv").show();
					}
              		
                    $("#pccLocationId").get(0).options.length = 0;	           
          	      	$("#pccLocationId").get(0).options[0] = new Option("Select PCC/Location", "");
          	  	    	$.each(data.pccLocationMap, function (key,obj) {
          	  	    		$("#pccLocationId").get(0).options[$("#pccLocationId").get(0).options.length] = new Option(obj, key);
          	  	    		
          	  	    });  	   
                           
              });
        });

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
	              		window.location.href= '${pageContext.request.contextPath}/admin/rebuttallist';          	 	
	                },
	                Cancel: function() {                    
	                	$( this ).dialog("close"); 
	                }
	              }
            });
	     }); 

		 $('#rebuttalCompleteFlag').change(function(e) {
			 if($(this).val()=="Yes") {
				 $("#complete-confirm" ).dialog({
		              resizable: false,
		              height: "auto",
		              width: 400,
		              modal: true,	              
		              buttons: {
		                "Complete": function() {
			                if($("#rebuttalResultTemp").val()=="") {
			                	$('#confirm-msg').text('Please select Result');
				            } else {
				            	$("#rebuttalResult").val($("#rebuttalResultTemp").val());
				            	alert($("#rebuttalResult").val());
				            	$( this ).dialog("close");
					        }               	 	
		                },
		                Cancel: function() {                    
		                	$( this ).dialog("close"); 
		                	$("#rebuttalCompleteFlag").val("");	                	
		                }
		              }
	            });

			}	
	          
	     }); 
	});
</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div id="dialog-confirm" title="Close Scorecard Confirmation?">
  		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Do you want to close the Rebuttal form without saving?</p>
	</div>
	<div id="complete-confirm" title="Complete Scorecard Confirmation?">
			                             <form >
								  		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Please select the Rebuttal Status</p>
								  		 <div id="confirm-msg" style="color: red;"></div>
								  		 <label for="rebuttalResult">Result:</label>
			                                <select id="rebuttalResultTemp" name="rebuttalResultTemp" required="true">											 
											  	<option value="">Select</option>
  												<option value="Scoring Modified">Scoring Modified</option>
  												<option value="Scoring Unchanged">Scoring Unchanged</option>
  												<option value="CMS ELevated">CMS Elevated</option>
											</select> 
										</form>
			                        	</div>
	

	<table id="mid">
		<form:form method="POST" modelAttribute="rebuttal" class="form-signin" action="${pageContext.request.contextPath}/admin/saveOrUpdateRebuttal" id="rebuttalForm">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
							
							<div class="table-users" style="width: 80%">
								<div class="header">Save/Update Rebuttal</div>	
								<table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td><span><button class="btn btn-primary" id="create">Save/Update</button></span>
									<span><button class="btn btn-primary" id="close1" type="button">Close</button></span></td>
							       </tr>
							     </table>						
      							 <div class="row " style="margin-top: 10px">
				                <div class="col-md-8 col-md-offset-1 form-container">
				                   
				                   <form:hidden path="macId" />
				                   <form:hidden path="programId" />
								 	<form:hidden path="jurisId" />
								 	<form:hidden path="id" />
								 	<form:hidden path="rebuttalResult" />
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="macReferenceId"> MAC Call Reference ID:</label>
			                                <c:if test="${rebuttal.id == 0}">
			                                <form:select path="macReferenceId" class="form-control" id="macReferenceId" required="true">	
			                                	<form:option value="" >---Select Mac Call Reference ID---</form:option>										   	
											  	<form:options items="${macReferenceFailedList}" />
											</form:select> 
											<form:input type="hidden" name="macCallReferenceNumber" path="macCallReferenceNumber" />
											</c:if>
											<c:if test="${rebuttal.id != 0}">
			                                
											<form:input name="macCallReferenceNumber" path="macCallReferenceNumber" readonly="true" class="form-control"/>
											<form:input type="hidden" name="macReferenceId" path="macReferenceId" />
											</c:if>
			                            </div>
			                           <div class="col-sm-6 form-group">
			                                <label for="contactPerson">PCC Contact Person:</label>
			                                <form:select path="contactPerson" class="form-control" id="contactPerson" required="true">
											   	<form:option value="" label="---Select Contact---"/>
											  	<form:option value="Contact 1" />
											  	<form:option value="Contact 2" />
											  	<form:option value="Contact 3" />
											  	<form:option value="Contact 4" />
											</form:select> 
			                            </div>
			                        </div>
			                         <div class="row">
			                             <div class="col-sm-6 form-group">
			                                <label for="name"> PCC/Location:</label>
			                                <form:select path="pccLocationId" class="form-control required" id="pccLocationId" >
			                                	<form:option value="" label="---Select PCC/Location---"/>
			                                	<form:options items="${programMapEdit}" />
											</form:select> 
			                            </div>
			                           <div class="col-sm-6 form-group">
			                                <label for="name"> CSR Full Name:</label>
			                                <form:input class="form-control" type = "text" name = "csrFullName" path="csrFullName" readonly="true"/>
			                            </div>
			                        </div>
			                        
			                        <div class="row">
			                              <div class="col-sm-6 form-group">
			                                <label for="name"> Date Posted:</label>
			                                <form:input type = "text" class="form-control" id="datePosted" name = "datePosted" path="datePosted" readonly="true"/>
			                            </div>
			                         <div class="col-sm-6 form-group">
			                                <label for="name"> QM Name/ID:</label>
			                                <form:input type = "text" class="form-control" id="qamFullName" name = "qamFullName" path="qamFullName" readonly="true"/>
			                                <form:input type = "hidden" name = "id" path="id" />
			                            </div>
			                        </div>
			                        
			                        <div class="row">
			                              
			                          <div class="col-sm-6 form-group">
			                                <label for="name"> Call Time:</label>
			                                <form:input class="form-control" type = "text" name = "callTime" path="callTime" readonly="true"/>
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Call Monitoring Date:</label>
			                                <form:input type = "text" class="form-control" id="callDate" name = "callDate" path="callDate" readonly="true"/>
			                            </div>
			                        </div>
			                        
			                        <div class="row">
			                              
			                            <div class="col-lg-6 form-group">
			                             <label for="email"> Call Category:</label>
			                              <form:select path="callCategory" class="form-control required" id="callCategory" required="true">
											   	<form:option value="" label="--- Select Call Category---"/>
											  	<form:options items="${callCategoryMap}" />										  	
											</form:select> 			                                
			                         	</div>
			                         	<div class="col-lg-6 form-group">
			                             <label for="email"> Rebuttal Call Category:</label>
			                              <form:select path="callCategory" class="form-control required" id="callCategory" required="true">
											   	<form:option value="" label="--- Select Rebuttal Call Category---"/>
											   	<form:option value="1" label="QAM"/>
											   	<form:option value="2" label="EDI"/>
											</form:select> 			                                
			                         	</div>
			                         </div>
			                         <div class="row">
			                         	<c:if test="${rebuttal.id != 0}">
			                         	 <div class="col-sm-6 form-group">
			                                <label for="rebuttalCompleteFlag">Complete:</label>
			                                <form:select path="rebuttalCompleteFlag" class="form-control"  required="true">
											   	<form:option value="" label="---Select---"/>
											  	<form:option value="Yes" />
											  	<form:option value="No" />
											</form:select> 
			                            </div>
			                       		</c:if>
			                        </div>
				                </div>
				            </div> 
				            
				             <div class="row" id="failureReasonsDiv">
				             	
				                <div class="col-md-10 col-md-offset-1 form-container">
				                <h2>Failure Reason/s</h2> 
				                	<div class="row" id="accuracyCallFailureReasonDiv">
			                            <div class="col-sm-10 form-group" >
			                                <label for="accuracyCallFailureReason">Accuracy Call Failure Reason:</label>
			                                <form:input path="accuracyCallFailureReason" readonly="true" class="form-control"/>
			                            </div>		                           
			                        </div>  
			                        <div class="row" id="completenessCallFailureReasonDiv">
			                            <div class="col-sm-10 form-group" >
			                                <label for="completenessCallFailureReason">Completeness Call Failure Reason:</label>
			                                <form:input path="completenessCallFailureReason" readonly="true" class="form-control"/>
			                            </div>		                           
			                        </div>  
			                        
			                        <div class="row" id="privacyCallFailureReasonDiv">
			                            <div class="col-sm-10 form-group" >
			                                <label for="privacyCallFailureReason">Privacy Call Failure Reason:</label>
			                                <form:input path="privacyCallFailureReason" readonly="true" class="form-control"/>
			                            </div>		                           
			                        </div>  
			                        
			                        <div class="row" id="customerSkillsCallFailureReasonDiv">
			                            <div class="col-sm-10 form-group" >
			                                <label for="customerSkillsCallFailureReason">Customer Skills Call Failure Reason:</label>
			                                <form:input path="customerSkillsCallFailureReason" readonly="true" class="form-control"/>
			                            </div>		                           
			                        </div> 
			                       
				                </div>
				            </div>			            
				            
				             <div class="row" >
				                <div class="col-md-10 col-md-offset-1 form-container">
				                
					                <c:if test="${rebuttal.id != 0}">
					                 <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name">Saved Description/Comments:</label>
			                                <form:textarea class="required form-control" type = "textarea" name = "descriptionComments" path="descriptionComments" readonly="true"/>
			                            </div>		                           
			                        </div>    
					                </c:if>
			                        
			                         <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name">Description/Comments:</label>
			                                <form:textarea class="required form-control" type = "textarea" name = "descriptionCommentsAppend" path="descriptionCommentsAppend" required="true"/>
			                            </div>		                           
			                        </div>       
			                         <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name">Attachments:</label>			                                
			                               <input class="form-control" id="file" type="file" name="uploadAttachment" style="box-sizing: content-box;" >
										</input>
			                            </div>		                           
			                        </div>       
				                    
				                </div>
				            </div>		
				            <table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td><span><button class="btn btn-primary" id="create">Save/Update</button></span>
									<span><button class="btn btn-primary" id="close2" type="button">Close</button></span></td>
							       </tr>
							</table>
								<!-- </div> -->
							</div>
						</div>
					</div>
				</td>
			</tr>
		</form:form>
	</table>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>