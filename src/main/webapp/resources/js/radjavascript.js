
/*!
 * Scorecard hide section 5 & 6 & 7
 */

<script type="text/javascript">
$(document).ready(function () {
	
	 $('#failreasoncommentsid').hide('fast');
	
                $('#idknowskillsno').click(function () {
                   $('#adherencetoprivacy').hide('fast');
                   $('#customerskills').hide('fast');
            });
                $('#idknowskillsyes').click(function () {
                    $('#adherencetoprivacy').show('fast');
                    $('#customerskills').show('fast');
             });
                $('#idknowskillscompleteno').click(function () {
                    $('#adherencetoprivacy').hide('fast');
                    $('#customerskills').hide('fast');
             });
                 $('#idknowskillscompleteyes').click(function () {
                     $('#adherencetoprivacy').show('fast');
                     $('#customerskills').show('fast');
              });
           });
</script>

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
      url : 'http://localhost:8080/radservices/api/createScoreCard',
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
	      url : 'http://localhost:8080/radservices/api/keepCurrentList?userId=1',
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
 	             url: "http://localhost:8080/radservices/api/csrList?fromDate=November 2017&toDate=November 2017",
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

$(document).ready(function(){
	$("#keepPreviousListButton").hide();
	$("#dialog-confirm").hide();
	
	// Call monitoring date - date picker
	$( function() {
	    $( "#datepicker" ).datepicker();
	  } );
	
   // Date and time picker
   
   $( function() {
	    $( "#datepickertime1" ).datetimepicker();
	  } );
	
   
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
