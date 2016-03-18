function getClientId() {
    var pathParts = window.location.pathname.split('/');
    return parseInt(pathParts[pathParts.length - 1]); 
}

function logAjaxError(url, xhr, status, err) {
    console.log('error!');
    console.error(url, status, err.toString());
}

function dropSeconds(time) {
        return moment(time, "HH:mm:ss").format("HH:mm")
}

function eventToString(event) {
    return moment().day(event.weekday).format('dd') + " " +
           dropSeconds(event.startTime) + " : " + 
           dropSeconds(event.finishTime) + 
           " (" + event.roomS + ")";
}

function coalesce(value, replace) {
    if (value === null || value === undefined)
        return replace;
    else 
        return value;
}

var Property = React.createClass({
    getDefaultProps: function() {
        return {
            editMode: false,
            type: "text",
            name: "prop",
            title: "prop",
            value: "",
            isDate: false,
            outerHandler: $.noop
        };
    },
    handleValueChange: function(e) {
        this.props.outerHandler(this.props.name, e.target.value);
    },
    render: function() {
        // prohibit manual input for date fields
        var valueChangeHandler = this.props.isDate
                                 ? $.noop
                                 : this.handleValueChange;

        var valueField = this.props.editMode
                         ? <input type={this.props.type} 
                                  value={this.props.value}                                  
                                  onChange={valueChangeHandler}
                                  ref="input"/> 
                         : this.props.value;
                       
        return (
            <tr>
                <th>{this.props.title}</th>
                <td>{valueField}</td>
            </tr>
        )
    },
    componentDidUpdate: function() {
        if(this.props.editMode && this.props.isDate) {
            $(this.refs.input).datepicker({
                dateFormat: "dd-mm-yy",
                onSelect: function() { 
                    // unfortunately, the datepicker's changes 
                    // cannot be tracked by the onChange handler
                    this.props.outerHandler(this.props.name, this.refs.input.value);
                }.bind(this)
            });
        }
    }
});

var ClientToolbarAction = React.createClass({
   getDefaultProps: function() {
        return {
            clickHandler: $.noop,
            iconName:     "icon-beer"    
        };
   },
   render: function() {
       return (
        <i onClick={this.props.clickHandler}
           className={"icon-2x icon-btn " + this.props.iconName} />
       );
   } 
});

var ClientToolbar = React.createClass({
    getDefaultProps: function() {
        return {
            saveHandler:   $.noop,
            revertHandler: $.noop,
            editHandler:   $.noop  
        };
    },
    getActions: function() {
        var actions = [];
        var saveAction   = <ClientToolbarAction key="saveAction" 
                                                clickHandler={this.props.saveHandler}
                                                iconName="icon-save" />;
        var revertAction = <ClientToolbarAction key="revertAction" 
                                                clickHandler={this.props.revertHandler}
                                                iconName="icon-rotate-left" />;
        var editAction   = <ClientToolbarAction key="editAction" 
                                                clickHandler={this.props.editHandler}
                                                iconName="icon-edit" />;
        
        if (this.props.editMode) {
            actions.push(saveAction);
            actions.push(revertAction);
        } else {
            actions.push(editAction);
        }

        return actions;
    },
    render: function() {
        return (
            <div className="client-toolbar">
                {this.getActions()}
            </div>
        );
    }
})

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
            error: logAjaxError.bind(this, this.props.url)
        });
    },
    componentDidMount: function() {
        this.reloadData();
    }
});

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
            error: logAjaxError.bind(this, this.props.url)
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
            error: logAjaxError.bind(this, "/do/contract/freeze")
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
            error: logAjaxError.bind(this, "/do/contract/unfreeze")
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
           error: logAjaxError.bind(this, "/do/contract/delete")
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
           error: logAjaxError.bind(this, "/do/contract/restore")
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
                    <ContractCreator visible={this.state.contractCreatorVisible}
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

var WidgetCollapser = React.createClass({
    getDefaultProps: function() {
        return {
            title: "Default title",
            classNameModifier: ""
        }  
    },
    render: function() {
        var className = "widget-header " + this.props.classNameModifier;
        
        return(
            <div className={className}>

                <h4>{this.props.title}</h4>

                <div className="toolbar no-padding">
                    <div className="btn-group">
                            <span className="btn btn-xs widget-collapse">
                                <i className="icon-angle-up" />
                            </span>
                    </div>                        
                </div>

            </div>
        );
    }
});

var LockForm = React.createClass({
    getDefaultProps: function() {
        return {
            visible: false,
            cancelHandler: $.noop
        };
    },
    getInitialState: function() {
        return {
            lockFrom: "",
            lockTo: "",
            submitDisabled: true
        };
    },
    lockFromChanged: function(lockFromValue) {
        this.setState({
            lockFrom: lockFromValue,
            submitDisabled: this.submitDisabled(lockFromValue, this.state.lockTo)
        });
    },
    lockToChanged: function(lockToValue) {
        this.setState({
            lockTo: lockToValue,
            submitDisabled: this.submitDisabled(this.state.lockFrom, lockToValue)
        });
    },
    handleSubmit: function(e) {
        e.preventDefault();
        this.props.lockHandler(this.props.contractId, 
                               this.state.lockFrom, 
                               this.state.lockTo);
        this.props.cancelHandler();
    },
    submitDisabled: function(lockFrom, lockTo) {
        return lockFrom === "" || lockTo === "";
    },
    render: function() {
        var style = this.props.visible
                    ? {}
                    : {display: "none"};

        return(
            <div style={style} className="lock-container">
                <i onClick={this.props.cancelHandler} className="icon-remove"/>
                <form onSubmit={this.handleSubmit}>
                    <div>
                        <label>Начало заморозки:</label>
                        <input ref="lockFromInput" 
                               type="text" 
                               value={this.state.lockFrom}/>
                    </div>
                    <div>
                        <label>Конец заморозки:</label>
                        <input ref="lockToInput" 
                               type="text" 
                               value={this.state.lockTo}/>
                    </div>
                    <div>
                        <button className="btn btn-xs" 
                                type="submit" 
                                disabled={this.state.submitDisabled}>
                            Заморозить!
                        </button>
                    </div>                    
                </form>
            </div>
        );
    },
    componentDidMount: function() {
        $(this.refs.lockFromInput).datepicker({
                dateFormat: "dd-mm-yy",
                onSelect: function(newValue) {                     
                    this.lockFromChanged(newValue);
                }.bind(this)
            });

        $(this.refs.lockToInput).datepicker({
                dateFormat: "dd-mm-yy",
                onSelect: function(newValue) {                     
                    this.lockToChanged(newValue);
                }.bind(this)
            });
    }
});

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

