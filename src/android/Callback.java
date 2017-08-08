package com.youbanban.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Callback extends CordovaPlugin {

CallbackContext callbackContext;

@Override
public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
	if (action.equals("initialize")) {
		this.callbackContext = callbackContext;
		return true;
	}
	else if (action.equals("start")) {
		this.start();
		return true;
	}
	return false;
}

private void start() {
	Timer timer = new Timer();
	timer.scheduleAtFixedRate(new TimerTask() {
		@Override
		public void run() {
			PluginResult result = new PluginResult(PluginResult.Status.OK);
			result.setKeepCallback(true);
			Callback.this.callbackContext.sendPluginResult(result);
		}
	}, 1000, 1000);
}

}
