angular.module('app').controller('ordersController', function ($scope, $http) {
    const apiPath = 'http://localhost:8189/market/api/v1';

    $scope.showMyOrders = function () {
        $http({
            url: apiPath + '/orders',
            method: 'GET'
        }).then(function (response) {
            $scope.orderList = response.data;
        });
    };

    $scope.showMyOrders();
});