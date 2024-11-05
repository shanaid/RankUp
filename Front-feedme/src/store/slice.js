import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  email: null,
  nickname: null,
  birthday: null,
  creatureName: null,
  keywords: [],
  keyword: 'pink',
  token: null,
  hasCreature: false,
  photo:''
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    setNickname: (state, action) => {
      state.nickname = action.payload;
    },
    setBirthday: (state, action) => {
      state.birthday = action.payload;
    },
    setphoto:(state,action)=>{
      state.photo = action.payload;
    },
    setEmail: (state, action) => {
      state.email = action.payload;
    },
    setCreatureName: (state, action) => {
      state.creatureName = action.payload;
    },
    setKeyword: (state, action) => {
      state.keyword = action.payload;
    },
    addKeyword: (state, action) => {
      const { type, value } = action.payload;
      state.keywords.push({ type, value });
    },
    removeKeyword: (state, action) => {
      const { type, value } = action.payload;
      state.keywords = state.keywords.filter(
        (keyword) => keyword.type !== type || keyword.value !== value
      );
    },
    resetKeywords: (state) => {
      state.keywords = [];
    },
    setToken: (state, action) => {
      state.token = action.payload;
    },
    setCreature: (state, action) => {
      state.hasCreature = action.payload;
    },
    logout: (state) => {
      state.token = null;
      state.hasCreature = false;
    },
  }
});

export const { 
  setNickname, 
  setBirthday, 
  setphoto,
  setEmail,
  setCreatureName,
  setKeyword,
  addKeyword, 
  removeKeyword, 
  resetKeywords,
  setToken, 
  setCreature, 
  logout 
} = authSlice.actions;
export default authSlice.reducer;
