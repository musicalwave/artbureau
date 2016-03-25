var React = require('react');
var ContractMenuAction = React.createClass({
    getDefaultProps: function() {
        return {
            clickHandler: $.noop,
            iconName:     "icon-beer"
        };
    },
    render: function() {
        return(
            <tr onClick={this.props.clickHandler}>
                <td><i className={this.props.iconName + " icon-2x"} /></td>
                <td><label>{this.props.name}</label></td>
            </tr>
        );
    }
});

var ContractMenu = React.createClass({

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
            return {top:  this.props.top,
                left: this.props.left};
        else
            return {display: "none"};
    },

    getActions: function() {
        var actions = [];
        var unlockAction    = <ContractMenuAction
            key="lockAction"
            clickHandler={this.props.unlockHandler}
            iconName="icon-unlock"
            name="Разморозить" />
        var lockAction      = <ContractMenuAction
            key="unlockAction"
            clickHandler={this.props.lockHandler}
            iconName="icon-lock"
            name="Заморозить" />;
        var restoreAction   = <ContractMenuAction
            key="restoreAction"
            clickHandler={this.props.restoreHandler}
            iconName="icon-refresh"
            name="Восстановить" />
        var deleteAction    = <ContractMenuAction
            key="deleteAction"
            clickHandler={this.props.deleteHandler}
            iconName="icon-trash"
            name="Удалить" />;
        var writeOffAction = <ContractMenuAction
            key="writeOffAction"
            clickHandler={this.handleWriteOffClick}
            iconName="icon-pencil"
            name="Списать" />;
        var cashbackAction  = <ContractMenuAction
            key="cashbackAction"
            clickHandler={this.handleCashbackClick}
            iconName="icon-money"
            name="Вернуть деньги" />;

        if (this.props.locked)
            actions.push(unlockAction);
        else
            actions.push(lockAction);

        actions.push(writeOffAction);
        actions.push(cashbackAction);

        if (this.props.deleted)
            actions.push(restoreAction);
        else
            actions.push(deleteAction);

        return actions;
    },

    render: function() {
        return (
            <table style={this.getStyle()} className="contract-menu">
                <tbody>
                {this.getActions()}
                </tbody>
            </table>
        );
    },

    handleWriteOffClick: function(e) {
        console.log('write off clicked!');
    },

    handleCashbackClick: function(e) {
        console.log('cashback clicked!');
    }
});

module.exports = ContractMenu;