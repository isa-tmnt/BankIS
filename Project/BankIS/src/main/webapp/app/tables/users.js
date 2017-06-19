'use strict';

app.component('users', {
    templateUrl: 'app/commonTemplates/users.html',
    controller: ['$scope', '$http', '$attrs', '$rootScope', '$element', '$compile', function CtrlIndeed($scope, $http, $attrs, $rootScope, $element, $compile) {

        $scope.pass = "";
        $scope.rows = [];
        $scope.selected = {};
        $scope.editing = {};
        $scope.setSelected = function (row) {
            if ($attrs.iamdialog) {
                $rootScope.$broadcast('USER_SELECTED', row);

            }
            $scope.selected = row;
            $scope.editing = $.extend({}, row);
        }

        $scope.header = [
            //{ label: "Id", code: "id", manatory: false, type: "text" },
            { label: "First Name", code: "firstName", manatory: false, type: "text" },
            { label: "Last Name", code: "lastName", manatory: false, type: "text" },
            { label: "Email", code: "email", manatory: false, type: "text"},
            //{ label: "Password", code: "password", manatory: false, type: "text" }, 
            { label: "Bank", code: "bank", type: "text", isReference: true, openDialog: () => $scope.openDialog('banks') },
        ];

        $scope.allRoles = [];

        $http.get(appConfig.apiUrl + 'roles', appConfig.config).then(function successCallback(response) {
            $scope.allRoles = response.data;
        });

        $scope.roleClickkk = function(role){
            if (!$scope.editing) $scope.editing = {};
            if (!$scope.editing.roles) $scope.editing.roles = [];


                var hasRole = false;
                var index;
                for (var indx in $scope.editing.roles) {                 
                    if (role.name == $scope.editing.roles[indx].name){
                        hasRole = true;
                        index = indx;
                    }
                }

                if(hasRole){
                    $scope.editing.roles.splice(index,1);
                }else{
                    $scope.editing.roles.push(role);
                }

        }

        $scope.isRoleChoosen = function(role){
            if(!role) return;
            if($scope.editing && $scope.editing.roles){
                for(var indx in $scope.editing.roles){
                    if(role.name == $scope.editing.roles[indx].name)
                        return true;
                }
                return false;
            }
        }
      
        $http.get(appConfig.apiUrl + 'users', appConfig.config).then(function successCallback(response) {
            $scope.header.filter(h => h.type == "date").forEach(h => response.data.forEach(row => row[h.code] = new Date(row[h.code])));  //conver strings to dates where needed
            $scope.rows = response.data;
        });

        $scope.allowAdd = true; $scope.allowEdit = false; $scope.allowRemove = true;
        $scope.doAdd = function () {
            if ($scope.editing.id) $scope.editing.id = null;
            var data = $.extend({}, $scope.editing);
            data.password = $('#passwordThing').val();//$scope.pass; scope.pass is bugging :/ sad sad
            $http.post(appConfig.apiUrl + 'users', data, appConfig.config).then(function successCallback(response) {
                var row = response.data;
                if (row && response.status < 300) {
                    $scope.header.filter(h => h.type == "date").forEach(h => row[h.code] = new Date(row[h.code]));  //conver strings to dates where needed
                    $scope.rows.push(row);

                    toastr.success('Added successfuly.')
                }else{
                    toastr.info('Email registred already.')
                }
            }, function err(e) {
                console.log(e);
                if (e && e.data && e.data.cause)
                    toastr.error(e.data.cause)
                else
                    toastr.error("Can't add sorry.")
            });
        }

        $scope.doRemove = function () {
            if ($scope.selected.email) {
                $http.delete(appConfig.apiUrl + 'users/' + encodeURIComponent($scope.selected.email) + ".", appConfig.config).then(function successCallback(response) {

                    if (response.status < 300) {
                        $scope.rows.splice($scope.rows.indexOf($scope.selected), 1);
                        toastr.success('Removed successfuly.');
                    }

                }, function err(e) {
                    toastr.error("Can't remove sorry.");
                });
            } else {
                toastr.info('Select row first.');
            }
        }

      
        $scope.iamdialog = $attrs.iamdialog == 'true';
        
        $scope.openDialog = function (tagName, id) {
            if (id) {
                $scope.dialog = $compile(
                    "<" + tagName + ' ' + "filterid='" + id + "'  iamdialog='true'></" + tagName + ">"
                )($scope);
            } else {
                $scope.dialog = $compile(
                    "<" + tagName + " iamdialog='true'></" + tagName + ">"
                )($scope);
            }

            $element.append($scope.dialog);
        }
        
        $scope.zoomSingleLine = function (code, row) {
            if (code == 'bank') {
                $scope.openDialog('banks', row[code].id);
            }
        };

        $rootScope.$on('BANK_SELECTED', function (event, row) {
            if (row['id'])
                $scope.editing['bank'] = row;
            if ($scope.dialog) $scope.dialog.remove();
        });

        //-------------------------------------> filtering, ordering, pagination <----------------------------------------------

        $scope.filters = {};
        $scope.filterId = $attrs.filterid;

        $scope.showRow = function (row) {
            if ($scope.filterId && $scope.filterId.toString() != row['id'].toString())  //if zoom on one entity
                return false;
            for (var code in $scope.filters) {

                if (row[code] && $scope.filters[code] && row[code].toString().indexOf($scope.filters[code].toString()) < 0)
                    return false;
            }
            return true;
        }
        $scope.ordering = 'id';
        $scope.setOrdering = function (ordering) {
            if ($scope.ordering == ordering)
                $scope.ordering = '-' + ordering;
            else
                $scope.ordering = ordering;
        }


        $scope.pageRows = 7;
        $scope.minShow = 0;
        $scope.maxShow = $scope.pageRows;
        $scope.getPageCount = function () {
            return new Array(parseInt($scope.rows.length / $scope.pageRows + 1));
        }
        $scope.changeMinMaxShow = function ($index) {
            $scope.minShow = $index * $scope.pageRows;
            $scope.maxShow = ($index + 1) * $scope.pageRows;
        }

    }]
});