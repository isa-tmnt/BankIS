'use strict';

app.component('ourHeader', {
    templateUrl: 'app/header/header.html',
    controller: ['authService', '$scope', function HeaderController(authService, $scope) {
        
        $scope.logedUser = null;
             
        authService.onLogin(function(user){
            $scope.logedUser = user;
        })

        authService.onLogout(function () {
            $scope.logedUser = null;
        })


        $scope.doLogout = function(){
            authService.doLogout();
        }


    }]
});