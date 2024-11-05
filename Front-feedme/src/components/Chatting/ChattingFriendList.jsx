import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch  } from 'react-redux';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMessage } from '@fortawesome/free-solid-svg-icons';
import { fetchFriendsList } from '../../store/friendsSlice'; 
import './ChattingFriendList.css';
import '../../assets/font/Font.css';
import eg1 from '../../assets/images/1.gif'
import eg2 from '../../assets/images/2.gif'
import eg3 from '../../assets/images/3.gif'
import eg4 from '../../assets/images/4.gif'
import eg5 from '../../assets/images/5.gif'

const ChattingFriendList = ({ friends: initialFriends, onFriendClick, onChatClick }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [activeFriendId, setActiveFriendId] = useState(null);
  const [friends, setFriends] = useState(initialFriends || []); // 로컬 상태로 관리
  const user = useSelector((state) => state.user);
  const token = useSelector((state) => state.auth.token); // 토큰 가져오기
  const dispatch = useDispatch();

  // 부모 컴포넌트에서 friends props가 변경될 때 로컬 상태를 업데이트
  useEffect(() => {
    console.log("Initial friends updated:", initialFriends);
    setFriends(initialFriends || []);
  }, [initialFriends]);

  
  const getRandomEggImage = () => {
    const eggImages = [eg1, eg2, eg3, eg4, eg5];
    return eggImages[Math.floor(Math.random() * eggImages.length)];
  };
  

  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
  };

  const handleChatButtonClick = (friend) => {
    setActiveFriendId(friend.friendId);
    dispatch(fetchFriendsList(token)).then((action) => {
      if (action.payload) {
        setFriends(action.payload); // 업데이트된 친구 리스트로 상태 업데이트
      }
    });
    if (friend.id === 'my-avatar') {
      onFriendClick({ id: 'my-avatar', name: user.nickname, avatar: user.image, isCreature: true }); 
    } else {
      onChatClick(friend);
    }
  };

  const filteredFriends = (friends || []).filter(friend =>
    friend.counterpartNickname
      ? friend.counterpartNickname.toLowerCase().includes(searchTerm.toLowerCase())
      : false
  );

  return (
    <div className="ChatFriendListContainer">
      <input
        type="text"
        placeholder="친구 검색"
        value={searchTerm}
        onChange={handleSearchChange}
        className="ChatFriendSearch"
      />
      <div className="ChatFriendList">
        <div key="my-avatar" className="ChatFriendItem">
          {/* <img src={ user.image ? `data:image/gif;base64,${user.image}` : getRandomEggImage()} alt="내 아바타" className="ChatFriendAvatar" /> */}
          <div className="ChatFriendInfo">
            <span onClick={() => onFriendClick({ id: 'my-avatar', name: user.nickname, avatar: user.image })} className="ChatFriendName">
              {user.creatureName}
            </span>
            <button
              onClick={() => handleChatButtonClick({ id: 'my-avatar', name: user.nickname, avatar: user.image })}
              className={`ChatIconButton ${activeFriendId === 'my-avatar' ? 'active' : ''}`}
            >
              <FontAwesomeIcon icon={faMessage} style={{ fontSize: '1.5rem' }} />
            </button>
          </div>
        </div>

        {filteredFriends
          .sort((a, b) => new Date(b.receiveTime) - new Date(a.receiveTime))
          .map(friend => (
            <div key={friend.friendId} className="ChatFriendItem">
              {/* <img src={friend.avatar ? `data:image/gif;base64,${friend.avatar}` : getRandomEggImage()} alt={friend.counterpartNickname} className="ChatFriendAvatar" /> */}
              <div className="ChatFriendInfo">
                <span onClick={() => onFriendClick(friend)} className="ChatFriendName">
                  {friend.counterpartNickname}
                </span>
                <button
                  onClick={() => handleChatButtonClick(friend)}
                  className={`ChatIconButton ${activeFriendId === friend.friendId ? 'active' : ''}`}
                  style={{ 
                    backgroundColor: friend.isChecked === 0 ? 'red' : 'transparent',
                    borderRadius: '100%',
                  }}
                >
                  <FontAwesomeIcon icon={faMessage} style={{ fontSize: '1.5rem' }} />
                </button>
              </div>
            </div>
          ))}
      </div>
    </div>
  );
};

export default ChattingFriendList;
