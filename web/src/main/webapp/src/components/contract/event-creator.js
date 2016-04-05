import React from 'react';
import ContractItemAction from './contract-item-action.js';
import {eventToString} from '../../utils/utils.js';
import {createScheduleEvent} from '../../actions/schedule_event_actions.js';

export default React.createClass({
  getInitialState: function() {
    return {
      eventId: this.props.teacherEvents[0].id
    };
  },
  insertEvent: function() {
    createScheduleEvent(
      this.props.contractId,
      this.state.eventId,
      this.props.reloadClientAndContracts
    );
    this.props.hideCreator();
  },
  eventIdChanged: function(e) {
    this.setState({
      eventId: parseInt(e.target.value)
    });
  },
  render: function() {
    return(
      <tr>
        <td>
          <input 
            onChange={$.noop}
            ref="eventInput"
            type="text"
            value={this.state.eventId} />
        </td>
        <td className="action-cell">
          <ContractItemAction 
            key="insertEventAction"
            clickHandler={this.insertEvent}
            iconName="icon-save" />
          <ContractItemAction 
            key="hideCreatorAction"
            clickHandler={this.props.hideCreator}
            iconName="icon-remove" />
        </td>
      </tr>
    );
  },
  componentDidMount: function() {
    $(this.refs.eventInput).select2({
      data: this.props.teacherEvents.map(event => {
        return {
          id: event.id,
          text: eventToString(event)
        };
      }),
      minimumResultsForSearch: Infinity
    });

    $(this.refs.eventInput).on("change", this.eventIdChanged);
  }
});

