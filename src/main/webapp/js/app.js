/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app = angular.module('myApp', [
    'ngRoute',
    'myApp.services',
    'myApp.directives',
    'myApp.controllers',
    'restangular',
    'ui.bootstrap'])
        .config(['$routeProvider', function ($routeProvider, $httpProvider) {
                $routeProvider.when('/home', {templateUrl: 'partials/home.html'});
                $routeProvider.when('/login', {templateUrl: 'partials/login.html', controller: 'LoginController'});
                $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: 'CustomerController'});
                $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: 'UserController'});
                $routeProvider.when('/chart', {templateUrl: 'partials/chart.html', controller: 'ChartController'});
                $routeProvider.when('/chart2', {templateUrl: 'partials/chart2.html', controller: 'PieChartController'});
                $routeProvider.when('/secure', {
                    templateUrl: 'partials/secure.html',
                    controller: 'SecureController',
                    resolve: {
                        auth: ['$q', 'AuthService', function ($q, AuthService) {
                                var userInfo = AuthService.isAuthenticated();
                                if (userInfo) {
                                    return $q.when(userInfo);
                                } else {
                                    return $q.reject(({authenticated: false}));
                                }
                            }]
                    }
                });

                $routeProvider.otherwise({redirectTo: '/login'});
            }])
        .config(function (RestangularProvider) {
            RestangularProvider.setBaseUrl('http://localhost:8080');
            RestangularProvider.setDefaultHeaders({'Content-Type': 'application/json'});
        })
        .config(function ($httpProvider) {
            $httpProvider.interceptors.push('authHttpRequestInterceptor');
        });

app.run(['$rootScope', '$location', 'AuthService', function ($rootScope, $location, AuthService) {
        $rootScope.session = AuthService;
        $rootScope.$on("$routeChangeSuccess", function (authData) {
//            console.log('RoutChange: ' + authData);
        });

        $rootScope.$on("$routeChangeError", function (event, current, previous, eventObj) {
            if (eventObj.authenticated === false) {
                console.log('routeChangeError: authenticated');
                $location.path("/login");
            }
        });
    }]);

app.constant('AUTH_EVENTS', {
    loginSuccess: 'auth-login-success',
    loginFailed: 'auth-login-failed',
    logoutSuccess: 'auth-logout-success',
    sessionTimeout: 'auth-session-timeout',
    notAuthenticated: 'auth-not-authenticated',
    notAuthorized: 'auth-not-authorized'
});

app.constant('USER_ROLES', {
    all: '*',
    admin: 'admin',
    editor: 'editor',
    guest: 'guest'
});
