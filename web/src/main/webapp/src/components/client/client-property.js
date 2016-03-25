var React = require('react');

var ClientProperty = React.createClass({
    getDefaultProps: function() {
        return {
            editMode: false,
            type: "text",
            name: "prop",
            title: "prop",
            value: "",
            isDate: false,
            outerHandler: $.noop
        };
    },
    handleValueChange: function(e) {
        this.props.outerHandler(this.props.name, e.target.value);
    },
    render: function() {
        // prohibit manual input for date fields
        var valueChangeHandler = this.props.isDate
                                 ? $.noop
                                 : this.handleValueChange;

        var valueField = this.props.editMode
                         ? <input type={this.props.type}
                                  value={this.props.value}
                                  onChange={valueChangeHandler}
                                  ref="input"/>
                         : this.props.value;

        return (
            <tr>
                <th>{this.props.title}</th>
                <td>{valueField}</td>
            </tr>
        )
    },
    componentDidUpdate: function() {
        if(this.props.editMode && this.props.isDate) {
            $(this.refs.input).datepicker({
                dateFormat: "dd-mm-yy",
                onSelect: function() {
                    // unfortunately, the datepicker's changes
                    // cannot be tracked by the onChange handler
                    this.props.outerHandler(this.props.name, this.refs.input.value);
                }.bind(this)
            });
        }
    }
});

module.exports = ClientProperty;
