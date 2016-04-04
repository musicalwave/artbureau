import React from 'react';
import ContractItemAction from './contract-item-action.js';
import {logAjaxError, eventToString} from '../../utils/utils.js';

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
        console.log('schedule event ' + this.props.event.contractScheduleId + ' updated!');
        console.log('new event id: ' + this.state.eventId);
        $.ajax({
            url:     "/do/contract/schedule/update",
            method:  "POST",
            data:    {
                       contractId: this.props.contractId,
                       contractScheduleId: this.props.event.contractScheduleId,
                       eventId: this.state.eventId
                     },
            success: this.props.reloadClientAndContracts,
            error:   logAjaxError.bind(this, "/do/contract/schedule/update")
        });

        this.cancel();
    },
    delete: function() {
        console.log('schedule event' + this.props.event.contractScheduleId + ' deleted!');
        $.ajax({
            url:     "/do/contract/schedule/delete",
            method:  "POST",
            data:    {
                       contractId: this.props.contractId,
                       contractScheduleId: this.props.event.contractScheduleId
                     },
            success: this.props.reloadClientAndContracts,
            error:   logAjaxError.bind(this, "/do/contract/schedule/delete")
        });
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

