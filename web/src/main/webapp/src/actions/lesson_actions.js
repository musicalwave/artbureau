import $ from 'jquery';
import {logAjaxError} from '../utils/utils';
import moment from 'moment';

export function insertLesson(lesson, successCallback) {
  $.ajax({
    url:         '/do/lessons/insert',
    method:      'POST',
    contentType: 'application/json',
    data:        lesson,
    success:     successCallback,
    error:       logAjaxError.bind(null, '/do/lessons/insert')
  });
}

export function updateLesson(lesson, successCallback, failureCallback) {
  $.ajax({
    url:         '/do/lesson/update',
    method:      'POST',
    contentType: 'application/json',
    data:        lesson,
    success:     successCallback, 
    error:       failureCallback
  })
}

export function deleteLesson(lesson, successCallback) {
  $.ajax({
    url:         '/do/lessons/delete',
    method:      'POST',
    contentType: 'application/json',
    data:        lesson,
    success:     successCallback, 
    error:       logAjaxError.bind(null, '/do/lessons/delete')
  });
}

export function burnLesson(id, successCallback) {
  $.ajax({
    url:     '/do/lessons/burn',
    method:  'POST',
    data:    { id },
    success: successCallback,
    error:   logAjaxError.bind(null, '/do/lessons/burn')
  });
}

export function conductLesson(id, successCallback) {
  $.ajax({
    url:     '/do/lessons/conduct',
    method:  'POST',
    data:    { id },
    success: successCallback,
    error:   logAjaxError.bind(null, '/do/lessons/conduct')
  });
}

export function restoreLesson(id, successCallback) {
  $.ajax({
    url:     '/do/lessons/restore',
    method:  'POST',
    data:    { id },
    success: successCallback,
    error:   logAjaxError.bind(null, '/do/lessons/restore')
  });
}

export function shiftLesson(
  lesson,
  successCallback, 
  failureCallback
) {
    $.ajax({
      url:         '/do/lesson/shift',
      method:      'POST',
      contentType: 'application/json',
      data:        lesson, 
      success:     successCallback,
      error:       failureCallback
    });
}

export function unshiftLesson(
  id, 
  successCallback
) {
  $.ajax({
    url:     '/do/lesson/unshift',
    method:  'POST',
    data:    { id },
    success: successCallback,
    error:   logAjaxError.bind(null, '/do/lesson/unshift')
  });
}



export function getRoomLessons(roomId, fromDate, toDate) {
  return $.ajax({
    url: '/do/lessons/room',
    data: {
      roomId:    roomId,
      fromDate:  fromDate.format('YYYY-MM-DD'),
      toDate:    toDate.subtract(1, 'd').format('YYYY-MM-DD')
    }
  });
}

export function getTeacherScheduleByRoom(teacherId, roomId) {
  return $.ajax({
    url: '/do/teachers/schedule/room',
    data: {
      teacherId,
      roomId
    }
  });
}
