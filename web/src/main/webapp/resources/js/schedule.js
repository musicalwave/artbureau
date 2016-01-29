function getSelectedRoomId() {
    return $('.rooms-list')
            .find('a.selected-room')
            .attr('room_id');
}


function eventRenderer ( event, element, view ) { 
    if(event.rendering !== "background") {
        $(element).attr("event_id", event._id);
        $(element).append(getEventIconsList());
        $(element).find("div.fc-content").css("top", "17px");
    }
}

function eventDropper(event, delta, revertFunc) {

    console.log("event dropped!");

    // update event on the server

    // well, this is true when we drop an event which is not in the calendar's
    // events cache (e.g. we refetched events after the dragging had started).
    // The dropped event hasn't been mutated (see the reportEventDrop func in FC source code) 
    // so we need to alter it here manually.
    if(event.lostOrigin) {
        event.start.add(delta._days, 'd')
                   .add(delta._milliseconds, 'ms');
        event.end.add(delta._days, 'd')
                 .add(delta._milliseconds, 'ms');
    }

    var lessonId = event._id;
    var teacherId = event.teacherId;
    var roomId = getSelectedRoomId();
    var weekday = event.start.day();
    if(weekday === 0) // sunday
        weekday = 7;
    var startTime = event.start.format("HH:mm:ss");
    var finishTime = event.end.format("HH:mm:ss");


    var date = event.start.format("YYYY-MM-DD");

    $.ajax({
        url: "/do/lessons/update",
        method: "POST",
        data : {
            id : lessonId,
            teacherId : teacherId,
            roomId : roomId,
            weekday : weekday,
            startTime : startTime,
            finishTime : finishTime,
            date : date
        },
        success : function(response) {
            if(!response)
                revertFunc();
            if(event.lostOrigin)
                $('#calendar').fullCalendar('refetchEvents');
        },
        error: function() {
            revertFunc();
        }
    })
}


function hideEventById(eventId) {
    $('a[event_id=\"' + eventId + '\"]').hide();
}

function openPositionToCalendarEvent(postition, firstDayOfWeek) {

    var date = firstDayOfWeek.clone();
    var dateStr = date.add(postition.weekday - 1, 'days')
                      .format("YYYY-MM-DD");
    var start = dateStr + "T" + postition.startTime;
    var end = dateStr + "T" + postition.finishTime;

    return { start : start,
             end : end,
             rendering : "background",
             color: "green" }
}

var openPositionSource;
function fetchOpenPositions(eventId, teacherId, firstDayOfWeek) {

    $.ajax({
        url: "/do/events",
        data: {
            teacherId : teacherId,
            roomId : getSelectedRoomId()
        },
        success: function(response) {

            openPositionSource = [];

            $(response).each(function() {
                openPositionSource.push(
                    openPositionToCalendarEvent(this, firstDayOfWeek));
            })
    
            $("#calendar").fullCalendar('addEventSource', openPositionSource);
            
            hideEventById(eventId);
        }
    })
}


function removeOpenPositions() {
    $('#calendar').fullCalendar('removeEventSource', openPositionSource);
}

function refetchOpenPositions(eventId, teacherId, firstDayOfWeek) {
    removeOpenPositions();
    fetchOpenPositions(eventId, teacherId, firstDayOfWeek);    
}

function getRoomLinkByCoords(x, y) {
    var $elements = $(document.elementsFromPoint(x, y));
    var $room_link;
    $.each($elements, function() {
        if($(this).is("a[room_id]")) {
            $room_link = $(this);
            return false;
        }
    })
    return $room_link;
}

function eventDragStarter (event, jsEvent, ui, view ) {

    // get open postitions 
    // for the dragged event in the current room
    // and render them as background events
    fetchOpenPositions(event._id, event.teacherId, view.start);

    $(document).mousemove(function(e){
        var $room_link = getRoomLinkByCoords(e.pageX, e.pageY);
        
        if($room_link !== undefined && $room_link.attr("room_id") !== getSelectedRoomId()) {
            $room_link.click();
            refetchOpenPositions(event._id, event.teacherId, view.start);
            // room switched -> events refetched -> origin lost
            event.lostOrigin = true; 
        }
    })
}

function eventDragStopper (event, jsEvent, ui, view ) {
    console.log("event drag stopped!");
    removeOpenPositions();
    $(document).unbind('mousemove');
}

