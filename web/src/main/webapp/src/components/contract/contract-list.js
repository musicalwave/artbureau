import React from 'react';
import ContractCreator from './contract-creator.js';
import Contract from './contract.js';
import {logAjaxError} from '../../utils/utils.js';

export default React.createClass({
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
            error: logAjaxError.bind(this, this.props.url)
        });
    },
    reloadDataAndClient: function() {
        this.reloadData();
        this.props.reloadClient();
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
                <Contract reloadContractList={this.reloadData}
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

