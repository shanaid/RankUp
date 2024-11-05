import React, {useEffect}from 'react';
import './ChattingFriendProfile.css';
import '../../assets/font/Font.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { useDispatch, useSelector } from 'react-redux';
import { deleteFriend } from '../../store/friendInfoSlice';
import eg1 from '../../assets/images/1.gif'
import eg2 from '../../assets/images/2.gif'
import eg3 from '../../assets/images/3.gif'
import eg4 from '../../assets/images/4.gif'
import eg5 from '../../assets/images/5.gif'

const ChattingFriendProfile = ({ friend, onDelete }) => {
  const dispatch = useDispatch();
  const token = useSelector((state) => state.auth.token);

  useEffect(() => {
    console.log('Friend Info:', friend); // friend 객체의 값이 올바르게 설정되었는지 확인합니다.
  }, [friend]);
  
  const handleDeleteClick = () => {
    dispatch(deleteFriend({ token, counterpartNickname: friend.nickname }))
      .then(() => {
        onDelete(friend); // 성공 시 onDelete 콜백 호출
        window.location.reload();
      });
  };

  const getRandomEggImage = () => {
    const eggImages = [eg1, eg2, eg3, eg4, eg5];
    return eggImages[Math.floor(Math.random() * eggImages.length)];
  };

  const getMaxExpForLevel = (level) => {
    switch (level) {
      case 0:
        return 10;
      case 1:
        return 30;
      case 2:
        return 100;
      case 3:
        return 3000;
      default:
        return 3000;
    }
  };
  
  const maxExp = getMaxExpForLevel(friend.level);
  console.log(friend)
  return (
    <div className="CFProfile">
      <FontAwesomeIcon 
        icon={faTrashCan} 
        style={{ color: "#8c8c8c" }} 
        className="CFProfileDeleteIcon" 
        onClick={handleDeleteClick}
      />
      
      <p className="CFProfileName">{friend.creatureNickname}</p>
      <p className="CFProfileterm">🤍 {friend.join}일째 함께하는 중</p>
      
      <img src={friend.creatureImg ? `data:image/gif;base64,${friend.creatureImg}` : getRandomEggImage()} alt={friend.nickname} className="CFProfileImage" /> 
      <div className="CFProfileInfo">
        <p className="CFProfileLv">Lv. {friend.level}</p>
        <div className="CFProfileExp">
          <p>EXP</p>
          <progress value={friend.exp}  max={maxExp}></progress>
        </div>
      </div>
    </div>
  );
};

export default ChattingFriendProfile;
