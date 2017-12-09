<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><c:choose>
		<c:when test="${subDataAnalysis.id=='9'}">Hypothesis 4</c:when>
		<c:when test="${subDataAnalysis.id=='10'}">Hypothesis 6</c:when>
		<c:otherwise>${subDataAnalysis.subDataAnalysisName}</c:otherwise>
	</c:choose></title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/Chart.bundle.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/utils.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<%-- <link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet"> --%>
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/adda_ico.png">
<link href="${pageContext.request.contextPath}/resources/css/main.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/responsive.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/prettyPhoto.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css"
	rel="stylesheet">

<link
	href="${pageContext.request.contextPath}/resources/css/animate.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/table.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://unpkg.com/leaflet@1.0.3/dist/leaflet.css"
	integrity="sha512-07I2e+7D8p6he1SIM+1twR5TIrhUQn9+I6yjqD53JQjFiMf8EtC93ty0/5vJTZGF8aAocvHYNEDJajGdNx1IsQ=="
	crossorigin="" />


<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script src="https://unpkg.com/leaflet@1.0.3/dist/leaflet.js"
	integrity="sha512-A7vV8IFfih/D732iSSKi20u/ooOfj/AGehOKq0f4vLT1Zr2Y+RX7C+w8A1gaSasGtRUZpF/NZgzSAu4/Gc41Lg=="
	crossorigin=""></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.3/jspdf.min.js"></script>



<!-- <style>
#map {
	width: 600px;
	height: 400px;
}
</style> -->

<style>
table td {
	border: 0px;
}

#map {
	width: 900px;
	height: 400px;
}

.info {
	padding: 6px 8px;
	font: 14px/16px Arial, Helvetica, sans-serif;
	background: white;
	background: rgba(255, 255, 255, 0.8);
	box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
	border-radius: 5px;
}

.info h4 {
	margin: 0 0 5px;
	color: #777;
}

.legend {
	text-align: left;
	line-height: 18px;
	color: #555;
}

.legend i {
	width: 18px;
	height: 18px;
	float: left;
	margin-right: 8px;
	opacity: 0.7;
}

select {
	width: 100%;
}

table {
	border-collapse: separate;
	border-spacing: 10px;
}

table td:last-child {
	text-align: center;
	width: 40%;
}

table td:first-child {
	width: 30%;
	text-align: right;
}

table th:last-child {
	text-align: center;
	width: 40%;
}

table th:first-child {
	width: 30%;
}
/* table td:first-child {
	text-align: right;
} */
#loading-gif {
	margin: auto;
}

#loading-gif img {
	height: 90px;
	width: 90px;
}

#NoData {
	background-color: #FFD42A;
	width: 40%;
	color: #000;
	font-weight: bold;
	text-align: center;
	margin: auto;
	text-align: center;
	font-weight: bold;
}
ul.ui-autocomplete { 
	max-height: 180px !important; 
	max-width: 338px !important;
	overflow: auto !important; 
	
}
...
/* #mapIframe{
background: url("${pageContext.request.contextPath}/resources/images/loading3.gif")
} */
</style>
</head>

<body>

