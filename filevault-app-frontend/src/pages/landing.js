import React from "react";
import { Link, Navigate } from "react-router-dom";
import { useAuth } from './authContext'

function Landing() {
    const Auth = useAuth()
    const user = Auth.getUser()
    const isLoggedIn = Auth.userIsAuthenticated()

    if (!isLoggedIn) {
        return <Navigate to='/' />
    }

    const uploadButtonLabel = user.isAdmin ? "View All Files (ADMIN)" : "View Files";
    
    return (
        <div class="container">
            <h2>File Management Application</h2>
            <div class="btn">
                <Link className="btn btn-outline-light" to="/view" >{uploadButtonLabel}</Link>
                <Link className="btn btn-outline-light" to="/upload" state={{ id: null }}>Upload New Files</Link>
            </div>
        </div>
    )
}

export default Landing;