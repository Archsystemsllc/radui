/* Utils Java Script */

'use strict';

window.chartColors = {
	red: 'rgb(255, 0, 0)',
	orange: 'rgb(255, 159, 64)',
	yellow: 'rgb(255, 255, 0)',
	green: 'rgb(0, 128, 0)',
	lime: 'rgb(0, 255, 0)',
	blue: 'rgb(0, 0, 255)',
	darkblue: 'rgba(0,0,128,.5)',
	purple: 'rgb(153, 102, 255)',
	grey: 'rgb(201, 203, 207)',
	brown: 'rgb(102, 51, 0)'
};

window.randomScalingFactor = function() {
	return (Math.random() > 0.5 ? 1.0 : -1.0) * Math.round(Math.random() * 100);
};

