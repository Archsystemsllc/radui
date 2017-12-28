<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>QAM - ScoreCard</title>
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
			<tr>
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">

							<div class="table-users" style="width: 80%">
								<div class="header">ScoreCard</div>							
      
                                 <form action="scorecardsave" method="post" enctype="multipart/form-data">
                                 <!--  Section 1 -->
								<table style="border-collapse: separate; border-spacing: 2px;" id='table1'>
									<tr><td colspan="3" width="95%" >
					<div class="form_grid_12">
					<display:table class="display hover stripe cell-border " id="row" name="${sessionScope.SCORECARDS_MAP.values()}" 
							requestURI="" keepStatus="true" clearStatus="${storepage == 'clear'}" style="width:95%;font-size:85%;" export="true" pagesize="15" sort="list">
							
							<display:column property="csrFullName" title="CSR Full Name" sortable="true" style="text-align:center;"/>
							<display:column property="callCategoryId" title="Call Category Id" sortable="true" style="align:center"/>
							<display:column property="macId" title="MAC" sortable="true" style="align:center"/>
							<display:column property="jurId" title="Jurisdiction" sortable="true" style="align:center"/>
							<display:column property="programId" title="Program" sortable="true" style="align:center"/>
							<display:column title="Actions" style="text-align:center;">
								<span><a class="action-icons c-edit" href="${pageContext.request.contextPath}/admin/edit-scorecard/${row.id}" title="Edit">Edit</a></span>
								<span><a class="action-icons c-approve" href="${pageContext.request.contextPath}/admin/new-scorecard" title="Create">Create</a></span>								
							</display:column>
						</display:table>	
						</div>
						</td>
						</tr>
						</table>
						</form>
						</div>
						</div>
						</div></td></tr></table>		
		

	<jsp:include page="footer.jsp"></jsp:include>
	
</body>
</html>
