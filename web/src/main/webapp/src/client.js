var React = require('react');
var ReactDOM = require('react-dom');
var Client = require('./components/client/client.js');

function getClientId() {
    var pathParts = window.location.pathname.split('/');
    return parseInt(pathParts[pathParts.length - 1]);
}

$(document).ready(function() {
    ReactDOM.render(
        <Client id={getClientId()} url='/do/client'/>,
        document.getElementById('client-box')
    );
});
