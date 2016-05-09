import React from 'react';
import moment from 'moment';
import {
  dropSeconds, 
  getWeekdayName,
  getWeekdays,
  getRoomName,
  getRoomData,
  noop
} from '../../utils/utils.js';
import ContractItemAction from './contract-item-action.js';

export default React.createClass({
  getDefaultProps: function() {
    return {
      newItem: false,
      item: null,
      rooms: [],
      editMode: false,
      update: noop,
      delete: noop,
      weekdayChanged: noop,
      roomChanged: noop,
      fromTimeChanged: noop,
      toTimeChanged: noop,
      revert: noop,
      remove: noop,
      save: noop
    };
  },
  getInitialState: function() {
    return {
      editMode: this.props.editMode,
      editorsInitialized: false,
      savedItem: null
    };
  },
  weekdayChanged: function(e) {
    var weekday = parseInt(e.target.value);
    this.props.weekdayChanged(this.props.item.id, weekday);
  },
  fromTimeChanged: function(e) {
    var fromTime = e.target.value;
    this.props.fromTimeChanged(this.props.item.id, fromTime);
  },
  toTimeChanged: function(e) {
    var toTime = e.target.value;
    this.props.toTimeChanged(this.props.item.id, toTime);
  },
  roomChanged: function(e) {
    var roomId = parseInt(e.target.value);
    this.props.roomChanged(this.props.item.id, roomId);
  },
  edit: function() {
    this.setState({
      editMode: true,
      savedItem: Object.assign( // shallow copy
        {}, 
        this.props.item
      ) 
    });
  },
  cancel: function() {
    if (this.props.newItem) {
      this.props.remove(this.props.item);
    } else {
      this.props.revert(this.state.savedItem);
      this.setState({
        editMode: false,
        editorsInitialized: false,
        savedItem: null
      });
    }
  },
  save: function() {
    this.setState({
      editMode: false,
      editorsInitialized: false,
      savedItem: null
    });
    this.props.save(this.props.item);
  },
  delete: function() {
    this.props.delete(this.props.item);
  },
  getActions: function() {
    var cancelAction = <ContractItemAction 
      key='cancelAction'
      clickHandler={this.cancel}
      iconName='icon-remove' 
    />;
    var saveAction = <ContractItemAction 
      key='saveAction'
      clickHandler={this.save}
      iconName='icon-save' 
    />;
    var deleteAction = <ContractItemAction 
      key='deleteAction'
      clickHandler={this.delete}
      iconName='icon-trash' 
    />;
    var editAction = <ContractItemAction 
      key='editAction'
      clickHandler={this.edit}
      iconName='icon-pencil' 
    />;

    var actions = [];
    if (this.state.editMode) {
      actions.push(saveAction);
      actions.push(cancelAction);
    } else {
      actions.push(editAction);
      actions.push(deleteAction);
    }
    return actions;
  },
  getEditor: function() {
    return (
      <tr className='contract-item'>
        <td className='weekday'>
          <input 
            ref='weekday' 
            value={this.props.item.weekday}
            onChange={$.noop}/>
        </td>
        <td className='room'>
          <input 
            ref='room' 
            value={this.props.item.roomId}
            onChange={$.noop}/>
        </td>
        <td className='time'>
          <input
            ref='fromTime'
            value={dropSeconds(this.props.item.fromTime)}
            onChange={$.noop}/>
        </td>
        <td className='time'>
          <input
            ref='toTime'
            value={dropSeconds(this.props.item.toTime)}
            onChange={$.noop}/>
        </td>
        <td className='action-cell'>
          {this.getActions()}
        </td>
      </tr> 
    );
  },
  getViewer: function() {
    return (
      <tr className='contract-item'>
        <td>{getWeekdayName(this.props.item.weekday)}</td>
        <td>{getRoomName(this.props.rooms, this.props.item.roomId)}</td>
        <td>{dropSeconds(this.props.item.fromTime)}</td>
        <td>{dropSeconds(this.props.item.toTime)}</td>
        <td className='action-cell'>{this.getActions()}</td>
      </tr>
    );
  },
  initEditors: function() {
    this.initEditorWidgets();  
    this.bindEditorEvents();
    this.setState({
      editorsInitialized: true
    });
  },
  initEditorWidgets: function() {
    $(this.refs.weekday).select2({
      data: getWeekdays()
    });

    $(this.refs.room).select2({
      data: getRoomData(this.props.rooms)
    });

    $(this.refs.fromTime).timepicker({
      timeFormat: 'H:i',
      step: 15
    });

    $(this.refs.toTime).timepicker({
      timeFormat: 'H:i',
      step: 15
    });
  },
  bindEditorEvents: function() {
    $(this.refs.weekday).on(
      'change', 
      this.weekdayChanged
    );

    $(this.refs.room).on(
      'change', 
      this.roomChanged
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
  render: function() {
    return this.state.editMode ?
      this.getEditor() : 
      this.getViewer();
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

