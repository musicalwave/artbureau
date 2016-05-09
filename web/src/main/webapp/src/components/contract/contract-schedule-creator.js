import React from 'react';
import ContractSchedule from './contract-schedule.js';
import {
  noop,
  findByKeyAndUpdateProp
} from '../../utils/utils.js';

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
      title: 'Title'
    };
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
    this.setState({
      newItemId: null
    });
  },
  update: function(newItem) {
    this.setState({
      schedule: this.state.schedule.reduce((prevVal, item) => {
        if (item.id !== newItem.id)
          return prevVal.concat(item);
        else
          return prevVal.concat(newItem);
      }, [])
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
        create={this.create}
        insert={this.insert} 
        update={this.update}
        delete={this.remove}
        remove={this.remove}
        rooms={this.state.rooms}
        title={this.props.title}
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
      $.get('/do/rooms')
    ).then((rooms) => {
      this.setState({ rooms });
    });
  }
});
