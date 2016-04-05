import moment from 'moment';

export function logAjaxError(url, xhr, status, err) {
  console.log('error!');
  console.error(url, status, err.toString());
}

export function dropSeconds(time) {
  return moment(time, "HH:mm:ss").format("HH:mm")
}

export function eventToString(event) {
  return moment().day(event.weekday).format('dd') + " " +
    dropSeconds(event.startTime) + " : " +
    dropSeconds(event.finishTime) +
    " (" + event.roomS + ")";
}

export function coalesce(value, replace) {
  if (value === null || value === undefined)
    return replace;
  else
    return value;
}

