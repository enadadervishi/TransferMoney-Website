function accountHtml(id, name) {
    return `
    <div class="card bg-primary" ng-click="showAccount(${id})">
        <div class="card-body" style="cursor: pointer;">
            <h6 class="side-by-side-header">${id}</h6>
        </div>
    </div>
    `;
}

angular.module('app.home.ctrl', [])
    .controller('home.ctrl', ['$scope', '$location', '$http', '$compile', '$rootScope',
    function($scope, $location, $http, $compile, $rootScope) {

    $rootScope.pageTitle = "Home";

    angular.element(document).ready(function() {
        // load accounts
        $http({
            method: "GET",
            url: 'account',
            headers: {
			   'Content-Type': "application/json;charset=utf-8"
			},
            responseType: "json",
            data: ""
        }).then(
            function(response) {
                console.log(response);
                var data = response.data;

                var accountsParent = angular.element('#boards-container');
                for (var index in data) {
                    var account = data[index];
                    var added = accountsParent.append(accountHtml(account.id));
                    $compile(added)($scope);
                }
            },
            function(response) {
                console.log(response);
                // error
            }
        );
    });

    $scope.showAccount = function(id) {
        $location.path(`/account/${id}`);
    };

    $scope.addAccount = function() {
        $http({
            method: "POST",
            url: 'account',
            headers: {
			   'Content-Type': "application/json;charset=utf-8"
			},
            responseType: "json",
            data: {}
        }).then(
            function(response) {
                console.log(response);

                var data = response.data;
                var parent = angular.element('#boards-container');
                var added = parent.append(accountHtml(data.id));
                $compile(added)($scope);
            },
            function(response) {
                console.log(response);
                // error
            }
        );
    };
}]);