<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>ADDA - Download</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/adda_ico.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/button.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
<link rel="stylesheet" href="/resources/demos/style.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.33.3/es6-shim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/systemjs/0.19.20/system-polyfills.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- CSS for Bootstrap -->
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"></link>
<!-- JQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">
$(function() {
  $('button[type=submit]').click(function(e) {
    e.preventDefault();
    //Disable submit button
    $(this).prop('disabled',true);
    
    var form = document.forms[0];
    var formData = new FormData(form);
    var username="qamadmin";
    var password="123456";
    // Ajax call for file uploaling
    var ajaxReq = $.ajax({
      url : url : "${WEB_SERVICE_URL}uploadCsrList",
      type : 'POST',
      data : formData,
      cache : false,
      contentType : false,
      processData : false,
      headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
      xhr: function(){
        //Get XmlHttpRequest object
         var xhr = $.ajaxSettings.xhr() ;
        
        //Set onprogress event handler 
         xhr.upload.onprogress = function(event){
          	var perc = Math.round((event.loaded / event.total) * 100);
          	$('#progressBar').text(perc + '%');
          	$('#progressBar').css('width',perc + '%');
         };
         return xhr ;
    	},
    	beforeSend: function( xhr ) {
    		//Reset alert message and progress bar
    		$('#alertMsg').text('');
    		$('#progressBar').text('');
    		$('#progressBar').css('width','0%');
              }
    });
  
    // Called on success of file upload
    ajaxReq.done(function(msg) {
      $('#alertMsg').text(msg+':'+'File Uploaded Succesfully');
      $('input[type=file]').val('');
      $('button[type=submit]').prop('disabled',false);
    });
    
    // Called on failure of file upload
    ajaxReq.fail(function(jqXHR) {
      $('#alertMsg').text(jqXHR.responseText+'('+jqXHR.status+
      		' - '+jqXHR.statusText+')');
      $('button[type=submit]').prop('disabled',false);
    });
  });
  
  $('button[id=keepPreviousListButton]').click(function(e) {
	    e.preventDefault();
	    //Disable submit button
	    $(this).prop('disabled',true);
	    
	    var username="qamadmin";
	    var password="123456";
	    // Ajax call for file uploaling
	    var ajaxReq2 = $.ajax({
	      url : url : "${WEB_SERVICE_URL}keepCurrentList?userId=1",
	      type : 'POST',
	      cache : false,
	      contentType : false,
	      processData : false,
	      headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
	      beforeSend: function( xhr ) {
	    		//Reset alert message and progress bar
	    		$('#alertMsg').text('');
	    		
	       }
	    });
	  
	    // Called on success of file upload
	    ajaxReq2.done(function(msg) {
	      $('#alertMsg').text(msg+':'+'List Updated Succesfully');
	      $('button[id=keepPreviousListButton]').prop('disabled',false);
	    });
	    
	    // Called on failure of file upload
	    ajaxReq2.fail(function(jqXHR) {
	      $('#alertMsg').text(jqXHR.responseText+'('+jqXHR.status+
	      		' - '+jqXHR.statusText+')');
	      $('button[id=keepPreviousListButton]').prop('disabled',false);
	    });
	  });
});
</script>



<script type="text/javascript">
	
    $(document).ready(function () {
    	 $('#resultstable').hide();
    	 $('#csrLists').hide();
    	 
       var username="qamadmin";
 	   var password="123456";
 	   
 	   $('#ajax').click(function(){ 
 		  $('#resultstable').show();        

 	  }); 
 	   
 	  $('#viewLink').click(function(){ 
 		  
 	         $.ajax({ 
 	             type: "GET",
 	             dataType: "json",
 	             url: url : "${WEB_SERVICE_URL}csrList?fromDate=November 2017&toDate=November 2017",
 	             headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
 	            success: function(data){   
 	            	 
	            	var trHTML = '';
	                 
            	    $.each(data, function (i, item) {
            	        
            	        trHTML += '<tr><td>' + item.fisrtName + '</td><td>' + item.lastName + '</td><td>' + item.location + '</td><td>' + item.jurisdiction + '</td><td>' + item.program + '</td></tr>';
            	        $('#alertMsg').text('List Updated Succesfully');
            	    });
            	    
            	    $('#csrLists').append(trHTML);
            	    $('#csrLists').show();
            	    
            	    
 	            },
 	            failure: function () {
 	                $("#csrLists").append(" Error when fetching data please contact administrator");
 	            }
 	        });

 	  }); 	   
 	  
	});

   
</script>
<script type="text/css">
	.ui-datepicker-calendar {
    	display: none;
    }
</script>

