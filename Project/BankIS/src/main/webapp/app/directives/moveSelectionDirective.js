'use strict';

app.directive('moveSelectionDirective', function () {
    return {
        templateUrl: 'app/directives/moveSelection.html',
        controller: ['$scope', '$http', '$attrs', '$timeout', '$rootScope', '$element', '$compile', '$routeParams', function gdsfg($scope, $http, $attrs, $timeout, $rootScope, $element, $compile, $routeParams) {


              $scope.moveMostLeft = function(){

                  $scope.setSelected($scope.rows[0]);

                    console.log($scope.selected);
              }

              $scope.moveMostRight = function () {
                  var rowLen = $scope.rows.length;
                  $scope.setSelected($scope.rows[rowLen - 1]);

                  console.log($scope.selected);
                  
              }

              $scope.moveLeft = function(){

              }



            /*  $timeout(function(){
                  console.log($scope.rows[0]);
                  $scope.selected = $scope.rows[0];
              },1000);*/
        }]
    }
});