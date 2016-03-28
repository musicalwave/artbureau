var React = require('react');
var ContractCreator = require('./contract-creator.js');
var Contract = require('./contract.js');
var Utils = require('../../utils/utils.js');

var ContractList = React.createClass({
    getDefaultProps: function() {
        return {
            clientId: 0,
            url: "/client/contracts"
        };
    },
    getInitialState: function() {
        return {
            contracts: [],
            contractCreatorVisible: false
        };
    },
    reloadData: function(callback) {
       $.ajax({
            url: this.props.url,
            data: {
                clientId: this.props.clientId
            },
            success: function(contracts) {
                this.setState({
                    contracts: contracts
                });
                if(callback)
                    callback();
            }.bind(this),
            error: Utils.logAjaxError.bind(this, this.props.url)
        });
    },
    reloadDataAndClient: function() {
        this.reloadData();
        this.props.reloadClient();
    },
    lockHandler: function(contractId, lockFrom, lockTo) {
        $.ajax({
            url: "/do/contract/freeze",
            method: "POST",
            data: {
                contractId: contractId,
                lockFrom:   moment(lockFrom, 'DD-MM-YYYY').format('x'),
                lockTo:     moment(lockTo, 'DD-MM-YYYY').format('x')
            },
            success: function() {
                this.reloadData();
            }.bind(this),
            error: Utils.logAjaxError.bind(this, "/do/contract/freeze")
        });
    },
    unlockHandler: function(contractId) {
        $.ajax({
            url: "/do/contract/unfreeze",
            method: "POST",
            data: {
                contractId: contractId
            },
            success: function() {
                this.reloadData();
            }.bind(this),
            error: Utils.logAjaxError.bind(this, "/do/contract/unfreeze")
        });
    },
    deleteHandler: function(contractId) {
        $.ajax({
           url: "/do/contract/delete",
           method: "POST",
           data: {
               contractId: contractId
           },
           success: function() {
               this.reloadData();
               this.props.reloadClient();
           }.bind(this),
           error: Utils.logAjaxError.bind(this, "/do/contract/delete")
        });
    },
    restoreHandler: function(contractId) {
        $.ajax({
           url: "/do/contract/restore",
           method: "POST",
           data: {
               contractId: contractId
           },
           success: function() {
               this.reloadData();
               this.props.reloadClient();
           }.bind(this),
           error: Utils.logAjaxError.bind(this, "/do/contract/restore")
        });
    },
    openContractCreator: function() {
        this.setState({
            contractCreatorVisible: true
        })
    },
    closeContractCreator: function() {
        this.setState({
            contractCreatorVisible: false
        })
    },
    render: function() {
        var contracts = this.state.contracts.map(function(contract) {
            return (
                <Contract lockHandler={this.lockHandler}
                          unlockHandler={this.unlockHandler}
                          deleteHandler={this.deleteHandler}
                          restoreHandler={this.restoreHandler}
                          reloadContractList={this.reloadData}
                          reloadClient={this.props.reloadClient}
                          contract={contract}
                          key={contract.id} />
            );
        }, this);

        var newContractButtonStyle = this.state.contractCreatorVisible
                                 ? {display: "none"}
                                 : {};

        return (
            <div className="contract-list">
                <h4 className="pull-left">Контракты:</h4>
                    <div className="contract-toolbar">
                        <i  style={newContractButtonStyle}
                            className="pull-right icon-plus icon-2x icon-btn add-contract-btn"
                            onClick={this.openContractCreator}/>
                    </div>
                <div style={{clear: "both"}}>
                    <ContractCreator clientId={this.props.clientId}
                                     visible={this.state.contractCreatorVisible}
                                     close={this.closeContractCreator}
                                     reload={this.reloadDataAndClient}/>
                    {contracts}
                </div>
            </div>
        );
    },
    componentDidMount: function() {
        this.reloadData();
    }
});

module.exports = ContractList;
