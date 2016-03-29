var React = require('react');
var ContractMenu = require('./contract-menu');
var WidgetCollapser = require('../widget-collapser.js');
var LockForm = require('./lock-form.js');
var Payment = require('./payment.js');
var PaymentCreator = require('./payment-creator.js');
var Lesson = require('./lesson.js');
var Event = require('./event.js');
var EventCreator = require('./event-creator.js');
var ContractItemList = require('./contract-item-list.js');

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

        var contract = this.props.contract;

        var freezeDates = [];
        if(contract.freezed) {
            freezeDates.push(
                <tr key="freezeDate">
                    <th>Дата заморозки:</th>
                    <td>{moment(contract.freezeDate).format("DD-MM-YYYY")}</td>
                </tr>);

            freezeDates.push(
                <tr key="freezeFinishDate">
                    <th>Дата разморозки:</th>
                    <td>{moment(contract.freezeFinishDate).format("DD-MM-YYYY")}</td>
                </tr>);
        }

        var shiftCount = contract.countShifts;
        var maxShifts  = contract.contractOptionModel.maxShifts;
        var shiftStr   = shiftCount + '(' + (maxShifts - shiftCount) + ')';

        var contractDate = moment(contract.date).format('DD-MM-YYYY');
        var contractTitle = contract.teacherS +
                            " - " +
                            contract.typeS +
                            " - " +
                            contractDate;

        return (
            <div className="contract widget widget-closed box">

                <LockForm contractId={contract.id}
                          visible={this.state.lockFormVisible}
                          cancelHandler={this.lockFormCancelHandler}
                          lockHandler={this.props.lockHandler}
                          ref="lockForm"/>

                <WidgetCollapser title={contractTitle}
                                 classNameModifier={this.getClassNameModifier()}/>
                <div className={"widget-content " + this.getClassNameModifier()}>
                    <i ref="menuButton"
                       className="contract-menu-btn pull-right icon-ellipsis-horizontal icon-2x"
                       onClick={this.handleMenuBtnClick} />

                    <ContractMenu visible={this.state.menuVisible}
                                  top={this.state.menuTop}
                                  left={this.state.menuLeft}
                                  locked={contract.freezed}
                                  lockHandler={this.lockHandler}
                                  unlockHandler={this.unlockHandler}
                                  deleted={this.props.contract.deleted}
                                  deleteHandler={this.deleteHandler}
                                  restoreHandler={this.restoreHandler}/>

                    <table className="info-table contract-info-table">
                        <tbody>
                            <tr>
                                <th>Преподаватель:</th>
                                <td>{contract.teacherS}</td>
                            </tr>
                            <tr>
                                <th>Предмет:</th>
                                <td>{contract.typeS}</td>
                            </tr>
                            <tr>
                                <th>Тип:</th>
                                <td>{contract.contractTypeS}</td>
                            </tr>
                            <tr>
                                <th>Вариант:</th>
                                <td>{contract.contractOptionS}</td>
                            </tr>
                            <tr>
                                <th>Статус:</th>
                                <td>{contract.statusS}</td>
                            </tr>
                            <tr>
                                <th>Дата заключения:</th>
                                <td>{contractDate}</td>
                            </tr>
                            {freezeDates}
                            <tr>
                                <th>Cумма:</th>
                                <td>{contract.price}</td>
                            </tr>
                            <tr>
                                <th>Баланс:</th>
                                <td>{contract.balance}</td>
                            </tr>
                            <tr>
                                <th>Переносы:</th>
                                <td>{shiftStr}</td>
                            </tr>
                            <tr>
                                <th>Кол-во занятий:</th>
                                <td>{contract.countLessons}</td>
                            </tr>
                            <tr>
                                <th>Осталось занятий:</th>
                                <td>{contract.availableLessons}</td>
                            </tr>
                        </tbody>
                    </table>

                    <ContractItemList contractId={contract.id}
                                 items={contract.schedule}
                                 title="Расписание"
                                 creatorVisible={this.state.eventCreatorVisible}
                                 showCreator={this.showNewEventCreator}
                                 createItemElement={this.createEventElement}
                                 createItemCreator={this.createEventCreator}/>

                    <ContractItemList contractId={contract.id}
                                items={contract.lessons}
                                url="/do/contract/lessons"
                                title="Занятия"
                                createItemElement={this.createLessonElement}/>

                    <ContractItemList contractId={contract.id}
                                 items={contract.payments}
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

module.exports = Contract;
