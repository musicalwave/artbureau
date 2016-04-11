import $ from 'jquery';
import {logAjaxError} from '../utils/utils';

export function getClient(id, successCallback) {
  $.ajax({
    url:     '/do/client',
    data:    { id },
    success: successCallback,
    error:   logAjaxError.bind(null, '/do/client/' + id)
  });
}

export function updateClient(data, successCallback, failureCallback) {
  $.ajax({
    url:         '/do/client',
    method:      'POST',
    contentType: 'application/json',
    data:        data,
    success:     successCallback,
    error:       failureCallback 
  });
}
