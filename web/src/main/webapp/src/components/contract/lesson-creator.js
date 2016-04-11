import React from 'react';
import moment from 'moment';
import ContractItemAction from './contract-item-action.js';
import {createLesson} from '../../actions/lesson_actions.js';
import {eventToString} from '../../utils/utils.js';

export default React.createClass({
  getInitialState: function() {
    var date = this.getFirstAppropriateDate(this.props.schedule);
    var events = this.getEventsByWeekday(this.props.schedule, date.isoWeekday());
    return {
      events,
      eventId: events.length !== -1 ? events[0].id : 0,
      date: date.format('DD-MM-YYYY')
    };
  },
  createLesson: function() {
    createLesson(
      this.props.contractId,
      moment(this.state.date, 'DD-MM-YYYY').format('x'),
      this.state.eventId,
      this.props.reloadData
    );
    this.props.hide();
  },
  render: function() {
    return(
      <tr>
        <td>
          <input onChange={$.noop}
                 ref='dateInput'
                 type='text'
                 value={this.state.date} />
        </td>
        <td colSpan='3'>
          <input onChange={$.noop}
                 ref='eventInput'
                 type='text'
                 value={this.state.eventId}/>
        </td>
        <td className='action-cell'>
          <ContractItemAction key='createLessonAction'
                              clickHandler={this.createLesson}
                              iconName='icon-save' />
          <ContractItemAction key='hideCreatorAction'
                              clickHandler={this.props.hide}
                              iconName='icon-remove' />
        </td>
      </tr>
    );
  },
  eventChanged: function(eventId) {
    this.setState({
      eventId
    });
  },
  dateChanged: function(date) {
    this.setState({
      date,
      events: this.getEventsByWeekday(this.props.schedule, moment(date, 'DD-MM-YYYY').isoWeekday())
    });
  },
  getScheduleWeekdays: function(schedule) {
    return schedule.map(event => event.weekday);
  },
  getFirstAppropriateDate: function(schedule) {
    var date = moment();
    var weekdays = this.getScheduleWeekdays(schedule);
    if (weekdays && weekdays.length !== 0) 
      while (weekdays.indexOf(date.isoWeekday()) === -1)
        date.add(1, 'days');
    return date;
  },
  getEventsByWeekday: function(schedule, weekday) {
    return schedule.filter(event => event.weekday === weekday);
  },
  getEventSelectData: function() {
    var eventData = this.state.events.map(
      (event) => ({
        id: event.id,
        text: eventToString(event)
      }));
    return eventData;
  },
  initEventSelect: function() {
    $(this.refs.eventInput).select2({
      data: this.getEventSelectData(),
      minimumResultsForSearch: Infinity
    });
  },
  componentDidMount: function() {
    this.initEventSelect(); 
    $(this.refs.eventInput).on('change', this.eventChanged);

    $(this.refs.dateInput).datepicker({
      firstDay: 1,
      dateFormat: 'dd-mm-yy',
      onSelect: date => this.dateChanged(date),
      beforeShowDay: date => 
        [this.getScheduleWeekdays(this.props.schedule).indexOf(moment(date).isoWeekday()) !== -1]
    });
  },
  componentDidUpdate: function() {
    // recreate event select
    this.initEventSelect(); 
  }
});

