# react-native-quiet

This is a [**React Native**](https://facebook.github.io/react-native/) wrapper around the [**Quiet Project**](https://github.com/quiet/quiet), which enables the transfer of data using sound as the transfer medium. This has a number of benefits:

  - Super cross-platform. (You just need a microphone and a speaker.)
  - Broadcast to devices within range without pairing.
  - No network connection required.

Quiet can even go _ultrasonic_, allowing us to communicate without impacting on noise levels that are perceptible by human ears.

Try the awesome online demo [**here**](https://quiet.github.io/quiet-js/).

## 🚀 Getting started

Using [`npm`]():

```bash
$ npm install react-native-quiet --save
```

Using [`yarn`]():

```bash
yarn add react-native-quiet
```

#### Android

This project relies upon the [**Android NDK**](https://developer.android.com/ndk); please make sure this is configured within your system path. Android relies upon caching the [**Quiet Android Project**](https://github.com/quiet/org.quietmodem.Quiet), meaning that we have to manually configure it's visibility to your compiled application. To do this, in your `<project-dir>/android/settings.gradle`, append the `:quiet` native project, which is packaged inside of `react-native-quiet`:

```java
include ':quiet'
project(':quiet').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-quiet/android/Transducer/quiet')
```

Finally, under **File > Project Structure**, be sure to define your `Android NDK location` under **SDK Location**. You can just use the dropdown to select the default location.

#### iOS

On iOS, after installing be sure to sync your Cocoapods via `pod install`.

### Upgrading

#### 0.1.0

**android/settings.xml**
```diff

include ':quiet'
- project(':quiet').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-quiet/android/org.quietmodem.Quiet/quiet')
+ project(':quiet').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-quiet/android/Transducer/quiet')
```

## ✍️ Example

This project exposes high level functionality to send and receive messages using near-ultrasound. Simply start the library, use `send()` to transmit a message string and `addListener` to listen to receive sent messages. Be careful; you can hear your own messages.

```javascript
import Quiet from 'react-native-quiet';

// Start listening. (This will ask for microphone permissions!)
(async() => {
  await Quiet.start("ultrasonic-experimental");
  const { unsubscribe } = Quiet
    .addListener(msg => console.warn(msg));
  Quiet.send("hello, world!");
  await new Promise(resolve => setTimeout(resolve, 10000));
  Quiet.stop();
  unsubscribe();
})();
```

### ✌️ License
[**MIT**](https://opensource.org/licenses/MIT)
