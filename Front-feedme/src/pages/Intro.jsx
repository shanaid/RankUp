import React, { useEffect,useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../pages/Intro.css';
import '../assets/font/Font.css';
 
const Intro = () => {
  const navigate = useNavigate();
  const [weatherCategory, setWeatherCategory] = useState('notknow');
  
  useEffect(() =>{
    const fetchWeather = async () => {
      try {
        navigator.geolocation.getCurrentPosition(
          async (position) => {
            const lat = position.coords.latitude;
            const lon = position.coords.longitude;
            const apiKey = process.env.REACT_APP_API_KEY;
            const response = await fetch(`https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${apiKey}`);
            const data = await response.json();
            if (response.ok) {
              
            } else {
              console.error('날씨 정보 가져오기 실패:', data);
              setWeatherCategory('notknow');
            }
          },
          (error) => {
            console.error("위치 정보 가져오기 실패:", error);
            setWeatherCategory('notknow');
          }
        );
      } catch (error) {
        console.error("날씨 정보 가져오는 중 오류 발생:", error);
        setWeatherCategory('notknow');
      }
    };
    fetchWeather();
  })
  
  const handleClick = () => {
    const token = sessionStorage.getItem('accessToken');
    console.log('토큰:', token);

    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        console.log('토큰 페이로드:', payload);

        const currentTime = Math.floor(Date.now() / 1000);
        console.log('현재 시간:', currentTime);
        console.log('토큰 만료 시간:', payload.exp);

        if (payload.exp < currentTime) {
          console.log('토큰이 만료되었습니다.');
          sessionStorage.removeItem('accessToken');
          navigate('/Login'); // 바로 로그인 페이지로 리디렉션
        } else {
          console.log('토큰이 유효합니다.');
          navigate('/Main'); // 바로 메인 페이지로 리디렉션
        }
      } catch (e) {
        console.error('토큰 파싱 중 오류 발생:', e);
        sessionStorage.removeItem('accessToken');
        navigate('/Login'); // 오류 발생 시 로그인 페이지로 리디렉션
      }
    } else {
      console.log('토큰이 존재하지 않습니다.');
      navigate('/Login'); // 토큰이 없으면 로그인 페이지로 리디렉션
    }
  };

  return (
    <div className="Intro">
      <div className="IntroChild">
        <h1 className="IntroLogo">Feed Me</h1>
        <div className="IntroClickWrapper">
          <button className="w-btn w-btn-gra1" onClick={handleClick}>
            Click
          </button>
        </div>
      </div>
    </div>
  );
};

export default Intro;
