import React from 'react';
import Link from './link.js';

export default React.createClass({
   render: function() {
       var links = this.props.links.map(function(link) {
          var selected = (link.id === this.props.selectedId)          
          return (<li key={link.id}>
                    <Link link={link}
                          selected={selected}
                          clickHandler={this.props.clickHandler}
                          hoverHandler={this.props.hoverHandler}/>
                  </li>);
       }.bind(this));
       
       return(
            <ul className='link-list'>
                {links}
            </ul>
       );
   } 
});

