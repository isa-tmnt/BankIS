var app = angular.module("CAApp", []);
app.controller("CACtrl", CACtrl);
CACtrl.$inject = ["$http"];

function CACtrl($http) {
    var self = this;

    self.ss = true;
    self.found = false;

    self.genKS = function() {
        var data = {
            alias: self.ksAlias,
            password: self.ksPassword
        }

        $http({
            method: 'POST',
            url: '/api/keystore',
            data: data
        }).then(function successCallback(response) {
            toastr.success("KeyStore generated.");
        }, function errorCallback(response) {
            toastr.error("KeyStore wasn't generated!");
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
            startDate: self.start,
            endDate: self.end,
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
            data: data
        }).then(function mySucces(response) {
            toastr.success("Certificate generated.");
        }, function myError(response) {
            toastr.error("Certificate wasn't generated!");
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
            data: data
        }).then(function mySucces(response) {
            self.dataCA = response.data;
            self.found = true;
            self.validFrom = new Date(self.dataCA.startDate).toLocaleDateString();
            self.validTo = new Date(self.dataCA.endDate).toLocaleDateString();
            toastr.success("Certificate found.");
        }, function myError(response) {
            self.found = false;
            toastr.error("Certificate doesn't exist!");
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
            data: data
        }).then(function mySucces(response) {
            toastr.success("Certificate exported.");
        }, function myError(response) {
            toastr.error("Certificate wasn't exported!");
        });
    }
    
    self.tab = 1;

    self.setTab = function(newTab) {
      self.tab = newTab;
    };

    self.isSet = function(tabNum) {
      return self.tab === tabNum;
    };
}