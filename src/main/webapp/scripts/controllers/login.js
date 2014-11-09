'use strict';

/**
 * @ngdoc function
 * @name swFrontApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the swFrontApp
 */
angular.module('JavaEEAngular')
        .controller('LoginCtrl', function ($scope, auth, $location) {
            $scope.login = function () {
                if ($scope.loginForm.$valid) {
                    var promise = auth.login($scope.user);
                    promise.then(success, error);
                }
            };

            var success = function (response) {
                $location.path('/home');
            };

            var error = function (response) {
                $scope.wrongCredentials = true;
            };
        });
