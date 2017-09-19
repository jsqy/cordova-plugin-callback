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
	else if (action.equals("onerror")) {
		int id = args.getInt(0);
		Object arg = args.get(1);
		this.onerror(id, arg);
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
				public void call(Object argument) {
					try {
					Callback.this.call("g", 0, new Callable(){
						@Override
						public void call(Object argument) {
						}
					}, null);
					} catch (Exception e) { }
				}
			}, new Callable(){
				@Override
				public void call(Object argument) {
					try {
					Callback.this.call("h", 0, new Callable(){
						@Override
						public void call(Object argument) {
						}
					}, null);
					} catch (Exception e) { }
				}
			});
			} catch (Exception e) { }
		}
	}, 1000, 1000);
}

interface Callable {
	void call(Object argument);
}

class Context {
	public Callable onsucceed;
	public Callable onerror;
}

int id = 0;
HashMap<Integer, Context> context = new HashMap<Integer, Context>();
void call(String name, Object argument, Callable onsucceed, Callable onerror) throws JSONException {
	Context context = new Context();
	context.onsucceed = onsucceed;
	context.onerror = onerror;
	int id = this.id++;
	this.context.put(id, context);
	JSONObject call = new JSONObject();
	call.put("name", name);
	call.put("argument", argument);
	call.put("id", id);
	PluginResult result = new PluginResult(PluginResult.Status.OK, call);
	result.setKeepCallback(true);
	callbackContext.sendPluginResult(result);
}

void onsucceed(int id, Object argument) {
	Callable callback = this.context.get(id).onsucceed;
	this.context.remove(id);
	callback.call(argument);
}

void onerror(int id, Object argument) {
	Callable callback = this.context.get(id).onerror;
	this.context.remove(id);
	callback.call(argument);
}

void notify(String name, Object argument) throws JSONException {
	JSONObject event = new JSONObject();
	event.put("name", name);
	event.put("argument", argument);
	PluginResult result = new PluginResult(PluginResult.Status.ERROR, event);
	result.setKeepCallback(true);
	callbackContext.sendPluginResult(result);
}

}
