import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleLeft, faRotate } from '@fortawesome/free-solid-svg-icons';
import './CreatureResult.css';
import '../../assets/font/Font.css';
import { fetchUserData } from '../../store/userSlice';
import eg1 from '../../assets/images/1.gif'
import eg2 from '../../assets/images/2.gif'
import eg3 from '../../assets/images/3.gif'
import eg4 from '../../assets/images/4.gif'
import eg5 from '../../assets/images/5.gif'

const CreatureResult = () => {
  const dispatch = useDispatch();

  
  const token = useSelector((state) => state.auth.token);
  const user = useSelector((state) => state.user);

  useEffect(() => {
    if (token) {
      dispatch(fetchUserData(token));
    }
  }, [dispatch, token]);
  
  const { creatureName, photo } = useSelector((state) => state.auth);
  
  const getRandomEggImage = () => {
    const eggImages = [eg1, eg2, eg3, eg4, eg5];
    return eggImages[Math.floor(Math.random() * eggImages.length)];
  };

  const displayImage = photo ? `data:image/gif;base64,${photo}` : getRandomEggImage();
  
  return (
    <div className="CreatureResultMain">
      <div className="CreatureResultContainer">
        <Link to="/CreatureCreate" className="backButton">
          <FontAwesomeIcon icon={faAngleLeft} size="2x" />
        </Link>
        <div className="CreatureResultNameContainer">
          <div className="CreatureResultName">{creatureName}</div>
        </div>
        <div className="CreatureResultHeader">
          {/* <img src={`data:image/gif;base64,${photo}`} alt="Creature" className="CreatureResultImage" /> */}
          <img src={displayImage} alt="Creature" className="CreatureResultImage" />
        </div>
        <Link to="/Main">
          <button type="submit" className="CreatureResultStartButton">Start</button>
        </Link>
        {/* <Link to="/CreatureResult" className="CreatureResultReloadButton">
          <button>
            <FontAwesomeIcon icon={faRotate} rotation={180} size="xl" style={{color: "#ffffff",}} />
          </button>
        </Link> */}
      </div>
    </div>
  );
};

export default CreatureResult;
