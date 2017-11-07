<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="breadcrumb">
    
        <a href="${pageContext.request.contextPath}/admin/dashboard">Home</a> 
        <span> >> </span>
        <a href="${pageContext.request.contextPath}/admin/users">Users</a> 
        <span> >> </span>
        <a href="#" style="text-decoration: none;">Edit User</a>
        
    
</div>
<div class="content">
    <div class="grid_container">
        <div class="grid_12 full_block">
            <div class="widget_wrap">
                <div class="widget_top">
                    <span class="h_icon list"></span>
                </div>
                <div class="widget_content">
                    <h6>Edit User : <c:out value='${user.name }'/></h6>
                    <form:form modelAttribute="user" class="form_container left_label"
                               action="../edit-user" method="post">


                        <ul>
                            <li>
                                <fieldset>
                                    <legend>User Information</legend>
                                    <ul>
                                        <li>
                                            <div class="form_grid_12">
                                                <label for="lastname" class="field_title">Full Name</label>
                                                <div class="form_input">
                                                    <form:input type="text" class="mid" id="lastname" name="lastname" path="name" ></form:input>
                                                    <form:input type="hidden" id="id" name="id" path="id"></form:input>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="form_grid_12">
                                                <label for="username" class="field_title">User Name</label>
                                                <div class="form_input">
                                                    <form:input type="text" class="mid" id="username" name="username" path="username" ></form:input>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="form_grid_12">
                                                <label for="password" class="field_title">Password</label>
                                                <div class="form_input">
                                                    <form:password showPassword="true" id="password" name="password"
                                                                   path="password" class="mid"></form:password>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="form_grid_12">
                                                <label for="email" class="field_title">Email Address</label>
                                                <div class="form_input">
                                                    <form:input type="text" id="email" class="mid" name="email" path="email" ></form:input>
                                                </div>
                                            </div>
                                        </li>
                                        
                                        <li>
                                <div class="form_grid_12">
                                  
							   
							</div>	
							</li>	

                                      

                                    </ul>
                                </fieldset>
                            </li>
                        </ul>

                        <ul>
                            <li>
                                <div class="form_grid_12">
                                    <button type="submit" class="btn_small btn_blue"><span>Update</span></button>
                                    <button type="reset" class="btn_small btn_blue"><span>Reset</span></button>
                                </div>
                            </li>
                        </ul>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>