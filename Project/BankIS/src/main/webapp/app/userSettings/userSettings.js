'use strict';

app.component('userSettings', {
    templateUrl: 'app/userSettings/userSettings.html',
    controller: ['$scope', 'authService', function LoginController($scope, authService) {
        $scope.showValidatons = true;

     /*   $scope.wrongOldPassword = true;
        $scope.chars8long = true;
        $scope.oneLowercase = true;
        $scope.oneUppercase = true;
        $scope.oneNumber = true;
        $scope.passwordsMatch = true;*/
        $scope.oldPassword = "";
        $scope.newPassword = "";
        $scope.repatedPassword = ""

        $scope.changePassword = function(){
            $scope.showValidatons = !$scope.showValidatons;
        }

        $scope.hasLowerCase = function(str) {
            if (!str) return false;
            return (/[a-z]/.test(str));
        }


        $scope.hasUpperCase = function (str) {
            if(!str) return false;
            return (/[A-Z]/.test(str));
        }

        $scope.hasNumber = function (str) {
            if (!str) return false;
            return (/[0-9]/.test(str));
        }
    }]
});