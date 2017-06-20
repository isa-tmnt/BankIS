var app = angular.module("CAApp", []);
app.controller("CACtrl", CACtrl);
CACtrl.$inject = ["$http"];

function CACtrl($http) {
    var self = this;
    
    self.tab = 5;
    self.isLogedIn = false;
    self.ca = false;
    self.ss = true;
    self.found = false;
    
    if(localStorage.getItem("authEmail") != null && localStorage.getItem("authEmail") != "null") {
    	self.isLogedIn = true;
    	self.tab = 1;
    }
    
    self.login = function() {
        var data = {
            email: self.uemail,
            password: self.password
        }

        $http({
            method: 'POST',
            url: '/api/login',
            data: data
        }).then(function successCallback(response) {
        	if (response.status >= 400 && response.status < 500){
                toastr.error('Invalid email/password.')
                return;
            }
        	
        	self.tab = 1;
        	toastr.success('You are logged in.');
        	
        	localStorage.setItem("authEmail", response.data.email);
        	localStorage.setItem("csrfToken", response.headers('CsrfToken'));
        	self.isLogedIn = true;
        	
        }, function errorCallback(response) {
        	toastr.error('Invalid email/password.');
        });
    }
    
    self.logout = function() {
    	
    	$http({
            method: 'GET',
            url: '/api/logout'
        }).then(function successCallback(response) {
        	
        	self.tab = 5;
        	toastr.success('You logged out.');
        	
        	localStorage.setItem("authEmail", null);
        	localStorage.setItem("csrfToken", null);
        	self.isLogedIn = false;
        	
        	self.uemail = "";
            self.password = "";
        	
        }, function errorCallback(response) {
        	toastr.error('Logout error.');
        });
    }

    self.genKS = function() {
        var data = {
            alias: self.ksAlias,
            password: self.ksPassword
        }
        
        $http({
            method: 'POST',
            url: '/api/keystore',
            data: data,
            headers: {
            	"CsrfToken": localStorage.getItem("csrfToken"),
                "AuthEmail": localStorage.getItem("authEmail")
            }
        }).then(function successCallback(response) {
        	
        	
            toastr.success("KeyStore generated.");
        }, function errorCallback(response) {
        	if (response.status == 403 || response.status == 401) { //forbiden or unauthorized
                toastr.info("No permission for this action, TODO for developers, user shouldn't see this action available.");
            } else {
            	toastr.error("KeyStore wasn't generated!");
            }
        });
    }

    self.genCA = function() {
        var data = {
            alias: self.alias,
            keyAlias: self.keyAlias,
            commonName: self.cn,
            surname: self.surname,
            givenName: self.givenname,
            organizationUnit: self.ou,
            organizationName: self.o,
            country: self.c,
            email: self.email,
            ca: self.ca,
            selfSigned: self.ss,
            issuer: "",
            issuerKeyStore: {
                alias: "",
                password: ""
            },
            keyStore: {
                alias: self.ksal,
                password: self.kspw
            }
        }

        if(!self.ss) {
            data.issuer = self.issuer;
            data.issuerKeyStore.alias = self.isksal;
            data.issuerKeyStore.password = self.iskspw;
        }

        //alert(data.issuer);
        
        $http({
            method:'POST', 
            url:'/api/certificates',
            data: data,
            headers: {
            	"CsrfToken": localStorage.getItem("csrfToken"),
                "AuthEmail": localStorage.getItem("authEmail")
            }
        }).then(function mySucces(response) {
            toastr.success("Certificate generated.");
        }, function myError(response) {
        	if (response.status == 403 || response.status == 401) { //forbiden or unauthorized
                toastr.info("No permission for this action, TODO for developers, user shouldn't see this action available.");
            } else {
            	toastr.error("Certificate wasn't generated!");
            }
        });
    }

    self.getCA = function() {
        var data = {
            alias: self.getKSA,
            password: self.getKSP
        }

        $http({
            method:'POST', 
            url:'/api/certificates/' + self.getA,
            data: data,
            headers: {
            	"CsrfToken": localStorage.getItem("csrfToken"),
                "AuthEmail": localStorage.getItem("authEmail")
            }
        }).then(function mySucces(response) {
            self.dataCA = response.data;
            self.found = true;
            self.validFrom = new Date(self.dataCA.startDate).toLocaleDateString();
            self.validTo = new Date(self.dataCA.endDate).toLocaleDateString();
            toastr.success("Certificate found.");
        }, function myError(response) {
            self.found = false;
            if (response.status == 403 || response.status == 401) { //forbiden or unauthorized
                toastr.info("No permission for this action, TODO for developers, user shouldn't see this action available.");
            } else {
            	toastr.error("Certificate doesn't exist!");
            }
        });
    }

    self.exportCA = function() {
        var data = {
            alias: self.getKSA,
            password: self.getKSP
        }

        $http({
            method:'POST', 
            url:'/api/certificates/export/' + self.getA,
            data: data,
            headers: {
            	"CsrfToken": localStorage.getItem("csrfToken"),
                "AuthEmail": localStorage.getItem("authEmail")
            }
        }).then(function mySucces(response) {
            toastr.success("Certificate exported.");
        }, function myError(response) {
        	if (response.status == 403 || response.status == 401) { //forbiden or unauthorized
                toastr.info("No permission for this action, TODO for developers, user shouldn't see this action available.");
            } else {
            	toastr.error("Certificate wasn't exported!");
            }
        });
    }

    self.setTab = function(newTab) {
      self.tab = newTab;
    };

    self.isSet = function(tabNum) {
      return self.tab === tabNum;
    };
}