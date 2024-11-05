import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrashCan } from '@fortawesome/free-solid-svg-icons';

const CreatureDel = ({ onRelease, creature }) => {
  const handleRelease = () => {
    // const creatureId = 1; 
    onRelease(creature.id);  
  };

  return (
    <div>
        {/* <button className="MCreatureDelButton" onClick={handleRelease}>방생</button> */}
        <FontAwesomeIcon 
          icon={faTrashCan} 
          style={{ color: "#8c8c8c" }} 
          className="MCreatureDelButton" 
          onClick={handleRelease}
        />
    </div>
  );
};
 
export default CreatureDel;
