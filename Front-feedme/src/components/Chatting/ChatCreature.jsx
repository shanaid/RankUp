import React, { useState, useEffect, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setToken } from '../../store/slice';
import { fetchUserData } from '../../store/userSlice';
import axios from 'axios';
import './ChatCreature.css';
import '../../assets/font/Font.css';

const ChatCreature = ({ user }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [inputValue, setInputValue] = useState('');  
  const [chatHistory, setChatHistory] = useState([]);  
  const [loading, setLoading] = useState(false); 
  const chatWindowRef = useRef(null);
  console.log(user)
  useEffect(() => {
    const sessionToken = sessionStorage.getItem('accessToken');
    if (sessionToken) {
      dispatch(setToken(sessionToken));
    } else {
      navigate('/login'); 
    }
  }, [dispatch, navigate]);

  const token = useSelector((state) => state.auth.token);

  useEffect(() => {
    if (token) {
      dispatch(fetchUserData(token));
    }
  }, [dispatch, token]);

  useEffect(() => {
    if (chatWindowRef.current) {
      chatWindowRef.current.scrollTop = chatWindowRef.current.scrollHeight;
    }
  }, [chatHistory]);

  const handleSend = async () => {
    if (!inputValue.trim()) return; 
    
    setChatHistory(prevHistory => [
      ...prevHistory,
      { type: 'user', text: inputValue },
      { type: 'bot', text: `${user.creatureName}가 생각 중 입니다...`},
    ]);

    setInputValue(''); 
    setLoading(true); 

    try {
      const response = await axios.get('https://i11b104.p.ssafy.io/api/api/chat/creature', {
        params: {
          ragQuestion: inputValue,
        },
        headers: {
          Authorization: `${token}`, 
        },
      });

      
      setChatHistory(prevHistory => {
        const updatedHistory = [...prevHistory];
        updatedHistory[updatedHistory.length - 1] = { type: 'bot', text: response.data.ragData };
        return updatedHistory;
      });
    } catch (error) {
      console.error('Error fetching chat data:', error);
      
      setChatHistory(prevHistory => {
        const updatedHistory = [...prevHistory];
        updatedHistory[updatedHistory.length - 1] = { type: 'bot', text: '오류가 발생했습니다. 다시 시도해주세요.' };
        return updatedHistory;
      });
    } finally {
      setLoading(false);  
    }
  };

  return (
    <div className="ChatCreatureMain">
      <div className="chat-window" ref={chatWindowRef}>
        {chatHistory.map((chat, index) => (
          <div key={index} className={`chat-message ${chat.type}`}>
            {chat.text}
          </div>
        ))}
      </div>
      <div className="chat-input">
        <input
          type="text"
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
          placeholder="질문을 입력하세요..."
          onKeyPress={(e) => {  
            if (e.key === 'Enter') {  
              handleSend();
            }
          }}
          disabled={loading} 
        />
        <button onClick={handleSend} disabled={loading}>보내기</button>
      </div>
    </div>
  );
};

export default ChatCreature;
