import React, { useState, useEffect, useRef } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { useDispatch, useSelector } from 'react-redux';
import { fetchUserData } from '../../store/userSlice';
import axios from 'axios';
import './ChatWindow.css';

const ChatWindow = ({ roomId }) => {
  const dispatch = useDispatch();
  const token = useSelector((state) => state.auth.token);
  const user = useSelector((state) => state.user);
  const { sendId, nickname } = user;

  const [messageContent, setMessageContent] = useState('');
  const [messages, setMessages] = useState([]);
  const [hasMore, setHasMore] = useState(true);
  const [skip, setSkip] = useState(0);
  const messageContainerRef = useRef(null);
  const stompClient = useRef(null);
  const isSubscribed = useRef(false);

  const limit = 5;

  useEffect(() => {
    if (roomId) {
      console.log('Room ID:', roomId);
      disconnect(); // 이전 연결을 명확히 해제
      connect(); // 새로운 연결 설정
    }
    return () => {
      disconnect(); // 컴포넌트 언마운트 시 연결 해제
    };
  }, [roomId]);

  useEffect(() => {
    if (token) {
      dispatch(fetchUserData(token));
    }
  }, [dispatch, token]);

  const renewConnect = async (id) => {
    try {
      const response = await axios.post(`https://i11b104.p.ssafy.io/api/friends/chats/connect`, 
      {}, // 본문 데이터가 필요 없을 경우 빈 객체 전달
      {
        params: {
          room: id // URL에 추가될 요청 파라미터
        },
        headers: {
          'Content-Type': 'application/json',
          'Authorization': sessionStorage.getItem('accessToken'),
        }
      });
      console.log(response.data);
    } catch (error) {
      console.error('Error fetching connect:', error);
    }
  };
  
  const renewDisconnect = async (id) => {
    try {
      const response = await axios.post(`https://i11b104.p.ssafy.io/api/friends/chats/disconnect`,
      {}, // 본문 데이터가 필요 없을 경우 빈 객체 전달
        {
          params: {
            room: id // URL에 추가될 요청 파라미터
          },
          headers: {
            'Content-Type': 'application/json',
            'Authorization': sessionStorage.getItem('accessToken'),
          }
        });
        console.log(response.data);
      } catch (error) {
        console.error('Error fetching connect:', error);
      }
  };

  const connect = () => {

    if (stompClient.current) {
      disconnect(); // 기존 연결이 있을 경우 해제
    }

    renewConnect(roomId);

    const socket = new SockJS('https://i11b104.p.ssafy.io/api/ws/friendChat');
    const newClient = new Client({
        webSocketFactory: () => socket,
        debug: (str) => {
            console.log(str);
        },
        onConnect: (frame) => {
            console.log('Connected: ' + frame);
            
            stompClient.current = newClient; 

            if (stompClient.current) { 
                stompClient.current.subscribe(`/chatRoom/messages/${roomId}`, (message) => {
                    const receivedMessage = JSON.parse(message.body);
                    showMessage(receivedMessage);
                });

                stompClient.current.subscribe(`/chatRoom/loadMessages/${roomId}`, (message) => {
                    const slice = JSON.parse(message.body);
                    const newMessages = slice.content || [];

                    const updatedMessages = newMessages.map(msg => ({
                      ...msg,
                      isOwnMessage: msg.sendNickname === nickname 
                    }));

                    if (updatedMessages.length === 0) {
                        setHasMore(false);
                    } else {
                        const previousScrollHeight = messageContainerRef.current.scrollHeight;
                        setMessages((prevMessages) => [...updatedMessages.reverse(), ...prevMessages]);
                        setSkip((prevSkip) => prevSkip + limit);

                        setTimeout(() => {
                            const newScrollHeight = messageContainerRef.current.scrollHeight;
                            messageContainerRef.current.scrollTop = newScrollHeight - previousScrollHeight;
                        }, 100); // 스크롤 위치를 복원합니다.
                    }
                    setHasMore(!slice.last);
                });

                isSubscribed.current = true;
                fetchMoreData(); 
            } else {
                console.warn('stompClient.current is null after connection');
            }
        },
        onStompError: (frame) => {
            console.error('Broker reported error: ' + frame.headers['message']);
            console.error('Additional details: ' + frame.body);
        },
        onDisconnect: () => {
            console.log("Disconnected");
            isSubscribed.current = false;
            stompClient.current = null;
        },
        reconnectDelay: 1000
    });

    newClient.activate();
  };

  const disconnect = () => {
    if (stompClient.current) {
      stompClient.current.deactivate(); // 기존 연결 해제
      stompClient.current = null;
      isSubscribed.current = false;
      setMessages([]); // 메시지 초기화
      setSkip(0); // 스킵 초기화
      setHasMore(true); // 페이징 관련 상태 초기화
      console.log("Disconnected from the WebSocket");
      renewDisconnect(roomId);
    }
  };

  const sendMessage = () => {
    if (stompClient.current && messageContent.trim() !== '') {
      stompClient.current.publish({
        destination: `/chat/messages/${roomId}`,
        headers:{
          Authorization: `${token}`,
        },
        body: JSON.stringify({
          'sendId': sendId,
          'content': messageContent
        })
      });
      setMessageContent('');
    }
  };

  const fetchMoreData = () => {
    if (stompClient.current && hasMore) {
      stompClient.current.publish({
        destination: `/chat/loadMessages/${roomId}`,
        body: JSON.stringify({
          'skip': skip,
          'limit': limit,
          'memberId' : sendId
        })
      });
    }
  };

  const handleScroll = () => {
    if (messageContainerRef.current.scrollTop === 0 && hasMore) {
      fetchMoreData();
    }
  };

  const showMessage = (message) => {
    console.log("Received message:", message);

    try {
        if (message && typeof message === 'object') {
            const isOwnMessage = message.sendNickname === nickname;
            setMessages((prevMessages) => [...prevMessages, { ...message, isOwnMessage }]);
        } else {
            console.warn("Unexpected message format:", message);
        }
    } catch (error) {
        console.error("Failed to process message:", error);
        console.error("Original message:", message);
    }

    if (messageContainerRef.current) {
        setTimeout(() => {
            messageContainerRef.current.scrollTop = messageContainerRef.current.scrollHeight;
        }, 100); // 짧은 지연 시간 설정
    }
};

  return (
    <div className='WebChatDiv'>
      
      <div
        id="messageContainer"
        ref={messageContainerRef}
        onScroll={handleScroll}
      >
        {messages.map((message, index) => (
          <div 
            key={index} 
            className={`message ${message.isOwnMessage ? 'own' : 'other'}`}
          >
            {message.message}
          </div>
        ))}
      </div>

      <div className="WebChats">
        <input
          type="text"
          value={messageContent}
          onChange={(e) => setMessageContent(e.target.value)}
          placeholder="메시지를 입력하세요"
          className="WebChatInput"
          onKeyPress={(e) => {  
            if (e.key === 'Enter') {  
              sendMessage();
            }
          }}
        />
        <button onClick={sendMessage} className="WebChatButton">보내기</button>
      </div>
    </div>
  );
};

export default ChatWindow;
