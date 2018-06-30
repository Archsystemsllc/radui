<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>QAM - Reports</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/Comrad_icon.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />


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
	function resetFields() {
			
		$('#fromDateString').val("");
		$('#toDateString').val("");
		$('#macId').prop('selectedIndex',0);
		$("#jurisdictionIds").get(0).options.length = 0;	    
		$("#jurisId").get(0).options.length = 0;	  
		$('#programId').prop('selectedIndex',0);
		$('#loc').prop('selectedIndex',0);
		
		$('#scoreCardType').prop('selectedIndex',0);  
		$('#complianceReportType').prop('selectedIndex',0);
		$('#callResult').prop('selectedIndex',0);
		$('#callCategoryType').prop('selectedIndex',0);
		$('#rebuttalStatus').prop('selectedIndex',0);
		
	}

	$(document).ready(function () {    	

		$('#scoreCardTypeDiv').hide();
		$('#callResultDiv').hide();
		$('#complianceTypeDiv').hide();
		$('#callCategoryTypeDiv').hide();
		$('#rebuttalStatusDiv').hide();
		$('#jurisSingleSelect').hide();
		

    	$('#fromDateString').datepicker({
    		maxDate: 0,
    		format: "mm/dd/yyyy",
    		onSelect: function(selected) {
    			$("#toDateString").datepicker("option","minDate", selected)
    		}

    	});    

    	$('#toDateString').datepicker({
    		maxDate: 0,
    		format: "mm/dd/yyyy",
    		onSelect: function(selected) {
    			$("#fromDateString").datepicker("option","maxDate", selected)
    		}
    			    		
    	});  

    	$('.required').each(function(){
		       $(this).prev('label').after("<span class='red'><strong>*</strong></span>");
		});

    	
    	 var reportSelectValue=$('input:radio[name=mainReportSelect]:checked').val();
    	
    	if (reportSelectValue == '') {    		
    		$('input:radio[name=mainReportSelect]')[0].checked = true;
    		$('#scoreCardTypeDiv').show();
    		$('#callResultDiv').show();
    		$('#programPccLocDiv,#datesDiv').show();   
    		$("#programId,#loc,#fromDateString,#toDateString,#jurisdictionIds").attr('required',true);
    		$("#jurisId").removeAttr('required');
    		$('#jurisMultiSelect').show();
    		$('#jurisSingleSelect').hide();
    		$('select[id="macId"] > option[value="ALL"]').show();
        } else if (reportSelectValue == 'ScoreCard' ) {
        	$('input:radio[name=mainReportSelect]')[0].checked = true;
    		$('#scoreCardTypeDiv').show();
    		$('#callResultDiv').show();
    		$('#programPccLocDiv,#datesDiv').show();  
    		$("#programId,#loc,#fromDateString,#toDateString,#jurisdictionIds").attr('required',true); 
    		$("#jurisId").removeAttr('required');
    		$('#jurisMultiSelect').show();
    		$('#jurisSingleSelect').hide();
    		$('select[id="macId"] > option[value="ALL"]').show();
        } else if (reportSelectValue == 'Compliance' ) {
        	$('#complianceTypeDiv').show();
        	$('#datesDiv').show();   
        	$("#fromDateString,#toDateString,#jurisdictionIds").attr('required',true);
        	$("#jurisId").removeAttr('required');
        	$('#jurisMultiSelect').show();
    		$('#jurisSingleSelect').hide();
    		$('select[id="macId"] > option[value="ALL"]').show();
    		$('#programPccLocDiv').hide();
    		$("#programId,#loc").removeAttr('required');
        } else if (reportSelectValue == 'Rebuttal' ) {
        	$('#callCategoryTypeDiv').show();
    		$('#rebuttalStatusDiv').show();
    		$('#programPccLocDiv,#datesDiv').show();
    		$("#programId,#loc,#fromDateString,#toDateString,#jurisdictionIds").attr('required',true);
    		$("#jurisId").removeAttr('required');
    		$('#jurisMultiSelect').show();
    		$('#jurisSingleSelect').hide();
    		$('select[id="macId"] > option[value="ALL"]').show();
        }  else if (reportSelectValue == 'Qasp' ) {
        	$('#scoreCardTypeDiv').hide();
        	$('#callResultDiv').hide();
        	$('#complianceTypeDiv').hide();
        	$('#callCategoryTypeDiv').hide();
    		$('#rebuttalStatusDiv').hide();
    		$('#programPccLocDiv').hide();
    		$("#programId,#loc,#jurisdictionIds").removeAttr('required');
    		$('#jurisMultiSelect').hide();
    		$('#jurisSingleSelect').show();
    		$("#jurisId").attr('required',true);    		
    		$("#macId option[value='ALL']").remove();
    		$('select[id="macId"] > option[value="ALL"]').hide();
    		
    		
        }  
        

    	var mainReportSelect = $("input[name='mainReportSelect']:selected").val();

    	
            if (mainReportSelect=="ScoreCard") {
            	$('#scoreCardTypeDiv').show();
            	$('#callResultDiv').show();
            	$('#complianceTypeDiv').hide();
            	$('#callCategoryTypeDiv').hide();
        		$('#rebuttalStatusDiv').hide();
        		$('#programPccLocDiv,#datesDiv').show();  
        		$("#programId,#loc,#fromDateString,#toDateString,#jurisdictionIds").attr('required',true); 
        		$('#jurisMultiSelect').show();
        		$('#jurisSingleSelect').hide();
        		$('select[id="macId"] > option[value="ALL"]').show();
            } else if (mainReportSelect=="Compliance") {
            	$('#scoreCardTypeDiv').hide();
            	$('#callResultDiv').hide();
            	$('#complianceTypeDiv').show();
            	$('#callCategoryTypeDiv').hide();
        		$('#rebuttalStatusDiv').hide();
        		$('#datesDiv').show(); 
        		$("#fromDateString,#toDateString,#jurisdictionIds").attr('required',true);  
        		$('#programPccLocDiv').hide();
        		$("#programId,#loc").removeAttr('required');
        		$('#jurisMultiSelect').show();
        		$('#jurisSingleSelect').hide();
        		$('select[id="macId"] > option[value="ALL"]').show();
        		
            } else if (mainReportSelect=="Rebuttal") {
            	$('#scoreCardTypeDiv').hide();
            	$('#callResultDiv').hide();
            	$('#complianceTypeDiv').hide();
            	$('#callCategoryTypeDiv').show();
        		$('#rebuttalStatusDiv').show();
        		$('#programPccLocDiv,#datesDiv').show();   
        		$("#programId,#loc,#fromDateString,#toDateString,#jurisdictionIds").attr('required',true);
        		$('#jurisMultiSelect').show();
        		$('#jurisSingleSelect').hide();
        		$('select[id="macId"] > option[value="ALL"]').show();
            } else if (mainReportSelect=="Qasp") {
            	$('#scoreCardTypeDiv').hide();
            	$('#callResultDiv').hide();
            	$('#complianceTypeDiv').hide();
            	$('#callCategoryTypeDiv').hide();
        		$('#rebuttalStatusDiv').hide();
        		$('#programPccLocDiv').hide();
        		$("#programId,#loc,#jurisdictionIds").removeAttr('required');
        		$('#jurisMultiSelect').hide();
        		$('#jurisSingleSelect').show();
        		$("#jurisId").attr('required',true);        		
        		$('select[id="macId"] > option[value="ALL"]').hide();
            }

          
 		var scoreCardType = $("select#scoreCardType").val();
           if (scoreCardType=="Scoreable") {
           
           	$('#callResultDiv').show();
           } else if (scoreCardType=="Non-Scoreable") {
           	
           	$('#callResultDiv').hide();
           } else if (scoreCardType=="Does Not Count") {
           	
           	$('#callResultDiv').hide();
           }
	});

	$(function(){


		$("input[name='mainReportSelect']").change(function(){
            var mainReportSelect = $(this).val();
            if (mainReportSelect=="ScoreCard") {
            	$('#scoreCardTypeDiv').show();
            	$('#callResultDiv').show();
            	$('#complianceTypeDiv').hide();
            	$('#callCategoryTypeDiv').hide();
        		$('#rebuttalStatusDiv').hide();
        		$('#programPccLocDiv,#datesDiv').show();  
        		$("#programId,#loc,#fromDateString,#toDateString,#jurisdictionIds").attr('required',true); 
        		$('#jurisMultiSelect').show();
        		$('#jurisSingleSelect').hide();
        		$('select[id="macId"] > option[value="ALL"]').show();
        		
            } else if (mainReportSelect=="Compliance") {
            	$('#scoreCardTypeDiv').hide();
            	$('#callResultDiv').hide();
            	$('#complianceTypeDiv').show();
            	$('#callCategoryTypeDiv').hide();
        		$('#rebuttalStatusDiv').hide();
        		$('#datesDiv').show();  
        		$("#fromDateString,#toDateString,#jurisdictionIds").attr('required',true); 
        		$('#programPccLocDiv').hide();
        		$("#programId,#loc").removeAttr('required');
        		$('#jurisMultiSelect').show();
        		$('#jurisSingleSelect').hide();
        		$('select[id="macId"] > option[value="ALL"]').show();
        		
            } else if (mainReportSelect=="Rebuttal") {
            	$('#scoreCardTypeDiv').hide();
            	$('#callResultDiv').hide();
            	$('#complianceTypeDiv').hide();
            	$('#callCategoryTypeDiv').show();
        		$('#rebuttalStatusDiv').show();
        		$('#programPccLocDiv,#datesDiv').show();   
        		$("#programId,#loc,#fromDateString,#toDateString,#jurisdictionIds").attr('required',true);
        		$('#jurisMultiSelect').show();
        		$('#jurisSingleSelect').hide();
        		$('select[id="macId"] > option[value="ALL"]').show();
            } else if (mainReportSelect=="Qasp") {
            	$('#scoreCardTypeDiv').hide();
            	$('#callResultDiv').hide();
            	$('#complianceTypeDiv').hide();
            	$('#callCategoryTypeDiv').hide();
        		$('#rebuttalStatusDiv').hide();
        		$('#programPccLocDiv').hide();    
        		$("#programId,#loc,#jurisdictionIds").removeAttr('required');   
        		$('#jurisMultiSelect').hide();
        		$('#jurisSingleSelect').show();
        		$("#jurisId").attr('required',true);		
        		$('select[id="macId"] > option[value="ALL"]').hide();
            }
        });

		$("select#scoreCardType").change(function(){
			 var scoreCardType = $(this).val();
	            if (scoreCardType=="Scoreable") {
	            	
	            	$('#callResultDiv').show();
	            } else if (scoreCardType=="Non-Scoreable") {
	            	
	            	$('#callResultDiv').hide();
	            } else if (scoreCardType=="Does Not Count") {
	            	
	            	$('#callResultDiv').hide();
	            }
        });

		$("select#macId").change(function(){
			var userRole = $('#userRole').val();			
			if ((userRole != "MAC Admin") && (userRole != "MAC User")){
			
			var macIdValue = $(this).val();
			
	            $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectJuris",                    
	                    {macId: macIdValue , multipleInput: false}, function(data){
	                    	
	                 $("#jurisId").get(0).options.length = 0;	    
	                 //$("#jurisId").get(0).options[0] = new Option("---Select Jurisdiction---", "");        
	      	      	 $.each(data, function (key,obj) {
	      	  	    		$("#jurisId").get(0).options[$("#jurisId").get(0).options.length] = new Option(obj, key);
	      	  	    		
	      	  	    	});  

	      	  	     $("#jurisdictionIds").get(0).options.length = 0;	    
	      	  	 	 $("#jurisdictionIds").get(0).options[0] = new Option("---Select Jurisdiction---", "");       
	      	      	 $("#jurisdictionIds").get(0).options[1] = new Option("---Select All---", "ALL");
	      	  	    	$.each(data, function (key,obj) {
	      	  	    		$("#jurisdictionIds").get(0).options[$("#jurisdictionIds").get(0).options.length] = new Option(obj, key);
	      	  	    		
	      	  	    	});  
	      	  	    	   
	               });

	               
			}
        });

		$("#jurisdictionIds").change(function(){

			var userRole = $('#userRole').val();			
			if ((userRole != "MAC Admin") && (userRole != "MAC User")){

				var macIdValue = $('#macId').val();
				
				var selectedJurisdiction =""; 
				
				
				$("#jurisdictionIds :selected").each(function() {
					 selectedJurisdiction+=($(this).attr('value'))+",";
				}); 
				
	            $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectProgram",{macId: macIdValue,jurisId: selectedJurisdiction}, function(data){
	                
	                 $("#programId").get(0).options.length = 0;	           
	      	      	 $("#programId").get(0).options[0] = new Option("---Select Program---", "");
	      	      	 $("#programId").get(0).options[1] = new Option("---Select All---", "ALL");
	      	  	    	$.each(data, function (key,obj) {
	      	  	    		$("#programId").get(0).options[$("#programId").get(0).options.length] = new Option(obj, key);
	      	  	    		
	      	  	    	});  	   
	               });
			} 
        });

		
		$("#jurisId").change(function(){

			var userRole = $('#userRole').val();			
			if ((userRole != "MAC Admin") && (userRole != "MAC User")){

				var macIdValue = $('#macId').val();
				
				var selectedJurisdiction =""; 
				
				$("#jurisId :selected").each(function() {
					 selectedJurisdiction+=($(this).attr('value'))+",";
					}); 
				
	            $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectProgram",{macId: macIdValue,jurisId: selectedJurisdiction}, function(data){
	                
	                 $("#programId").get(0).options.length = 0;	           
	      	      	 $("#programId").get(0).options[0] = new Option("---Select Program---", "");
	      	      	 $("#programId").get(0).options[1] = new Option("---Select All---", "ALL");
	      	  	    	$.each(data, function (key,obj) {
	      	  	    		$("#programId").get(0).options[$("#programId").get(0).options.length] = new Option(obj, key);
	      	  	    		
	      	  	    	});  	   
	               });
			} 
        });

		$("select#programId").change(function(){

			var userRole = $('#userRole').val();			
			if ((userRole != "MAC Admin") && (userRole != "MAC User")){

				var selectedJurisdiction =""; 
				var mainReportSelect = $("input[name='mainReportSelect']:selected").val();

		    	
	            if (mainReportSelect=="Qasp") {
	            	 $("#jurisId :selected").each(function() {
						 selectedJurisdiction+=($(this).attr('value'))+",";
					});
	            } else {
	            	 $("#jurisdictionIds :selected").each(function() {
						 selectedJurisdiction+=($(this).attr('value'))+",";
					});
		        }
				
	            $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectLocation",{macId: $('#macId').val(),jurisId: selectedJurisdiction,programId: $(this).val(),programIdAvailableFlag: true}, function(data){
	                
	                 $("#loc").get(0).options.length = 0;	           
	      	      	 $("#loc").get(0).options[0] = new Option("---Select PCC/Location---", "");
	      	      	 $("#loc").get(0).options[1] = new Option("---Select All---", "ALL");
	      	  	    	$.each(data, function (key,obj) {
	      	  	    		$("#loc").get(0).options[$("#loc").get(0).options.length] = new Option(obj, key);
	      	  	    		
	      	  	    });  	   
               });
			}
        });
		
     });		
