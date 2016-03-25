var React = require('react');

var WidgetCollapser = React.createClass({
    getDefaultProps: function() {
        return {
            title: "Default title",
            classNameModifier: ""
        }
    },
    render: function() {
        var className = "widget-header " + this.props.classNameModifier;

        return(
            <div className={className}>

                <h4>{this.props.title}</h4>

                <div className="toolbar no-padding">
                    <div className="btn-group">
                            <span className="btn btn-xs widget-collapse">
                                <i className="icon-angle-up" />
                            </span>
                    </div>
                </div>

            </div>
        );
    }
});

module.exports = WidgetCollapser;
