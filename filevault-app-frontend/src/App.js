import Home from './pages/home';
import Login from './pages/login';
import Register from './pages/register';
import Landing from './pages/landing';
import Upload from './pages/upload/upload';
import View from './pages/view/view';
import Navbar from './pages/navbar/navbar'
import { AuthProvider } from './pages/authContext'
import './App.css';
import { BrowserRouter as Router, Routes, Route} from 'react-router-dom';

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route exact path = "/" element={<Home/>}/>
          <Route exact path = "/login" element={<Login/>}/>
          <Route exact path = "/register" element={<Register/>}/>
          <Route exact path = "/landing" element={<>
                <Navbar />
                <Landing />
              </>}/>
          <Route exact path = "/upload" element={<>
                <Navbar />
                <Upload />
              </>}/>
          <Route exact path = "/view" element={<>
                <Navbar />
                <View />
              </>}/>
        </Routes>
      </Router>
    </AuthProvider>
  )
}
export default App;
