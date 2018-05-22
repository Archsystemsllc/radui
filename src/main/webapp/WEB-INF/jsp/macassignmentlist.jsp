<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>QAM - Mac Assignment List</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/Comrad_icon.png" />
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
<script src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.html5.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.print.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.colVis.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	var data =eval('${macAssignmentObjectList}');
	
	var role = $('#userRole').val();
	
	var macassignmentListTable = $('#macassignmentLists').DataTable( {
	"aaData": data,
	"aoColumns": [
	{ "mData": "assignedMonthYear"},
	{ "mData": "assignedMonthYear"}
	],
    "columnDefs": [ 
        { 
           "render" : function(data, type, row) {
			//var linkData = "<span><a class='action-icons c-pending'	href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/view-macassignment/"+data+"' title='View'>View</a></span>";
			if (role == 'Administrator' || role == 'Quality Manager') {
				var linkData = "<span><a class='action-icons c-edit'	href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/edit-macassignment/"+data+"' title='Edit'>Edit</a></span>";
			}
                       
            return linkData;		
             
        },
	   "targets" : 1
	   },
	 ],	
	 
	  "paging" : true,
	  "pageLength" : 10,
	   "ordering" : true,
	});
});
</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div role="main">
	<table id="mid">
		<form:form method="POST" modelAttribute="macAssignment" class="form-signin"	action="#"
			id="rebuttalfilterForm">
			<tr>
				<td style="vertical-align: top" >

					<div id="updates" class="boxed">

						<div class="content">

							<div class="table-users" style="width: 90%">
								<div class="header">List of Mac Assignments By Month</div>
							<!-- 	Removed Blank Space - 508 -->
							 <input type="hidden" id="userRole" value='${SS_LOGGED_IN_USER_ROLE}'/>					
								
								<div class="row" id="rebuttallistdatatablediv">
				                <div class="col-lg-12 col-lg-offset-1 form-container">
				                   
				                    <!-- <p> Please provide your feedback below: </p> -->		
				                    
				                    <div class="row ">
										 <div class="col-lg-10 form-group">
											<table
												style="border-collapse: separate; border-spacing: 2px; "
												id='table1'>
												<tr align="left">
													<td align="left">
													
													<sec:authorize access="hasAuthority('Administrator') or hasAuthority('Quality Monitor')">
													<span><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/edit-macassignment/null" title="Select Add Mac Assignment button to add new Mac Assignment"><button class="btn btn-primary" id="addmacassignment" type="button">Add Mac Assignment For Month</button></a></span> 
													</sec:authorize>
												</td>
												
												</tr>
											</table>
										</div>
									</div>		                   
				                   
			                         <div class="row" id="macassignmentlistdatatable">
			                            <div class="col-lg-10 form-group">
			                                <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="macassignmentLists" style="width: 90%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">Month/Year</th>
										            <th style="text-align: left">Actions</th>
										           
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
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
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>