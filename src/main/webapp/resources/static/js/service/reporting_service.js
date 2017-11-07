'use strict';

angular.module('myApp').factory('ReportingService', ['$http', '$q', function($http, $q){

    var factory = {
    		dataAnalysisOptions: dataAnalysisOptions,
    		allYears: allYears
    };

    return factory;

    function dataAnalysisOptions(configData) {
    	console.log("configData::"+configData.restUrl)
        var deferred = $q.defer();
        $http.get(configData.restUrl+"dataanalysis")
            .then(
            function (response) {
            	//console.log("**********:"+JSON.stringify(response.data))
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching data analysis options');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function allYears(configData) {
    	console.log("configData::"+configData.restUrl)
        var deferred = $q.defer();
        $http.get(configData.restUrl+"/year/all")
            .then(
            function (response) {
            	//console.log("**********:"+JSON.stringify(response.data))
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching data analysis options');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

 
}]);
