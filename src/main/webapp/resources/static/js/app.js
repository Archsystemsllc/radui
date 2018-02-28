'use strict';

var App = angular.module('myApp',[]);
//angular.module('myApp').constant('configData', "{'restUrl' : 'http2://localhost:8080/imapservices/api/'}");
(function() {
	console.log("*************************************************: Start");
	  var xhr = new XMLHttpRequest();
	  xhr.open("GET", "config.json", false);
	  xhr.onload = function (e) {
	    if (xhr.readyState === 4) {
	      if (xhr.status === 200) {
	        console.log(xhr.responseText);
	        angular.module('myApp').constant('configData', xhr);
	        console.log("aaaaaaaaaaaaa")
	      } else {
	        console.error(xhr.statusText);
	      }
	    }
	  };
	  xhr.onerror = function (e) {
	    console.error(xhr.statusText);
	  };
	  xhr.send(null);
  
  console.log("*************************************************: End");
})();

angular.module('myApp').provider('configDataProvider', ['configData', function(configData) {	
	this.data = configData;
	  this.$get = function() {
	    return this.data;
	  };
}]);


