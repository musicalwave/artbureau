import React from 'react';
import ContractSchedule from './contract-schedule.js';
import {
  insertScheduleItem,
  updateScheduleItem,
  deleteScheduleItem
} from '../../actions/contract_schedule_actions.js';
import {
  noop,
  findByKeyAndUpdateProp
} from '../../utils/utils.js';
import $ from 'jquery';

export default React.createClass({
  getInitialState: function() {
    return {
      rooms: [],
      schedule: [],
      newItemId: null
    };
  },
  getDefaultProps: function() {
    return {
      contractId: 0,
      title: 'Title',
      reloadClient: noop,
      reloadContracts: noop,
    };
  },
  reload: function() {
    this.props.reloadContracts();
    this.props.reloadLessons();
    this.reloadSchedule();
  },
  reloadSchedule: function() {
    $.when(
      $.get('/do/contract/schedule/', { contractId: this.props.contractId })
    ).then((schedule) => {
      console.log(schedule);
      this.setState({ schedule });
    });
  },
  getNewItemId: function() {
    var maxId = this.state.schedule.reduce((currMaxId, item) => {
      return item.id > currMaxId ? item.id : currMaxId;
    }, 0);
    return maxId + 1;
  },
  getNewItem: function() {
    return {
      id: this.getNewItemId(),
      contractId: this.props.contractId,
      weekday: 1,
      fromTime: '10:00',
      toTime: '11:00',
      roomId: 1
    };
  },
  create: function() {
    var newItem = this.getNewItem();
    this.setState({
      schedule: this.state.schedule.concat(newItem),
      newItemId: newItem.id
    });
  },
  insert: function(item) {
    insertScheduleItem(
      JSON.stringify(item), 
      this.reload
    );
    this.setState({
      newItemId: null
    });
  },
  update: function(item) {
    updateScheduleItem(
      JSON.stringify(item), 
      this.reload
    );
  },
  delete: function(item) {
    deleteScheduleItem(
      JSON.stringify(item),
      this.reload
    );
  },
  remove: function(item) {
    this.setState({
      schedule: this.state.schedule.filter(currItem => 
        currItem.id !== item.id
      ),
      newItemId: this.state.newItemId === item.id ?
        null : 
        this.state.newItemId
    });
  },
  weekdayChanged: function(itemId, weekday) {
    this.setState({
      schedule: findByKeyAndUpdateProp(
        this.state.schedule, 
        'id', 
        itemId, 
        'weekday', 
        weekday
      )
    });
  },
  roomChanged: function(itemId, roomId) {
    this.setState({
      schedule: findByKeyAndUpdateProp(
        this.state.schedule, 
        'id', 
        itemId, 
        'roomId', 
        roomId
      )
    });
  },
  fromTimeChanged: function(itemId, fromTime) {
    this.setState({
      schedule: findByKeyAndUpdateProp(
        this.state.schedule, 
        'id', 
        itemId, 
        'fromTime', 
        fromTime
      )
    });
  },
  toTimeChanged: function(itemId, toTime) {
    this.setState({
      schedule: findByKeyAndUpdateProp(
        this.state.schedule, 
        'id', 
        itemId, 
        'toTime', 
        toTime
      )
    });
  },
  revert: function(oldItem) {
    this.setState({
      schedule: this.state.schedule.map((item) => {
        return item.id === oldItem.id ?
          oldItem :
          item;
      })
    });
  },
  render: function() {
    return (
      <ContractSchedule
        schedule={this.state.schedule}
        rooms={this.state.rooms}
        title={this.props.title}
        create={this.create}
        insert={this.insert}
        update={this.update}
        delete={this.delete}
        remove={this.remove}
        weekdayChanged={this.weekdayChanged}
        roomChanged={this.roomChanged}
        fromTimeChanged={this.fromTimeChanged}
        toTimeChanged={this.toTimeChanged}
        revert={this.revert}
        newItemId={this.state.newItemId}
      />
    );
  },
  componentDidMount: function() {
    $.when(
      $.get('/do/rooms'),
      $.get('/do/contract/schedule/', { contractId: this.props.contractId })
    ).then((rooms, schedule) => {
      this.setState({ 
        rooms:    rooms[0],
        schedule: schedule[0]
      });
    });
  }  
})

