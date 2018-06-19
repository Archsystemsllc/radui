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
<title>QAM - My Account</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css"
	rel="stylesheet" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/Comrad_icon.png" />
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
	//Required Fields Logic
	$('.required').each(function(){
	       $(this).prev('label').after("<span class='red'><strong>*</strong></span>");
	});

	document.getElementById("update").disabled = false; 

});

function activateButton(element) {	
	 
	  if(element.checked) {
  	  
        document.getElementById("update").disabled = false; 
  		 
     }
     else  {
  	  
        document.getElementById("update").disabled = true;      
		  
    }

}
</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div role="main">
		<table id="mid">
			
 	<form:form method="POST" modelAttribute="userForm" action="${pageContext.request.contextPath}/${SS_USER_FOLDER}/change-password" class="form-signin">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">

							<div class="table-users" style="width: 80%">
								<div class="header">Change Password</div>
								<div class="row">


									<table
										style="border-collapse: separate; border-spacing: 2px; valign: middle"
										id='table1'>
										<tr>
										
			             
										</tr>
									</table>
									
									<div class="row" style="margin-top: 10px">
									<form:input type = "hidden" path="id" />
									 <div class="col-lg-8 col-lg-offset-1 form-container">
									 <c:if test="${not empty success}">
					                 	<div class="successblock" ><spring:message code="${success}"></spring:message>
					                    </div>
					                 </c:if>
								
									<div class="row" style="margin-top: 10px">
			                            <div class="col-lg-6 form-group">
			                                <label for="userName"> Username/Email Address:</label> 
											 <spring:bind path="userName">
											 <div class="form-group ${status.error ? 'has-error' : ''}">

								                <form:input type="text" path="userName" class="form-control" placeholder=""
								                            autofocus="true" readonly="true" title="Edit Username field"></form:input>

								                
								            </div>
								            </spring:bind>
			                            </div>
			                           
			                        </div>
			                        <div class="row" >
			                        <div class="col-lg-6 form-group">
			                            <table border="1">
			                            <tr><td>
			                            <h2>Password Rules</h2>
			                              <ul>Your new password must:
			                              	<li>Contain a minimum of 8 characters</li>
			                              	<li>Contain at least 1 letter</li>
			                              	<li>Contain at least 1 number</li>
			                              	<li>Contain at least 1 of the following characters: !@#$/-_+\</li>
			                              	<li>Be different from the last six passwords</li>
			                              </ul>
			                              </td></tr>
			                           </table>
			                            </div>
			                        </div>
			                        	
			                         <div class="row">
			                            <div class="col-lg-6 form-group">
			                               <label for="password"> Enter new password:</label> 
											 <spring:bind path="password">
											 <div class="required ${status.error ? 'has-error' : ''}">
								                <form:input type="password" path="password" class="form-control required" placeholder=""></form:input>
								                <form:errors path="password" title="Please fill out password field" required="true"></form:errors>
								            </div>
								            </spring:bind>
			                            </div>
			                            
			                        </div>
			                         <div class="row">
			                            
			                            <div class="col-lg-6 form-group">
			                                <label for="passwordConfirm"> Confirm your new password:</label> 
											<spring:bind path="passwordConfirm">
											<div class="required ${status.error ? 'has-error' : ''}">
								                <form:input type="password" path="passwordConfirm" class="form-control required"
								                            placeholder="" title="Please fill out confirm password field" required="true"></form:input>
								                <form:errors path="passwordConfirm"></form:errors>
								            </div>
								            </spring:bind>
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-10 form-group">
			                                
			                                <input type="checkbox" name="terms" id="terms" onchange="activateButton(this)">&nbsp; I Agree that I have read the <a target="_blank" href="https://www.hhs.gov/about/agencies/asa/ocio/cybersecurity/rules-of-behavior-for-use-of-hhs-information-resources/index.html">Rules of Behavior for Use of HHS Information Resources</a>.</input>	                                
			                               
			                                    
			                                <br/>
			                             
			                            </div>		                           
			                        </div>       
									
									
				                	<div class="row">
			                            <div class="col-lg-6 form-group">
			                            <button class=" btn btn-primary" id="update" >Update Password</button>
			                            <span><button class="btn btn-primary" id="close1" type="button" onclick="location.href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/myaccount'">Close</button></span>
			                            </div>
			                            <div class="col-lg-6 form-group">
			                            	
			                        	</div>				                    
				                	</div>		
				                	
				                	
										<form:input type="hidden" path="createdBy" value="${pageContext.request.userPrincipal.name}"/>
										
									
									
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