var Contract = React.createClass({

    getInitialState: function() {
        return {
            menuVisible: false,
            menuTop: 0,
            menuLeft: 0,
            lockFormVisible: false,
            paymentCreatorVisible: false,
            newPaymentButtonVisible: true,
            newPaymentDate: this.getNewPaymentDate(),
            newPaymentValue: this.getNewPaymentValue(),
            newPaymentStatus: 1
        };
    },

    reloadClientAndContracts: function() {
        this.props.reloadClient();
        this.props.reloadContractList(this.updatePaymentCreator);
    },

    // >> LESSONS

    createLessonElement: function(lesson) {
        return (
            <Lesson reloadClientAndContracts={this.reloadClientAndContracts}
                    teacherEvents={this.props.contract.teacherEvents}
                    lesson={lesson} 
                    key={lesson.id}/>
        );
    },

    // << LESSONS

    // >> PAYMENTS

    getNewPaymentDate: function() {
        var newPaymentDate;
        var contract = this.props.contract;
        var payments = contract.payments;
        if(payments.length === 0)
            newPaymentDate = moment(contract.date);
        else {
            newPaymentDate = moment(payments[payments.length - 1].date);
            newPaymentDate.add(contract.contractOptionModel.paymentInterval, 'days');
        }

        return newPaymentDate.format('DD-MM-YYYY');  
    },

    getNewPaymentValue: function() {
        var contract = this.props.contract;
        return contract.price - contract.moneyR - contract.moneyV;
    },

    newPaymentDateChanged: function(newDate) {
        this.setState({
            newPaymentDate: newDate
        });
    },

    newPaymentValueChanged: function(e) {
        this.setState({
            newPaymentValue: parseInt(e.target.value)
        });
    },

    newPaymentStatusChanged: function(e) {
        this.setState({
            newPaymentStatus: parseInt(e.target.value)
        });
    },

    showNewPaymentCreator: function() {
        var contract = this.props.contract;
        this.updatePaymentCreator();
        if(this.getNewPaymentValue() > 0 && 
           contract.payments.length < contract.contractOptionModel.maxPaymentsCount) {
            this.setState({
                paymentCreatorVisible: true
            });
        }
    },

    hideNewPaymentCreator: function() {
        this.setState({
            paymentCreatorVisible: false
        });
    },

    updatePaymentCreator: function() {
        this.setState({
            newPaymentDate: this.getNewPaymentDate(),
            newPaymentValue: this.getNewPaymentValue(),
            newPaymentStatus: 1
        });
    },

    createPaymentCreator: function() {
        
        return (
            <PaymentCreator contractId={this.props.contract.id}
                            value={this.state.newPaymentValue}
                            date={this.state.newPaymentDate} 
                            status={this.state.newPaymentStatus}
                            dateChanged={this.newPaymentDateChanged}
                            valueChanged={this.newPaymentValueChanged}
                            statusChanged={this.newPaymentStatusChanged}
                            reloadClientAndContracts={this.reloadClientAndContracts}
                            hideCreator={this.hideNewPaymentCreator} />
        );
    },

    createPaymentElement: function(payment) {
        return (
            <Payment reloadClientAndContracts={this.reloadClientAndContracts}
                     payment={payment} 
                     key={payment.id} />
        );
    },


    // << PAYMENTS

    // >> SCHEDULE

    createEventElement: function(event) {
        return (
            <Event event={event}
                   key={event.contractScheduleId}
                   contractId={this.props.contract.id}
                   teacherEvents={this.props.contract.teacherEvents}
                   reloadClientAndContracts={this.reloadClientAndContracts} />
        );
    },

    showNewEventCreator: function() {
        this.setState({
            eventCreatorVisible: true
        });
    },

    hideNewEventCreator: function() {
        this.setState({
            eventCreatorVisible: false
        });
    },

    createEventCreator: function() {
        return (
            <EventCreator contractId={this.props.contract.id}
                          teacherEvents={this.props.contract.teacherEvents}
                          reloadClientAndContracts={this.reloadClientAndContracts}
                          hideCreator={this.hideNewEventCreator} />
        );  
    },

    // << SCHEDULE

    getClassNameModifier: function() {        
        if(this.props.contract.deleted)
            return "deleted";
        if(this.props.contract.freezed)
            return "freezed";                
    },

    render: function() {

        var freezeDates = [];
        if(this.props.contract.freezed) {
            freezeDates.push(
                <tr key="freezeDate">
                    <th>Дата заморозки:</th>
                    <td>{moment(this.props.contract.freezeDate).format("DD-MM-YYYY")}</td>
                </tr>);

            freezeDates.push(
                <tr key="freezeFinishDate">
                    <th>Дата разморозки:</th>
                    <td>{moment(this.props.contract.freezeFinishDate).format("DD-MM-YYYY")}</td>
                </tr>);
        }

        return (
            <div className="contract widget widget-closed box">

                <LockForm contractId={this.props.contract.id}
                          visible={this.state.lockFormVisible}
                          cancelHandler={this.lockFormCancelHandler}
                          lockHandler={this.props.lockHandler}
                          ref="lockForm"/>

                <WidgetCollapser title={this.props.contract.teacherS + 
                                        " - " + 
                                        this.props.contract.typeS}
                                 classNameModifier={this.getClassNameModifier()}/>
                <div className={"widget-content " + this.getClassNameModifier()}>
                    <i ref="menuButton" 
                       className="contract-menu-btn pull-right icon-ellipsis-horizontal icon-2x"
                       onClick={this.handleMenuBtnClick} />

                    <ContractMenu visible={this.state.menuVisible} 
                                  top={this.state.menuTop} 
                                  left={this.state.menuLeft}
                                  locked={this.props.contract.freezed}                                  
                                  lockHandler={this.lockHandler}
                                  unlockHandler={this.unlockHandler}
                                  deleted={this.props.contract.deleted}
                                  deleteHandler={this.deleteHandler}
                                  restoreHandler={this.restoreHandler}/>

                    <table className="info-table contract-info-table">
                        <tbody>
                            <tr>
                                <th>Преподаватель:</th>
                                <td>{this.props.contract.teacherS}</td>
                            </tr>
                            <tr>
                                <th>Предмет:</th>
                                <td>{this.props.contract.typeS}</td>
                            </tr>
                            <tr>
                                <th>Тип:</th>
                                <td>{this.props.contract.contractTypeS}</td>
                            </tr>
                            <tr>
                                <th>Вариант:</th>
                                <td>{this.props.contract.contractOptionS}</td>
                            </tr>
                            <tr>
                                <th>Статус:</th>
                                <td>{this.props.contract.statusS}</td>
                            </tr>
                            <tr>
                                <th>Дата заключения:</th>
                                <td>{moment(this.props.contract.date).format("DD-MM-YYYY")}</td>
                            </tr>
                            {freezeDates}
                            <tr>
                                <th>Cумма:</th>
                                <td>{this.props.contract.price}</td>
                            </tr>
                            <tr>
                                <th>Баланс:</th>
                                <td>{this.props.contract.moneyR - this.props.contract.price}</td>
                            </tr>
                            <tr>
                                <th>Кол-во занятий:</th>
                                <td>{this.props.contract.countLessons}</td>
                            </tr>
                            <tr>
                                <th>Осталось занятий:</th>
                                <td>{this.props.contract.availableLessons}</td>
                            </tr>
                        </tbody>
                    </table>

                    <ContractItemList contractId={this.props.contract.id} 
                                 items={this.props.contract.schedule}
                                 title="Расписание"
                                 creatorVisible={this.state.eventCreatorVisible}
                                 showCreator={this.showNewEventCreator}
                                 createItemElement={this.createEventElement}
                                 createItemCreator={this.createEventCreator}/>

                    <ContractItemList contractId={this.props.contract.id} 
                                items={this.props.contract.lessons}
                                url="/do/contract/lessons"
                                title="Занятия"
                                createItemElement={this.createLessonElement}/>

                    <ContractItemList contractId={this.props.contract.id} 
                                 items={this.props.contract.payments}
                                 url="/do/contract/payments"
                                 title="Платежи"
                                 creatorVisible={this.state.paymentCreatorVisible}
                                 showCreator={this.showNewPaymentCreator}
                                 createItemElement={this.createPaymentElement}                                 
                                 createItemCreator={this.createPaymentCreator}/>

                </div>
                
            </div>
        );
    },
    handleMenuBtnClick: function(e) {
      // show or hide contract menu
      var $menuBtn = $(this.refs.menuButton);
      var menuPos = {
          top: $menuBtn.position().top + $menuBtn.height(),
          left: $menuBtn.position().left + $menuBtn.width() + 18
      };

      this.setState({menuVisible: !this.state.menuVisible,
                     menuTop: menuPos.top,
                     menuLeft: menuPos.left});
    },
    lockFormCancelHandler: function() {
        this.setState({lockFormVisible: false});
    },
    lockHandler: function() {
        this.setState({lockFormVisible: true,
                       menuVisible: false});
    },
    unlockHandler: function() {
        this.setState({menuVisible: false});
        this.props.unlockHandler(this.props.contract.id);
    },
    deleteHandler: function() {
        this.setState({menuVisible: false});
        this.props.deleteHandler(this.props.contract.id);
    },
    restoreHandler: function() {
        this.props.restoreHandler(this.props.contract.id);  
    },
    componentDidMount: function() {
        // activate collapser
        $('.client').find('.widget .toolbar .widget-collapse').click(App.collapser);
        // make contract menu close on click outside
        $(document).click(function(e) {
           if($(e.target).closest(".contract-menu").length === 0 && 
              e.target != this.refs.menuButton)
             this.setState({menuVisible: false});
        }.bind(this));
        // make lock form close on click outside
        $(document).click(function(e) {
           if(this.state.lockFormVisible && 
              // click somewhere on the form
              $(e.target).closest('.lock-container').length === 0 &&
              // click on the next/prev month on the datepicker
              $(e.target).closest('.ui-datepicker-header').length === 0) {
             this.setState({lockFormVisible: false});
           }           
        }.bind(this));
    }
});

