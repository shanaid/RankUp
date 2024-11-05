import React, { useEffect, useState } from 'react';
import './TodoList.css';
import axios from 'axios';
import { AiFillFire } from "react-icons/ai";

const ToDoList = ({ onClick }) => {

  const [incompletedTodos, setIncompletedTodos] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('https://i11b104.p.ssafy.io/api/todos/main/daily', {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': sessionStorage.getItem('accessToken'),
          }
        });

        if (response.status === 200) {
          console.log('todos : ', response.data); // 실제 데이터에 접근
          setIncompletedTodos(response.data); // 불러온 데이터를 상태로 설정
        } else {
          console.log('불러오기 실패', response);
        }

        const creatureRes = await axios.get(`https://i11b104.p.ssafy.io/api/creatureTodo/main/daily`, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': sessionStorage.getItem('accessToken'),
          }
        });

        if (creatureRes.status === 200) {
          const IncompletedMission = creatureRes.data;

          // 이전 상태를 기반으로 업데이트
          setIncompletedTodos(prevTodos => [
            ...prevTodos,
            ...IncompletedMission.map(mission => ({
              id: mission.id,
              content: mission.content,
              createdAt: mission.createdAt,
              isCompleted: mission.isCompleted
            }))
          ]);
        } else {
          console.log("일일 미션 안한거 추가 실패", creatureRes);
        }
      } catch (error) {
        console.error('서버 요청 중 오류 발생', error);
      }

    };

    fetchData();
  }, []);

  console.log('data : ', incompletedTodos.length);

  return (
    <div className='ToDoList' onClick={onClick}>
      <div>
        <span className='ToDoListTitle'>TO DO</span>
        <span className='ToDoListNotDo'>
          {incompletedTodos ? (incompletedTodos.length < 10 ? `(0${incompletedTodos.length})` : `(${incompletedTodos.length})`) : '(00)'}
        </span>
      </div>
      <div className='ToDoListContents'>
        <ul>
          {incompletedTodos && incompletedTodos.length > 0 ? (
            incompletedTodos.map((todo, index) => (
              <li key={index} style={{
                alignItems : 'cetner'
              }}>
                <AiFillFire style={{
                  color: 'rgb(255,95,95)',
                  marginRight : '5px'
                }}/>
                <label htmlFor={`todo-${index}`}>{todo.content}</label>
              </li>
            ))
          ) : (
            <li>할 일이 없습니다..</li>
          )}
        </ul>
      </div>
    </div>
  );

};

export default ToDoList;
