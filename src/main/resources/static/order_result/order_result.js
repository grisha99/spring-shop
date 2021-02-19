angular.module('app').controller('orderResultController', function ($scope, $http, $location, $routeParams) {
    const apiPath = 'http://localhost:8189/market/api/v1';

    $scope.showOrder = function () {
        $http({
            url: apiPath + '/orders/' + $routeParams.orderId,
            method: 'GET'
        }).then(function (response) {
            $scope.OrderInfo = response.data;
        });
    };

    $scope.showOrder();
});