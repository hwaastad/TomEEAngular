'use strict';

/**
 * @ngdoc directive
 * @name swFrontApp.directive:amPie
 * @description
 * # amPie
 */
angular.module('TomEEAngular')
        .directive('amPie', function () {
            return {
                restrict: 'E',
                replace: true,
                template: '<div id="chartdiv" style="min-width: 310px; height: 400px; margin: 0 auto"></div>',
                link: function (scope, element, attrs) {

                    var chart = false;

                    var initChart = function () {
                        if (chart)
                            chart.destroy();
                        chart = AmCharts.makeChart("chartdiv", {
                            "type": "pie",
                            "theme": "none",
                            "valueField": "value",
                            "titleField": "year",
                            "outlineAlpha": 0.4,
                            "depth3D": 15,
                            "balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
                            "angle": 30,
                            "exportConfig": {
                                menuItems: [{
                                        icon: '/lib/3/images/export.png',
                                        format: 'png'
                                    }]
                            }
                        });
                        scope.$watch('chartData', function (val) {
                            if (val === undefined || val === null)
                                return;
                            chart.dataProvider = val;
                            chart.validateData();
                        });

                    };
                    initChart();

                }
            };
        });
