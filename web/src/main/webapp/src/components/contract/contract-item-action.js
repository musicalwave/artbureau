var React = require('react');
var ContractItemAction = React.createClass({
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

module.exports = ContractItemAction;