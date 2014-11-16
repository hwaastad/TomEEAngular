'use strict';

/**
 * @ngdoc service
 * @name swFrontApp.AlertService
 * @description
 * # AlertService
 * Factory in the swFrontApp.
 */
angular.module('TomEEAngular').factory('AlertService', function ($timeout, $rootScope) {
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
});
