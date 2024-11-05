import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    calendarTodos: [],
    currentMonth: null,
    dailyTodos: [],
    currentDate: new Date(),

};

const todoSlice = createSlice({
    name: 'todo',
    initialState,
    reducers: {
        setCalendarTodos: (state, action) => {
            // 전체 데이터를 대체합니다.
            state.calendarTodos = action.payload;
        },
        addCalendarTodos: (state, action) => {
            // 데이터를 추가합니다.
            state.calendarTodos.push(...action.payload);
        },
        setCurrentMonth: (state, action) => {
            state.currentMonth = action.payload;
        },
        setDailyTodos: (state, action) => {
            state.dailyTodos = action.payload;
        },
        setCurrentDate: (state, action) => {
            state.currentDate = action.payload;
        }
    }
});

export const {
    setCalendarTodos,
    addCalendarTodos,
    setCurrentMonth,
    setDailyTodos,
    setCurrentDate,
    
} = todoSlice.actions;

export default todoSlice.reducer;