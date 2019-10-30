import React from 'react';
import { SafeAreaView, View, StyleSheet, Image, TextInput, Text, Button } from 'react-native';
import Quiet from 'react-native-quiet';

const styles = StyleSheet
  .create(
    {
      container: {
        flex: 1,
        padding: 15,
        alignItems: 'center',
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
        <Image
          style={StyleSheet.absoluteFill}
          source={{ uri: 'https://wearejh.com/content/uploads/2017/10/react-native-1500x1000.jpg' }}
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
            <Text
              style={styles.subtitle}
              children="Communicate with ultrasound."
            />
            <TextInput
              style={styles.textInput}
              onChangeText={msg => this.setState(
                {
                  msg,
                },
              )}
              value={msg}
              placeholder="Enter a message to get started."
            />
            <Button
              title={msg.length ? 'Tap to send' : 'Type a Message'}
              onPress={this.onPress}
              disabled={!msg.length}
            />
            <Text
              style={rcv.length ? styles.title : undefined}
              children={rcv.length ? `Received: "${rcv}"` : 'Detected messages will be displayed here. (Devices can listen to themselves)'}
            />
          </View>
        </SafeAreaView>
      </>
    );
  }
}
