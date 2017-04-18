import React from 'react';
import PropTypes from 'prop-types';
import ReactDOM from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Menubar from '../views/Menubar';
import injectTapEventPlugin from 'react-tap-event-plugin';
 
injectTapEventPlugin();

const App = () => (
  <MuiThemeProvider>
    <Menubar />
  </MuiThemeProvider>
);

ReactDOM.render(
  <App />,
  document.getElementById('app')
);