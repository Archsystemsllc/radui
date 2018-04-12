<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>CSR List</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/adda_ico.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/button.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />

<link rel="stylesheet" href="/resources/demos/style.css" />
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" />
<!-- CSS for Bootstrap -->
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"></link>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- JQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>

<script src="https://cdn.datatables.net/buttons/1.5.1/js/dataTables.buttons.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.flash.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
<script src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.html5.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.print.min.js"></script>


<script type="text/javascript">

function resetFields() {
	//alert("Inside ResetFields");
    $('#csrMonthLists').hide();
    CsrListTable.clear().draw();
	$('#csrLists').hide();
	$('input[type=file]').val('');
	
	$('#macIdU').prop('selectedIndex',0);
	$('#macIdS').prop('selectedIndex',0);
	$('#macIdK').prop('selectedIndex',0);

	$("#jurisdictionK option:selected").prop("selected", false);	
	$("#jurisdictionS option:selected").prop("selected", false);
	$("#jurisdictionU option:selected").prop("selected", false);	

	$('#csrListViewDiv').hide();
	$('#csrListMonthDiv').hide();	
}
	
$(function() {

	$('INPUT[type="file"]').change(function () {
	    var ext = this.value.match(/\.(.+)$/)[1];
	    switch (ext) {
	        case 'xls':
	        case 'xlsx':
	            $('#uploadButton').attr('disabled', false);
	            break;
	        default:
	            alert('Please Upload A Valid File.');
	            this.value = '';
	    }
	});	
	
  $('button[id=uploadQamEnvForm]').click(function(e) {
    e.preventDefault();
    //Disable submit button
    //$(this).prop('disabled',true);
    $('#csrListViewDiv').hide();
	$('#csrListMonthDiv').hide();
   
    	var validatedMac = $('#macIdU').val();
		var validateJurisdiction = $('#jurisdictionU').val(); 
	    var fileUpload = $('#file').val();
		
	  if(fileUpload == "" && validatedMac == "" && validateJurisdiction == "") {
		  $('#alertMsg').text("Please Select Mac Id and Jurisdiction Id");
			return;
		} else if(fileUpload == "") {
			  $('#alertMsg').text("Please Select a File for Upload");
				return;
			} else if(validatedMac == "" ) {
			  $('#alertMsg').text("Please Select Mac Id ");
				return;
			} else if(validateJurisdiction == "") {
				  $('#alertMsg').text("Please Select Jurisdiction Id");
					return;
				} 

	  $('#jurisdictionUText').val($('#jurisdictionU').val()); 
	 	
    var form = document.forms[0];
    
    var formData = new FormData(form);
    
    var username="qamadmin";
    var password="123456";
    // Ajax call for file uploaling
    var ajaxReq = $.ajax({
      url : "${WEB_SERVICE_URL}uploadQamEnvForm",
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
    ajaxReq.done(function(data) {
      $('#alertMsg').text(data.status);      
     
      resetFields();
    });
    
    // Called on failure of file upload
    ajaxReq.fail(function(jqXHR) {
    	$('#alertMsg').text(jqXHR.responseText+'('+jqXHR.status+
          		' - '+jqXHR.statusText+')');
    	
    });
  }); 
  

  /* $(document).on('click',".viewLink",function (){    


	  	$('#csrLists tbody').empty();
		$('#alertMsg').text('');
		$('#searchalertMsg').text('');
		
		 var row= $(this).closest('tr');  
	  	var monthYear=$("td:eq(0)",row).text(); 

	  	var username="qamadmin";
		var password="123456";

		var selectedJurisArr = [];
        $.each($("#jurisdictionS option:selected"), function(){            
        	selectedJurisArr.push($(this).text());
        });
	 
	 	var selectedMac = JSON.stringify($('select[name=macIdS]').val());     	
		var selectedJurisdiction = JSON.stringify(selectedJurisArr); 
	  
	     $.ajax({ 
	         type: "GET",
	         dataType: "json",
	         data: {fromDate: $("#fromDate").val(), toDate: $("#toDate").val(), macIdS: selectedMac, jurisdictionS: selectedJurisdiction},
	         url : "${WEB_SERVICE_URL}csrList",
	         headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
	        success: function(data){ 
	        	$('#csrListViewDiv').show();
	            $('#csrLists').show();	            
	        	CsrListTable.clear().draw();
	        	CsrListTable.rows.add(data).draw();	
	        },
	        failure: function () {
	            $("#csrLists").append("Error when fetching data please contact administrator");
	        }
	    });
	 }); */

	$('#searchCsr').click(function(e){ 
  		$('#alertMsg').text('');
  		$('#searchalertMsg').text('');
  		$('#csrMonthLists tbody').empty();
  		$('#csrListViewDiv').hide();
  	  	$('#csrListMonthDiv').hide();
  	  	
	  	e.preventDefault();			  	
	  	var validateMac = $('select[name=macIdS]').val();
		var validateJurisdiction = $('select[name=jurisdictionS]').val(); 	    	
	  	var validateFromDate = $('#fromDate').val();
		var validateToDate = $('#toDate').val();		  
		
		  if(validateMac == null && validateJurisdiction == null) {
			  $('#searchalertMsg').text("Please Select MAC Id and Jurisdiction Id");
			  
				return;
			} else if(validateFromDate == "" ) {
				  $('#searchalertMsg').text("Please Enter From Date");
					return;
				}else if(validateToDate == "" ) {
					  $('#searchalertMsg').text("Please Enter To Date");
						return;
					} else if(validateMac == null ) {
						  $('#searchalertMsg').text("Please Select MAC Id ");
							return;
						} else if(validateJurisdiction == null) {
							  $('#searchalertMsg').text("Please Select Jurisdiction Id");
								return;
							} 

		  var selectedJurisArr = [];
	        $.each($("#jurisdictionS option:selected"), function(){            
	        	selectedJurisArr.push($(this).text());
	        });
		 
		 var selectedMac = JSON.stringify($('select[name=macIdS]').val());     	
		 var selectedJurisdiction = JSON.stringify(selectedJurisArr); 
	 	
	 	var username="qamadmin";
	   	var password="123456";
	 	$.ajax({ 
           type: "GET",
           dataType: "json",
           data: {fromDate: $("#fromDate").val(), toDate: $("#toDate").val(), macIdS: selectedMac, jurisdictionS: selectedJurisdiction},
           url : "${WEB_SERVICE_URL}qamEnvListMonths",
           headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
          success: function(data){	    
	        var resultCount = data.length;
	       // alert("Result Count:"+resultCount);
	        if(resultCount==0) {
	        	$('#searchalertMsg').text('No Data Found for the Selected Months');
		    } else {
		    	 
		    	var trHTML = '<tbody>';  
		    	$.each(data, function (i, item) {  	        
	      	        trHTML += '<tr>'
		      	       + '<td align="center">' + item[0] + ' ' + item[1] + '</td>'
		      	       + '<td align="center">' + item[2] + '</td>'
		      	     + '<td align="center">' + item[3] + '</td>'
	      	        +'<td style="text-align: center"><a class="viewLink" href="#" >Download</a></td></tr>';
	      	        
	  	    	});
		    	$('#searchalertMsg').text('QAM Environment Change Form Months Retrieved');
		    	trHTML += '</tbody>';
		    	$('#csrListMonthDiv').show();
		  	    $('#csrMonthLists').append(trHTML);
		  	    $('#csrMonthLists').show();
			 } 
          },
          failure: function () {
              $("#csrMonthLists").append("Error when fetching data please contact administrator");
          }
      });

 	});	

	$("select#macIdK").change(function(){
		var userRole = $('#userRole').val();
		
		if ((userRole != "MAC Admin") && (userRole != "MAC User")){
			
	        $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectJuris",                    
	                {macId: $(this).val(), multipleInput: false}, function(data){
	           
	             $("#jurisdictionK").get(0).options.length = 0;	           
	  	      	 $("#jurisdictionK").get(0).options[0] = new Option("---Select Jurisdiction---", "");
	  	  	    	$.each(data, function (key,obj) {
	  	  	    		$("#jurisdictionK").get(0).options[$("#jurisdictionK").get(0).options.length] = new Option(obj, key);
	  	  	    		
	  	  	    });  	   
           });
		}
    });

    $("#macIdS").change(function () {

		var userRole = $('#userRole').val();
		
		if ((userRole != "MAC Admin") && (userRole != "MAC User")){
	      var selectedMacs = "";
	      $( "#macIdS option:selected" ).each(function() {
	    	  selectedMacs += $( this ).val() + ",";
	      });
	     
	     $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectJuris",                    
	             {macId: selectedMacs, multipleInput: true}, function(data){
	        
	          $("#jurisdictionS").get(0).options.length = 0;	
	          $("#jurisdictionS").get(0).options[0] = new Option("---Select Jurisdiction---", "");           
		      	 $("#jurisdictionS").get(0).options[1] = new Option("Select ALL", "ALL");
		  	    	$.each(data, function (key,obj) {
		  	    		$("#jurisdictionS").get(0).options[$("#jurisdictionS").get(0).options.length] = new Option(obj, key);
		  	    		
		  	    	});  	   
	        });
		}
    });
    //.change();

    $("select#macIdU").change(function(){
		var userRole = $('#userRole').val();
		
		if ((userRole != "MAC Admin") && (userRole != "MAC User")){
	        $.getJSON("${pageContext.request.contextPath}/${SS_USER_FOLDER}/selectJuris",                    
	                {macId: $(this).val(), multipleInput: false}, function(data){
	           
	             $("#jurisdictionU").get(0).options.length = 0;	           
	             $("#jurisdictionU").get(0).options[0] = new Option("---Select Jurisdiction---", "");
	  	  	    	$.each(data, function (key,obj) {
	  	  	    		$("#jurisdictionU").get(0).options[$("#jurisdictionU").get(0).options.length] = new Option(obj, key);
	  	  	    		
	  	  	    	});  	   
	           });
		}
    });
    
   
});
</script>
<script type="text/javascript">
	var CsrListTable;
    $(document).ready(function () {

    	$('#csrMonthLists').hide();
    	$('#csrLists').hide();
    	$('#alertMsg').text('');
    	$('#searchalertMsg').text('');
    	
    	$("#keepPreviousListButton").hide();
    	$("#dialog-confirm").hide();
    	$("#macIdK_Div").hide();	
    	$("#jurisdictionK_Div").hide();	
    	
    	$('#progressBar_Hideme').hide(); 

    	$('#csrLists').hide();
    	
    	$("#fromDate").datepicker({ 
            dateFormat: 'mm-yy',
            maxDate: new Date,
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,

            onClose: function(dateText, inst) {  
                var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
                var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
                $(this).val($.datepicker.formatDate('yy-mm', new Date(year, month, 1)));
            }
        });

        $("#fromDate").focus(function () {
            $(".ui-datepicker-calendar").hide();
            $("#ui-datepicker-div").position({
                my: "center top",
                at: "center bottom",
                of: $(this)
            });    
        });  
        
        $("#toDate").datepicker({ 
            dateFormat: 'mm-yy',
            maxDate: new Date,
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,

            onClose: function(dateText, inst) {  
                var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
                var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
                $(this).val($.datepicker.formatDate('yy-mm', new Date(year, month, 1)));
            }
        });

        $("#toDate").focus(function () {
            $(".ui-datepicker-calendar").hide();
            $("#ui-datepicker-div").position({
                my: "center top",
                at: "center bottom",
                of: $(this)
            });    
        }); 

        
 	  
	});
    
