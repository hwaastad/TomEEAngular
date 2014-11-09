'use strict';

/**
 * @ngdoc function
 * @name swFrontApp.controller:ChartsCtrl
 * @description
 * # ChartsCtrl
 * Controller of the swFrontApp
 */
angular.module('JavaEEAngular')
        .controller('PieChartsCtrl', function ($scope, ChartDataService) {
            $scope.chartData = {};

            ChartDataService.getPieData()
                    .then(function (data) {
                        $scope.chartData = data.chartData;
                        $scope.categoryField = data.categoryField;
                        $scope.plotAreaBorderAlpha = data.plotAreaBorderAlpha;
                    });

        });