var ContractItemAction = React.createClass({
   getDefaultProps: function() {
       return {
            clickHandler: $.noop,
            iconName: "icon-beer"
       };
   },
   render: function() {
       return <i onClick={this.props.clickHandler}
                 className={"action-icon icon-border icon-btn " + this.props.iconName}/>;
   } 
});

var ContractItemList = React.createClass({
    getDefaultProps: function() {
        return {
          items: [],
          creatorVisible: false,
          createItemCreator: $.noop,
          showCreator: $.noop,
          createItemElement: $.noop,
          title: "Title"
        };
    },
    render: function() {
        var itemElems = this.props.items.map(function(item) {
            return this.props.createItemElement(item);
        }, this);        

        var creator;
        if(this.props.creatorVisible)
            creator = this.props.createItemCreator();

        var newItemButtonStyle = {};
        if(this.props.creatorVisible)
            newItemButtonStyle = {display: "none"};

        return (
            <div className="contract-item-list">
                <h4>{this.props.title}:</h4>

                <table className="contract-item-table info-table">
                    <tbody>
                        {itemElems}
                        {creator}
                    </tbody>
                </table>
                
                <i style={newItemButtonStyle} 
                   onClick={this.props.showCreator} 
                   className="icon-plus icon-border icon-btn add-btn"/>
            
            </div>
        );
    },
})

