'use strict';

angular.module('TomEEAngular')
        .controller('PersonCtrl', function ($scope, $location, personService, AlertService, modalService) {
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

            $scope.confirmDelete = function (person) {
                var modalOptions = {
                    closeButtonText: 'Cancel',
                    actionButtonText: 'Delete Person',
                    headerText: 'Delete ' + person.name + '?',
                    bodyText: 'Are you sure you want to delete this person?'
                };

                modalService.showModal({}, modalOptions).then(function (result) {
                    personService.deletePerson(person).then(function (data) {
                        AlertService.add('success', 'Person delete OK');
                        $scope.persons = _.without($scope.persons, person);
                        $location.path('/persons');
                    }).catch(function (status) {
                        AlertService.add('danger', 'Person delete failed: ', status.status);
                    });
                });
            };

            $scope.ok = function () {
                $scope.showModal = false;
            };

            $scope.cancel = function () {
                $scope.showModal = false;
            };

        });

