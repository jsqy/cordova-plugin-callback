package com.youbanban.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Callback extends CordovaPlugin {

CallbackContext callbackContext;

@Override
public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
	if (action.equals("initialize")) {
		this.callbackContext = callbackContext;
		return true;
	}
	else if (action.equals("start")) {
		return true;
	}
	return false;
}

}