var Event = React.createClass({
    getInitialState: function() {
        return {
            editMode: false,
            eventId: this.props.event.id
        };
    },
    edit: function() {
        this.setState({
            editMode: true,
            eventId: this.props.event.id
        });
    },
    cancel: function() {
        this.setState(this.getInitialState());
    },
    update: function() {
        console.log('schedule event ' + this.props.event.contractScheduleId + ' updated!');
        console.log('new event id: ' + this.state.eventId);
        $.ajax({
            url:     "/do/contract/schedule/update",
            method:  "POST",
            data:    {
                       contractId: this.props.contractId,
                       contractScheduleId: this.props.event.contractScheduleId,
                       eventId: this.state.eventId
                     },
            success: this.props.reloadClientAndContracts,
            error:   logAjaxError.bind(this, "/do/contract/schedule/update")
        });

        this.cancel();
    },
    delete: function() {
        console.log('schedule event' + this.props.event.contractScheduleId + ' deleted!');
        $.ajax({
            url:     "/do/contract/schedule/delete",
            method:  "POST",
            data:    {
                       contractId: this.props.contractId,
                       contractScheduleId: this.props.event.contractScheduleId 
                     },
            success: this.props.reloadClientAndContracts,
            error:   logAjaxError.bind(this, "/do/contract/schedule/delete")
        });
    },
    getActions: function() {
        var editAction   = <ContractItemAction
                                key="editAction"
                                iconName="icon-pencil"
                                clickHandler={this.edit}/>;
        var deleteAction = <ContractItemAction 
                                key="deleteAction"
                                iconName="icon-trash"
                                clickHandler={this.delete}/>;
        var updateAction   = <ContractItemAction
                                key="saveAction"
                                iconName="icon-save"
                                clickHandler={this.update}/>;
        var cancelAction = <ContractItemAction 
                                key="cancelAction"
                                iconName="icon-remove"
                                clickHandler={this.cancel}/>;
        if(this.state.editMode)
            return [updateAction, cancelAction];
        else                                
            return [editAction, deleteAction];
    },
    render: function() {
        
        var fields = [];
        if (this.state.editMode) {
            fields.push(<td key="eventId" colSpan="4">
                            <input type="text" 
                                   ref="eventInput" 
                                   value={this.state.eventId}
                                   onChange={$.noop}/>
                        </td>);
        } else {
            fields.push(<td key="weekday">{this.props.event.weekdayS}</td>);
            fields.push(<td key="start">{this.props.event.startTime}</td>);
            fields.push(<td key="finish">{this.props.event.finishTime}</td>);
            fields.push(<td key="room">{this.props.event.roomS}</td>);
        }

        return(
            <tr>
                {fields}
                <td className="action-cell">
                  {this.getActions()}
                </td>
                
            </tr>
        );
    },
    componentDidUpdate: function() {
        $(this.refs.eventInput).select2({
            data: this.props.teacherEvents.map(
                    function(event) {
                        return {
                          id: event.id,
                          text: eventToString(event)
                        };
                    }),
            minimumResultsForSearch: Infinity
        });

        $(this.refs.eventInput).on("change", this.eventIdChanged);
    },
    eventIdChanged: function(e) {
        this.setState({
          eventId: parseInt(e.target.value)
        });
    }
});

var Lesson = React.createClass({

    getInitialState: function() {
        return {
            editMode:        false,
            date:            moment(this.props.lesson.date).format("DD-MM-YYYY"),
            eventId:         this.props.lesson.eventId,
            availableEvents: this.getAvailableEvents(moment(this.props.lesson.date))
        };
    },

    getAvailableEvents: function(moment) {
        return this.props.teacherEvents.filter(
            function(event) {
                return event.weekday === moment.isoWeekday();
            }
        );
    },

    getActions: function() {

        var conductAction = <ContractItemAction key="conductAction" 
                                                clickHandler={this.conduct} 
                                                iconName="icon-check" />;
        var burnAction    = <ContractItemAction key="burnAction" 
                                                clickHandler={this.burn}
                                                iconName="icon-fire" />;
        var restoreAction = <ContractItemAction key="restoreAction"
                                                clickHandler={this.restore}
                                                iconName="icon-refresh" />;
        var editAction    = <ContractItemAction key="editAction"
                                                clickHandler={this.edit}
                                                iconName="icon-pencil" />;
        var saveAction    = <ContractItemAction key="saveAction" 
                                                clickHandler={this.save} 
                                                iconName="icon-save" />;
        var cancelAction  = <ContractItemAction key="cancelAction" 
                                                clickHandler={this.cancel} 
                                                iconName="icon-remove" />;

        var actions = [];

        if(this.state.editMode) {
            actions.push(saveAction);
            actions.push(cancelAction);
        } else {
            actions.push(editAction);

            if(this.props.lesson.statusId === 1) {
                actions.push(conductAction);
                actions.push(burnAction);
            } else {
                actions.push(restoreAction);
            }
        }

        return actions;
    },

    burn: function() {
        console.log('burn lesson ' + this.props.lesson.id);  
        $.ajax({
            url:     "/do/lessons/burn",
            method:  "POST",
            data:    { id: this.props.lesson.id },
            success: this.props.reloadClientAndContracts,
            error:   logAjaxError.bind(this, "/do/lessons/burn")
        });
    },

    conduct: function() {
        console.log('conduct lesson ' + this.props.lesson.id);  
        $.ajax({
            url:     "/do/lessons/conduct",
            method:  "POST",
            data:    { id: this.props.lesson.id },
            success: this.props.reloadClientAndContracts,
            error:   logAjaxError.bind(this, "/do/lessons/conduct")
        });
    },

    restore: function() {
        console.log('restore lesson ' + this.props.lesson.id);  
        $.ajax({
            url:     "/do/lessons/restore",
            method:  "POST",
            data:    { id: this.props.lesson.id },
            success: this.props.reloadClientAndContracts,
            error:   logAjaxError.bind(this, "/do/lessons/restore")
        });
    },

    update: function(lessonId, date, eventId) {
      console.log('lesson ' + lessonId + ' updated');

      $.ajax({
          url: "/do/lesson/update",
          method: "POST",
          data: {
              lessonId: lessonId,
              date: date,
              eventId: eventId
          },
          success: this.props.reloadClientAndContracts,
          error: logAjaxError.bind(this, "/do/lesson/update")
      })
    },


    edit: function() {
      this.setState({
          editMode: true,
          date: moment(this.props.lesson.date).format("DD-MM-YYYY"),
          eventId: this.props.lesson.eventId,
          availableEvents: this.getAvailableEvents(moment(this.props.lesson.date))
      });
    },

    cancel: function() {
      this.setState({
          editMode: false
      });
    },

    save: function() {
      this.update(this.props.lesson.id,
                  this.state.date, 
                  this.state.eventId);
        
      this.setState({
          editMode: false
      });
    },

    dateChanged: function(newDate) {
      var availableEvents = this.getAvailableEvents(moment(newDate, "DD-MM-YYYY"));
      this.setState({
          date: newDate,
          availableEvents: availableEvents,
          eventId: availableEvents[0].id
      });
    },

    eventIdChanged: function(e) {
      this.setState({
          eventId: parseInt(e.target.value)
      });
    },

    render: function() {
        var fields = [];
        if (this.state.editMode) {
            
            fields.push(<td key="date">
                            <input ref="dateInput" 
                                   type="text" 
                                   onChange={$.noop} 
                                   value={this.state.date} />
                        </td>);
            fields.push(<td colSpan="2" key="event">
                            <input ref="eventInput" 
                                   type="text" 
                                   onChange={$.noop} 
                                   value={this.state.eventId} />
                        </td>);
        } else {
            var date = moment(this.props.lesson.date).format("DD-MM-YYYY (dd)");
            var startTime = dropSeconds(this.props.lesson.startTime);
            var finishTime = dropSeconds(this.props.lesson.finishTime);

            fields.push(<td key="date">{date}</td>);
            fields.push(<td key="time">{startTime + " : " + finishTime}</td>);
            fields.push(<td key="room">{this.props.lesson.roomName}</td>);
        }

        return(
            <tr>
                {fields}
                <td>{this.props.lesson.statusName}</td>
                <td className="action-cell">
                  {this.getActions()}
                </td>
                
            </tr>
        );
    },
    componentDidUpdate: function() {
        if(this.state.editMode) {
            var weekdays = this.props.teacherEvents.map(function(event) {
                return event.weekday;
            });

            $(this.refs.dateInput).datepicker({
                dateFormat: "dd-mm-yy",
                onSelect: function(date) {
                    this.dateChanged(date);
                }.bind(this),
                beforeShowDay: function(date) {
                    // show only available dates
                    return [weekdays.indexOf(moment(date).weekday()) !== -1];
                }
            });

            $(this.refs.eventInput).select2({
                data: this.state.availableEvents.map(
                        function(event) {
                            return {
                              id: event.id,
                              text: eventToString(event)
                            };
                        })
            });

            $(this.refs.eventInput).on("change", this.eventIdChanged);
        }
    }    
});

