/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var myDirectives = angular.module('myApp.directives', []);

myDirectives.directive('amChart', function () {
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

myDirectives.directive('amPie', function () {
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

myDirectives.directive('loginDialog', function (AUTH_EVENTS) {
    return {
        restrict: 'A',
       // template: '<div ng-if="visible" ng-include="\'login-form.html\'">',
        templateUrl: 'partials/tpl/login.tpl.html',
        link: function (scope) {
            var showDialog = function () {
                scope.visible = true;
            };

            scope.visible = false;
            scope.$on(AUTH_EVENTS.notAuthenticated, showDialog);
            scope.$on(AUTH_EVENTS.sessionTimeout, showDialog);
        }
    };
});
