<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CMS RAD</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#fromDate" ).datepicker();
    $( "#toDate" ).datepicker();
  } );
  $(document).ready(function() {
	  var username="qamadmin";
	   var password="123456";
	   
	   $('#ajax').click(function(){ 
	         $.ajax({ 
	             type: "GET",
	             dataType: "json",
	             url: "http://localhost:8080/radservices/api/csrList?fromDate=11102017&toDate=11152018",
	             headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
	             success: function(data){        
	            	var trHTML = '';
	                 
            	    $.each(data, function (i, item) {
            	        
            	        trHTML += '<tr><td>' + item.fisrtName + '</td><td>' + item.lastName + '</td></tr>';
            	    });
            	    
            	    $('#csrLists').append(trHTML);
	             }
	         });
	    });
	});
  </script>
</head>
<body>
 
<p>From Date: <input type="text" id="fromDate"></p>
<p>To Date: <input type="text" id="toDate"></p>
  
 <button id="ajax">Get CSR Lists</button>
 <br/>
 <table id="csrLists" border='1'>
    <tr>
        <th>First Name</th>
         <th>Last Name</th>
         
    </tr>
</table>
</body>
</html>