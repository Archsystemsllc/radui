<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Hypothesis</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/adda_ico.png">
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<%-- <link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet"> --%>
<link href="${pageContext.request.contextPath}/resources/css/table.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/button.css"
	rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

</head>

<body id="b">

	<%-- 	 <c:if test="${sessionScope.user.name == 'Administrator'}">
		<jsp:include page="admin_header.jsp"></jsp:include>
	</c:if>
	<c:if test="${sessionScope.user.name != 'Administrator'}">
		<jsp:include page="header.jsp"></jsp:include>
	</c:if> --%>


	<sec:authorize
		access="hasAuthority('Administrator')">
		<jsp:include page="admin_header.jsp"></jsp:include>
	</sec:authorize>


	<table id="mid">

		<tr>
			<td width="30%"
				style="background-color: #327a89; vertical-align: top; padding: 0px 25px">
				<div style="color: #fff">
					<!-- <ul style="border-bottom: solid #fff 2px" type="square">
						<li><h2
								style="color: #fff; font-family: 'Rubik', sans-serif;">Description</h2></li>
					</ul> -->
					<p style="text-align: justify; font-family: 'Rubik', sans-serif;">
						<br> <br>From the Base Year to Option Year 3 Rural Area
						Percentage line plot, we would like to see the change trend of the
						rural area percentage of all combined EPs and GPROs and the
						difference among reporting options (Claims, Registry, EHR, QCDR
						and GPROWI)
					</p>
				</div>
			</td>
			<td style="padding: 10px 105px; vertical-align: top">
				<div class="DataAnalysisScreen">

					<script type="text/javascript">console.log("justprint")
					</script>
					<div class="table-users">
						<div class="header">Data Analysis</div>

						<table id="dataAnalysisTableId" class="display">
							<thead style="font-weight: bold">
								<tr>
									<th align="center">Data Analysis Name</th>
									<th align="center">Action</th>
								</tr>
							</thead>

							<tbody>

								<c:forEach items="${dataAnalysisList}" var="dataAnalysis">
									<%-- <tr>
										<td style="border-top:1px solid #327A81"><a title="${dataAnalysis.dataAnalysisDescription}"
											href="${pageContext.request.contextPath}/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/0">${dataAnalysis.dataAnalysisName}</a>
										</td>

										<td style="text-align: center;border-top:1px solid #327A81;"><a
											href="${pageContext.request.contextPath}/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/0"><button
													title="Select View to see the results of the Analysis"
													class="button search" id="view" value="View">View</button></a>
											<button
												title="Select to Download the report for the Hypothesis selected"
												class="button arrow" id="download" value="Download">Download</button>
										</td>

									</tr> --%>
									<c:choose>
										<c:when test="${dataAnalysis.id =='2'}">
											<tr>
												<td style="border-top: 1px solid #327A81;font-weight:bold;font-size:1.05em;"><a
													title="${dataAnalysis.dataAnalysisDescription}"
													href="${pageContext.request.contextPath}/exclusion/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/5">${dataAnalysis.dataAnalysisName}</a>
												</td>

												<td
													style="text-align: center; border-top: 1px solid #327A81;"><a
													href="${pageContext.request.contextPath}/exclusion/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/5"><button
															title="Select View to see the results of the Analysis"
															class="button search" id="view" value="View">View</button></a>
													<!-- <button
														title="Select to Download the report for the Hypothesis selected"
														class="button arrow" id="download" value="Download">Download</button> -->
												</td>

											</tr>
										</c:when>
										<c:when test="${dataAnalysis.id =='4' || dataAnalysis.id =='5'}">
											<tr>
												<td style="border-top: 1px solid #327A81;font-weight:bold;font-size:1.05em;"><a
													title="${dataAnalysis.dataAnalysisDescription}"
													href="${pageContext.request.contextPath}/measures/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/${dataAnalysis.subDataAnalysis[0].id}">${dataAnalysis.dataAnalysisName}</a>
												</td>

												<td
													style="text-align: center; border-top: 1px solid #327A81;"><a
													href="${pageContext.request.contextPath}/measures/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/${dataAnalysis.subDataAnalysis[0].id}"><button
															title="Select View to see the results of the Analysis"
															class="button search" id="view" value="View">View</button></a>
													<!-- <button
														title="Select to Download the report for the Hypothesis selected"
														class="button arrow" id="download" value="Download">Download</button> -->
												</td>

											</tr>
										</c:when>
										<c:otherwise>
											<tr>
												<td style="border-top: 1px solid #327A81;font-weight:bold;font-size:1.05em">${dataAnalysis.dataAnalysisName}<%-- <a
													title="${dataAnalysis.dataAnalysisDescription}"
													href="${pageContext.request.contextPath}/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/0">${dataAnalysis.dataAnalysisName}</a> --%>
												</td>

												<td
													style="text-align: center; border-top: 1px solid #327A81;"><%-- <a
													href="${pageContext.request.contextPath}/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/0"><button
															title="Select View to see the results of the Analysis"
															class="button search" id="view" value="View">View</button></a>
													<button
														title="Select to Download the report for the Hypothesis selected"
														class="button arrow" id="download" value="Download">Download</button> --%>
												</td>

											</tr>
										</c:otherwise>
									</c:choose>
									<c:forEach items="${dataAnalysis.subDataAnalysis}"
										var="subDataAnalysis">

										<c:if
											test="${subDataAnalysis.subDataAnalysisName ne 'Not Applicable'}">
											<%-- <c:choose>
												<c:when test="${dataAnalysis.id =='3'}">
													<tr>
														<td style="border: 0px">
															<ul>
																<li><a
																	title="${subDataAnalysis.subDataAnalysisDescription}"
																	href="${pageContext.request.contextPath}/measures/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/${subDataAnalysis.id}">${subDataAnalysis.subDataAnalysisName}</a>
																</li>
															</ul>
														</td>

														<td style="text-align: center; border: 0px;"><a
															href="${pageContext.request.contextPath}/measures/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/${subDataAnalysis.id}"><button
																	title="Select view to see the results of the Analysis"
																	class="button search" id="view" value="View">View</button></a>
															<button
																title="Select to download the report for the Hypothesis selected"
																class="button arrow" id="download" value="Download">Download</button>
														</td>
													</tr>
												</c:when>
												<c:otherwise>
													<tr>
														<td style="border: 0px;">
															<ul>
																<li><a
																	title="${subDataAnalysis.subDataAnalysisDescription}"
																	href="${pageContext.request.contextPath}/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/${subDataAnalysis.id}">${subDataAnalysis.subDataAnalysisName}</a>
																</li>
															</ul>
														</td>

														<td style="text-align: center; border: 0px"><a
															href="${pageContext.request.contextPath}/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/${subDataAnalysis.id}"><button
																	title="Select view to see the results of the Analysis"
																	class="button search" id="view" value="View">View</button></a>
															<button
																title="Select to download the report for the Hypothesis selected"
																class="button arrow" id="download" value="Download">Download</button>
														</td>
													</tr>
												</c:otherwise>
											</c:choose> --%>
											<c:choose>
												<c:when test="${dataAnalysis.id =='2'}">
													<tr>
														<td style="border: 0px">
															<ul>
																<li><a
																	title="${subDataAnalysis.subDataAnalysisDescription}"
																	href="${pageContext.request.contextPath}/exclusion/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/${subDataAnalysis.id}">${subDataAnalysis.subDataAnalysisName}</a>
																</li>
															</ul>
														</td>

														<td style="text-align: center; border: 0px"><a
															href="${pageContext.request.contextPath}/exclusion/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/${subDataAnalysis.id}"><button
																	title="Select view to see the results of the Analysis"
																	class="button search" id="view" value="View">View</button></a>
															<!-- <button
																title="Select to download the report for the Hypothesis selected"
																class="button arrow" id="download" value="Download">Download</button> -->
														</td>
													</tr>
												</c:when>
												<c:when test="${dataAnalysis.id =='3'}">
													<tr>
														<td style="border: 0px">
															<ul>
																<li><a
																	title="${subDataAnalysis.subDataAnalysisDescription}"
																	href="${pageContext.request.contextPath}/measures/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/${subDataAnalysis.id}">${subDataAnalysis.subDataAnalysisName}</a>
																</li>
															</ul>
														</td>

														<td style="text-align: center; border: 0px"><a
															href="${pageContext.request.contextPath}/measures/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/${subDataAnalysis.id}"><button
																	title="Select view to see the results of the Analysis"
																	class="button search" id="view" value="View">View</button></a>
															<!-- <button
																title="Select to download the report for the Hypothesis selected"
																class="button arrow" id="download" value="Download">Download</button> -->
														</td>
													</tr>
												</c:when>
												<c:otherwise>
													<tr>
														<td style="border: 0px">
															<ul>
																<li><a
																	title="${subDataAnalysis.subDataAnalysisDescription}"
																	href="${pageContext.request.contextPath}/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/${subDataAnalysis.id}">${subDataAnalysis.subDataAnalysisName}</a>
																</li>
															</ul>
														</td>

														<td style="text-align: center; border: 0px"><a
															href="${pageContext.request.contextPath}/mapandchartdisplay/dataAnalysisId/${dataAnalysis.id}/subDataAnalysisId/${subDataAnalysis.id}"><button
																	title="Select view to see the results of the Analysis"
																	class="button search" id="view" value="View">View</button></a>
															<!-- <button
																title="Select to download the report for the Hypothesis selected"
																class="button arrow" id="download" value="Download">Download</button> -->
														</td>
													</tr>
												</c:otherwise>
											</c:choose>
										</c:if>
									</c:forEach>

								</c:forEach>

							</tbody>

						</table>

						<!-- 	
<script type="text/javascript">
	$(document).ready(function() {
	    $('#dataAnalysisTableId').DataTable();
	} );
</script>

 -->
						<script type="text/javascript">
	 var h;
		h=screen.height-357;
		document.getElementById('mid').style.minHeight=h+'px';
	 $(document).ready(function () {
		 $('.nav > li').eq(0).addClass('active');	 
	 });

	 </script>
					</div>
				</div>
			</td>
		</tr>
	</table>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
