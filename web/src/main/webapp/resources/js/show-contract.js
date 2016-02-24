function getClientId() {
    var pathParts = window.location.pathname.split('/');
    return parseInt(pathParts[pathParts.length - 1]); 
}

var Property = React.createClass({
    getDefaultProps: function() {
        return {
            editMode: false,
            type: "text",
            name: "prop",
            title: "prop",
            value: "value",
            isDate: false,
            outerHandler: function() {}
        };
    },
    handleValueChange: function(e) {
        this.props.outerHandler(this.props.name, e.target.value);
    },
    render: function() {
        var valueField = this.props.editMode
                         ? <input type={this.props.type} 
                                  value={this.props.value}                                  
                                  onChange={this.handleValueChange}
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

var ClientToolbar = React.createClass({
    render: function() {
        var toolbar = this.props.editMode 
              ? <div className="client-toolbar">
                  <i onClick={this.props.saveHandler} className="icon-save icon-2x icon-btn"/>
                  <i onClick={this.props.revertHandler} className="icon-rotate-left icon-2x icon-btn"/>
                </div>
              : <div className="client-toolbar">
                  <i onClick={this.props.editHandler} className="icon-edit icon-2x icon-btn"/>                         
                </div>;
        return toolbar;
    }
})

var Client = React.createClass({
    clientToState: function(client) {
        return {
            fname: client.fname,
            pname: client.pname,
            lname: client.lname,
            bdate: client.bdate,
            phone1: client.phone1,
            email: client.email,
            startDate: moment(client.firstContractDate).format("DD-MM-YYYY"),
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
                                  value={this.state.activeContractsCount}/>
                        <Property outerHandler={this.propChanged} 
                                  type={"number"}
                                  name={"total"}
                                  title={"На сумму"}
                                  value={this.state.total}/>
                    </tbody>
                </table>

                <ContractList clientId={this.props.id} url="/do/client/contracts"/>

            </div>
        );
    },
    componentDidMount: function() {
        $.ajax({
            url: this.props.url,
            data: {id: this.props.id},
            success: function(client) {
                this.setState(this.clientToState(client));
            }.bind(this),
            error: function(xhr, status, err) {
                console.log('error!');
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        })
    }
});

var ContractList = React.createClass({
    getInitialState: function() {
        return {
            contracts: []
        };
    },
    render: function() {
        var contracts = this.state.contracts.map(function(contract) {
            return (
                <Contract contract={contract} key={contract.id}/>
            );
        });

        return (
            <div className="contract-list">
                <h4>Контракты:</h4>
                {contracts}
            </div>    
        );
    },
    componentDidMount: function() {
        $.ajax({
            url: this.props.url,
            data: {
                clientId: this.props.clientId
            },
            success: function(contracts) {
                this.setState({
                    contracts: contracts
                });
            }.bind(this),
            error: function(xhr, status, err) {
                console.log('error!');
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    }
});

var WidgetCollapser = React.createClass({
    getDefaultProps: function() {
        return {
            title: "Default title"
        }  
    },
    render: function() {
        return(
            <div className="widget-header">

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

var ContractMenu = React.createClass({

    render: function() {

        var divStyle = this.props.visible
                       ? {top: this.props.top,
                          left: this.props.left}
                       : {display: "none"};

        return (
            <table style={divStyle} className="contract-menu"> 
                <tbody>               
                    <tr onClick={this.handleLockClick}>
                        <td><i className="icon-lock icon-2x"/></td>
                        <td><label>Заморозить</label></td>
                    </tr>
                    <tr onClick={this.handleWriteOffClick}>
                        <td><i className="icon-pencil icon-2x"/></td>
                        <td><label>Списать</label></td>
                    </tr>
                    <tr onClick={this.handleCashbackClick}>
                        <td><i className="icon-money icon-2x"/></td>
                        <td><label>Вернуть деньги</label></td>
                    </tr>
                    <tr onClick={this.handleDeleteClick}>
                        <td><i className="icon-remove icon-2x"/></td>
                        <td><label>Удалить</label></td>
                    </tr>
                </tbody>
            </table>
        );
    },

    handleLockClick: function(e) {
        console.log('lock clicked!');
    },

    handleWriteOffClick: function(e) {
        console.log('write off clicked!');
    },

    handleCashbackClick: function(e) {
        console.log('cashback clicked!');
    },

    handleDeleteClick: function(e) {
        console.log('delete clicked!');
    },
});

var Contract = React.createClass({

    createLessonElement: function(lesson) {
        return (
            <Lesson lesson={lesson} key={lesson.id}/>
        );
    },

    createPaymentElement: function(payment) {
        return (
            <Payment payment={payment} key={payment.id}/>
        );
    },

    getInitialState: function() {
        return {
            menuVisible: false,
            menuTop: 0,
            menuLeft: 0
        };
    },

    render: function() {
        return (
            <div className="contract widget widget-closed box">

                <WidgetCollapser title={this.props.contract.teacherS + 
                                        " - " + 
                                        this.props.contract.typeS} />
                <div className="widget-content">
                    <i ref="menuButton" 
                       className="contract-menu-btn pull-right icon-ellipsis-horizontal icon-2x"
                       onClick={this.handleMenuBtnClick} />

                    <ContractMenu visible={this.state.menuVisible} 
                                  top={this.state.menuTop} 
                                  left={this.state.menuLeft}/>

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
                            <tr>
                                <th>Cумма:</th>
                                <td>{this.props.contract.price}</td>
                            </tr>
                            <tr>
                                <th>Баланс:</th>
                                <td>{this.props.contract.balance}</td>
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
                                url="/do/contract/lessons"
                                title="Занятия"
                                createItemElement={this.createLessonElement}/>

                    <ContractItemList contractId={this.props.contract.id} 
                                 url="/do/contract/payments"
                                 title="Платежи"
                                 createItemElement={this.createPaymentElement}/>

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
    componentDidMount: function() {
        // activate collapser
        $('.client').find('.widget .toolbar .widget-collapse').click(App.collapser);
        // make contract menu close on click outside
        $(document).click(function(e) {
           if($(e.target).closest(".contract-menu").length == 0 && 
              e.target != this.refs.menuButton)
             this.setState({menuVisible: false});
        }.bind(this));
    }
});

var ContractItemList = React.createClass({
    getInitialState: function() {
        return {
            items: []
        };
    },
    render: function() {
        var itemElems = this.state.items.map(function(item) {
            return this.props.createItemElement(item);
        }, this);

        return (
            <div className="contract-item-list">
                <h4>{this.props.title}:</h4>

                <table className="contract-item-table info-table">
                    <tbody>
                        {itemElems}
                    </tbody>
                </table>
                
                <i className="icon-plus icon-border icon-btn add-btn"/>
            
            </div>    
        );
    },
    componentDidMount: function() {
        $.ajax({
            url: this.props.url,
            data: {
                contractId: this.props.contractId
            },
            success: function(items) {
                this.setState({
                    items: items
                });
            }.bind(this),
            error: function(xhr, status, err) {
                console.log('error!');
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        })
    }
})

var Lesson = React.createClass({
    render: function() {
        var date = moment(this.props.lesson.date).format("DD-MM-YYYY");
        var startTime = moment(this.props.lesson.startTime, "HH:mm:ss").format("HH:mm");
        var finishTime = moment(this.props.lesson.finishTime, "HH:mm:ss").format("HH:mm");
        return(
            <tr>
                <td>{date}</td>
                <td>{startTime + " : " + finishTime}</td>
                <td>{this.props.lesson.roomName}</td>
                <td>{this.props.lesson.statusName}</td>
                <td className="action-cell">
                  <i className="action-icon icon-check icon-border icon-btn"/>
                  <i className="action-icon icon-fire icon-border icon-btn"/>
                  <i className="action-icon icon-remove icon-border icon-btn"/>
                </td>
                
            </tr>
        );
    }
});

var Payment = React.createClass({
    getPaymentStatus: function(payment) {
        if(this.props.payment.done)
            return "Проведён";
        else if (this.props.payment.planned)
            return "Запланирован";
        else
            return "Не определён";    
    },
    render: function() {
        var date = moment(this.props.payment.date).format("DD-MM-YYYY");
        
        return(
            <tr>
                <td>{date}</td>
                <td>{this.props.payment.value}</td>
                <td>{this.getPaymentStatus(this.props.payment)}</td>
                <td className="action-cell">
                  <i className="action-icon icon-check icon-border icon-btn"/>                  
                  <i className="action-icon icon-time icon-border icon-btn"/>
                  <i className="action-icon icon-remove icon-border icon-btn"/>
                </td>
                
            </tr>
        );
    }
});


ReactDOM.render(
    <Client id={getClientId()} url="/do/client"/>,
    document.getElementById('client-box')
);
