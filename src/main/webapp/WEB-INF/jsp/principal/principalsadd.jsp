<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
//Javascript for select list starts here

var httpRequest;

function getPrivilegeList() 
 { 
  alert("HelloWorld");
  var Stateid=document.principalForm.rolesList.value;
  alert(StateId);
   var url = "principal/getPrivilegeList;

     if (window.ActiveXObject) 
    { 
        httpRequest = new ActiveXObject("Microsoft.XMLHTTP"); 
    } 
    else if (window.XMLHttpRequest) 
    { 
        httpRequest = new XMLHttpRequest(); 
    } 
    
    httpRequest.open("GET", url, true); 
    httpRequest.onreadystatechange = function() {processRequest(); } ; 
    httpRequest.send(null);  

}

 function processRequest() 
{ 
    if (httpRequest.readyState == 4) 
    { 
        if(httpRequest.status == 200)        
        { 

         var privilegeListXml = httpRequest.responseXML.getElementsByTagName("privilegesIdsList")[0]; 
      
            updateHTML(privilegeListXml); 
        } 
        else 
        { 
            alert("Error loading page\n"+ httpRequest.status +":"+ httpRequest.statusText); 
        } 
    } 
}    

function updateHTML(privilegeListXml) 
{ 

   var len = window.document.principalForm.privilegesIdsList.options.length;
	while(len>0) {
	len = len -1;
	window.document.principalForm.privilegesIdsList.options[len] = null;
	}//end while  

	window.document.principalForm.privilegesIdsList.options[0] = new Option("Select","0");

      for (loop = 0; loop < privilegeListXml.childNodes.length; loop++) {

       var privilegeListObj = privilegeListXml.childNodes[loop];
       var privId = privilegeListObj.getElementsByTagName("id")[0];
       var privName= privilegeListObj.getElementsByTagName("name")[0];

       window.document.principalForm.privilegesIdsList.options[loop+1] = new Option(privName.childNodes[0].nodeValue,privId.childNodes[0].nodeValue);
      
     }
    
   return; 
}
//JavaScript code ends here
/* $(document).ready(function(){
	$("#name").click(function(){
	alert("HELLO WORLD!");
	});
	}); */

    /* $('select#rolesList').select(function(event) {
    	alert('mbeena');
        var val = $(this).val();
        var options = '';
        for (var i = 0; i < ${roleCompleteList}.length; i++) {
            for(var j=0;)
                options += '<option value="' + i.privilege.id + '">' + i.privilege.name + '</option>';
        }
        $("select#privileges").html(options);
    })
    .select();
 */
</script>
<script type="text/javascript">
/* $(document).ready(function(){
	 $("#flag").html("Hello World !! (display due to jQuery)");
	}); */
	

</script>
<div id="breadcrumb">
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/app/groupdashboard">Home</a> <span> >> </span>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/app/principals">Users</a> <span> >> </span>
        </li>
        <li>
            <a href="#" style="text-decoration: none;">New User</a>
        </li>
    </ul>
