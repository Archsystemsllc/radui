<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="breadcrumb">
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/app/groupdashboard">Home</a> <span> >> </span>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/app/principals">Users</a> <span> >> </span>
        </li>
        <li>
            <a href="#" style="text-decoration: none;">User</a>
        </li>
    </ul>
</div>
<div class="content">
    <div class="grid_container">
        <div class="grid_12 full_block">
            <div class="widget_wrap">
                <div class="widget_top">
                    <span class="h_icon list"></span>
                    <h6>User</h6>
                </div>
                <div class="widget_content">
                 <form action="../edit-principal/${principal.id}" class="form_container left_label">
                        <span class="subheader-title">Edit User Information</span>
                        
                        <ul>
                            <li>
                                <div class="form_grid_12">
                                    <label class="field_title">ID</label>
                                    <div class="form_input">
                                        <span class="uneditable-input mid">${principal.id}</span>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="form_grid_12">
                                    <label class="field_title">Username</label>
                                    <div class="form_input">
                                        <span class="uneditable-input mid">${principal.username}</span>
                                    </div>
                                </div>
                            </li>
                             <li>
                                <div class="form_grid_12">
                                    <label class="field_title">Name</label>
                                    <div class="form_input">
                                        <span class="uneditable-input mid">${principal.name}</span>
                                    </div>
                                </div>
                            </li>
                             <li>
                                <div class="form_grid_12">
                                    <label class="field_title">Email</label>
                                    <div class="form_input">
                                        <span class="uneditable-input mid">${principal.email}</span>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="form_grid_12">
                                    <label class="field_title">JIRA User Name</label>
                                    <div class="form_input">
                                        <span class="uneditable-input mid">${principal.jiraUsername}</span>
                                    </div>
                                </div>
                            </li>
                            
                              <li>
                                <div class="form_grid_12">
                                    <label class="field_title">Roles</label>
                                    <div class="form_input">
                                        <span class="uneditable-input mid">
                                        <c:forEach var="role" items="${principal.roles}">
									 	<c:out value="${role.name}" />
										</c:forEach>
										</span>
                                    </div>
                                </div>
                            </li>
                             <li>
                                <div class="form_grid_12">
                                    <label class="field_title">Email</label>
                                    <div class="form_input">
                                        <span class="uneditable-input mid">${principal.email}</span>
                                    </div>
                                </div>
                            </li>
                            </ul>
                        <ul>
                            <li>
                                <div class="form_grid_12">
                                    <a href="../edit-principal/${principal.id}"><button class="btn_small btn_blue">Edit Principal</button></a>
                                </div>
                            </li>
                        </ul>
                        
                	</form>
            	</div>
        	</div>
    	</div>
	</div>
</div>
