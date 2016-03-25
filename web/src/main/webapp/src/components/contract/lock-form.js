var React = require('react');
var LockForm = React.createClass({
    getDefaultProps: function() {
        return {
            visible: false,
            cancelHandler: $.noop
        };
    },
    getInitialState: function() {
        return {
            lockFrom: "",
            lockTo: "",
            submitDisabled: true
        };
    },
    lockFromChanged: function(lockFromValue) {
        this.setState({
            lockFrom: lockFromValue,
            submitDisabled: this.submitDisabled(lockFromValue, this.state.lockTo)
        });
    },
    lockToChanged: function(lockToValue) {
        this.setState({
            lockTo: lockToValue,
            submitDisabled: this.submitDisabled(this.state.lockFrom, lockToValue)
        });
    },
    handleSubmit: function(e) {
        e.preventDefault();
        this.props.lockHandler(this.props.contractId,
                               this.state.lockFrom,
                               this.state.lockTo);
        this.props.cancelHandler();
    },
    submitDisabled: function(lockFrom, lockTo) {
        return lockFrom === "" || lockTo === "";
    },
    render: function() {
        var style = this.props.visible
                    ? {}
                    : {display: "none"};

        return(
            <div style={style} className="lock-container">
                <i onClick={this.props.cancelHandler} className="icon-remove"/>
                <form onSubmit={this.handleSubmit}>
                    <div>
                        <label>Начало заморозки:</label>
                        <input ref="lockFromInput"
                               type="text"
                               value={this.state.lockFrom}/>
                    </div>
                    <div>
                        <label>Конец заморозки:</label>
                        <input ref="lockToInput"
                               type="text"
                               value={this.state.lockTo}/>
                    </div>
                    <div>
                        <button className="btn btn-xs"
                                type="submit"
                                disabled={this.state.submitDisabled}>
                            Заморозить!
                        </button>
                    </div>
                </form>
            </div>
        );
    },
    componentDidMount: function() {
        $(this.refs.lockFromInput).datepicker({
                dateFormat: "dd-mm-yy",
                onSelect: function(newValue) {
                    this.lockFromChanged(newValue);
                }.bind(this)
            });

        $(this.refs.lockToInput).datepicker({
                dateFormat: "dd-mm-yy",
                onSelect: function(newValue) {
                    this.lockToChanged(newValue);
                }.bind(this)
            });
    }
});

module.exports = LockForm;
