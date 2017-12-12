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
	href="${pageContext.request.contextPath}/resources/css/table.css" />
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
			<td style="vertical-align: top; text-align: center;">
				<!-- <div class="container" style="min-height: 600px"> -->
				<div id="updates">
					<div class='table-users' style="width: 85%">
						<div class="header" style="font-size: 30px">Upload excel
							data</div>
						<div class="content">
							<form:form
								action="${pageContext.request.contextPath}/admin/documentupload/"
								modelAttribute="documentFileUpload"
								enctype="multipart/form-data" method="post">
								<c:if test="${not empty documentuploadsuccess}">
									<br />
									<div class="successblock">
										<spring:message code="${documentuploadsuccess}"></spring:message>
									</div>
								</c:if>
								<c:if test="${not empty documentuploaderror}">
									<br />
									<div class="successblock">
										<spring:message code="${documentuploaderror}"></spring:message>
									</div>
								</c:if>
								<form:errors path="*" cssClass="errorblock" element="div" />
								<table>
									<colgroup>
										<col width="30%"></col>
										<col width="40%"></col>
										<col width="30%"></col>
									</colgroup>
									<tr>
										<td colspan="3">
											<form:select path="providerHypId"
												id="ddl1"
												onchange="configureDropDownLists(this,document.getElementById('ddl2'))">
												<option value="0">Select</option>
												<c:forEach var="category" items="${dataAnalysisCategories}">
													<option value="${category.id}">${category.dataAnalysisName}</option>
												</c:forEach>
											</form:select> 
											<form:select path="providerSubHypId" id="ddl2"
												class="hidden">
												<option value="0">NA</option>
												<!--<c:forEach var="subCategory"
							items="${subDataAnalysisCategories}">
							<option value="${subCategory.id}">${subCategory.subDataAnalysisName}</option>
						</c:forEach>-->
											</form:select></td>
									</tr>


									<tr id="provider-row" class="hidden">
										<td style="text-align: right;">
											<p>Provider Data:</p>
										</td>
										<td>
											<p>
												<form:input id="provider-input" type="file" path="provider" size="40" />
											</p>
										</td>
										<td>
											<div class="btn-group btn-xs">
												<input class="btn btn-primary" type="submit" value="Upload"
													id="provider-upload" /> <input class="btn btn-info"
													type="reset" value="Reset" id="provider-reset" />
											</div>
										</td>
									</tr>
									<tr id="specialty-row" class="hidden">
										<td style="text-align: right;">
											<p>Specialty Data:</p>
										</td>
										<td>
											<p>
												<form:input id="specialty-input" type="file" path="specialty" size="40" />
											</p>
										</td>
										<td>
											<div class="btn-group btn-xs">
												<input class="btn btn-primary" type="submit" value="Upload" id="specialty-upload"/>
												<input class="btn btn-info" type="reset" value="Reset" id="specialty-reset"/>
											</div>
										</td>
									</tr>
									<tr id="statewise-row" class="hidden">
										<td style="text-align: right;">
											<p>State Statistics Data:</p>
										</td>
										<td>
											<p>
												<form:input id="statewise-input" type="file" path="statewise" size="40" />
											</p>
										</td>
										<td>
											<div class="btn-group btn-xs">
												<input class="btn btn-primary" type="submit" value="Upload" id="statewise-upload" />
												<input class="btn btn-info" type="reset" value="Reset" id="statewise-reset"/>
											</div>
										</td>
									</tr>
									
									<%-- TODO: ADDED a Exclusion_ Trends --%>
									
									<tr id="exclusionTrends-row" class="hidden">
										<td style="text-align: right;">
											<p>Exclusion Trends Data:</p>
										</td>
										<td>
											<p>
												<form:input id="exclusionTrends-input" type="file" path="exclusionTrends" size="40" />
											</p>
										</td>
										<td>
											<div class="btn-group btn-xs">
												<input class="btn btn-primary" type="submit" value="Upload" id="exclusionTrends-upload" />
												<input class="btn btn-info" type="reset" value="Reset" id="exclusionTrends-reset"/>
											</div>
										</td>
									</tr>
																		
									<tr id="measureWisePerformanceAndReporting-row" class="hidden">
										<td style="text-align: right;">
											<p>Measure Wise Performance and Reporting Rate Data :</p>
										</td>
										<td>
											<p>
												<form:input id="measureWisePerformanceAndReporting-input" type="file" path="measureWisePerformanceAndReportingRate" size="40" />
											</p>
										</td>
										<td>
											<div class="btn-group btn-xs">
												<input class="btn btn-primary" type="submit" value="Upload" id="measureWisePerformanceAndReporting-upload" />
												<input class="btn btn-info" type="reset" value="Reset" id="measureWisePerformanceAndReporting-reset" />
											</div>
										</td>
									</tr>
									<tr id="measureWiseExclusionRate-row" class="hidden">
										<td style="text-align: right;">
											<p>Measure Exclusion Data:</p>
										</td>
										<td>
											<p>
												<form:input id="measureWiseExclusionRate-input" type="file" path="measureWiseExclusionRate" size="40" />
											</p>
										</td>
										<td>
											<div class="btn-group btn-xs">
												<input class="btn btn-primary" type="submit" value="Upload" id="measureWiseExclusionRate-upload" />
												<input class="btn btn-info" type="reset" value="Reset" id="measureWiseExclusionRate-reset" />
											</div>
										</td>
									</tr>
								</table>

							</form:form>
						</div>
					</div>
				</div> <!-- </div> -->

			</td>
		</tr>

	</table>
	<jsp:include page="footer.jsp" />
	<script type="text/javascript">
	var h;
	h=screen.height-357;
	/* alert(document.getElementById('mid').style.height); */
	document.getElementById('mid').style.minHeight=h+'px';
	</script>
	<script type="text/javascript">
    var data = [];
    
	function configureDropDownLists(ddl, ddl2) {
		var ele = document.getElementById("ddl1");
		var seleHypoId = ele.options[ele.selectedIndex].value;
		var subData;
		var subDataArray = [];
		$('#ddl2').empty();
		
		if (seleHypoId === "0") {
			$('#ddl2').addClass('hidden');
			$('#provider-row').addClass('hidden');
			$('#specialty-row').addClass('hidden');
			$('#statewise-row').addClass('hidden');
			
			<%-- TODO:Need to Add exclusionTrends-row and measureWisePerformanceAndReporting-row or NOT?   --%>
			
			$('#exclusionTrends-row').addClass('hidden');
			$('#measureWisePerformanceAndReporting-row').addClass('hidden');     
			$('#measureWiseExclusionRate-row').addClass('hidden');
		} else {	
			
				/*for(i = 0; i < data.length; i++){
					if(data[i].id == seleHypoId && data[i].subDataAnalysis.length > 0 ){
						
						for(j = 0; j < data[i].subDataAnalysis.length; j++){							
							subData = new Object();
							subData.id = data[i].subDataAnalysis[j].id;
							subData.name = data[i].subDataAnalysis[j].subDataAnalysisName;							
							subDataArray.push(subData);							
						}						
					}
				}
				if (subDataArray.length == 0) {
					$('#ddl2').addClass('hidden');
				} else {*/
					$('#ddl2').removeClass('hidden');
					/*for (i = 0; i < subDataArray.length; i++) {						
						createOption(ddl2, subDataArray[i].name, subDataArray[i].id);
					}
				}*/
				
				switch (seleHypoId) {
				case '1':
					createOption(ddl2, 'Summary by Reporting Option - Bar Chart', 1);
					createOption(ddl2, 'Summary by Reporting Option - Line Chart', 2);
					createOption(ddl2, 'Map', 3);
					createOption(ddl2, 'Not Applicable', 4);
					break;
				case '2':
					createOption(ddl2, 'Not Applicable', 5);
					break;
				case '3':
					createOption(ddl2, 'High Exclusion - Expected', 6);
					createOption(ddl2, 'High Exclusion - Not Expected', 7);
					createOption(ddl2, 'Frequencies', 8);
					break;
				case '4':
					createOption(ddl2, 'Not Applicable', 9);
					break;
				case '5':
					createOption(ddl2, 'Not Applicable', 10);
					break;
			
			}
				
				if (seleHypoId === "1") {
					$('#provider-row').removeClass('hidden');
					$('#specialty-row').addClass('hidden');
					$('#statewise-row').removeClass('hidden');
					$('#exclusionTrends-row').addClass('hidden');
					$('#measureWisePerformanceAndReporting-row').addClass('hidden');
					$('#measureWiseExclusionRate-row').addClass('hidden');
				} else if(seleHypoId === "2" ) {
					$('#provider-row').addClass('hidden');
					$('#specialty-row').addClass('hidden');
					$('#statewise-row').addClass('hidden');
					$('#exclusionTrends-row').removeClass('hidden');
					$('#measureWisePerformanceAndReporting-row').addClass('hidden');
					$('#measureWiseExclusionRate-row').addClass('hidden');
				} else if(seleHypoId === "3" ) {
					$('#provider-row').addClass('hidden');
					$('#specialty-row').addClass('hidden');
					$('#statewise-row').addClass('hidden');
					$('#exclusionTrends-row').addClass('hidden');
					$('#measureWisePerformanceAndReporting-row').addClass('hidden');
					$('#measureWiseExclusionRate-row').removeClass('hidden');
			    }else if(seleHypoId === "4" ) {
					$('#provider-row').addClass('hidden');
					$('#specialty-row').addClass('hidden');
					$('#statewise-row').addClass('hidden');
					$('#exclusionTrends-row').addClass('hidden');
					$('#measureWisePerformanceAndReporting-row').removeClass('hidden');
					$('#measureWiseExclusionRate-row').addClass('hidden');
					$('#ddl2').addClass('hidden');
			    } else if(seleHypoId === "5" ) {
			    	$('#provider-row').addClass('hidden');
					$('#specialty-row').addClass('hidden');
					$('#statewise-row').addClass('hidden');
					$('#exclusionTrends-row').addClass('hidden');
					$('#measureWisePerformanceAndReporting-row').removeClass('hidden');
					$('#measureWiseExclusionRate-row').addClass('hidden');
					$('#ddl2').addClass('hidden');
		        } 
					
		}

	}

	function createOption(ddl, text, value) {
		var opt = document.createElement('option');
		opt.value = value;
		opt.text = text;
		ddl.options.add(opt);
	}
	
	/*function subDataAnalysis() {
		$.ajax({
			//url : "http://ec2-34-208-54-139.us-west-2.compute.amazonaws.com/imapservices/api/dataanalysis/",			
			url : "http://localhost:8080/imapservices/api/dataanalysis/",
			type : 'GET',
			dataType : 'json',
			success : function(hypData) {					
				data = hypData;				
			},
			error : function(request, error) {
				//alert("Request: " + JSON.stringify(request));
				alert("Request: " + failed);
			}
		});
	}*/

	</script>
	<script type="text/javascript">
	$(document).ready(function () {
				 $('.nav > li').eq(1).addClass('active');	
				 //subDataAnalysis();
	});	
	$("input[type='reset']").on("click", function(event){
		event.preventDefault();
	     // stops the form from resetting after this function
	     
	     $(this).parent().parent().parent().find("input[type='file']").val("");
	     
	});
	</script>
</body>
</html>