<script type="text/javascript">
$(document).ready(function(){
	$("#keepPreviousListButton").hide();
	$("#dialog-confirm").hide();
	
	
    $("#datepicker1").datepicker({ 
        dateFormat: 'mm-yy',
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true,

        onClose: function(dateText, inst) {  
            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
            $(this).val($.datepicker.formatDate('yy-mm', new Date(year, month, 1)));
        }
    });

    $("#datepicker1").focus(function () {
        $(".ui-datepicker-calendar").hide();
        $("#ui-datepicker-div").position({
            my: "center top",
            at: "center bottom",
            of: $(this)
        });    
    });  
    
    $("#datepicker2").datepicker({ 
        dateFormat: 'mm-yy',
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true,

        onClose: function(dateText, inst) {  
            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
            $(this).val($.datepicker.formatDate('yy-mm', new Date(year, month, 1)));
        }
    });

    $("#datepicker2").focus(function () {
        $(".ui-datepicker-calendar").hide();
        $("#ui-datepicker-div").position({
            my: "center top",
            at: "center bottom",
            of: $(this)
        });    
    }); 
    
    $('#keepCurrentListCB').click(function() {
        if( $(this).is(':checked')) {
            
            
            $( "#dialog-confirm" ).dialog({
                resizable: false,
                height: "auto",
                width: 400,
                modal: true,
                
                buttons: {
                  "Yes": function() {
                	$( this ).dialog( "close" );
                    $("#keepPreviousListButton").show();
                    $('#table1 .hideme').hide();
                                        
                  },
                  Cancel: function() {                    
                    $("#keepPreviousListButton").hide();
                    $('#table1 .hideme').show();
                    $('input[id=keepCurrentListCB]').attr('checked', false);
                    $( this ).dialog( "close" );
                  }
                  
                }
	            
              });
            
        } else {
            $("#keepPreviousListButton").hide();
            $('#table1 .hideme').show();
        }
    }); 
});


</script>
</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div id="dialog-confirm" title="Current CSR List Confirmation?">
  		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Are you sure you want to keep the current CSR list?</p>
	</div>
	<table id="mid">
		<form:form method="POST" modelAttribute="userForm" class="form-signin" action="#">
			<tr>

				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">

							<div class="table-users" style="width: 80%">
								<div class="header">CSR Lists</div>							
      

								<table style="border-collapse: separate; border-spacing: 2px;" id='table1'>
									<tr>
									<td class='hideme'>
										<div class="progress">
										      <div id="progressBar" class="progress-bar progress-bar-success" role="progressbar"
										        aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">0%</div>
										    </div>
									</td>
									<td>	
										    <!-- Alert -->
										    <div id="alertMsg" style="color: red;font-size: 18px;"></div>
										  </div>
									</td>
									</tr>
									<tr>
										<td colspan="2" align="left" style="text-align: left"><a class="${linkcolor }"
												href="${pageContext.request.contextPath}/resources/QAM_MAC_ CSR_List_Template.xlsx">Download Sample CSR Template</a></td>
										<td colspan="2" align="left" style="text-align: left"><form:checkbox
												path="username" value="username" id="keepCurrentListCB"/><label for="password">&nbsp;Keep Current List</label></td>
										<td colspan="1" ><button class="btn btn-primary" id="keepPreviousListButton">Keep List</button></td>
									</tr>									
									
									<form action="fileUpload" method="post" enctype="multipart/form-data">
									<tr class='hideme'>
										<td><label for="password">CSR List Upload: </label></td>

										<td colspan="3" align="right"><input class="form-control" type="file" name="file"></td>
										<td><button class="btn btn-primary" type="submit">Upload File</button></td>		
									</tr>
									</form>		
														
									
									
									<tr>

										<td><form:input type="input" path="password"
												placeholder="From Date" id="datepicker1"></form:input></td>
										<td><form:input type="input" path="passwordConfirm"
												placeholder="To Date" id="datepicker2"></form:input></td>
										<td><select id="name" name="name"
											title="Select one of the MAC">
												<option value="">Select MAC</option>
												<option value="Option 1">Option 1</option>
												<option value="Option 2">Option 2</option>
										</select></td>
										<td><select id="email" name="email"
											title="Select one of the Jurisdiction">
												<option value="">Select Jurisdiction</option>
												<option value="Option 1">Option 1</option>
												<option value="Option 2">Option 2</option>
										</select></td>
										<td style="padding-top: 10px"><button class="btn btn-primary" id="ajax">Search CSR</button></td>


									</tr>



								</table>
								


								<table class="display data_tbl" id="resultstable">
									<thead>
										<tr>
											<th title="Monthly">Monthly</th>
											<th title="CSR Name">CSR Name</th>

										</tr>
									</thead>
									<tbody>

										
										<tr>
											<td align="center"><a class="${linkcolor }">October
													CSR List</a></td>
											<td style="text-align: center"><a class="${linkcolor }" id="viewLink"
												href="#">View</a></td>

										</tr>
										<tr>
											<td align="center"><a class="${linkcolor }" id="viewLink" href="#">November 
													CSR List</a></td>
											<td style="text-align: center"><a class="${linkcolor }" id="viewLink"
												href="${pageContext.request.contextPath}/resources/QAM_MAC_ CSR_List_October 2017.xlsx">View</a></td>

										</tr>

									</tbody>
								</table> 
								
								<table id="csrLists">
								    <thead>
								        <tr>
								            <th style="text-align: left">First Name</th>
								            <th style="text-align: left">Last Name</th>
								            <th style="text-align: left">Location</th>
								            <th style="text-align: left">Jurisdiction</th>
								            <th style="text-align: left">Program</th>
								            
								            
								        </tr>
								    </thead>
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
