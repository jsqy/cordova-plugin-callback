exports.initialize = function (callback) {
	cordova.exec(function (call) {
		callback[call.name](call.argument, function (argument) {
			cordova.exec(undefined, undefined, "Callback", "onsucceed", [call.id, argument]);
		}, function (argument) {
			cordova.exec(undefined, undefined, "Callback", "onerror", [call.id, argument]);
		});
	}, undefined, "Callback", "initialize", []);
};
exports.start = function (onsucceed, onerror) {
	cordova.exec(onsucceed, onerror, "Callback", "start", []);
};
