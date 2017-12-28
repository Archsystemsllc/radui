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

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/button.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui-timepicker-addon.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/radjavascript.js" />
<link rel="stylesheet" href="/resources/demos/style.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>

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


<script type="text/javascript">

	$(document).ready(function () {

    	var username="qamadmin";
  	   	var password="123456";
    	
    	$('#alertMsg').text('');

    	var dNow = new Date();
    	var localdate= (dNow.getMonth()+1) + '/' + dNow.getDate() + '/' + dNow.getFullYear() + ' ' + dNow.getHours() + ':' + dNow.getMinutes();
		//alert(localdate);
    	$('#qamStartdateTime').val(localdate);
    	$("#qamStartdateTime,#qamEnddateTime,#qamFullName").attr("disabled", true);		

    	$("#csrFullName" ).autocomplete({
    		source: function(request, response) {
    			var autocompleteContext = request.term;
    			var selectedMac = $('select[name=macIdS]').val();
    			var selectedJurisdiction = $('select[name=jurisdictionS]').val(); 	 
    			var selectedProgram = $('select[name=programS]').val();    
    			$.ajax({
	    			//url: "http://localhost:8080/radservices/api/csrListNames",
	    			url: "http://radservices.us-east-1.elasticbeanstalk.com/api/csrListNames",
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
    		timeFormat: 'HH:mm',
    		
    		timezoneList: [ 
    				{ value: -300, label: 'Eastern'}, 
    				{ value: -360, label: 'Central' }, 
    				{ value: -420, label: 'Mountain' }, 
    				{ value: -480, label: 'Pacific' } 
    			]
    	});
    	$('#qamEnddateTime').datetimepicker({
    		timeFormat: 'HH:mm',
    		timezoneList: [ 
    				{ value: -300, label: 'Eastern'}, 
    				{ value: -360, label: 'Central' }, 
    				{ value: -420, label: 'Mountain' }, 
    				{ value: -480, label: 'Pacific' } 
    			]
    	});

    	$('#callDuration').timepicker({
    		timeFormat: 'HH:mm',
    		timeOnlyTitle: 'Select Duration',
    		hourGrid: 4,
    		hourMax: 4,
        	minuteGrid: 10
    	});    	

    	$('#callTime').timepicker({
    		timeFormat: 'HH:mm',    		
    		hourGrid: 4,
        	minuteGrid: 10
    	});

    	$('#callFailureTime').datetimepicker({
    		timeFormat: 'HH:mm z',
    		
    		timezoneList: [ 
    				{ value: -300, label: 'Eastern'}, 
    				{ value: -360, label: 'Central' }, 
    				{ value: -420, label: 'Mountain' }, 
    				{ value: -480, label: 'Pacific' } 
    			]
    	});    	
    	
 	   	
 	  
 	   	
 	   $.ajax({ 
           type: "GET",
           dataType: "json",           
           url: "http://radservices.us-east-1.elasticbeanstalk.com/api/macList",
           //url : 'http://localhost:8080/radservices/api/macList',
           headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
           success: function(data){   

        	   $("#macIdS").get(0).options.length = 0;
 	           $("#macIdS").get(0).options[0] = new Option("Select MAC", "");
 	           
	           
	  	    	$.each(data, function (i, item) {
	  	        
	  	    		$("#macIdS").get(0).options[$("#macIdS").get(0).options.length] = new Option(item.macName, item.id);
	  	    		
	  	    	});  	    
	  	    
	          },
	          failure: function () {
	              
	          }
	      });
 	   
 	  $.ajax({ 
          type: "GET",
          dataType: "json",           
          url: "http://radservices.us-east-1.elasticbeanstalk.com/api/jurisdictionList",
          //url : 'http://localhost:8080/radservices/api/jurisdictionList',
          headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
          success: function(data){   
         	 
       	   $("#jurisdictionS").get(0).options.length = 0;	           
	       $("#jurisdictionS").get(0).options[0] = new Option("Select All", "All");
	      
	           
	  	    	$.each(data, function (i, item) {
	  	        
	  	    		$("#jurisdictionS").get(0).options[$("#jurisdictionS").get(0).options.length] = new Option(item.jurisdictionName, item.jurisdictionName);
	  	    		
	  	    	});  	    
	  	    
	          },
	          failure: function () {
	              
	          }
	      });	   
 	
	});
    
</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>

	<table id="mid">
		<form:form method="POST" modelAttribute="scorecard" class="form-signin" action="${pageContext.request.contextPath}/admin/saveorupdatescorecard">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
							
							<div class="table-users" style="width: 80%">
								<div class="header">Save/Update ScoreCard</div>	
								<table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
					
									
									<tr>
									<td><span><button class="btn btn-primary" id="create">Save/Update</button></span>
									<span><button class="btn btn-primary" id="close">Close</button></span></td>
								

							       </tr>
							     </table>						
      							 <div class="row " style="margin-top: 10px">
				                <div class="col-md-8 col-md-offset-1 form-container">
				                    <h2>QAM Info</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->
				                   
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> QM Full Name:</label>
			                                <form:input type = "text" class="form-control" id="qamFullName" name = "qamFullName" path="qamFullName" />
			                                <form:input type = "hidden" name = "id" path="id" />
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <!-- <label for="email"> Email:</label>
			                                <input type="email" class="form-control" id="email" name="email" required> -->
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> QM Start Date/Time:</label>
			                                <form:input type = "text" class="form-control" id="qamStartdateTime" name = "qamStartdateTime" path="qamStartdateTime" />
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> QM End Date/Time:</label>
			                                <form:input type = "text" class="form-control" id="qamEnddateTime" name = "qamEnddateTime" path="qamEnddateTime"/>
			                            </div>
			                        </div>
				                    
				                </div>
				            </div>
				            
				             <div class="row " >
				                <div class="col-md-8 col-md-offset-1 form-container">
				                    <h2>QAM Contract Information</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->
				                   
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> MAC:</label>
			                                <select class="form-control" id="macIdS" name="macIdS"
											title="Select one of the MAC">
												<option value="">Select MAC</option>
												
										</select>
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> Jurisdiction:</label>
			                                <select class="form-control" id="jurisdictionS" name="jurisdictionS"
											title="Select one of the Jurisdiction">
												<option value="">Select Jurisdiction</option>
												
										</select>
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Program:</label>
			                                <select class="form-control" id="programS" name="programS"
											title="Select Program">
												<option value="">Select Program</option>
												<option value="ABMAC-A">ABMAC-A</option>
												<option value="ABMAC-B">ABMAC-B</option>
										</select>
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> LOB:</label>
			                                <select class="form-control" id="programId" name="programId"
											title="Select LOB">
												<option value="">Select LOB</option>
												<option value="Option 1">Option 1</option>
												<option value="Option 2">Option 2</option>
										</select>
			                            </div>
			                        </div>
				                    
				                </div>
				            </div>
				            
				             <div class="row " >
				                <div class="col-md-8 col-md-offset-1 form-container">
				                    <h2>QAM Call and CSR Information</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->
				                   
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> CSR Last Name:</label>
			                                <form:input class="form-control" type = "text" name = "csrFullName" path="csrFullName" />
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> Call Category:</label>
			                               <select class="form-control" id="callCategoryId" name="callCategoryId"
											title="Select Call Category">
												<option value="">Select Call Category</option>
												<option value="Option 1">Option 1</option>
												<option value="Option 2">Option 2</option>
										</select>
			                            </div>
			                        </div>
			                         <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Call Duration:</label>
			                                <form:input class="form-control"  type = "text" name = "callDuration" path="callDuration" />
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="macCallReferenceNumber"> MAC Call Reference Id:</label>
			                                <form:input class="form-control" type = "text" name = "macCallReferenceNumber" path="macCallReferenceNumber" />
			                            </div>
			                        </div>
			                       
			                        <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Call Time:</label>
			                                <form:input class="form-control" type = "text" name = "callTime" path="callTime" />
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <label for="email"> Call Language: </label>
			                                <form:input class="form-control" type = "text" name = "callLanguage" path="callLanguage" />
			                            </div>
			                        </div>
			                         
			                        <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> CSR Level:</label>
			                                <form:input class="form-control" type = "text" name = "csrLevel" path="csrLevel" />
			                            </div>
			                            <div class="col-sm-6 form-group">
			                                <%-- <label for="email"> Call Language: </label>
			                                <form:input type = "text" name = "callLanguage" path="callLanguage" /> --%>
			                            </div>
			                        </div>
				                    
				                </div>
				            </div>
				            
				            <div class="row " >
				                <div class="col-md-10 col-md-offset-1 form-container">
				                    <h2>Knowledge Skills</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   
			                         <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name"> Did the CSR provide accurate information? If 'No' was selected,please enter reason in text box below:</label>
			                                <form:radiobutton path="csrPrvAccInfo" value="Yes" name="csrPrvAccInfo" id="csrPrvAccInfoYes" />Yes
										  	<form:radiobutton path="csrPrvAccInfo" value="No" name="csrPrvAccInfo" id="csrPrvAccInfoNo"/>No
			                            </div>
			                           
			                        </div>
			                        <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name"> Did the CSR provide complete information? If 'No' was selected,please enter reason in text box below:</label>
			                                <form:radiobutton path="csrPrvCompInfo" value="Yes" name="csrPrvCompInfo" id="csrPrvCompInfoYes" />Yes
										  	<form:radiobutton path="csrPrvCompInfo" value="No" name="csrPrvCompInfo" id="csrPrvCompInfoNo"/>No
			                            </div>
			                           
			                        </div> 
				                    
				                </div>
				            </div>
				            <div class="row" >
				                <div class="col-md-10 col-md-offset-1 form-container">
				                    <h2>Adherence to Privacy</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   
			                         <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name"> Did CSR follow privacy procedures? If 'No' was selected,please select the reason below:</label>
			                                <form:radiobutton path="csrFallPrivacyProv" value="Yes"/>Yes
										  <form:radiobutton path="csrFallPrivacyProv" value="No"/>No
			                            </div>		                           
			                        </div>         
				                    
				                </div>
				            </div>
				            <div class="row" >
				                <div class="col-md-10 col-md-offset-1 form-container">
				                    <h2>Customer Skills</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->				                   
				                   
			                         <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name">Did CSR follow privacy procedures? If 'No' was selected,please select the reason below:Was CSR courteous,friendly,and professional? If 'No' was selected,please select the reason below:</label>
			                                <form:radiobutton path="csrFallPrivacyProv" value="Yes"/>Yes
										  <form:radiobutton path="csrFallPrivacyProv" value="No"/>No
			                            </div>		                           
			                        </div>         
				                    
				                </div>
				            </div>
				             <div class="row" >
				                <div class="col-md-8 col-md-offset-1 form-container">
				                    <h2>Results</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->
				                   
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="name"> Call Result:</label>
			                                <select class="form-control" class="form-control" id="callResult" name="callResult"
											title="Select one of the MAC">
												<option value="">Select</option>
												<option value="Pass">Pass</option>
												<option value="Fail">Fail</option>
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
									<td><span><button class="btn btn-primary" id="create">Save/Update</button></span>
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
