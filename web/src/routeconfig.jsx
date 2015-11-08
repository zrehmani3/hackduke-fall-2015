import React from 'react'
import { Router, Route } from 'react-router'
import { Test } from './test'

export const RouteConfig = React.createClass({
  render() {
    return (
      <Router>
        <Route path="/" component={Test} />
      </Router>
    );
  }
});
