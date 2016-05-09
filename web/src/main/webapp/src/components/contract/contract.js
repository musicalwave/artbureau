import React from 'react';
import moment from 'moment';
import ContractMenu from './contract-menu';
import WidgetCollapser from '../widget-collapser.js';
import LockForm from './lock-form.js';
import Payment from './payment.js';
import PaymentCreator from './payment-creator.js';
import Lesson from './lesson.js';
import LessonCreator from './lesson-creator.js';
import ContractItemList from './contract-item-list.js';
import ContractScheduleEditor from './contract-schedule-editor.js';
import ContractLessonsEditor from './contract-lessons-editor.js';
import TeacherSchedule from './teacher-schedule.js';
import {
  deleteContract, 
  restoreContract,
  writeoffContract,
  cashbackContract,
  unlockContract
} from '../../actions/contract_actions';


export default React.createClass({
  getInitialState: function() {
    return {
      menuVisible: false,
      menuTop: 0,
      menuLeft: 0,
      lockFormVisible: false,
      paymentCreatorVisible: false,
      newPaymentDate: this.getNewPaymentDate(),
      newPaymentValue: this.getNewPaymentValue(),
      newPaymentStatus: 1,
      lessonCreatorVisible: false
    };
  },
  reloadClientAndContracts: function() {
    this.props.reloadClient();
    this.props.reloadContractList(this.updatePaymentCreator);
  },
  // >> LESSONS
  createLessonElement: function(lesson) {
    return (
      <Lesson 
        reloadClientAndContracts={this.reloadClientAndContracts}
        teacherEvents={this.props.contract.teacherEvents}
        lesson={lesson}
        key={lesson.id}/>
    );
  },
  createLessonCreator: function() {
    return (
      <LessonCreator 
        contractId={this.props.contract.id}
        schedule={this.props.contract.schedule}
        reloadData={this.reloadClientAndContracts}
        hide={this.hideLessonCreator} />
    );
  },
  showLessonCreator: function() {
    if (this.props.contract.contractOptionModel.arbitrary)
      this.setState({
        lessonCreatorVisible: true
      });
  },
  hideLessonCreator: function() {
    this.setState({
      lessonCreatorVisible: false
    });
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
      newPaymentDate:   this.getNewPaymentDate(),
      newPaymentValue:  this.getNewPaymentValue(),
      newPaymentStatus: 1
    });
  },
  createPaymentCreator: function() {
    return (
      <PaymentCreator 
        contractId={this.props.contract.id}
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
      <Payment 
        reloadClientAndContracts={this.reloadClientAndContracts}
        payment={payment}
        key={payment.id} />
    );
  },
  // << PAYMENTS
  getClassNameModifier: function() {
    if(this.props.contract.deleted)
      return "deleted";
    if(this.props.contract.freezed)
      return "freezed";
  },
  reloadLessons: function() {
    this.refs.lessonsEditor.reloadLessons();
  },
  getShiftStr: function(contract) {
    var shiftCount = contract.countShifts;
    var maxShifts  = contract.contractOptionModel.maxShifts;
    return shiftCount + ' (' + (maxShifts - shiftCount) + ')';
  },
  getContractDate: function(contract) {
    return moment(contract.date).format('DD-MM-YYYY');
  },
  getContractTitle: function(contract) {
    return contract.teacherS +
      " - " +
      contract.typeS +
      " - " +
      this.getContractDate(contract);
  },
  getFreezeDates: function(contract) {
    if (contract.freezed) 
      return [
        <tr key="freezeDate">
          <th>Дата заморозки:</th>
          <td>{moment(contract.freezeDate).format("DD-MM-YYYY")}</td>
        </tr>,
        <tr key="freezeFinishDate">
          <th>Дата разморозки:</th>
          <td>{moment(contract.freezeFinishDate).format("DD-MM-YYYY")}</td>
        </tr>
      ];
  },
  getWriteoff: function(contract) {
    if (contract.writeoff !== 0) 
      return (
        <tr>
          <th>Списано:</th>
          <td>{contract.writeoff}</td>
        </tr>
      );
  },
  getCashback: function(contract) {
    if (contract.cashback !== 0)
      return (
        <tr>
          <th>Возврат:</th>
          <td>
            {contract.cashback + ' (' + contract.fine + ')'}
          </td>
        </tr>
      );
  },
  render: function() {
    var contract = this.props.contract;
    return (
      <div className="contract widget widget-closed box">

        <LockForm 
          contractId={contract.id}
          visible={this.state.lockFormVisible}
          hide={this.hideLockForm}
          reloadData={this.reloadClientAndContracts}
          ref="lockForm"/>

        <WidgetCollapser title={this.getContractTitle(contract)}
                         classNameModifier={this.getClassNameModifier()}/>
        <div className={"widget-content " + this.getClassNameModifier()}>
          <i ref="menuButton"
             className="contract-menu-btn pull-right icon-ellipsis-horizontal icon-2x"
             onClick={this.menuButtonClickHandler} />

          <ContractMenu 
            contract={contract}
            visible={this.state.menuVisible}
            top={this.state.menuTop}
            left={this.state.menuLeft}
            openLockForm={this.openLockForm}
            hide={this.hideMenu}
            reloadData={this.reloadClientAndContracts} />

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
                <td>{this.getContractDate(contract)}</td>
              </tr>
              {this.getFreezeDates(contract)}
              <tr>
                <th>Cумма:</th>
                <td>{contract.price}</td>
              </tr>
              <tr>
                <th>Баланс:</th>
                <td>{contract.balance}</td>
              </tr>
              {this.getWriteoff(contract)}
              {this.getCashback(contract)}
              <tr>
                <th>Переносы:</th>
                <td>{this.getShiftStr(contract)}</td>
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

          <TeacherSchedule
            schedule={contract.teacherSchedule}
            title='Расписание преподавателя'/>

          <ContractScheduleEditor
            contractId={contract.id}
            schedule={contract.schedule}
            title='Расписание'
            reloadContracts={this.reloadContractList}
            reloadLessons={this.reloadLessons}/>

          <ContractLessonsEditor
            ref='lessonsEditor'
            contractId={contract.id}
            title='Занятия' 
            reloadClient={this.props.reloadClient}
            reloadContracts={this.props.reloadContractList}/>

          <ContractItemList 
            contractId={contract.id}
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
  menuButtonClickHandler: function(e) {
    // show or hide contract menu
    var $menuBtn = $(this.refs.menuButton);
    var menuPos = {
      top: $menuBtn.position().top + $menuBtn.height(),
      left: $menuBtn.position().left + $menuBtn.width() + 18
    };

    this.setState({
      menuVisible: !this.state.menuVisible,
      menuTop: menuPos.top,
      menuLeft: menuPos.left
    });
  },
  hideMenu: function() {
    this.setState({menuVisible: false});
  }, 
  hideLockForm: function() {
    this.setState({lockFormVisible: false});
  },
  openLockForm: function() {
    this.setState({
      lockFormVisible: true,
      menuVisible: false
    });
  },
  componentDidMount: function() {
    // activate collapser
    $('.client').find('.widget .toolbar .widget-collapse').click(App.collapser);
    // make contract menu close on click outside
    $(document).click(function(e) {
     if ($(e.target).closest(".contract-menu").length === 0 &&
        e.target != this.refs.menuButton)
       this.setState({menuVisible: false});
    }.bind(this));
    // make lock form close on click outside
    $(document).click(function(e) {
      if (this.state.lockFormVisible &&
       $(e.target).closest('.lock-container').length === 0 &&
       // click on the next/prev month on the datepicker
       $(e.target).closest('.ui-datepicker-header').length === 0) {
         this.setState({lockFormVisible: false});
      }
    }.bind(this));
  }
});

