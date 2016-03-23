// React components:

var Link = React.createClass({
    getDefaultProps: function() {
        return {
            clickHandler: $.noop,
            hoverHandler: $.noop,
            selected:     false,
            link: {
                id: -1,
                name: 'default'
            }
        }
    },
    clickHandler: function(e) {
        e.preventDefault();
        this.props.clickHandler(this.props.link.id);
    },
    hoverHandler: function(e) {
        e.preventDefault();
        this.props.hoverHandler(this.props.link.id);
    },
    render: function() {
        var className = this.props.selected
                        ? 'selected'
                        : '';
        return(
            <a data-id={this.props.link.id} 
               className={className}
               onClick={this.clickHandler}
               onMouseEnter={this.hoverHandler}>
               {this.props.link.name}
            </a>
        );
    }
});

var LinkList = React.createClass({
   render: function() {
       var links = this.props.links.map(function(link) {
          var selected = (link.id === this.props.selectedId)          
          return (<li key={link.id}>
                    <Link link={link}
                          selected={selected}
                          clickHandler={this.props.clickHandler}
                          hoverHandler={this.props.hoverHandler}/>
                  </li>);
       }.bind(this));
       
       return(
            <ul className='link-list'>
                {links}
            </ul>
       );
   } 
});

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

var Calendar = React.createClass({
    getInitialState: function() {
        return {
            selectedRoomId:         -1,
            draggedEvent:           null,
            needRefetch:            false,
            addEmptyEventSource:    false,
            removeEmptyEventSource: false,
            changeRoomOnHover:      false
        };
    },
    getLessons: function(start, end, roomId) {
        return $.ajax({
            url: '/do/lessons/room',
            data: {
                roomId:    roomId,
                leftDate:  start.format('YYYY-MM-DD'),
                rightDate: end.subtract(1, 'd').format('YYYY-MM-DD')
            }
        });
    },
    lessonToCalendarEvent: function(lesson) {    
        var date       = lesson.date;
        var startTime  = lesson.startTime;
        var finishTime = lesson.finishTime;
        var start      = new Date(date + ' ' + startTime);
        var end        = new Date(date + ' ' + finishTime);

        return { id:               lesson.lessonId,
                 teacherId :       lesson.teacherId,
                 roomId :          lesson.roomId,
                 lostOrigin :      false,
                 weeksPassed:      0,
                 title:            'Ученик: ' + lesson.clientName + '\n' +
                                   'Препод: ' + lesson.teacherName,
                 start:            start,
                 end:              end,
                 startEditable:    true,                            
                 durationEditable: false,
                 color:            this.getEventColorByStatus(lesson.doneStatus) }
    },
    getEventColorByStatus: function(status) {
        switch(status) {
            case 1: return 'grey';
            case 2: return 'blue';
            case 3: return 'red';
        }
    },
    getEmptyEvents: function(draggedEvent, roomId) {
        return $.ajax({
            url: '/do/events',
            data: {
                teacherId: draggedEvent.teacherId,
                roomId:    roomId
        }});
    },
    emptyEventToCalendarEvent: function(postition, firstDayOfWeek) {

        var dateStr = firstDayOfWeek
                          .add(postition.weekday - 1, 'days')
                          .format('YYYY-MM-DD');
        var start   = dateStr + 'T' + postition.startTime;
        var end     = dateStr + 'T' + postition.finishTime;

        return { 
            start:      start,
            end:        end,
            rendering: 'background',
            color:     'green' };
    },
    getEmptyCalendarEvents: function(start, end, timezone, callback) {
        $.when(
            this.getEmptyEvents(
                this.state.draggedEvent, this.state.selectedRoomId
            )
        ).done(function(emptyEvents) {
            var emptyCalendarEvents = emptyEvents.map(function(event) {
                return this.emptyEventToCalendarEvent(event, moment(start));
            }.bind(this));
            callback(emptyCalendarEvents);

            // need to hide the dragged event here, 
            // strictly after the callback gets executed
            // (the callback calls the FC's renderEvents() method, 
            //  which renders the dragged event as if it is not dragged)
            this.hideDraggedEvent();

        }.bind(this));
    },
    getEvents: function(start, end, timezone, callback) {
        $.when(
            this.getLessons(start, end, this.state.selectedRoomId)
        ).done(function(lessons) {
            var lessonEvents = lessons.map(this.lessonToCalendarEvent);
            callback(lessonEvents);
        }.bind(this));
    },
    hideDraggedEvent: function() {
        if (this.state.draggedEvent) {
            $(this.refs.calendarContent)
                .fullCalendar('getView')
                .hideEvent(this.state.draggedEvent);
        }
    },
    eventRenderer: function( event, element, view ) {
        if(event.rendering !== 'background') {
            $(element).attr('event_id', event._id);
            $(element).append(this.getEventIconsList());
            $(element).find('div.fc-content').css('top', '17px');
        }
    },
    getEventIconsList: function(event) {
        return  '<ul class="event-icons-list">' +
                    '<li><img src="/resources/i/icons_set/book.svg"/></li>' +
                    '<li><img src="/resources/i/icons_set/weird_thing.svg"/></li>' +
                    '<li><img src="/resources/i/icons_set/pin.svg"/></li>' +
                '</ul>';
    },
    eventDragStarter: function(event, jsEvent, ui, view ) {
        this.setState({
            draggedEvent:           event,
            addEmptyEventSource:    true,
            removeEmptyEventSource: false,
            needRefetch:            false,
            changeRoomOnHover:      true
        });
    },
    eventDragStopper: function(event, jsEvent, ui, view ) {
        this.setState({
           draggedEvent:           null,
           addEmptyEventSource:    false,
           removeEmptyEventSource: true,
           needRefetch:            false,
           changeRoomOnHover:      false
        });
    },
    mutateEvent: function(event, delta) {
        var real_delta = delta._days - event.weeksPassed * 7;
        event.start.add(real_delta, 'd')
                   .add(delta._milliseconds, 'ms');
        event.end.add(real_delta, 'd')
                 .add(delta._milliseconds, 'ms');
    },
    updateLesson: function(event,
                           lessonId, 
                           teacherId, 
                           roomId, 
                           weekday, 
                           startTime, 
                           finishTime, 
                           date,
                           revertFunc,
                           successCallback) {
        $.ajax({
            url: '/do/lessons/update',
            method: 'POST',
            data: {
                id:         lessonId,
                teacherId:  teacherId,
                roomId:     roomId,
                weekday:    weekday,
                startTime:  startTime,
                finishTime: finishTime,
                date:       date
            },
            success : function(response) {
                if(!response)
                    revertFunc();
                else
                    if(event.lostOrigin)
                        successCallback();
            },
            error: function() {
                revertFunc();
            }
        })
    },
    actUponLesson: function(lessonElement, ajaxUrl) {
        var lessonId = lessonElement.attr('event_id');        
        $.ajax({
            url:     ajaxUrl,
            method:  'POST',
            data:    { id: lessonId },
            success: function(response) {
                if(response !== 0) {
                    var newEventColor = this.getEventColorByStatus(response);
                    lessonElement.css('border-color', newEventColor);
                    lessonElement.css('background-color', newEventColor);
                }                
            }.bind(this)
        });
    },
    conductLesson: function(key, options) {        
        this.actUponLesson(options.$trigger, '/do/lessons/conduct');        
    },
    burnLesson: function(key, options) {
        this.actUponLesson(options.$trigger, '/do/lessons/burn');
    },
    eventDropper: function(event, delta, revertFunc) {
        // The event is said to have lost its origin when it is no longer in the calendar's
        // events cache (e.g. we refetched events after the dragging had started).
        // The dropped event hasn't been mutated (see the reportEventDrop func in FC source code) 
        // so we need to mutate it here manually.
        if(event.lostOrigin)
            this.mutateEvent(event, delta);

        var successCallback = function() {
             this.setState({
                needRefetch:            true,
                addEmptyEventSource:    false,
                removeEmptyEventSource: false
            });
        }.bind(this);

        this.updateLesson(event,
                          event._id,
                          event.teacherId,
                          this.state.selectedRoomId,
                          event.start.isoWeekday(),
                          event.start.format('HH:mm:ss'),
                          event.end.format('HH:mm:ss'),
                          event.start.format('YYYY-MM-DD'),
                          revertFunc,
                          successCallback);
        
    },
    roomChanged: function(roomId) {
        this.setState({
            selectedRoomId: roomId,
            needRefetch:    true
        });
    },
    roomHovered: function(roomId) {
        if (this.state.draggedEvent) {
            this.state.draggedEvent.lostOrigin = true;
            this.setState({
                selectedRoomId: roomId,
                needRefetch:    true,
                addEmptyEventSource: false,
                removeEmptyEventSource: false
            })
        }  
    },
    render: function() {
        return (<div>
                  <CalendarHeader roomChanged={this.roomChanged}
                                  roomHovered={this.roomHovered}
                                  changeRoomOnHover={this.state.changeRoomOnHover}/>
                  <div ref='calendarContent'/>
                </div>);
    },
    initCalendarContent: function() {
        $(this.refs.calendarContent).fullCalendar({
            events: this.getEvents,
            defaultView:    'agendaWeek',
            header : {
                left:       'title',
                center:     '',
                right:      'today prev,next'
            } ,
            theme:           true,
            firstDay:        1,
            views: {
                agendaWeek:  { columnFormat: 'ddd' }
            },
            lang:            'ru',        
            allDaySlot:      false,
            minTime:         '10:00:00',
            maxTime:         '23:00:00',
            slotLabelFormat: 'H:mm',
            height:          750,

            eventRender:     this.eventRenderer,
            eventDragStart:  this.eventDragStarter,
            eventDragStop:   this.eventDragStopper,
            eventDrop:       this.eventDropper        
        });
    },
    mouseLeaveHandler: function(e) {
        var event = this.state.draggedEvent;
        if(event) {
            var $cal = $(this.refs.calendarContent);
            var leftEdgeX = $cal.offset().left;
            var rightEdgeX = leftEdgeX + $cal.width();

            var weekDiff = 0;
            var direction = "";
            if (e.pageX <= leftEdgeX) {
                weekDiff = -1;
                direction = 'prev';
            } else if (e.pageX >= rightEdgeX) {
                weekDiff = 1;
                direction = 'next';
            }

            if (weekDiff) {
                event.lostOrigin = true;
                event.start.add(7 * weekDiff, 'days');
                event.end.add(7 * weekDiff, 'days');
                event.weeksPassed += weekDiff;
                // switch to the next/prev week's view
                $cal.fullCalendar(direction);
                // need to manually prepare hits
                // for the dragged event to be properly rendered
                // on the another week's view
                $cal.fullCalendar('getView').prepareHits();
                this.setState({
                    refetchEvents:          true,
                    addEmptyEventSource:    false,
                    removeEmptyEventSource: false
                });
            }
        }
    },
    initContextMenu: function() {
        $.contextMenu({
            selector: '.fc-event', 
            items: {

                check: {
                    name: 'Провести', 
                    icon: function() {
                        return 'context-menu-icon context-menu-icon-check';
                    },
                    callback: this.conductLesson
                },

                burn: {
                    name: 'Сжечь', 
                    icon: function() {
                        return 'context-menu-icon context-menu-icon-burn';
                    },
                    callback: this.burnLesson
                }

            }
        });  
    },
    componentDidMount: function() {        
        this.initCalendarContent();
        $(this.refs.calendarContent).mouseleave(this.mouseLeaveHandler);
        this.initContextMenu();
    },
    componentDidUpdate: function() {
        if (this.state.needRefetch)
            $(this.refs.calendarContent).fullCalendar('refetchEvents');

        if (this.state.addEmptyEventSource)
            $(this.refs.calendarContent).fullCalendar(
                'addEventSource', this.getEmptyCalendarEvents);

        if (this.state.removeEmptyEventSource) 
            $(this.refs.calendarContent).fullCalendar(
                'removeEventSource', this.getEmptyCalendarEvents);
    }
});


function getRoomLinkByCoords(x, y) {
    var $elements = $(document.elementsFromPoint(x, y));
    var $room_link;
    $.each($elements, function() {
        if($(this).is('a[room_id]')) {
            $room_link = $(this);
            return false;
        }
    })
    return $room_link;
}

function eventDragStarter (event, jsEvent, ui, view ) {

    draggedEvent = function() { return event; };

    // get open postitions 
    // for the dragged event in the current room
    // and render them as background events

    fetchOpenPositions(event, view)

    $(document).mousemove(function(e){
        var $room_link = getRoomLinkByCoords(e.pageX, e.pageY);
        
        if($room_link !== undefined && $room_link.attr('room_id') !== getSelectedRoomId()) {
            $room_link.click();
            refetchOpenPositions(event, view);
            // room switched -> events refetched -> origin lost
            event.lostOrigin = true;
        }
    })
}

$(document).ready(function() {
    ReactDOM.render(
        <Calendar />,
        document.getElementById('calendar')
    );
});

