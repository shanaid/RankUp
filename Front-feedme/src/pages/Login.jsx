import React from 'react';
import { Link } from 'react-router-dom';
import '../pages/Login.css'
import '../assets/font/Font.css'
import MyNaverLoginButton from '../components/Buttons/NaverButton';
import MyKakaoLoginButton from '../components/Buttons/KakaoButton';

const Login = () => {
  return (
    <div className='Login'>
      <div className='LoginRectangle'>
        <div className='LoginImage'></div>
        <div className='LoginRecInLogin'>
          <h1>Login</h1>
          <p>Quickly sign in with your social network</p>
          <hr />
          <div class="LoginButtons">
            <div className='LoginGoogleButton'>
              {/* <Link to='/Main'> */}
                <MyKakaoLoginButton style={{
                  height: "45px",
                  fontSize: "17px"
                }}/>
              {/* </Link> */}
            </div>
            <div className='LoginNaverButton'>
              {/* <Link to='/Main'> */}
                <MyNaverLoginButton style={{
                  height: "45px",
                  fontSize: "17px"
                }} />
              {/* </Link> */}
            </div>
          </div>
          <div>
            <span>©Phoenix</span>
            {/* <Link className='LoginSignup' to='/Signup'>Signup</Link> */}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
