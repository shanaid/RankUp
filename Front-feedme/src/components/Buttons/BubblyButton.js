import React, { useState } from 'react';
import './BubblyButton.css'; // 변환된 CSS 파일을 import 합니다.

const BubblyButton = ({ children }) => {
  const [animate, setAnimate] = useState(false);

  const animateButton = (e) => {
    // e.preventDefault(); // e.preventDefault()를 호출하지 않음

    setAnimate(false); // 애니메이션 초기화
    setTimeout(() => {
      setAnimate(true); // 애니메이션 시작
      setTimeout(() => {
        setAnimate(false); // 애니메이션 종료
      }, 700);
    }, 0);
  };

  return (
    <button
      className={`bubbly-button ${animate ? 'animate' : ''}`}
      onClick={animateButton}
    >
      {children}
    </button>
  );
};

export default BubblyButton;
