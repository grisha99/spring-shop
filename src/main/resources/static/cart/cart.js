angular.module('app').controller('cartController', function ($scope, $http, $location) {
    const apiPath = 'http://localhost:8189/market/api/v1';

    $scope.fillCart = function() {
        $http({
            url: apiPath + '/cart',
            method: 'GET',

        }).then(function (response) {
            $scope.cartList = response.data;
        })
    };

    $scope.addToCartById = function (id) {
        $http({
            url: apiPath + '/cart/add/' + id,
            method: 'GET',

        }).then(function (response) {
             $scope.fillCart();
        })
    };

    $scope.removeFromCartById = function (id) {
        $http({
            url: apiPath + '/cart/delete/' + id,
            method: 'GET',

        }).then(function (response) {
            $scope.fillCart();
        })
    };

    $scope.removeAllFromCartById = function (id) {
        $http({
            url: apiPath + '/cart/delete/all/' + id,
            method: 'GET',

        }).then(function (response) {
            $scope.fillCart();
        })
    };

    $scope.clearCart = function () {
        $http({
            url: apiPath + '/cart/clear',
            method: 'GET',

        }).then(function (response) {
            $scope.fillCart();
        })
    };

    $scope.createOrder = function () {
        $http.post(apiPath + '/orders', this.orderDelivery)
            .then(function (response) {
//                $scope.orderDelivery = null;
//                $scope.fillOrders();
                $scope.fillCart();
            });
    };

    $scope.createOrder = function () {
        $http.get(contextPath + '/api/v1/orders/create')
            .then(function (response) {
                $scope.showMyOrders();
                $scope.showCart();
            });
    }

    $scope.goToOrderSubmit = function () {
        $location.path('/order_confirmation');
    }

    $scope.fillCart();
});