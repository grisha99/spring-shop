(function ($localStorage) {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'home/home.html',
                controller: 'homeController'
            })
            .when('/products', {
                templateUrl: 'products/products.html',
                controller: 'productsController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/order_confirmation', {
                templateUrl: 'order_confirmation/order_confirmation.html',
                controller: 'orderConfirmationController'
            })
            .when('/order_result/:orderId', {
                templateUrl: 'order_result/order_result.html',
                controller: 'orderResultController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .otherwise({
                redirectTo: '/'
            });

        // $httpProvider.interceptors.push(function ($q, $location) {
        //     return {
        //         'responseError': function (rejection, $localStorage, $http) {
        //             var defer = $q.defer();
        //             if (rejection.status == 401 || rejection.status == 403) {
        //                 console.log('error: 401-403');
        //                 $location.path('/auth');
        //                 if (!(localStorage.getItem("localUser") === null)) {
        //                     delete $localStorage.currentUser;
        //                     $http.defaults.headers.common.Authorization = '';
        //                 }
        //                 console.log(rejection.data);
        //                 var answer = JSON.parse(rejection.data);
        //                 console.log(answer);
        //                 // window.alert(answer.message);
        //             }
        //             defer.reject(rejection);
        //             return defer.promise;
        //         }
        //     };
        // });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
//        if (!$localStorage.cartUUID) {
            $http({
                url: 'http://localhost:8189/market/api/v1/cart',
                method: 'POST',

            }).then(function (response) {
                $localStorage.cartUUID = response.data;
            })
//        }
    }
})();

angular.module('app').controller('indexController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/market';
    const rootPath = 'http://localhost:8189/market';

    $scope.tryToAuth = function () {

        $scope.user.cartUuid = $localStorage.cartUUID;

        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.currentUser = {username: $scope.user.username, token: response.data.token};

                    $scope.currentUserName = $scope.user.username;
                    $scope.getUserAlias();

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $rootScope.$broadcast('logInOut', {
                        upUUID: $localStorage.cartUUID // посылайте что хотите
                    });
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();

        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }

        $http({
            url: 'http://localhost:8189/market/api/v1/cart',
            method: 'POST',

        }).then(function (response) {
            $localStorage.cartUUID = response.data;
            $rootScope.$broadcast('logInOut', {
                upUUID: $localStorage.cartUUID // посылайте что хотите
            });
        })


    };

    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.currentUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.getUserAlias = function () {
        $http.get(rootPath + '/auth/alias')
            .then(function (response) {
                $scope.userInfo = response.data;
            });

    }
});