import $ from 'jquery';
import {logAjaxError} from '../utils/utils';

export function insertScheduleItem(item, successCallback) {
  $.ajax({
    url:         '/do/contract/schedule/insert',
    method:      'POST',
    contentType: 'application/json',
    data:        item,
    success:     successCallback,
    error:       logAjaxError.bind(null, '/do/contract/schedule/insert')
  });
}

export function updateScheduleItem(item, successCallback) {
  console.log(item);
  $.ajax({
    url:         '/do/contract/schedule/update',
    method:      'POST',
    contentType: 'application/json',
    data:        item, 
    success:     successCallback, 
    error:       logAjaxError.bind(null, '/do/contract/schedule/update')
  });
}

export function deleteScheduleItem(item, successCallback) {
  $.ajax({
    url:         '/do/contract/schedule/delete',
    method:      'POST',
    contentType: 'application/json',
    data:        item, 
    success:     successCallback, 
    error:       logAjaxError.bind(null, '/do/contract/schedule/delete')
  });
}

