import $ from 'jquery';
import {logAjaxError} from '../utils/utils';

export function createPayment(
  contractId, 
  date, 
  value, 
  status,
  successCallback) {
    $.ajax({
      url: '/do/payment/insert',
      method: 'POST',
      data: {
        contractId,
        date:       date.format('x'),
        value,
        planned:    status === 1 ? 1 : 0,
        done:       status === 1 ? 0 : 1
      },
      success: successCallback,
      error: logAjaxError.bind(this, '/do/payment/insert')
    });
}

export function commitPayment(paymentId, successCallback) {
  $.ajax({
    url:     '/do/payment/commit',
    method:  'POST',
    data:    { paymentId },
    success: successCallback,
    error:   logAjaxError.bind(this, '/do/payment/commit')
  });
}

export function restorePayment(paymentId, successCallback) {
  $.ajax({
    url:     '/do/payment/restore',
    method:  'POST',
    data:    { paymentId },
    success: successCallback,
    error:   logAjaxError.bind(this, '/do/payment/restore')
  });
}

export function deletePayment(paymentId, successCallback) {
  $.ajax({
    url:     '/do/payment/delete',
    method:  'POST',
    data:    { paymentId },
    success: successCallback,
    error:   logAjaxError.bind(this, '/do/payment/delete')
  });
}

export function updatePayment(paymentId, date, value, successCallback) {
  $.ajax({
    url:     '/do/payment/update',
    method:  'POST',
    data:    {
     paymentId,
     date: date.format('x'),
     value
    },
    success: successCallback,
    error:   logAjaxError.bind(this, '/do/payment/update')
  });
}
