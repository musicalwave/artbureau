import $ from 'jquery';
import {logAjaxError} from '../utils/utils';

export function createScheduleEvent(contractId, eventId, successCallback) {
  $.ajax({
      url:     "/do/contract/schedule/insert",
      method:  "POST",
      data:    {
        contractId,
        eventId
      },
      success: successCallback,
      error:   logAjaxError.bind(this, "/do/contract/schedule/insert")
  });
}

export function updateScheduleEvent(contractId, contractScheduleId, eventId, successCallback) {
  $.ajax({
    url:     "/do/contract/schedule/update",
    method:  "POST",
    data:    {
      contractId, 
      contractScheduleId,
      eventId
    },
    success: successCallback,
    error:   logAjaxError.bind(this, "/do/contract/schedule/update")
  });
}

export function deleteScheduleEvent(contractId, contractScheduleId, successCallback) {
  $.ajax({
    url:     "/do/contract/schedule/delete",
    method:  "POST",
    data:    {
      contractId,
      contractScheduleId
    },
    success: successCallback,
    error:   logAjaxError.bind(this, "/do/contract/schedule/delete")
  });
}