<%-- 	<jsp:include page="admin_header.jsp"></jsp:include> --%>
<!-- Farheen : 08/29/2017 Based on the Role header is assigned to the user -->
<sec:authorize
		access="hasAuthority('Administrator')">
		<jsp:include page="admin_header.jsp"></jsp:include>
	</sec:authorize>
		<sec:authorize
		access="hasAuthority('Report Viewer')">
	<jsp:include page="header.jsp"></jsp:include>
	</sec:authorize>

	<table id="mid">
		<tr>
			<td
				style="background-color: #327a89; vertical-align: top; padding: 0px 25px; width: 15%">
				<div style="color: #fff">
					<!-- <ul style="border-bottom: solid #fff 2px" type="square">
						<li><h2 style="color: #fff;">Description</h2></li>
					</ul> -->
					<p style="text-align: justify;">
						<br> <br>From the Base Year to Option Year 3 Rural Area
						Percentage line plot, we would like to see the change trend of the
						rural area percentage of all combined EPs and GPROs and the
						difference among reporting options (Claims, Registry, EHR, QCDR
						and GPROWI)
					</p>
				</div>
				<div id="onScreenHelpLabelId" style="color: #fff">
					<!--  <br>On Screen User Help:-->
				</div>
				<div style="color: #fff">
					<p style="text-align: justify;">${subDataAnalysis.onScreenHelpText}</p>
				</div>
			</td>
			<td style="vertical-align: top;">
				<h2 style="text-align: center; font-size: 30px;">
					<c:choose>
						<c:when test="${subDataAnalysis.id=='9'}">Hypothesis 4</c:when>
						<c:when test="${subDataAnalysis.id=='10'}">Hypothesis 6</c:when>
						<c:otherwise>${subDataAnalysis.subDataAnalysisName}</c:otherwise>
					</c:choose>
				</h2>
				<div class="HypothesisScreen" style="padding: 20px 25%;">
					<table
						style="border-collapse: separate; border-spacing: 2px; width: 100%">
						<colgroup>
							<col width="38%"></col>
							<col width="62%"></col>
						</colgroup>
						<tr id="excluFreqRow">
							<td><label for="excluFreqRowId">Exc/Fre Option :</label></td>
							<td><c:if
									test="${subDataAnalysisId == '7' || subDataAnalysisId == '6'}">
									<select id="excluFreqRowId" name="excluFreqRowId"
										title="Click here to choose Exc/Fre">

										<option value="1" selected>Exclusion Rate</option>
										<option value="2" disabled>Frequency</option>

									</select>
								</c:if>
								<c:if test="${subDataAnalysisId == '8'}">
									<select id="excluFreqRowId" name="excluFreqRowId"
										title="Click here to choose Exc/Fre">

										<option value="1" disabled>Exclusion Rate</option>
										<option value="2" selected>Frequency</option>

									</select>
								</c:if> 
								<c:if test="${subDataAnalysisId == '9' || subDataAnalysisId == '10'}">
									<select id="excluFreqRowId" name="excluFreqRowId"
										title="Click here to choose Exc/Fre">
										<option value="">Select</option>
										<option value="1" >Exclusion Rate</option>
										<option value="2" >Frequency</option>

									</select>
								</c:if> </td>
						</tr>
						<tr>
							<td><label for="yearLookUpId">Option Year : </label></td>
							<td><select id="yearLookUpId" name="yearLookUpId"
								title="Select one of the option years or ALL where available">
									<%-- <option value="">Select</option>
									<c:forEach items="${yearLookups}" var="yearLookUp">
										<option value="${yearLookUp.id}"
											${yearLookUp.id == yearLookUpId ? 'selected="selected"' : ''}>${yearLookUp.yearName}</option>
									</c:forEach> --%>
									<option value="5">ALL</option>
							</select></td>
						</tr>
						<tr>

							<td><label for="automplete-1">Search Measure ID/Name: </label>
							<p style="font-size: 13px; font-weight: normal; width: 208px">(Click Measures from drop-down list to add up to 4 Measures)</p>
									
								<p style="color: red" id="errormeasuretxt"></p></td>
							<td>
								<div class="ui-widget">
									<label for="id_label_multiple" style="width: 100%"> <input
										type="text" size="50" id="automplete-1">
										<!--  <input type="button" id="searchMeasure" value="Search">-->
										<p style="font-size: 13px; font-weight: normal; font-type: italic;">(You can enter either Measure ID  Number or Keyword to search Measures)</p>
									</label>
								</div>
							</td>

						</tr>
						<!-- <tr>
							<div class = "ui-widget">
					         <p>Type "a" or "s"</p>
					         <label for = "automplete-1">Tags: </label>
					         <input id = "automplete-1">
					         </div>
				         </tr> -->
						<!--<tr>
							<td><label for="measureLookupId">Search Results :</label>
								<p style="font-size: 13px; font-weight: normal;">(Double Click to add)</p></td>
							<td><label for="id_label_multiple" style="width: 100%">
									<select id="searchMeasureLookupId" name="searchMeasureLookupId"
									multiple="multiple" title="Search Results for measures">
								</select>
							</label></td>
						</tr>  -->
						<tr>
							<td><label for="measureLookupId">Measure(s) Name :</label>
								<p style="font-size: 13px; font-weight: normal;">(To remove
									double click on measure)</p>
								<p style="color: red" id="errmeasureName"></p></td>
							<td><label for="id_label_multiple" style="width: 100%">
									<select id="measureLookupId" name="measureLookupId"
									multiple="multiple">
										<%--<option disabled>Add up to 4 more measures </option>
										 <c:forEach items="${measureLookups}" var="measureLookup">

											<option value="${measureLookup.id}"
												${measureLookup.id == measureLookupId ? 'selected="selected"' : ''}>${measureLookup.measureId} - ${measureLookup.measureName}</option>
										</c:forEach> --%>
								</select>
							</label></td>
						</tr>

						<c:if test="${dataAnalysisId == '4' || dataAnalysisId == '5'}">
							<tr>
								<td><label for="reportingOptionLookupId">Reporting
										Option : </label></td>
								<td><select id="reportingOptionLookupId"
									name="reportingOptionLookupId"
									title="Select one of the reporting options">
										<option value="">Select</option>
										<c:forEach items="${reportingOptionLookups}"
											var="reportingOptionLookup">
											<option value="${reportingOptionLookup.id}"
												${reportingOptionLookup.id == reportingOptionLookupId ? 'selected="selected"' : ''}>${reportingOptionLookup.reportingOptionName}</option>
										</c:forEach>
								</select></td>
							</tr>
						</c:if>
						<tr>
							<td><label for="reportTypeId">Report Type :</label></td>
							<td><select id="reportTypeId" name="reportTypeId"
								title="Select one of the Reporting Types, only one reporting type may be displayed at one time">
									<%-- <option value="">Select</option>
									<c:forEach items="${reportTypes}" var="reportType">
										<option value="${reportType}">${reportType}</option>
									</c:forEach> --%>
									<option value="Line Chart">Line Chart</option>
							</select></td>
						</tr>
						<tr id="yesOrNoOptionRow" hidden="true">
							<td><label for="yesOrNoOptionId">Yes/No Option :</label></td>
							<td><select id="yesOrNoOptionId" name="yesOrNoOptionId"
								title="Click here to choose YES/NO">
									<option value="">Select</option>
									<option value="0">No</option>
									<option value="1">Yes</option>
							</select></td>
						</tr>
						<tr>
							<td colspan="2" style="padding-top: 10px"><input
								title="Click the button to submit the selections above and view the results"
								class="btn btn-primary btn-sm"
								style="display: block; margin: auto; width: 30%;" type="submit"
								id="displayreport" value="Submit" /></td>
						</tr>
			
					</table>
	<div>
				<button title="Click the button to Export the chart as PDF"
								class="btn btn-primary btn-sm"
								style="display: none; margin: auto; float: right; width: 30%;"
								id="downloadPDF" >Export as PDF</button>
							</div>
				</div>
				<div class="HypothesisScreen" style="max-height: 600px">
					<iframe id='mapIframe' hidden="true" frameborder="0" scrolling="no"
						style="overflow: hidden; width: 100%; height: 550px; margin:auto"></iframe>
					<div id="messageDisplay"></div>

					<div id="chart-container" style="width: 75%; margin: auto">
						<div id="loading-gif" hidden="true">
							<img
								src="${pageContext.request.contextPath}/resources/images/loading3.gif" />
						</div>
						<canvas id="chart-canvas"></canvas>
							
					</div>
	

				</div>
							<p id="noteMeasure" style="font-size: 13px; font-weight: normal; display: none">Note: Click on Measure to remove it from the Line Chart </p>
				
	
				<div id="summary" style="display: initial;"></div>

			</td>
		</tr>
	</table>
	<script type="text/javascript">
	var h;
	h=screen.height-357;
	document.getElementById('mid').style.minHeight=h+'px';
