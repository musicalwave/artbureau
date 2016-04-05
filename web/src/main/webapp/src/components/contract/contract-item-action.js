import React from 'react';

export default React.createClass({
  getDefaultProps: function() {
    return {
      clickHandler: $.noop,
      iconName: "icon-beer"
    };
  },
  render: function() {
    return <i onClick={this.props.clickHandler}
              className={"action-icon icon-border icon-btn " + this.props.iconName}/>;
  }
});

