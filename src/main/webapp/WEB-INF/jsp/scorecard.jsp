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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/radjavascript.js" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
<link rel="stylesheet" href="/resources/demos/style.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.33.3/es6-shim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/systemjs/0.19.20/system-polyfills.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- CSS for Bootstrap -->
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"></link>
<!-- JQuery -->
 <script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>

	<table id="mid">
		<form:form method="POST" modelAttribute="userForm" class="form-signin" action="#">
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">

							<div class="table-users" style="width: 80%">
								<div class="header">ScoreCard</div>							
      
                                 <form action="fileUpload" method="post" enctype="multipart/form-data">
                                 <!--  Section 1 -->
								<table style="border-collapse: separate; border-spacing: 2px;" id='table1'>
									<tr>
									<td>
									<div class="scorecardtdclass">
										 <span>QM Full Name:</span>
										  <span style="padding-left:32px;"><form:input type = "text" name = "first_name" path="password" /></span>
       
							       </div>
							       <div class="scorecardtdclass">
										 <span>QM Start Date/Time:</span>
										 <span><form:input type = "text" name = "first_name" path="password" /></span>
       
									</div>
									<div class="scorecardtdclass">
										 QM End Date/Time: <span style="padding-left:5px;"><form:input type = "text" name = "first_name" path="password"/></span>
       </div>
									</td>
									</tr>
									</table>
									  <!--QAM Contract Information -->
									<table style="border-collapse: separate; border-spacing: 2px;" id='table1'>
									<tr><td>QAM Contract Information</td></tr>
									<tr>
									<td>
									<div class="scorecardtdclass">
										 <span>MAC:</span>
										   <span style="padding-left:87px;"><select id="name" name="name"
											title="Select one of the MAC">
												<option value="">Select MAC</option>
												<option value="Option 1">Option 1</option>
												<option value="Option 2">Option 2</option>
										</select></span>
       
							       </div>
							       </td>
							       </tr>
							       <tr>
							       <td>
							       <div class="scorecardtdclass">
										 <span>Jurisdiction</span>
										<span style="padding-left:53px;"><select id="name" name="name"
											title="Select one of the MAC">
												<option value="">Select MAC</option>
												<option value="Option 1">Option 1</option>
												<option value="Option 2">Option 2</option>
										</select></span>
       
       
									</div>
									
									</td>
									</tr>
									
									<tr>
							       <td>
							       <div class="scorecardtdclass">
										 <span>Program</span>
										 <span style="padding-left:65px;"><select id="name" name="name"
											title="Select one of the MAC">
												<option value="">Select MAC</option>
												<option value="Option 1">Option 1</option>
												<option value="Option 2">Option 2</option>
										</select></span>
       
       
									</div>
									
									</td>
									</tr>
									
									<tr>
							       <td>
							       <div class="scorecardtdclass">
										 <span>LOB</span>
										 <span style="padding-left:89px;"><select id="name" name="name"
											title="Select one of the MAC">
												<option value="">Select MAC</option>
												<option value="Option 1">Option 1</option>
												<option value="Option 2">Option 2</option>
										</select></span>
       
       
									</div>
									
									</td>
									</tr>
									</table>	
									  <!--QAM Call and CSR Information -->
									<table style="border-collapse: separate; border-spacing: 2px;" id='table1'>
									<tr>
									<td>
									QAM Call and CSR Information
									</td>
									</tr>
									<tr>
									<td>
									<div class="scorecardtdclass">
										 <span>CSR Full Name:</span>
										  <span style="padding-left:20px;"><input type = "text" name = "first_name" /></span>
       
							       </div>
							       </td>
							       </tr>
							       <tr>
							       <td>
							       <div class="scorecardtdclass">
										 <span>Call Category:</span>
										
										 <span style="padding-left:25px;"><select id="name" name="name"
											title="Select one of the MAC">
												<option value="">Select MAC</option>
												<option value="Option 1">Option 1</option>
												<option value="Option 2">Option 2</option>
										</select></span>
       
       
									</div>
									</td>
									</tr>
									<tr>
									<td>
									
										 <div class="scorecardtdclass">
										 <span> Call Duration:</span> 
										  <span style="padding-left:25px;">
										  <input type = "text" name = "first_name" /></span>
                                   </div>
									</td>
									</tr>
									<tr>
									<td>
									
										 <div class="scorecardtdclass">
										 <span>
									
										 MAC Call Reference Id:</span>
										 <span style="padding-left:25px;">
										  <input type = "text" name = "first_name" />
										  </span></div>
                                   
									</td>
									</tr>
									<tr>
									<td>
									 <div class="scorecardtdclass">
										 <span>
									
										 Call Time:</span>
										   <span style="padding-left:25px;">
										   <input type = "text" name = "first_name" /></span>
										   </div>
                                   
									</td>
									</tr>
									<tr>
									<td>
									<div class="scorecardtdclass">
										 <span>
										 Call Language: </span>
										 <span style="padding-left:25px;">
										  <input type = "text" name = "first_name" />
										  </span>
										  </div>
                                   
									</td>
									</tr>
									<tr>
									<td>
									<div class="scorecardtdclass">
										 <span>
									
										 CSR Level:</span>
										 <span style="padding-left:25px;">
										  <input type = "text" name = "first_name" />
										  </span></div>
                                   
									</td>
									</tr>
									</table>
									  <!--Knowledge Skills-->
									  <div id="knowledgeskills">
									<table style="border-collapse: separate; border-spacing: 2px;" id='table1'>
									<tr>
									<td>
									Knowledge Skills
									</td>
									</tr>
									<tr>
									<td>
									<div class="scorecardtdclass">
										 <span>Did the CSR provide accurate information? If 'No' was selected,please enter reason in text box below:</span>
										  <span>
										  <form:radiobutton path="password" value="Yes" name="knowskills" id="idknowskillsyes" />Yes
										  <form:radiobutton path="password" value="No" name="knowskills" id="idknowskillsno"/>No
                                         </span>
                                        
							       </div>
							     
									</td>
									</tr>
									<tr>
									<td>
									<div class="scorecardtdclass">
										 <span>Did the CSR provide complete information? If 'No' was selected,please enter reason in text box below:</span>
										  <span>
										  <form:radiobutton path="password" value="Yes" name="knowskillscomplete" id="idknowskillscompleteyes" />Yes
										  <form:radiobutton path="password" value="No"  name="knowskillscomplete" id="idknowskillscompleteno"/>No
                                         </span>
                                        
							       </div>
							     
									</td>
									</tr>
									</table>
									</div>
									  <!--Adherence to Privacy-->
									   <div id="adherencetoprivacy">
									<table style="border-collapse: separate; border-spacing: 2px;" id='table1'>
									
									<tr>
									<td>
									
									Adherence to Privacy
									</td>
									</tr>
									<tr>
									
									<td>
									<div class="scorecardtdclass">
										 <span>Did CSR follow privacy procedures? If 'No' was selected,please select the reason below:</span>
										  <span>
										  <form:radiobutton path="password" value="Yes"/>Yes
										  <form:radiobutton path="password" value="No"/>No
                                         </span>
                                        
							       </div>
							     
									</td>
									
									</tr>
									</table>
									</div>
									  <!--Customer Skills-->
									   <div id="customerskills">
									<table style="border-collapse: separate; border-spacing: 2px;" id='table1'>
									<tr>
									<td>
									
								Customer Skills
									</td>
									</tr>
									<tr>
									
									<td>
									<div class="scorecardtdclass">
										 <span>Did CSR follow privacy procedures? If 'No' was selected,please select the reason below:Was CSR courteous,friendly,and professional? If 'No' was selected,please select the reason below:</span>
										  <span>
										  <form:radiobutton path="password" value="Yes"/>Yes
										  <form:radiobutton path="password" value="No"/>No
                                         </span>
                                        
							       </div>
							     
									</td>
									
									</tr>
									</table>
									</div>
									  <!--Result-->
									<table style="border-collapse: separate; border-spacing: 2px;" id='table1'>
					<tr>
									
									<td>
									Results
							     
									</td>
									
									</tr>
									<tr>
									<td>
									<div class="scorecardtdclass">
										 <span>Call Result:</span>
										
										 <span><select id="name" name="name"
											title="Select one of the MAC">
												<option value="">Select</option>
												<option value="Option 1">Pass</option>
												<option value="Option 2">Fail</option>
										</select></span>
       
       
									</div>
									<div class="scorecardtdclass">
										 <span>Call Failure Time:</span>
										
										 <span><select id="name" name="name"
											title="Select one of the MAC">
												<option value="">Select</option>
												<option value="Option 1">Pass</option>
												<option value="Option 2">Fail</option>
										</select></span>
       
       
									</div>
									</td>
									</tr>
									<tr>
									<td>
									<div class="scorecardtdclass" id="failreasoncommentsid">
										 <span>Fail Reason Comments:</span>
										  <span><input type = "text" name = "first_name" /></span>
       
							       </div>
							       </td>
							       </tr>
							       <tr>
									<td>
									<div class="scorecardtdclass">
										 <span>Additional Comments Box:</span>
										  <span><input type = "text" name = "first_name" /></span>
       
							       </div>
							       </td>
							       </tr>
									</table>							
										<table style="border-collapse: separate; border-spacing: 2px;valign:middle" id='table1'>
					
									
									<tr>
									<td><span><button class="btn btn-primary" id="create">Create</button></span>
									<span><button class="btn btn-primary" id="close">Close</button></span></td>
								

							       </tr>
							      
									
									</table>
								
									</form>		
														
									
									


								
								


								

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
