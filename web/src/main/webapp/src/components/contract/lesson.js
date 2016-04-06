import React from 'react';
import moment from 'moment';
import ContractItemAction from './contract-item-action.js';
import {
  dropSeconds, 
  eventToString
} from '../../utils/utils.js';
import {
  burnLesson, 
  conductLesson, 
  restoreLesson, 
  updateLesson
} from '../../actions/lesson_actions.js'; 

export default React.createClass({
  getInitialState: function() {
    return {
      editMode:        false,
      date:            moment(this.props.lesson.date).format("DD-MM-YYYY"),
      eventId:         this.props.lesson.eventId,
      availableEvents: this.getAvailableEvents(moment(this.props.lesson.date))
    };
  },
  getAvailableEvents: function(moment) {
    return this.props.teacherEvents.filter(event => 
      event.weekday === moment.isoWeekday());
  },
  getActions: function() {
    var lessonId = this.props.lesson.id;
    var callback = this.props.reloadClientAndContracts;

    var conductAction = <ContractItemAction key="conductAction"
                                            clickHandler={conductLesson.bind(null, lessonId, callback)}
                                            iconName="icon-check" />;
    var burnAction    = <ContractItemAction key="burnAction"
                                            clickHandler={burnLesson.bind(null, lessonId, callback)}
                                            iconName="icon-fire" />;
    var restoreAction = <ContractItemAction key="restoreAction"
                                            clickHandler={restoreLesson.bind(null, lessonId, callback)}
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
    updateLesson(
      this.props.lesson.id,
      this.state.date,
      this.state.eventId,
      this.props.reloadClientAndContracts
    );
    this.cancel();
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
      fields.push(
        <td key="date">
          <input ref="dateInput"
                 type="text"
                 onChange={$.noop}
                 value={this.state.date} />
        </td>);
      fields.push(
        <td colSpan="2" key="event">
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
        <td className="action-cell">{this.getActions()}</td>
      </tr>
    );
  },
  componentDidUpdate: function() {
    if(this.state.editMode) {
      var weekdays = this.props.teacherEvents.map(event => event.weekday);

      $(this.refs.dateInput).datepicker({
        firstDay: 1,
        dateFormat: "dd-mm-yy",
        onSelect: date => this.dateChanged(date),
        beforeShowDay: date => [weekdays.indexOf(moment(date).isoWeekday()) !== -1]
      });

      $(this.refs.eventInput).select2({
        data: this.state.availableEvents.map(event => {
          return  {
            id: event.id,
            text: eventToString(event)
          }
        })
      });

      $(this.refs.eventInput).on("change", this.eventIdChanged);
    }
  }
});

