import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

// 비동기 친구 목록 불러오기 Thunk
export const fetchFriendsList = createAsyncThunk(
  'friends/fetchFriendsList', 
  async (token, { rejectWithValue }) => {
    try {
      const response = await axios.get('https://i11b104.p.ssafy.io/api/friends/chats', {
        headers: {
          Authorization: `${token}`, // Bearer 추가 여부 확인 필요
        },
      });
      
      return response.data.map(friend => ({
        friendId: friend.friendId,
        counterpartNickname: friend.counterpartNickname,
        avatar: friend.avatar,
        isChecked: friend.isChecked,
        receiveTime: friend.receiveTime,
      }));
    } catch (error) {
      return rejectWithValue(error.response?.data || 'Something went wrong');
    }
  }
);

const friendsSlice = createSlice({
  name: 'friends',
  initialState: {
    list: [],
    status: 'idle',
    error: null,
  },
  reducers: {
    // 새로운 채팅 데이터로 친구 목록 업데이트
    updateFriendsList: (state, action) => {
      const newChat = action.payload;
      const index = state.list.findIndex(f => f.friendId === newChat.friendId);
  
      if (index !== -1) {
        // 이미 존재하는 친구라면 목록에서 제거하고 가장 앞으로 이동
        const updatedFriend = {
          ...state.list[index],
          ...newChat,
        };
        state.list.splice(index, 1);  // 기존 위치에서 제거
        state.list.push(updatedFriend);  // 가장 뒤로 추가
      } else {
        // 새로운 친구라면 뒤에 추가
        state.list.push(newChat);
      }
    }
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchFriendsList.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchFriendsList.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.list = action.payload;
      })
      .addCase(fetchFriendsList.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  },
});

// 액션과 리듀서를 내보내기
export const { updateFriendsList } = friendsSlice.actions;
export default friendsSlice.reducer;
