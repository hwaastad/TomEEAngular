'use strict';

var myServices = angular.module('myApp.services', ['ngResource', 'restangular']);
myServices.factory('CustomerService', ['Restangular', function (Restangular) {
        var baseCustomers = Restangular.all('customer');

        var service = {
            getCustomers: function () {
                return baseCustomers.getList();
//                return baseCustomers.getList().then(function (data) {
//                    return data;
//                });
            },
            createCustomer: function (customer) {
                return baseCustomers.post(customer).then(function (response) {
                    return response;
                });
            },
            deleteCustomer: function (customer) {
                return Restangular.one('customer', customer.id).get().then(function (user) {
                    user.name = customer.name;
                    user.remove();
                });
            },
            updateCustomer: function (customer) {
                return Restangular.one('customer', customer.id).get().then(function (user) {
                    user.name = customer.name;
                    user.put();
                });
            }
        };
        return service;
    }]);


myServices.factory('SampleDataService', function (Restangular) {
    var service = {
        getChartData: function () {
            var response = {};
            response.categoryField = 'year';
            response.plotAreaBorderAlpha = 0.2;
            return Restangular.all('chartdata').getList().then(function (data) {
                response.chartData = data;
                return response;
            });
        },
        getPieData: function () {
            var response = {};
            response.categoryField = 'year';
            response.plotAreaBorderAlpha = 0.2;
            return Restangular.all('chartdata/pie').getList().then(function (data) {
                response.chartData = data;
                return response;
            });
        }
    };
    return service;
});

myServices.factory('AuthService', ['$rootScope', 'Restangular', '$window', 'AUTH_EVENTS', '$http', function ($rootScope, Restangular, $window, AUTH_EVENTS, $http) {
        var authFactory = {
            authData: undefined
        };

        var baseCustomers = Restangular.all('auth');

        authFactory = {
            login: function (user) {
                return baseCustomers.post(user);
            },
            setAuthData: function (authData) {
                console.log("AuthService: Setting auth data....");
                this.authData = {
                    authId: authData.authId,
                    authToken: authData.authToken,
                    authPermission: authData.authPermission
                };
                $window.sessionStorage.authData = JSON.stringify(this.authData);
                $rootScope.$broadcast('authChanged');
            },
            getAuthData: function () {
                if ($window.sessionStorage.authData) {
                    console.log('getAuthdata: ' + $window.sessionStorage.authData);
                    this.authData = $window.sessionStorage.authData;
                }
                return this.authData;
            },
            isAuthenticated: function () {
                console.log('AuthService: isAuthenticated...' + !angular.isUndefined(this.authData));
                if ($window.sessionStorage.authData) {
                    console.log('Session exists in window...');
                    return true;
                } else {
                    console.log('Session NOT exists in window, checking authData...');
                    return !angular.isUndefined(this.authData);
                }
            },
            logout: function () {
                authFactory.authData = undefined;
                if ($window.sessionStorage.authData) {
                    $window.sessionStorage.authData = null;
                }
            }
        };

        return authFactory;
    }]);

myServices.factory('authHttpRequestInterceptor', ['$rootScope', '$injector', function ($rootScope, $injector) {
        var authHttpRequestInterceptor = {
            request: function ($request) {
                var authService = $injector.get('AuthService');
                if (authService.isAuthenticated()) {
                    console.log('authHttpRequestInterceptor: Setting headers...');
                    $request.headers['auth-id'] = authService.getAuthData().authId;
                    console.log('Setting token....');
                    $request.headers['auth-token'] = authService.getAuthData().authToken;
                }
                console.log('authHttpRequestInterceptor: Returning...');
                return $request;
            }
        };
        return authHttpRequestInterceptor;

    }]);

myServices.factory('AlertService', ['$timeout', '$rootScope', function ($timeout, $rootScope) {
        $rootScope.alerts = [];
        var service = {
            add: function (type, title, msg, timeout) {
                $rootScope.alerts.push({
                    type: type,
                    title: title,
                    msg: msg,
                    close: function () {
                        return service.closeAlert(this);
                    }
                });

                if (typeof timeout === 'undefined') {
                    timeout = 8000;
                }

                if (timeout) {
                    $timeout(function () {
                        service.closeAlert(this);
                    }, timeout);
                }
            },
            closeAlert: function (alert) {
                return this.closeAlertIdx($rootScope.alerts.indexOf(alert));
            },
            closeAlertIdx: function (index) {
                return $rootScope.alerts.splice(index, 1);
            }
        };

        return service;
    }
]);


