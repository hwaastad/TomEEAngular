'use strict';

angular.module('TomEEAngular')
        .controller('PersonCtrl', function ($scope, $location, personService, AlertService, $modal) {
            $scope.persons = [];
            $scope.person = {};

            personService.getPersons().then(function (response) {
                $scope.persons = response.data;
            });

            $scope.createPerson = function (person) {
                personService.savePerson(person).then(function (data) {
                    AlertService.add('success', 'Person create OK');
                    $location.path("/persons");
                }).catch(function (status) {
                    AlertService.add('danger', 'Person save failed: ', status.status);
                });
            };

            $scope.deletePerson = function (person) {
                personService.deletePerson(person).then(function (data) {
                    AlertService.add('success', 'Person delete OK');
                    $scope.persons = _.without($scope.persons, person);
                    $location.path("/persons");
                }).catch(function (status) {
                    AlertService.add('danger', 'Person delete failed: ', status.status);
                });
            };

            $scope.confirmDelete = function (size) {
                var modalInstance = $modal.open({
                    templateUrl: 'views/person/confirmDeletePerson.html',
                    controller: 'PersonCtrl',
                    size: size,
                    resolve: {
                        items: function () {
                            return $scope.items;
                        }
                    }
                });
            };

            $scope.ok = function () {
                $modalInstance.close($scope.selected.item);
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

        });

