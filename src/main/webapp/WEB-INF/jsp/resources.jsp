<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Resources</title>
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

<script type="text/javascript">


</script>
<script type="text/javascript">
	
</script>

</head>
<body>
	<jsp:include page="admin_header.jsp"></jsp:include>
	<div role="main">
	
	
	<table id="mid">
		<form:form method="POST" modelAttribute="csrUploadForm" class="form-signin" action="#" enctype="multipart/form-data" id="csrupload">
			<tr>
				
				<td style="vertical-align: top">

					<div id="updates" class="boxed">

						<div class="content">
						
						<div class="table-users" style="width: 80%">
								<div class="header">Resources</div>	
												
						<ul style="padding-left:480px;padding-top:10px;font-size:18px;">
						<li style="padding-top:10px;"><a href="${pageContext.request.contextPath}/resources/documents/COMRAD_Help_ Guide_ Final_012019.pdf" title="Help Guide Document" target="_blank">Help Guide</a> </li>
						
						<li style="padding-top:20px;"><a href="${pageContext.request.contextPath}/resources/documents/CRAD_QAM_MAC_Handbook_012019.pdf" title="QAM Handbook Document" target="_blank">QAM Handbook</a></li>
						</ul>
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
