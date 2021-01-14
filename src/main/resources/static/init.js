angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';
    let $currentPage;
    let $viewCount;

    $scope.fillTable = function () {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                page: $currentPage ? $currentPage : 0,
                count: $viewCount ? $viewCount : 5

            }
        }).then(function (response) {
            $scope.productsList = response.data;
        });
    };

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    };

    $scope.deleteProductById = function (id) {
        $http({
            url: contextPath + '/products',
            method: 'DELETE',
            params: {
                id: id

            }
        }).then(function (response) {
            $scope.fillTable();
        });
    };

    $scope.changePage = function (page) {
        $currentPage = page;
        $viewCount = 5;
        $scope.fillTable();
    };

    $scope.fillTable();
});