</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div role="main" id="dialog-confirm" title="Current CSR List Confirmation?">
  		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Are you sure you want to keep the current CSR list?</p>
	</div>
	
	<table id="mid">
		<form:form method="POST" modelAttribute="qamEnvironmentForm" class="form-signin" action="#" enctype="multipart/form-data" id="csrupload">
			<tr>
				
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
						
						<div class="table-users" style="width: 80%">
								<div class="header">QAM Environment Change Form</div>	
												
      							 <div class="row " style="margin-top: 10px">
				                <div class="col-lg-8 col-lg-offset-1 form-container">
				                   				                   
				                    <div class="row">
				                      <div id="alertMsg" style="color: red;font-size: 18px;"  class="col-lg-8 form-group"></div>
									</div>
				                     <div class="row" id='progressBar_Hideme'>
				                     	<div class="progress" >
										      <div id="progressBar" class="progress-bar progress-bar-success" role="progressbar"
										        aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">0%</div>
										</div>
				                     </div>
				                 </div>
				                </div>
				                
				             <div class="row " >
				                <div class="col-lg-8 col-lg-offset-1 form-container">
				                    
				                    <div class="row">
			                              <div class="col-lg-6 form-group">
			                                <a class="${linkcolor }"
												href="${pageContext.request.contextPath}/resources/static/CSR_LIST_TEMPLATE_SAMPLE.xlsx" title="Click here to download Sample QAM Environment Change Form">Download Sample QAM Environment Change Form</a>
											<input type="hidden" id="userRole" value='${SS_LOGGED_IN_USER_ROLE}'/>
			                             </div>
			                        </div>
			                      </div>
			                      </div>
				           
				            
				             <div class="row " >
				                <div class="col-lg-8 col-lg-offset-1 form-container">
				                 <h2>Upload QAM Environment Change Form Section</h2> 
				                     <div class="row">
			                            <div class="col-lg-6 form-group">
			                                <label for="file">QAM Environment Change Form Upload: </label>
										
										<form:input type = "hidden" path="userId" />
										<input class="form-control" id="file" type="file" name="file" style="box-sizing: content-box;" title="Select Choose File button to upload CSR List from Local">
										</input>
			                            </div>
			                          </div>
			                         </div>
			                          <div class="col-lg-8 col-lg-offset-1 form-container">
				                     <div class="row">
			                             <div class="col-lg-6 form-group">
			                              <label for="name"> MAC:</label>
			                                <form:select path="macIdU" class="form-control" id="macIdU" title="Select one MAC ID from the list">
										   <form:option value="" label="---Select MAC---"/>
										   <form:options items="${macIdMap}" />
										</form:select> 	
			                            </div>
			                            <div class="col-lg-6 form-group">
			                             <label for="name"> Jurisdiction:</label>
			                               	<form:select path="jurisdictionU" class="form-control" id="jurisdictionU" title="Select one Jurisdiction from the list">
										   		<form:option value="" label="---Select Jurisdiction---"/>
										   		 <form:options items="${jurisMapEdit}" />	
											</form:select>
											<form:input type = "hidden" name="jurisdictionUText" path="jurisdictionUText" />
			                            </div>
			                            <div class="col-lg-6 form-group">
			                               	<button class="btn btn-primary" type="submit" name="uploadQamEnvForm" id="uploadQamEnvForm" title="Select Upload File button to upload CSR List">Upload File</button>
			                            </div>
			                        </div>
				                </div>
				            </div>
				            
				             <div class="row " >
				                <div class="col-lg-8 col-lg-offset-1 form-container">
				                    <h2>Search QAM Environment Change Form</h2> 
				                    <!-- <p> Please provide your feedback below: </p> -->
				                    <div class="row">
				                      <div id="searchalertMsg" style="color: red;font-size: 18px;"  class="col-lg-8 form-group"></div>
									</div>
				                     <div class="row">
			                            <div class="col-lg-6 form-group">
			                             <label for="name"> From Date:</label>
			                            	<form:input class="form-control"  type="text" path="fromDate" placeholder="From Date" title="Select From Date from the Calendar"/>
			                            </div>
			                             <div class="col-lg-6 form-group">
			                              <label for="name"> To Date:</label>
			                             	<form:input class="form-control"  type="text" path="toDate" placeholder="To Date" title="Select To Date from the Calendar"/>
			                            </div>
			                            
			                            <div class="col-lg-6 form-group">
			                             <label for="name"> MAC:</label>
			                               	<form:select path="macIdS" class="form-control" id="macIdS"  multiple="multiple" title="Select one or multiple MAC ID from the list">
										   <form:option value="" label="---Select MAC---"/>
										   <form:option value="ALL" label="Select ALL" />
										   <form:options items="${macIdMap}" />
										</form:select> 	
			                            </div>
			                            <div class="col-lg-6 form-group">
			                             <label for="name"> Jurisdiction:</label>
			                               	<form:select path="jurisdictionS" class="form-control" id="jurisdictionS" multiple="multiple" title="Select one or multiple Jurisdiction from the list">
										   <form:option value="" label="---Select Jurisdiction---" />
										   <form:option value="ALL" label="Select ALL" />
										    <form:options items="${jurisMapEdit}" />	
										</form:select>
			                            </div>
			                            <div class="col-lg-6 form-group">
			                               	<button class="btn btn-primary" id="searchCsr" title="Select Search QAM Environment Change Form button to get the QAM list">Search</button>
			                            </div>
			                        </div>
				                </div>
				            </div>
				            
				            <div class="row " id="csrListMonthDiv">
				                <div class="col-lg-10 col-lg-offset-1 form-container">
				                    
				                     <h3>QAM Environment Monthly List: </h3> 					                   
				                   
			                         <div class="row">
			                            <div class="col-lg-10 form-group">
			                                <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="csrMonthLists" style="width: 80%">
											<thead>
												<tr>
													<th title="Monthly">Month</th>
													<th title="MAC">Mac</th>
													<th title="Jurisdiction">Jurisdiction</th>
													<th title="Action">Action</th>
												</tr>
											</thead>
										</table>
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
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
