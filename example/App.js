import React from 'react';
import { SafeAreaView, View, StyleSheet, TextInput, Text, Button, Image } from 'react-native';
import Quiet from 'react-native-quiet';

const styles = StyleSheet
  .create(
    {
      container: {
        flex: 1,
        padding: 15,
        alignItems: 'center',
      },
      spacing: {
        marginBottom: 15,
      },
      title: {
        color: 'white',
        fontWeight: 'bold',
        fontSize: 26,
        marginBottom: 10,
      },
      subtitle: {
        color: 'white',
        fontSize: 16,
        marginBottom: 10,
      },
      textInput: {
        fontSize: 26,
        textAlign: 'center',
      },
      logo: {
        position: 'absolute',
        bottom: 15,
        width: 250,
        height: 100,
      },
      logoContainer:{
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
      }
    },
  );

export default class App extends React.Component {
  state = {
    msg: '',
    rcv: '',
  };
  onPress = () => {
    const { msg } = this.state;
    if (msg && msg.length > 0) {
      this.setState(
        {
          msg: '',
        },
        () => Quiet.send(msg),
      );
    }
  };
  componentDidMount() {
    Quiet.start(
      'ultrasonic-experimental',
    );
    Quiet.addListener(
      rcv => this.setState(
        {
          rcv,
        },
      ),
    );
  }
  render() {
    const { msg, rcv } = this.state;
    return (
      <>
        <View
          style={[
            StyleSheet.absoluteFill,
            {
              backgroundColor: '#00D8FF',
            },
          ]}
        />
        <SafeAreaView
          style={[
            StyleSheet.absoluteFill,
          ]}
        >
          <View
            style={styles.container}
          >
            <Text
              style={styles.title}
              children="Welcome to react-native-quiet!"
            />
            <TextInput
              style={styles.textInput}
              onChangeText={msg => this.setState(
                {
                  msg,
                },
              )}
              value={msg}
              placeholder="Enter a message."
            />
            <Button
              title={msg.length ? 'Tap to send' : 'Type a Message'}
              onPress={this.onPress}
              disabled={!msg.length}
            />
            <View
              style={styles.spacing}
            />
            <Text
              style={rcv.length ? styles.title : undefined}
              children={rcv.length ? `Received: "${rcv}"` : 'Detected messages will be displayed here. (Devices can listen to themselves)'}
            />
          </View>
          <View
            style={styles.logoContainer}
          >
            <Image
              style={styles.logo}
              source={{ uri: 'https://camo.githubusercontent.com/662bcd3919d8d23a56e80ae4044c3ab676585864/68747470733a2f2f71756965742e6769746875622e696f2f716d705f6865616465725f742e706e67' }}
            />
          </View>
        </SafeAreaView>
      </>
    );
  }
}
