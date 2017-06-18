'use strict';

app.component('userSettings', {
    templateUrl: 'app/userSettings/userSettings.html',
    controller: ['$scope', 'authService','$http', function LoginController($scope, authService, $http) {
        $scope.showValidatons = true;

     /*   $scope.wrongOldPassword = true;
        $scope.chars8long = true;
        $scope.oneLowercase = true;
        $scope.oneUppercase = true;
        $scope.oneNumber = true;
        $scope.passwordsMatch = true;*/
        $scope.oldPassword = "";
        $scope.newPassword = "";
        $scope.repeatedPassword = ""

        $scope.logedUser = null;

        authService.onLogin(user => $scope.logedUser = user);

        $scope.changePassword = function(){
            $scope.showValidatons = !$scope.showValidatons;
            var data = { oldPassword: $scope.oldPassword, newPassword: $scope.newPassword, repeatedPassword: $scope.repeatedPassword}
            $http.put(appConfig.apiUrl + 'users', data, appConfig.config).then(function successCallback(response) {
                if(response.status == 200 || response.data.success)
                    toastr.success("changed successfuly")
                else{
                    if(response && response.data && response.data.message)
                        toastr.info(response.data.message)
                    else{
                        toastr.error("unknown error :/ sry");
                        console.log(response);
                    }
                }
            }, function errorCallback(e){
                toastr.error(e);
                console.log(e);
            });

            
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