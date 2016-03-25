var React = require('react');
var ReactDOM = require('react-dom');
var Calendar = require('./components/calendar/calendar.js');

$(document).ready(function() {
    ReactDOM.render(
        <Calendar />,
        document.getElementById('calendar')
    );
});

