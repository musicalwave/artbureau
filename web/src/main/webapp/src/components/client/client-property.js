import React from 'react';

export default React.createClass({
    getDefaultProps: function() {
        return {
            editMode: false,
            type: 'text',
            name: 'prop',
            title: 'prop',
            value: '',
            needDatePicker: false,
            outerHandler: $.noop,
            placeholder: ''
        };
    },
    handleValueChange: function(e) {
        this.props.outerHandler(this.props.name, e.target.value);
    },
    render: function() {
        var valueField = this.props.editMode
                         ? <input type={this.props.type}
                                  value={this.props.value}
                                  onChange={this.handleValueChange}
                                  ref='input'
                                  placeholder={this.props.placeholder}/>
                         : this.props.value;

        return (
            <tr>
                <th>{this.props.title}</th>
                <td>{valueField}</td>
            </tr>
        )
    },
    componentDidUpdate: function() {
        if(this.props.editMode && this.props.needDatePicker) {
            $(this.refs.input).datepicker({
                dateFormat: 'dd-mm-yy',
                onSelect: function() {
                    // unfortunately, the datepicker's changes
                    // cannot be tracked by the onChange handler
                    this.props.outerHandler(this.props.name, this.refs.input.value);
                }.bind(this)
            });
        }
    }
});

