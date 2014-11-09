'use strict';

/**
 * @ngdoc directive
 * @name swFrontApp.directive:cuFocus
 * @description
 * # cuFocus
 */
angular.module('JavaEEAngular')
        .directive('cuFocus', function () {
            return {
                restrict: 'A',
                require: 'ngModel',
                link: function postLink(scope, element, attrs, controller) {
                    controller.$focused = false;
                    element.bind('focus', function (e) {
                        scope.$apply(function () {
                            controller.$focused = true;
                        });
                    }).bind('blur', function () {
                        scope.$apply(function () {
                            controller.$focused = false;
                        });
                    });
                }};
        });
