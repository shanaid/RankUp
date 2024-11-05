import React from "react";
import Sidebar from "../Main/Sidebar";
import SearchBar from "../Main/Search";
import DiaryPic from "./DiaryPic";
import "./Diary.css";

const Diary = () => {
  return (
    <div className="Diary">
      <div className="DiaryContainer">
        <Sidebar />
        <div className="DiaryMain">
          <div className="DiaryDashboard">
            <DiaryPic />
          </div>

          <SearchBar />
        </div>
      </div>
    </div>
  );
};

export default Diary;
