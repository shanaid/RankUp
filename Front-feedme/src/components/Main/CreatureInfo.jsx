import React, { useEffect } from 'react';
import ToDoList from '../Todo/TodoList';
import '../Main/CreatureInfo.css';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { fetchUserData } from '../../store/userSlice';
import { setToken } from '../../store/slice';
import snowyGif from '../../assets/images/snowy.gif'; 
import rainyGif from '../../assets/images/rainy.gif'; 
import eg1 from '../../assets/images/1.gif'
import eg2 from '../../assets/images/2.gif'
import eg3 from '../../assets/images/3.gif'
import eg4 from '../../assets/images/4.gif'
import eg5 from '../../assets/images/5.gif'

const CreatureInfo = ({weatherCategory}) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const token = useSelector((state) => state.auth.token);
  const user = useSelector((state) => state.user);
  const { creatureName, image, togetherDay, status, error } = user;

  useEffect(() => {
    // 세션 스토리지에서 토큰 가져오기
    const storedToken = sessionStorage.getItem('accessToken');
    if (storedToken && !token) {
      dispatch(setToken(storedToken));
    }

    if (status === 'idle' && storedToken) {
      dispatch(fetchUserData(storedToken));
    }
  }, [status, dispatch, token]);

  if (status === 'loading') return <div>Loading...</div>;
  if (status === 'failed') return <div>Error: {error}</div>;

  const MoveTo = (path) => {
    navigate(path);
  };

  const getRandomEggImage = () => {
    const eggImages = [eg1, eg2, eg3, eg4, eg5];
    return eggImages[Math.floor(Math.random() * eggImages.length)];
  };
  
  const displayImage = image ? `data:image/gif;base64,${image}` : getRandomEggImage();

  return (
    <div className='CreatureInfo'>
      {weatherCategory === 'snowy' && (
        <img 
          src={snowyGif} 
          alt="snowy weather" 
          className="WeatherOverlay" 
        />
      )}
      {weatherCategory === 'rainy' && (
        <img 
          src={rainyGif} 
          alt="rainy weather" 
          className="WeatherOverlay" 
        />
      )}

      <h2>{creatureName}</h2>
      <img 
        className='CreatureInfoPhoto' 
        src={displayImage} 
        alt="creature" 
        onClick={() => MoveTo('/MyPage')} 
      />
      <p>❤️ <span id='CreatureInfoDay'>{togetherDay}</span> 일째 함께하는 중</p>
      <div className='CreatureInfoTodo'>
        <ToDoList onClick={() => MoveTo('/Todo')} />
      </div>
    </div>
  );
};

export default CreatureInfo;
