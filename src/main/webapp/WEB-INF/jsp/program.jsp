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
	              		window.location.href= '${pageContext.request.contextPath}/${SS_USER_FOLDER}/programlist';          	 	
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
	<div id="dialog-confirm" title="Close MAC Info Confirmation?">
  		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Do you want to close the Program form without saving?</p>
	</div>
	
	<table id="mid">
		<form:form method="POST" modelAttribute="program" enctype="multipart/form-data"  class="form-signin" action="${pageContext.request.contextPath}/${SS_USER_FOLDER}/saveOrUpdateProgram" id="ProgramId">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
							
							<div class="table-users" style="width: 80%">
								<div class="header">								
								<c:if test="${program.id == null || program.id == 0}">
										<span>Save Program</span>
									</c:if>
									<c:if test="${program.id > 0}">
										<span>Update MAC</span>
									</c:if>
								</div>	
											
      							 <div class="row " style="margin-top: 10px">
				                <div class="col-md-8 col-md-offset-1 form-container">
				                   
				                   <form:hidden path="id" />
				                   <form:hidden path="createdBy" />				                  
								 	<form:hidden path="createdDate" />
				                    
			                         <div class="row">
			                              
			                          <div class="col-sm-6 form-group">
			                                <label for="name"> Program Name:</label>
			                                <form:input class="required form-control" type = "text" name = "programName" path="programName" />
			                            </div>
			                            <div class="col-sm-6 form-group">
			                               
			                            </div>
			                        </div>
			                          <div class="row">			                              
			                            <div class="col-lg-6 form-group">
			                             <label for="email"> Description:</label>
			                             <form:textarea class="form-control" type = "textarea" name = "programDescription" path="programDescription" />	                                
			                         	</div>
			                         	<div class="col-lg-6 form-group">
			                               
			                            </div>
			                         </div>			     
			                                               
			                        
				                </div>
				            </div> 
				            <table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
									<tr>
									<td>
									<c:if test="${program.id == null || program.id == 0}">
										<span><button class="btn btn-primary" id="create"  type="submit">Save</button></span>
									</c:if>
									<c:if test="${program.id > 0}">
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