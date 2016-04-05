import React from 'react';
import ContractCreator from './contract-creator.js';
import Contract from './contract.js';
import {getContracts} from '../../actions/contract_actions.js';

export default React.createClass({
  getDefaultProps: function() {
    return {
      clientId: 0
    };
  },
  getInitialState: function() {
    return {
      contracts: [],
      contractCreatorVisible: false
    };
  },
  setContracts: function(contracts) {
    this.setState({
      contracts
    });
  },
  reloadData: function(callback) {
    getContracts(
      this.props.clientId, 
      this.setContracts, 
      callback
    );
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
        <Contract 
          reloadContractList={this.reloadData}
          reloadClient={this.props.reloadClient}
          contract={contract}
          key={contract.id} />
      );
    }, this);

    return (
      <div className='contract-list'>
        <h4 className='pull-left'>Контракты:</h4>
          <div className='contract-toolbar'>
            <i  style={this.state.contractCreatorVisible ? {display: 'none'} : null}
                className='pull-right icon-plus icon-2x icon-btn add-contract-btn'
                onClick={this.openContractCreator}/>
          </div>
        <div style={{clear: 'both'}}>
          <ContractCreator 
            clientId={this.props.clientId}
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

