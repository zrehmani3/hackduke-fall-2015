var React = require('react');
var ReactDOM = require('react-dom');

var MyComponent = React.createClass({
  render: function() {
    return <span className="MyComponent">Hello, MyComponent!</span>;
  }
});

module.exports = MyComponent;

ReactDOM.render(
  <MyComponent />,
  document.getElementById('container')
);
