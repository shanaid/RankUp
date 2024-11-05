import React from 'react';
import './Creature.css';
import eg1 from '../../assets/images/1.gif'
import eg2 from '../../assets/images/2.gif'
import eg3 from '../../assets/images/3.gif'
import eg4 from '../../assets/images/4.gif'
import eg5 from '../../assets/images/5.gif'

const Creature = ({ creature }) => {
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

  const maxExp = getMaxExpForLevel(creature.level);

  const getRandomEggImage = () => {
    const eggImages = [eg1, eg2, eg3, eg4, eg5];
    return eggImages[Math.floor(Math.random() * eggImages.length)];
  };

  // const displayImage = creature.image ? `data:image/gif;base64,${creature.image}` : getRandomEggImage();
  const displayImage = creature.image ? `data:image/gif;base64,${creature.image}` : getRandomEggImage();


  return (
    <div className="MCreature">
      <p className="MCreatureName">{creature.name}</p>
      <p className="MCreatureterm">🤍 {creature.daysTogether}일째 함께하는 중</p>
      
      <img src={displayImage} alt="creature" />
      <div className="MCreatureInfo">
        <p className="MCreatureLv">Lv. {creature.level}</p>
        <div className="MCreatureExp">
          <p>EXP</p>
          +
          <progress value={creature.exp} max={maxExp}></progress>
        </div>
      </div>
    </div>
  );
};
 
export default Creature;
