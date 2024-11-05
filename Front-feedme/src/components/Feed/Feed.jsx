import React from 'react';
import Sidebar from '../Main/Sidebar';
import Search from '../Main/Search';
import FeedList from './FeedList';
import './Feed.css'

 
const Feed = () => {
  return (
    <div className="FeedBack">
      <div className="FeedContainer">
        <Sidebar />
        <div className="FeedMain">
          <div className="FeedDashboard">
            <FeedList />
          </div>
          <Search />
        </div>
      </div>
    </div>
  );
};
 
export default Feed;