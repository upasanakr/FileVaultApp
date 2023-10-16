import './view.css'
import { useAuth } from '../authContext'
import { useNavigate, Navigate } from "react-router-dom";
import React, { useState, useEffect } from "react";
import axios from "axios";

function View() {
    const Auth = useAuth()
    const user = Auth.getUser()
    const isLoggedIn = Auth.userIsAuthenticated()
    let navigate = useNavigate();
    const userId = (user != null) ? user.id : null;
    const [files, setFiles]=useState([])
    const [loadError, setLoadError] = useState(null);
    const [downloadError, setDownloadError] = useState(null);
    const [deleteError, setDeleteError] = useState(null);

    useEffect(() => {
        if (isLoggedIn) {
            loadFiles();
        }
    }, [isLoggedIn]);

    const loadFiles = async ()=>{
        try {
            let response;
            if (user.isAdmin == true) {
                response = await axios.get(`http://localhost:8080/api/file/all`)
            } 
            else {
                response = await axios.get(`http://localhost:8080/api/file/${userId}`)
            }
            setFiles(response.data)
        } catch (error) {
            console.error("File loading error:", error);
            setLoadError("File loading failed. Please try again."); // Set an error message
        }
    }

    const deleteFile = async (fileId) => {
        try {
            const response = await axios.delete(`http://localhost:8080/api/file/delete/${fileId}`)
            setFiles((prevFiles) => prevFiles.filter((file) => file.id !== fileId));
            setDeleteError(null);
        } catch (error) {
            console.error("File deletion failed:", error);
            setDeleteError("File deletion failed. Please try again.");
        }
    }

    const editFile = async (fileId) => {
        navigate("/upload", { state: { id: fileId }});
    }

    const downloadFile = async (fileId, filename) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/file/download/${fileId}`);
            const fileUrl = response.data;
            window.open(fileUrl, "_blank");
            setDownloadError(null);
        } catch (error) {
            console.error("File download failed:", error);
            setDownloadError("File download failed. Please try again.");
        }
    }

    if (!isLoggedIn) {
        return <Navigate to='/' />
    }

    return (
        <div class="view-container">
            {loadError && (
                <div id="errorMessage" style={{ color: 'red', textAlign: 'center', marginBottom: '10px' }}>
                    {loadError}
                </div>
            )}
        <h1>File Management System</h1>
        <table>
            <thead>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>File Name</th>
                    <th>Description</th>
                    <th>Upload Time</th>
                    <th>Update Time</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                {files.map((file, index) => (
                    <tr key={index}>
                        <td>{file.user.firstName}</td>
                        <td>{file.user.lastName}</td>
                        <td>{file.filename}</td>
                        <td>{file.description}</td>
                        <td>{file.lastModifiedTime}</td>
                        <td>{file.lastUpdatedTime}</td>
                        <td>
                            <div className="action-buttons">
                                <button onClick={() => downloadFile(file.id, file.filename)} className="action-button">Download</button>
                                <button onClick={() => editFile(file.id)} className="action-button">Edit</button>
                                <button onClick={() => deleteFile(file.id)} className="action-button">Delete</button>
                                {downloadError && (
                                    <span style={{ color: 'red', marginRight: '10px' }}>{downloadError}</span>
                                )}
                                {deleteError && (
                                    <span style={{ color: 'red' }}>{deleteError}</span>
                                )}
                            </div>
                        </td>
                    </tr>
                    ))
                }
            </tbody>
        </table>
    </div>
    )
}

export default View;