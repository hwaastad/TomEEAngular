'use strict';

angular.module('TomEEAngular')
        .controller('navigationController', function ($scope, $location, auth) {

            $scope.isLoggedIn = auth.isLoggedIn;
    
            $scope.currentUser=auth.getCurrentUser();

            $scope.isActive = function (path) {
                return path === $location.path();
            };
            
            $scope.isAuthorized = function (role) {
                return auth.authorize(role);
            };

            $scope.logout = function () {
                auth.logout();
                $location.path('/login');
            };
            var success = function (response) {
                console.log('Done processing....');
                //$location.path('/login');
            };

            var error = function (response) {
                $scope.wrongCredentials = true;
            };
        });