function getEventIconsList(event) {
    return  "<ul class=\"event-icons-list\">" +
                "<li><img src=\"/resources/i/icons_set/book.svg\"/></li>" +
                "<li><img src=\"/resources/i/icons_set/weird_thing.svg\"/></li>" +
                "<li><img src=\"/resources/i/icons_set/pin.svg\"/></li>" +
            "</ul>"
}

function getEventColorByStatus(status) {
    switch(status) {
        case 1: return "grey";
        case 2: return "blue";
        case 3: return "red";
    }
}

function lessonToCalendarEvent(lesson) {
    
    var date = lesson.date;
    var startTime = lesson.startTime;
    var finishTime = lesson.finishTime;
    var start = new Date(date + " " + startTime);
    var end = new Date(date + " " + finishTime);

    return { id: lesson.lessonId,
             teacherId : lesson.teacherId,
             roomId : lesson.roomId,
             lostOrigin : false,
             title: "Ученик: " + lesson.clientName + "\n"
                    + "Препод: " + lesson.teacherName,
             start: start,
             end: end,
             startEditable: true,                            
             durationEditable: false,
             color: getEventColorByStatus(lesson.doneStatus) }
}

function fetchLessons (start, end, timezone, callback) {
    $.ajax({
        url: '/do/lessons/room',
        data: { roomId: getSelectedRoomId(),
                leftDate: start.format('YYYY-MM-DD'),
                rightDate: end.subtract(1, 'd')
                              .format('YYYY-MM-DD') },

        success: function(response) {

            var events = [];

            $(response).each(function() {
                events.push(lessonToCalendarEvent(this));
            });

            callback(events);

        }
    })}


function clickRoomLink(e) {
        e.preventDefault();

        $(this).parent()
               .siblings()
               .children()
               .removeClass('selected-room');
        $(this).addClass('selected-room');

        $('#calendar').fullCalendar('refetchEvents');
}

function conductLesson(key, options) {
    var id = options.$trigger.attr('event_id');
    $.ajax({
        url: "/do/lessons/conduct",
        method: "POST",
        data: {
            id: id
        },
        success: function(response) {
            if(response !== 0) {
                options.$trigger.css('border-color', getEventColorByStatus(response));
                options.$trigger.css('background-color', getEventColorByStatus(response));
            }                
        }
    })
}

function burnLesson(key, options) {
    var id = options.$trigger.attr('event_id');
    $.ajax({
        url: "/do/lessons/burn",
        method: "POST",
        data: {
            id: id
        },
        success: function(response) {
            if(response !== 0) {
                options.$trigger.css('border-color', getEventColorByStatus(response));
                options.$trigger.css('background-color', getEventColorByStatus(response));
            }
        }
    })
}

$(document).ready(function() {

    $('.rooms-list').find('a')
        .first()
        .addClass('selected-room');

    $('.rooms-list').find('a').click(clickRoomLink);

    $('#calendar').fullCalendar({

        events: fetchLessons,
        defaultView: "agendaWeek",
        header : {
            left:   'title',
            center: '',
            right:  'today prev,next'
        } ,
        theme: true,
        firstDay: 1,
        views: {
            agendaWeek: { // name of view
                columnFormat: 'ddd'
            }
        },
        lang: 'ru',        
        allDaySlot: false,
        minTime: "10:00:00",
        maxTime: "23:00:00",
        slotLabelFormat: "H:mm",
        height: 750,
        
        eventRender:    eventRenderer,
        eventDragStart: eventDragStarter,
        eventDragStop:  eventDragStopper,
        eventDrop:      eventDropper        
    });

//     $(function() {
//         $('.fc-time-grid').selectable({
//             filter: ".fc-event"
//         });
//         }
//     ),


   $(function() {

    // custom commands:

    // MENU

    $.contextMenu({
        selector: '.fc-event', 
        items: {

//             transfer: {
//                 name: "Перенести",
//                 icon: function() {
//                     return 'context-menu-icon context-menu-icon-transfer';
//                 }
//             },

            check: {
                name: "Провести", 
                icon: function() {
                    return 'context-menu-icon context-menu-icon-check';
                },
                callback: conductLesson
            },

            burn: {
                name: "Сжечь", 
                icon: function() {
                    return 'context-menu-icon context-menu-icon-burn';
                },
                callback: burnLesson
            }

        }
    });

    });
});

