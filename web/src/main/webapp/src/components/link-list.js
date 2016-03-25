var React = require('react');
var Link = require('./link.js');

var LinkList = React.createClass({
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

module.exports = LinkList;
