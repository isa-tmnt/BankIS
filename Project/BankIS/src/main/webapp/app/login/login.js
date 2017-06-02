'use strict';

app.component('login', {
    templateUrl: 'app/login/login.html',
    controller: ['$scope', 'authService', function LoginController($scope, authService) {
        $scope.email = null;
        $scope.password = null;

        $scope.doLogin = function(){
            authService.doLogin($scope.email,$scope.password);
        }
       
    }]
});