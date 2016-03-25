var React = require('react');

var Link = React.createClass({
    getDefaultProps: function() {
        return {
            clickHandler: $.noop,
            hoverHandler: $.noop,
            selected:     false,
            link: {
                id: -1,
                name: 'default'
            }
        }
    },
    clickHandler: function(e) {
        e.preventDefault();
        this.props.clickHandler(this.props.link.id);
    },
    hoverHandler: function(e) {
        e.preventDefault();
        this.props.hoverHandler(this.props.link.id);
    },
    render: function() {
        var className = this.props.selected
                        ? 'selected'
                        : '';
        return(
            <a data-id={this.props.link.id} 
               className={className}
               onClick={this.clickHandler}
               onMouseEnter={this.hoverHandler}>
               {this.props.link.name}
            </a>
        );
    }
});

module.exports = Link;
