'use strict';

/**
 * @ngdoc function
 * @name swFrontApp.controller:AdminCtrl
 * @description
 * # AdminCtrl
 * Controller of the swFrontApp
 */
angular.module('TomEEAngular')
        .controller('AdminCtrl', function ($scope, $http) {
            $http.get('http://localhost:8080/customer');
        });
