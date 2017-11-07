<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type='text/javascript' src='https://www.google.com/jsapi'></script>
<script type='text/javascript'>
<script src= "http://code.jquery.com/jquery-1.8.3.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>

<script type="text/javascript">
$(document).ready(function(){
 
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

<div id="breadcrumb">
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/app/groupdashboard">Home</a> <span> >> </span>
        </li>
        <li>
            <a href="#" style="text-decoration: none;">Users</a>
        </li>
    </ul>
</div>
<div id="content">
    <div class="grid_container">
        <div class="grid_12">
			<div class="widget_wrap">
				 <div class="widget_top">
                    <span class="h_icon documents"></span>
                    <h6>Users</h6>
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
                        <c:forEach var="principal" items="${principals}">
                            <tr>
                                <td><a class="${linkcolor }">${principal.id}</a></td>
                                <td><a class="${linkcolor }" href="principal/${principal.id}">${principal.username}</a></td>
                                <td><a class="${linkcolor }">${principal.name}</a></td>
                                <td><a class="${linkcolor }">${principal.email}</a></td>
                                <td>
                                    <span><a class="action-icons c-edit" href="${pageContext.request.contextPath}/app/edit-principal/${principal.id}" title="Edit">Edit</a></span><span><a class="action-icons c-approve" href="${pageContext.request.contextPath}/app/new-principal" title="Create">Create</a></span>
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