var Payment = React.createClass({
    getInitialState: function() {
        return {
            editMode: false,
            date: this.getFormattedDate(),
            value: this.props.payment.value
        };
    },
    
    getPaymentStatus: function(payment) {
        if(this.props.payment.done)
            return "Проведён";
        else if (this.props.payment.planned)
            return "Запланирован";
        else
            return "Не определён";    
    },

    getFormattedDate: function() {
        return moment(this.props.payment.date).format("DD-MM-YYYY");
    },

    getActions: function() {

        var commitAction  = <ContractItemAction key="commitAction" 
                                                clickHandler={this.commit} 
                                                iconName="icon-check" />;
        var restoreAction = <ContractItemAction key="restoreAction" 
                                                clickHandler={this.restore} 
                                                iconName="icon-refresh" />;
        var editAction    = <ContractItemAction key="editAction" 
                                                clickHandler={this.edit} 
                                                iconName="icon-pencil" />;
        var updateAction    = <ContractItemAction key="saveAction" 
                                                clickHandler={this.update} 
                                                iconName="icon-save" />;
        var cancelAction  = <ContractItemAction key="cancelAction" 
                                                clickHandler={this.cancel} 
                                                iconName="icon-remove" />;
        var deleteAction  = <ContractItemAction key="deleteAction" 
                                                clickHandler={this.delete} 
                                                iconName="icon-trash" />;

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
        console.log('payment ' + this.props.payment.id + ' commited!');
        $.ajax({
            url:     "/do/payment/commit",
            method:  "POST",
            data:    { paymentId: this.props.payment.id },
            success: this.props.reloadClientAndContracts,
            error:   logAjaxError.bind(this, "/do/payment/commit")
        });
    },

    restore: function() {
        console.log('payment ' + this.props.payment.id + ' restored!');
        $.ajax({
            url:     "/do/payment/restore",
            method:  "POST",
            data:    { paymentId: this.props.payment.id },
            success: this.props.reloadClientAndContracts,
            error:   logAjaxError.bind(this, "/do/payment/restore")
        });        
    },

    delete: function() {
        console.log('payment ' + this.props.payment.id + ' deleted!');
        $.ajax({
            url:     "/do/payment/delete",
            method:  "POST",
            data:    { paymentId: this.props.payment.id },
            success: this.props.reloadClientAndContracts,
            error:   logAjaxError.bind(this, "/do/payment/delete")
        });        
    },

    update: function() {
        console.log('payment ' + this.props.payment.id + " updated!");

        $.ajax({
            url:     "/do/payment/update",
            method:  "POST",
            data:    {
                       paymentId: this.props.payment.id,
                       date: moment(this.state.date, "DD-MM-YYYY").format('x'),
                       value: this.state.value
                     },
            success: this.props.reloadClientAndContracts,
            error:   logAjaxError.bind(this, "/do/payment/update")
        });

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

        var dateRow = this.state.editMode
                      ? <td>
                            <input ref="dateInput" 
                                   type="text" 
                                   defaultValue={this.getFormattedDate()} />
                        </td>
                      : <td>{this.getFormattedDate()}</td>;

        var valueRow = this.state.editMode
                       ? <td>
                             <input onChange={this.valueChanged} 
                                    type="number" 
                                    defaultValue={this.props.payment.value} />
                         </td>
                       : <td>{this.props.payment.value}</td>;

        return(
            <tr>
                {dateRow}
                {valueRow}
                <td>{this.getPaymentStatus(this.props.payment)}</td>
                <td className="action-cell">                                    
                  {this.getActions()}                  
                </td>
                
            </tr>
        );
    },

    componentDidUpdate: function() {
        $(this.refs.dateInput).datepicker({
            dateFormat: "dd-mm-yy",
            onSelect: function(date) {
                this.dateChanged(date);
            }.bind(this)
        });
    }
});

var EventCreator = React.createClass({
    getInitialState: function() {
        return {
            eventId: this.props.teacherEvents[0].id  
        };
    },
    insertEvent: function() {
        console.log('new schedule event inserted!');
        console.log(this.props.contractId);
        console.log(this.state.eventId);
        $.ajax({
            url:     "/do/contract/schedule/insert",
            method:  "POST",
            data:    {
                      contractId: this.props.contractId,
                      eventId: this.state.eventId
                     },
            success: this.props.reloadClientAndContracts,
            error:   logAjaxError.bind(this, "/do/contract/schedule/insert")
        });
        this.props.hideCreator();
    },
    eventIdChanged: function(e) {
        this.setState({
          eventId: parseInt(e.target.value)
        });
    },
    render: function() {
        return(
            <tr>
                <td>
                    <input onChange={$.noop} 
                           ref="eventInput" 
                           type="text" 
                           value={this.state.eventId} />
                </td>
                <td className="action-cell">
                    <ContractItemAction key="insertEventAction" 
                                        clickHandler={this.insertEvent} 
                                        iconName="icon-save" />
                    <ContractItemAction key="hideCreatorAction" 
                                        clickHandler={this.props.hideCreator} 
                                        iconName="icon-remove" />
                </td>
            </tr>
        );
    },
    componentDidMount: function() {
        $(this.refs.eventInput).select2({
            data: this.props.teacherEvents.map(
                function(event) {
                    return {
                      id: event.id,
                      text: eventToString(event)
                    };
                }),
            minimumResultsForSearch: Infinity
        });

        $(this.refs.eventInput).on("change", this.eventIdChanged);
    }
});

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
            error: logAjaxError.bind(this, "/do/payment/insert")
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

