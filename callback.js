exports.initialize = function (onsucceed, onerror) {
	cordova.exec(onsucceed, onerror, "Callback", "initialize", []);
};
exports.start = function (onsucceed, onerror) {
	cordova.exec(onsucceed, onerror, "Callback", "start", []);
};
