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

<meta http-equiv="content-type" content="text/html; charset=utf-8" />



<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>


<!-- CSS for Bootstrap -->

<!-- JQuery -->


<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.16.0/jquery.validate.min.js"></script>

<script>

$.validator.setDefaults({
	submitHandler: function() {
		alert("submitted!");
	}
});

$().ready(function() {
	// validate the comment form when it is submitted
	$("#commentForm").validate();

	// validate signup form on keyup and submit
	$("#signupForm").validate({
		rules: {
			firstname: "required",
			lastname: "required",
			username: {
				required: true,
				minlength: 2
			},
			password: {
				required: true,
				minlength: 5
			},
			confirm_password: {
				required: true,
				minlength: 5,
				equalTo: "#password"
			},
			email: {
				required: true,
				email: true
			},
			topic: {
				required: "#newsletter:checked",
				minlength: 2
			},
			agree: "required"
		},
		messages: {
			firstname: "Please enter your firstname",
			lastname: "Please enter your lastname",
			username: {
				required: "Please enter a username",
				minlength: "Your username must consist of at least 2 characters"
			},
			password: {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long"
			},
			confirm_password: {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long",
				equalTo: "Please enter the same password as above"
			},
			email: "Please enter a valid email address",
			agree: "Please accept our policy",
			topic: "Please select at least 2 topics"
		}
	});

	// propose username by combining first- and lastname
	$("#username").focus(function() {
		var firstname = $("#firstname").val();
		var lastname = $("#lastname").val();
		if (firstname && lastname && !this.value) {
			this.value = firstname + "." + lastname;
		}
	});

	//code to hide topic selection, disable for demo
	var newsletter = $("#newsletter");
	// newsletter topics are optional, hide at first
	var inital = newsletter.is(":checked");
	var topics = $("#newsletter_topics")[inital ? "removeClass" : "addClass"]("gray");
	var topicInputs = topics.find("input").attr("disabled", !inital);
	// show when newsletter is checked
	newsletter.click(function() {
		topics[this.checked ? "removeClass" : "addClass"]("gray");
		topicInputs.attr("disabled", !this.checked);
	});
});
</script>
<style>
#commentForm {
	width: 500px;
}
#commentForm label {
	width: 250px;
}
#commentForm label.error, #commentForm input.submit {
	margin-left: 253px;
}
#signupForm {
	width: 670px;
}
#signupForm label.error {
	margin-left: 10px;
	width: auto;
	display: inline;
}
#newsletter_topics label.error {
	display: none;
	margin-left: 103px;
}
</style>
</head>
<body>
	<%-- <jsp:include page="admin_header.jsp"></jsp:include> --%>
	<form class="cmxform" id="commentForm" method="get" action="">
<fieldset>
<legend>Please provide your name, email address (won't be published) and a comment</legend>
<p>
<label for="cname">Name (required, at least 2 characters)</label>
<input id="cname" name="name" minlength="2" type="text" required>
</p>
<p>
<label for="cemail">E-Mail (required)</label>
<input id="cemail" type="email" name="email" required>
</p>
<p>
<label for="curl">URL (optional)</label>
<input id="curl" type="url" name="url">
</p>
<p>
<label for="ccomment">Your comment (required)</label>
<textarea id="ccomment" name="comment" required></textarea>
</p>
<p>
<input class="submit" type="submit" value="Submit">
</p>
</fieldset>
</form>

	<table id="mid">
		<form:form method="POST" modelAttribute="scorecard" class="form-signin" action="${pageContext.request.contextPath}/admin/saveorupdatescorecard" >
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
			                               
										<form:select path="macId" class="form-control required" id="macId">
										   <form:option value="" label="--- Select MAC---"/>
										   <form:options items="${macIdMap}" />
										</form:select> 									
										
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> Jurisdiction:</label>
			                             
										
										<form:select path="jurId" class="form-control required" id="jurId">
										   <form:option value="" label="--- Select Jurisdiction---"/>
										   <form:options items="${jurisMapEdit}" />
										</form:select> 				
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Program:</label>
			                             
										<form:select path="programId" class="form-control required" id="programId">
										   <form:option value="" label="--- Select Program---"/>
										   <form:options items="${programMapEdit}" />
										</form:select> 	
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> LOB:</label>
			                                <form:select path="lob" class="form-control required" id="lob">
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
			                                <!-- <input id="cname" name="name" minlength="2" type="text" required></input> -->
			                                <form:input class="required form-control" type = "text" name = "csrFullName" path="csrFullName" required="true"/>
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
			                                <form:select path="callLanguage" class="form-control required" id="callLanguage">
											   	<form:option value="" label="--- Select Language---"/>
											  	<form:option value="English" />
											  	<form:option value="Spanish" />											  	
											</form:select> 	
			                               
										</div>
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-6 form-group">
			                             <label for="email"> Call Category:</label>
			                              <form:select path="callCategoryId" class="form-control required" id="callCategoryId">
											   	<form:option value="" label="--- Select Call Category---"/>
											  	<form:option value="1" label="Option 1"/>
											  	<form:option value="2" label="Option 2"/>											  	
											</form:select> 	
			                               
			                                
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> Call Sub Category:</label>
			                                <form:select path="callSubCategoryId" class="form-control required" id="callSubCategoryId">
											   	<form:option value="" label="--- Select Call Sub Category---"/>
											  	<form:option value="1" label="Option 1"/>
											  	<form:option value="2" label="Option 2"/>											  	
											</form:select> 	
			                                
			                              
			                            </div>
			                        </div>
			                       
			                        <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Call Duration:</label>
			                                <form:input class="form-control required"  type = "text" name = "callDuration" path="callDuration" />
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Call Time:</label>
			                                <form:input class="form-control required" type = "text" name = "callTime" path="callTime" />
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
	<%-- <jsp:include page="footer.jsp"></jsp:include> --%>
</body>
</html>