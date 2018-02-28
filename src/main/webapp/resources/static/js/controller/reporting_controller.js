'use strict';
angular.module('myApp').controller('DemoController', ["configData", function DemoController(configData) {
	  this.configData = configData;
	  
	}]);
angular.module('myApp').controller('ReportingController', ['configData', 'ReportingService', function(configData, ReportingService) {
    var self = this;
    self.dataAnalysis={id:null,dataAnalysisDescription:'',dataAnalysisName:''};
    self.dataAnalysis=[];
    var data = JSON.parse(configData.responseText) 
    console.log("configData::"+data.restUrl)
    dataAnalysisOptions();

    function dataAnalysisOptions(){
    	ReportingService.dataAnalysisOptions(data)
            .then(
            function(d) {
                self.dataAnalysis = d;
            },
            function(errResponse){
                console.error('Error while fetching data analysis options');
            }
        );
    }  
}]);

angular.module('myApp').controller('MapLineBarChartController', ['configData', 'ReportingService', function(configData, ReportingService) {
    var self = this;
    //self.dataAnalysis={id:null,dataAnalysisDescription:'',dataAnalysisName:''};
    self.years=[];
    var data = JSON.parse(configData.responseText) 
    console.log("configData::"+data.restUrl)
    allYears();

    function allYears(){
    	ReportingService.allYears(data)
            .then(
            function(d) {
                self.years = d;
            },
            function(errResponse){
                console.error('Error while fetching data analysis options');
            }
        );
    }  
}]);