var ContractCreator = React.createClass({
    getInitialState: function() {
        return {
            lessonTypeId: 0,
            optionId: 0,
            contractTypeId: 0,
            teacherTypeId: 0,
            startDate: "",
            scheduleEventIds: [],
            discountId: 0,
            lessonPrice: 0,
            totalPrice: 0,
            payments: [],
            lessonTypes: [],
            options: [],
            contractTypes: [],
            teacherTypes: [],
            teacherWeekdays: [],
            events: [],
            discounts: []
        };
    },
    render: function() {

        var contractCreatorStyle = this.props.visible
                                 ? {}
                                 : {display: "none"};

        var payments = this.state.payments.map(function(payment) {
            return <NewPayment key={payment.id}
                               payment={payment}
                               dateChanged={this.paymentDateChanged}
                               valueChanged={this.paymentValueChanged}
                               plannedChanged={this.paymentPlannedChanged} />
                                 
        }.bind(this));                               
        
        return (
            <div style={contractCreatorStyle} className="contract-creator">
                <i className="pull-right icon-remove icon-btn close-contract-creator-button"
                   onClick={this.props.close}/>
                <table className="info-table contract-info-table">
                      <tbody>
                          <tr>
                              <th>Направление:</th>
                              <td><input ref="lessonType" value={this.state.lessonTypeId}/></td>
                          </tr>
                          <tr>
                              <th>Вариант:</th>
                              <td><input ref="option" value={this.state.optionId}/></td>
                          </tr>
                          <tr>
                              <th>Тип:</th>
                              <td><input ref="contractType" value={this.state.contractTypeId}/></td>
                          </tr>
                          <tr>
                              <th>Педагог:</th>
                              <td><input ref="teacherType" value={this.state.teacherTypeId}/></td>
                          </tr>
                          <tr>
                              <th>Дата начала:</th>
                              <td><input ref="startDate" value={this.state.startDate}/></td>
                          </tr>
                          <tr>
                              <th>Расписание:</th>
                              <td><input ref="schedule" value={this.state.scheduleEventIds.join(',')}/></td>
                          </tr>
                          <tr>
                              <th>Скидка:</th>
                              <td><input ref="discount" value={this.state.discountId}/></td>
                          </tr>
                          <tr>
                              <th>Стоимость одного занятия:</th>
                              <td>{this.state.lessonPrice}</td>
                          </tr>
                          <tr>
                              <th>Общая сумма:</th>
                              <td>{this.state.totalPrice}</td>
                          </tr>
                      </tbody>
                </table>
                <h4>Платежи:</h4>
                <h5 className="pull-left">количество:</h5>
                <input className="pull-left payment-counter" type="number" 
                       min="0" max={this.getMaxNumOfPayments()}
                       value={this.state.payments.length}
                       onChange={this.numOfPaymentsChanged}/>
                <table className="info-table new-payments-table">
                    <thead>
                        <tr>
                            <td>Дата</td>
                            <td>Сумма</td>
                            <td>Планируемый</td>
                        </tr>
                    </thead>
                    <tbody>
                        {payments}
                    </tbody>
                </table>
                <button className="create-contract-button btn btn-default pull-right"
                        onClick={this.createButtonClicked}>
                    Создать!
                </button>
                <div style={{clear: "right"}}/>
            </div>
        );
    },
    preparePayment: function(payment) {
          return {
              id:      payment.id,
              value:   payment.value,
              date:    moment(payment.date, 'DD-MM-YYYY').format('x'),
              planned: payment.planned ? 1 : 0,
              done:    payment.planned ? 0 : 1,
          }
    },
    createButtonClicked: function() {
        var option = this.getItemById(this.state.options, this.state.optionId);

        $.ajax({
            url: "/do/contract/create",
            method: "POST",
            contentType: 'application/json',
            data: JSON.stringify({
                clientId:         getClientId(),
                teacherTypeId:    this.state.teacherTypeId,
                contractOptionId: this.state.optionId,
                countLessons:     option.lessonCount,
                date:             moment(this.state.startDate, 'DD-MM-YYYY').format('x'),
                price:            this.state.totalPrice,
                contractType:     this.state.contractTypeId,
                discount:         this.state.optionId,
                days:             this.state.scheduleEventIds.join(','),
                payments:         this.state.payments.map(this.preparePayment)
            }),
            success: function(r) {
                console.log(r);
                console.log('success!');
                this.props.reload();
                this.props.close();
            }.bind(this),
            error: logAjaxError.bind("/do/contract/create")
        });
    },
    getMaxNumOfPayments: function() {
        var option = this.getItemById(this.state.options, this.state.optionId);
        if(option)
            return option.maxPaymentsCount;
        else
            return 0;
    },
    createPayments: function(startDate, numberOfPayments, totalValue, interval) {
        var payments = [];
        var singlePaymentValue = Math.floor(totalValue / numberOfPayments);
        for(var i = 0; i < numberOfPayments; i++) {
            payments.push({
                id: i,
                date: startDate.format('DD-MM-YYYY'),
                value: singlePaymentValue,
                planned: true
            });
            startDate = startDate.add(interval, 'days');
        }
        if(payments.length !== 0)
            payments[0].value += (totalValue - singlePaymentValue * numberOfPayments);
        
        return payments;
    },
    createSelect2Item: function(rawItem) {
        return {
            id:   rawItem.id,
            text: rawItem.name
        }  
    },
    getItemById: function(items, id) {
        var matched = items.filter(function(item) {
                          return item.id === id;
                      });
        return matched[0];
    },
    getWeekdays: function(events) {
        var weekdays = events.reduce(function(prevValue, event) {
            if (prevValue.indexOf(event.weekday) === -1)
                return prevValue.concat(event.weekday);
            else
                return prevValue;
        }, []);
        weekdays.sort(function(a,b) {
            return a - b;
        });
        return weekdays;
    },
    getStartDate: function(fromDate, weekdays) {
        var startDate = fromDate
        while(weekdays.indexOf(startDate.isoWeekday()) === -1)
            startDate.add(1, 'days');
        return startDate;
    },
    getLessonPrice: function(teacherType, contractTypeId) {
        switch(contractTypeId) {
            case 1:
                return teacherType.pPrice;
            case 2:
                return teacherType.gPrice;
            case 3:
                return teacherType.dPrice;
            case 4:
                return teacherType.aPrice;
            default:
                return 0;
        }  
    },
    getTotalPrice: function(lessonCount, lessonPrice, discount) {
        return lessonCount * lessonPrice * (1 - discount / 100);
    },
    filterTeacherTypesByLessonType: function(teacherTypes, lessonTypeId) {
        return teacherTypes.filter(function(tt) {
                   return tt.typeId === lessonTypeId
               });
    },
    filterEventsByTeacher: function(events, teacherId) {
        return events.filter(function(event) {
                   return event.teacherId === teacherId; 
               });
    },
    filterEventsByWeekday: function(events, weekday) {
        return events.filter(function(event) {
                   return event.weekday === weekday;
               });
    },
    numOfPaymentsChanged(e) {
        var numberOfPayments = parseInt(e.target.value);
        var option = this.getItemById(this.state.options, this.state.optionId);
        var startDate = moment(this.state.startDate, 'DD-MM-YYYY');
        var payments = this.createPayments(startDate, 
                                           numberOfPayments, 
                                           this.state.totalPrice, 
                                           option.paymentInterval);
        this.setState({
            payments: payments
        });
    },
    paymentDateChanged: function(paymentId, newDate) {        
        var payments = this.state.payments.map(function(payment) {
            if(payment.id === paymentId)
                return Object.assign({}, payment, {date: newDate});
            else
                return payment;
        });
        this.setState({
            payments: payments
        });
    },
    paymentValueChanged: function(paymentId, newValue) {
        var payments = this.state.payments.map(function(payment) {
            if(payment.id === paymentId)
                return Object.assign({}, payment, {value: newValue});
            else
                return payment;
        });
        this.setState({
            payments: payments
        });
    },
    paymentPlannedChanged: function(paymentId, newPlanned) {
        var payments = this.state.payments.map(function(payment) {
            if(payment.id === paymentId)
                return Object.assign({}, payment, {planned: newPlanned});
            else
                return payment;
        });
        this.setState({
            payments: payments
        });
    },
    lessonTypeChanged: function(e) {
        var lessonTypeId = parseInt(e.target.value);
        
        // defaults                                                    
        var teacherTypeId   = 0;
        var teacherEvents   = [];
        var teacherWeekdays = [];
        var startDate       = moment();
        var contractType    = this.getItemById(this.state.contractTypes, this.state.contractTypeId);            
        var discount        = this.getItemById(this.state.discounts, this.state.discountId);
        var option          = this.getItemById(this.state.options, this.state.optionId);
        var lessonPrice     = 0;
        var totalPrice      = 0;
        var payments        = [];

        // actual data
        var filteredTeacherTypes = 
                this.filterTeacherTypesByLessonType(this.state.teacherTypes, 
                                                    lessonTypeId);

        if (filteredTeacherTypes.length !== 0) {
            var teacherType     = filteredTeacherTypes[0];
            teacherTypeId       = teacherType.id;
            teacherEvents       = this.filterEventsByTeacher(
                                    this.state.events, 
                                    teacherType.teacherId);
            if (teacherEvents.length !== 0) {
                teacherWeekdays = this.getWeekdays(teacherEvents)
                startDate       = this.getStartDate(moment(), teacherWeekdays);                
                lessonPrice     = this.getLessonPrice(teacherType, contractType.id);
                totalPrice      = this.getTotalPrice(option.lessonCount, lessonPrice, discount.id);
                payments        = this.createPayments(moment(startDate), 
                                                      this.state.payments.length, 
                                                      totalPrice, 
                                                      option.paymentInterval);
            }
        }
        
        this.setState({
            lessonTypeId:         lessonTypeId,
            teacherTypeId:        teacherTypeId,
            startDate:            startDate.format('DD-MM-YYYY'),
            lessonPrice:          lessonPrice,
            totalPrice:           totalPrice,
            filteredTeacherTypes: filteredTeacherTypes,
            teacherEvents:        teacherEvents,
            teacherWeekdays:      teacherWeekdays,
            scheduleEventIds:     [],
            payments:             payments
        });
    },
    optionChanged: function(e) {
        var option = this.getItemById(this.state.options, parseInt(e.target.value));
        var totalPrice = 
            this.getTotalPrice(
                option.lessonCount, 
                this.state.lessonPrice, 
                this.state.discountId);
        var payments = this.createPayments(moment(this.state.startDate, 'DD-MM-YYYY'),
                                           1, totalPrice, option.paymentInterval);

        this.setState({
            optionId:    option.id,
            totalPrice:  totalPrice,
            payments:    payments
        });
    },
    contractTypeChanged: function(e) {
        var contractType = this.getItemById(this.state.contractTypes, parseInt(e.target.value));
        var option = this.getItemById(this.state.options, this.state.optionId);
        var teacherType = this.getItemById(this.state.teacherTypes, this.state.teacherTypeId);
        var lessonPrice = this.getLessonPrice(teacherType, contractType.id);
        var totalPrice = 
            this.getTotalPrice(
                option.lessonCount, 
                lessonPrice, 
                this.state.discountId);
        var payments = this.createPayments(moment(this.state.startDate, 'DD-MM-YYYY'),
                                           this.state.payments.length, 
                                           totalPrice, 
                                           option.paymentInterval);

        this.setState({
            contractTypeId: contractType.id,
            lessonPrice:    lessonPrice,
            totalPrice:     totalPrice,
            payments:       payments
        });
    },
    discountChanged: function(e) {
        var discountId = parseInt(e.target.value);
        var contractType = this.getItemById(this.state.contractTypes, this.state.contractTypeId);
        var option = this.getItemById(this.state.options, this.state.optionId);
        var teacherType = this.getItemById(this.state.teacherTypes, this.state.teacherTypeId);
        var lessonPrice = this.getLessonPrice(teacherType, contractType.id);

        var totalPrice = 
            this.getTotalPrice(
                option.lessonCount, 
                lessonPrice, 
                discountId);

        var payments = this.createPayments(moment(this.state.startDate, 'DD-MM-YYYY'),
                                           this.state.payments.length, 
                                           totalPrice, 
                                           option.paymentInterval);                

        this.setState({
            discountId:  discountId,
            lessonPrice: lessonPrice,
            totalPrice:  totalPrice,
            payments:    payments
        });  
    },
    teacherTypeChanged: function(e) {
        var contractType    = this.getItemById(this.state.contractTypes, this.state.contractTypeId);            
        var discount        = this.getItemById(this.state.discounts, this.state.discountId);
        var option          = this.getItemById(this.state.options, this.state.optionId);
        var teacherTypeId   = parseInt(e.target.value);
        var teacherType     = this.getItemById(this.state.teacherTypes, teacherTypeId);
        var teacherEvents   = this.filterEventsByTeacher(
                                this.state.events, 
                                teacherType.teacherId);
        var teacherWeekdays = [];
        var startDate       = moment();
        var lessonPrice     = this.getLessonPrice(teacherType, contractType.id);;
        var totalPrice      = this.getTotalPrice(option.lessonCount, lessonPrice, discount.id);;
        var payments        = [];

        if (teacherEvents.length !== 0) {
            teacherWeekdays = this.getWeekdays(teacherEvents)
            startDate       = this.getStartDate(moment(), teacherWeekdays);
        }

        payments            = this.createPayments(moment(startDate), 
                                                  this.state.payments.length, 
                                                  totalPrice, 
                                                  option.paymentInterval);

        this.setState({
           teacherTypeId:    teacherTypeId,
           teacherEvents:    teacherEvents,
           teacherWeekdays:  teacherWeekdays, 
           startDate:        startDate.format('DD-MM-YYYY'),
           lessonPrice:      lessonPrice,
           totalPrice:       totalPrice,
           scheduleEventIds: [],
           payments:         payments
        });
    },
    scheduleChanged: function(e) {
        this.setState({
           scheduleEventIds: e.target.value.split(',') 
        });
    },
    startDateChanged: function(date) {
        var option = this.getItemById(this.state.options, this.state.optionId);
        var payments = this.createPayments(moment(date, 'DD-MM-YYYY'),
                                           this.state.payments.length, 
                                           this.state.totalPrice, 
                                           option.paymentInterval)
        this.setState({
            startDate: date,
            payments:  payments
        });
    },
    initTeacherTypeSelect: function(elem, filteredTeacherTypes) {
        
        filteredTeacherTypes = coalesce(filteredTeacherTypes, []);

        elem.select2({
                data: filteredTeacherTypes.map(function(tt) {
                    return {
                        id: tt.id,
                        text: tt.teacherName  
                    };
                })
            });
    },
    initScheduleSelect: function(elem, events) {

        events = coalesce(events, []);

        elem.select2({
                data: events.map(function(event) {
                    return {
                        id: event.id,
                        text: eventToString(event)  
                    };
                }),
                multiple: "multiple"
            });
    },
    initStartDatePicker: function(elem) {
        elem.datepicker({
                firstDay: 1,
                dateFormat: "dd-mm-yy",
                onSelect: this.startDateChanged
            });
    },
    setDatePickerWeekdays: function(elem, weekdays) {
        weekdays = coalesce(weekdays, []);
        elem.datepicker("option", "beforeShowDay", function(date) {
            return [weekdays.indexOf(moment(date).isoWeekday()) !== -1 &&
                    moment(date).isSameOrAfter(moment())];
        });
    },
    componentDidMount: function() {
        $.when(
            $.get("/do/types/all"),
            $.get("/do/options"),
            $.get("/do/contractTypes"),
            $.get("/do/discounts"),
            $.get("/do/events/all"),
            $.get("/do/teacherTypes")
        ).done(function(lessonTypes, 
                        options, 
                        contractTypes,
                        discounts,
                        events,
                        teacherTypes) {

            // default values for selects

            var lessonType      = lessonTypes[0][0];
            // hate this line
            var filteredTeacherTypes = 
                this.filterTeacherTypesByLessonType(teacherTypes[0], 
                                                    lessonType.id);
            var teacherType     = filteredTeacherTypes[0];
            var teacherEvents   = this.filterEventsByTeacher(events[0], teacherType.teacherId);
            var teacherWeekdays = this.getWeekdays(teacherEvents)
            var startDate       = this.getStartDate(moment(), teacherWeekdays);
            var contractType    = contractTypes[0][0];            
            var discount        = discounts[0][0];
            var option          = options[0][0];
            var lessonPrice     = this.getLessonPrice(teacherType, contractType.id);
            var totalPrice      = this.getTotalPrice(option.lessonCount, lessonPrice, discount.id);

            var payments        = this.createPayments(moment(startDate), 
                                                      1, 
                                                      totalPrice, 
                                                      option.paymentInterval);
            this.setState({
                lessonTypeId:         lessonType.id,
                optionId:             option.id,
                contractTypeId:       contractType.id,
                teacherTypeId:        teacherType.id,
                discountId:           discount.id,
                startDate:            startDate.format('DD-MM-YYYY'),
                lessonPrice:          lessonPrice,
                totalPrice:           totalPrice,
                lessonTypes:          lessonTypes[0],
                options:              options[0],
                contractTypes:        contractTypes[0],
                teacherTypes:         teacherTypes[0],
                events:               events[0],
                discounts:            discounts[0], 
                filteredTeacherTypes: filteredTeacherTypes,
                teacherEvents:        teacherEvents,
                teacherWeekdays:      teacherWeekdays,
                payments:             payments
            });

            // initialize selects

            $(this.refs.lessonType).select2({
                    data: lessonTypes[0].map(this.createSelect2Item)
                });
            $(this.refs.lessonType).on("change", this.lessonTypeChanged);

            $(this.refs.option).select2({
                data: options[0].map(this.createSelect2Item)
            });
            $(this.refs.option).on("change", this.optionChanged);

            $(this.refs.contractType).select2({
                data: contractTypes[0].map(this.createSelect2Item)
            });
            $(this.refs.contractType).on("change", this.contractTypeChanged);

            $(this.refs.discount).select2({
                data: discounts[0].map(function(discount) {
                    return {
                        id: discount.id,
                        text: discount.name + ' [' + discount.id + '%]'
                    }
                })
            });
            $(this.refs.discount).on("change", this.discountChanged);
            
            this.initTeacherTypeSelect($(this.refs.teacherType), filteredTeacherTypes);
            $(this.refs.teacherType).on("change", this.teacherTypeChanged);

            this.initScheduleSelect($(this.refs.schedule), teacherEvents);
            $(this.refs.schedule).on("change", this.scheduleChanged);
            
            this.initStartDatePicker($(this.refs.startDate));
            this.setDatePickerWeekdays($(this.refs.startDate), teacherWeekdays);
                        
        }.bind(this));        
    },
    componentDidUpdate: function() {
        // update selects' data
        this.initTeacherTypeSelect($(this.refs.teacherType), this.state.filteredTeacherTypes);
        this.initScheduleSelect($(this.refs.schedule), this.state.teacherEvents);
        this.setDatePickerWeekdays($(this.refs.startDate), this.state.teacherWeekdays);
    }
});

ReactDOM.render(
    <Client id={getClientId()} url="/do/client"/>,
    document.getElementById('client-box')
);
