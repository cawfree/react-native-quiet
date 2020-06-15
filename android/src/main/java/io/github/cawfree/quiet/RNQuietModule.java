package io.github.cawfree.quiet;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import androidx.core.content.ContextCompat;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.quietmodem.Quiet.*;

import java.util.Arrays;

public class RNQuietModule extends ReactContextBaseJavaModule {

  /* Static Declations. */
  private static final String TAG = "RNQuiet";

  /* Static, modisiable variables. (Antipattern!) */
  private static FrameTransmitter FRAME_TRANSMITTER = null;
  private static FrameReceiver    FRAME_RECEIVER    = null;

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
    // Place the beacon back into the initial state.
    this.stop();
    // Check if we have permission.
    final boolean hasPermission = ContextCompat.checkSelfPermission(this.reactContext, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    // Do we have permission?
    if (hasPermission) {
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
        new AsyncTask<Void, Void, Void>() { @Override protected final Void doInBackground(final Void... pIsUnused) {
          final byte[] buf = new byte[1024];
          while(FRAME_RECEIVER != null) {
            try {
              // Block and wait for updates.
              FRAME_RECEIVER.setBlocking(0, 0);
              // Wait for the FRAME_RECEIVER to return.
              final int numberOfBytes = (int)FRAME_RECEIVER.receive(buf);
              RNQuietModule.this.reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("onMessageReceived", new String(Arrays.copyOfRange(buf, 0, numberOfBytes), "UTF-8"));
            }
            catch (final Exception pException) {
              // Print the Stack Trace.
              pException.printStackTrace();
            }
          }
          return null;
        } }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
      }
      catch(final Exception pException) {
        // Print the Stack Trace.
        pException.printStackTrace();
        // Stop running.
        this.stop();
      }
    }
  }

  @ReactMethod
  public final void send(final String pMessage) {
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

  /** TODO: Actually close the resources. **/
  @ReactMethod
  public final void stop() {
    if (FRAME_TRANSMITTER != null) {
      // Nullify.
      FRAME_TRANSMITTER = null;
    }
    if (FRAME_RECEIVER != null) {
      //Nullify.
      FRAME_RECEIVER = null;
    }
  }

}
