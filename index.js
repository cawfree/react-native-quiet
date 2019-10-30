import { Platform, NativeEventEmitter, NativeModules, PermissionsAndroid } from 'react-native';

const { RNQuiet } = NativeModules;

const nativeEventEmitter = new NativeEventEmitter(
  RNQuiet,
);

export default {
  ...RNQuiet,
  start: (key) => {
    if (Platform.OS === 'android') {
      return Promise
        .resolve()
        .then(
          () => {
            return PermissionsAndroid
              .request(
                PermissionsAndroid.PERMISSIONS.RECORD_AUDIO,
              )
              .then(
                (result) => {
                  if (result === PermissionsAndroid.RESULTS.GRANTED) {
                    return RNQuiet
                      .start(key);
                  }
                  return Promise
                    .reject(
                      new Error(
                        'RNQuiet: Unable to start, because we\'re missing the microphone permission.',
                      ),
                    );
                },
              );
          },
        );
    }
    return Promise
      .resolve()
      .then(() => RNQuiet.start(key));
  },
  addListener: listener => nativeEventEmitter
    .addListener(
      "onMessageReceived",
      listener,
    ),
};
