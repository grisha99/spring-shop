angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market/api/v1';
    let $currentPage=0;
    let $viewCount;

    $scope.fillTable = function () {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                page: $currentPage ? $currentPage : 0,
                count: $viewCount ? $viewCount : 3

            }
        }).then(function (response) {
            $scope.productsList = response.data;

            let minPageIndex = $currentPage - 2;
            if (minPageIndex < 0) {
                minPageIndex = 0;
            }

            let maxPageIndex = $currentPage + 2;
            if (maxPageIndex > $scope.productsList.totalPages-1) {
                maxPageIndex = $scope.productsList.totalPages-1;
            }

            $scope.paginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);

        });
    };

    $scope.generatePagesIndexes = function(startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage+1; i++) {
            arr.push(i);
        }
        return arr;
    }

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
        $viewCount = 3;
        $scope.fillTable();
    };

    $scope.fillTable();
});