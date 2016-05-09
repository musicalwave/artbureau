import moment from 'moment';

export function noop() {
  return;
}

export function findByKeyAndUpdateProp(
  arr, 
  keyName, 
  keyValue, 
  propName, 
  propValue
) {
  var updItem = {};
  updItem[propName] = propValue;

  return arr.map((item) => {
    return item[keyName] === keyValue ?
      Object.assign({}, item, updItem) :
      item;
  });
}

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

export function getWeekdays() {
  var weekdays = [];
  for (var i = 1; i <= 7; i++)
    weekdays.push({
      id: i,
      text: getWeekdayName(i)
    });
  return weekdays;
}

export function getWeekdayName(weekday) {
  return moment().isoWeekday(weekday).format('dddd');
}

export function getRoomName(rooms, roomId) {
  var room = rooms.find(room => 
    room.id === roomId
  );
  return room ? room.name + ' [' + room.filialName + ']' : '';
}

export function getRoomData(rooms) {
  return rooms.map(room => ({
    id: room.id,
    text: room.name + ' [' + room.filialName + ']' 
  }));
}

export function getLessonStatusName(statuses, statusId) {
  var status = statuses.find(status =>
    status.id === statusId
  );
  return status ? status.name : ''; 
}

export function getLessonStatusData(statuses) {
  return statuses.map(status => ({
    id: status.id,
    text: status.name
  }));
}

export function coalesce(value, replace) {
  if (value === null || value === undefined)
    return replace;
  else
    return value;
}

