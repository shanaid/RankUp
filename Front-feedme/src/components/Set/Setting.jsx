import React, { useState } from 'react';
import Sidebar from '../Main/Sidebar';
import Search from '../Main/Search';
import './Setting.css'

const Setting = () => {

  return (
    <div className="SettingContainer">
      <Sidebar/>
      <div className="SettingMain">
        <Search />
          <div className="Settings">
            <p>세팅</p>
          </div>
      </div>
    </div>
  );
};

export default Setting;
