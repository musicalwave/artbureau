function logAjaxError(url, xhr, status, err) {
    console.log('error!');
    console.error(url, status, err.toString());
}

function dropSeconds(time) {
    return moment(time, "HH:mm:ss").format("HH:mm")
}

function eventToString(event) {
    return moment().day(event.weekday).format('dd') + " " +
        dropSeconds(event.startTime) + " : " +
        dropSeconds(event.finishTime) +
        " (" + event.roomS + ")";
}

function coalesce(value, replace) {
    if (value === null || value === undefined)
        return replace;
    else
        return value;
}

module.exports.logAjaxError  = logAjaxError;
module.exports.dropSeconds   = dropSeconds;
module.exports.eventToString = eventToString;
module.exports.coalesce      = coalesce;
