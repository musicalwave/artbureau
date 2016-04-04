import React from 'react';
import {
  deleteContract, 
  restoreContract,
  writeoffContract,
  cashbackContract,
  unlockContract
} from '../../actions/contract_actions';


var ContractMenuAction = React.createClass({
    getDefaultProps: function() {
        return {
            clickHandler: $.noop,
            iconName:     'icon-beer'
        };
    },
    render: function() {
        return(
            <tr onClick={this.props.clickHandler}>
                <td><i className={this.props.iconName + ' icon-2x'} /></td>
                <td><label>{this.props.name}</label></td>
            </tr>
        );
    }
});

export default React.createClass({

    getDefaultProps: function() {
        return {
            visible: false,
            top: 0,
            left: 0,
            unlockHandler: $.noop,
            lockHandler: $.noop,
            restoreHandler: $.noop,
            deleteHandler: $.noop,
            locked: false,
            deleted: false
        };
    },

    getStyle: function() {
        if (this.props.visible)
            return {
              top:  this.props.top,
              left: this.props.left
            };
        else
            return { display: 'none' };
    },
    hideAndReloadData: function() {
        this.props.hide();
        this.props.reloadData();
    },
    getActions: function() {
        var contractId = this.props.contract.id;
        var actions = [];
        var unlockAction    = <ContractMenuAction
            key='unlockAction'
            clickHandler={unlockContract.bind(null, contractId, this.hideAndReloadData)}
            iconName='icon-unlock'
            name='Разморозить' />
        var openLockFormAction      = <ContractMenuAction
            key='lockAction'
            clickHandler={this.props.openLockForm}
            iconName='icon-lock'
            name='Заморозить' />;
        var restoreAction   = <ContractMenuAction
            key='restoreAction'
            clickHandler={restoreContract.bind(null, contractId, this.hideAndReloadData)}
            iconName='icon-refresh'
            name='Восстановить' />
        var deleteAction    = <ContractMenuAction
            key='deleteAction'
            clickHandler={deleteContract.bind(null, contractId, this.hideAndReloadData)}
            iconName='icon-trash'
            name='Удалить' />;
        var writeOffAction = <ContractMenuAction
            key='writeOffAction'
            clickHandler={writeoffContract.bind(null, contractId, this.hideAndReloadData)}
            iconName='icon-pencil'
            name='Списать' />;
        var cashbackAction  = <ContractMenuAction
            key='cashbackAction'
            clickHandler={cashbackContract.bind(null, contractId, this.hideAndReloadData)}
            iconName='icon-money'
            name='Вернуть деньги' />;

        if (this.props.contract.active) {
            if (this.props.contract.freezed)
                actions.push(unlockAction);
            else
                actions.push(openLockFormAction);
            
            actions.push(writeOffAction);
            actions.push(cashbackAction);
        }

        if (this.props.contract.deleted)
            actions.push(restoreAction);
        else
            actions.push(deleteAction);

        return actions;
    },

    render: function() {
        return (
            <table style={this.getStyle()} className='contract-menu'>
                <tbody>
                {this.getActions()}
                </tbody>
            </table>
        );
    }
});

