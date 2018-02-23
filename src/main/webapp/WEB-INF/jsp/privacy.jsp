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
	
  $('button[id=uploadCsr]').click(function(e) {
    e.preventDefault();
    //Disable submit button
    //$(this).prop('disabled',true);
    $('#csrListViewDiv').hide();
	$('#csrListMonthDiv').hide();
   
    	var validatedMac = $('#macIdU').val();
		var validateJurisdiction = $('#jurisdictionU option:selected').val(); 
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

	  $('#jurisdictionUText').val($('#jurisdictionU option:selected').text()); 
	 	
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
     
      resetFields();
    });
    
    // Called on failure of file upload
    ajaxReq.fail(function(jqXHR) {
    	$('#alertMsg').text(jqXHR.responseText+'('+jqXHR.status+
          		' - '+jqXHR.statusText+')');
    	
    });
  });  

  
  $('button[id=keepPreviousListButton]').click(function(e) {
	  e.preventDefault();
	  //var result = $("#csrupload").valid();
	  //alert("result:"+result);
	  $('#csrListViewDiv').hide();
	  $('#csrListMonthDiv').hide();
	  
	  var selectedMac = $('#macIdK').val();
	  var validateJurisdiction = $('#jurisdictionK option:selected').val(); 
	  var selectedJurisdiction = $('#jurisdictionK option:selected').text(); 
		
	  if(selectedMac == "" && validateJurisdiction == "") {
		  $('#alertMsg').text("Please Select Mac Id and Jurisdiction Id");
			return;
		} else if(selectedMac == "" ) {
			  $('#alertMsg').text("Please Select Mac Id ");
				return;
			} else if(validateJurisdiction == "") {
				  $('#alertMsg').text("Please Select Jurisdiction Id");
					return;
				} 
	  
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

  $(document).on('click',".viewLink",function (){    


	  	$('#csrLists tbody').empty();
		$('#alertMsg').text('');
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
	 });

	$('#searchCsr').click(function(e){ 
  		$('#alertMsg').text('');
  		$('#csrMonthLists tbody').empty();
  		$('#csrListViewDiv').hide();
  	  	$('#csrListMonthDiv').hide();
  	  	
	  	e.preventDefault();			  	
	  	 var validateMac = $('select[name=macIdS]').val();
		 var validateJurisdiction = $('select[name=jurisdictionS]').val(); 	    	
	  	var validateFromDate = $('#fromDate').val();
		var validateToDate = $('#fromDate').val();		  
		//alert(validateMac+","+validateJurisdiction+","+validateFromDate+","+validateToDate)
		  if(validateMac == null && validateJurisdiction == null) {
			  $('#alertMsg').text("Please Select Mac Id and Jurisdiction Id");
				return;
			} else if(validateFromDate == "" ) {
				  $('#alertMsg').text("Please Enter From Date");
					return;
				}else if(validateToDate == "" ) {
					  $('#alertMsg').text("Please Enter To Date");
						return;
					} else if(validateMac == null ) {
						  $('#alertMsg').text("Please Select Mac Id ");
							return;
						} else if(validateJurisdiction == null) {
							  $('#alertMsg').text("Please Select Jurisdiction Id");
								return;
							} 

		  var selectedJurisArr = [];
	        $.each($("#jurisdictionS option:selected"), function(){            
	        	selectedJurisArr.push($(this).text());
	        });
		 
		 var selectedMac = JSON.stringify($('select[name=macIdS]').val());     	
		 var selectedJurisdiction = JSON.stringify(selectedJurisArr); 

		 
	 	//alert("inside Search:"+selectedMac+':::'+selectedJurisdiction+':::'+selectedJurisArr+"::::"+JSON.stringify(selectedJurisArr));
	 	var username="qamadmin";
	   	var password="123456";
	 	$.ajax({ 
           type: "GET",
           dataType: "json",
           data: {fromDate: $("#fromDate").val(), toDate: $("#toDate").val(), macIdS: selectedMac, jurisdictionS: selectedJurisdiction},
           url : "${WEB_SERVICE_URL}csrListMonths",
           headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
          success: function(data){	    
	        var resultCount = data.length;
	       // alert("Result Count:"+resultCount);
	        if(resultCount==0) {
	        	$('#alertMsg').text('No Data Found for the Selected Months');
		    } else {
		    	 
		    	var trHTML = '<tbody>';  
		    	$.each(data, function (i, item) {  	        
	      	        trHTML += '<tr><td align="center">' + item[0] + ' ' + item[1] + '</td><td style="text-align: center"><a class="viewLink" href="#" >View</a></td></tr>';
	      	        $('#alertMsg').text('CSR List Available Months Retrieved');
	  	    	});
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
});
</script>
<script type="text/javascript">
	var CsrListTable;
    $(document).ready(function () {

    	$('#csrMonthLists').hide();
    	$('#csrLists').hide();
    	$('#alertMsg').text('');
    	$("#keepPreviousListButton").hide();
    	$("#dialog-confirm").hide();
    	$("#macIdK_Div").hide();	
    	$("#jurisdictionK_Div").hide();	
    	
    	$('#progressBar_Hideme').hide();   

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

        $("#macIdS").change(function () {
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
                        $("#macIdK_Div").show();	
                        $("#jurisdictionK_Div").show();	
                        $('#progressBar_Hideme').hide();
                        $('#table1 .hideme').hide();
                                            
                      },
                      Cancel: function() {                    
                        $("#keepPreviousListButton").hide();
                        $("#macIdK_Div").hide();
                        $("#jurisdictionK_Div").hide();	
                        $('#table1 .hideme').show();
                        $('input[id=keepCurrentListCB]').attr('checked', false);
                        $( this ).dialog( "close" );
                      }
                      
                    }
    	            
                  });
                
            } else {
                $("#keepPreviousListButton").hide();
                $("#macIdK_Div").hide();
                $("#jurisdictionK_Div").hide();
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
		<form:form method="POST" modelAttribute="csrUploadForm" class="form-signin" action="#" enctype="multipart/form-data" id="csrupload">
			<tr>
				
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
						
						<div class="table-users" style="width: 80%">
								<div class="header">Privacy Policy</div>	
												
    
<div style="font-size:17px;padding-bottom:10px;">The privacy of our customers is important. We understand that visitors need to be in control of their personal information. 
You do not have to give us personal information to visit this site. </div>

<div style="font-size:17px;padding-bottom:10px;">This site uses session cookies only. We do not use any persistent cookies.</div>

<div style="font-size:17px;padding-bottom:10px;">A cookie is a small piece of information that is sent to your browser -- along with a Web page -- when you access a Web site. There are two kinds of cookies. A session cookie is a line of text that is stored temporarily in your computer's memory. 
Because a session cookie is never written to a drive, it is destroyed as soon as you close your browser. A persistent cookie is a more permanent line of text that gets saved by your browser to a file on your hard drive.</div>
				            
						</div>
					</div>

	<div class="table-users" style="width: 80%">
								<div class="header">System Security</div>	
												
    
<div style="font-size:21px;"><b>This is a U.S. Government Computer System</b></div>

<div style="font-size:17px;padding-bottom:10px;">So that this service remains available to you and all other visitors, we monitor network traffic to identify unauthorized attempts to upload or change information or otherwise cause damage to the web service. Use of this system constitutes consent to such monitoring and auditing. Unauthorized attempts to upload information and/or change information on this web site are strictly prohibited and are subject to prosecution under the Computer Fraud and Abuse Act of 1986 and Title 18 U.S.C. Sec.1001 and 1030. </div>

					</div>
						<div class="table-users" style="width: 80%">
								<div class="header">Disclaimer</div>	
												
    
<div style="font-size:17px;">This web site is not hosted on a Centers for Medicare & Medicaid Services (CMS) web server. </div>

					</div>
				</td>
			</tr>
		</form:form>
	</table>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
