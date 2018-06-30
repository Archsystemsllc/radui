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
<title>Privacy, Security, Disclaimer</title>
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
								<div class="header">Privacy Policy</div>	
												
    
<div style="font-size:17px;padding: 10px">The privacy of our customers is important. We understand that visitors need to be in control of their personal information. 
You do not have to give us personal information to visit this site. </div>

<div style="font-size:17px;padding: 10px">This site uses session cookies only. We do not use any persistent cookies.</div>

<div style="font-size:17px;padding: 10px">A cookie is a small piece of information that is sent to your browser -- along with a Web page -- when you access a Web site. There are two kinds of cookies. A session cookie is a line of text that is stored temporarily in your computer's memory. 
Because a session cookie is never written to a drive, it is destroyed as soon as you close your browser. A persistent cookie is a more permanent line of text that gets saved by your browser to a file on your hard drive.</div>
				            
						</div>
					</div>

	<div class="table-users" style="width: 80%">
								<div class="header">System Security</div>	
												
    
<div style="font-size:21px;padding: 10px"><b>This is a U.S. Government Computer System</b></div>

<div style="font-size:17px;padding: 10px">So that this service remains available to you and all other visitors, we monitor network traffic to identify unauthorized attempts to upload or change information or otherwise cause damage to the web service. Use of this system constitutes consent to such monitoring and auditing. Unauthorized attempts to upload information and/or change information on this web site are strictly prohibited and are subject to prosecution under the Computer Fraud and Abuse Act of 1986 and Title 18 U.S.C. Sec.1001 and 1030. </div>

					</div>
						<div class="table-users" style="width: 80%">
								<div class="header">Disclaimer</div>	
												
    
<div style="font-size:17px;padding: 10px">This web site is not hosted on a Centers for Medicare & Medicaid Services (CMS) web server. </div>

					</div>
				</td>
			</tr>
		</form:form>
	</table>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
