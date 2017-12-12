<!doctype html>
<html>

<head>
    <title>Bar Chart</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Chart.bundle.js"></script>
    <style>
    canvas {
        -moz-user-select: none;
        -webkit-user-select: none;
        -ms-user-select: none;
    }
    </style>
</head>

<body>
    <div id="container" style="width: 75%;">
        <canvas id="canvas"></canvas>
    </div>

    <script>
    
        var barChartData = {
            labels: ${parameters},
            datasets: [{
                label: 'YES',
				backgroundColor: "rgba(0,0,128,.5)",
				borderColor: "rgba(0,0,128,.5)",
                borderWidth: 1,
                data: ${yesPercents}
            }, {
                label: 'NO',
				backgroundColor: "rgba(255,0,0,.7)",
				borderColor: "rgba(255,0,0,.7)",
                borderWidth: 1,
                data: ${noPercents}
            }]

        };
        
        var optionsInfo = {
                responsive: true,
                title:{
                    display:true,
                    text:'Base Year(2012) to Option Year 3(2015) REGISTRY Reporting Option Eligible Professionals Summary'
                },
                legend: {
                    position: 'bottom',
                },
                tooltips: {
                    mode: 'index',
                    intersect: true,
                },
                hover: {
                    mode: 'nearest',
                    intersect: true,
                },
                scales: {
                    xAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'REPORTING OPTION'
                        },
	                    ticks: {
	                        display: true,
	                        beginAtZero:true
	                    }
                    }],
                    yAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'PERCENT'
                        },
	                    ticks: {
	                    	callback: function(label, index, labels) {
	                    		return label + ' %';
	                    	},
	                        display: true,
	                        beginAtZero:true
	                    }
                    }]
                }
        }

        var config = {
        	type: 'bar',
        	data: barChartData,
            options: optionsInfo
        };
        
        
        window.onload = function() {
            var ctx = document.getElementById("canvas").getContext("2d");
            window.myBar = new Chart(ctx, config);
        };

    </script>
</body>

</html>