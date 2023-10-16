import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from '../authContext';
import './navbar.css'


function Navbar() {
    const Auth = useAuth();
    const navigate = useNavigate();

    const handleSignOut = () => {
        Auth.userLogout();
        navigate("/login");
    };

    const navigateToLandingPage = () => {
      navigate("/landing");
    };

    return (
        <nav className="navbar">
        <div className="navbar-left">
          <button className="blue-button" onClick={() => navigateToLandingPage()}>Home</button>
        </div>
        <div className="navbar-right">
          <button className="blue-button" onClick={() => handleSignOut()}>SignOut</button>
        </div>
      </nav>
    );
}

export default Navbar;
