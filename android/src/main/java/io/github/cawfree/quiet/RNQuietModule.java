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
    private static FrameTransmitter FRAME_TRANSMITTER = null;
    private static FrameReceiver FRAME_RECEIVER = null;

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
      try {
        FRAME_TRANSMITTER = new FrameTransmitter(
          new FrameTransmitterConfig(
            this.reactContext,
            pKey
          )
        );
        FRAME_RECEIVER = new FrameReceiver(
          new FrameReceiverConfig(
            this.reactContext,
            pKey
          )
        );
      } catch (final Exception pException) {
        // Print the Stack Trace.
        pException.printStackTrace();
      }
    }

    @ReactMethod
    public final void send(final String pMessage) {
      Log.d(TAG, "send "+pMessage);
      try {
        if (FRAME_TRANSMITTER != null) {
          // Transmit the message.
          FRAME_TRANSMITTER.send(pMessage.getBytes());
        }
      }
      catch (final Exception pException) {
        // Print the Stack Trace.
        pException.printStackTrace();
      }
    }

    @ReactMethod
    public final void stop() {
      Log.d(TAG, "stop!");
    }
    
}
