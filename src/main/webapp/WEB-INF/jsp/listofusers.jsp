<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>QAM - listofUsers</title>
<link href="${pageContext.request.contextPath}/resources/css/table.css"
	rel="stylesheet" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/Comrad_icon.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/button.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui-timepicker-addon.css" />

<link rel="stylesheet" href="/resources/demos/style.css" />
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>
<link rel="stylesheet"
	href="//cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css"></link>

<!-- CSS for Bootstrap -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet"></link>
<!-- JQuery -->
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.33.3/es6-shim.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/systemjs/0.19.20/system-polyfills.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-ui-timepicker-addon.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.16.0/jquery.validate.min.js"></script>
<script
	src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
	
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

	var data =eval('${users}');	
	var role = $('#userRole').val();
	
	var userListTable = $('#userLists').DataTable( {
	"aaData": data,
	"aoColumns": [
	{ "mData": "id"},
	{ "mData": "firstName"},
	{ "mData": "middleName"},
	{ "mData": "lastName"},
	{ "mData": "lastName"},
	{ "mData": "lastName"},
	{ "mData": "status"},
	{ "mData": "id"}
	],
    "columnDefs": [ 
        { 
           "render" : function(data, type, row) {
			var linkData = "<span><a class='action-icons c-edit' href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/edit-user/"+data+"' title='Edit User'>Edit</a></span>";
			if (role == 'Administrator'  ) {
				var linkData = linkData+ "<span><a class='action-icons c-delete' href='${pageContext.request.contextPath}/${SS_USER_FOLDER}/delete-user/"+data+"/${pageContext.request.userPrincipal.name}' title='Delete User' onclick='return confirm('Are you sure you want to delete this item?');'>Delete</a></span>";
			}
                       
            return linkData;		
             
        },
	   "targets" : 7
	   },
	 ],
	
	 dom: '<lif<t>pB>',
     buttons: [
         {
             extend: 'copyHtml5',
             exportOptions: {
                 columns: [ 0, 1, 2, 3, 4, 5, 6 ]
             },
             messageTop: 'User List.'
         },
         {
             extend: 'excelHtml5',
             exportOptions: {
                 columns: [ 0, 1, 2, 3, 4, 5, 6 ]
             },
             messageTop: 'User List.'
         },
         {
             extend: 'pdfHtml5',
             exportOptions: {
                 columns: [ 0, 1, 2, 3, 4, 5, 6 ]
             },
             messageTop: 'User List.',
             orientation : 'landscape',
             pageSize : 'LEGAL'
         }         
     ],
	  "paging" : true,
	  "pageLength" : 10,
	   "ordering" : true,
	});
	
});


