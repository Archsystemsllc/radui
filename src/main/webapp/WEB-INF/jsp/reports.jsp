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
<title>QAM - ScoreCard</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/adda_ico.png" />
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

	$(document).ready(function () {    	

		$('#scoreCardTypeDiv').hide();
		$('#callResultDiv').hide();
		$('#complianceTypeDiv').hide();
		$('#callCategoryTypeDiv').hide();
		$('#rebuttalStatusDiv').hide();

    	$('#fromDateString').datepicker({
    		maxDate: 0,
    		format: "mm/dd/yyyy"
    	});    

    	$('#toDateString').datepicker({
    		maxDate: 0,
    		format: "mm/dd/yyyy"
    	});  

    	
    	 var reportSelectValue=$('input:radio[name=mainReportSelect]:checked').val();
    	
    	if (reportSelectValue == '') {    		
    		$('input:radio[name=mainReportSelect]')[0].checked = true;
    		$('#scoreCardTypeDiv').show();
    		$('#callResultDiv').show();
        } else if (reportSelectValue == 'ScoreCard' ) {
        	$('input:radio[name=mainReportSelect]')[0].checked = true;
    		$('#scoreCardTypeDiv').show();
    		$('#callResultDiv').show();
        } else if (reportSelectValue == 'Compliance' ) {
        	$('#complianceTypeDiv').show();
        } else if (reportSelectValue == 'Rebuttal' ) {
        	$('#callCategoryTypeDiv').show();
    		$('#rebuttalStatusDiv').show();
        }  

    	var mainReportSelect = $("input[name='mainReportSelect']:selected").val();

    	
            if (mainReportSelect=="ScoreCard") {
            	$('#scoreCardTypeDiv').show();
            	$('#callResultDiv').show();
            	$('#complianceTypeDiv').hide();
            	$('#callCategoryTypeDiv').hide();
        		$('#rebuttalStatusDiv').hide();
            } else if (mainReportSelect=="Compliance") {
            	$('#scoreCardTypeDiv').hide();
            	$('#callResultDiv').hide();
            	$('#complianceTypeDiv').show();
            	$('#callCategoryTypeDiv').hide();
        		$('#rebuttalStatusDiv').hide();
        		
            } else if (mainReportSelect=="Rebuttal") {
            	$('#scoreCardTypeDiv').hide();
            	$('#callResultDiv').hide();
            	$('#complianceTypeDiv').hide();
            	$('#callCategoryTypeDiv').show();
        		$('#rebuttalStatusDiv').show();
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


		$("#toDateString").change(function () {
		    var startDate = $("#fromDateString").val();
		    var endDate = $("#toDateString").val();

		    if ((Date.parse(startDate) >= Date.parse(endDate))) {
		        alert("To Date should be greater than From Date");
		        $("#fromDateString").val("");
		        $("#toDateString").val("");
		    }
		});

		$("input[name='mainReportSelect']").change(function(){
            var mainReportSelect = $(this).val();
            if (mainReportSelect=="ScoreCard") {
            	$('#scoreCardTypeDiv').show();
            	$('#callResultDiv').show();
            	$('#complianceTypeDiv').hide();
            	$('#callCategoryTypeDiv').hide();
        		$('#rebuttalStatusDiv').hide();
            } else if (mainReportSelect=="Compliance") {
            	$('#scoreCardTypeDiv').hide();
            	$('#callResultDiv').hide();
            	$('#complianceTypeDiv').show();
            	$('#callCategoryTypeDiv').hide();
        		$('#rebuttalStatusDiv').hide();
        		
            } else if (mainReportSelect=="Rebuttal") {
            	$('#scoreCardTypeDiv').hide();
            	$('#callResultDiv').hide();
            	$('#complianceTypeDiv').hide();
            	$('#callCategoryTypeDiv').show();
        		$('#rebuttalStatusDiv').show();
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
            $.getJSON("${pageContext.request.contextPath}/admin/selectJuris",                    
                    {macId: $(this).val(), multipleInput: false}, function(data){
               
                 $("#jurisId").get(0).options.length = 0;	           
      	      	 $("#jurisId").get(0).options[0] = new Option("---Select All---", "ALL");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#jurisId").get(0).options[$("#jurisId").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
        });
        
		$("#jurisId").change(function(){
			
			 var selectedJurisdiction =""; 
			 $("#jurisId :selected").each(function() {
				 selectedJurisdiction+=($(this).attr('value'))+",";
				});
			
            $.getJSON("${pageContext.request.contextPath}/admin/selectProgram",{macId: $('#macId').val(),jurisId: selectedJurisdiction}, function(data){
                
                 $("#programId").get(0).options.length = 0;	           
      	      	 $("#programId").get(0).options[0] = new Option("---Select Program---", "");
      	      	 $("#programId").get(0).options[1] = new Option("---Select All---", "ALL");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#programId").get(0).options[$("#programId").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
        });

		$("select#programId").change(function(){

			var selectedJurisdiction =""; 
			 $("#jurisId :selected").each(function() {
				 selectedJurisdiction+=($(this).attr('value'))+",";
				});
            $.getJSON("${pageContext.request.contextPath}/admin/selectLocation",{macId: $('#macId').val(),jurisId: selectedJurisdiction,programId: $(this).val()}, function(data){
                
                 $("#loc").get(0).options.length = 0;	           
      	      	 $("#loc").get(0).options[0] = new Option("---Select PCC/Location---", "");
      	      	 $("#loc").get(0).options[1] = new Option("---Select All---", "ALL");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#loc").get(0).options[$("#loc").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
        });
		
     });		
</script>
</head>
<body  >
	<jsp:include page="admin_header.jsp"></jsp:include>

	<table id="mid">
		<form:form method="POST" modelAttribute="reportsForm" class="form-signin" action="${pageContext.request.contextPath}/admin/getMacJurisReport" id="reportsFormId">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
							
							<div class="table-users" style="width: 80%">
								<div class="header">Search Report Screen</div>	
								<br/>
								<div id="sessionTimer" align="center" style="" ></div>
								<br/>
				             <div class="row " >
				                <div class="col-md-8 col-md-offset-1 form-container">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->
				                   
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> MAC:</label>
			                               
										<form:select path="macId" class="form-control required" id="macId" required="true">
										   <form:option value="" label="---Select MAC---"/>
										   <form:option value="ALL" label="---Select All---"/>
										   <form:options items="${macIdMap}" />
										</form:select> 									
										
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> Jurisdiction:</label>
										<form:select path="jurisId" class="form-control required" id="jurisId" required="true" multiple="true">
										  
										   <form:options items="${jurisMapEdit}" />
										</form:select> 				
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Program:</label>
										<form:select path="programId" class="form-control required" id="programId" required="true" >
										   <form:option value="" label="---Select Program---"/>
										    <form:option value="ALL" label="---Select All---"/>
										   <form:options items="${programMapEdit}" />
										</form:select> 	
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> PCC/Location:</label>
			                                <form:select path="loc" class="form-control required" id="loc" >
			                                	<form:option value="" label="---Select PCC/Location---"/>
											   <form:option value="ALL" label="---Select All---"/>
										   		<form:options items="${locationMapEdit}" />
											</form:select> 
			                            </div>
			                        </div>
			                        
			                         <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> From Date:</label>
			                             
										<form:input type = "text" class="form-control" id="fromDateString" name = "fromDateString" path="fromDateString" required="true"/>
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> To Date:</label>
			                                <form:input type = "text" class="form-control" id="toDateString" name = "toDateString" path="toDateString"  required="true"/>
			                            </div>
			                        </div>
			                        
			                        <div class="row">
			                            <div class="col-sm-6 form-group">			                                 
											<form:radiobutton path="mainReportSelect" value="ScoreCard" />&nbsp;ScoreCard &nbsp;										                            
										  	<form:radiobutton path="mainReportSelect" value="Compliance" />&nbsp;Compliance &nbsp;
										  	<form:radiobutton path="mainReportSelect" value="Rebuttal" />&nbsp;Rebuttal &nbsp;
			                            </div>
			                        </div>
			                        
			                         <div class="row">
			                            <div class="col-sm-6 form-group" id="scoreCardTypeDiv">
			                                <label for="scoreCardType"> ScoreCard Type:</label> 
										  	<form:select path="scoreCardType" class="form-control required" id="scoreCardType" >
											   	<form:option value="" label="ALL"/>
											  	<form:option value="Scoreable" />
											  	<form:option value="Non-Scoreable" />
											  	<form:option value="Does Not Count" />											  	
											</form:select> 
			                            </div>
			                            <div class="col-sm-6 form-group" id="complianceTypeDiv">
			                                <label for="complianceReportType"> Compliance Type:</label> 
										  	<form:select path="complianceReportType" class="form-control required" id="complianceReportType" >
											   	<form:option value="" label="ALL"/>
											  	<form:option value="Compliant" />
											  	<form:option value="Non-Compliant" /> 	
											</form:select> 
			                            </div>
			                            <div class="col-sm-6 form-group" id="callResultDiv">
			                            <label for="callResult"> Call Result:</label> 
			                                <form:select path="callResult" class="form-control required" id="callResult" >
											   	<form:option value="All" Label="Both Pass and Fail"/>
											  	<form:option value="Pass" />
											  	<form:option value="Fail" />											  										  	
											</form:select> 
			                            </div>
			                            <div class="col-sm-6 form-group" id="callCategoryTypeDiv">
			                            <label for="callCategoryType"> Call Category:</label> 
			                               <form:select path="callCategoryType" class="form-control" id="callCategoryType" >
											   	<form:option value="ALL" label="ALL"/>
											  	<form:options items="${callCategoryMap}" />										  	
											</form:select> 		
			                            </div>
			                            <div class="col-sm-6 form-group" id="rebuttalStatusDiv">
			                            <label for="rebuttalStatus"> Rebuttal Status:</label> 
			                                <form:select path="rebuttalStatus" class="form-control required" id="rebuttalStatus" >
											   	<form:option value="All" Label="ALL"/>
											  	<form:option value="Completed" />
											  	<form:option value="Pending" />											  										  	
											</form:select> 
			                            </div>
			                        </div>
			                        
			                          <table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td><span><button class="btn btn-primary" id="generateReport" type="submit">Generate Report</button></span>
									<span><button class="btn btn-primary" id="reset" type="reset">Reset</button></span></td>
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
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>