'use strict';

app.component('closingAccounts', {
    templateUrl: 'app/commonTemplates/defaultTable.html',
    controller: ['$scope', '$http', '$attrs', '$rootScope', '$element', '$compile', '$routeParams', '$timeout', function ClosingAccountsCtrl($scope, $http, $attrs, $rootScope, $element, $compile, $routeParams, $timeout) {

        $scope.rows = [];
        $scope.selected = {};
        $scope.editing = {};
        $scope.setSelected = function (row) {
            if ($attrs.iamdialog) {
                $rootScope.$broadcast('CLOSING_ACCOUNT_SELECTED', row);

            }
            $scope.selected = row;
            $scope.editing = $.extend({}, row);
        }

        $scope.header = [
            { label: "Id", code: "id", manatory: false, type: "text" },
            { label: "Switch to an account", code: "switchToAnAccount", manatory: false, type: "text" },
            { label: "End date", code: "endDate", manatory: false, type: "date" },
            { label: "Bank Account", code: "bankAccount", manatory: false, type: "text", isReference: true, openDialog: () => { $scope.openDialog('bank-accounts'); } },
            { label: "Bank Order", code: "bankOrder", manatory: false, type: "text", isReference: true, openDialog: () => { $scope.openDialog('bank-orders'); } },
        ];

        $http.get('/api/closingAccounts.json').then(function successCallback(response) {
            $scope.header.filter(h => h.type == "date").forEach(h => response.data.forEach(row => row[h.code] = new Date(row[h.code])));  //conver strings to dates where needed
            $scope.rows = response.data;
        });

        $scope.allowAdd = true; $scope.allowEdit = true; $scope.allowRemove = true;
        $scope.doAdd = function () {
            if ($scope.editing.id) $scope.editing.id = null;
            $scope.rows.push(JSON.parse(JSON.stringify($scope.editing)));
        }

        $scope.doEdit = function () {
            $scope.rows.splice($scope.rows.indexOf($scope.selected), 1);
            $scope.rows.push($scope.editing);
        }

        $scope.doRemove = function () {
            $scope.rows.splice($scope.rows.indexOf($scope.selected), 1);
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
            if (code == 'bankAccount') {
               $scope.openDialog('bank-accounts',row[code]);
            }
            if (code == 'bankOrder') {
              $scope.openDialog('bank-orders',row[code]);
            }
        };

        $rootScope.$on('BANK_ACCOUNT_SELECTED', function (event, row) {
            if (row['id']) $scope.editing['bankAccount'] = row['id']
            if($scope.dialog) $scope.dialog.remove();
        });
        $rootScope.$on('BANK_ORDER_SELECTED', function (event, row) {
            if (row['id']) $scope.editing['bankOrder'] = row['id']
            if($scope.dialog) $scope.dialog.remove();
        });

        //-------------------------------------> filtering, ordering, pagination <----------------------------------------------

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