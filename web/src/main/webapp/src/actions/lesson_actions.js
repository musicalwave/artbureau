import $ from 'jquery';
import {logAjaxError} from '../utils/utils';
import moment from 'moment';

export function burnLesson(id, successCallback) {
  $.ajax({
    url:     "/do/lessons/burn",
    method:  "POST",
    data:    { id },
    success: successCallback,
    error:   logAjaxError.bind(this, "/do/lessons/burn")
  });
}

export function conductLesson(id, successCallback) {
  $.ajax({
    url:     "/do/lessons/conduct",
    method:  "POST",
    data:    { id },
    success: successCallback,
    error:   logAjaxError.bind(this, "/do/lessons/conduct")
  });
}

export function restoreLesson(id, successCallback) {
  $.ajax({
    url:     "/do/lessons/restore",
    method:  "POST",
    data:    { id },
    success: successCallback,
    error:   logAjaxError.bind(this, "/do/lessons/restore")
  });
}

export function updateLesson(lessonId, date, eventId, successCallback) {
  $.ajax({
    url: "/do/lesson/update",
    method: "POST",
    data: {
      lessonId,
      date,
      eventId
    },
    success: successCallback,
    error: logAjaxError.bind(this, "/do/lesson/update")
  })
}

export function updateLessonData(data, successCallback, failureCallback) {
  $.ajax({
    url:     '/do/lessons/update',
    method:  'POST',
    data,
    success: successCallback,
    error:   failureCallback
  });
}

export function getRoomLessons(roomId, from, to) {
  return $.ajax({
    url: '/do/lessons/room',
    data: {
      roomId:    roomId,
      leftDate:  from.format('YYYY-MM-DD'),
      rightDate: to.subtract(1, 'd').format('YYYY-MM-DD')
    }
  });
}

export function getEmptyEvents(teacherId, roomId, from, to) {
  return $.ajax({
    url: '/do/events/empty',
    data: {
      teacherId,
      roomId,
      start:     from.format('YYYY-MM-DD'),
      end:       to.subtract(1, 'd').format('YYYY-MM-DD')
    }
  });
}
