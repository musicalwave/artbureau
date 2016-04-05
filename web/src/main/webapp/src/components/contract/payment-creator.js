import React from 'react';
import moment from 'moment';
import ContractItemAction from './contract-item-action.js';
import {createPayment} from '../../actions/payment_actions.js';

export default React.createClass({
  insertPayment: function() {
    createPayment(
      this.props.contractId,
      moment(this.props.date, 'DD-MM-YYYY'),
      this.props.value,
      this.props.status,
      this.props.reloadClientAndContracts
    );
    this.props.hideCreator();
  },
  render: function() {
    return(
      <tr>
        <td>
          <input onChange={$.noop}
                 ref='dateInput'
                 type='text'
                 value={this.props.date} />
        </td>
        <td>
          <input onChange={this.props.valueChanged}
                 type='number'
                 min='0'
                 value={this.props.value} />
        </td>
        <td>
          <input onChange={$.noop}
                 ref='statusInput'
                 type='text'
                 value={this.props.status} />
        </td>
        <td className='action-cell'>
          <ContractItemAction key='insertPaymentAction'
                              clickHandler={this.insertPayment}
                              iconName='icon-save' />
          <ContractItemAction key='hideCreatorAction'
                              clickHandler={this.props.hideCreator}
                              iconName='icon-remove' />
        </td>
      </tr>
    );
  },
  componentDidMount: function() {
    var statuses = [
      {id: 1, text: 'Запланирован'},
      {id: 2, text: 'Проведён'}
    ];
    $(this.refs.statusInput).select2({
      data: statuses,
      minimumResultsForSearch: Infinity
    });

    $(this.refs.statusInput).on('change', this.props.statusChanged);

    $(this.refs.dateInput).datepicker({
      dateFormat: 'dd-mm-yy',
      onSelect: date => this.props.dateChanged(date)
    });
  }
});

