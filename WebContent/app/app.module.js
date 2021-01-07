var app = angular.module("myApp",   [
                                        "ngRoute",
                                        "app.home.ctrl",
                                        "app.account.ctrl"
                                    ]);

app.config(function ($routeProvider) {
    $routeProvider //url routing
        .when("/", {
            templateUrl: "app/components/home/home.html"
        })
        .when("/account/:accountId", {
            templateUrl: "app/components/account/account.html"
        })
        .otherwise( { redirectTo: "/"});
});

app.run(function($rootScope, $location, $http, $window) {
    $rootScope.showHome = function() {
        $location.path("/");
    };
    // register listener to watch route changes (risorsa StackOverflow)
    //"listener" -> cambi di pagina in angular
    $rootScope.$on( "$routeChangeStart", function(event, next, current) {
        console.log(next.templateUrl); //Stampa il "path" della nuova pagina
    });
});