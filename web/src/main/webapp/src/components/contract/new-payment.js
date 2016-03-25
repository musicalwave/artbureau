var React = require('react');
var NewPayment = React.createClass({
    dateChanged: function(newDate) {
        this.props.dateChanged(this.props.payment.id, newDate);
    },
    valueChanged: function(e) {
        var newValue = parseInt(e.target.value);
        this.props.valueChanged(this.props.payment.id, newValue);
    },
    plannedChanged: function() {
         this.props.plannedChanged(this.props.payment.id, !this.props.payment.planned);
    },
    render: function() {
        return (
            <tr>
                <td>
                    <input type="text"
                           ref="date"
                           value={this.props.payment.date}
                           onChange={$.noop}/>
                </td>
                <td>
                    <input type="number"
                           min="0"
                           value={this.props.payment.value}
                           onChange={this.valueChanged}/>
                </td>
                <td>
                    <input type="checkbox"
                           className="checkbox"
                           checked={this.props.payment.planned}
                           onChange={this.plannedChanged}/>
                </td>
            </tr>
        );
    },
    componentDidMount: function() {
        $(this.refs.date).datepicker({
            firstDay: 1,
            dateFormat: "dd-mm-yy",
            onSelect: this.dateChanged,
            minDate: new Date()
        })
    }
});

module.exports = NewPayment;
