var React = require('react');
var ContractItemAction = require('./contract-item-action.js');
var Utils = require('../../utils/utils.js');

var EventCreator = React.createClass({
    getInitialState: function() {
        return {
            eventId: this.props.teacherEvents[0].id
        };
    },
    insertEvent: function() {
        console.log('new schedule event inserted!');
        console.log(this.props.contractId);
        console.log(this.state.eventId);
        $.ajax({
            url:     "/do/contract/schedule/insert",
            method:  "POST",
            data:    {
                      contractId: this.props.contractId,
                      eventId: this.state.eventId
                     },
            success: this.props.reloadClientAndContracts,
            error:   Utils.logAjaxError.bind(this, "/do/contract/schedule/insert")
        });
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
                    <input onChange={$.noop}
                           ref="eventInput"
                           type="text"
                           value={this.state.eventId} />
                </td>
                <td className="action-cell">
                    <ContractItemAction key="insertEventAction"
                                        clickHandler={this.insertEvent}
                                        iconName="icon-save" />
                    <ContractItemAction key="hideCreatorAction"
                                        clickHandler={this.props.hideCreator}
                                        iconName="icon-remove" />
                </td>
            </tr>
        );
    },
    componentDidMount: function() {
        $(this.refs.eventInput).select2({
            data: this.props.teacherEvents.map(
                function(event) {
                    return {
                      id: event.id,
                      text: Utils.eventToString(event)
                    };
                }),
            minimumResultsForSearch: Infinity
        });

        $(this.refs.eventInput).on("change", this.eventIdChanged);
    }
});

module.exports = EventCreator;

