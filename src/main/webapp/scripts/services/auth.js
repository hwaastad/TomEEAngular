'use strict';

/**
 * @ngdoc service
 * @name swFrontApp.auth
 * @description
 * # auth
 * Factory in the swFrontApp.
 */
angular.module('TomEEAngular')
        .factory('auth', function ($q, $http, $rootScope, $location, AlertService, USER_ROLES) {

            var authData = undefined;
            var currentUser = angular.fromJson(localStorage.getItem('authData')) || {authId: '', authPermission: USER_ROLES.guest, authToken: ''};

            function changeUser(user) {
                angular.extend(currentUser, user);
            }

            // Public API here
            return {
                login: function (user) {
                    console.log('Logging in...');
                    var defer = $q.defer();
                    // return $http.post('http://localhost:8080/auth',user);
                    $http.post('http://localhost:8080/auth', user)
                            .success(function (data) {
                                changeUser(data);
                                authData = data;
                                localStorage.setItem('authData', angular.toJson(authData));
                                defer.resolve(authData);
                                AlertService.add('success', 'Login OK');
                            })
                            .error(function () {
                                defer.reject();
                            });
                    return defer.promise;

                },
                logout: function () {
                    $http.delete('http://localhost:8080/auth')
                            .success(function (data) {
                                localStorage.removeItem('authData');
                                changeUser({
                                    authId: '',
                                    authToken: '',
                                    authPermission: USER_ROLES.guest
                                });
                                AlertService.add('success', 'Logout OK');
                            })
                            .error(function (status) {
                                AlertService.add('danger', 'Logout Failed', status.data);
                            });
                },
                isLoggedIn: function () {
                    return (localStorage.getItem('authData')) ? true : false;
                },
                authorize: function (accessLevel, role) {
                    if (!angular.isArray(accessLevel)) {
                        accessLevel = [accessLevel];
                    }
                    if (role === undefined) {
                        role = currentUser.authPermission;
                    }
                    var ok = _.contains(accessLevel, role);
                    console.log('Authorizing role: ' + role + ' and accesslevel: ' + accessLevel + ' Status: ' + ok);
                    return ok;
                },
                getCurrentUser: function () {
                    return currentUser;
                }
            };
            user : currentUser;
        });
