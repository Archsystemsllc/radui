<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>ADDA - Users </title>
<link href="${pageContext.request.contextPath}/resources/css/table.css"
	rel="stylesheet" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/adda_ico.png" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/button.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.33.3/es6-shim.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/systemjs/0.19.20/system-polyfills.js"></script>
<script
	src="https://code.angularjs.org/2.0.0-beta.6/angular2-polyfills.js"></script>
<script src="https://code.angularjs.org/tools/system.js"></script>
<script src="https://code.angularjs.org/tools/typescript.js"></script>
<script src="https://code.angularjs.org/2.0.0-beta.6/Rx.js"></script>
<script src="https://code.angularjs.org/2.0.0-beta.6/angular2.dev.js"></script>

<script type='text/javascript' src='https://www.google.com/jsapi'></script>
<script type='text/javascript'>
<script src= "http://code.jquery.com/jquery-1.8.3.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('.nav > li').eq(3).addClass('active');
    $('#table ul li a').append('<span></span>');
 
    $('#table ul li a').hover(
        function(){ 
            $(this).find('span').animate({opacity:'show', top: '-70'}, 'slow');
 
            var hoverTexts = $(this).attr('title');
             $(this).find('span').text(hoverTexts);
        },
 
        function(){ 
            $(this).find('span').animate({opacity:'hide', top: '-90'}, 'fast');
        }
    );
});
</script>
</head>

<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<table id="mid">
		<tr>
			<td
				style="background-color: #327a89; width: 30%; vertical-align: top; padding: 0px 25px">
				<div style="color: #fff">
					<!-- <ul style="border-bottom: solid #fff 2px" type="square">
						<li><h2
								style="color: #fff; font-family: 'Rubik', sans-serif;">Description</h2></li>
					</ul> -->
					<p style="text-align: justify; font-family: 'Rubik', sans-serif;">
						<br></br> <br></br>From the Base Year to Option Year 3 Rural Area
						Percentage line plot, we would like to see the change trend of the
						rural area percentage of all combined EPs and GPROs and the
						difference among reporting options (Claims, Registry, EHR, QCDR
						and GPROWI)
					</p>
				</div>
			</td>
			<td style="vertical-align: top">

				<div id="updates" class="boxed">

					<div class="content">
						<div class="table-users" style="width: 80%">
							<div class="header">Users List</div>
							<div id="breadcrumb">
						    	<a href="${pageContext.request.contextPath}/admin/dashboard">Home</a> 
						        <span> >> </span>
						        <a href="#" style="text-decoration: none;">Users</a>
							</div>
							<div style="float: right;">
								<span><a href="${pageContext.request.contextPath}/admin/registration" title="Create">Create New User</a></span>
							</div>
							<div id="content">
									    <div class="grid_container">
									        <div class="grid_12">
												<div class="widget_wrap">
													 <div class="widget_top">
									                    <span class="h_icon documents"></span>
									                </div>
									                <div class="widget_content">
									                 <c:if test="${not empty success}">
									                 	<div class="successblock"><spring:message code="${success}"></spring:message>
									                    </div>
									                 </c:if>
									                    <table class="display data_tbl" id="table">
									                        <thead>
									                        <tr>
									                            <th title="User Id">ID</th>
									                            <th title="Username">Username</th>	
																<th title="Name of the User">Name</th>
																<th title="Email Id">Email</th>
																<th title="Edit or Create User">Manage</th>
									                        </tr>
									                        </thead>
									                        <tbody>
									                        <c:forEach var="user" items="${users}">
									                            <tr>
									                                <td><a class="${linkcolor }">${user.id}</a></td>
									                                <td><a class="${linkcolor }" href="edit-user/${user.id}">${user.username}</a></td>
									                                <td><a class="${linkcolor }">${user.name}</a></td>
									                                <td><a class="${linkcolor }">${user.email}</a></td>
									                                <td>
									                                    <span><a class="action-icons c-edit" href="${pageContext.request.contextPath}/admin/edit-user/${user.id}" title="Edit">Edit</a></span>
									                                    <span><a class="action-icons c-delete" href="${pageContext.request.contextPath}/admin/delete-user/${user.id}" title="Delete" onclick="return confirm('Are you sure you want to delete this item?');">Delete</a></span>
									                                </td>
									                            </tr>
									                        </c:forEach>
									                        </tbody>
									                    </table>
									                </div>
									            </div>
									        </div>
									        <span class="clear"></span>
									    </div>
									</div>	
						</div>
					</div>
				</div>

			</td>			
		</tr>
	</table>		
	
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
