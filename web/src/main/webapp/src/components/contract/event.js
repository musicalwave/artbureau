import React from 'react';
import ContractItemAction from './contract-item-action.js';
import {logAjaxError, eventToString} from '../../utils/utils.js';
import {updateScheduleEvent, deleteScheduleEvent} from '../../actions/schedule_event_actions.js';

export default React.createClass({
    getInitialState: function() {
        return {
            editMode: false,
            eventId: this.props.event.id
        };
    },
    edit: function() {
        this.setState({
            editMode: true,
            eventId: this.props.event.id
        });
    },
    cancel: function() {
        this.setState(this.getInitialState());
    },
    update: function() {
        updateScheduleEvent(
          this.props.contractId, 
          this.props.event.contractScheduleId, 
          this.state.eventId, 
          this.props.reloadClientAndContracts
        );
        this.cancel(); 
    },
    delete: function() {
        deleteScheduleEvent(
          this.props.contractId,
          this.props.event.contractScheduleId,
          this.props.reloadClientAndContracts
        );
    },
    getActions: function() {
        var editAction   = <ContractItemAction
                                key="editAction"
                                iconName="icon-pencil"
                                clickHandler={this.edit}/>;
        var deleteAction = <ContractItemAction
                                key="deleteAction"
                                iconName="icon-trash"
                                clickHandler={this.delete}/>;
        var updateAction   = <ContractItemAction
                                key="saveAction"
                                iconName="icon-save"
                                clickHandler={this.update}/>;
        var cancelAction = <ContractItemAction
                                key="cancelAction"
                                iconName="icon-remove"
                                clickHandler={this.cancel}/>;
        if(this.state.editMode)
            return [updateAction, cancelAction];
        else
            return [editAction, deleteAction];
    },
    render: function() {
        var fields = [];
        if (this.state.editMode) {
            fields.push(<td key="eventId" colSpan="4">
                            <input type="text"
                                   ref="eventInput"
                                   value={this.state.eventId}
                                   onChange={$.noop}/>
                        </td>);
        } else {
            fields.push(<td key="weekday">{this.props.event.weekdayS}</td>);
            fields.push(<td key="start">{this.props.event.startTime}</td>);
            fields.push(<td key="finish">{this.props.event.finishTime}</td>);
            fields.push(<td key="room">{this.props.event.roomS}</td>);
        }

        return(
            <tr>
                {fields}
                <td className="action-cell">
                  {this.getActions()}
                </td>

            </tr>
        );
    },
    componentDidUpdate: function() {
        $(this.refs.eventInput).select2({
          data: this.props.teacherEvents.map(
            function(event) {
              return {
                id: event.id,
                text: eventToString(event)
              };
          }),
          minimumResultsForSearch: Infinity
        });

        $(this.refs.eventInput).on("change", this.eventIdChanged);
    },
    eventIdChanged: function(e) {
        this.setState({
          eventId: parseInt(e.target.value)
        });
    }
});