</script>
<style type="text/css">
	.red {  color:#cd2026;  }
</style>
</head>
<body  >
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div role="main">
	<table id="mid">
		<form:form method="POST" modelAttribute="reportsForm" class="form-signin" action="${pageContext.request.contextPath}/${SS_USER_FOLDER}/getMacJurisReport" id="reportsFormId">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
							
							<div class="table-users" style="width: 80%">
								<div class="header">Search Report Screen</div>	
								<br/>
								
				             <div class="row "  style="margin-top: 10px">
				                <div class="col-md-8 col-md-offset-1 form-container">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->
				                   <input type="hidden" id="userRole" value='${SS_LOGGED_IN_USER_ROLE}'/>
				                   <div class="row">
			                            <div class="col-sm-10 form-group">
			                            <label for="reportType"> Report Type:</label>
											<form:radiobutton path="mainReportSelect" value="ScoreCard" title="Choose Scorecard"/>&nbsp;Scorecard &nbsp;										                            
										  	<form:radiobutton path="mainReportSelect" value="Compliance" title="Choose Compliance"/>&nbsp;Compliance &nbsp;
										  	<form:radiobutton path="mainReportSelect" value="Rebuttal" title="Choose Rebuttal" />&nbsp;Rebuttal &nbsp;
										  	<form:radiobutton path="mainReportSelect" value="Qasp" title="Choose QASP" />&nbsp;QASP Report &nbsp;
			                         	     
			                            </div>
			                        </div>
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> MAC:</label>
			                               
										<form:select path="macId" class="form-control required" id="macId" required="true" title="Select one Medical Administrative Contractor ID from the List">
										   <form:option value="" label="---Select MAC---"/>
										   <form:option value="ALL" label="---Select ALL---"/>
										   <form:options items="${macIdMap}" />
										</form:select> 									
										
			                            </div>
			                            <div class="col-sm-6 form-group" id="jurisMultiSelect">
			                                <label for="jurisdictionIds"> Jurisdiction:</label>
										<form:select path="jurisdictionIds" class="form-control required" id="jurisdictionIds" required="true" multiple="true" title="Select one or multiple Jurisdiction from the list">
										  <form:option value="" label="---Select Jurisdiction---"/>
										   <form:option value="ALL" label="---Select ALL---"/>
										   <form:options items="${jurisMapEdit}" />
										</form:select> 				
			                            </div>
			                            <div class="col-sm-6 form-group" id="jurisSingleSelect">
			                                <label for="jurisId"> Jurisdiction:</label>
										<form:select path="jurisId" class="form-control required" id="jurisId" required="true" title="Select one Jurisdiction from the list">
										  <form:option value="" label="---Select Jurisdiction---"/>										   
										   <form:options items="${jurisMapEdit}" />
										</form:select> 				
			                            </div>
			                        </div>
			                         <div class="row" id="programPccLocDiv">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Program:</label>
										<form:select path="programId" class="form-control required" id="programId" required="true" title="Select one Program from the List">
										   <form:option value="" label="---Select Program---"/>
										    <form:option value="ALL" label="---Select ALL---"/>
										   <form:options items="${programMapEdit}" />
										</form:select> 	
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> PCC/Location:</label>
			                                <form:select path="loc" class="form-control required" id="loc" title="Select one Provider Contact Centers/Location from the List">
			                                	<form:option value="" label="---Select PCC/Location---"/>
											   <form:option value="ALL" label="---Select ALL---"/>
										   		<form:options items="${locationMapEdit}" />
											</form:select> 
			                            </div>
			                        </div>
			                        
			                         <div class="row" id="datesDiv">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> From Date:</label>
			                             
										<form:input type = "text" class="form-control required" id="fromDateString" name = "fromDateString" path="fromDateString" required="true" title="Choose From Date from the Calendar" readonly="true"/>
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> To Date:</label>
			                                <form:input type = "text" class="form-control required"  id="toDateString" name = "toDateString" path="toDateString"  required="true" title="Choose To Date from the List" readonly="true"/>
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-6 form-group" id="scoreCardTypeDiv">
			                                <label for="scoreCardType"> Scorecard Type:</label> 
										  	<form:select path="scoreCardType" class="form-control required" id="scoreCardType" title="Select one ScoreCard Type from the List" >
											   	<form:option value="" label="---Select Scorecard Type---"/>
											   	<form:option value="ALL" label="---Select ALL---"/>
											  	<form:option value="Scoreable" />
											  	<form:option value="Non-Scoreable" />
											  	<form:option value="Does Not Count" />											  	
											</form:select> 
			                            </div>
			                            <div class="col-sm-6 form-group" id="complianceTypeDiv">
			                                <label for="complianceReportType"> Compliance Type:</label> 
										  	<form:select path="complianceReportType" class="form-control required" id="complianceReportType" title="Select one Compliance Type from the List" >
											   <form:option value="" label="---Select Compliance Type---"/>
											   	<form:option value="ALL" label="---Select ALL---"/>
											  	<form:option value="Compliant" />
											  	<form:option value="Non-Compliant" /> 	
											</form:select> 
			                            </div>
			                            <div class="col-sm-6 form-group" id="callResultDiv">
			                            <label for="callResult"> Call Result:</label> 
			                                <form:select path="callResult" class="form-control required" id="callResult" title="Select one Call Result from the List" >
											   	<form:option value="" label="---Select Call Result---"/>
											   	<form:option value="ALL" label="---Select ALL---"/>
											  	<form:option value="Fail" />											  										  	
											</form:select> 
			                            </div>
			                            <div class="col-sm-6 form-group" id="callCategoryTypeDiv">
			                            <label for="callCategoryType"> Call Category:</label> 
			                               <form:select path="callCategoryType" class="form-control required" id="callCategoryType" title="Select one Call Category Type from the List">
											   	<form:option value="" label="---Select Call Category---"/>
											   	<form:option value="ALL" label="---Select ALL---"/>
											  	<form:options items="${callCategoryMap}" />										  	
											</form:select> 		
			                            </div>
			                            <div class="col-sm-6 form-group" id="rebuttalStatusDiv">
			                            <label for="rebuttalStatus"> Rebuttal Status:</label> 
			                                <form:select path="rebuttalStatus" class="form-control required" id="rebuttalStatus" title="Select one Rebuttal Status from the List">
											   <form:option value="" label="---Select Rebuttal Status---"/>
											   	<form:option value="ALL" label="---Select ALL---"/>
											  	<form:option value="Completed" />
											  	<form:option value="Pending" />											  										  	
											</form:select> 
			                            </div>
			                        </div>
			                        
			                          <table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td><span><button class="btn btn-primary" id="generateReport" type="submit" title="Select Generate Report button to Generate Report">Generate Report</button></span>
									<span><button class="btn btn-primary" onclick="resetFields();" type="button" title="Select Reset button to Reset the results">Reset</button></span></td>
							       </tr>
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