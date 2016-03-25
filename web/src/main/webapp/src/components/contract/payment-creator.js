var React = require('react');
var ContractItemAction = require('./contract-item-action.js');
var Utils = require('../../utils/utils.js');

var PaymentCreator = React.createClass({
    insertPayment: function() {
        console.log('new payment inserted!');
        $.ajax({
            url: "/do/payment/insert",
            method: "POST",
            data: {
                contractId: this.props.contractId,
                date: moment(this.props.date, 'DD-MM-YYYY').format('x'),
                value: this.props.value,
                planned: this.props.status === 1 ? 1 : 0,
                done: this.props.status === 1 ? 0 : 1
            },
            success: this.props.reloadClientAndContracts,
            error: Utils.logAjaxError.bind(this, "/do/payment/insert")
        });
        this.props.hideCreator();
    },
    render: function() {
        return(
            <tr>
                <td>
                    <input onChange={$.noop}
                           ref="dateInput"
                           type="text"
                           value={this.props.date} />
                </td>
                <td>
                    <input onChange={this.props.valueChanged}
                           type="number"
                           min="0"
                           value={this.props.value} />
                </td>
                <td>
                    <input onChange={$.noop}
                           ref="statusInput"
                           type="text"
                           value={this.props.status} />
                </td>
                <td className="action-cell">
                    <ContractItemAction key="insertPaymentAction"
                                        clickHandler={this.insertPayment}
                                        iconName="icon-save" />
                    <ContractItemAction key="hideCreatorAction"
                                        clickHandler={this.props.hideCreator}
                                        iconName="icon-remove" />
                </td>
            </tr>
        );
    },
    componentDidMount: function() {
        var statuses = [{id: 1, text: "Запланирован"},
            {id: 2, text: "Проведён"}];
        $(this.refs.statusInput).select2({
            data: statuses,
            minimumResultsForSearch: Infinity
        });

        $(this.refs.statusInput).on("change", this.props.statusChanged);

        $(this.refs.dateInput).datepicker({
            dateFormat: "dd-mm-yy",
            onSelect: function(date) {
                this.props.dateChanged(date);
            }.bind(this)
        });
    }
});

module.exports = PaymentCreator;
