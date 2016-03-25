var React = require('react');
var ContractItemList = React.createClass({
    getDefaultProps: function() {
        return {
          items: [],
          creatorVisible: false,
          createItemCreator: $.noop,
          showCreator: $.noop,
          createItemElement: $.noop,
          title: "Title"
        };
    },
    render: function() {
        var itemElems = this.props.items.map(function(item) {
            return this.props.createItemElement(item);
        }, this);

        var creator;
        if(this.props.creatorVisible)
            creator = this.props.createItemCreator();

        var newItemButtonStyle = {};
        if(this.props.creatorVisible)
            newItemButtonStyle = {display: "none"};

        return (
            <div className="contract-item-list">
                <h4>{this.props.title}:</h4>

                <table className="contract-item-table info-table">
                    <tbody>
                        {itemElems}
                        {creator}
                    </tbody>
                </table>

                <i style={newItemButtonStyle}
                   onClick={this.props.showCreator}
                   className="icon-plus icon-border icon-btn add-btn"/>

            </div>
        );
    }
});

module.exports = ContractItemList;
