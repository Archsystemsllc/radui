<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>CSR List</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/Comrad_icon.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/button.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
<link rel="stylesheet" href="/resources/demos/style.css" />
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" />

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

<script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>

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
      //url : 'http://radservices.us-east-1.elasticbeanstalk.com/api/uploadCsrList',
      url : "${WEB_SERVICE_URL}uploadCsrList",
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
      $('button[type=submit]').prop('disabled',false);
      resetFields();
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
	  var form = $("#keepCurrentListForm");
	  	form.validate({
	  	    errorPlacement: function errorPlacement(error, element) { element.before(error); }
	  	});
	  	form.validate().settings.ignore = ":disabled,:hidden";
	  return form.valid();
	  var selectedMac = $('select[id=macIdK]').val();
	  var selectedJurisdiction = $('#jurisdictionK option:selected').text(); 
	  
	  var username="qamadmin";
	    var password="123456";
	  $.ajax({ 
	      type: "GET",
	      dataType: "json",
	      data: {userId: $('#userId').val(), macIdK: selectedMac, jurisdictionK: JSON.stringify(selectedJurisdiction)},
	      url : "${WEB_SERVICE_URL}keepCurrentList",
	      headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
	     success: function(data){	    
		     //alert("Inside success"+data.erroMessage+data.status);
	    	 $('#alertMsg').text(data.erroMessage);
		      $('button[id=keepPreviousListButton]').prop('disabled',false);		    
	     },
	     failure: function (jqXHR) {
	    	 $('#alertMsg').text(jqXHR.responseText+'('+jqXHR.status+
	 	      		' - '+jqXHR.statusText+')');
	 	      $('button[id=keepPreviousListButton]').prop('disabled',false);
	     }
	 	});
	 });  
});
</script>
<script type="text/javascript">
	
    $(document).ready(function () {

    	var form = $("#keepCurrentListForm");
    	form.validate({
    	    errorPlacement: function errorPlacement(error, element) { element.before(error); }
    	});
    	
    	$('#csrMonthLists').hide();
    	$('#csrLists').hide();
    	$('#alertMsg').text('');
    	
    	$('#table1 .progressBar_Hideme').hide();    

    	$('#ajax').click(function(e){ 
 	   		$('#alertMsg').text('');
 	   		$('#csrMonthLists tbody').empty();
 	   		
 		  	e.preventDefault();			  	

 		  	var selectedMac = $('select[name=macIdS]').val();
 		  	var selectedJurisdiction = $('select[name=jurisdictionS]').val(); 	    	
 		 	
 		 	//alert("inside Search:"+selectedMac+':::'+selectedJurisdiction);
 		 	var username="qamadmin";
 		   	var password="123456";
 		 	$.ajax({ 
	             type: "GET",
	             dataType: "json",
	             data: {fromDate: $("#datepicker1").val(), toDate: $("#datepicker2").val(), macIdS: JSON.stringify(selectedMac), jurisdictionS: JSON.stringify(selectedJurisdiction)},
	             url : "${WEB_SERVICE_URL}csrListMonths",
	             headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
	            success: function(data){	    
		            //alert("Inside Success");        	 
            		var trHTML = '<tbody>';
                 
        	    	$.each(data, function (i, item) {
        	        
	        	        trHTML += '<tr><td align="center">' + item[0] + ' ' + item[1] + '</td><td style="text-align: center"><a class="viewLink" href="#" >View</a></td></tr>';
	        	        $('#alertMsg').text('CSR List Available Months Retrieved');
        	    	});
        	    
        	    trHTML += '</tbody>';
        	    
        	    $('#csrMonthLists').append(trHTML);
        	    $('#csrMonthLists').show();
        	    
        	    
	            },
	            failure: function () {
	                $("#csrMonthLists").append("Error when fetching data please contact administrator");
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
	$("#macIdK").hide();	
	$("#jurisdictionK").hide();	
	
    $("#datepicker1").datepicker({ 
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

    $("#datepicker2").focus(function () {
        $(".ui-datepicker-calendar").hide();
        $("#ui-datepicker-div").position({
            my: "center top",
            at: "center bottom",
            of: $(this)
        });    
    }); 

    $("select#macIdK").change(function(){
        $.getJSON("${pageContext.request.contextPath}/admin/selectJuris",                    
                {macId: $(this).val(), multipleInput: false}, function(data){
           
             $("#jurisdictionK").get(0).options.length = 0;	           
  	      	 $("#jurisdictionK").get(0).options[0] = new Option("---Select Jurisdiction---", "");
  	  	    	$.each(data, function (key,obj) {
  	  	    		$("#jurisdictionK").get(0).options[$("#jurisdictionK").get(0).options.length] = new Option(obj, key);
  	  	    		
  	  	    	});  	   
           });
    });

    $( "#macIdS" ).change(function () {
      var selectedMacs = "";
      $( "#macIdS option:selected" ).each(function() {
    	  selectedMacs += $( this ).val() + ",";
      });
     
     $.getJSON("${pageContext.request.contextPath}/admin/selectJuris",                    
             {macId: selectedMacs, multipleInput: true}, function(data){
        
          $("#jurisdictionS").get(0).options.length = 0;	
          $("#jurisdictionS").get(0).options[0] = new Option("---Select Jurisdiction---", "");           
	      	 $("#jurisdictionS").get(0).options[1] = new Option("Select ALL", "ALL");
	  	    	$.each(data, function (key,obj) {
	  	    		$("#jurisdictionS").get(0).options[$("#jurisdictionS").get(0).options.length] = new Option(obj, key);
	  	    		
	  	    	});  	   
        });
    })
    .change();

    $("select#macIdU").change(function(){
        $.getJSON("${pageContext.request.contextPath}/admin/selectJuris",                    
                {macId: $(this).val(), multipleInput: false}, function(data){
           
             $("#jurisdictionU").get(0).options.length = 0;	           
             $("#jurisdictionU").get(0).options[0] = new Option("---Select Jurisdiction---", "");
  	  	    	$.each(data, function (key,obj) {
  	  	    		$("#jurisdictionU").get(0).options[$("#jurisdictionU").get(0).options.length] = new Option(obj, key);
  	  	    		
  	  	    	});  	   
           });
    });
    
    $('#keepCurrentListCB').click(function() {
    	$('#alertMsg').text('');
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
                    $("#macIdK").show();	
                    $("#jurisdictionK").show();	
                    $('#table1 .progressBar_Hideme').hide();
                    $('#table1 .hideme').hide();
                                        
                  },
                  Cancel: function() {                    
                    $("#keepPreviousListButton").hide();
                    $("#macIdK").hide();
                    $("#jurisdictionK").hide();	
                    $('#table1 .hideme').show();
                    $('input[id=keepCurrentListCB]').attr('checked', false);
                    $( this ).dialog( "close" );
                  }
                  
                }
	            
              });
            
        } else {
            $("#keepPreviousListButton").hide();
            $("#macIdK").hide();
            $("#jurisdictionK").hide();
            $('#table1 .hideme').show();
        }
    }); 
    
    
});


</script>
<script>
var CsrListTable;
$(document).ready(function(){
	
CsrListTable = $("#csrLists").DataTable({
	data:[],
	columns: [
	{ "data": "firstName" },
	{ "data": "middleName" },
	{ "data": "lastName" },
	{ "data": "level" },
	{ "data": "location" },
	{ "data": "jurisdiction" },
	{ "data": "program" },
	{ "data": "status" }
	],
	rowCallback: function (row, data) {},
		filter: false,
		info: false,
		ordering: true,
		processing: true,
		retrieve: true,
		sort:true
		
	});

$('#csrLists').hide();
});

$(document).on('click',".viewLink",function (){    
	
	$('#csrLists tbody').empty();
	$('#alertMsg').text('');
	 var row= $(this).closest('tr');  
  	var monthYear=$("td:eq(0)",row).text(); 
  	var selectedMac = $('select[name=macIdS]').val();
	var selectedJurisdiction = $('select[name=jurisdictionS]').val(); 	
	var username="qamadmin";
	var password="123456";
  
     $.ajax({ 
         type: "GET",
         dataType: "json",
         data: {fromDate: $("#datepicker1").val(), toDate: $("#datepicker2").val(), macIdS: JSON.stringify(selectedMac), jurisdictionS: JSON.stringify(selectedJurisdiction)},
         url : "${WEB_SERVICE_URL}csrList",
         headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
        success: function(data){ 
            $('#csrLists').show();
        	CsrListTable.clear().draw();
        	CsrListTable.rows.add(data).draw();	
        },
        failure: function () {
            $("#csrLists").append("Error when fetching data please contact administrator");
        }
    });
});
</script>

</head>
<body>
	<%-- <jsp:include page="admin_header.jsp"></jsp:include> --%>
	<div id="dialog-confirm" title="Current CSR List Confirmation?">
  		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Are you sure you want to keep the current CSR list?</p>
	</div>
	
	<table id="mid">
		<form:form method="POST" modelAttribute="csrUploadForm" class="form-signin" action="#" enctype="multipart/form-data" id="csrupload">
			<tr>
				
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">

							<div class="table-users" style="width: 80%">
								<div class="header">CSR Lists</div>							
      

								<table style="border-collapse: separate; border-spacing: 2px;" id='table1'>
									<tr>
									<td class='progressBar_Hideme' colspan="5">
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
									<form id="keepCurrentListForm" action="#">
										<td colspan="1" align="left" style="text-align: left"><a class="${linkcolor }"
												href="${pageContext.request.contextPath}/resources/static/CSR_LIST_TEMPLATE_SAMPLE.xlsx">Download Sample CSR Template</a></td>
										
										<td colspan="1" align="left" style="text-align: left"><form:checkbox
												path="keepCurrentListCB" value="keepCurrentListCB" id="keepCurrentListCB"/>
												<label for="keepCurrentListCB">&nbsp;Keep Current List</label></td>
										<td colspan="1">
										
										<form:select path="macIdK" id="macIdK" class="required">
										   <form:option value="" label="---Select MAC---"/>
										   <form:options items="${macIdMap}" />
										</form:select> 	
										</td>
										<td>
										
										<form:select path="jurisdictionK"  id="jurisdictionK" class="required">
										   <form:option value="" label="---Select Jurisdiction---"/>										   
										</form:select></td>
										<td colspan="1" ><button class="btn btn-primary" id="keepPreviousListButton">Keep List</button></td>
									</form>
									</tr>									
									
									<tr class='hideme'>
										<td><label for="file">CSR List Upload: </label></td>

										<td colspan="1" align="right">
										<input type="hidden" id="userId" name="userId" value="1"/>
										<input class="form-control" id="file" type="file" name="file" style="box-sizing: content-box;">
										</input></td>
										<td colspan="1">
										
										<form:select path="macIdU" class="form-control" id="macIdU">
										   <form:option value="" label="---Select MAC---"/>
										   <form:options items="${macIdMap}" />
										</form:select> 	
										</td>
										<td>
										
										<form:select path="jurisdictionU" class="form-control" id="jurisdictionU">
										   <form:option value="" label="---Select Jurisdiction---"/>
										   
										</form:select>
										</td>
										<td><button class="btn btn-primary" type="submit" name="uploadButton" id="uploadButton">Upload File</button></td>		
									</tr>
									
									<tr>

										<td><input type="input" path="password"
												placeholder="From Date" id="datepicker1" ></input></td>
										<td><input type="input" path="passwordConfirm"
												placeholder="To Date" id="datepicker2"></input></td>
										<td>
										
										<form:select path="macIdS" class="form-control" id="macIdS"  multiple="multiple">
										   <form:option value="" label="---Select MAC---"/>
										   <form:option value="ALL" label="Select ALL" />
										   <form:options items="${macIdMap}" />
										</form:select> 	
										</td>
										<td>
										
										<form:select path="jurisdictionS" class="form-control" id="jurisdictionS" multiple="multiple">
										   <form:option value="" label="---Select Jurisdiction---" />
										   <form:option value="ALL" label="Select ALL" />
										   
										</form:select></td>
										<td style="padding-top: 10px"><button class="btn btn-primary" id="ajax">Search CSR</button></td>
									</tr>
								</table>
								<br/>


								<table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="csrMonthLists" style="width: 80%">
									<thead>
										<tr>
											<th title="Monthly">Monthly</th>
											<th title="CSR Name">Action</th>

										</tr>
									</thead>
									
								</table>
								
								<br/>
								
								<div id="csrlistsdiv" style="width: 100%">
												
								<table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="csrLists" style="width: 90%">
				                    <thead>
								        <tr>
								            <th style="text-align: left">First Name</th>
								            <th style="text-align: left">Middle Name</th>
								            <th style="text-align: left">Last Name</th>
								            <th style="text-align: left">CSR Level</th>
								            <th style="text-align: left">Location</th>
								            <th style="text-align: left">Jurisdiction</th>
								            <th style="text-align: left">Program</th>       
								            <th style="text-align: left">Status</th>
								        </tr>
								    </thead>
				                    <tbody>  
				                    </tbody>
				                </table> 
				                </div>
				                <br/>
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
