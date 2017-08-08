package com.youbanban.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Callback extends CordovaPlugin {

CallbackContext callbackContext;

@Override
public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
	if (action.equals("initialize")) {
		this.callbackContext = callbackContext;
		return true;
	}
	else if (action.equals("start")) {
		this.start();
		return true;
	}
	else if (action.equals("onsucceed")) {
		int id = args.getInt(0);
		Object arg = args.get(1);
		this.onsucceed(id, arg);
		return true;
	}
	return false;
}

private void start() {
	Timer timer = new Timer();
	timer.scheduleAtFixedRate(new TimerTask() {
		@Override
		public void run() {
			try {
			Callback.this.call("f", 0, new Callable(){
				@Override
				public void call(Object argument) { }
			});
			} catch (Exception e) { }
		}
	}, 1000, 1000);
}

interface Callable {
	void call(Object argument);
}

int id = 0;
HashMap<Integer, Callable> callback = new HashMap<Integer, Callable>();
void call(String name, Object argument, Callable callback) throws JSONException {
	int id = this.id++;
	this.callback.put(id, callback);
	JSONObject call = new JSONObject();
	call.put("name", name);
	call.put("argument", argument);
	call.put("id", id);
	PluginResult result = new PluginResult(PluginResult.Status.OK, call);
	result.setKeepCallback(true);
	callbackContext.sendPluginResult(result);
}

void onsucceed(int id, Object argument) {
	Callable callback = this.callback.get(id);
	this.callback.remove(id);
	callback.call(argument);
}

}
