import React from 'react';

var ClientToolbarAction = React.createClass({
    getDefaultProps: function() {
        return {
            clickHandler: $.noop,
            iconName:     "icon-beer"
        };
    },
    render: function() {
        return (
            <i onClick={this.props.clickHandler}
               className={"icon-2x icon-btn " + this.props.iconName} />
        );
    }
});

export default React.createClass({
    getDefaultProps: function() {
        return {
            saveHandler:   $.noop,
            revertHandler: $.noop,
            editHandler:   $.noop
        };
    },
    getActions: function() {
        var actions = [];
        var saveAction   = <ClientToolbarAction key="saveAction"
                                                clickHandler={this.props.saveHandler}
                                                iconName="icon-save" />;
        var revertAction = <ClientToolbarAction key="revertAction"
                                                clickHandler={this.props.revertHandler}
                                                iconName="icon-rotate-left" />;
        var editAction   = <ClientToolbarAction key="editAction"
                                                clickHandler={this.props.editHandler}
                                                iconName="icon-edit" />;

        if (this.props.editMode) {
            actions.push(saveAction);
            actions.push(revertAction);
        } else {
            actions.push(editAction);
        }

        return actions;
    },
    render: function() {
        return (
            <div className="client-toolbar">
                {this.getActions()}
            </div>
        );
    }
});

