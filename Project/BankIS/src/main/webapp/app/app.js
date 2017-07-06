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

                'responseError': function (response, b) {
                    if (response.status == 403 || response.status == 401) { //forbiden or unauthorized
                        //  location.href = "/#!/login";
                        toastr.info("No permission for this action, TODO for developers, user shouldnt see this action available");
                    }
                    if (response.status == 400) {
                        if (response.data.cause)
                            toastr.info(response.data.cause);
                    }
                    if (response.status >= 500) {
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
        }).when("/bankAccounts/:filterId/:filterProperty", {
            template: "<bank-accounts></bank-accounts>",
        }).when("/bankAccounts", {
            template: "<bank-accounts></bank-accounts>",
        }).when("/currensies", {
            template: "<currensies></currensies>",
        }).when("/currensies/:filterId/:filterProperty", {
            template: "<currensies></currensies>",
        }).when("/closingAccounts/:filterId/:filterProperty", {
            template: "<closing-accounts></closing-accounts>",
        }).when("/closingAccounts", {
            template: "<closing-accounts></closing-accounts>",
        }).when("/bankMessages", {
            template: "<bank-messages></bank-messages>",
        }).when("/bankMessages/:filterId/:filterProperty", {
            template: "<bank-messages></bank-messages>",
        }).when("/clientDetails", {
            template: "<client-details></client-details>",
        }).when("/clientDetails/:filterId/:filterProperty", {
            template: "<client-details></client-details>",
        }).when("/legalPersonDetails", {
            template: "<legal-person-details></legal-person-details>",
        }).when("/legalPersonDetails/:filterId/:filterProperty", {
            template: "<legal-person-details></legal-person-details>",
        }).when("/banks", {
            template: "<banks></banks>",
        }).when("/banks/:filterId/:filterProperty", {
            template: "<banks></banks>",
        }).when("/interbankTransfers", {
            template: "<interbank-transfers></interbank-transfers>",
        }).when("/interbankTransfers/:filterId/:filterProperty", {
            template: "<interbank-transfers></interbank-transfers>",
        }).when("/bankOrders", {
            template: "<bank-orders></bank-orders>",
        }).when("/bankOrders/:filterId/:filterProperty", {
            template: "<bank-orders></bank-orders>",
        }).when("/transferItems", {
            template: "<transfer-items></transfer-items>",
        }).when("/transferItems/:filterId/:filterProperty", {
            template: "<transfer-items></transfer-items>",
        }).when("/dailyAccountBalances", {
            template: "<daily-account-balances></daily-account-balances>",
        }).when("/dailyAccountBalances/:filterId/:filterProperty", {
            template: "<daily-account-balances></daily-account-balances>",
        }).when("/workTypes", {
            template: "<work-types></work-types>",
        }).when("/workTypes/:filterId/:filterProperty", {
            template: "<work-types></work-types>",
        }).when("/users", {
            template: "<users></users>",
        }).when("/userSettings", {
            template: "<user-settings></user-settings>",
        }).when("/roles", {
            template: "<roles></roles>",
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
    apiUrl: "https://localhost:10011/api/",
    config: {
        headers: {
            "CsrfToken": localStorage.getItem("X-CSRF-TOKEN"),
            "AuthEmail": localStorage.getItem("basicAuthEmail"),
            "BankId": localStorage.getItem("bankId")
        }
    }
};