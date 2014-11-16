'use strict';

/**
 * @ngdoc directive
 * @name swFrontApp.directive:amChart
 * @description
 * # amChart
 */
angular.module('TomEEAngular')
        .directive('amChart', function () {
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
                            "type": "serial",
                            "theme": "none",
                            "marginLeft": 20,
                            "pathToImages": "http://www.amcharts.com/lib/3/images/",
                            "valueAxes": [{
                                    "axisAlpha": 0,
                                    "inside": true,
                                    "position": "left",
                                    "ignoreAxisWidth": true
                                }],
                            "graphs": [{
                                    "balloonText": "[[category]]<br><b><span style='font-size:14px;'>[[value]]</span></b>",
                                    "bullet": "round",
                                    "bulletSize": 6,
                                    "lineColor": "#d1655d",
                                    "lineThickness": 2,
                                    "negativeLineColor": "#637bb6",
                                    "type": "smoothedLine",
                                    "valueField": "value"
                                }],
                            "chartScrollbar": {},
                            "chartCursor": {
                                "categoryBalloonDateFormat": "YYYY",
                                "cursorAlpha": 0,
                                "cursorPosition": "mouse"
                            },
                            "dataDateFormat": "YYYY",
                            "categoryField": "year",
                            "categoryAxis": {
                                "minPeriod": "YYYY",
                                "parseDates": true,
                                "minorGridAlpha": 0.1,
                                "minorGridEnabled": true
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
