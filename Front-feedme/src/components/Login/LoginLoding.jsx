import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { motion } from "framer-motion";
import "./LoginLoding.css";
import { useDispatch } from 'react-redux';
import { setCreature, setToken } from '../../store/slice';

const LoginLoding = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const params = new URLSearchParams(location.search);
  const urlToken = params.get('accessToken');
  const isCreature = (params.get('hasCreature') === 'true') ? true : false;

  useEffect(() => {
    if (!urlToken) {
      navigate('/Login');
    }

    sessionStorage.setItem('accessToken', urlToken);
    dispatch(setToken(urlToken));

    if (!isCreature) {
      navigate('/CreatureCreate');
    } else {
      dispatch(setCreature(true));
      navigate('/Main');
    }
  }, [dispatch, isCreature, navigate, urlToken]);

  return (
    <div className="LoginLoding">
      <div className="LoginLodingMain">
        <div className="LoginLodingWrap">
          <motion.div
            className="LoginLodingMainBox"
            animate={{ scale: [1, 1.5, 1.1] }}
            transition={{ duration: 3, times: [0, 0.2, 1], repeat: Infinity }}
          >
            loading
          </motion.div>
        </div>
      </div>
    </div>
  );
};

export default LoginLoding;