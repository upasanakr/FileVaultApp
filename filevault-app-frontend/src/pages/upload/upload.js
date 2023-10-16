import { useLocation, useNavigate, Navigate } from "react-router-dom";
import { useAuth } from '../authContext'
import React, { useState } from "react";
import './upload.css'
import axios from "axios";

function Upload() {
    const location = useLocation();
    let navigate = useNavigate();
    const Auth = useAuth()
    const user = Auth.getUser()
    const isLoggedIn = Auth.userIsAuthenticated()

    const [file, setFile] = useState(null);
    const [fileDescription, setFileDescription] = useState("");
    const [uploadSuccess, setUploadSuccess] = useState(false);
    const [uploadError, setUploadError] = useState(null);


    if (!isLoggedIn) {
        return <Navigate to='/' />
    }

    const userId = user.id
    const fileId = location.state.id;

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    const handleDescriptionChange = (e) => {
        setFileDescription(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append("file", file);
        formData.append("description", fileDescription);
        try {
            if (fileId)
                await axios.post(`http://localhost:8080/api/file/update/${fileId}`, formData);
            else
                await axios.post(`http://localhost:8080/api/file/upload/${userId}`, formData);
            e.target.reset();
            setFile(null);
            setFileDescription("");
            setUploadSuccess(true);
            navigate("/view")
        } catch (error) {
            console.error("File upload error:", error);
            setUploadError("File upload failed. Please try again.");
        }
    };
    
    return (
        <div class="upload-container">
            <form id="uploadForm" onSubmit={handleSubmit}>
                <label for="fileToUpload">Select a File:</label>
                <input type="file" name="fileToUpload" id="fileToUpload" onChange={handleFileChange} required/>
                <label for="fileDescription">File Description:</label>
                <input type="text" name="fileDescription" id="fileDescription" placeholder="File Description" onChange={handleDescriptionChange} required/>
                <input type="submit" value="Upload File"/>
                {uploadError && (
                    <div id="errorMessage" style={{ color: 'red', textAlign: 'center', marginBottom: '10px' }}>
                        {uploadError}
                    </div>
                )}
                {uploadSuccess && (
                    <div id="successMessage" style={{display: 'none', textAlign: 'center', marginTop: '20px'}}>
                        File Upload Successful!
                    </div>
                )}
            </form>
        </div>
    )
}

export default Upload;