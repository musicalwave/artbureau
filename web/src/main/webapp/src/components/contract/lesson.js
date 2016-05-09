import React from 'react';
import moment from 'moment';
import ContractItemAction from './contract-item-action.js';
import {
  dropSeconds,
  getRoomName,
  getRoomData,
  getLessonStatusName,
  getLessonStatusData,
  noop
} from '../../utils/utils.js';

export default React.createClass({
  getDefaultProps: function() {
    return {
      newLesson: false,
      lesson: null,
      rooms: [],
      statuses: [],
      editMode: false,
      update: noop,
      delete: noop,
      conduct: noop,
      burn: noop,
      restore: noop,
      dateChanged: noop,
      roomChanged: noop,
      fromTimeChanged: noop,
      toTimeChanged: noop,
      statusChanged: noop,
      revert: noop,
      remove: noop,
      save: noop
    };
  },
  getInitialState: function() {
    return {
      editMode: this.props.editMode,
      editorsInitialized: false,
      savedLesson: null
    };
  },
  getActions: function() {
    var conductAction = 
      <ContractItemAction 
        key='conductAction'
        clickHandler={this.conduct}
        iconName='icon-check' />;
    var burnAction = 
      <ContractItemAction 
        key='burnAction'
        clickHandler={this.burn}
        iconName='icon-fire' />;
    var restoreAction = 
      <ContractItemAction 
        key='restoreAction'
        clickHandler={this.restore}
        iconName='icon-refresh' />;
    var editAction = 
      <ContractItemAction 
        key='editAction'
        clickHandler={this.edit}
        iconName='icon-pencil' />;
    var deleteAction = 
      <ContractItemAction 
        key='deleteAction'
        clickHandler={this.delete}
        iconName='icon-trash' />;
    var saveAction = 
      <ContractItemAction 
        key='saveAction'
        clickHandler={this.save}
        iconName='icon-save' />;
    var cancelAction  = 
      <ContractItemAction 
        key='cancelAction'
        clickHandler={this.cancel}
        iconName='icon-remove' />;

    var actions = [];

    if(this.state.editMode) {
      actions.push(saveAction);
      actions.push(cancelAction);
    } else {
      actions.push(editAction);
      if (this.props.lesson.statusId === 1) {
        actions.push(conductAction);
        actions.push(burnAction);
      } else {
        actions.push(restoreAction);
      }
      actions.push(deleteAction);
    }

    return actions;
  },
  edit: function() {
    this.setState({
      editMode: true,
      savedLesson: Object.assign( // shallow copy
        {}, 
        this.props.lesson
      ) 
    });
  },
  cancel: function() {
    if (this.props.newLesson) {
      this.props.remove(this.props.lesson);
    } else {
      this.props.revert(this.state.savedLesson);
      this.setState({
        editMode: false,
        editorsInitialized: false,
        savedLesson: null
      });
    }
  },
  save: function() {
    this.setState({
      editMode: false,
      editorsInitialized: false,
      savedLesson: null
    });
    this.props.save(this.props.lesson);
  },
  burn: function() {
    this.props.burn(this.props.lesson);
  },
  conduct: function() {
    this.props.conduct(this.props.lesson);
  },
  restore: function() {
    this.props.restore(this.props.lesson);
  },
  delete: function() {
    console.log('delete lesson');
    this.props.delete(this.props.lesson);
  },
  dateChanged: function(date) {
    var parsedDate = parseInt(moment(date, 'DD-MM-YYYY').format('x'));
    this.props.dateChanged(this.props.lesson.id, parsedDate);
  },
  fromTimeChanged: function(e) {
    var fromTime = e.target.value;
    this.props.fromTimeChanged(this.props.lesson.id, fromTime);
  },
  toTimeChanged: function(e) {
    var toTime = e.target.value;
    this.props.toTimeChanged(this.props.lesson.id, toTime);
  },
  roomChanged: function(e) {
    var roomId = parseInt(e.target.value);
    this.props.roomChanged(this.props.lesson.id, roomId);
  },
  statusChanged: function(e) {
    var statusId = parseInt(e.target.value);
    this.props.statusChanged(this.props.lesson.id, statusId);
  },
  getEditorFields: function() {
    return [
      <td key='date' className='date'>
        <input 
          ref='date'
          onChange={noop}
          value={moment(this.props.lesson.date).format('DD-MM-YYYY')}/>
      </td>,
      <td key='fromTime' className='time'>
        <input 
          ref='fromTime'
          onChange={noop}
          value={dropSeconds(this.props.lesson.fromTime)}/>
      </td>,
      <td key='toTime' className='time'>
        <input 
          ref='toTime'
          onChange={noop}
          value={dropSeconds(this.props.lesson.toTime)}/>
      </td>,
      <td key='room' className='room'>
        <input 
          ref='room'
          type='text'
          onChange={noop}
          value={this.props.lesson.roomId}/>
      </td>,
      <td key='status' className='status'>
        <input 
          ref='status'
          type='text'
          onChange={noop}
          value={this.props.lesson.statusId}/>
      </td>
    ];
  },
  getViewerFields: function() {
    return [
      <td key='date'>{moment(this.props.lesson.date).format('DD-MM-YYYY (dd)')}</td>,
      <td key='fromTime'>{dropSeconds(this.props.lesson.fromTime)}</td>,    
      <td key='toTime'>{dropSeconds(this.props.lesson.toTime)}</td>,    
      <td key='room'>{getRoomName(this.props.rooms, this.props.lesson.roomId)}</td>,
      <td key='status'>{getLessonStatusName(this.props.statuses, this.props.lesson.statusId)}</td>
    ];
  },
  render: function() {
    if (this.props.lesson !== null)
      return (
        <tr className='contract-item'>
          {this.state.editMode ? 
            this.getEditorFields() : 
            this.getViewerFields()}
          <td className='action-cell'>{this.getActions()}</td>
        </tr>
      );
    else
      return <tr/>;
  },
  initEditors: function() {
    this.initEditorWidgets();
    this.bindEditorEvents();
    this.setState({
      editorsInitialized: true
    });
  },
  initEditorWidgets: function() {
    $(this.refs.date).datepicker({
      firstDay: 1,
      dateFormat: 'dd-mm-yy',
      onSelect: date => this.dateChanged(date),
    });
    $(this.refs.fromTime).timepicker({
      timeFormat: 'H:i',
      step: 15
    });
    $(this.refs.toTime).timepicker({
      timeFormat: 'H:i',
      step: 15
    });
    $(this.refs.room).select2({
      data: getRoomData(this.props.rooms)
    });
    $(this.refs.status).select2({
      data: getLessonStatusData(this.props.statuses)
    });
  },
  bindEditorEvents: function() {
    $(this.refs.room).on(
      'change', 
      this.roomChanged
    );
    $(this.refs.status).on(
      'change', 
      this.statusChanged
    );     
    $(this.refs.fromTime).on(
      'changeTime',
      this.fromTimeChanged
    );
    $(this.refs.toTime).on(
      'changeTime',
      this.toTimeChanged
    );
  },
  needToInitEditors: function() {
    return this.state.editMode && 
      !this.state.editorsInitialized;
  },
  componentDidMount: function() {
    if (this.needToInitEditors()) 
      this.initEditors(); 
  },
  componentDidUpdate: function() {
    if (this.needToInitEditors()) 
      this.initEditors(); 
  }
});

