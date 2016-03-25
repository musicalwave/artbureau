var React = require('react');
var Property = require('./client-property.js');
var ClientToolbar = require('./client-toolbar.js');
var ContractList = require('../contract/contract-list.js');
var Utils = require('../../utils/utils.js');

var Client = React.createClass({
    getDefaultProps: function() {
        return {
            id: 0,
            url: "/client"
        };
    },
    clientToState: function(client) {
        var startDate = client.firstContractDate !== null
                        ? moment(client.firstContractDate).format("DD-MM-YYYY")
                        : "";
        return {
            fname: client.fname,
            pname: client.pname,
            lname: client.lname,
            bdate: client.bdate,
            phone1: client.phone1,
            email: client.email,
            startDate: startDate,
            balance: client.balance,
            activeContractsCount: client.countActiveContracts,
            contractsCount: client.countContracts,
            total: client.total };
    },
    stateToClient: function(state) {
        return {
            id: this.props.id,
            fname: this.state.fname,
            lname: this.state.lname,
            pname: this.state.pname,
            phone1: this.state.phone1,
            email: this.state.email,
            bdate: this.state.bdate
        };
    },
    getInitialState: function() {
        return {
            fname: "",
            pname: "",
            lname: "",
            bdate: "",
            phone1: "",
            email: "",
            startDate: "",
            balance: "",
            activeContractsCount: "",
            contractsCount: "",
            total: "",
            editMode: false,
            savedState: {}
        }
    },

    propChanged: function(propName, propValue) {
        var newState = this.state;
        newState[propName] = propValue;
        this.setState(newState);
    },

    editHandler: function() {
        this.setState({
            savedState: {
                fname: this.state.fname,
                pname: this.state.pname,
                lname: this.state.lname,
                bdate: this.state.bdate,
                phone1: this.state.phone1,
                email: this.state.email,
                editMode: false,
                savedState: {}
            },
            editMode: true
        });
    },

    saveHandler: function() {
        $.ajax({
            url: this.props.url,
            method: "POST",
            data: this.stateToClient(this.state),
            success: function(client) {
                var newState = this.clientToState(client);
                newState.editMode = false;
                newState.savedState = {};
                this.setState(newState);
            }.bind(this),
            error: function() {
                console.log('error!');
                console.error(this.props.url, status, err.toString());
                this.revertHandler();
            }.bind(this)
        });
    },

    revertHandler: function() {
        this.setState(this.state.savedState);
    },

    render: function() {
        
        return (
            <div className="client">
                <div className="client-toolbar-box">
                    <h2>{this.state.fname + " " + this.state.lname}</h2>            
                    <ClientToolbar editMode={this.state.editMode}
                                   editHandler={this.editHandler}
                                   saveHandler={this.saveHandler}
                                   revertHandler={this.revertHandler} />
                </div>

                <table className="info-table client-info-table">
                    <tbody>
                        <Property outerHandler={this.propChanged}
                                  editMode={this.state.editMode}
                                  name={"fname"}
                                  title={"Имя"}
                                  value={this.state.fname}/>
                        <Property outerHandler={this.propChanged} 
                                  editMode={this.state.editMode} 
                                  name={"pname"}
                                  title={"Отчество"}
                                  value={this.state.pname}/>                    
                        <Property outerHandler={this.propChanged} 
                                  editMode={this.state.editMode} 
                                  name={"lname"}
                                  title={"Фамилия"}
                                  value={this.state.lname}/>
                        <Property outerHandler={this.propChanged} 
                                  editMode={this.state.editMode} 
                                  type={"text"}
                                  name={"bdate"}
                                  title={"День рождения"}
                                  value={this.state.bdate}
                                  isDate={true}/>
                        <Property outerHandler={this.propChanged} 
                                  editMode={this.state.editMode} 
                                  type={"text"}
                                  name={"phone1"}
                                  title={"Телефон"}
                                  value={this.state.phone1}/>
                        <Property outerHandler={this.propChanged} 
                                  editMode={this.state.editMode} 
                                  type={"text"}
                                  name={"email"}
                                  title={"email"}
                                  value={this.state.email}/>
                        <Property outerHandler={this.propChanged} 
                                  type={"text"}
                                  name={"startDate"}
                                  title={"Занимается с"}
                                  value={this.state.startDate}/>
                        <Property outerHandler={this.propChanged} 
                                  type={"number"}
                                  name={"balance"}
                                  title={"Баланс"}
                                  value={this.state.balance}/>                              
                        <Property outerHandler={this.propChanged} 
                                  type={"number"}
                                  name={"activeContractsCount"}
                                  title={"Активных договоров"}
                                  value={this.state.activeContractsCount}/>
                        <Property outerHandler={this.propChanged} 
                                  type={"number"}
                                  name={"contractsCount"}
                                  title={"Всего договоров"}
                                  value={this.state.contractsCount}/>
                        <Property outerHandler={this.propChanged} 
                                  type={"number"}
                                  name={"total"}
                                  title={"На сумму"}
                                  value={this.state.total}/>
                    </tbody>
                </table>

                <ContractList reloadClient={this.reloadData} 
                              clientId={this.props.id} 
                              url="/do/client/contracts"/>

            </div>
        );
    },
    reloadData: function() {
        $.ajax({
            url: this.props.url,
            data: {id: this.props.id},
            success: function(client) {
                this.setState(this.clientToState(client));
            }.bind(this),
            error: Utils.logAjaxError.bind(this, this.props.url)
        });
    },
    componentDidMount: function() {
        this.reloadData();
    }
});

module.exports = Client;
