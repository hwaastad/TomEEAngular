/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module('TomEEAngular')
        .controller('DynamicFormCtrl', ['$scope', function ($scope) {
                $scope.processForm = function () {
                    console.log('submitting data....');
                };
                $scope.schema = {
                    "type": "object",
                    "title": "Comment",
                    "properties": {
                        "name": {
                            "title": "Name",
                            "type": "string"
                        },
                        "email": {
                            "title": "Email",
                            "type": "string",
                            "pattern": "^\\S+@\\S+$",
                            "description": "Email will be used for evil."
                        },
                        "comment": {
                            "title": "Comment",
                            "type": "string",
                            "maxLength": 20,
                            "validationMessage": "Don't be greedy!"
                        }
                    },
                    "required": [
                        "name",
                        "email",
                        "comment"
                    ]
                };
                $scope.form = [
                    {
                        "key": "name",
                        "type": "text",
                        "placeholder": "Write your nanme"
                    },
                    "email",
                    {
                        "key": "comment",
                        "type": "textarea",
                        "placeholder": "Make a comment"
                    },
                    {
                        "type": "submit",
                        "style": "btn-info",
                        "title": "OK"
                    }
                ];
                $scope.model = {};
            }
        ]);

