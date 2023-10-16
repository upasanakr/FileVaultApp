import React from "react";
import { Link } from "react-router-dom";

function Home() {
    
    return (
        <div class="container">
            <h2>File Management Application</h2>
            <div class="btn">
                <Link className="btn btn-outline-light" to="/login">Already a user? Login here</Link>
                <Link className="btn btn-outline-light" to="/register">New user? Register here</Link>
            </div>
        </div>
    )
}

export default Home;