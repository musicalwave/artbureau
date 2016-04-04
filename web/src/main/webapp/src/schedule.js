import React from 'react';
import ReactDOM from 'react-dom';
import Calendar from './components/calendar/calendar.js';

$(document).ready(function() {
    ReactDOM.render(
        <Calendar />,
        document.getElementById('calendar')
    );
});

