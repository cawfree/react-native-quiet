# react-native-quiet

## Getting started

`$ npm install react-native-quiet --save`

### Mostly automatic installation

`$ react-native link react-native-quiet`

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
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-quiet')
  	```


## Usage
```javascript
import RNQuiet from 'react-native-quiet';

// TODO: What to do with the module?
RNQuiet;
```
