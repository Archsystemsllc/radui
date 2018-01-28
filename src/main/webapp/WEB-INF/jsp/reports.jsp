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

    	$('#fromDateString').datepicker({
    		maxDate: 0,
    		format: "mm/dd/yyyy"
    	});    

    	$('#toDateString').datepicker({
    		maxDate: 0,
    		format: "mm/dd/yyyy"
    	});  	
 	
	});

	$(function(){

		$("input[name='mainReportSelect']").change(function(){
            var mainReportSelect = $(this).val();
            if (mainReportSelect=="ScoreCard") {
            	$('#scoreCardTypeDiv').show();
            } else if (mainReportSelect=="Compliance") {
            	$('#scoreCardTypeDiv').hide();
            } else if (mainReportSelect=="Rebuttal") {
            	$('#scoreCardTypeDiv').hide();
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
      	      	 $("#jurisId").get(0).options[0] = new Option("Select Jurisdiction", "");
      	      	 $("#jurisId").get(0).options[1] = new Option("Select All", "ALL");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#jurisId").get(0).options[$("#jurisId").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
        });
		$("select#jurisId").change(function(){
            $.getJSON("${pageContext.request.contextPath}/admin/selectProgram",{macId: $('#macId').val(),jurisId: $(this).val()}, function(data){
                
                 $("#programId").get(0).options.length = 0;	           
      	      	 $("#programId").get(0).options[0] = new Option("Select Program", "");
      	      	 $("#programId").get(0).options[1] = new Option("Select All", "ALL");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#programId").get(0).options[$("#programId").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
        });

		$("select#programId").change(function(){
            $.getJSON("${pageContext.request.contextPath}/admin/selectLocation",{macId: $('#macId').val(),jurisId: $('#jurisId').val(),programId: $(this).val()}, function(data){
                
                 $("#loc").get(0).options.length = 0;	           
      	      	 $("#loc").get(0).options[0] = new Option("Select Location", "");
      	      	 $("#loc").get(0).options[1] = new Option("Select All", "ALL");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#loc").get(0).options[$("#loc").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
        });

		
        
     });		
</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>

	<table id="mid">
		<form:form method="POST" modelAttribute="reportsForm" class="form-signin" action="${pageContext.request.contextPath}/admin/getMacJurisReport" id="reportsForm">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
							
							<div class="table-users" style="width: 80%">
								<div class="header">Search Report Screen</div>	
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
										<form:select path="jurisId" class="form-control required" id="jurisId" required="true">
										   <form:option value="" label="---Select Jurisdiction---"/>
										    <form:option value="ALL" label="---Select All---"/>
										   <form:options items="${jurisMapEdit}" />
										</form:select> 				
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Program:</label>
										<form:select path="programId" class="form-control required" id="programId"  >
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
			                                
			                               
											<form:radiobutton path="mainReportSelect" value="ScoreCard" />ScoreCard											                            
										  	<form:radiobutton path="mainReportSelect" value="Compliance" />Compliance
										  	<form:radiobutton path="mainReportSelect" value="Rebuttal" />Rebuttal
			                            </div>
			                        </div>
			                        
			                         <div class="row">
			                            <div class="col-sm-6 form-group" id="scoreCardTypeDiv">
			                                <label for="scoreCardType"> ScoreCard Type:</label> 
										  	<form:select path="scoreCardType" class="form-control required" id="scoreCardType" >
											   	<form:option value="" label="--- Select Type---"/>
											  	<form:option value="Scoreable" />
											  	<form:option value="Non-Scoreable" />
											  	<form:option value="Does Not Count" />											  	
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
			                        </div>
			                        
			                          <table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td><span><button class="btn btn-primary" id="create" type="submit">Generate Repot</button></span>
									<span><button class="btn btn-primary" id="reset" type="button">Reset</button></span></td>
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