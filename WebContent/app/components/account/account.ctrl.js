
angular.module('app.account.ctrl', [])
    .controller('account.ctrl', ['$scope', '$location', '$http', '$compile', '$rootScope', '$routeParams',
    function($scope, $location, $http, $compile, $rootScope, $routeParams) {

    $rootScope.pageTitle = "Account";
    $scope.accountId = $routeParams.accountId;
    $scope.accountBalance = -1;

    angular.element(document).ready(function() {
        angular.element('#transfer-error').hide();
        angular.element('#transfer-success').hide();

        // load account
        $http({
            method: "GET",
            url: `account/${$scope.accountId}`,
            headers: {
			   'Content-Type': "application/json;charset=utf-8"
			},
            responseType: "json",
            data: ""
        }).then(
            function(response) {
                console.log(response);
                var data = response.data;

                $scope.accountBalance = data.balance;
            },
            function(response) {
                console.log(response);
                // error
            }
        );

        // load transfers table
        $scope.transfersTable = angular.element('#transfer-table').DataTable({
            "ajax": function(data, callback, settings) {
                $http({
                    method: "GET",
                    url: `transfer/${$scope.accountId}`,
                    headers: {
                       'Content-Type': "application/json;charset=utf-8"
                    },
                    responseType: "json",
                    data: ""
                }).then(
                    function(response) {
                        callback(response);
                    }
                );
            },
            "order": [[ 0, "desc" ]],
            "columns": [
                { "data": "timestamp" },
                {
                    "data": function(row, type, set, meta) {
                       if (row.sourceAccountId == $scope.accountId) {
                            return row.destinationAccountId;
                       } else {
                            return row.sourceAccountId;
                       }
                   }
                },
                { "data": "amount" },
                {
                    "data": "sourceAccountId",
                    "render": function(data, type, row, meta) {
                       if (data == $scope.accountId) {
                            return "Outgoing";
                       } else {
                            return "Incoming";
                       }
                   }
                }
            ]
        });

        // configure autocomplete
        angular.element('.autocomplete').autocomplete({
            minLength: 1,
            source: function (request, callback) {
                var fieldName = angular.element(this)[0].element.attr('data-field-name');
                $http({
                    method: "GET",
                    url: 'addressbook',
                    headers: {
                       'Content-Type': "application/json;charset=utf-8"
                    },
                    responseType: "json",
                    params: {
                        "term": request.term,
                        "field": fieldName
                    }
                }).then(
                    function(response) {
                        for(var index in response.data) {
                            var item = response.data[index];
                            item.label = `User: ${item.recipientUserId}, Account: ${item.recipientAccountId}`;
                            item.value = '';
                        }
                        console.log("res");
                        console.log(response);
                        callback(response.data);
                    }
                );
            },
            select: function(event, ui) {
                angular.element('#destinationUserId').val(ui.item.recipientUserId);
                angular.element('#destinationAccountId').val(ui.item.recipientAccountId);

                $scope.transfer.destinationUserId = ui.item.recipientUserId;
                $scope.transfer.destinationAccountId = ui.item.recipientAccountId;
                return false;
            }
        });
    });

    $scope.deleteAccount = function() {
        $http({
            method: "DELETE",
            url: `account/${$scope.accountId}`,
            headers: {
               'Content-Type': "application/json;charset=utf-8"
            },
            responseType: "json",
            data: {}
        }).then(
            function(response) {
                console.log(response);
                $location.path('/');
            },
            function(response) {
                console.log(response);
                // error
            }
        );
    };

    $scope.transfer = {
        destinationUserId: '',
        destinationAccountId: '',
        amount: '',
        doTransfer: function() {
            var transferForm = angular.element('#transfer-form')[0];

            if (!transferForm.reportValidity()) {
                return;
            }

            var destinationUserId = parseInt($scope.transfer.destinationUserId);
            var destinationAccountId = parseInt($scope.transfer.destinationAccountId);
            var amount = parseInt($scope.transfer.amount);

            $http({
                method: "POST",
                url: `transfer/${$scope.accountId}`,
                headers: {
                   'Content-Type': "application/json;charset=utf-8"
                },
                responseType: "json",
                data: {
                    "destinationUserId": destinationUserId,
                    "destinationAccountId": destinationAccountId,
                    "amount": amount
                }
            }).then(
                function(response) {
                    console.log(response);

                    var data = response.data;
                    $scope.accountBalance = data.newBalance;

                    $scope.transfer.destinationUserId = '';
                    $scope.transfer.destinationAccountId = '';
                    $scope.transfer.amount = '';

                    transferForm.reset();

                    angular.element('#transfer-success').show();
                    angular.element('#transfer-success').html('Transferred');
                    angular.element('#transfer-error').hide();

                    $scope.transfersTable.ajax.reload();

                    if (!data.isRecipientInAddressBook) {
                        $scope.promptSaveToAddressBook(destinationUserId, destinationAccountId);
                    }
                },
                function(response) {
                    console.log(response);
                    angular.element('#transfer-success').hide();
                    angular.element('#transfer-error').show();
                    angular.element('#transfer-error').html(response.data.message);
                }
            );
        }
    };

    $scope.promptSaveToAddressBook = function(userId, accountId) {
        if (confirm('Do you want to save this recipient to the address book?')) {
            $http({
                method: "POST",
                url: `addressbook`,
                headers: {
                   'Content-Type': "application/json;charset=utf-8"
                },
                responseType: "json",
                data: {
                    "recipientUserId": userId,
                    "recipientAccountId": accountId
                }
            }).then(
                function(response) {
                    console.log(response);

                },
                function(response) {
                    console.log(response);
                    // error
                    alert(response.data.message);
                }
            );
        }
    }
}]);