</div>
<div class="content">
    <div class="grid_container">
        <div class="grid_12 full_block">
            <div class="widget_wrap">
                <div class="widget_top">
                    <span class="h_icon list_image"></span>
                    <h6>User</h6>
                </div>
                <form:form modelAttribute="principal" class="form_container left_label"
                           action="new-principal" method="post" name="principalForm">
                    <div class="widget_content">
                        <h6>User Registration</h6>
                        <form:errors path="*" cssClass="errorblock" element="div" />
                        <ul>
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
                                        <form:password class="mid" id="password" name="password" path="password" ></form:password>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="form_grid_12">
                                    <label for="confirmPassword" class="field_title">Confirm Password</label>
                                    <div class="form_input">
                                        <form:password class="mid" id="confirmPassword" name="confirmPassword" path="confirmPassword" ></form:password>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="form_grid_12">
                                    <label for="name" class="field_title">Full Name</label>
                                    <div class="form_input">
                                        <form:input type="text" class="mid" id="name" name="name" path="name" ></form:input>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="form_grid_12">
                                    <label for="email" class="field_title">Email</label>
                                    <div class="form_input">
                                        <form:input type="text" class="mid" id="email" name="email" path="email" ></form:input>
                                    </div>
                                </div>
                            </li>
                            
                            <li>
                                <div class="form_grid_12">
                                    <label for="email" class="field_title">JIRA User Name</label>
                                    <div class="form_input">
                                        <form:input type="text" class="mid" id="jiraUsername" name="jiraUsername" path="jiraUsername" ></form:input>
                                    </div>
                                </div>
                            </li>
                            
                                <%--<li>
                                    <div class="form_grid_12">
                                        <label for="emailNotification" class="field_title">Send Email Notification</label>
                                        <div class="form_input">
                                            <form:input type="checkbox" class="mid" id="email" name="email" ></form:input>
                                        </div>
                                    </div>
                                </li>--%>
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
							
		
                           <%--  <li><div id="flag">
                                <div class="form_grid_12">
                                    <label class="field_title">Roles</label>
                                    <div class="form_input">
                                        <div class="list_left">
                                            <div class="list_filter">
                                                <label>Filter: </label>
                                                    <form:input type="text" id="box1Filter"/>
                                                <span id="box1Clear" class="list_refresh"><span class="filter_btn refresh_3">&nbsp;</span></span>
                                            </div>
                                            <div style="padding-top:13px;"><form:select style="width:300px" multiple="true" id="roles" name="roles" path="roles" >
                                                <c:forEach var="role" items="${referenceData.roleCompleteList}">
                                                    <form:option id="${role.id}" value="${role.name}"  />
                                                </c:forEach>

                                            </form:select></div>
                                            <div class="list_itemcount">
                                                <span id="box1Counter" class="countLabel"></span>
                                                    <form:select id="box1Storage">
                                                    </form:select>
                                            </div>
                                        </div>
                                        <div class="list_switch">
                                            <span id="to2" class="swap_btn"><span class="filter_btn p_next">&nbsp;</span></span><span id="to1" class="swap_btn"><span class="filter_btn p_prev">&nbsp;</span></span><span id="allTo2" class="swap_btn"><span class="filter_btn p_last">&nbsp;</span></span><span id="allTo1" class="swap_btn"><span class="filter_btn p_first">&nbsp;</span></span>
                                        </div>
                                        <div class="list_right">
                                            <div class="list_filter">
                                                <label>Filter: </label>
                                                    <form:input type="text" id="box2Filter"/>
                                                <span class="list_refresh" id="box2Clear"><span class="filter_btn refresh_3">&nbsp;</span></span>
                                            </div>
                                            <form:select id="box2View" multiple="multiple" class="multiple_list">
                                            </form:select>
                                            <div class="list_itemcount">
                                                <span id="box2Counter" class="countLabel"></span>
                                                    <form:select id="box2Storage">
                                                    </form:select>
                                            </div>
                                        </div>
                                        <span class="clear"></span>
                                    </div>
                                </div>
                            </li> --%>
                                <%--<li>
                                    <div class="form_grid_12">
                                        <label class="field_title">Privileges</label>
                                        <div class="form_input">
                                            <div class="list_left">
                                                <div class="list_filter">
                                                    <label>Filter: </label>
                                                    <form:input type="text" id="pbox1Filter"/>
                                                    <span id="dualBox1Clear" class="list_refresh"><span class="filter_btn refresh_3">&nbsp;</span></span>
                                                </div>
                                                    &lt;%&ndash;<select multiple="multiple" class="multiple_list" id="roles" name="roles" path="roles" >
                                                        <c:forEach var="role" items="${roleCompleteList}">
                                                            <option id="${role.id}" value="${role.name}"  />
                                                        </c:forEach>
                                                    </select>&ndash;%&gt;
                                                <form:select id="dualBox1View" multiple="multiple" class="multiple_list" name="roles" path="roles">
                                                    <c:forEach var="role" items="${roleCompleteList}">
                                                        <form:option id="${role.id}" value="${role.name}">${role.name}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                                <div class="list_itemcount">
                                                    <span id="dualBox1Counter" class="countLabel"></span>
                                                    <form:select id="box1Storage">
                                                    </form:select>
                                                </div>
                                            </div>
                                            <div class="list_switch">
                                                <span id="pto2" class="swap_btn"><span class="filter_btn p_next">&nbsp;</span></span><span id="pto1" class="swap_btn"><span class="filter_btn p_prev">&nbsp;</span></span><span id="pallTo2" class="swap_btn"><span class="filter_btn p_last">&nbsp;</span></span><span id="pallTo1" class="swap_btn"><span class="filter_btn p_first">&nbsp;</span></span>
                                            </div>
                                            <div class="list_right">
                                                <div class="list_filter">
                                                    <label>Filter: </label>
                                                    <form:input type="text" id="pbox2Filter"/>
                                                    <span class="list_refresh" id="dualBox2Clear"><span class="filter_btn refresh_3">&nbsp;</span></span>
                                                </div>
                                                <form:select id="dualBox2View" multiple="multiple" class="multiple_list">
                                                </form:select>
                                                <div class="list_itemcount">
                                                    <span id="dualBox2Counter" class="countLabel"></span>
                                                    <form:select id="box2Storage">
                                                    </form:select>
                                                </div>
                                            </div>
                                            <span class="clear"></span>
                                        </div>
                                    </div>
                                </li>--%>

                            <li>
                                <div class="form_grid_12">
                                    <div class="form_input">
                                        <button type="submit" class="btn_small btn_blue"><span id="sub">Register</span></button>
                                        <button type="reset" class="btn_small btn_blue"><span>Reset</span></button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                        </div>
						</form:form>
						
                    </div>
                    </div>
            </div>
        </div> 