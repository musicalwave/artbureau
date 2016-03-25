var React = require('react');
var LinkList = require('../link-list.js');

var CalendarHeader = React.createClass({
    getInitialState: function() {
       return {
            filials:          [],
            filialRooms:      [],
            selectedFilialId: -1,
            selectedRoomId:   -1
       };
    },
    getFilialRooms: function(rooms, filialId) {
       return rooms.filter(function(room) {
           return room.filialId === filialId;
       });
    },
    roomClicked: function(roomId) {
        this.setState({
            selectedRoomId: roomId
        });
        this.props.roomChanged(roomId);
    },
    roomHovered: function(roomId) {
       if (this.props.changeRoomOnHover) {
           this.setState({
               selectedRoomId: roomId
           });
           this.props.roomHovered(roomId);
       }
    },
    filialClicked: function(filialId) {
       var filialRooms    = this.getFilialRooms(this.state.rooms, filialId);
       var selectedRoomId = this.getFirstItemId(filialRooms);       
       this.setState({
           selectedFilialId: filialId,
           filialRooms:      filialRooms,
           selectedRoomId:   selectedRoomId
       });
       this.props.roomChanged(selectedRoomId);

    },
    getFirstItemId: function(items) {
       return !items.length
              ? -1
              : items[0].id;
    },
    render: function() {
       return (
            <div>
                <a href="/tasks" className="home-btn">
                    <i className="icon-home icon-2x"/>
                </a>
                <LinkList links={this.state.filials} 
                          clickHandler={this.filialClicked}
                          selectedId={this.state.selectedFilialId} />
                <LinkList links={this.state.filialRooms} 
                          clickHandler={this.roomClicked}
                          hoverHandler={this.roomHovered}
                          selectedId={this.state.selectedRoomId}/>
            </div>
       );
    },
    componentDidMount: function() {
       $.when(
         $.get('/do/filials'),
         $.get('/do/rooms')
       ).done(function(filials, rooms) {           
           var selectedFilialId = this.getFirstItemId(filials[0]);
           var filialRooms      = this.getFilialRooms(rooms[0], selectedFilialId);
           var selectedRoomId   = this.getFirstItemId(filialRooms);

           this.setState({
                filials:          filials[0],
                selectedFilialId: selectedFilialId,
                rooms:            rooms[0],
                filialRooms:      filialRooms,
                selectedRoomId:   selectedRoomId
           });

           this.props.roomChanged(selectedRoomId);
       }.bind(this));
    }
});

module.exports = CalendarHeader;
