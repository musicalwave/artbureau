 require('../../css/calendar.css');
import React from 'react';
import CalendarHeader from './calendar-header.js';
import EventDropDialog from './event-drop-dialog.js';
import {ModalContainer, ModalDialog} from 'react-modal-dialog';
import {
  conductLesson,
  burnLesson,
  shiftLesson,
  unshiftLesson,
  getRoomLessons,
  getTeacherScheduleByRoom
} from '../../actions/lesson_actions.js';
import {noop} from '../../utils/utils.js';
import moment from 'moment';

export default React.createClass({
  getInitialState: function() {
    return {
      selectedRoomId:           -1,
      draggedEvent:             null,
      needRefetch:              false,
      addEmptyEventSource:      false,
      removeEmptyEventSource:   false,
      changeRoomOnHover:        false,
      isShowingEventDropDialog: false,
      cancelDropEventHandler:   noop,
      acceptDropEventHandler:   noop
    };
  },
  lessonToEvent: function(lesson) {    
    var lessonDate = moment(lesson.date).format('YYYY-MM-DD');

    return { 
      id:               lesson.id,
      lesson:           lesson,
      lostOrigin :      false,
      weeksPassed:      0,
      title:            'Ученик: ' + lesson.clientName + '\n' +
                        'Препод: ' + lesson.teacherName,
      start:            lessonDate + 'T' + lesson.fromTime,
      end:              lessonDate + 'T' + lesson.toTime,
      startEditable:    !lesson.cancelled,
      durationEditable: false,
      color:            this.getEventColorByStatus(lesson.doneStatus) 
    };
  },
  getEventColorByStatus: function(status) {
    switch(status) {
      case 1: return 'grey';
      case 2: return 'blue';
      case 3: return 'red';
    }
  },
  teacherScheduleItemToEvent: function(item, firstDayOfWeek) {
    var dateStr = 
      firstDayOfWeek
      .add(item.weekday - 1, 'days')
      .format('YYYY-MM-DD');
    var start = dateStr + 'T' + item.fromTime;
    var end   = dateStr + 'T' + item.toTime;

    return { 
      start,
      end,
      rendering: 'background',
      color:     'green' 
    };
  },
  getTeacherScheduleEvents: function(start, end, timezone, callback) {
    $.when(
      getTeacherScheduleByRoom(
        this.state.draggedEvent.lesson.teacherId, 
        this.state.selectedRoomId
      )
    ).done(teacherSchedule => {
      var teacherScheduleEvents = teacherSchedule.map(
        item => this.teacherScheduleItemToEvent(item, start)
      );
      callback(teacherScheduleEvents);

      // need to hide the dragged event here, 
      // strictly after the callback gets executed
      // (the callback calls the FC's renderEvents() method, 
      //  which renders the dragged event as if it is not dragged)
      this.hideDraggedEvent();
    });
  },
  getLessonEvents: function(start, end, timezone, callback) {
    $.when(
      getRoomLessons(this.state.selectedRoomId, start, end)
    ).done(lessons => {
      var lessonEvents = lessons.map(this.lessonToEvent);
      callback(lessonEvents);
    });
  },
  hideDraggedEvent: function() {
    if (this.state.draggedEvent) {
      $(this.refs.calendarContent)
        .fullCalendar('getView')
        .hideEvent(this.state.draggedEvent);
    }
  },
  eventRenderer: function(event, element, view) {
    if(event.rendering !== 'background') {
      $(element).attr('lesson_id', event.lesson.id);
      $(element).append(this.getEventIconsList());
      $(element).find('div.fc-content').css('top', '17px');
      if (event.lesson.cancelled) 
        $(element).addClass('cancelled-event');
      if (event.lesson.temporary) 
        $(element).addClass('temporary-event');
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
  shiftLesson: function(
    event,
    roomId,
    tempShift,
    causedByClient,
    revertFunc,
    successCallback) {
      var lesson = {
        id:              event.id,
        roomId:          this.state.selectedRoomId,
        date:            parseInt(event.start.format('x')),
        fromTime:        event.start.format('HH:mm:ss'),
        toTime:          event.end.format('HH:mm:ss'),
        temporary:       event.lesson.temporary,
        cancelled:       event.lesson.cancelled,
        shiftedByClient: causedByClient,
        tempShift:       tempShift
      };
      shiftLesson(
        JSON.stringify(lesson),
        successCallback,
        revertFunc
      );
  },
  updateLessonColor: function(lessonElement, lessonStatus) {
    if(lessonStatus !== 0) {
      var newEventColor = this.getEventColorByStatus(lessonStatus);
      lessonElement.css('border-color', newEventColor);
      lessonElement.css('background-color', newEventColor);
    }
  },
  conductLesson: function(key, options) {        
    var lessonId = options.$trigger.attr('lesson_id');
    conductLesson(lessonId, this.updateLessonColor.bind(this, options.$trigger));
  },
  burnLesson: function(key, options) {
    var lessonId = options.$trigger.attr('leeson_id');
    burnLesson(lessonId, this.updateLessonColor.bind(this, options.$trigger));
  },
  unshiftLesson: function(key, options) {
    var lessonId = options.$trigger.attr('lesson_id');
    var callback = () => {
      this.setState({
        needRefetch: true
      });
    };
    unshiftLesson(lessonId, callback);
  },
  eventDropper: function(event, delta, revertFunc) {
    // The event is said to have lost its origin when it is no longer in the calendar's
    // events cache (e.g. we refetched events after the dragging had started).
    // The dropped event hasn't been mutated (see the reportEventDrop func in FC source code) 
    // so we need to mutate it here manually.
    if(event.lostOrigin)
      this.mutateEvent(event, delta);

    this.setState({
      isShowingEventDropDialog: true,
      
      acceptDropEventHandler: (tempShift, causedByClient) => {
        this.shiftLesson(
          event,
          this.state.selectedRoomId,
          tempShift,
          causedByClient,
          revertFunc,
          () => this.setState({
            needRefetch:            true,
            addEmptyEventSource:    false,
            removeEmptyEventSource: false,
            isShowingEventDropDialog: false
          })
        );
      },
      cancelDropEventHandler: () => {
        revertFunc();
        this.closeEventDropDialog();
      }
    });
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
        selectedRoomId:         roomId,
        needRefetch:            true,
        addEmptyEventSource:    false,
        removeEmptyEventSource: false
      })
    }  
  },
  showEventDropDialog: function() {
    this.setState({
      isShowingEventDropDialog: true
    });
  },
  closeEventDropDialog: function() {
    this.setState({
      isShowingEventDropDialog: false
    });
  },
  render: function() {
    return (
      <div>
        <CalendarHeader 
          roomChanged={this.roomChanged}
          roomHovered={this.roomHovered}
          changeRoomOnHover={this.state.changeRoomOnHover}/>
        {this.state.isShowingEventDropDialog &&
          <ModalContainer onClose={this.state.cancelDropEventHandler}>
            <ModalDialog onClose={this.state.cancelDropEventHandler}>
              <EventDropDialog onAccept={this.state.acceptDropEventHandler}/>
            </ModalDialog>
          </ModalContainer>}
        <div ref='calendarContent'/>
      </div>
    );
  },
  initCalendarContent: function() {
    $(this.refs.calendarContent).fullCalendar({
      events:         this.getLessonEvents,
      defaultView:    'agendaWeek',
      header : {
        left:         'title',
        center:       '',
        right:        'today prev,next'
      } ,
      theme:           true,
      firstDay:        1,
      views: {
        agendaWeek:    { columnFormat: 'ddd' }
      },
      lang:            'ru',        
      allDaySlot:      false,
      minTime:         '10:00:00',
      maxTime:         '23:00:00',
      slotDuration:    '00:15:00',
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
      var direction = '';
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
          icon: () => {
            return 'context-menu-icon context-menu-icon-check';
          },
          callback: this.conductLesson,
          disabled: (key, opt) => {
            return opt.$trigger.hasClass('cancelled-event')
          }
        },
        burn: {
          name: 'Сжечь', 
          icon: () => {
            return 'context-menu-icon context-menu-icon-burn';
          },
          callback: this.burnLesson,
          disabled: (key, opt) => {
            return opt.$trigger.hasClass('cancelled-event')
          }
        },
        unshift: {
          name: 'Отменить перенос', 
          icon: () => {
            return 'context-menu-icon context-menu-icon-unshift';
          },
          callback: this.unshiftLesson,
          disabled: (key, opt) => {
            return !opt.$trigger.hasClass('temporary-event')
          }
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

    if (this.state.removeEmptyEventSource) 
      $(this.refs.calendarContent).fullCalendar(
        'removeEventSource', this.getTeacherScheduleEvents
      );

    if (this.state.addEmptyEventSource)
      $(this.refs.calendarContent).fullCalendar(
        'addEventSource', this.getTeacherScheduleEvents
      );
  }
});

