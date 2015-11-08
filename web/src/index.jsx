import React from 'react'
import { render } from 'react-dom'
import { RouteConfig } from './routeconfig'

export const Main = React.createClass({
  render() {
    return <RouteConfig />;
  }
});

render(
  <Main />,
  document.getElementById('container')
);
