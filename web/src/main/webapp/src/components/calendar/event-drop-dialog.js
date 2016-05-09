import React from 'react';
import {noop} from '../../utils/utils.js';

export default React.createClass({
  getInitialState: function() {
    return {
      temporary: true,
      causedByClient: true
    };
  },
  getDefaultProps: function() {
    return {
      onAccept: noop
    };
  }, 
  selectTemporary: function() {
    console.log('select temp');
    this.setState({
      temporary: true
    });
  },
  selectPermanent: function() {
    console.log('select perm');
    this.setState({
      temporary: false
    });
  },
  selectClient: function() {
    console.log('select client');
    this.setState({
      causedByClient: true
    });
  },
  selectTeacher: function() {
    console.log('select teacher');
    this.setState({
      causedByClient: false
    });
  },
  accept: function() {
    this.props.onAccept(
      this.state.temporary,
      this.state.causedByClient ? 1 : 0
    );
  },
  render: function() {
    return (
      <div className='event-drop-dialog'>
        <div>
          <input 
            type='radio' 
            name='shiftType' 
            onChange={this.selectTemporary} 
            checked={this.state.temporary}
          />Временный<br/>
          <input 
            type='radio' 
            name='shiftType' 
            onChange={this.selectPermanent} 
            checked={!this.state.temporary}
          />Постоянный
        </div>
        <hr/>
        <div>
          <input 
            type='radio' 
            name='culprit' 
            onChange={this.selectClient} 
            checked={this.state.causedByClient}
          />По вине ученика<br/>
          <input 
            type='radio' 
            name='culprit' 
            onChange={this.selectTeacher} 
            checked={!this.state.causedByClient}
          />По вине учителя
        </div>
        <hr/>
        <button onClick={this.accept}>Перенести</button>
      </div>
    );
  }
})
