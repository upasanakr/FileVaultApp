import React, {useState} from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from './authContext'
import axios from "axios"

function Register() {
        const Auth = useAuth()
        let navigate = useNavigate();
        const [user, setUser] = useState({
            firstName:"",
            lastName:"",
            email:"",
            password:""
        }) 

        const [error, setError] = useState("");
        
        const {firstName, lastName, email, password}=user 

        const onInputChange=(e)=> {
            setUser({...user, [e.target.name]:e.target.value})
        }

        const onSubmit = async (e) => {
            e.preventDefault();
            try {
                const response = await axios.post("http://localhost:8080/api/register", user);
                const authenticatedUser = response.data;
                Auth.userLogin(authenticatedUser)
                navigate("/landing");
            } catch (error) {
                if (error.response && error.response.status === 400) {
                    setError("Registration failed. Username already exists");
                } else {
                    setError("An error occurred. Please try again later.");
                }
            }
        }

        return (
            <div className="container">
                <h2>User Registration</h2>
                <form onSubmit={(e) => onSubmit(e)}> 
                    <label for="firstName">First Name:</label>
                    <input type="text" id="firstName" name="firstName" value={firstName} onChange={(e)=>onInputChange(e)} required/>
                    <span className="error-message" id="firstname-error"></span>

                    <label for="lastName">Last Name:</label>
                    <input type="text" id="lastName" name="lastName" value={lastName} onChange={(e)=>onInputChange(e)} required/>
                    <span className="error-message" id="lastname-error"></span>

                    <label for="email">Email:</label>
                    <input type="text" id="email" name="email" value={email} onChange={(e)=>onInputChange(e)} required/>
                    <span className="error-message" id="email-error"></span>

                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" value={password} onChange={(e)=>onInputChange(e)} required/>
                    <span className="error-message" id="password-error"></span>

                    {error && <div className="error-message">{error}</div>}

                    <input type="submit" value="Register"/>
                </form>
                <div className="login-link">
                    <p>Already registered? <Link className="login" to="/login">Login here</Link></p>
                </div>
            </div>  
        )
}

export default Register;