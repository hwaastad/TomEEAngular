'use strict';

/**
 * @ngdoc function
 * @name swFrontApp.controller:ChartsCtrl
 * @description
 * # ChartsCtrl
 * Controller of the swFrontApp
 */
angular.module('TomEEAngular')
        .controller('ChartsCtrl', function ($scope, ChartDataService) {
            $scope.chartData = {};

//            $scope.getChartData = function () {
                ChartDataService.getChartData()
                        .then(function (data) {
                            $scope.chartData = data.chartData;
                            $scope.categoryField = data.categoryField;
                            $scope.plotAreaBorderAlpha = data.plotAreaBorderAlpha;
                        });
//            };

        });
