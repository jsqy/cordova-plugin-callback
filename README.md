# cordova-plugin-callback
This plugin demonstrates a machanism to call js functions from native.

## API
`callback.initialize(functions);`

Register functions to be exposed to native. The argument is a map of functions.

Functions are in form `function(argument,onsucceed,onerror);`. They accept single argument and use callbacks to return results or errors.

```js
callback.initialize({
	f:function(argument,onsucceed,onerror){
		if(..)
			onsucceed(..);
		else
			onerror("error");
	},
	g:..
});
```

`callback.start();`

Start the native process, which may call js functions back as needed.

## API from native side

```java
void call(String name, Object argument, Callable onsucceed, Callable onerror);
```

Call js function. `name` is the function name. `argument` can be any value that is supported by cordova as PluginResult. Provide callbacks to receive the result or error.
