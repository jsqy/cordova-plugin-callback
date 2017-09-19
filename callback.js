exports.initialize = function (callback, listener) {
	cordova.exec(function (call) {
		callback[call.name](call.argument, function (argument) {
			cordova.exec(undefined, undefined, "Callback", "onsucceed", [call.id, argument]);
		}, function (argument) {
			cordova.exec(undefined, undefined, "Callback", "onerror", [call.id, argument]);
		});
	}, function (event) {
		listener[event.name](event.argument);
	}, "Callback", "initialize", []);
};
exports.start = function (onsucceed, onerror) {
	cordova.exec(onsucceed, onerror, "Callback", "start", []);
};
