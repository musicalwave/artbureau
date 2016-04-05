import React from 'react';
import moment from 'moment';
import NewPayment from './new-payment.js';
import {eventToString, coalesce} from '../../utils/utils.js';
import {createContract} from '../../actions/contract_actions.js';

export default React.createClass({
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
  getPayments: function() {
    return this.state.payments.map(payment => 
      <NewPayment 
        key={payment.id}
        payment={payment}
        dateChanged={this.paymentDateChanged}
        valueChanged={this.paymentValueChanged}
        plannedChanged={this.paymentPlannedChanged}/>);
  },
  render: function() {
    return (
      <div style={this.props.visible ? null : {display: 'none'}} 
           className="contract-creator">
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
            {this.getPayments()}
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
  reloadAndClose: function() {
    this.props.reload();
    this.props.close();
  },
  createButtonClicked: function() {
    var option = this.getItemById(this.state.options, this.state.optionId);
    var data = JSON.stringify({
      clientId:         this.props.clientId,
      teacherTypeId:    this.state.teacherTypeId,
      contractOptionId: this.state.optionId,
      countLessons:     option.lessonCount,
      date:             moment(this.state.startDate, 'DD-MM-YYYY').format('x'),
      price:            this.state.totalPrice,
      contractType:     this.state.contractTypeId,
      discount:         this.state.optionId,
      days:             this.state.scheduleEventIds.join(','),
      payments:         this.state.payments.map(this.preparePayment)
    });
    
    createContract(data, this.reloadAndClose);        
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
    return items.filter(item => item.id === id)[0];
  },
  getWeekdays: function(events) {
    var weekdays = events.reduce(function(prevValue, event) {
      if (prevValue.indexOf(event.weekday) === -1)
        return prevValue.concat(event.weekday);
      else
        return prevValue;
    }, []);
    weekdays.sort((a,b) => a - b);
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
    return teacherTypes.filter(tt => tt.typeId === lessonTypeId);
  },
  filterEventsByTeacher: function(events, teacherId) {
    return events.filter(event => event.teacherId === teacherId);
  },
  filterEventsByWeekday: function(events, weekday) {
    return events.filter(event => event.weekday === weekday);
  },
  numOfPaymentsChanged(e) {
    var numberOfPayments = parseInt(e.target.value);
    var option = this.getItemById(this.state.options, this.state.optionId);
    var startDate = moment(this.state.startDate, 'DD-MM-YYYY');
    var payments = this.createPayments(
      startDate,
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
      teacherEvents = 
        this.filterEventsByTeacher(
          this.state.events,
          teacherType.teacherId
      );
      if (teacherEvents.length !== 0) {
        teacherWeekdays = this.getWeekdays(teacherEvents)
        startDate       = this.getStartDate(moment(), teacherWeekdays);
        lessonPrice     = this.getLessonPrice(teacherType, contractType.id);
        totalPrice      = this.getTotalPrice(option.lessonCount, lessonPrice, discount.id);
        payments = 
          this.createPayments(moment(startDate),
            this.state.payments.length,
            totalPrice,
            option.paymentInterval
        );
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
        this.state.discountId
    );
    var payments = this.createPayments(
      moment(this.state.startDate, 'DD-MM-YYYY'),
      1, 
      totalPrice, 
      option.paymentInterval
    );

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
        this.state.discountId
    );
    var payments = this.createPayments(moment(this.state.startDate, 'DD-MM-YYYY'),
      this.state.payments.length,
      totalPrice,
      option.paymentInterval
    );
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
    ).done(
      function(
        lessonTypes,
        options,
        contractTypes,
        discounts,
        events,
        teacherTypes) {

          // default values for selects

          var lessonType      = lessonTypes[0][0];
          var filteredTeacherTypes =
            this.filterTeacherTypesByLessonType(
              teacherTypes[0],
              lessonType.id
          );
          var teacherType     = filteredTeacherTypes[0];
          var teacherEvents   = this.filterEventsByTeacher(events[0], teacherType.teacherId);
          var teacherWeekdays = this.getWeekdays(teacherEvents)
          var startDate       = this.getStartDate(moment(), teacherWeekdays);
          var contractType    = contractTypes[0][0];
          var discount        = discounts[0][0];
          var option          = options[0][0];
          var lessonPrice     = this.getLessonPrice(teacherType, contractType.id);
          var totalPrice      = this.getTotalPrice(option.lessonCount, lessonPrice, discount.id);

          var payments = this.createPayments(
            moment(startDate),
            1,
            totalPrice,
            option.paymentInterval
          );
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
                id:   discount.id,
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

