<html>
<head>

<title>Choropleth Tutorial - Leaflet</title>

<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="shortcut icon" type="image/x-icon"
	href="docs/images/favicon.ico" />

<link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.3/dist/leaflet.css" integrity="sha512-07I2e+7D8p6he1SIM+1twR5TIrhUQn9+I6yjqD53JQjFiMf8EtC93ty0/5vJTZGF8aAocvHYNEDJajGdNx1IsQ==" crossorigin=""/>
<script src="https://unpkg.com/leaflet@1.0.3/dist/leaflet.js"
	integrity="sha512-A7vV8IFfih/D732iSSKi20u/ooOfj/AGehOKq0f4vLT1Zr2Y+RX7C+w8A1gaSasGtRUZpF/NZgzSAu4/Gc41Lg=="
	crossorigin=""></script>
<!-- Load Esri Leaflet from CDN -->
<script src="https://unpkg.com/esri-leaflet@2.0.8"
    integrity="sha384-ze7rgny7/YGFxBlVgTBdOABNWe5V9BYjju/qwqJhCU8XJHtuEnRlbUpN5lXyY706"
    crossorigin=""></script>



<style>
#map {
	width: 600px;
	height: 400px;
}
</style>

<style>
#map {
	width: 900px;
	height: 500px;
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
</style>
</head>
<body>

	<div id='map'></div>

	<script type="text/javascript"
		src='${pageContext.request.contextPath}/maps-data/epOrGpro/${epOrGpro}/ruralOrUrban/${ruralOrUrban}/yesOrNoOption/${yesOrNoOption}/year/${yearId}/reportingOption/${reportingOptionId}/dataAnalysis/${dataAnalysisId}/subDataAnalysis/${subDataAnalysisId}'></script>

	<script type="text/javascript">

	var map = L.map('map').setView([37.8, -96], 4);

	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 18,
		/* attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
			'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery © <a href="http://mapbox.com">Mapbox</a>', */
		id: 'mapbox.satellite'
	}).addTo(map);
//Labels to Map
L.esri.basemapLayer('ImageryLabels').addTo(map);

	// control that shows state info on hover
	var info = L.control();

	info.onAdd = function (map) {
		this._div = L.DomUtil.create('div', 'info');
		this.update();
		return this._div;
	};

	info.update = function (props) {
		this._div.innerHTML = '${hoverTitle}' +  (props ?
			'<b>State: </b>' + ${hoverStateKeyName} + '<br />' +
			'<b>Count: </b>' + ${hoverStateValueName} + '<br />' +
			'<b>EP/GPro: </b>${hoverEPOrGro}<br />' +
			'<b>Rural/Urban: </b>${hoverRuralOrUrban}<br />' +
			'<b>Yes/No Option: </b>${hoverYesOrNoOption}<br />' +
			'<b>Reporting Option: </b> ${hoverReportingOption} <br />' 
			: 'Hover over a state');
	};

	info.addTo(map);


	// get color depending on population density value
	function getColor(d) {
		var keys = ${legendKeys};
		var values = ${legendValues};
		for (i = 0; i < keys.length-2; i++) {
			if(d > keys[i]) return values[i];
		}
		return values[keys.length-1];
	}

	function style(feature) {
		var mapPropertyKey = ${mapPropertyKey};
		var value = eval(mapPropertyKey);
		return {
			weight: 2,
			opacity: 1,
			color: 'white',
			dashArray: '3',
			fillOpacity: 0.7,
			fillColor: getColor(value)
		};
	}

	function highlightFeature(e) {
		var layer = e.target;

		layer.setStyle({
			weight: 5,
			color: '#666',
			dashArray: '',
			fillOpacity: 0.7
		});

		if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
			layer.bringToFront();
		}

		info.update(layer.feature.properties);
	}

	var geojson;

	function resetHighlight(e) {
		geojson.resetStyle(e.target);
		info.update();
	}

	function zoomToFeature(e) {
		map.fitBounds(e.target.getBounds());
	}

	function onEachFeature(feature, layer) {
		layer.on({
			mouseover: highlightFeature,
			mouseout: resetHighlight,
			click: zoomToFeature
		});
	}

	geojson = L.geoJson(data, {
		style: style,
		onEachFeature: onEachFeature
	}).addTo(map);

	map.attributionControl.addAttribution('');


	var legend = L.control({position: 'bottomright'});

	legend.onAdd = function (map) {
		var keys = ${legendKeys};
		var div = L.DomUtil.create('div', 'info legend'),
			grades = keys.reverse(),
			labels = [],
			from, to;

		for (var i = 0; i < grades.length; i++) {
			from = grades[i];
			to = grades[i + 1];

			labels.push(
				'<i style="background:' + getColor(from + 1) + '"></i> ' +
				from + (to ? '&ndash;' + to : '+'));
		}

		div.innerHTML = labels.join('<br>');
		return div;
	};
	legend.addTo(map);

</script>



</body>
</html>