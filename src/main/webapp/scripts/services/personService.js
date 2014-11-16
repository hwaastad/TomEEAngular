'use strict';

angular.module('TomEEAngular')
        .factory('personService', function ($q, $http, AlertService) {
            return {
                getPersons: function () {
                    console.log('Getting persons....');
                    return $http.get('http://localhost:8080/schema');
                },
                savePerson: function (person) {
                    console.log('Getting persons....');
                    return $http.post('http://localhost:8080/schema', person);
                },
                deletePerson: function (person) {
                    console.log('Deleting person...');
                    return $http.delete('http://localhost:8080/schema/' + person.id);
                }
            };
        });


