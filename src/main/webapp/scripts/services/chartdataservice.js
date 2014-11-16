'use strict';

/**
 * @ngdoc service
 * @name swFrontApp.ChartDataService
 * @description
 * # ChartDataService
 * Factory in the swFrontApp.
 */
angular.module('TomEEAngular').factory('ChartDataService', function ($q, $http, AlertService) {
    var service = {
        getChartData: function () {
            var response = {};
            var defer = $q.defer();
            response.categoryField = 'year';
            response.plotAreaBorderAlpha = 0.2;
            $http.get('http://localhost:8080/chartdata')
                    .success(function (data) {
                        console.log('We got chartdata from api');
                        response.chartData = data;
                        defer.resolve(response);
                    })
                    .error(function (status) {
                        AlertService.add('danger', 'DataService failure',status);
                        defer.reject();
                    });
            return defer.promise;
//            return Restangular.all('chartdata').getList().then(function (data) {
//                response.chartData = data;
//                return response;
//            });
        },
        getPieData: function () {
            var response = {};
            var defer = $q.defer();
            response.categoryField = 'year';
            response.plotAreaBorderAlpha = 0.2;
            $http.get('http://localhost:8080/chartdata/pie')
                    .success(function (data) {
                        console.log('We got piedata from api');
                        response.chartData = data;
                        defer.resolve(response);
                    })
                    .error(function (status) {
                        AlertService.add('danger', 'DataService failure',status);
                        defer.reject();
                    });
            return defer.promise;
            
//            return Restangular.all('chartdata/pie').getList().then(function (data) {
//                response.chartData = data;
//                return response;
//            });
        }
    };
    return service;

});
