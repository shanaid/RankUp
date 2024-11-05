import React, { useEffect, useState  } from 'react';
import test from '../../assets/images/test1.png';
import RingImage from '../../assets/images/ring.gif';
import PopImage from '../../assets/images/pop.gif';
import star from '../../assets/images/star.gif';
import flower from '../../assets/images/flower.gif';
import '../../assets/font/Font.css' 
import './TodoCreature.css'
import { useDispatch, useSelector } from 'react-redux';
import { fetchUserData } from '../../store/userSlice';
import { setToken } from '../../store/slice';
import eg1 from '../../assets/images/1.gif'
import eg2 from '../../assets/images/2.gif'
import eg3 from '../../assets/images/3.gif'
import eg4 from '../../assets/images/4.gif'
import eg5 from '../../assets/images/5.gif'

const TodoCreature = () => {
  const dispatch = useDispatch();
  
  const token = useSelector((state) => state.auth.token);
  const user = useSelector((state) => state.user);
  const { image, level, exp, status } = user;

  const [effectImage, setEffectImage] = useState(null);
  
  useEffect(() => {
    const storedToken = sessionStorage.getItem('accessToken');
    if (storedToken && !token) {
      dispatch(setToken(storedToken));
    }

    if (status === 'idle' && storedToken) {
      dispatch(fetchUserData(storedToken));
    }
  }, [status, dispatch, token]);

  useEffect(() => {
    const handleToggleTodo = () => {
      // 할일 토글 시 랜덤 이미지 선택
      const images = [ RingImage, PopImage, star, flower ];
      const randomImage = images[Math.floor(Math.random() * images.length)];
      setEffectImage(randomImage);
      
      // 일정 시간 후에 이미지 제거 (2초 후 제거)
      setTimeout(() => {
        setEffectImage(null);
      }, 3000);
    };

    document.addEventListener('toggleTodo', handleToggleTodo);

    return () => {
      document.removeEventListener('toggleTodo', handleToggleTodo);
    };
  }, []);

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
  
  const maxExp = getMaxExpForLevel(level);

  const getRandomEggImage = () => {
    const eggImages = [eg1, eg2, eg3, eg4, eg5];
    return eggImages[Math.floor(Math.random() * eggImages.length)];
  };

  const displayImage = image ? `data:image/gif;base64,${image}` : getRandomEggImage();

  return (
    <div className="TodoCreatureA">
      <p className="TodoCreatureAName">Lv. {level}</p> 
      <img src={displayImage} alt="creature" />
      {/* <img src={test} alt="creature" /> */}
      {effectImage && <img src={effectImage} alt="effect" className="TodoCreatureEffect" />}
      <div className="TodoCreatureAInfo">
        <div className="TodoCreatureAExp">
          <p>EXP</p>
          <progress value={exp} max={maxExp}></progress>
        </div>
      </div>
    </div>
  );
};

export default TodoCreature;