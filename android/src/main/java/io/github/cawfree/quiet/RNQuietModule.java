package io.github.cawfree.quiet;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import org.quietmodem.Quiet.*;

public class RNQuietModule extends ReactContextBaseJavaModule {

    /* Static Declations. */
    private static final String TAG = "RNQuiet";

    private final ReactApplicationContext reactContext;

    public RNQuietModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return RNQuietModule.TAG;
    }

    @ReactMethod
    public final void start(final String pKey) {
      this.stop();
      Log.d(TAG, "start! "+pKey);
    }

    @ReactMethod
    public final void send(final String pMessage) {
      Log.d(TAG, "send "+pMessage);
      
    }

    @ReactMethod
    public final void stop() {
      Log.d(TAG, "stop!");
    }
    
}
