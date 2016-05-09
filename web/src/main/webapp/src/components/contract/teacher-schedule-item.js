import React from 'react';
import {
  dropSeconds, 
  getWeekdayName,
} from '../../utils/utils.js';


export default React.createClass({
  render: function() {
    return (
      <tr className='teacher-schedule-item'>
        <td>{getWeekdayName(this.props.item.weekday)}</td>
        <td>{this.props.item.roomName}</td>
        <td>{dropSeconds(this.props.item.fromTime)}</td>
        <td>{dropSeconds(this.props.item.toTime)}</td>
      </tr>
    );
  }
})
