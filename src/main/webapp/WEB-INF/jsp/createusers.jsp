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
<title>QAM - CreateUsers</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css"
	rel="stylesheet" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/adda_ico.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/button.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui-timepicker-addon.css" />

<link rel="stylesheet" href="/resources/demos/style.css" />
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>
<link rel="stylesheet"
	href="https://jqueryvalidation.org/files/demo/css/screen.css"></link>

<!-- CSS for Bootstrap -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet"></link>
<!-- JQuery -->
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.33.3/es6-shim.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/systemjs/0.19.20/system-polyfills.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-ui-timepicker-addon.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.16.0/jquery.validate.min.js"></script>
	
<style type="text/css">
	.red {  color:#cd2026;  }
</style>
	
<script type="text/javascript">

$(document).ready(function () {
	$("#dialog-confirm").hide();

	$('.required').each(function(){
	       $(this).prev('label').after("<span class='red'>*</span>");
	});

	var selectedOrganization="${userForm.organizationLookup.id}";
	
	var selectedRole="${userForm.role.id}";
	
	 if(selectedOrganization==1) {	
		 $('#macId,#jurId,#pccId').attr("required",false);
		 $("#macIdBlock,#jurIdBlock,#pccIdBlock").hide();	
		 
		 	
	 } else if(selectedOrganization==2) {	
		 $('#macId,#jurId,#pccId').attr("required",false);
		 $("#macIdBlock,#jurIdBlock,#pccIdBlock").hide();
		 	
	 } else if(selectedOrganization==3) {	
		 $('#macId,#jurId,#pccId').attr("required",true);
		 $("#macIdBlock,#jurIdBlock,#pccIdBlock").show();
		 	
	 }	

	 $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectRole",                    
                {organizationId: selectedOrganization}, function(data){
           
             $("#roleId").get(0).options.length = 0;	           
  	      	 $("#roleId").get(0).options[0] = new Option("---Select Role---", "");
  	  	    	$.each(data, function (key,obj) {
  	  	    		$("#roleId").get(0).options[$("#roleId").get(0).options.length] = new Option(obj, key);     	  	    		
  	  	    	});  	 

  	  	    $("#roleId select").val(selectedRole);      
   });	   

	
	
	
});

