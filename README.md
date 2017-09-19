# cordova-plugin-callback
This plugin demonstrates a machanism to call js functions and notify of events from native.

## API
`callback.initialize(callback, listener);`

Register callbacks to be exposed to native, and event listeners. The arguments are maps of functions.

Callbacks are in form `function(argument, onsucceed, onerror);`. They accept single argument and use callbacks to return results or errors.

Listeners are in form `function(argument);`. They accept single argument and don't call native back.

```js
callback.initialize({
	f: function(argument, onsucceed, onerror){
		if(..)
			onsucceed(..);
		else
			onerror("error");
	},
	g: ..
}, {
	e: function(argument){
		..;
	},
	f: ..
});
```

`callback.start();`

Start the native process, which may call js functions back and notify of events as needed.

## API from native side

```java
void call(String name, Object argument, Callable onsucceed, Callable onerror);
```

Call js function. `name` is the function name. `argument` can be any value that is supported by cordova as PluginResult. Provide callbacks to receive the result or error.

```java
void notify(String name, Object argument);
```

Notify js of an event. `name` is the event name. `argument` can be any value that is supported by cordova as PluginResult.