/* 	$('mapIframe').ready(function () {
	    $('#loadinggif').css('display', 'none');
	});
	$('mapIframe').load(function () {
	    $('#loadinggif').css('display', 'none');
	}); */
		var btn = document.getElementById("displayreport");
		var barChartData = null;
		var lineChartData = null;
		var measureParameters = '';	
	//validations	
		var measures = document.getElementById('measureLookupId');
	    var valdmeasureLookup=false;
	var serverContextPath = '/imapservices';
		var measuresData; 
	   	function validations()
	{
			if( measures.options.length === 0 ) {
				    document.getElementById("errmeasureName").innerHTML = "Required *";   
				 }else{
			    document.getElementById("errmeasureName").innerHTML ="";  
			    valdmeasureLookup=true;

				 }
		
			return valdmeasureLookup;
	}
	   btn.addEventListener("click", function() {
		   if( validations()=== false){
				 
			 }else{
			$('#loading-gif').show(); 
			$('#chart-canvas').hide();
			$('#summary').hide();
			$('#noteMeasure').hide();
			$('#downloadPDF').hide();
			measureParameters = '';
			var yesOrNoOptionId = $("#yesOrNoOptionId option:selected").text();
			var reportTypeSelectedText = $("#reportTypeId option:selected").text();			
			var yearId = document.getElementById("yearLookUpId").value;
			var yearSelectedText = $("#yearLookUpId option:selected").text();
			var reportingOptionId;
			
			var measureId = document.getElementById("measureLookupId").value;
			var measureSelectedText = $("#measureLookupId option:selected").text();			
			
			
			if('${dataAnalysisId}' == '4' || '${dataAnalysisId}' == '5'){
				reportingOptionId = document.getElementById("reportingOptionLookupId").value;
			}
			
	        var multiSelectedMeasure = function(){	        	
	        	var selectedMeasures = document.getElementById('measureLookupId');
	        	for(var i =0; i < selectedMeasures.options.length; i++){
	        		//if(selectedMeasures.options[i].selected){	        			
	        			measureParameters = measureParameters + ',' + selectedMeasures.options[i].value;	        			
	        		//}
	        	}	        	
	        }
	        
	        
			if (reportTypeSelectedText == "Bar Chart") {
				var url = serverContextPath + '/api/barChart/dataAnalysisId/${dataAnalysisId}/subDataAnalysisId/${subDataAnalysisId}/yearId/' + yearId + '/reportingOptionId/' + reportingOptionId;
			}
			if (reportTypeSelectedText == "Line Chart") {
				multiSelectedMeasure();				
				var url;
				
				if('${dataAnalysisId}' == '3'){					
					if($('#excluFreqRowId').val() === '1'){
						url = serverContextPath + '/api/measureExclusionRate/dataAnalysisId/${dataAnalysisId}/subDataAnalysisId/${subDataAnalysisId}/measure/' + measureParameters.substring(1, measureParameters.length);
					}else if($('#excluFreqRowId').val() === '2'){
						url = serverContextPath + '/api/measureFrequency/dataAnalysisId/${dataAnalysisId}/subDataAnalysisId/${subDataAnalysisId}/measure/' + measureParameters.substring(1, measureParameters.length);
					}
				}else if('${dataAnalysisId}' == '4' || '${dataAnalysisId}' == '5'){
					if($('#excluFreqRowId').val() === '1'){
						url = serverContextPath + '/api/measureExclusionRate/dataAnalysisId/${dataAnalysisId}/subDataAnalysisId/${subDataAnalysisId}/reportingOptionId/' + reportingOptionId + '/measure/' + measureParameters.substring(1, measureParameters.length);
					}else if($('#excluFreqRowId').val() === '2'){
						url = serverContextPath + '/api/measureFrequency/dataAnalysisId/${dataAnalysisId}/subDataAnalysisId/${subDataAnalysisId}/reportingOptionId/' + reportingOptionId + '/measure/' + measureParameters.substring(1, measureParameters.length);
					}
				}
				
			}
			if (reportTypeSelectedText == "Map") {
				//document.getElementById("mapIframe").hidden = false;		
				$('#mapIframe').hide();
				/* $('#loading-gif').show(); */
				var epGpro = '0';
				if (reportingOptionSelectedText == "CLAIMS" || reportingOptionSelectedText == "EHR"
					|| reportingOptionSelectedText == "REGISTRY"
					|| reportingOptionSelectedText == "QCDR") {
					epGpro = '1';
				} else if (reportingOptionSelectedText == "GPROWI" || reportingOptionSelectedText == "GPRO Registry"
					|| reportingOptionSelectedText == "GPRO EHR"
						|| reportingOptionSelectedText == "GPRO WI GROP") {
					epGpro = '2';
				}
				var ruralUrbanId = document.getElementById("parameterLookupId").value;
				var yesNoId = document.getElementById("yesOrNoOptionId").value;
				var yearId = document.getElementById("yearLookUpId").value;
				var reportingOptionId = document.getElementById("reportingOptionLookupId").value;
				var url = serverContextPath + '/maps/epOrGpro/' + epGpro + '/ruralOrUrban/' + ruralUrbanId + '/yesOrNoOption/' + yesNoId + '/yearId/' + yearId + '/reportingOptionId/' + reportingOptionId + '/dataAnalysisId/${dataAnalysisId}/subDataAnalysisId/${subDataAnalysisId}';				
				
				document.getElementById("mapIframe").src = url;
				/* $('#loading-gif').hide(); */
				$('#mapIframe').show();
			}
			 }
			var ourRequest = new XMLHttpRequest();
			ourRequest.open('GET', url);
			ourRequest.setRequestHeader( 'Authorization', 'Basic ' + btoa( 'imapadmin:Vt@786' ) );
			ourRequest.onload = function() {
				$('#loading-gif').hide();
				$('#chart-canvas').show();
				$('#summary').show();
				$('#noteMeasure').show();
				$('#downloadPDF').show();
				if (reportTypeSelectedText == "Bar Chart") {
					barChartData = JSON.parse(ourRequest.responseText);
					//console.log(barChartData);
					var barChartDataAvail = barChartData.dataAvailable;
					var yesCountValues = barChartData.yesCountValues;
					var noCountValues = barChartData.noCountValues;
					var titleYearTextVal = yearSelectedText;
					if (yearSelectedText == 'ALL') {
						titleYearTextVal = 'Base Year(2012) to Option Year 3(2015)';
					}
	
					<!-- BAR CHART :: JAVA SCRIPT ###### START  -->
					var barChartData = {
						labels : barChartData.parameters,
						datasets : [ {
							label : 'YES',
							backgroundColor : window.chartColors.blue,
							borderColor : window.chartColors.blue,
							borderWidth : 1,
							data : barChartData.yesPercents
						}, {
							label : 'NO',
							backgroundColor : window.chartColors.orange,
							borderColor : window.chartColors.orange,
							borderWidth : 1,
							data : barChartData.noPercents
						} ]
					};
	
					var optionsInfo = {
						responsive : true,
						title : {
							display : true,
							text : titleYearTextVal + ' ' + reportingOptionSelectedText + ' Reporting Option Eligible Professionals Summary',
							padding : 40
						},
						animation : {
							duration : 1,
							onComplete : function() {
								var chartInstance = this.chart,
									ctx = chartInstance.ctx;
	
								ctx.font = Chart.helpers.fontString(Chart.defaults.global.defaultFontSize, Chart.defaults.global.defaultFontStyle, Chart.defaults.global.defaultFontFamily);
								ctx.textAlign = 'center';
								ctx.textBaseline = 'bottom';
	
								this.data.datasets.forEach(function(dataset, i) {
									var meta = chartInstance.controller.getDatasetMeta(i);
									meta.data.forEach(function(bar, index) {
										//console.log(bar._view.datasetLabel);
										var data = yesCountValues[index];
										if (bar._view.datasetLabel == "NO") {
											data = noCountValues[index];
										}
	
										ctx.fillText(data, bar._model.x, bar._model.y - 5);
									});
								});
							}
						},
						legend : {
							position : 'bottom',
						},
						tooltips : {
							mode : 'index',
							intersect : true,
						},
	
						hover : {
							mode : 'nearest',
							intersect : true,
						},
						scales : {
							xAxes : [ {
								display : true,
								scaleLabel : {
									display : true,
									labelString : 'PARAMETER'
								},
								ticks : {
									display : true,
									beginAtZero : true
								}
							} ],
							yAxes : [ {
								display : true,
								scaleLabel : {
									display : true,
									labelString : 'PERCENT'
								},
								ticks : {
									callback : function(label, index, labels) {
										return label + ' %';
									},
									display : true,
									beginAtZero : true
								}
							} ]
						}
					}
	
					var barconfig = {
						type : 'bar',
						data : barChartData,
						options : optionsInfo
					};
					<!-- BAR CHART :: JAVA SCRIPT ###### END  -->
	
				}  /* Bar Chart If Logic Ends */
	
				if (reportTypeSelectedText == "Line Chart") {
					lineChartData = JSON.parse(ourRequest.responseText);
					
					<!-- LINE CHART :: JAVA SCRIPT ###### START  -->
					var lineChartDataAvail = lineChartData.dataAvailable;				
					var titletext;
					var yaxeslabelstring;
					var precisionDigits;
					var percentLabel = "";
					var lineconfig;
					if($('#excluFreqRowId').val() === '1'){
						titletext = 'Mean Exclusion Rate Trend'
					    yaxeslabelstring = 'Mean Exclusion Rate';
						precisionDigits = 3;
						percentLabel = ' %';
					}else if($('#excluFreqRowId').val() === '2'){
						titletext = 'Frequency Trend'
						yaxeslabelstring = 'Frequency';
						precisionDigits = 6;
						percentLabel = ' Hz';
					}
						
					lineconfig = {
						type : 'line',
						data : {
							labels : lineChartData.uniqueYears,
							datasets : []
						},
						options : {
							responsive : true,
							title : {
								display : true,
								text : titletext
							},
							legend : {
								position : 'bottom',
							},
							tooltips : {
								mode : 'index',
								intersect : false,
							},
							hover : {
								mode : 'nearest',
								intersect : true
							},
							scales : {
								xAxes : [ {
									display : true,
									scaleLabel : {
										display : true,
										labelString : 'YEAR'
									}
								} ],
								yAxes : [ {
									display : true,
									scaleLabel : {
										display : true,
										labelString : yaxeslabelstring
									},
									ticks : {
										callback : function(label, index, labels) {
											return Number(label).toPrecision(precisionDigits) + percentLabel;
										},
										display : true
									}
								} ]
							}
						  }
					};				
					
					function addData(){		
						
						for (var key in lineChartData) {
							  if (lineChartData.hasOwnProperty(key)) {
							    console.log(key + " -> " + lineChartData[key]);	
							    
							    switch(key) {
								    case "measureData1":		
								    	lineconfig.data.datasets.push({
											label : "Measure-" + lineChartData.measureIdList[0],
											fill : false,
											backgroundColor : window.chartColors.green,
											borderColor : window.chartColors.green,
											data : lineChartData.measureData1,
										});								    	
								    	console.log("measureData1: " + key + " -> " + lineChartData[key]);
								        break;
								    case "measureData2":
								    	lineconfig.data.datasets.push({
											label : "Measure-" + lineChartData.measureIdList[1],
											fill : false,
											backgroundColor : window.chartColors.orange,
											borderColor : window.chartColors.orange,
											data : lineChartData.measureData2,
										});								    	
								    	console.log("measureData2: " + key + " -> " + lineChartData[key]);
								        break;
								    case "measureData3":
								    	lineconfig.data.datasets.push( {
											label : "Measure-" + lineChartData.measureIdList[2],
											fill : false,
											backgroundColor : window.chartColors.blue,
											borderColor : window.chartColors.blue,
											data : lineChartData.measureData3,
										});
								    	console.log("measureData3: " + key + " -> " + lineChartData[key]);
								        break;
								    case "measureData4":
								    	lineconfig.data.datasets.push({
											label : "Measure-" + lineChartData.measureIdList[3],
											fill : false,
											backgroundColor : window.chartColors.brown,
											borderColor : window.chartColors.brown,
											data : lineChartData.measureData4,
										});
								    	console.log("measureData4: " + key + " -> " + lineChartData[key]);
								        break;								    
								    default:
								        break;
								}
							  }
						}
						
						
					}
					addData();
			
					if('${dataAnalysisId}' == '3'){
						 if($('#excluFreqRowId').val() === '1'){
							 $("#summary").empty();
							 
								$('#summary').append("<div class='header'>Summary</div>");
								$('#summary').append("<table style='border:1px solid #327A81;'><tr><th>Measure</th><th>Allowable Exclusion</th><th>Reporting Options</th></tr></table>")
								$.each(lineChartData.measureIdList, function(index, value) {						  
									 $('#summary').append("<table><tr style='border-bottom:1px solid #327a81;border-left:1px solid #327a81;border-right:1px solid #327a81;border-top:0px;'><td style='text-align:center'>" 
									 + "Measure-" + value + "</td><td>" 
									 + lineChartData.allowableExclusionsList[index] + "</td><td>" 
									 + lineChartData.reportingOptionsList[index] + "</td></tr></table>" 
								);
								})
							}else if($('#excluFreqRowId').val() === '2'){
								$("#summary").empty();
								$('#summary').append("<div class='header' style='width:80%;margin:auto;'>Summary</div>");
								$('#summary').append("<table style='border:1px solid #327A81;width:80%;margin:auto;'><tr><th>Measure</th><th>Reporting Options</th></tr></table>")
								$.each(lineChartData.measureIdList, function(index, value) {						  
									 $('#summary').append("<table style='width:80%;margin:auto;'><tr style='border-bottom:1px solid #327a81;border-left:1px solid #327a81;border-right:1px solid #327a81;border-top:0px;'><td style='text-align:center'>" 
									 + "Measure-" + value + "</td><td>" 
									 + lineChartData.reportingOptionsList[index] + "</td></tr></table>" 
								);})
							};
			    
					}
					<!-- LINE CHART :: JAVA SCRIPT ###### END  -->
	
				} <!-- Line Chart If Logic Ends-->
				if (reportTypeSelectedText == "Map") {
					<!-- TODO for Map-->
				}
				<!-- MAP ENDS -->
	
				<!-- Deleting the <canvas> element and then reappending a new <canvas> to the parent container: To Fix the Hover Over Issue   -->
				resetCanvas();
	
				var chartctx = document.getElementById("chart-canvas").getContext("2d");
				/* $('#noteMeasure').hide(); */
	
				<!-- Different Chart Display :: START -->
				if (reportTypeSelectedText == "Bar Chart") {
					document.getElementById("mapIframe").hidden = true;
					if (barChartDataAvail == "YES") {
						//document.getElementById('messageDisplay').style.display = 'none';
						document.getElementById("messageDisplay").innerHTML = "";
						$("messageDisplay").attr("disabled", true);
						var myBarChart = new Chart(chartctx, barconfig);
					}
					if (barChartDataAvail == "NO") {
						$("messageDisplay").attr("disabled", false);
						$('#noteMeasure').hide();
						$('#downloadPDF').hide();
						document.getElementById("messageDisplay").innerHTML = "<div id='NoData'>No Data Available For The Selected Options!</div>";
					}
				}
				if (reportTypeSelectedText == "Line Chart") {
					document.getElementById("mapIframe").hidden = true;
					if (lineChartDataAvail == "YES") {
						document.getElementById("messageDisplay").innerHTML = "";
						$("messageDisplay").attr("disabled", true);
						var myLineChart = new Chart(chartctx, lineconfig);
					}
					if (lineChartDataAvail == "NO") {
						$("messageDisplay").attr("disabled", false);
						$('#noteMeasure').hide();
						$('#downloadPDF').hide();
						$('#summary').hide();
						document.getElementById("messageDisplay").innerHTML = "<div id='NoData'>No Data Available For The Selected Options!</div>";
					}
				}
	
				<!-- Different Chart Display :: END -->
	
			};
	
			ourRequest.send();
	
		});
	
		var resetCanvas = function() {
			$('#chart-canvas').remove(); // this is my <canvas> element
			$('#chart-container').append('<canvas id="chart-canvas"><canvas>');
		};
	
		document.getElementById("reportTypeId").onchange = function() {
			console.log('Inside on change..');
			var x = document.getElementById("reportTypeId").value;
			console.log('value of x:' + x);
	
			if (x == 'Bar Chart') {
				// Set the Parameter as "ALL" for Bar Chart
				$("#parameterLookupId > option").each(function() {
					if (this.text == 'ALL') {
						$('#parameterLookupId').val(this.value);
					}
				});
			}
	
			if (x == 'Line Chart') {
				// Set Option Year as "ALL" for Line Chart
				$("#yearLookUpId > option").each(function() {
					if (this.text == 'ALL') {
						$('#yearLookUpId').val(this.value);
					}
				});
				// Set Reporting Option as "ALL" for Line Chart
				$("#reportingOptionLookupId > option").each(function() {
					if (this.text == 'ALL') {
						$('#reportingOptionLookupId').val(this.value);
					}
				});
			}
			if (x == 'Map') {
				console.log('Map..');
				var x = document.getElementById("yesOrNoOptionRow")
				x.hidden = false;
			} else {
				var x = document.getElementById("yesOrNoOptionRow")
				x.hidden = true;
			}
		};
		
		$('#measureLookupId').dblclick(function() {	
		    $('#measureLookupId option[value=' + $('#measureLookupId option:selected').val() + ']').remove();
		});
		//Download chart in the form of pdf
		
		downloadPDF.addEventListener("click", function() {
			  // only jpeg is supported by jsPDF
			  var chart = document.getElementById("chart-canvas");
			  var imgData = chart.toDataURL();		  
			  var pdf = new jsPDF();
			  pdf.addImage(imgData, 'JPG', 15, 40);
			  var downloadPDF = document.getElementById('downloadPDF');
			  if('${subDataAnalysisId}' == '9'){pdf.save("Hypothesis4.pdf");}
			  else if('${subDataAnalysisId}' == '10'){pdf.save("Hypothesis6.pdf");}
			  else if('${subDataAnalysisId}' == '6' || '${subDataAnalysisId}' == '7' || '${subDataAnalysisId}' == '8')
			  {pdf.save("Hypothesis5.pdf");}
			}, false);  
		 
		
	</script>
	<!-- Javascript -->
	<script>
      $(function() {    
    	    var sourceData;
            var availableTutorials  =  [
               "ActionScript",
               "Bootstrap",
               "C",
               "C++",
            ]; 
            var jsonArr = [];
            
            var testData = [
                { "label": "India", "value": "IND" },
                { "label": "Australia", "value": "AUS" }
             ]
            $.ajax({
            	   /*  url: 'http://localhost:8080/imapservices/api/measure/all', */ 
            	    /* url: 'http://ec2-34-208-54-139.us-west-2.compute.amazonaws.com/imapservices/api/measure/all', */
            	    url: '/imapservices/api/measure/all',
            	   type: 'GET',
            	   async: false,
            	   beforeSend: function (xhr) {
            		   xhr.setRequestHeader ('Authorization', 'Basic ' + btoa( 'imapadmin:Vt@786' ) );
            		   },
            	   dataType: 'json',
            	   success: function(data) {
            		   //alert('Success!');
            		   $.each(data, function(idx, obj) {
            				//alert(obj.measureId + '-' + obj.measureName);
            				jsonArr.push({            			        
            			        value: obj.measureId + '-' + obj.measureName,
            			        id: obj.id
            			        //selected: 'false'
            			    });
            			});
             	   },
            	   error: function() {
            	      alert('Ajax error!');
            	   },
            });            
            
            $("#automplete-1").autocomplete({
               source: jsonArr,               
               minLength:1,
               focus: function( event, ui ) {
                   $( "#automplete-1" ).val( ui.item.value);
                      return false;
                },  
                change: function (event, ui) {
                    if (ui.item == null){ 
                     //here is null if entered value is not match in suggestion list
                        $(this).val((ui.item ? ui.item.value : ""));
                    }
                },
                select: function( event, ui ) {                    
                    if($('#measureLookupId').children('option').length < 4 && $('#measureLookupId').find('option[value='+ ui.item.id + ']').length == 0){
                    	$('#measureLookupId').append( new Option(ui.item.value,ui.item.id) );
                    }                    
                    $( "#automplete-1" ).val() = '';
                    return false;
                 }
            });
         });
      </script>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>