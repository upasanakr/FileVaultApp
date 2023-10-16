import React, {useState} from "react";
import { Link, useNavigate, Navigate } from "react-router-dom";
import { useAuth } from './authContext'
import axios from "axios";

function Login() {
    const Auth = useAuth()
    const isLoggedIn = Auth.userIsAuthenticated()
    
    let navigate = useNavigate();

    const [user, setUser] = useState({
        email:"",
        password:""
    }) 

    const [error, setError] = useState("");
    
    const { email, password}=user

    const onInputChange=(e)=> {
        setUser({...user, [e.target.name]:e.target.value})
    }

    const onSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post("http://localhost:8080/api/login", user);
            const authenticatedUser = response.data;
            Auth.userLogin(authenticatedUser)
            navigate("/landing", { state: { authenticatedUser } });
        } catch (error) {
            if (error.response && error.response.status === 400) {
                console.log("ADITYA");
                setError("Invalid username or password. Please try again.");
            } else {
                setError("An error occurred. Please try again later.");
            }
        }
    }

    if (isLoggedIn) {
        return <Navigate to='/landing' />
    }

    return (
        <div class="login-container">
            <h2>Login</h2>
            <form onSubmit={(e) => onSubmit(e)}>
                <label for="email">Email:</label>
                <input type="text" id="email" name="email" value={email} onChange={(e)=>onInputChange(e)} required/>
                <span class="error-message" id="email-error"></span>
    
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" value={password} onChange={(e)=>onInputChange(e)}required/>
                <span class="error-message" id="password-error"></span>
                {error && <div className="error-message">{error}</div>} {/* Display error message */}
                <input type="submit" value="Login" />
            </form>
            <div class="register-link">
                <p>Not registered yet? <Link className="register" to="/register">Register here</Link></p>
            </div>
        </div>  
    )
}

export default Login;