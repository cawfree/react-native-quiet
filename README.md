# react-native-quiet

This is a [React Native](https://facebook.github.io/react-native/) wrapper around the [Quiet Project](https://github.com/quiet/quiet), which enables us to transfer data over sound at a fairly high speed. This has a number of benefits:

  - Super cross-platform. (You just need a microphone and a speaker.)
  - Broadcast to devices within range without pairing.
  - No network connection required.

Quiet can even go _ultrasonic_, allowing us to communicate without impacting on noise levels that are perceptible by human ears.

New to Quiet? Try the awesome online demo [here](https://quiet.github.io/quiet-js/).

## 🚀 Getting started

Using [`npm`]():

```bash
$ npm install react-native-quiet --save
```

Using [`yarn`]():

```bash
yarn add react-native-quiet
```

### ✍️ Example

```javascript
import RNQuiet from 'react-native-quiet';

// Start listening. (This will ask for microphone permissions!)
RNQuiet
  .start('ultrasonic-experimental')
  .then(
    () => {
      // Listen for Messages.
      const { unsubscribe } = RNQuiet.addListener(msg => /* do something with received message */);
      // Send Messages. (Careful; you can hear your own!)
      RNQuiet.send(
        'hello, world!',
      );
      // Stop listening.
      RNQuiet.stop();
      // Release the observer.
      unsubscribe();
    },
  )
  .catch(
    (e) => /* user rejected permission */
  );

```

### ⚙️ Installation (> 0.60.0)

React Native takes care of most of the heavy lifting when integrating this library. However, there are a couple of additional steps you need to execute to integrate with Android and iOS.

#### Android

In your `<project-dir>/android/settings.gradle`, append the `:quiet` native project, which is packaged inside of `react-native-quiet`:

```java
include ':quiet'
project(':quiet').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-quiet/android/org.quietmodem.Quiet/quiet')

```

#### iOS

On iOS, after installing be sure to sync your Cocoapods. Ensure that:

1. The line `pod 'react-native-quiet', :path => '../../react-native-quiet.podspec'` is visible in your `Podfile`.
2. You have ran `pod install` inside your `/ios` directory.

### Mostly automatic installation (Pre: 0.60.0)

1. `$ react-native link react-native-quiet`

### Manual installation

#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-quiet` and add `RNQuiet.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNQuiet.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import io.github.cawfree.quiet.RNQuietPackage;` to the imports at the top of the file
  - Add `new RNQuietPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-quiet'
  	project(':react-native-quiet').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-quiet/android')
    include ':quiet'
    project(':quiet').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-quiet/android/org.quietmodem.Quiet/quiet')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-quiet')
  	```

### ✌️ License
[MIT](https://opensource.org/licenses/MIT)
