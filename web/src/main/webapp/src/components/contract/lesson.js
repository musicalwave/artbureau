var React = require('react');
var ContractItemAction = require('./contract-item-action.js');
var Utils = require('../../utils/utils.js');

var Lesson = React.createClass({

    getInitialState: function() {
        return {
            editMode:        false,
            date:            moment(this.props.lesson.date).format("DD-MM-YYYY"),
            eventId:         this.props.lesson.eventId,
            availableEvents: this.getAvailableEvents(moment(this.props.lesson.date))
        };
    },

    getAvailableEvents: function(moment) {
        return this.props.teacherEvents.filter(
            function(event) {
                return event.weekday === moment.isoWeekday();
            }
        );
    },

    getActions: function() {

        var conductAction = <ContractItemAction key="conductAction"
                                                clickHandler={this.conduct}
                                                iconName="icon-check" />;
        var burnAction    = <ContractItemAction key="burnAction"
                                                clickHandler={this.burn}
                                                iconName="icon-fire" />;
        var restoreAction = <ContractItemAction key="restoreAction"
                                                clickHandler={this.restore}
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

    burn: function() {
        console.log('burn lesson ' + this.props.lesson.id);
        $.ajax({
            url:     "/do/lessons/burn",
            method:  "POST",
            data:    { id: this.props.lesson.id },
            success: this.props.reloadClientAndContracts,
            error:   Utils.logAjaxError.bind(this, "/do/lessons/burn")
        });
    },

    conduct: function() {
        console.log('conduct lesson ' + this.props.lesson.id);
        $.ajax({
            url:     "/do/lessons/conduct",
            method:  "POST",
            data:    { id: this.props.lesson.id },
            success: this.props.reloadClientAndContracts,
            error:   Utils.logAjaxError.bind(this, "/do/lessons/conduct")
        });
    },

    restore: function() {
        console.log('restore lesson ' + this.props.lesson.id);
        $.ajax({
            url:     "/do/lessons/restore",
            method:  "POST",
            data:    { id: this.props.lesson.id },
            success: this.props.reloadClientAndContracts,
            error:   Utils.logAjaxError.bind(this, "/do/lessons/restore")
        });
    },

    update: function(lessonId, date, eventId) {
      console.log('lesson ' + lessonId + ' updated');

      $.ajax({
          url: "/do/lesson/update",
          method: "POST",
          data: {
              lessonId: lessonId,
              date: date,
              eventId: eventId
          },
          success: this.props.reloadClientAndContracts,
          error: Utils.logAjaxError.bind(this, "/do/lesson/update")
      })
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
      this.update(this.props.lesson.id,
                  this.state.date,
                  this.state.eventId);

      this.setState({
          editMode: false
      });
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

            fields.push(<td key="date">
                            <input ref="dateInput"
                                   type="text"
                                   onChange={$.noop}
                                   value={this.state.date} />
                        </td>);
            fields.push(<td colSpan="2" key="event">
                            <input ref="eventInput"
                                   type="text"
                                   onChange={$.noop}
                                   value={this.state.eventId} />
                        </td>);
        } else {
            var date = moment(this.props.lesson.date).format("DD-MM-YYYY (dd)");
            var startTime = Utils.dropSeconds(this.props.lesson.startTime);
            var finishTime = Utils.dropSeconds(this.props.lesson.finishTime);

            fields.push(<td key="date">{date}</td>);
            fields.push(<td key="time">{startTime + " : " + finishTime}</td>);
            fields.push(<td key="room">{this.props.lesson.roomName}</td>);
        }

        return(
            <tr>
                {fields}
                <td>{this.props.lesson.statusName}</td>
                <td className="action-cell">
                  {this.getActions()}
                </td>

            </tr>
        );
    },
    componentDidUpdate: function() {
        if(this.state.editMode) {
            var weekdays = this.props.teacherEvents.map(function(event) {
                return event.weekday;
            });

            $(this.refs.dateInput).datepicker({
                dateFormat: "dd-mm-yy",
                onSelect: function(date) {
                    this.dateChanged(date);
                }.bind(this),
                beforeShowDay: function(date) {
                    // show only available dates
                    return [weekdays.indexOf(moment(date).isoWeekday()) !== -1];
                }
            });

            $(this.refs.eventInput).select2({
                data: this.state.availableEvents.map(
                        function(event) {
                            return {
                              id: event.id,
                              text: Utils.eventToString(event)
                            };
                        })
            });

            $(this.refs.eventInput).on("change", this.eventIdChanged);
        }
    }
});

module.exports = Lesson;
