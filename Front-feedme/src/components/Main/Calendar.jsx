// ReactCalendar.js
import React, { useEffect } from 'react';
import '../Main/Calendar.css';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import { useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { setCalendarTodos, addCalendarTodos, setDailyTodos } from '../../store/todoSlice';
import { useNavigate } from 'react-router-dom';
import moment from "moment";

function ReactCalendar() {
  const dispatch = useDispatch();
  const navigate = useNavigate(); // useNavigate 훅을 사용하여 페이지 이동
  const [value, onChange] = useState(new Date()); // 초기값은 현재 날짜
  const { token } = useSelector((state) => state.auth);
  const { calendarTodos, dailyTodos } = useSelector((state) => state.todo);

  const handleMonthChange = ({ activeStartDate }) => {
    const date = new Date(activeStartDate);
    onChange(date);
    calendarTodo(date);
  };

  const calendarTodo = (month) => {
    const formattedDate = new Date(month);  // 새로운 Date 객체 생성
    formattedDate.setDate(formattedDate.getDate() + 1);  // 날짜에 1일 추가
    const date = formattedDate.toISOString().split('T')[0];  // YYYY-MM-DD 형식으로 변환
    console.log(date);
    axios.get(`https://i11b104.p.ssafy.io/api/todos/calendar?date=${date}`,
      {
        headers: { Authorization: sessionStorage.getItem('accessToken'), },
      }
    )
      .then(response => {
        if (Array.isArray(response.data)) {
          dispatch(setCalendarTodos(response.data));
        } else {
          console.error('Unexpected response format:', response.data);
        }
      })
      .catch((error) => console.error('Error:', error));
  }

  useEffect(() => {
    calendarTodo(value);  
  },[]);

  // console.log('Calendar Todos:', calendarTodos);

  // 각 타일에 숫자를 표시하는 함수
  const tileContent = ({ date, view }) => {
    if (view === 'month') {
      const todoForDate = calendarTodos.find(calendarTodo => {
        const todoDate = new Date(calendarTodo.date);

        return (
          todoDate.getDate() === date.getDate() &&
          todoDate.getMonth() === date.getMonth() &&
          todoDate.getFullYear() === date.getFullYear()
        );
      });

      if (todoForDate) {
        return (
          <div className='date-number'>
            {todoForDate.completed}/{todoForDate.total}
          </div>
        );
      }
    }

    return null;
  };

  // 날짜 클릭 시 다른 페이지로 이동하는 함수
  const handleDateClick = (date) => {

    const formattedDate = moment(date).format('YYYY-MM-DD'); // 날짜를 YYYY-MM-DD 형식으로 포맷

    navigate('/Todo', { state: { date: formattedDate } }); // 상태로 날짜 전달

  };

  return (
    <div className='Calendar'>
      <Calendar
        onChange={onChange}
        value={value}
        formatDay={(locale, date) => moment(date).format("DD")}
        tileContent={tileContent}
        onClickDay={handleDateClick}
        onActiveStartDateChange={handleMonthChange}
      />
    </div>
  );
}

export default ReactCalendar;