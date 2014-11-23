'use strict';

/**
 * @ngdoc overview
 * @name swFrontApp
 * @description
 * # swFrontApp
 *
 * Main module of the application.
 */
angular.module('TomEEAngular', [
    'ui.bootstrap',
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ui.router',
    'ngSanitize',
    'ngTouch',
    'dynform',
    'schemaForm',
    'ngTable'
])

        .factory('authInterceptor', function ($q, $location) {
            return {
                request: function (config) {
                    config.headers = config.headers || {};
                    if (localStorage.authData) {
                        var authData = angular.fromJson(localStorage.authData);
                        console.log('Setting header: auth-token:' + authData.authToken + " auth-id: " + authData.authId);
                        config.headers['auth-token'] = authData.authToken;
                        config.headers['auth-id'] = authData.authId;
                    }
                    return config;
                },
                responseError: function (response) {
                    console.log('authInterceptor error: ');
                    if (response.status === 401) {
                        $location.path('/login');
                    }
                    return $q.reject(response);
                }
            };
        })
        .constant('USER_ROLES', {
            all: '*',
            admin: 'admin',
            editor: 'editor',
            guest: 'guest'
        })
        .constant('AUTH_EVENTS', {
            loginSuccess: 'auth-login-success',
            loginFailed: 'auth-login-failed',
            logoutSuccess: 'auth-logout-success',
            sessionTimeout: 'auth-session-timeout',
            notAuthenticated: 'auth-not-authenticated',
            notAuthorized: 'auth-not-authorized'
        })

        .config(function ($httpProvider, $stateProvider) {
            $httpProvider.interceptors.push('authInterceptor');
            // CORS options
            $httpProvider.defaults.useXDomain = true;
            delete $httpProvider.defaults.headers.common['X-Requested-With'];
        })
        .config(function ($stateProvider, USER_ROLES, $urlRouterProvider) {

            $urlRouterProvider.otherwise('/home');

            $stateProvider
                    .state('anon', {
                        abstract: true,
                        template: "<ui-view/>",
                        data: {
                            access: [USER_ROLES.editor, USER_ROLES.admin, USER_ROLES.guest]
                        }
                    })
                    .state('anon.charts', {
                        url: '/charts',
                        templateUrl: 'views/charts.html',
                        controller: 'ChartsCtrl'
                    })
                    .state('anon.piechart', {
                        url: '/piecharts',
                        templateUrl: 'views/piechart.html',
                        controller: 'PieChartsCtrl'
                    })
                    .state('anon.persons', {
                        url: '/persons',
                        templateUrl: 'views/person/persons.html',
                        controller: 'PersonCtrl'
                    })
                    .state('anon.personsnew', {
                        url: '/persons/new',
                        templateUrl: 'views/person/person_new.html',
                        controller: 'PersonCtrl'
                    })
                    .state('anon.home', {
                        url: '/home',
                        templateUrl: 'views/main.html',
                        controller: 'MainCtrl'
                    })
                    .state('anon.about', {
                        url: '/about',
                        templateUrl: 'views/about.html',
                        controller: 'AboutCtrl'
                    })
                    .state('anon.dynamicform', {
                            url: '/dynamicform',
                            templateUrl: 'views/dynamicform.html',
                            controller: 'DynamicFormCtrl'
                        })
                    .state('anon.login', {
                        url: '/login',
                        templateUrl: 'views/login.html',
                        controller: 'LoginCtrl'
                    })
                    .state('admin', {
                        url: '/admin',
                        templateUrl: 'views/admin.html',
                        controller: 'AdminCtrl',
                        data: {
                            access: [USER_ROLES.editor]
                        }
                    });
        })


        .run(function ($rootScope, auth, $state, AlertService) {
            $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
                auth.authorize(toState.data.access);
                if (!('data' in toState) || !('access' in toState.data)) {
                    AlertService.add('danger', 'Access undefined for this state');
                    event.preventDefault();
                }
                else if (!auth.authorize(toState.data.access)) {

                    event.preventDefault();
                    if (!auth.isLoggedIn()) {
                        AlertService.add('danger', "Not logged in and not authroized, going to login");
                        $state.go('anon.login');
                    } else {
                        AlertService.add('danger', "Seems like you tried accessing a route you don't have access to");
                    }
                }
            }
            );
        })
        ;
