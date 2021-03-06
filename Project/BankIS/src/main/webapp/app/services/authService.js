app.factory('authService', ['$http', function ($http) {


    var logedUser = null;
    if (localStorage.getItem('basicAuthEmail') != "null" && localStorage.getItem('basicAuthEmail') != null){ //note that null is in "", because localStorage saves only stirngs i guess, idk, but its string "null" if no user pre-authenticated
        logedUser = {
            email: localStorage.getItem("basicAuthEmail")
        }
        appConfig.config.headers.CsrfToken = localStorage.getItem("csrfToken");
        appConfig.config.headers.AuthEmail = localStorage.getItem("basicAuthEmail");
        appConfig.config.headers.BankId = localStorage.getItem("bankId");
        //$http.defaults.headers.common.Authorization = 'Basic ' + localStorage.getItem('basicAuth');
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
                        if(response.status == 423){
                            toastr.info('3 times failure, wait 5 mins for next try')
                            return;
                        }else{
                            toastr.error('Invalid email/password.')
                            return;
                        }
                    }
                    toastr.success('You are logged in.')
                    //var btoaED = btoa(email + ":" + password);
                    //localStorage.setItem("basicAuth", btoaED);
                    localStorage.setItem("basicAuthEmail", email);
                    //$http.defaults.headers.common.Authorization = 'Basic ' + btoaED;
                    logedUser = { email: email }
                    raiseOnLoginCallbacks(logedUser);
                    location.href = "/#!/";
                    
                    //alert(response.headers('X-CSRF-TOKEN'));
                    localStorage.setItem("csrfToken", response.headers('CsrfToken'));
                    //alert(response.data.bank.id);
                    localStorage.setItem("bankId", response.data.bank.id);
                    
                    //alert(localStorage.getItem("X-CSRF-TOKEN"));
                    appConfig.config.headers.CsrfToken = localStorage.getItem("csrfToken");
                    appConfig.config.headers.AuthEmail = localStorage.getItem("basicAuthEmail");
                    appConfig.config.headers.BankId = localStorage.getItem("bankId");
                    //alert(appConfig.config.headers.csrf);
                }, function error() {
                    toastr.error('Invalid email/password.')
                })
        },

        doLogout: function(){
        	$http.get(appConfig.apiUrl + 'logout', appConfig.config).then(function successCallback(response) {
        		//localStorage.setItem("basicAuth", null)
                localStorage.setItem("basicAuthEmail", null)
                localStorage.setItem("csrfToken", null);
                localStorage.setItem("bankId", 0);
                logedUser = null;
                //$http.defaults.headers.common.Authorization = null;
                appConfig.config.headers.CsrfToken = null;
                appConfig.config.headers.AuthEmail = null;
                appConfig.config.headers.BankId = 0;
                raiseOnLogoutCallbacks();
            });
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