import './App.css';
import {Route, Routes} from "react-router-dom";
import Navbar from "./components/navbar/navbar";
import Home from "./components/home/home";
import CourseList from "./components/course-list/course-list";
import CourseDetails from "./components/course-details/course-details";
import CourseAdd from "./components/course-add/course-add";
import CourseSignup from "./components/course-signup/course-signup";
import CourseLogin from "./components/course-login/course-login";
import {AuthProvider} from "./contexts/AuthContext";
import {CartProvider} from "./contexts/CartContext";
import CourseCart from "./components/course-cart/course-cart";
import CourseUserPanel from "./components/course-user-panel/course-user-panel";
import PrivateRoute from "./routers/PrivateRoute";
import CourseAdminPanel from "./components/course-admin-panel/course-admin-panel";
import CourseEmployeePanel from "./components/course-employee-panel/course-employee-panel";

function App() {
  return (
      <div>
          <AuthProvider>
              <CartProvider>
                <Navbar/>
                <Routes>
                    <Route path='/' element={<Home/>} />
                    <Route path='/course-list' element={<CourseList/>} />
                    <Route path='/course-list/course-details/:id' element={<CourseDetails/>} />

                    <Route path='/course-user-panel/course-details/:id'
                           element={<PrivateRoute component={<CourseDetails/>} path={"/course-user-panel/course-details/:id"}/>} />
                    <Route path='/course-add'
                           element={<PrivateRoute component={<CourseAdd/>} path={"/course-add"}/>} />
                    <Route path='/course-user-panel'
                           element={<PrivateRoute component={<CourseUserPanel/>} path={"/course-user-panel"}/>}/>

                    <Route path='/course-employee-panel'
                           element={<PrivateRoute component={<CourseEmployeePanel/>} path={"/course-employee-panel"}/>}/>
                    <Route path='/course-employee-panel/course-details/:id'
                           element={<PrivateRoute component={<CourseDetails/>} path={"/course-employee-panel/course-details/:id"}/>} />

                    <Route path='/course-admin-panel'
                           element={<PrivateRoute component={<CourseAdminPanel/>} path={"/course-admin-panel"}/>}/>

                    <Route path='/course-cart' element={<CourseCart/>} />

                    <Route path='/signup' element={<CourseSignup/>} />
                    <Route path='/login' element={<CourseLogin/>} />
                </Routes>
              </CartProvider>
          </AuthProvider>
      </div>
  );
}

export default App;
