'use strict';

/**
 * @ngdoc directive
 * @name swFrontApp.directive:navbar
 * @description
 * # navbar
 */
angular.module('JavaEEAngular')
        .directive('navbar', function () {
            return {
                templateUrl: 'views/tpl/navbar.tpl.html',
                controller: 'navigationController',
                restrict: 'E'
            };
        });
