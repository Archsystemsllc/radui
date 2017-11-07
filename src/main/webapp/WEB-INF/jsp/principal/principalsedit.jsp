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
            <a href="#" style="text-decoration: none;">Edit User</a>
        </li>
    </ul>
</div>
<div class="content">
    <div class="grid_container">
        <div class="grid_12 full_block">
            <div class="widget_wrap">
                <div class="widget_top">
                    <span class="h_icon list"></span>
                    <h6>Principal Item</h6>
                </div>
                <div class="widget_content">
                    <h6>Edit User : <c:out value='${principal.name }'/></h6>
                    <form:form modelAttribute="principal" class="form_container left_label"
                               action="../edit-principal" method="post">


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
                                                <label for="email" class="field_title">JIRA User Name</label>
                                                <div class="form_input">
                                                    <form:input type="text" id="jiraUsername" class="mid" name="jiraUsername" path="jiraUsername" ></form:input>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                <div class="form_grid_12">
                                  
							   <table>
							     <tr>
							       <td> <label class="field_title">Roles</label>
							       <form:select style="width:300px" multiple="true" id="rolesList" name="rolesList" path="rolesList" class='cho' cssClass="cho"
									items="${referenceData.roleCompleteList}" itemLabel="name" itemValue="id">
									</form:select>
									
								</td>
							       <td><label class="field_title">Current Privileges</label>								
									<form:select disabled="true" style="width:300px" mulitiple="true" id="privileges" name="privileges" path="privileges" items="${privileges}" itemLabel="name" itemValue="id">
										
									</form:select>
								</td>
							     </tr>
							   </table>
							</div>	
							</li>	

                                       <%--  <li>
                                            <div class="form_grid_12">
                                                <label class="field_title">Roles</label>
                                                <div class="form_input">
                                                    <div class="list_left">
                                                        <div class="list_filter">
                                                            <label>Filter: </label>
                                                            <input type="text" id="box1Filter"/>
                                                            <span id="box1Clear" class="list_refresh"><span class="filter_btn refresh_3">&nbsp;</span></span>
                                                        </div>
                                                            <select multiple="multiple" class="multiple_list" id="roles" name="roles" path="roles" >
                                                                <c:forEach var="role" items="${roleCompleteList}">
                                                                    <option id="${role.id}" value="${role.name}"  />
                                                                </c:forEach>
                                                            </select>
                                                        <select id="box1View" multiple="multiple" class="multiple_list" name="roles" path="roles">
                                                            <c:forEach var="role" items="${roleCompleteList}">
                                                                <option id="${role.id}" value="${role.name}">${role.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <div class="list_itemcount">
                                                            <span id="box1Counter" class="countLabel"></span>
                                                            <select id="box1Storage">
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="list_switch">
                                                        <span id="to2" class="swap_btn"><span class="filter_btn p_next">&nbsp;</span></span><span id="to1" class="swap_btn"><span class="filter_btn p_prev">&nbsp;</span></span><span id="allTo2" class="swap_btn"><span class="filter_btn p_last">&nbsp;</span></span><span id="allTo1" class="swap_btn"><span class="filter_btn p_first">&nbsp;</span></span>
                                                    </div>
                                                    <div class="list_right">
                                                        <div class="list_filter">
                                                            <label>Filter: </label>
                                                            <input type="text" id="box2Filter"/>
                                                            <span class="list_refresh" id="box2Clear"><span class="filter_btn refresh_3">&nbsp;</span></span>
                                                        </div>
                                                        <select id="box2View" multiple="multiple" class="multiple_list">
                                                        </select>
                                                        <div class="list_itemcount">
                                                            <span id="box2Counter" class="countLabel"></span>
                                                            <select id="box2Storage">
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <span class="clear"></span>
                                                </div>
                                            </div>
                                        </li> --%>
                                       <%--  <li>
                                            <div class="form_grid_12">
                                                <label class="field_title">Privileges</label>
                                                <div class="form_input">
                                                    <div class="list_left">
                                                        <div class="list_filter">
                                                            <label>Filter: </label>
                                                            <input type="text" id="pbox1Filter"/>
                                                            <span id="dualBox1Clear" class="list_refresh"><span class="filter_btn refresh_3">&nbsp;</span></span>
                                                        </div>
                                                            <select multiple="multiple" class="multiple_list" id="roles" name="roles" path="roles" >
                                                                <c:forEach var="role" items="${roleCompleteList}">
                                                                    <option id="${role.id}" value="${role.name}"  />
                                                                </c:forEach>
                                                            </select>
                                                        <select id="dualBox1View" multiple="multiple" class="multiple_list" name="roles" path="roles">
                                                            <c:forEach var="role" items="${roleCompleteList}">
                                                                <option id="${role.id}" value="${role.name}">${role.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <div class="list_itemcount">
                                                            <span id="dualBox1Counter" class="countLabel"></span>
                                                            <select id="box1Storage">
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="list_switch">
                                                        <span id="pto2" class="swap_btn"><span class="filter_btn p_next">&nbsp;</span></span><span id="pto1" class="swap_btn"><span class="filter_btn p_prev">&nbsp;</span></span><span id="pallTo2" class="swap_btn"><span class="filter_btn p_last">&nbsp;</span></span><span id="pallTo1" class="swap_btn"><span class="filter_btn p_first">&nbsp;</span></span>
                                                    </div>
                                                    <div class="list_right">
                                                        <div class="list_filter">
                                                            <label>Filter: </label>
                                                            <input type="text" id="pbox2Filter"/>
                                                            <span class="list_refresh" id="dualBox2Clear"><span class="filter_btn refresh_3">&nbsp;</span></span>
                                                        </div>
                                                        <select id="dualBox2View" multiple="multiple" class="multiple_list">
                                                        </select>
                                                        <div class="list_itemcount">
                                                            <span id="dualBox2Counter" class="countLabel"></span>
                                                            <select id="box2Storage">
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <span class="clear"></span>
                                                </div>
                                            </div>
                                        </li> --%>

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