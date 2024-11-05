import React from 'react';
import { Link } from 'react-router-dom';
import '../Main/Sidebar.css';

const Menu = ({ icon: Icon, des, link, isActive, onClick }) => {
    return (
        <div
            style={{
                backgroundColor: isActive ? 'rgba(255, 95, 95, 0.13)' : 'transparent',
                color: isActive ? '#FF5F5F' : '#49454F'
            }}
        >
            <div className='menu'>
                <Icon
                    className='menu-icon'
                    style={{ color: isActive ? '#FF5F5F' : '#49454F' }}
                />
                <Link
                    onClick={onClick}
                    style={{
                        fontFamily: 'PretendardM',
                        color: isActive ? '#FF5F5F' : '#49454F',
                    }}
                    to={des}
                >
                    {link}
                </Link>
            </div>
            <span
                id='SidebarLine'
                style={{ backgroundColor: isActive ? '#FF5F5F' : 'transparent' }}
            >|</span>
        </div>
    );
};

export default Menu;
