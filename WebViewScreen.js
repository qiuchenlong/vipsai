import React from 'react';
import { WebView } from 'react-native';

export default class WebViewScreen extends React.Component {
//  const source = (Platform.OS == 'ios') ? require('./pages/demo.html') : { uri: 'file:///android_asset/pages/demo.html' }
  const source = { uri:'file:///android_asset/demo.html' }
  render() {
    return (
      <WebView source={require('./demo.html')} />
    )
  }

  bootstrapJS() {
    const data = { hello: 'world' }
    return `init(${JSON.stringify(data)})`
  }
}