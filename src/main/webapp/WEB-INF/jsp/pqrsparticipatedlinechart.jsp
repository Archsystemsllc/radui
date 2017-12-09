<!doctype html>
<html>

<head>

    <title>PQRS Participated - Line Chart</title>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Chart.bundle.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/utils.js"></script>

    <style>
    canvas{
        -moz-user-select: none;
        -webkit-user-select: none;
        -ms-user-select: none;
    }
    </style>

</head>

<body>

	<script type="text/javascript" language="javascript" ></script>
	
    <div style="width:75%;">
        <canvas id="canvas"></canvas>
    </div>
    <br>
    <br>

    <script>
    
    	var titletext = 'Base Year to Option Year 3 ' + 'Mental Health HPSA' + ' Percentage Summary'
    
    	var yaxeslabelstring = 'Percent of EPs & GPROs in ' +'Mental Health HPSA'
    
        var config = {
            type: 'line',
            data: {
                labels: ${uniqueYears},
                datasets: [{
                    label: "CLAIMS",
                    fill: false,
                    backgroundColor: window.chartColors.red,
                    borderColor: window.chartColors.red,
                    data: ${claimsPercents},
                }, {
                    label: "EHR",
                    fill: false,
                    backgroundColor: window.chartColors.green,
                    borderColor: window.chartColors.green,
                    data: ${ehrPercents},
                }, {
                    label: "Registry",
                    fill: false,
                    backgroundColor: window.chartColors.orange,
                    borderColor: window.chartColors.orange,
                    data: ${registryPercents},
                }, {
                    label: "GPROWI",
                    fill: false,
                    backgroundColor: window.chartColors.purple,
                    borderColor: window.chartColors.purple,
                    data: ${gprowiPercents},
                }, {
                    label: "QCDR",
                    fill: false,
                    backgroundColor: window.chartColors.brown,
                    borderColor: window.chartColors.brown,
                    data: ${qcdrPercents},
                }]
            },
            options: {
                responsive: true,
                title:{
                    display:true,
                    text: titletext
                },
                legend: {
                    position: 'bottom',
                },
                tooltips: {
                    mode: 'index',
                    intersect: false,
                },
                hover: {
                    mode: 'nearest',
                    intersect: true
                },
                scales: {
                    xAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'YEAR'
                        }
                    }],
                    yAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: yaxeslabelstring
                        },
	                    ticks: {
	                    	callback: function(label, index, labels) {
	                    		return label + ' %';
	                    	},
	                        display: true
	                    }
                    }]
                }
            }
        };

        window.onload = function() {
            var ctx = document.getElementById("canvas").getContext("2d");
            window.myLine = new Chart(ctx, config);
        };

    </script>
</body>

</html>
