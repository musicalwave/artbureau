import $ from 'jquery';
import {logAjaxError} from '../utils/utils';

export function deleteContract(contractId, successCallback) {
  $.ajax({
    url:     "/do/contract/delete",
    method:  "POST",
    data:    { contractId },
    success: successCallback,
    error:   logAjaxError.bind(null, "/do/contract/delete")
  });
}

export function restoreContract(contractId, successCallback) {
  $.ajax({
     url:     "/do/contract/restore",
     method:  "POST",
     data:    { contractId },
     success: successCallback,
     error:   logAjaxError.bind(null, "/do/contract/restore")
  });
}

export function writeoffContract(contractId, successCallback) {
  $.ajax({
     url:     "/do/contract/writeoff",
     method:  "POST",
     data:    { contractId },
     success: successCallback,
     error:   logAjaxError.bind(null, "/do/contract/writeoff")
  });
}

export function cashbackContract(contractId, successCallback) {
  $.ajax({
     url: "/do/contract/cashback",
     method: "POST",
     data: { contractId },
     success: successCallback,
     error: logAjaxError.bind(null, "/do/contract/cashback")
  });
}

export function lockContract(contractId, lockFrom, lockTo, successCallback) {
  $.ajax({
      url:     "/do/contract/freeze",
      method:  "POST",
      data:    {
         contractId: contractId,
         lockFrom:   lockFrom.format('x'),
         lockTo:     lockTo.format('x')
      },
      success: successCallback, 
      error:   logAjaxError.bind(null, "/do/contract/freeze")
  });
}
export function unlockContract(contractId, successCallback) {
  $.ajax({
      url:     "/do/contract/unfreeze",
      method:  "POST",
      data:    { contractId },
      success: successCallback,
      error:   logAjaxError.bind(this, "/do/contract/unfreeze")
  });
}
