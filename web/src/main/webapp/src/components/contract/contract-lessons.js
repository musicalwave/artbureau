import React from 'react';
import Lesson from './lesson.js';
import {noop} from '../../utils/utils.js';

export default React.createClass({
  getDefaultProps: function() {
    return {
      title: 'Title',
      rooms: [],
      statuses: [],
      lessons: [],
      create: noop,
      insert: noop,
      update: noop,
      delete: noop,
      remove: noop,
      conduct: noop,
      burn: noop,
      restore: noop,
      dateChanged: noop,
      roomChanged: noop,
      fromTimeChanged: noop,
      toTimeChanged: noop,
      statusChanged: noop,
      revert: noop,
      newLessonId: null
    };
  },
  getLessonElement: function(lesson) {
    return <Lesson
      key={lesson.id}
      lesson={lesson} 
      rooms={this.props.rooms}
      statuses={this.props.statuses}
      save={this.props.update}
      burn={this.props.burn}
      conduct={this.props.conduct}
      restore={this.props.restore}
      delete={this.props.delete}
      dateChanged={this.props.dateChanged}
      roomChanged={this.props.roomChanged}
      fromTimeChanged={this.props.fromTimeChanged}
      toTimeChanged={this.props.toTimeChanged}
      statusChanged={this.props.statusChanged}
      revert={this.props.revert}
    />;
  },
  getLessonCreator: function(lesson) {
    return <Lesson 
      key='lessonCreator'
      lesson={lesson}
      rooms={this.props.rooms}
      statuses={this.props.statuses}
      save={this.props.insert}
      remove={this.props.remove}
      editMode={true}
      newLesson={true}
      dateChanged={this.props.dateChanged}
      roomChanged={this.props.roomChanged}
      fromTimeChanged={this.props.fromTimeChanged}
      toTimeChanged={this.props.toTimeChanged}
      statusChanged={this.props.statusChanged}
    /> 
  },
  getLessonElements: function() {
    return this.props.lessons.map(lesson => {
      if (lesson.id !== this.props.newLessonId)
        return this.getLessonElement(lesson);
      else
        return this.getLessonCreator(lesson);
    });
  },
  getCreateButton: function() {
    return !this.props.newLessonId ?
      <i onClick={this.props.create}
         className="icon-plus icon-border icon-btn"/> :
      null;
  },
  render: function() {
    return (
      <div className='contract-items'>
        <h4>{this.props.title}:</h4>
        <table className="info-table">
          <tbody>
            {this.getLessonElements()}
          </tbody>
        </table>
        {this.getCreateButton()}
      </div>
    );
  }
});
