import React from 'react';
import ContractScheduleItem from './contract-schedule-item.js';
import {noop} from '../../utils/utils.js';

export default React.createClass({
  getDefaultProps: function() {
    return {
      title: 'Title',
      rooms: [],
      schedule: [],
      create: noop,
      insert: noop,
      update: noop,
      delete: noop,
      remove: noop,
      weekdayChanged: noop,
      roomChanged: noop,
      fromTimeChanged: noop,
      toTimeChanged: noop,
      revert: noop,
      newItemId: null
    };
  },
  getItemElements: function() {
    return this.props.schedule.map(item => {
      if (item.id !== this.props.newItemId)
        return this.getItemElement(item);
      else
        return this.getItemCreator(item);
    });
  },
  getItemElement: function(item) {
    return <ContractScheduleItem
      key={item.id}
      item={item} 
      rooms={this.props.rooms}
      save={this.props.update}
      delete={this.props.delete}
      weekdayChanged={this.props.weekdayChanged}
      roomChanged={this.props.roomChanged}
      fromTimeChanged={this.props.fromTimeChanged}
      toTimeChanged={this.props.toTimeChanged}
      revert={this.props.revert}
    />;
  },
  getItemCreator: function(item) {
    return <ContractScheduleItem 
      key='itemCreator'
      item={item}
      newItem={true}
      editMode={true}
      rooms={this.props.rooms}
      save={this.props.insert}
      remove={this.props.remove}
      weekdayChanged={this.props.weekdayChanged}
      roomChanged={this.props.roomChanged}
      fromTimeChanged={this.props.fromTimeChanged}
      toTimeChanged={this.props.toTimeChanged}
    />;
  },
  getCreateButton: function() {
    return !this.props.newItemId ?
      <i onClick={this.props.create}
         className="icon-plus icon-border icon-btn"/> :
      null;
  },
  render: function() {
    return (
      <div className="contract-items">
        <h4>{this.props.title}:</h4>
        <table className="info-table">
          <tbody>
            {this.getItemElements()}
          </tbody>
        </table>
        {this.getCreateButton()}
      </div>
    );
  }
});
