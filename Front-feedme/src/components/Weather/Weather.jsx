import React, { useEffect, useState } from 'react';

const Weather = () => {
  const [lat, setLat] = useState(null);
  const [lon, setLon] = useState(null);
  const [weather, setWeather] = useState(null);
  const [weatherCategory, setWeatherCategory] = useState('notknow');

  useEffect(() => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        setLat(position.coords.latitude);
        setLon(position.coords.longitude);
      },
      (error) => {
        console.error("Error getting location:", error);
        setWeatherCategory('notknow');
      }
    );
  }, []);

  useEffect(() => {
    if (lat && lon) {
      const apiKey = process.env.REACT_APP_API_KEY;

      fetch(`https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${apiKey}`)
        .then(response => response.json())
        .then(data => {
          setWeather(data);
          categorizeWeather(data.weather[0].id);
        });
    }
  }, [lat, lon]);

  const categorizeWeather = (id) => {
    if (id >= 200 && id < 600) {
      setWeatherCategory('rainy');
    } else if (id >= 600 && id < 700) {
      setWeatherCategory('snowy');
    } else if (id === 800) {
      setWeatherCategory('sunny');
    } else if (id > 800) {
      setWeatherCategory('cloudy');
    } else {
      setWeatherCategory('notknow');
    }
  };

  return (
    <div>
      <p>Category: {weatherCategory}</p>
    </div>
  );
}

export default Weather;
