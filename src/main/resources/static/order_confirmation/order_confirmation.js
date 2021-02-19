angular.module('app').controller('orderConfirmationController', function ($scope, $http, $location) {
    const apiPath = 'http://localhost:8189/market/api/v1';

    $scope.cartContentRequest = function () {
        $http({
            url: apiPath + '/cart',
            method: 'GET'
        }).then(function (response) {
            $scope.cartList = response.data;
        });
    };

    $scope.submitOrder = function () {
        $http({
            url: apiPath + '/orders',
            method: 'POST',
            params: {
                address: $scope.order_info.address
            }
        }).then(function (response) {
            $location.path('/order_result/' + response.data.id);
        });
    }

    $scope.cartContentRequest();
});