$(function(){
	$('#delete').click(function(e) {	
		 e.preventDefault();		
          $("#dialog-confirm" ).dialog({
              resizable: false,
              height: "auto",
              width: 400,
              modal: true,	              
              buttons: {
                "Yes": function() {
              		$( this ).dialog("close");	              		
              		window.location.href= '${pageContext.request.contextPath}/${SS_USER_FOLDER}/listofusers';          	 	
                },
                Cancel: function() {                    
                	$( this ).dialog("close"); 
                }
              }
        });
     }); 
});
</script>
</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div role="main">
		<table id="mid">
			
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">

							<div class="panel panel-primary table-users" style="width: 75%; box-shadow: 3px 3px 0 rgba(0, 0, 0, 0.1); border: 1px solid #327a81;">
								<div class="panel-heading" style="background-color: #327a81;">List of Users</div>
								<div class="panel-body" style="background-color: white;">
								<form:form method="GET" modelAttribute="userFilterForm" action="${pageContext.request.contextPath}/${SS_USER_FOLDER}/userFilter">

									<table
										style="border-collapse: separate; border-spacing: 2px; valign: middle"
										id='table1'>
										<tr>
										</tr>
									</table>

									<div class="row">
										<div class="col-sm-3 form-group">
											<label for="lastName"> Last Name:</label> 
											<form:input type="text" path="lastName" class="form-control" title="Enter Last Name"/>
										</div>
										
										<div class="col-md-3 col-md-offset-1 form-container">
											<label for="roleId"> Role:</label> 
											<form:select path="roleId"
													class="form-control" id="roleId" title="Select Role ID From the List">
													<option value="" label="--- Select Role---" />
													<form:options items="${roleIds}" />
												</form:select>
										</div>
										<sec:authorize access="hasAuthority('Administrator') or hasAuthority('Quality Manager') or hasAuthority('CMS User')">
										<div class="col-md-3 col-md-offset-1 form-container">
											<label for="organizationId"> Organization:</label>
											
											 <form:select path="orgId"
													 class="form-control" id="organizationId" title="Select Organization from the List"
													>
													<option value="" label="--- Select Org---" />
													<form:options items="${orgIds}" />
												</form:select>
										</div>
										</sec:authorize>
									</div>
									<div class="row">
										<div class="col-sm-3 form-group">
											<button class=" btn btn-primary" title="Select Search button">Search</button>
											<sec:authorize access="hasAuthority('Administrator') or hasAuthority('MAC Admin')">
													<span><a href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/createusers" title="Select Add User button to add new user"><button class="btn btn-primary" id="addUser" type="button">Add User</button></a></span> 
													</sec:authorize>
										</div>
									</div>
									<div align="center">
									<c:if test="${not empty success}">
					                 	<div class="successblock" ><spring:message code="${success}"></spring:message>
					                    </div>
					                 </c:if>
					                 </div>
					                 
					                  <div class="row" id="userListTableDiv">
			                            <div class="col-lg-10 form-group">
			                                <table style="border-collapse: separate; border-spacing: 2px;" class="display data_tbl" id="userLists" style="width: 90%">
						                    <thead>
										        <tr>
										            <th style="text-align: left">User Id</th>
										            <th style="text-align: left">First Name</th>
										            <th style="text-align: left">Middle Name</th>
										            <th style="text-align: left">Last Name</th>
										            <th style="text-align: left">Organization</th> 
										            <th style="text-align: left">Role</th>
										            <th style="text-align: left">User Status</th>
										            <th style="text-align: left">Actions</th>
										           
										        </tr>
										    </thead>
						                    <tbody>  
						                    </tbody>
						                </table> 
			                            </div>		                           
			                        </div>         
					                 
					                 
					                 
					                 <%-- <div class="row">
								
								<div class="col-md-8">
									<table id="usersTable">
										<thead>
											<tr>
											<th style="text-align: left">
													User Id
												</th>
												<th style="text-align: left">
													FirstName
												</th>
												<th style="text-align: left">
													MiddleName
												</th>
												<th style="text-align: left">
													LastName
												</th>
												<th style="text-align: left">
													Organization
												</th>
												<th style="text-align: left">
													Role
												</th>
												<th style="text-align: left">
													User Access
												</th>
												<th style="text-align: left">
													Action
												</th>
											</tr>
										</thead>
										 <tbody>
					                        <c:forEach var="user" items="${users}">
					                            <tr>
					                          	  
					                                <td><a class="${linkcolor }">${user.id}</a></td>
					                                <td><a class="${linkcolor }">${user.firstName}</a></td>
					                                 <td><a class="${linkcolor }">${user.middleName}</a></td>
					                                  <td><a class="${linkcolor }">${user.lastName}</a></td>
					                                <td><a class="${linkcolor }">${user.organizationLookup.organizationName}</a></td>
					                                 <td><a class="${linkcolor }">${user.role.roleName}</a></td>
					                                <td><a class="${linkcolor }">${user.status == 1? "Active":"Inactive"}</a></td>
					                                <td>
					                                    <span><a class="action-icons c-edit" href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/edit-user/${user.id}" title="Edit User">Edit</a></span>
					                                    <span><a class="action-icons c-delete" href="${pageContext.request.contextPath}/${SS_USER_FOLDER}/delete-user/${user.id}/${pageContext.request.userPrincipal.name}" title="Delete User" onclick="return confirm('Are you sure you want to delete this item?');">Delete</a></span>
					                                </td>
					                            </tr>
					                        </c:forEach>
					                        </tbody>
									</table>
								</div>
								
							</div> --%>
									</form:form>
								</div>
							</div>
							
							
							
							
							
						</div>
				</td>
			</tr>

			<%-- 		</form:form> --%>
		</table>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
<head>
	<script>
	/* $(document).ready(function() {
		$("#usersTable").DataTable();
	
		$("#usersTable").on('dblclick','tr',function() {
			//alert("aaaaaa:"+$(this).text())
	          var id = $(this).find("td:first-child").text();
	          var updatedBy = "${pageContext.request.userPrincipal.name}";
	          var st = $(this).find("td:nth-child(7)");
	          var replaceSt = "Active";
	          var status = 1;
	          if(st.text() == 'Active'){
	        	  status = 0;
	        	  replaceSt = "Inactive";
	          }
	          
	          var username="qamadmin";
	          var password="123456";
	          $(this).toggleClass("select");
			 $.ajax({
				url : "${WEB_SERVICE_URL}updateStatus",
				 headers:{  "Authorization": "Basic " + btoa(username+":"+password)},
			    method: 'POST',
			    data: { userId : id, status: status, updatedBy: updatedBy },
			    success: function(result) {
			      var jsondata = $.parseJSON(result);
			       st.html(replaceSt);	
			       st.css("font-weight","bold");
			    }
			  });

	        });
	}); */
	
	
	</script>
</head>
</html>
