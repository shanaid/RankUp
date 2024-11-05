import React, { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import Sidebar from '../Main/Sidebar';
import Search from '../Main/Search';
import TodoMainList from './TodoMainList';
import TodoCreature from './TodoCreature';
import './Todo.css'


const Todo = () => {
  const location = useLocation();
  const date = location.state?.date; // 상태에서 date를 읽어옵니다.4

  // console.log('date :::: ', date);
  return (
    <div className="TodoBack">
      <div className="TodoContainer">
        <Sidebar />
        <div className="TodoMain">
          <div className="TodoDashboard">
            <div className="TodoList">
              <TodoMainList
                date = {date}
              />
            </div>
            <div className="TodoCreatures">
              <TodoCreature />
            </div>
          </div>
          <Search />
        </div>
      </div>
    </div>
  );
};

export default Todo;
