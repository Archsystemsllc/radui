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

<script type="text/javascript">

$.validator.setDefaults({
	submitHandler: function() {
		alert("submitted!");
	}
});

	$(document).ready(function () {

    	var username="qamadmin";
  	   	var password="123456";
    	
    	$('#alertMsg').text('');
    	
    	$("#csrFullName" ).autocomplete({
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
    		}
    	});	
    	
    	$('#qamStartdateTime').datetimepicker({
    		timeFormat: 'hh:mm:ss TT'     	
    	});
    	
    	$('#qamEnddateTime').datetimepicker({
    		timeFormat: 'hh:mm:ss TT',
    		timezoneList: [ 
    				{ value: -300, label: 'Eastern'}, 
    				{ value: -360, label: 'Central' }, 
    				{ value: -420, label: 'Mountain' }, 
    				{ value: -480, label: 'Pacific' } 
    			]
    	});

    	$('#callMonitoringDate').datepicker({
    		maxDate: 0
    	});    	

    	$('#callDuration').timepicker({
    		timeFormat: 'HH:mm',
    		timeOnlyTitle: 'Select Duration',
    		hourGrid: 4,
    		hourMax: 4,
        	minuteGrid: 10
    	});    	

    	$('#callTime').timepicker({
    		timeFormat: 'HH:mm:ss',    		
    		hourGrid: 4,
        	minuteGrid: 10
    	});

    	$('#callFailureTime').datetimepicker({
    		timeFormat: 'HH:mm:ss'
    	}); 
 	
	});

	$(function(){

		 
		
		$("select#macId").change(function(){
            $.getJSON("${pageContext.request.contextPath}/admin/selectJuris",                    
                    {macId: $(this).val(), multipleInput: false}, function(data){
               
                 $("#jurId").get(0).options.length = 0;	           
      	      	 $("#jurId").get(0).options[0] = new Option("Select Jurisdiction", "");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#jurId").get(0).options[$("#jurId").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
        });
		$("select#jurId").change(function(){
            $.getJSON("${pageContext.request.contextPath}/admin/selectProgram",{macId: $('#macId').val(),jurisId: $(this).val()}, function(data){
                
                 $("#programId").get(0).options.length = 0;	           
      	      	 $("#programId").get(0).options[0] = new Option("Select Program", "");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#programId").get(0).options[$("#programId").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
        });

		$("#csrFullName").change(function(){
			
			var selectedJurisdiction = $('#jurId :selected').text();
			var selectedProgram = $('#programId :selected').text();
			var csrFullName = $(this).val();
			var csrFullNameArray = csrFullName.split(",");
			
			var firstName = csrFullNameArray[0].substr(0,1);
			var lastName = csrFullNameArray[2].substr(0,5);
			var timeString = $('#qamStartdateTime').val();
			
            var macRefId = selectedJurisdiction+"_"+firstName+"_"+lastName+"_"+timeString;

            $('#macCallReferenceNumber').val(macRefId);            	
        });

        //Secton 4 - Option 1

		$("input[name='csrPrvAccInfo']").change(function(){		
			
			var selected_value = $("input[name='csrPrvAccInfo']:checked").val();
			
			if(selected_value=="No") {
				$("input[name='csrPrvCompInfo']").attr('disabled', true);	
				$("input[name='csrFallPrivacyProv']").attr('disabled', true);	
				$("input[name='csrWasCourteous']").attr('disabled', true);
				$("#failReasonComments").focus();		
				setCallResult();	
			} else if(selected_value=="Yes") {
				$("input[name='csrPrvCompInfo']").attr('disabled', false);	
				$("input[name='csrFallPrivacyProv']").attr('disabled', false);	
				$("input[name='csrWasCourteous']").attr('disabled', false);		
				setCallResult();					
			}    		
        });

        //Section 4 - Option 2

		$("input[name='csrPrvCompInfo']").change(function(){		
			
			var selected_value = $("input[name='csrPrvCompInfo']:checked").val();			
			if(selected_value=="No") {
				$("input[name='csrPrvAccInfo']").attr('disabled', true);	
				$("input[name='csrFallPrivacyProv']").attr('disabled', true);	
				$("input[name='csrWasCourteous']").attr('disabled', true);
				$("#failReasonComments").focus();		
				setCallResult();	
			} else if(selected_value=="Yes") {
				$("input[name='csrPrvAccInfo']").attr('disabled', false);	
				$("input[name='csrFallPrivacyProv']").attr('disabled', false);	
				$("input[name='csrWasCourteous']").attr('disabled', false);
				setCallResult();	
			}    		
        });

        //Section 5 - Option 1

		$("input[name='csrFallPrivacyProv']").change(function(){		
			
			var selected_value = $("input[name='csrFallPrivacyProv']:checked").val();			
			if(selected_value=="No") {
				$("input[name='csrPrvAccInfo']").attr('disabled', true);	
				$("input[name='csrPrvCompInfo']").attr('disabled', true);	
				$("input[name='csrWasCourteous']").attr('disabled', true);
					

				$("#failReason").get(0).options.length = 0;
	 	        $("#failReason").get(0).options[0] = new Option("Select Failure Reason", ""); 

	 	       	$("#failReason").get(0).options[1] = new Option("PII and/or PHI were released, but the caller was not authorized to receive the information"); 
		 	  	$("#failReason").get(0).options[2] = new Option("PII and/or PHI were not released, but the caller requested and was authorized to receive the information"); 
		 	    $("#failReason").get(0).options[3] = new Option("PII and/or PHI were released, but the caller did not request the information"); 
		 	    $("#failReason").get(0).options[4] = new Option("General information was requested, but not released and the CSR did not forward the call or obtain callback information"); 

			  		
		 	    $("#failReasonComments").focus();  		
		 	   setCallResult();	

				
			} else if(selected_value=="Yes") {
				$("input[name='csrPrvAccInfo']").attr('disabled', false);	
				$("input[name='csrPrvCompInfo']").attr('disabled', false);	
				$("input[name='csrWasCourteous']").attr('disabled', false);
				$("#failReason").get(0).options.length = 0;
				setCallResult();	
			}    		
        });

        //Section 6 - Option 1

		$("input[name='csrWasCourteous']").change(function(){		
			
			var selected_value = $("input[name='csrWasCourteous']:checked").val();			
			if(selected_value=="No") {
				$("input[name='csrPrvAccInfo']").attr('disabled', true);	
				$("input[name='csrPrvCompInfo']").attr('disabled', true);	
				$("input[name='csrFallPrivacyProv']").attr('disabled', true);
				
				$("#failReason").get(0).options.length = 0;
	 	        $("#failReason").get(0).options[0] = new Option("Select Failure Reason", ""); 

	 	       	$("#failReason").get(0).options[1] = new Option("Inappropriately interrupting the caller"); 
		 	  	$("#failReason").get(0).options[2] = new Option("Using profanity"); 
		 	    $("#failReason").get(0).options[3] = new Option("Hanging up on the caller"); 
		 	    $("#failReason").get(0).options[4] = new Option("Using derogatory terms and/or making disrespectful comments"); 
		 	    $("#failReason").get(0).options[5] = new Option("Making negative comments about CMS or the MACs"); 
		 	    $("#failReason").get(0).options[6] = new Option("Making negative comments about CMS policies and procedures"); 			  	   
				
				$("#failReasonComments").focus();
				setCallResult();
							
			} else if(selected_value=="Yes") {
				$("input[name='csrPrvAccInfo']").attr('disabled', false);	
				$("input[name='csrPrvCompInfo']").attr('disabled', false);	
				$("input[name='csrFallPrivacyProv']").attr('disabled', false);
				$("#failReason").get(0).options.length = 0;

				setCallResult();
			}    		
        });

		function setCallResult() {
			$("#callResult").val("");
        	var csrWasCourteous_value = $("input[name='csrWasCourteous']:checked").val();	
        	var csrFallPrivacyProv_value = $("input[name='csrFallPrivacyProv']:checked").val();
        	var csrPrvCompInfo_value = $("input[name='csrPrvCompInfo']:checked").val();	
        	var csrPrvAccInfo_value = $("input[name='csrPrvAccInfo']:checked").val();

        	var scorecardType_value = $("input[name='scorecardType']:checked").val();

        	if ((scorecardType_value == "Non-Scoreable" || scorecardType_value == "Not-Valued") 
        		|| (csrWasCourteous_value =="No" || csrFallPrivacyProv_value =="No" 
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
        }

		
	});
</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>

	<table id="mid">
		<form:form method="POST" modelAttribute="scorecard" class="form-signin" action="${pageContext.request.contextPath}/admin/saveorupdatescorecard" id="scorecardForm">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
							
							<div class="table-users" style="width: 80%">
								<div class="header">Save/Update ScoreCard</div>	
								<table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
					
									
									<tr>
									<td><span><button class="btn btn-primary" id="create"  type="submit">Save/Update</button></span>
									<span><button class="btn btn-primary" id="close">Close</button></span></td>
								

							       </tr>
							     </table>						
      							 <div class="row " style="margin-top: 10px">
				                <div class="col-md-8 col-md-offset-1 form-container">
				                    <h2>Section 1 - QAM Info</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->
				                   
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> QM Full Name:</label>
			                                <form:input type = "text" class="form-control" id="qamFullName" name = "qamFullName" path="qamFullName" readonly="true"/>
			                                <form:input type = "hidden" name = "id" path="id" />
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> ScoreCard Type:</label></br>
			                                <c:if test="${scorecardType==null}">
											   <form:radiobutton path="scorecardType" value="Scoreable" checked="checked"/>Scoreable
											</c:if>	
											<c:if test="${scorecardType!=null}">
											   <form:radiobutton path="scorecardType" value="Scoreable" />Scoreable
											</c:if>			                                
										  	<form:radiobutton path="scorecardType" value="Non-Scoreable" />Non-Scoreable
										  	<form:radiobutton path="scorecardType" value="Does Not Count" />Does Not Count
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> QM Start Date/Time:</label>
			                                <input type="hidden" id="DatePickerHidden" />
			                                <form:input type = "text" class="form-control" id="qamStartdateTime" name = "qamStartdateTime" path="qamStartdateTime" readonly="true"/>
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> QM End Date/Time:</label>
			                                <form:input type = "text" class="form-control" id="qamEnddateTime" name = "qamEnddateTime" path="qamEnddateTime" readonly="true"/>
			                            </div>
			                        </div>
				                    
				                </div>
				            </div>
				            
				             <div class="row " >
				                <div class="col-md-8 col-md-offset-1 form-container">
				                    <h2>Section 2 - QAM Contract Information</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->
				                    
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Call Monitoring Date:</label>
			                                <form:input type = "text" class="form-control required" id="callMonitoringDate" name = "callMonitoringDate" path="callMonitoringDate" required="true"/>
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> </label>
			                                
			                            </div>
			                        </div>
				                   
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> MAC:</label>
			                               
										<form:select path="macId" class="form-control required" id="macId" required="true">
										   <form:option value="" label="--- Select MAC---"/>
										   <form:options items="${macIdMap}" />
										</form:select> 									
										
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> Jurisdiction:</label>
			                             
										
										<form:select path="jurId" class="form-control required" id="jurId" required="true">
										   <form:option value="" label="--- Select Jurisdiction---"/>
										   <form:options items="${jurisMapEdit}" />
										</form:select> 				
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Program:</label>
			                             
										<form:select path="programId" class="form-control required" id="programId"  required="true">
										   <form:option value="" label="--- Select Program---"/>
										   <form:options items="${programMapEdit}" />
										</form:select> 	
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> LOB:</label>
			                                <form:select path="lob" class="form-control required" id="lob" required="true">
											   	<form:option value="" label="--- Select LOB---"/>
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
				                <div class="col-md-8 col-md-offset-1 form-container">
				                    <h2>Section 3 - QAM Call and CSR Information</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->
				                   
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> CSR Full Name:</label>
			                                <form:input class="form-control required" type = "text" name = "csrFullName" path="csrFullName" required="true"/>
			                            </div>
			                            <div class="col-sm-6 form-group">
			                            	<label for="name"> CSR Level:</label>
			                                <form:input class="form-control required" type = "text" name = "csrLevel" path="csrLevel" />	
			                            </div>
			                        </div>
			                        <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="macCallReferenceNumber"> MAC Call Reference Id:</label>
			                                <form:input class="form-control required" type = "text" name = "macCallReferenceNumber" path="macCallReferenceNumber"  readonly="true"/>
			                               </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> Call Language: </label>
			                                <form:select path="callLanguage" class="form-control required" id="callLanguage" required="true">
											   	<form:option value="" label="--- Select Language---"/>
											  	<form:option value="English" />
											  	<form:option value="Spanish" />											  	
											</form:select> 	
			                               
										</div>
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-6 form-group">
			                             <label for="email"> Call Category:</label>
			                              <form:select path="callCategoryId" class="form-control required" id="callCategoryId" required="true">
											   	<form:option value="" label="--- Select Call Category---"/>
											  	<form:option value="1" label="Option 1"/>
											  	<form:option value="2" label="Option 2"/>											  	
											</form:select> 	
			                               
			                                
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> Call Sub Category:</label>
			                                <form:select path="callSubCategoryId" class="form-control required" id="callSubCategoryId" required="true">
											   	<form:option value="" label="--- Select Call Sub Category---"/>
											  	<form:option value="1" label="Option 1"/>
											  	<form:option value="2" label="Option 2"/>											  	
											</form:select> 	
			                                
			                              
			                            </div>
			                        </div>
			                       
			                        <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Call Duration:</label>
			                                <form:input class="form-control required"  type = "text" name = "callDuration" path="callDuration" required="true"/>
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Call Time:</label>
			                                <form:input class="form-control required" type = "text" name = "callTime" path="callTime" required="true"/>
			                            </div>
			                        </div>
				                </div>
				            </div>
				            
				            <div class="row " >
				                <div class="col-md-10 col-md-offset-1 form-container">
				                    <h2>Section 4 - Knowledge Skills</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   
			                         <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name"> Did the CSR provide accurate information? If 'No' was selected,please enter reason in text box below:</label>
			                                <form:radiobutton path="csrPrvAccInfo" value="Yes" />Yes
										  	<form:radiobutton path="csrPrvAccInfo" value="No" />No
			                            </div>
			                           
			                        </div>
			                        <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name"> Did the CSR provide complete information? If 'No' was selected,please enter reason in text box below:</label>
			                                <form:radiobutton path="csrPrvCompInfo" value="Yes"  />Yes
										  	<form:radiobutton path="csrPrvCompInfo" value="No" />No
			                            </div>
			                           
			                        </div> 
				                    
				                </div>
				            </div>
				            <div class="row" >
				                <div class="col-md-10 col-md-offset-1 form-container">
				                    <h2>Section 5 - Adherence to Privacy</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   
			                         <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name"> Did CSR follow privacy procedures? If 'No' was selected,please select the reason below:</label>
			                                <form:radiobutton path="csrFallPrivacyProv" value="Yes" />Yes
										  <form:radiobutton path="csrFallPrivacyProv" value="No"/>No
			                            </div>		                           
			                        </div>         
				                    
				                </div>
				            </div>
				            <div class="row" >
				                <div class="col-md-10 col-md-offset-1 form-container">
				                    <h2>Section 6 - Customer Skills</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   
			                         <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name">Was the CSR courteous, friendly, and professional? If 'No' was selected,please select the reason below:</label>
			                                <form:radiobutton path="csrWasCourteous" value="Yes"/>Yes
										  <form:radiobutton path="csrWasCourteous" value="No"/>No
			                            </div>		                           
			                        </div>         
				                    
				                </div>
				            </div>
				             <div class="row" >
				                <div class="col-md-8 col-md-offset-1 form-container">
				                    <h2>Section 7 - Results</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->
				                   
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Call Result:</label>
			                                
											<form:input class="form-control required" type = "text" name = "callResult" path="callResult" readonly="true"/>
			                            </div>
			                            <div class="col-sm-6 form-group">
			                               </div>
			                        </div>
			                        <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name">Call Failure Reason:</label>
			                                <select class="form-control" class="form-control" id="failReason" name="failReason"
											title="Select one of the Failure Reason">
												
											</select>
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> Call Failure Time:</label>
			                                <form:input class="form-control" type = "text" name = "callFailureTime" path="callFailureTime" />
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name">Fail Reason Comments:</label>
			                                <form:textarea class="form-control" type = "textarea" name = "failReasonComments" path="failReasonComments" />
			                            </div>		                           
			                        </div>       
			                         <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name">Additional Comments Box:</label>
			                               <form:textarea class="form-control" type = "text" name = "failReasonAdditionalComments" path="failReasonAdditionalComments" />
			                            </div>		                           
			                        </div>       
				                    
				                </div>
				            </div>		
				            <table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td><span><button class="btn btn-primary" id="create" type="submit">Save/Update</button></span>
									<span><button class="btn btn-primary" id="close">Close</button></span></td>
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