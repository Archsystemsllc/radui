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
<meta charset="utf-8" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/button.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui-timepicker-addon.css" />

<link rel="stylesheet" href="/resources/demos/style.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>
<link rel="stylesheet" href="https://jqueryvalidation.org/files/demo/css/screen.css"></link>

<!-- CSS for Bootstrap -->
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"></link>
<!-- JQuery -->
 <script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.33.3/es6-shim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/systemjs/0.19.20/system-polyfills.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery-ui-timepicker-addon.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.16.0/jquery.validate.min.js"></script>

<style type="text/css">
	.red {  color:red;  }
</style>

<script type="text/javascript">

	$(document).ready(function () {
		//Required Fields Logic
		$('.required').each(function(){
		       $(this).prev('label').after("<span class='red'>*</span>");
		});
		
    	//Set Default Values    	
    	$('#alertMsg').text('');
    	$('#nonScoreableReasonCommentsDiv').hide();
    	$('#section4HeaderDiv').hide();
    	$("#section4HeaderDiv_DoesNotCount").hide();	
    	$("#dialog-confirm").hide();
    	//Hiding Section 4, 5, 6, 7 Failure Blocks
    	$("#accuracyCallFailureBlock").hide();	
    	$("#completenessCallFailureBlock").hide();	
    	$("#privacyCallFailureBlock").hide();	
    	$("#customerSkillsCallFailureBlock").hide();

	    var csrPrvAccInfoFlag="${scorecard.csrPrvAccInfo}";
	    var csrPrvCompInfoFlag="${scorecard.csrPrvCompInfo}";
	    var csrFallPrivacyProvFlag="${scorecard.csrFallPrivacyProv}";
	    var csrWasCourteousFlag="${scorecard.csrWasCourteous}";

	    if(csrPrvAccInfoFlag=="No") {
			$("#accuracyCallFailureBlock").show();	
		}
		if(csrPrvCompInfoFlag=="No") {
			$("#completenessCallFailureBlock").show();	
		}
		if(csrPrvCompInfoFlag=="No") {
			$("#privacyCallFailureBlock").show();	
		}
		if(csrWasCourteousFlag=="No") {
			$("#customerSkillsCallFailureBlock").show();	
		}

		//Auto Complete CSF Full Name Functionality
		var username="qamadmin";
  	   	var password="123456";
    	    	
    	$("#csrFullName" ).autocomplete({
    	      minLength: 0,
    	      source: function(request, response) {
      			var autocompleteContext = request.term;
      			var selectedMac = $('select[name=macId]').val();
      			var selectedJurisdiction = $('#jurId :selected').text();
      			var selectedProgram = $('#programId :selected').text();
      			$.ajax({	    			
  	    			url : "${WEB_SERVICE_URL}csrListNames",
  	    			contentType: "application/json; charset=utf-8",
  	    			headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
  	    			dataType: "json",
  	    			data: { term:autocompleteContext, macIdS: selectedMac, jurisdictionS: selectedJurisdiction,programS: selectedProgram},
      				success: function(data) {
      					response(data);
      				}
      			});
      		  },
    	      focus: function( event, ui ) {
    	        $( "#csrFullName" ).val( ui.item.firstName+" "+ui.item.middleName+" "+ui.item.lastName);
    	        return false;
    	      },
    	      select: function( event, ui ) {
    	    	$( "#csrFullName" ).val( ui.item.firstName+" "+ui.item.middleName+" "+ui.item.lastName);
    	        $( "#csrLevel" ).val( ui.item.level );  

    	        var selectedJurisdiction = $('#jurId :selected').text();
    			var selectedProgram = $('#programId :selected').text();
    			var csrFullName = $(this).val();
    			var csrFullNameArray = csrFullName.split(",");
    			
    			var firstName = ui.item.firstName.substr(0,1);
    			var lastName = ui.item.lastName.substr(0,5);

    			var dateString= $('#callMonitoringDate_Alt').val();
    			
    			var callTimString = $('#callTime').val();
    			
				var hoursString = callTimString.substr(0, 2);
    			 var hours = parseInt(hoursString);
   			    if(callTimString.indexOf('AM') != -1 && hours == 12) {
   			    	callTimString = callTimString.replace('12', '0');
   			    }
   			    if(callTimString.indexOf('PM')  != -1 && hours < 12) {
   			    	callTimString = callTimString.replace(hoursString, (hours + 12));
   			    }
   			    callTimString = callTimString.replace(/:/,'');
   			    callTimString = callTimString.replace(/:/,'');
   			    callTimString = callTimString.replace(/(AM|PM)/, '');
    			
                var macRefId = selectedJurisdiction+firstName+lastName+dateString+"_"+callTimString;

                $('#macCallReferenceNumber').val(macRefId);         	 
    	        return false;
    	      }
    	})
   	    .autocomplete( "instance" )._renderItem = function( ul, item ) {
   	      return $( "<li>" )
   	        .append( "<div>" + item.firstName+" "+item.middleName+" "+item.lastName + " ---- " +item.level + "</div>" )
   	        .appendTo( ul );
   	    };

   	    //Dates Initialisation Functionality
    	
    	$('#callMonitoringDate').datepicker({
    		maxDate: 0,
    		altField: "#callMonitoringDate_Alt",
    		altFieldTimeOnly: false,
    		altFormat: "yymmdd"
    	});    	

    	$('#callDuration').timepicker({
    		timeFormat: 'HH:mm:ss',
    		controlType: 'select',
        	oneLine: true,
        	hourMax: 4
    	});    	

    	$('#callTime').timepicker({
    		timeFormat: 'hh:mm:ss TT',    		
    		controlType: 'select',
        	oneLine: true,
        	altField: "#callTime_Alt",
    		altFieldTimeOnly: false,
    		altFormat: "HHmmss"
    	});

    	$('#callFailureTime,#accuracyCallFailureTime,#completenessCallFailureTime,#privacyCallFailureTime,#customerSkillsCallFailureTime').timepicker({
    		timeFormat: 'HH:mm:ss',
    		controlType: 'select',
        	oneLine: true,
        	hourMax: 4
    	}); 
 	
	});

	$(function(){		

		//Select Jurisdiction Functionality
		$("select#macId").change(function(){
            $.getJSON("${pageContext.request.contextPath}/admin/selectJuris",                    
                    {macId: $(this).val(), multipleInput: false}, function(data){
               
                 $("#jurId").get(0).options.length = 0;	           
      	      	 $("#jurId").get(0).options[0] = new Option("---Select Jurisdiction---", "");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#jurId").get(0).options[$("#jurId").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
        });

		//Select Program Functionality
		$("select#jurId").change(function(){
            $.getJSON("${pageContext.request.contextPath}/admin/selectProgram",{macId: $('#macId').val(),jurisId: $(this).val()}, function(data){
                
                 $("#programId").get(0).options.length = 0;	           
      	      	 $("#programId").get(0).options[0] = new Option("---Select Program---", "");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#programId").get(0).options[$("#programId").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
        });

		//Select Call Category Functionality
		$("select#callCategoryId").change(function(){
			
            $.getJSON("${pageContext.request.contextPath}/admin/selectCallSubcategories",{categoryId: $('#callCategoryId').val()}, function(data){
                
                 $("#callSubCategoryId").get(0).options.length = 0;	           
      	      	 $("#callSubCategoryId").get(0).options[0] = new Option("---Select Sub Category---", "");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#callSubCategoryId").get(0).options[$("#callSubCategoryId").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
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
	              	 	window.history.back();
	                },
	                Cancel: function() {                    
	                	$( this ).dialog("close"); 
	                }
	              }
            });
	     }); 

      	//Secton 1 - Option 1

		$("input[name='scorecardType']").change(function(){		
			
			var selected_value = $("input[name='scorecardType']:checked").val();
			
			if(selected_value=="Non-Scoreable") {				
				$("#section4Div").hide();	
				$("#section5Div").hide();
				$("#section6Div").hide();	
				
				$("#callFailureReasonDiv").hide();
				$("#failReasonCommentsDiv").hide();	
				$('#section7HeaderDiv').hide();		
				$("#nonScoreableReasonCommentsDiv").show();	
				$('#section4HeaderDiv').show();		
				$("#section4HeaderDiv_DoesNotCount").hide();
							
				$('#csrPrvAccInfo1,#csrPrvAccInfo2').attr("required",false);
				$('#csrPrvCompInfo1,#csrPrvCompInfo2').attr("required",false);
				$('#csrFallPrivacyProv1,#csrFallPrivacyProv2').attr("required",false);
				$('#csrWasCourteous1,#csrWasCourteous2').attr("required",false);				
				
				
			} else if(selected_value=="Scoreable") {
				$("#section4Div").show();	
				$("#section5Div").show();
				$("#section6Div").show();	
				$("#callResultDiv").show();	
				$("#callFailureReasonDiv").show();
				$("#failReasonCommentsDiv").show();
				$('#section7HeaderDiv').show();		
				$("#nonScoreableReasonCommentsDiv").hide();	
				$('#section4HeaderDiv').hide();	
				$("#section4HeaderDiv_DoesNotCount").hide();	

				$('#csrPrvAccInfo1,#csrPrvAccInfo2').attr("required",true);
				$('#csrPrvCompInfo1,#csrPrvCompInfo2').attr("required",true);
				$('#csrFallPrivacyProv1,#csrFallPrivacyProv2').attr("required",true);
				$('#csrWasCourteous1,#csrWasCourteous2').attr("required",true);	
								
			}  else if(selected_value=="Does Not Count") {
				
				$("#section4Div").hide();	
				$("#section5Div").hide();
				$("#section6Div").hide();	
				
				$("#callFailureReasonDiv").hide();
				$("#failReasonCommentsDiv").hide();	
				$('#section7HeaderDiv').hide();	
				$('#section4HeaderDiv').hide()	
				$("#nonScoreableReasonCommentsDiv").hide();	
				$("#section4HeaderDiv_DoesNotCount").show();	

				$('#csrPrvAccInfo1,#csrPrvAccInfo2').attr("required",false);
				$('#csrPrvCompInfo1,#csrPrvCompInfo2').attr("required",false);
				$('#csrFallPrivacyProv1,#csrFallPrivacyProv2').attr("required",false);
				$('#csrWasCourteous1,#csrWasCourteous2').attr("required",false);	
								
			} 
			setCallResult();	 		
        });

        //Secton 4 - Option 1

		$("input[name='csrPrvAccInfo']").change(function(){		
			
			var selected_value = $("input[name='csrPrvAccInfo']:checked").val();
			
			if(selected_value=="No") {
				$("#accuracyCallFailureBlock").show();	
				$("#accuracyCallFailureReason").focus();
				$('#accuracyCallFailureReason,#accuracyCallFailureTime').attr("required",true);				
				setCallResult();	
			} else if(selected_value=="Yes") {
				$("#accuracyCallFailureBlock").hide();	
				$('#accuracyCallFailureReason,#accuracyCallFailureTime').attr("required",false);				
				setCallResult();					
			}    		
        });

        //Section 4 - Option 2

		$("input[name='csrPrvCompInfo']").change(function(){		
			
			var selected_value = $("input[name='csrPrvCompInfo']:checked").val();			
			if(selected_value=="No") {
				
				$("#completenessCallFailureBlock").show();	
				$("#completenessCallFailureReason").focus();
				$('#completenessCallFailureReason,#completenessCallFailureTime').attr("required",true);						
				setCallResult();	
			} else if(selected_value=="Yes") {
				$("#completenessCallFailureBlock").hide();	
				$('#completenessCallFailureReason,#completenessCallFailureTime').attr("required",false);	
				setCallResult();	
			}    		
        });

        //Section 5 - Option 1

		$("input[name='csrFallPrivacyProv']").change(function(){		
			
			var selected_value = $("input[name='csrFallPrivacyProv']:checked").val();			
			if(selected_value=="No") {
				
				$("#privacyCallFailureBlock").show();	
		 	    $("#privacyCallFailureReason").focus();  
		 	    $('#privacyCallFailureReason,#privacyCallFailureTime').attr("required",true);			
		 	    setCallResult();	
				
			} else if(selected_value=="Yes") {
				$("#privacyCallFailureBlock").hide();
				 $('#privacyCallFailureReason,#privacyCallFailureTime').attr("required",false);						
				setCallResult();	
			}    		
        });

        //Section 6 - Option 1

		$("input[name='csrWasCourteous']").change(function(){		
			
			var selected_value = $("input[name='csrWasCourteous']:checked").val();			
			if(selected_value=="No") {
				
				$("#customerSkillsCallFailureBlock").show();
				$("#customerSkillsCallFailureReason").focus();
				$('#customerSkillsCallFailureReason,#customerSkillsCallFailureTime').attr("required",true);	
				setCallResult();
							
			} else if(selected_value=="Yes") {				
				
				$("#customerSkillsCallFailureBlock").hide();
				$('#customerSkillsCallFailureReason,#customerSkillsCallFailureTime').attr("required",false);	
				setCallResult();
			}    		
        });

		//Select Call Result Functionality
		function setCallResult() {
			$("#callResult").val("");
        	var csrWasCourteous_value = $("input[name='csrWasCourteous']:checked").val();	
        	var csrFallPrivacyProv_value = $("input[name='csrFallPrivacyProv']:checked").val();
        	var csrPrvCompInfo_value = $("input[name='csrPrvCompInfo']:checked").val();	
        	var csrPrvAccInfo_value = $("input[name='csrPrvAccInfo']:checked").val();

        	var scorecardType_value = $("input[name='scorecardType']:checked").val();

        	if ((scorecardType_value == "Scoreable") 
        		&& (csrWasCourteous_value =="No" || csrFallPrivacyProv_value =="No" 
            		|| csrPrvCompInfo_value =="No" || csrPrvAccInfo_value =="No" )             		
            	) {
            	
        			$("#callResult").val("Fail");
        	} 

        	if (( scorecardType_value == "Scoreable" ) 
            		&& ( csrWasCourteous_value == "Yes" && csrFallPrivacyProv_value == "Yes" 
            			&& csrPrvCompInfo_value == "Yes" && csrPrvAccInfo_value == "Yes" )             		
                	) {
            		
            		$("#callResult").val("Pass");
        	}

        	if ((scorecardType_value == "Non-Scoreable" || scorecardType_value == "Does Not Count")) {

        		$("#callResult").val("N/A");
           } 
        }
	});
	
</script>



</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div id="dialog-confirm" title="Close Scorecard Confirmation?">
  		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Do you want to close the scorecard without saving?</p>
	</div>

	<table id="mid">
		<form:form method="POST" modelAttribute="scorecard" class="form-signin" action="${pageContext.request.contextPath}/${SS_USER_FOLDER}/saveorupdatescorecard" id="scorecardForm">
		<!-- Hidden Fields -->
		<form:input type = "hidden" path="id" />
		<form:input type = "hidden" path="qamStartdateTime" />
		<form:input type = "hidden" path="qamEnddateTime" />
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
							
							<div class="table-users" style="width: 80%">
								<div class="header">Save/Update Scorecard</div>	
								<table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td>
									<c:if test="${scorecard.id == 0}">
										<span><button class="btn btn-primary" id="create"  type="submit">Save</button></span>
									</c:if>
									<c:if test="${scorecard.id > 0}">
										<span><button class="btn btn-primary" id="create"  type="submit">Update</button></span>
									</c:if>
									<span><button class="btn btn-primary" id="close1" type="button">Close</button></span></td>
							       </tr>
							     </table>						
      							 <div class="row " style="margin-top: 10px">
				                <div class="col-lg-8 col-lg-offset-1 form-container">
				                <div class="row">
				                      <div style="color: red;font-size: 18px;"  class="col-lg-12 form-group">
				                      <c:if test="${not empty ValidationFailure}">
										${ValidationFailure}
										</c:if>
				                      </div>
									</div>
				                    <h2>Section 1 - QAM Info</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->
				                   
				                    <div class="row">
			                            <div class="col-lg-6 form-group">
			                                <label for="qamFullName"> QM Name/QM ID: </label>
			                                <form:input type = "text" class="form-control" id="qamFullName" name = "qamFullName" path="qamFullName" readonly="true"/>			                                
			                            </div>
			                            <div class="col-lg-6 form-group">
			                                <label for="email"> Scorecard Type:</label></br>
			                                <c:if test="${scorecardType==null}">
											   <form:radiobutton path="scorecardType" value="Scoreable" checked="checked"/>&nbsp;Scoreable&nbsp;
											</c:if>	
											<c:if test="${scorecardType!=null}">
											   <form:radiobutton path="scorecardType" value="Scoreable" />&nbsp;Scoreable&nbsp;
											</c:if>			                                
										  	&nbsp;<form:radiobutton path="scorecardType" value="Non-Scoreable" />&nbsp;Non-Scoreable&nbsp;
										  	&nbsp;<form:radiobutton path="scorecardType" value="Does Not Count" />&nbsp;Does Not Count&nbsp;
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-lg-6 form-group">
			                                <label for="name"> QM Start Date/Time:</label>
			                                <input type="hidden" id="DatePickerHidden" />
			                                <form:input type = "text" class="form-control" path="qamStartdateTimeString" readonly="true"/>
			                            </div>
			                            <div class="col-lg-6 form-group">
			                                <label for="email"> QM End Date/Time:</label>
			                                <form:input type = "text" class="form-control" path="qamEnddateTimeString" readonly="true"/>
			                            </div>
			                        </div>
				                    
				                </div>
				            </div>
				            
				             <div class="row " >
				                <div class="col-lg-8 col-lg-offset-1 form-container">
				                    <h2>Section 2 - QAM Contract Information</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->
				                    
				                    <div class="row">
			                            <div class="col-lg-6 form-group">
			                                <label for="callMonitoringDate"> Call Monitoring Date:</label>
			                                <form:input type = "text" class="form-control required" id="callMonitoringDate" name = "callMonitoringDate" path="callMonitoringDate" required="true"/>
			                            	<input type="hidden" id="callMonitoringDate_Alt"></input>
			                            </div>
			                            <div class="col-lg-6 form-group">
			                                <label for="email"> </label>
			                                
			                            </div>
			                        </div>
				                   
				                    <div class="row">
			                            <div class="col-lg-6 form-group">
			                                <label for="macId"> MAC:</label>
			                               
										<form:select path="macId" class="form-control required" id="macId" required="true">
										   <form:option value="" label="---Select MAC---"/>
										   <form:options items="${macIdMap}" />
										</form:select> 									
										
			                            </div>
			                            <div class="col-lg-6 form-group">
			                                <label for="jurId"> Jurisdiction:</label>
			                             
										
										<form:select path="jurId" class="form-control required" id="jurId" required="true">
										   <form:option value="" label="---Select Jurisdiction---"/>
										   <form:options items="${jurisMapEdit}" />
										</form:select> 				
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-lg-6 form-group">
			                                <label for="programId"> Program:</label>
			                             
										<form:select path="programId" class="form-control required" id="programId"  required="true">
										   <form:option value="" label="---Select Program---"/>
										   <form:options items="${programMapEdit}" />
										</form:select> 	
			                            </div>
			                            <div class="col-lg-6 form-group">
			                                <label for="lob"> LOB:</label>
			                                <form:select path="lob" class="form-control required" id="lob" required="true">
											   	<form:option value="" label="---Select LOB---"/>
											  	<form:option value="Appeals/Reopenings" />
											  	<form:option value="Electronic Data Interchange (EDI)" />
											  	<form:option value="Enrollments" />
											  	<form:option value="General" />
											</form:select> 
			                            </div>
			                        </div>
				                    
				                </div>
				            </div>
				            
				             <div class="row " >
				                <div class="col-lg-8 col-lg-offset-1 form-container">
				                    <h2>Section 3 - QAM Call and CSR Information</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->
				                    
				                     <div class="row">
			                            <div class="col-lg-6 form-group">
			                                <label for="callTime"> Call Time:</label>			                                 
			                                <form:input class="form-control required" type = "text" name = "callTime" path="callTime" required="true"/>
			                                <input type="hidden" id="callTime_Alt"></input>
			                            </div>
			                             <div class="col-lg-6 form-group">
			                                <label for="callDuration"> Call Duration:</label>
			                                <form:input class="form-control required"  type = "text" name = "callDuration" path="callDuration" required="true"/>
			                            </div>
			                        </div>
				                   
				                    <div class="row">
			                            <div class="col-lg-6 form-group">
			                                <label for="csrFullName"> CSR Full Name:</label>
			                                <form:input class="form-control required" type = "text" name = "csrFullName" path="csrFullName" required="true"/>
			                            </div>
			                            <div class="col-lg-6 form-group">
			                            	<label for="csrLevel"> CSR Level:</label>
			                                <form:input class="form-control" type = "text" name = "csrLevel" path="csrLevel"  readonly="true"/>	
			                            </div>
			                        </div>
			                         <div class="row">
			                        <div class="col-lg-6 form-group">
			                                <label for="email"> Call Language: </label>
			                                <form:select path="callLanguage" class="form-control required" id="callLanguage" required="true">
											   	<form:option value="" label="--- Select Language---"/>
											  	<form:option value="English" />
											  	<form:option value="Spanish" />											  	
											</form:select> 	
			                               
										</div>
			                            <div class="col-lg-6 form-group">
			                                <label for="macCallReferenceNumber"> MAC Call Reference ID:</label>
			                                <form:input class="form-control" type = "text" name = "macCallReferenceNumber" path="macCallReferenceNumber"  readonly="true"/>
			                               </div>
			                        </div>
			                        <div class="row">
			                            <div class="col-lg-6 form-group">
			                             <label for="email"> Call Category:</label>
			                              <form:select path="callCategoryId" class="form-control required" id="callCategoryId" required="true">
											   	<form:option value="" label="---Select Call Category---"/>
											  	<form:options items="${callCategoryMap}" />										  	
											</form:select> 			                                
			                            </div>
			                            <div class="col-lg-6 form-group">
			                                <label for="email"> Call Sub Category:</label>
			                                <form:select path="callSubCategoryId" class="form-control required" id="callSubCategoryId" required="true">
											   	<form:option value="" label="---Select Call Sub Category---"/>											   	
											   	<form:options items="${subCategorylMapEdit}" />												  						  	
											</form:select> 
			                            </div>
			                        </div>             
			                       
				                </div>
				            </div>
				            
				            <div class="row " id="section4Div">
				                <div class="col-lg-10 col-lg-offset-1 form-container">
				                    <h2>Section 4 - Knowledge Skills</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   
			                         <div class="row">
			                            <div class="col-lg-10 form-group">
			                                <label for="csrPrvAccInfo"> Did the CSR provide accurate information? If 'No' was selected, please enter reason in text box below:</label>
			                                <form:radiobutton path="csrPrvAccInfo" value="Yes" class="required" required="true"/>&nbsp;Yes&nbsp;
										  	<form:radiobutton path="csrPrvAccInfo" value="No" class="required" required="true"/>&nbsp;No
			                            </div>			                           
			                        </div>
			                        <div class="row" id="accuracyCallFailureBlock">
			                        <div class="col-lg-5 form-group">
			                                <label for="accuracyCallFailureReason">Accuracy Call Failure Reason: </label>
			                                 <form:input class="form-control required" type = "text" name = "accuracyCallFailureReason" path="accuracyCallFailureReason"  />
			                               
										</div>
			                            <div class="col-lg-5 form-group">
			                                <label for="accuracyCallFailureTime">Accuracy Call Failure Time:</label>
			                                <form:input class="form-control required" type = "text" name = "accuracyCallFailureTime" path="accuracyCallFailureTime"  />
			                               </div>
			                        </div>
			                        
			                        <div class="row">
			                            <div class="col-lg-10 form-group">
			                                <label for="name"> Did the CSR provide complete information? If 'No' was selected, please enter reason in text box below:</label>
			                                <form:radiobutton path="csrPrvCompInfo" value="Yes"   class="required" required="true"/>&nbsp;Yes&nbsp;
										  	<form:radiobutton path="csrPrvCompInfo" value="No" />&nbsp;No
			                            </div>
			                           
			                        </div> 
			                         <div class="row" id="completenessCallFailureBlock">
			                        <div class="col-lg-5 form-group">
			                                <label for="completenessCallFailureReason">Completeness Call Failure Reason: </label>
			                                 <form:input class="form-control required" type = "text" name = "completenessCallFailureReason" path="completenessCallFailureReason"  />
			                               
										</div>
			                            <div class="col-lg-5 form-group">
			                                <label for="completenessCallFailureTime">Completeness Call Failure Time:</label>
			                                <form:input class="form-control required" type = "text" name = "completenessCallFailureTime" path="completenessCallFailureTime"  />
			                               </div>
			                        </div>
				                    
				                </div>
				            </div>	
				            <div class="row" id="section5Div">
				                <div class="col-lg-10 col-lg-offset-1 form-container">
				                    <h2>Section 5 - Adherence to Privacy</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   
			                         <div class="row">
			                            <div class="col-lg-10 form-group">
			                                <label for="name"> Did CSR follow privacy procedures? If 'No' was selected, please select the reason below:</label>
			                                <form:radiobutton path="csrFallPrivacyProv" value="Yes"  class="required" required="true"/>&nbsp;Yes&nbsp;
										  <form:radiobutton path="csrFallPrivacyProv" value="No"/>&nbsp;No
			                            </div>		                           
			                        </div>   
			                         <div class="row" id="privacyCallFailureBlock">
			                        <div class="col-lg-5 form-group">
			                                <label for="privacyCallFailureReason">Privacy Call Failure Reason: </label>
			                                 <form:select class="form-control required" id="privacyCallFailureReason" path="privacyCallFailureReason" title="Select one of the Failure Reason" >
			                                 	<form:option value="" label="Select Privacy Failure Reason"/>
			                                 	<form:option value="PII and/or PHI were released, but the caller was not authorized to receive the information"/>
			                                 	<form:option value="PII and/or PHI were not released, but the caller requested and was authorized to receive the information"/>
			                                 	<form:option value="PII and/or PHI were released, but the caller did not request the information"/>
			                                 	<form:option value="General information was requested, but not released and the CSR did not forward the call or obtain callback information"/>
											</form:select>
			                               
										</div>
			                            <div class="col-lg-5 form-group">
			                                <label for="privacyCallFailureTime">Privacy Call Failure Time:</label>
			                                <form:input class="form-control required" type = "text" name = "privacyCallFailureTime" path="privacyCallFailureTime"/>
			                               </div>
			                        </div>      
				                    
				                </div>
				            </div>
				            <div class="row" id="section6Div">
				                <div class="col-lg-10 col-lg-offset-1 form-container">
				                    <h2>Section 6 - Customer Skills</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   
			                         <div class="row">
			                            <div class="col-lg-10 form-group">
			                                <label for="name">Was the CSR courteous, friendly, and professional? If 'No' was selected, please select the reason below:</label>
			                                <form:radiobutton path="csrWasCourteous" value="Yes"  class="required" required="true"/>&nbsp;Yes&nbsp;
										  <form:radiobutton path="csrWasCourteous" value="No"/>&nbsp;No
			                            </div>		                           
			                        </div>   
			                         <div class="row" id="customerSkillsCallFailureBlock">
			                       	 <div class="col-lg-5 form-group">
			                                <label for="customerSkillsCallFailureReason">Customer Skills Call Failure Reason: </label>
			                                 <form:select class="form-control required" id="customerSkillsCallFailureReason" path="customerSkillsCallFailureReason" title="Select one of the Failure Reason"  >
			                                 	<form:option value="" label="Select Customer Skills Call Failure Reason"/>
			                                 	<form:option value="Inappropriately interrupting the caller"/>
			                                 	<form:option value="Using profanity"/>
			                                 	<form:option value="Hanging up on the caller"/>
			                                 	<form:option value="Using derogatory terms and/or making disrespectful comments"/>
			                                 	<form:option value="Making negative comments about CMS or the MACs"/>
			                                 	<form:option value="Making negative comments about CMS policies and procedures"/>
											</form:select>
			                               
										</div>
			                            <div class="col-lg-5 form-group">
			                                <label for="customerSkillsCallFailureTime">Customer Skills Call Failure Time:</label>
			                                <form:input class="form-control required" type = "text" name = "customerSkillsCallFailureTime" path="customerSkillsCallFailureTime"/>
			                               </div>
			                        </div>            
				                    
				                </div>
				            </div>
				             <div class="row" >
				                <div class="col-lg-8 col-lg-offset-1 form-container">
				                    <h2 id="section7HeaderDiv">Section 7 - Call Result</h2> 
				                    <h2 id="section4HeaderDiv">Section 4 - Non-Scoreable Call Result</h2> 
				                    <h2 id="section4HeaderDiv_DoesNotCount">Section 4 - Does Not Count Call Result</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->
				                   
				                    <div class="row" id="callResultDiv">
			                            <div class="col-lg-6 form-group" >
			                                <label for="name"> Call Result:</label>
			                                
											<form:input class="form-control required" type = "text" name = "callResult" path="callResult" readonly="true" />
			                            </div>
			                            <div class="col-lg-6 form-group">
			                             
			                            </div>
			                        </div>
			                       
			                         <div class="row" id="failReasonCommentsDiv">
			                            <div class="col-lg-10 form-group">
			                                <label for="name">Call Failure Reason Comments:</label>
			                                <form:textarea class="form-control required" type = "textarea" name = "failReasonComments" path="failReasonComments" />
			                            </div>		                           
			                        </div>
			                        <div class="row" id="nonScoreableReasonCommentsDiv">
			                            <div class="col-lg-10 form-group">
			                                <label for="name">Non-Scoreable Reason:</label>
			                                <form:select class="form-control required" id="nonScoreableReason" path="nonScoreableReason" title="Select one of the Reason" >
			                                 	<form:option value="" label="Select Non-Scoreable Reason"/>
			                                 	<form:option value="Recorded file disconnected unexpectedly"/>
			                                 	<form:option value="Recorded file was inaudible/not viewable and deemed corrupted"/>
			                                 	<form:option value="Recorded file was not available"/>
			                                 	<form:option value="MAC Jurisdiction Program CSR selection invalid for requirement"/>
			                                 	<form:option value="Call Category not eligible for QAM"/>			                                 	
											</form:select>
			                            </div>
			                                                       
			                        </div>       
			                         <div class="row">
			                            <div class="col-lg-10 form-group">
			                                <label for="name">Additional Comments Box:</label>
			                               <form:textarea class="form-control" type = "text" name = "failReasonAdditionalComments" path="failReasonAdditionalComments" />
			                            </div>		                           
			                        </div>       
				                    
				                </div>
				            </div>		
				            <table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td><c:if test="${scorecard.id == 0}">
										<span><button class="btn btn-primary" id="create"  type="submit">Save</button></span>
									</c:if>
									<c:if test="${scorecard.id > 0}">
										<span><button class="btn btn-primary" id="create"  type="submit">Update</button></span>
									</c:if>
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