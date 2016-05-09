import React from 'react';
import TeacherScheduleItem from './teacher-schedule-item.js';

export default React.createClass({
  getDeafultProps: function() {
    return {
      title: 'Title',
      schedule: []
    };
  },
  render: function() {
    return (
      (this.props.schedule && this.props.schedule.length !== 0) ?
        <div className='teacher-schedule'>
          <h4>{this.props.title}</h4>
          <table className='info-table'>
            <tbody>
              {this.props.schedule.map(
                 item => <TeacherScheduleItem 
                   key={item.id}
                   item={item} 
                 />)
              }
            </tbody>
          </table>
        </div> :
        <h4 className='warning'>У преподавателя не задано расписание</h4>
    );
  }
})
