import React from 'react';
import moment from 'moment';
import ContractItemAction from './contract-item-action.js';
import {
  commitPayment,
  restorePayment,
  updatePayment,
  deletePayment
} from '../../actions/payment_actions.js';

export default React.createClass({
  getInitialState: function() {
    return {
      editMode: false,
      date: this.getFormattedDate(),
      value: this.props.payment.value
    };
  },
  getPaymentStatus: function(payment) {
    if(this.props.payment.done)
      return 'Проведён';
    else if (this.props.payment.planned)
      return 'Запланирован';
    else
      return 'Не определён';
  },
  getFormattedDate: function() {
    return moment(this.props.payment.date).format('DD-MM-YYYY');
  },
  getActions: function() {
    var commitAction  = <ContractItemAction key='commitAction'
                                            clickHandler={this.commit}
                                            iconName='icon-check' />;
    var restoreAction = <ContractItemAction key='restoreAction'
                                            clickHandler={this.restore}
                                            iconName='icon-refresh' />;
    var editAction    = <ContractItemAction key='editAction'
                                            clickHandler={this.edit}
                                            iconName='icon-pencil' />;
    var updateAction  = <ContractItemAction key='saveAction'
                                            clickHandler={this.update}
                                            iconName='icon-save' />;
    var cancelAction  = <ContractItemAction key='cancelAction'
                                            clickHandler={this.cancel}
                                            iconName='icon-remove' />;
    var deleteAction  = <ContractItemAction key='deleteAction'
                                            clickHandler={this.delete}
                                            iconName='icon-trash' />;
    var actions = [];

    if(this.state.editMode) {
      actions.push(updateAction);
      actions.push(cancelAction);
    } else {
      if(this.props.payment.done)
        actions.push(restoreAction);
      else
        actions.push(commitAction);

      actions.push(editAction);
      actions.push(deleteAction);
    }

    return actions;
  },
  commit: function() {
    commitPayment(
      this.props.payment.id, 
      this.props.reloadClientAndContracts
    );
  },
  restore: function() {
    restorePayment(
      this.props.payment.id,
      this.props.reloadClientAndContracts
    );
  },
  delete: function() {
    deletePayment(
      this.props.payment.id,
      this.props.reloadClientAndContracts
    );
  },
  update: function() {
    updatePayment(
      this.props.payment.id,
      moment(this.state.date, 'DD-MM-YYYY'),
      this.state.value,
      this.props.reloadClientAndContracts
    );
    this.cancel();
  },
  edit: function() {
    this.setState({
      editMode: true
    });
  },
  cancel: function() {
    this.setState({
      editMode: false
    });
  },
  dateChanged: function(newDate) {
    this.setState({
      date: newDate
    });
  },
  valueChanged: function(e) {
    this.setState({
      value: parseInt(e.target.value)
    });
  },
  render: function() {
    return(
      <tr>
        {this.state.editMode
         ? <td>
             <input ref='dateInput'
                    type='text'
                    defaultValue={this.getFormattedDate()} />
           </td>
         : <td>{this.getFormattedDate()}</td>
        }
        {this.state.editMode
         ? <td>
             <input onChange={this.valueChanged}
                    type='number'
                    defaultValue={this.props.payment.value} />
           </td>
         : <td>{this.props.payment.value}</td>
        }
        <td>{this.getPaymentStatus(this.props.payment)}</td>
        <td className='action-cell'>
          {this.getActions()}
        </td>
      </tr>
    );
  },
  componentDidUpdate: function() {
    $(this.refs.dateInput).datepicker({
      firstDay: 1,
      dateFormat: 'dd-mm-yy',
      onSelect:   date => this.dateChanged(date)
    });
  }
});

