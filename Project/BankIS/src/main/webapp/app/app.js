'use strict';

// Declare app level module which depends on views, and components
var app = angular.module('myApp', [
    'ngRoute',
]).
    config(['$locationProvider', '$routeProvider', '$httpProvider', function ($locationProvider, $routeProvider, $httpProvider) {

        $httpProvider.interceptors.push(function () {
            return {
                'request': function (request) {
                    return request;
                },

                'responseError': function (response) {
                    if (response.status >= 400 && response.status < 500){ //unauthorized
                      //  location.href = "/#!/login";
                        toastr.info("No permission for this action, TODO for developers, user shouldnt see this action available");
                    }
                   if(response.status >= 500){
                       toastr.error("Server error, status 500");
                       console.log(response);
                   }

                    return response;
                }
            };
        });

        $locationProvider.hashPrefix('!');
        $routeProvider.when("/login", {
            template: "<login></login>",
        }).when("/otvaranjeRacuna", {
            template: "<otvaranje-racuna></otvaranje-racuna>",
        }).when("/bankAccounts", {
            template: "<bank-accounts></bank-accounts>",
        }).when("/currensies", {
            template: "<currensies></currensies>",
        }).when("/closingAccounts", {
            template: "<closing-accounts></closing-accounts>",
        }).when("/bankMessages", {
            template: "<bank-messages></bank-messages>",
        }).when("/clientDetails", {
            template: "<client-details></client-details>",
        }).when("/legalPersonDetails", {
            template: "<legal-person-details></legal-person-details>",
        }).when("/banks", {
            template: "<banks></banks>",
        }).when("/interbankTransfers", {
            template: "<interbank-transfers></interbank-transfers>",
        }).when("/bankOrders", {
            template: "<bank-orders></bank-orders>",
        }).when("/transferItems", {
            template: "<transfer-items></transfer-items>",
        }).when("/dailyAccountBalances", {
            template: "<daily-account-balances></daily-account-balances>",
        }).when("/workTypes", {
            template: "<work-types></work-types>",
        }).when("/", {
            template: "<welcome-page></welcome-page>",
        });
        $routeProvider.otherwise({ redirectTo: '/' });
    }]);



app.run(function ($http) {
   /* $http.interceptors.push(function ($q, dependency1, dependency2) {
        return {
            'request': function (config) {
                console.log("req");
            },

            'response': function (response) {
                console.log("resp");
            }
        };
    });*/
});






var appConfig = {
    apiUrl: "http://localhost:10011/api/"
};