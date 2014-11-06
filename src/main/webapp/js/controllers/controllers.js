/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var myControllers = angular.module('myApp.controllers', []);

myControllers.controller('CustomerController', ['$scope', 'CustomerService', 'AlertService', function ($scope, CustomerService, AlertService) {
        $scope.input = '';
        $scope.customers = {};
        $scope.customer = {};
        init = function () {
            CustomerService.getCustomers().then(function (data) {
                $scope.customers = data;
            })
                    .catch(function (response) {
                        AlertService.add('danger', 'Danger', 'Error getting customers: ' + response.status);
                    });
        };
        init();

        $scope.addCustomer = function (customer) {
            CustomerService.createCustomer(customer).then(function (data) {
                init();
            });
        };

        $scope.removeCustomer = function (customer) {
            CustomerService.deleteCustomer(customer).then(function (data) {
                $scope.customers = _.without($scope.customers, customer);
            });
        };

        $scope.updateCustomer = function (customer) {
            CustomerService.updateCustomer(customer).then(function (data) {
                init();
            });
        };

    }]);

myControllers.controller('SecureController', ['$scope', 'CustomerService', 'AlertService', function ($scope, CustomerService, AlertService) {

    }]);

myControllers.controller('ChartController', ['$scope', 'SampleDataService', function ($scope, SampleDataService) {
        $scope.chartData = {};
        SampleDataService
                .getChartData()
                .then(function (data) {
                    $scope.chartData = data.chartData;
                    $scope.categoryField = data.categoryField;
                    $scope.plotAreaBorderAlpha = data.plotAreaBorderAlpha;
                });

    }]);

myControllers.controller('PieChartController', ['$scope', 'SampleDataService', function ($scope, SampleDataService) {
        $scope.chartData = {};
        SampleDataService
                .getPieData()
                .then(function (data) {
                    $scope.chartData = data.chartData;
                    $scope.categoryField = data.categoryField;
                    $scope.plotAreaBorderAlpha = data.plotAreaBorderAlpha;
                });

    }]);

myControllers.controller('UserController', function ($scope) {
    $scope.input = '';
});

myControllers.controller('LoginController', ['$scope', 'AuthService', '$location', 'AlertService', function ($scope, AuthService, $location, AlertService) {
        $scope.user = {};
        $scope.login = function (user) {
            console.log('LoginController: Authenticated: ' + AuthService.isAuthenticated());
            AuthService.login(user).then(function (data) {
                console.log('LoginController: login OK');
                AlertService.add('success', 'Success', 'LoginController: login OK');
                AuthService.setAuthData(data);
            }).catch(function (response) {
                AlertService.add('danger', 'Danger', 'LoginController: login Fail: ' + response.status);
                console.log('LoginController: login Fail: ' + response.status);
            });
        };
        $scope.logout=function (user){
            AuthService.logout().then(function (){
                AlertService.add('success', 'Success', 'LoginController: logout OK');
            });
        };

    }]);

