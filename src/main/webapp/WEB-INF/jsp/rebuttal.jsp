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


<script type="text/javascript">

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
	    			url:"${WEB_SERVICE_URL}csrListNames",
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
	});

	$(function(){
		$("select#macReferenceId").change(function(){
            $.getJSON("${pageContext.request.contextPath}/admin/selectScoreCardFromMacRef",                    
                    {scoreCardId: $(this).val()}, function(data){                     
              		$("#macPCCName").val("DummyName");
              		$("#csrFullName").val(data.csrFullName);
              		$("#datePosted").val(data.qamEnddateTime);
              		$("#failureReason").val(data.failReason);
              		$("#qamFullName").val(data.csrFullName);
              		$("#callTime").val(data.callTime);
              		$("#callDate").val(data.callMonitoringDate);
              		$("#callCategory").val(data.callCategoryName);
              		$("#macCallReferenceNumber").val(data.macCallReferenceNumber);
              		$("#macId").val(data.macId);
              		$("#jurisId").val(data.jurId);
              });
        });
	});
</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>

	<table id="mid">
		<form:form method="POST" modelAttribute="rebuttal" class="form-signin" action="${pageContext.request.contextPath}/admin/saveOrUpdateRebuttal">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
							
							<div class="table-users" style="width: 80%">
								<div class="header">Save/Update Rebuttal</div>	
								<table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td><span><button class="btn btn-primary" id="create">Save/Update</button></span>
									<span><button class="btn btn-primary" id="close">Close</button></span></td>
							       </tr>
							     </table>						
      							 <div class="row " style="margin-top: 10px">
				                <div class="col-md-8 col-md-offset-1 form-container">
				                   
				                   <form:hidden path="macId" />
								 	<form:hidden path="jurisId" />
				                    <div class="row">
			                            <div class="col-sm-6 form-group">
			                                <label for="macReferenceId"> MAC Call Reference Id:</label>
			                                <form:select path="macReferenceId" class="form-control" id="macReferenceId">	
			                                	<form:option value="" >---Select Mac Ref Number---</form:option>										   	
											  	<form:options items="${macReferenceFailedList}" />
											</form:select> 
											<form:input type="hidden" name="macCallReferenceNumber" path="macCallReferenceNumber" />
			                            </div>
			                           <div class="col-sm-6 form-group">
			                                <label for="contactPerson"> MAC/PCC Contact Person:</label>
			                                <form:select path="contactPerson" class="form-control" id="contactPerson" >
											   	<form:option value="" label="---Select Contact---"/>
											  	<form:option value="Contact 1" />
											  	<form:option value="Contact 2" />
											  	<form:option value="Contact 3" />
											  	<form:option value="Contact 4" />
											</form:select> 
			                            </div>
			                        </div>
			                         <div class="row">
			                             <div class="col-sm-6 form-group">
			                                <label for="name"> MAC/PCC Name:</label>
			                                <form:input type = "text" class="form-control" id="macPCCName" name = "macPCCName" path="macPCCName" readonly="true"/>
			                                <form:input type = "hidden" name = "id" path="id" />
			                            </div>
			                           <div class="col-sm-6 form-group">
			                                <label for="name"> CSR Full Name:</label>
			                                <form:input class="form-control" type = "text" name = "csrFullName" path="csrFullName" readonly="true"/>
			                            </div>
			                        </div>
			                        
			                        <div class="row">
			                              <div class="col-sm-6 form-group">
			                                <label for="name"> Date Posted:</label>
			                                <form:input type = "text" class="form-control" id="datePosted" name = "datePosted" path="datePosted" readonly="true"/>
			                            </div>
			                         <div class="col-sm-6 form-group">
			                                <label for="name">Call Failure Reasons:</label>
			                                <form:input type = "text" class="form-control" id="failureReason" name = "failureReason" path="failureReason" readonly="true"/>
			                                
			                            </div>
			                        </div>
			                        
			                        <div class="row">
			                              <div class="col-sm-6 form-group">
			                                <label for="name"> QM Full Name:</label>
			                                <form:input type = "text" class="form-control" id="qamFullName" name = "qamFullName" path="qamFullName" readonly="true"/>
			                                <form:input type = "hidden" name = "id" path="id" />
			                            </div>
			                          <div class="col-sm-6 form-group">
			                                <label for="name"> Call Time:</label>
			                                <form:input class="form-control" type = "text" name = "callTime" path="callTime" readonly="true"/>
			                            </div>
			                        </div>
			                        
			                        <div class="row">
			                              <div class="col-sm-6 form-group">
			                                <label for="name"> Call Monitoring Date:</label>
			                                <form:input type = "text" class="form-control" id="callDate" name = "callDate" path="callDate" readonly="true"/>
			                            </div>
			                          <div class="col-sm-6 form-group">
			                             <label for="email"> Call Category:</label>
			                             <form:input type = "text" class="form-control" id="callCategory" name = "callCategory" path="callCategory" readonly="true"/>
			                            </div>
			                        </div>
			                        
				                    
				                </div>
				            </div> 			            
				            
				             <div class="row" >
				                <div class="col-md-8 col-md-offset-1 form-container">
			                        
			                         <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name">Description/Comments:</label>
			                                <form:textarea class="required form-control" type = "textarea" name = "descriptionComments" path="descriptionComments" required="true"/>
			                            </div>		                           
			                        </div>       
			                         <div class="row">
			                            <div class="col-sm-10 form-group">
			                                <label for="name">Attachments:</label>			                                
			                               <input class="form-control" id="file" type="file" name="uploadAttachment" style="box-sizing: content-box;">
										</input>
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