$(function(){
	$("select#organizationLookupId").change(function(){
		
		 var selectedOrganization = $(this).val();
		 if(selectedOrganization==1) {	
			 $('#macId,#jurId,#pccId').attr("required",false);
			 $("#macIdBlock,#jurIdBlock,#pccIdBlock").hide();	
			 
			 	
		 } else if(selectedOrganization==2) {	
			 $('#macId,#jurId,#pccId').attr("required",false);
			 $("#macIdBlock,#jurIdBlock,#pccIdBlock").hide();
			 	
		 } else if(selectedOrganization==3) {	
			 $('#macId,#jurId,#pccId').attr("required",true);
			 $("#macIdBlock,#jurIdBlock,#pccIdBlock").show();
			 	
		 }	

		 $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectRole",                    
	                 {organizationId: selectedOrganization}, function(data){
	            
	              $("#roleId").get(0).options.length = 0;	           
	   	      	 $("#roleId").get(0).options[0] = new Option("---Select Role---", "");
	   	  	    	$.each(data, function (key,obj) {
	   	  	    		$("#roleId").get(0).options[$("#roleId").get(0).options.length] = new Option(obj, key);     	  	    		
	   	  	    	});  	   
	    });
			
		   
	});
	
	$("select#macId").change(function(){
		 
           $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectJuris",                    
                   {macId: $(this).val(), multipleInput: false}, function(data){
              
                $("#jurId").get(0).options.length = 0;	           
     	      	 $("#jurId").get(0).options[0] = new Option("---Select Jurisdiction---", "");
     	  	    	$.each(data, function (key,obj) {
     	  	    		$("#jurId").get(0).options[$("#jurId").get(0).options.length] = new Option(obj, key);     	  	    		
     	  	    	});  	   
              });
       });
       
	$("#jurId").change(function(){			
			 var selectedJurisdiction =""; 
			 $("#jurId :selected").each(function() {
				 selectedJurisdiction+=($(this).attr('value'))+",";
				});			
           $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectProgram",{macId: $('#macId').val(),jurisId: selectedJurisdiction}, function(data){
               
                $("#pccId").get(0).options.length = 0;	           
     	      	 $("#pccId").get(0).options[0] = new Option("---Select Program---", "");
     	      	 
     	  	    	$.each(data, function (key,obj) {
     	  	    		$("#pccId").get(0).options[$("#pccId").get(0).options.length] = new Option(obj, key);     	  	    		
     	  	    	});  	   
              });
      });

	 $('#close1').click(function(e) {	
		 e.preventDefault();		
          $("#dialog-confirm" ).dialog({
              resizable: false,
              height: "auto",
              width: 400,
              modal: true,	              
              buttons: {
                "Yes": function() {
              		$( this ).dialog("close");	              		
              		window.location.href= '${pageContext.request.contextPath}/${SS_USER_FOLDER}/listofusers';          	 	
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
	<div role="main" id="dialog-confirm" title="Close User Create Confirmation?">
  		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Do you want to close the User Create form without saving?</p>
	</div>
	<div>
		<table id="mid">	
		
 			<form:form method="POST" modelAttribute="userForm" action="${pageContext.request.contextPath}/${SS_USER_FOLDER}/createUser" class="form-signin">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">

							<div class="table-users" style="width: 80%">
								<div class="header">Create User</div>
								<div class="row">


									<table
										style="border-collapse: separate; border-spacing: 2px; valign: middle"
										id='table1'>
										<tr>
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
				                  <!--   <h2>User Information</h2>  -->
				                    <!-- <p> Please provide your feedback below: </p> -->
				                   
				                    <div class="row">
			                            <div class="col-lg-6 form-group">
			                                <label for="userName"> Username / Email Address:</label> 
											 <spring:bind path="userName">
											 <div class="required ${status.error ? 'has-error' : ''}">
								                <form:input type="text" path="userName" class="form-control required" 
								                            title="Please fill out Username field" required="true"></form:input>
								                <form:errors path="userName"></form:errors>
								            </div>
								            </spring:bind>
			                            </div>
			                            <div class="col-lg-6 form-group">
			                               
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-lg-6 form-group">
			                               <label for="password"> Password:</label> 
											 <spring:bind path="password">
											 <div class="required ${status.error ? 'has-error' : ''}">
								                <form:input title="Please fill out password field" type="password" path="password" class="form-control required" placeholder=""></form:input>
								                <form:errors path="password" title="Please fill out password field" required="true"></form:errors>
								            </div>
								            </spring:bind>
			                            </div>
			                            <div class="col-lg-6 form-group">
			                                <label for="passwordConfirm"> Confirm your password:</label> 
											<spring:bind path="passwordConfirm">
											<div class="required ${status.error ? 'has-error' : ''}">
								                <form:input type="password" path="passwordConfirm" class="form-control"
								                            placeholder="" title="Please fill out confirm password field" required="true"></form:input>
								                <form:errors path="passwordConfirm"></form:errors>
								            </div>
								            </spring:bind>
			                            </div>
			                        </div>
			                        
			                        <div class="row">
			                            <div class="col-lg-6 form-group">
			                              <label for="firstName"> First Name:</label> 
											<spring:bind path="firstName">
											<div class="required ${status.error ? 'has-error' : ''}">
								                <form:input type="text" path="firstName" class="form-control" placeholder=""
								                            title="Please fill out First Name field" required="true"></form:input>
								                <form:errors path="firstName"></form:errors>
								            </div>
								            </spring:bind>
			                            </div>
			                            <div class="col-lg-6 form-group">
			                                <label for="middleName"> Middle Name:</label> 
											 <div class="form-group ${status.error ? 'has-error' : ''}">
								                <form:input type="text" path="middleName" class="form-control" placeholder=""
								                            title="Please fill out Middle Name field"></form:input>
								                <form:errors path="middleName"></form:errors>
			                            	</div>
			                        	</div>
				                    
				                	</div>
				                	<div class="row">
			                            <div class="col-lg-6 form-group">
			                              <label for="lastName"> Last Name:</label> 
			                               <spring:bind path="firstName">
											 <div class="required  ${status.error ? 'has-error' : ''}">
											
								                <form:input type="text" path="lastName" class="form-control" placeholder=""
								                            title="Please fill out Last Name Field" required="true"></form:input>
								                <form:errors path="lastName"></form:errors>
								            </div>
								           </spring:bind>
			                            </div>
			                            <div class="col-lg-6 form-group">
			                               
			                        	</div>				                    
				                	</div>
				                	<div class="row">
				                		<div class="col-lg-6 form-group">
			                            	<label for="organization"> Organization:</label> <form:select path="organizationLookup.id"
													class="form-control required" id="organizationLookupId" required="true" title="Select one organization from the list">
													<form:option value="" label="---Select Organization---" />
													<form:options items="${orgIds}" />
												</form:select>
			                                
			                        	</div>		
			                            <div class="col-lg-6 form-group">
			                             <label for="name"> Role:</label>
											<form:select path="role.id"
													class="form-control required" id="roleId" required="true" title="Select one role from the list">
													<form:option value="" label="---Select Role---" />
													<form:options items="${roleIds}" />
												</form:select>
			                            </div>
			                            		                    
				                	</div>
				                	<div class="row">
			                            <div class="col-lg-6 form-group" id="macIdBlock">
			                          
										<label for="name"> MAC:</label>
			                                <form:select path="macId" id="macId" class="form-control required" title="Select one Medicare Administrative Contractor ID from the list">
											   <form:option value="" label="---Select MAC---"/>
											   <form:options items="${macIds}" />
											</form:select> 	
			                            </div>
			                            <div class="col-lg-6 form-group"  id="jurIdBlock">
			                            
			                           	<label for="jurId"> Jurisdiction:</label>
			                               <form:select path="jurisidictionId"  id="jurId" class="form-control required" data-val="true"  multiple="true"  required="true" title="Select one or multiple jurisdiction from the list">
										   <form:option value="" label="---Select Jurisdiction---"/>		
										   <form:options items="${jurisMapEdit}" />						   
										</form:select>	
			                        	</div>				                    
				                	</div>
				                	<div class="row">
			                            <div class="col-lg-6 form-group" id="pccIdBlock">
			                          
										<label for="pccId"> PCC Location:</label>
										<form:select path="pccId"  id="pccId" class="form-control required" data-val="true" title="Select one PCC Location from the list">
										   <form:option value="" label="---Select PCC Location---"/>	
										    <form:options items="${programMapEdit}" />										   							   
										</form:select>	
			                            </div>
			                            <div class="col-lg-6 form-group">
			                            	<form:input type="hidden" path="createdBy" value="${pageContext.request.userPrincipal.name}" style ="padding-top: 40px;"/>
			                        	</div>				                    
				                	</div>
				                	
				                	<div class="row">
			                            <div class="col-lg-6 form-group">

			                             <button class=" btn btn-primary" title="Select Submit Button to Create New User">Create</button>
			                             <span><button class="btn btn-primary" id="close1" type="button">Close</button></span>
			                            </div>
			                            <div class="col-lg-6 form-group">
			                            	
			                        	</div>				                    
				                	</div>
				            </div>
									
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
