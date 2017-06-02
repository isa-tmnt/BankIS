app.factory('authService', ['$http', function ($http) {


    var logedUser = null;
    if (localStorage.getItem('basicAuth') != "null" && localStorage.getItem('basicAuth') != null){ //note that null is in "", because localStorage saves only stirngs i guess, idk, but its string "null" if no user pre-authenticated
        logedUser = {
            email: localStorage.getItem("basicAuthEmail")
        }
        $http.defaults.headers.common.Authorization = 'Basic ' + localStorage.getItem('basicAuth');
    }

    var onLoginCallbacks = [];
    var onLogoutCallbacks = [];

    var raiseOnLoginCallbacks = function (user) {
        onLoginCallbacks.forEach(c => {
            try {
                c(user)
            } catch (e) { }
        })
    }

    var raiseOnLogoutCallbacks = function () {
        onLogoutCallbacks.forEach(c => {
            try {
                c()
            } catch (e) { }
        })
    }


    return {
        doLogin: function (email, password) {
            $http.post(appConfig.apiUrl + 'login', { email: email, password: password }).then(
                function successCallback(response) {
                    if (response.status >= 400 && response.status < 500){
                        toastr.error('Invalid email/password.')
                        return;
                    }
                    toastr.success('You are logged in.')
                    var btoaED = btoa(email + ":" + password);
                    localStorage.setItem("basicAuth", btoaED)
                    localStorage.setItem("basicAuthEmail", email)
                    $http.defaults.headers.common.Authorization = 'Basic ' + btoaED;
                    logedUser = { email: email, password: password }
                    raiseOnLoginCallbacks(logedUser);
                    location.href = "/#!/";
                }, function error() {
                    toastr.error('Invalid email/password.')
                })
        },

        doLogout: function(){
            localStorage.setItem("basicAuth", null)
            localStorage.setItem("basicAuthEmail", null)
            logedUser = null
            $http.defaults.headers.common.Authorization = null;
            raiseOnLogoutCallbacks();
        },

        onLogin: function (callback) {
            if (logedUser)
                callback(logedUser);
            onLoginCallbacks.push(callback);
        },

        onLogout: function (callback) {
            if (!logedUser)
                callback();
            onLogoutCallbacks.push(callback);
        }
    };
}]);