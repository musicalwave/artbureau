import React from 'react';
import $ from 'jquery';
import {
  burnLesson, 
  conductLesson, 
  restoreLesson, 
  insertLesson,
  updateLesson,
  deleteLesson
} from '../../actions/lesson_actions.js'; 
import {
  noop,
  findByKeyAndUpdateProp
} from '../../utils/utils.js';
import ContractLessons from './contract-lessons.js';
import moment from 'moment';

export default React.createClass({
  getInitialState: function() {
    return {
      rooms: [],
      statuses: [],
      lessons: [],
      newLessonId: null 
    };
  },
  getDefaultProps: function() {
    return {
      contractId: 0,
      title: 'Title',
      reloadClient: noop,
      reloadContracts: noop
    };
  },
  reloadLessons: function() {
    $.when(
      $.get(
        '/do/contract/lessons', 
        { contractId: this.props.contractId }
      )
    ).then((lessons) => {
      this.setState({
        lessons: lessons
      });
    });
  },
  reload: function() {
    this.props.reloadClient();
    this.props.reloadContracts();
    this.reloadLessons();
  },
  getNewLessonId: function() {
    var maxId = this.state.lessons.reduce((currMaxId, lesson) => {
      return lesson.id > currMaxId ? lesson.id : currMaxId;
    }, 0);
    return maxId + 1;
  },
  getNewLesson: function() {
    return {
      id: this.getNewLessonId(),
      contractId: this.props.contractId,
      date: parseInt(moment().format('x')),
      fromTime: '10:00',
      toTime: '11:00',
      roomId: 1,
      statusId: 1
    };
  },
  create: function() {
    var newLesson = this.getNewLesson();
    this.setState({
      lessons: this.state.lessons.concat(newLesson),
      newLessonId: newLesson.id
    });
  },
  insert: function(lesson) {
    insertLesson(
      JSON.stringify(lesson), 
      this.reload
    );
    this.setState({
      newLessonId: null
    });
  },
  update: function(lesson) {
    updateLesson(
      JSON.stringify(lesson), 
      this.reload
    );
  },
  burn: function(lesson) {
    burnLesson(
      lesson.id, 
      this.reload
    );
  },
  conduct: function(lesson) {
    conductLesson(
      lesson.id, 
      this.reload
    );
  },
  restore: function(lesson) {
    restoreLesson(
      lesson.id, 
      this.reload
    );
  },
  delete: function(lesson) {
    deleteLesson(
      JSON.stringify(lesson),
      this.reload
    );
  },
  remove: function(lesson) {
    this.setState({
      lessons: this.state.lessons.filter(currLesson => 
        currLesson.id !== lesson.id
      ),
      newLessonId: this.state.newLessonId === lesson.id ?
        null : 
        this.state.newLessonId
    });
  },
  dateChanged: function(lessonId, date) {
    this.setState({
      lessons: findByKeyAndUpdateProp(
        this.state.lessons, 
        'id', 
        lessonId, 
        'date', 
        date
      )
    });
  },
  roomChanged: function(lessonId, roomId) {
    this.setState({
      lessons: findByKeyAndUpdateProp(
        this.state.lessons, 
        'id', 
        lessonId, 
        'roomId', 
        roomId
      )
    });
  },
  fromTimeChanged: function(lessonId, fromTime) {
    this.setState({
      lessons: findByKeyAndUpdateProp(
        this.state.lessons, 
        'id', 
        lessonId, 
        'fromTime', 
        fromTime
      )
    });
  },
  toTimeChanged: function(lessonId, toTime) {
    this.setState({
      lessons: findByKeyAndUpdateProp(
        this.state.lessons, 
        'id', 
        lessonId, 
        'toTime', 
        toTime
      )
    });
  },
  statusChanged: function(lessonId, statusId) {
    this.setState({
      lessons: findByKeyAndUpdateProp(
        this.state.lessons, 
        'id', 
        lessonId, 
        'statusId', 
        statusId
      )
    });
  },
  revert: function(oldLesson) {
    this.setState({
      lessons: this.state.lessons.map((lesson) => {
        return lesson.id === oldLesson.id ?
          oldLesson :
          lesson;
      })
    });
  },
  render: function() {
    return (
      <ContractLessons
        title={this.props.title}
        rooms={this.state.rooms}         
        statuses={this.state.statuses}
        lessons={this.state.lessons}
        create={this.create}
        insert={this.insert}
        update={this.update}
        delete={this.delete}
        remove={this.remove}
        conduct={this.conduct}
        burn={this.burn}
        restore={this.restore}
        dateChanged={this.dateChanged}
        roomChanged={this.roomChanged}
        fromTimeChanged={this.fromTimeChanged}
        toTimeChanged={this.toTimeChanged}
        statusChanged={this.statusChanged}
        revert={this.revert}
        newLessonId={this.state.newLessonId}
      />
    );
  },
  componentDidMount: function() {
    $.when(
      $.get('/do/rooms'),
      $.get('/do/lessons/statuses'),
      $.get('/do/contract/lessons', { contractId: this.props.contractId })
    ).then((rooms, statuses, lessons) => {
      this.setState({ 
        rooms: rooms[0], 
        statuses: statuses[0],
        lessons: lessons[0] 
      });
    });
  }  
})

