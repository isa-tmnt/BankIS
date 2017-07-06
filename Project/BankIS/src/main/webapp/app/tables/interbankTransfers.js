'use strict';

app.component('interbankTransfers', {
    templateUrl: 'app/commonTemplates/defaultTable.html',
    controller: ['$scope', '$http', '$attrs', '$rootScope', '$compile', '$element', '$routeParams', function InterbankTrCtrl($scope, $http, $attrs, $rootScope, $compile, $element, $routeParams) {

        $scope.rows = [];
        $scope.selected = {};
        $scope.editing = {};
        $scope.setSelected = function (row) {
            if ($attrs.iamdialog)
                $rootScope.$broadcast('INTERBANK_TRANSFER_SELECTED', row);
            $scope.selected = row;
            $scope.editing = $.extend({}, row);
        }

        $scope.header = [
            { label: "Id", code: "id", manatory: false, type: "number" },
            { label: "Transfer Date", code: "transferDate", manatory: false, type: "date" },
            { label: "Amount", code: "amount", manatory: false, type: "number" },
            { label: "Bank Message", code: "bankMessage", manatory: false, type: "text", isReference: true, openDialog: () => $scope.openDialog('bank-messages') },
         //    { label: "Sender bank", code: "senderBank", manatory: false, type: "text" },
            { label: "Recipient bank", code: "recipientBank", manatory: false, type: "text", isReference: true, openDialog: () => $scope.openDialog('banks') },
        ];

        $http.get(appConfig.apiUrl + 'interbankTransfers').then(function successCallback(response) {
            $scope.header.filter(h => h.type == "date").forEach(h => response.data.forEach(row => row[h.code] = new Date(row[h.code])));  //conver strings to dates where needed
            $scope.rows = response.data;
        });
        $scope.allowAdd = true; $scope.allowEdit = true; $scope.allowRemove = true;
        $scope.doAdd = function () {
            if ($scope.editing.id) $scope.editing.id = null;
            var data = $.extend({}, $scope.editing);
            $http.post(appConfig.apiUrl + 'interbankTransfers', data).then(function successCallback(response) {
                var row = response.data;
                if (row && response.status < 300) {
                    $scope.header.filter(h => h.type == "date").forEach(h => row[h.code] = new Date(row[h.code]));  //conver strings to dates where needed
                    $scope.rows.push(row);

                    toastr.success('Added successfuly.')
                }
            }, function err(e) {
                console.log(e);
                if (e && e.data && e.data.cause)
                    toastr.error(e.data.cause)
                else
                    toastr.error("Can't add sorry.")
            });
        }

        $scope.doEdit = function () {
            if ($scope.selected.id) {
                var data = $.extend({}, $scope.editing);
                console.log(data);
                $http.post(appConfig.apiUrl + 'interbankTransfers', data).then(function successCallback(response) {
                    var row = response.data;
                    if (row && response.status < 300) {
                        $scope.header.filter(h => h.type == "date").forEach(h => row[h.code] = new Date(row[h.code]));  //conver strings to dates where needed
                        $scope.rows.splice($scope.rows.indexOf($scope.selected), 1);
                        $scope.rows.push(row);
                        toastr.success('Edited successfuly.')
                    }
                }, function err(e) {
                    console.log(e);
                    if (e && e.data && e.data.cause)
                        toastr.error(e.data.cause)
                    else
                        toastr.error("Can't edit sorry.")
                });
            } else {
                toastr.info('Select row first.')
            }

        }

        $scope.doRemove = function () {
            if ($scope.selected.id) {
                $http.delete(appConfig.apiUrl + 'interbankTransfers/' + $scope.selected.id).then(function successCallback(response) {

                    if (response.status < 300) {
                        $scope.rows.splice($scope.rows.indexOf($scope.selected), 1);
                        toastr.success('Removed successfuly.')
                    }

                }, function err(e) {
                    toastr.error("Can't remove sorry.")
                });
            } else {
                toastr.info('Select row first.')
            }
        }

        


        $scope.iamdialog = $attrs.iamdialog == 'true';

        //-------------------------------------> zoom <--------------------------------------------------------------------------

        $scope.openDialog = function (tagName, id) {
            if (id) {
                $scope.dialog = $compile(
                    "<" + tagName + ' ' +  "filterid='"+id+"'  iamdialog='true'></" + tagName + ">"
                )($scope);
            } else {
                $scope.dialog = $compile(
                    "<" + tagName + " iamdialog='true'></" + tagName + ">"
                )($scope);
            }

            $element.append($scope.dialog);
        }

        $scope.zoomSingleLine = function (code, row) {
            if (code == 'bankMessage') {
               $scope.openDialog('bank-messages',row[code].id);
            }
            if (code == 'recipientBank') {
              $scope.openDialog('banks',row[code].id);
            }
        };

        $rootScope.$on('BANK_MESSAGE_SELECTED', function (event, row) {
            if (row['id']) $scope.editing['bankMessage'] = row['id']
            if($scope.dialog) $scope.dialog.remove();
        });
        $rootScope.$on('BANK_SELECTED', function (event, row) {
            if (row['id']) $scope.editing['recipientBank'] = row['id']
            if($scope.dialog) $scope.dialog.remove();
        });

        //-------------------------------------> filtering, ordering, pagination <----------------------------------------------


        $scope.nexts = [
            { label: "Transfer Items", link: "/#!/transferItems", filterProperty: "interbankTransfer" },
        ];

        $scope.filters = {};
        $scope.filterId = $attrs.filterid;
        if ($routeParams.filterId) {
            $scope.filters[$routeParams.filterProperty] = $routeParams.filterId;
        }

        $scope.showRow = function (row) {
            if ($scope.filterId && $scope.filterId.toString() != row['id'].toString())  //if zoom on one entity
                return false;
            for (var code in $scope.filters) {
                if (row[code] && $scope.filters[code]) {
                    if (typeof row[code] == 'object') {
                        if (row[code]['id'].toString().indexOf($scope.filters[code].toString()) < 0) {
                            return false;
                        }
                    } else if (row[code].toString().indexOf($scope.filters[code].toString()) < 0) {
                        return false;
                    }
                }
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