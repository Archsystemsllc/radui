<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>ADDA - Upload</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/adda_ico.png" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/table.css" />
<link
	href="${pageContext.request.contextPath}/resources/css/responsive.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/css/prettyPhoto.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/css/animate.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css" />
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
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<style>
.hidden {
	display: none;
}
</style>

</head>
<body>
	<jsp:include page="admin_header.jsp" />
    <div id="mid" class="container about">
   <div> Arch Systems is an established, small wornan-owned business, high growth IT Solutions and Services Company serving many of the major US government agencies. At Arch, our professonals passionately conceive, analyze, develop, and implement optimal solutions for your most challenging business and technology needs.
 </div><br/>
<div>Arch has served the Federal government in over 18 projects providing support in all phases of the Systems Development Lifecycle. Arch has a commitment towards quality and cost-effective solutions and services with special interests in innovative technologies. This commitment and innovative approach is a culture that is bred in the organization and our hard working staff pride in delivering such quality, innovations and methodologies to our customers
    </div>
    </div>

<jsp:include page="footer.jsp"></jsp:include>
	<script type="text/javascript">
	var h;
	h=screen.height-357;
	document.getElementById('mid').style.minHeight=h+'px';
		
	$("input[type='reset']").on("click", function(event){
		event.preventDefault();
	     // stops the form from resetting after this function
	     
	     $(this).parent().parent().parent().find("input[type='file']").val("");
	     
	});
	</script>
</body>
</html>
