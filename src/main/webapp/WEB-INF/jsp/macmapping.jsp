 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>QAM - Mac Info</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/Comrad_icon.png" />
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

<style type="text/css">
	.red {  color:red;  }
</style>
<script type="text/javascript">

	$(document).ready(function () {

		$('.required').each(function(){
		       $(this).prev('label').after("<span class='red'>*</span>");
		});

		$("#dialog-confirm").hide();
		$("#complete-confirm").hide();
			
    	var username="qamadmin";
  	   	var password="123456";

    	$('#alertMsg').text('');
    	
    	var failureBlockDisplay = false;

    	$('#qamStartDate').datepicker({    		
    		format: "mm/dd/yyyy",
    		onSelect: function(selected) {
    			$("#qamEndDate").datepicker("option","minDate", selected)
    		}

    	});    

    	$('#qamEndDate').datepicker({    		
    		format: "mm/dd/yyyy",
    		onSelect: function(selected) {
    			$("#qamStartDate").datepicker("option","maxDate", selected)
    		}
    			    		
    	});  

    	
	});

	$(function(){

		/* //Select Jurisdiction Functionality
		$("select#macId").change(function(){
			
			var userRole = $('#userRole').val();			
			if ((userRole != "MAC Admin") && (userRole != "MAC User")){
			
			var macIdValue = $(this).val();
			
            $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectJuris",                    
                    {macId: macIdValue, multipleInput: false}, function(data){
               
                 $("#jurisdictionId").get(0).options.length = 0;	           
      	      	 $("#jurisdictionId").get(0).options[0] = new Option("---Select Jurisdiction---", "");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#jurisdictionId").get(0).options[$("#jurisdictionId").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
			}
        });

		//Select Program Functionality
		$("select#jurisdictionId").change(function(){
			
			var userRole = $('#userRole').val();			
			if ((userRole != "MAC Admin") && (userRole != "MAC User")){

			var macIdValue = $('#macId').val();
            $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectProgram",{macId: macIdValue,jurisId: $(this).val()}, function(data){
                
                 $("#programId").get(0).options.length = 0;	           
      	      	 $("#programId").get(0).options[0] = new Option("---Select Program---", "");
      	  	    	$.each(data, function (key,obj) {
      	  	    		$("#programId").get(0).options[$("#programId").get(0).options.length] = new Option(obj, key);
      	  	    		
      	  	    	});  	   
               });
			}
        });

		$("select#programId").change(function(){

			var selectedJurisdiction =""; 
				
	        $("#jurisdictionId :selected").each(function() {
						 selectedJurisdiction+=($(this).attr('value'))+",";
			   });
		       
				
	         $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectLocation",{macId: $('#macId').val(),jurisId: selectedJurisdiction,programId: $(this).val(),programIdAvailableFlag: true}, function(data){
	                
	                 $("#pccId").get(0).options.length = 0;	           
	      	      	 $("#pccId").get(0).options[0] = new Option("---Select PCC/Location---", "");
	      	      	
	      	  	    	$.each(data, function (key,obj) {
	      	  	    		$("#pccId").get(0).options[$("#pccId").get(0).options.length] = new Option(obj, key);
	      	  	    		
	      	  	    });  	   
               });
			
        }); */

		

		 $('#close2').click(function(e) {	
			 e.preventDefault();		
	          $("#dialog-confirm" ).dialog({
	              resizable: false,
	              height: "auto",
	              width: 400,
	              modal: true,	              
	              buttons: {
	                "Yes": function() {
	              		$( this ).dialog("close");	              		
	              		window.location.href= '${pageContext.request.contextPath}/${SS_USER_FOLDER}/macmappinglist';          	 	
	                },
	                Cancel: function() {                    
	                	$( this ).dialog("close"); 
	                }
	              }
            });
	     }); 

		
	});
</script>
<script>

</script>
</head>
<body >
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div id="dialog-confirm" title="Close MAC Mapping Form Confirmation?">
  		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Do you want to close the MAC Mapping form without saving?</p>
	</div>
	
	<table id="mid">
		<form:form method="POST" modelAttribute="macProgJurisPccMapping" enctype="multipart/form-data"  class="form-signin" action="${pageContext.request.contextPath}/${SS_USER_FOLDER}/saveOrUpdateMacProgJurisPccMapping" id="macMappingId">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
							
							<div class="table-users" style="width: 80%">
								<div class="header">								
								<c:if test="${macProgJurisPccMapping.id == null || macProgJurisPccMapping.id == 0}">
										<span>Save MAC Mapping</span>
									</c:if>
									<c:if test="${macProgJurisPccMapping.id > 0}">
										<span>Update MAC Mapping</span>
									</c:if>
								</div>	
											
      							 <div class="row " style="margin-top: 10px">
				                <div class="col-md-8 col-md-offset-1 form-container">
				                   
				                   <form:hidden path="id" />
				                  
				                    
			                         <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> MAC:</label>
			                               
										<form:select path="macId" class="form-control required" id="macId" required="true" title="Select one Medical Administrative Contractor ID from the List">
										   <form:option value="" label="---Select MAC---"/>
										  
										   <form:options items="${macIdMapEdit}" />
										</form:select> 									
										
			                            </div>
			                            <div class="col-sm-6 form-group" id="jurisMultiSelect">
			                                <label for="jurisdictionId"> Jurisdiction:</label>
										<form:select path="jurisdictionId" class="form-control required" id="jurisdictionId" required="true"  title="Select one or multiple Jurisdiction from the list">
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
										   
										   <form:options items="${programMapEdit}" />
										</form:select> 	
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> PCC/Location:</label>
			                                <form:select path="pccId" class="form-control required" id="pccId" title="Select one Provider Contact Centers/Location from the List">
			                                	<form:option value="" label="---Select PCC/Location---"/>
											   
										   		<form:options items="${locationMapEdit}" />
											</form:select> 
			                            </div>
			                        </div>                 
			                       
				                </div>
				            </div> 
				            <table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td>
									<c:if test="${macProgJurisPccMapping.id == null || macProgJurisPccMapping.id == 0}">
										<span><button class="btn btn-primary" id="create"  type="submit">Save</button></span>
									</c:if>
									<c:if test="${macProgJurisPccMapping.id > 0}">
										<span><button class="btn btn-primary" id="update"  type="submit">Update</button></span>
									</c:if>
									<span><button class="btn btn-primary" id="close2" type="button">Close</button></span></td>
							       </tr>
							</table>
								
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