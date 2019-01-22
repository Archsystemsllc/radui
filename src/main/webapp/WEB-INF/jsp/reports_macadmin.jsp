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
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.23.0/moment.js"></script>


<script type="text/javascript">
	function resetFields() {
			
		$('#fromDateString').val("");
		$('#toDateString').val("");
		$('#macId').prop('selectedIndex',0);
		
		
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
		
		 
    	$('#fromDateString').datepicker({
    		maxDate: maxAllowedDate,
    		format: "mm/dd/yyyy",
    		onSelect: function(selected) {
    			$("#toDateString").datepicker("option","minDate", selected)
    		}

    	}); 
    	    	
    	var previousFromDate;

    	$("#fromDateString").focus(function(){   
    		previousFromDate= $(this).val(); ;
    	});
    	$("#fromDateString").blur(function(){   
    	     var newDate = $(this).val();    
    	     
    	    if (!moment(newDate, 'MM/DD/YYYY', true).isValid()){         
        	   
    	        $(this).val(previousFromDate);      
    	        console.log("Error");
    	    }  
    	});

    	$('#toDateString').datepicker({
    		maxDate: maxAllowedDate,
    		format: "mm/dd/yyyy",
    		onSelect: function(selected) {
    			$("#fromDateString").datepicker("option","maxDate", selected)
    		}
    			    		
    	}); 

    	var previousToDate;

    	$("#toDateString").focus(function(){   
    		previousToDate= $(this).val(); ;
    	});
    	$("#toDateString").blur(function(){   
    	     var newDate = $(this).val();    
    	     
    	    if (!moment(newDate, 'MM/DD/YYYY', true).isValid()){         
        	   
    	        $(this).val(previousToDate);      
    	        console.log("Error");
    	    }  
    	}); 

    	var reportSelectValue=$('input:radio[name=mainReportSelect]:checked').val();
    	
    	if (reportSelectValue == '') {    		
    		$('input:radio[name=mainReportSelect]')[0].checked = true;
    		$('#scoreCardTypeDiv,#callResultDiv,#programPccLocDiv,#callCategoryTypeDiv,#rebuttalStatusDiv,#datesDiv,#jurisMultiSelect').show();      			
    		$("#programId,#pccLocationId,#fromDateString,#toDateString,#jurisdictionIds,#scoreCardType,#callResult").attr('required',true);
    		$("#jurisId").removeAttr('required');    		
    		$('#jurisSingleSelect,#callCategoryTypeDiv,#rebuttalStatusDiv,#complianceTypeDiv').hide();
    		$('select[id="macId"] > option[value="ALL"]').show();    		
    		
        } else if (reportSelectValue == 'ScoreCard' ) {
                    	
    		$('#scoreCardTypeDiv,#callResultDiv,#programPccLocDiv,#callCategoryTypeDiv,#rebuttalStatusDiv,#datesDiv,#jurisMultiSelect').show();      		 		
    		$("#programId,#pccLocationId,#fromDateString,#toDateString,#jurisdictionIds,#scoreCardType,#callResult").attr('required',true);
    		$("#jurisId").removeAttr('required');    		
    		$('#jurisSingleSelect,#callCategoryTypeDiv,#rebuttalStatusDiv,#complianceTypeDiv').hide();
    		$('select[id="macId"] > option[value="ALL"]').show(); 
        } else if (reportSelectValue == 'Compliance' ) {
        	$('#scoreCardTypeDiv,#callResultDiv,#callCategoryTypeDiv,#rebuttalStatusDiv,#programPccLocDiv,#jurisSingleSelect').hide();            	
        	$('#complianceTypeDiv,#datesDiv,#jurisMultiSelect').show();
    		$("#fromDateString,#toDateString,#jurisdictionIds,#scoreCardType,#callResult").attr('required',true);         		
    		$("#jurisId,#programId,#pccLocationId,#scoreCardType,#callResult").removeAttr('required');
    		$('select[id="macId"] > option[value="ALL"]').show();
    		
        } else if (reportSelectValue == 'Rebuttal' ) {
        	$('#callCategoryTypeDiv,#rebuttalStatusDiv,#programPccLocDiv,#datesDiv,#jurisMultiSelect').show();        	    		
    		$("#programId,#pccLocationId,#fromDateString,#toDateString,#jurisdictionIds,#callCategoryType,#rebuttalStatus").attr('required',true);
    		$("#jurisId,#scoreCardType,#callResult").removeAttr('required');    		
    		$('#jurisSingleSelect,#complianceTypeDiv').hide();
    		$('select[id="macId"] > option[value="ALL"]').show(); 
    		
        }  else if (reportSelectValue == 'Qasp' ) {            
        	$('#jurisSingleSelect').show();
        	$("#scoreCardType,#callResult,#complianceReportType,#callCategoryType,#rebuttalStatus,#programId,#pccLocationId,#jurisdictionIds").removeAttr('required');   
        	$('#scoreCardTypeDiv,#callResultDiv,#complianceTypeDiv,#callCategoryTypeDiv,#rebuttalStatusDiv,#programPccLocDiv,#jurisMultiSelect').hide();
        	$('select[id="macId"] > option[value="ALL"]').hide();        	
        	$("#jurisId").attr('required',true);    				
    		$("#macId option[value='ALL']").hide();    		
        }  else if (reportSelectValue == 'Summary' ) {
                    	
    		$('#programPccLocDiv,#datesDiv,#jurisMultiSelect').show();  
    		$("#complianceReportType,#callCategoryType,#rebuttalStatus,#programId,#pccLocationId,#jurisdictionIds,#jurisId,#callResult,#scoreCardType").removeAttr('required');      		 		
    		$("#fromDateString,#toDateString,#scoreCardType").attr('required',true);
       		$('#jurisSingleSelect,#callCategoryTypeDiv,#rebuttalStatusDiv,#complianceTypeDiv,#callResultDiv,#scoreCardType').hide();
    		$('select[id="macId"] > option[value="ALL"]').show();    		
        }


    	$('.required').each(function(){
		       $(this).prev('label').after("<span class='red'><strong>*</strong></span>");
		});

    	var mainReportSelect = $("input[name='mainReportSelect']:selected").val();

    	
            if (mainReportSelect=="ScoreCard") {
            	$('#scoreCardTypeDiv,#callResultDiv,#programPccLocDiv,#callCategoryTypeDiv,#rebuttalStatusDiv,#datesDiv,#jurisMultiSelect').show();        		 		
        		$("#programId,#pccLocationId,#fromDateString,#toDateString,#jurisdictionIds,#scoreCardType,#callResult").attr('required',true);
        		$("#jurisId").removeAttr('required');    		
        		$('#jurisSingleSelect,#callCategoryTypeDiv,#rebuttalStatusDiv,#complianceTypeDiv').hide();
        		$('select[id="macId"] > option[value="ALL"]').show(); 
            } else if (mainReportSelect=="Compliance") {
            	$('#scoreCardTypeDiv,#callResultDiv,#callCategoryTypeDiv,#rebuttalStatusDiv,#programPccLocDiv,#jurisSingleSelect').hide();            	
            	$('#complianceTypeDiv,#datesDiv,#jurisMultiSelect').show();
        		$("#fromDateString,#toDateString,#jurisdictionIds,#scoreCardType,#callResult").attr('required',true);         		
        		$("#jurisId,#programId,#pccLocationId,#scoreCardType,#callResult").removeAttr('required');
        		$('select[id="macId"] > option[value="ALL"]').show();
        		
            } else if (mainReportSelect=="Rebuttal") {
            	$('#callCategoryTypeDiv,#rebuttalStatusDiv,#programPccLocDiv,#datesDiv,#jurisMultiSelect').show();            	  		
        		$("#programId,#pccLocationId,#fromDateString,#toDateString,#jurisdictionIds,#callCategoryType,#rebuttalStatus").attr('required',true);
        		$("#jurisId,#scoreCardType,#callResult").removeAttr('required');    		
        		$('#jurisSingleSelect,#complianceTypeDiv').hide();
        		$('select[id="macId"] > option[value="ALL"]').show(); 
            } else if (mainReportSelect=="Qasp") {
            	$('#jurisSingleSelect').show();
            	$("#scoreCardType,#callResult,#complianceReportType,#callCategoryType,#rebuttalStatus,#programId,#pccLocationId,#jurisdictionIds").removeAttr('required');   
            	$('#scoreCardTypeDiv,#callResultDiv,#complianceTypeDiv,#callCategoryTypeDiv,#rebuttalStatusDiv,#programPccLocDiv,#jurisMultiSelect').hide();
            	$('select[id="macId"] > option[value="ALL"]').hide();        	
            	$("#jurisId").attr('required',true);    				
        		$("#macId option[value='ALL']").hide();    		
            }else if (mainReportSelect == 'Summary' ) {
            	
            	$('#scoreCardTypeDiv,#programPccLocDiv,#datesDiv,#jurisMultiSelect').show();  
        		$("#complianceReportType,#callCategoryType,#rebuttalStatus,#programId,#pccLocationId,#jurisdictionIds,#jurisId,#callResult").removeAttr('required');      		 		
        		$("#fromDateString,#toDateString,#scoreCardType").attr('required',true);
           		$('#jurisSingleSelect,#callCategoryTypeDiv,#rebuttalStatusDiv,#complianceTypeDiv,#callResultDiv').hide();
        		$('select[id="macId"] > option[value="ALL"]').show(); 
        		$('select[id="scoreCardType"] > option[value="Scoreable"]').show(); 
            }

          
 		var scoreCardType = $("select#scoreCardType").val();
           if (scoreCardType=="Scoreable"  && mainReportSelect != 'Summary' ) {           
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

	$(function(){

		var mainReportSelect = "";
		$("input[name='mainReportSelect']").change(function(){
			mainReportSelect = $(this).val();
            if (mainReportSelect=="ScoreCard") {
            	$('#scoreCardTypeDiv,#callResultDiv,#callCategoryTypeDiv,#rebuttalStatusDiv,#programPccLocDiv,#datesDiv,#jurisMultiSelect').show();          		 		
        		$("#programId,#pccLocationId,#fromDateString,#toDateString,#jurisdictionIds,#scoreCardType,#callResult").attr('required',true);
        		$("#jurisId").removeAttr('required');    		
        		$('#jurisSingleSelect,#callCategoryTypeDiv,#rebuttalStatusDiv,#complianceTypeDiv').hide();  
        		$('select[id="macId"] > option[value="ALL"]').show();
        		resetFields();    		
        		
            } else if (mainReportSelect=="Compliance") {
                
            	$('#scoreCardTypeDiv,#callResultDiv,#callCategoryTypeDiv,#rebuttalStatusDiv,#programPccLocDiv,#jurisSingleSelect').hide();            	
            	$('#complianceTypeDiv,#datesDiv,#jurisMultiSelect').show();
        		$("#fromDateString,#toDateString,#jurisdictionIds,#scoreCardType,#callResult").attr('required',true);         		
        		$("#jurisId,#programId,#pccLocationId,#scoreCardType,#callResult").removeAttr('required');
        		$('select[id="macId"] > option[value="ALL"]').show();
        		resetFields();
        		
            } else if (mainReportSelect=="Rebuttal") {
            	$('#callCategoryTypeDiv,#rebuttalStatusDiv,#programPccLocDiv,#datesDiv,#jurisMultiSelect').show();            	    		
        		$("#programId,#pccLocationId,#fromDateString,#toDateString,#jurisdictionIds,#callCategoryType,#rebuttalStatus").attr('required',true);
        		$("#jurisId,#scoreCardType,#callResult").removeAttr('required');    		
        		$('#scoreCardTypeDiv,#callResultDiv,#jurisSingleSelect,#complianceTypeDiv').hide();
        		$('select[id="macId"] > option[value="ALL"]').show(); 
        		resetFields();
            } else if (mainReportSelect=="Qasp") {
            	$('#jurisSingleSelect').show();
            	$("#scoreCardType,#callResult,#complianceReportType,#callCategoryType,#rebuttalStatus,#programId,#pccLocationId,#jurisdictionIds").removeAttr('required');             	
            	$('#scoreCardTypeDiv,#callResultDiv,#complianceTypeDiv,#callCategoryTypeDiv,#rebuttalStatusDiv,#programPccLocDiv,#jurisMultiSelect').hide();
            	$('select[id="macId"] > option[value="ALL"]').hide();        	
            	$("#jurisId").attr('required',true);    				
        		$("#macId option[value='ALL']").hide();    
        		resetFields();	
            }else if (mainReportSelect == 'Summary' ) {
            	
            	$('#scoreCardTypeDiv,#programPccLocDiv,#datesDiv,#jurisMultiSelect').show();  
        		$("#complianceReportType,#callCategoryType,#rebuttalStatus,#programId,#pccLocationId,#jurisdictionIds,#jurisId,#callResult").removeAttr('required');      		 		
        		$("#fromDateString,#toDateString,#scoreCardType").attr('required',true);
           		$('#jurisSingleSelect,#callCategoryTypeDiv,#rebuttalStatusDiv,#complianceTypeDiv,#callResultDiv').hide();
        		$('select[id="macId"] > option[value="ALL"]').show(); 
        		$('select[id="scoreCardType"] > option[value="Scoreable"]').show(); 
            }
        });

		$("select#scoreCardType").change(function(){
			 var scoreCardType = $(this).val();
			 if (scoreCardType=="Scoreable"  && mainReportSelect != 'Summary') {           
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

		$("select#macId").change(function(){
			var userRole = $('#userRole').val();			
			if ((userRole != "MAC Admin") && (userRole != "MAC User")){
			
			var macIdValue = $(this).val();
			
	            $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectJuris",                    
	                    {macId: macIdValue , multipleInput: false}, function(data){
	                    	
	                 $("#jurisId").get(0).options.length = 0;	    
	                 $("#jurisId").get(0).options[0] = new Option("---Select Jurisdiction---", "");              
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
	                
	                 $("#pccLocationId").get(0).options.length = 0;	           
	      	      	 $("#pccLocationId").get(0).options[0] = new Option("---Select PCC/Location---", "");
	      	      	 $("#pccLocationId").get(0).options[1] = new Option("---Select All---", "ALL");
	      	  	    	$.each(data, function (key,obj) {
	      	  	    		$("#pccLocationId").get(0).options[$("#pccLocationId").get(0).options.length] = new Option(obj, key);
	      	  	    		
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
		<form:form method="POST" modelAttribute="reportsForm" class="form-signin" action="${pageContext.request.contextPath}/${SS_USER_FOLDER}/getMacAdminReport" id="reportsFormId">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
							
							<div class="table-users" style="width: 80%">
								<div class="header">MAC Admin Search Report Screen</div>	
								<br/>
								
				             <div class="row "  style="margin-top: 10px">
				                <div class="col-md-8 col-md-offset-1 form-container">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->
				                   <input type="hidden" id="userRole" value='${SS_LOGGED_IN_USER_ROLE}'/>
				                   <div class="row">
			                            <div class="col-sm-10 form-group">
			                            <label for="reportType"> Report Type:</label>
											<form:radiobutton path="mainReportSelect" value="ScoreCard" title="Choose Scorecard"/>&nbsp;Scorecard &nbsp;
										  	<form:radiobutton path="mainReportSelect" value="Rebuttal" title="Choose Rebuttal" />&nbsp;Rebuttal &nbsp;
										  	<form:radiobutton path="mainReportSelect" value="Summary" title="Choose Summary" />&nbsp;Summary&nbsp;
			                         	  
			                            </div>
			                        </div>
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> MAC:</label>
			                               
										<form:select path="macId" class="form-control required" id="macId" required="true" title="Select one Medical Administrative Contractor ID from the List">
										   <form:option value="" label="---Select MAC---"/>
										   <form:option value="ALL" label="---Select ALL---"/>
										   <form:options items="${macIdMapEdit}" />
										</form:select> 									
										
			                            </div>
			                            <div class="col-sm-6 form-group" id="jurisMultiSelect">
			                                <label for="jurisdictionIds"> Jurisdiction:</label>
										<form:select path="jurisdictionIds" class="form-control required" id="jurisdictionIds" required="true" multiple="true" title="Select one or multiple Jurisdiction from the list">
										  
										   <form:option value="ALL" label="---Select ALL---"/>
										   <form:options items="${jurisMapEdit}" />
										</form:select> 				
			                            </div>
			                            <div class="col-sm-6 form-group" id="jurisSingleSelect">
			                                <label for="jurisId"> Jurisdiction:</label>
										<form:select path="jurisId" class="form-control required" id="jurisId" required="true" title="Select one Jurisdiction from the list">
										  <form:option value="" label="---Select Jurisdiction---"/>		
										   <form:option value="ALL" label="---Select ALL---"/>								   
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
			                            
			                        </div>
			                        
			                         <div class="row" id="datesDiv">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> From Date:</label>
			                             
										<form:input type = "text" class="form-control required" id="fromDateString" name = "fromDateString" path="fromDateString" required="true" title="Choose From Date from the Calendar" />
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> To Date:</label>
			                                <form:input type = "text" class="form-control required"  id="toDateString" name = "toDateString" path="toDateString"  required="true" title="Choose To Date from the List" />
			                            </div>
			                        </div>
			                        <div class="row">
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