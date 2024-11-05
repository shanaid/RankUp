import React, { useState} from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleLeft } from '@fortawesome/free-solid-svg-icons';
import './LoginInfo.css';
import '../../assets/font/Font.css'

const LoginInfo = () => {
  const [nickname, setNickname] = useState('');
  const [email, setEmail] = useState('');
  const [birthdate, setBirthdate] = useState('');

  return (
    <div className="LoginMain">
      <div className="LoginMainFormContainer">
        <Link to="/Login" className="backButton">
          <FontAwesomeIcon icon={faAngleLeft} size="2x" />
        </Link>
        <div className="LoginMainHeadre">회원 정보 입력</div>
        <form className='LoginInfoForm'>
        <div className="LoginInfoNicknameContainer">
          <label htmlFor="nickname" className="LoginInfoFormNickname">닉네임</label>
          <input
            className="LoginInfoFormNicknameInput"
            type="text"
            id="nickname"
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
          />
        </div>
        
        <div className="LoginInfoEmailContainer">
          <label htmlFor="email" className="LoginInfoEmail">이메일</label>
          <input
            className="LoginInfoEmailInput"
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
          
        <div className="LoginInfoBirthdateContaine">
          <label htmlFor="birthdate" className="LoginInfoBirthdate">생 일</label>
          <input
            className="LoginInfoBirthdateInput"
            type="date"
            id="birthdate"
            value={birthdate}
            onChange={(e) => setBirthdate(e.target.value)}
          />
        </div>

          <Link to="/CreatureCreate">
            <button type="submit" className="LoginInfoButton">다음</button>
          </Link>
        </form>
      </div>
    </div>
  );
};

export default LoginInfo;