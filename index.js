import { NativeEventEmitter, NativeModules } from 'react-native';

const { RNQuiet } = NativeModules;

const nativeEventEmitter = new NativeEventEmitter(
  RNQuiet,
);

export default {
  ...RNQuiet,
  addListener: listener => nativeEventEmitter
    .addListener(
      "onMessageReceived",
      listener,
    ),
};
