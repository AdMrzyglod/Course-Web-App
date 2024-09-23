import { Navigate } from 'react-router-dom'
import { useContext } from 'react'
import AuthContext from "../contexts/AuthContext";


const PrivateRoute = ({component, path}) => {
    let { getUserData } = useContext(AuthContext)

    const userPaths = ["/course-user-panel", "/course-add", "/course-user-panel/course-details/:id"]
    const employeePaths = ["/course-employee-panel","/course-employee-panel/course-details/:id"]
    const adminPaths = ["/course-admin-panel"]

    const routeComponent = () => {
        console.log()
        if(!getUserData()){
            return <Navigate to='/login'/>;
        }
        else{
            if(getUserData().role === "USER" && userPaths.includes(path)){
                return component;
            }
            else if(getUserData().role === "EMPLOYEE" && employeePaths.includes(path)){
                return component;
            }
            else if(getUserData().role === "ADMIN" && (employeePaths.includes(path) || adminPaths.includes(path))){
                return component;
            }
            else if(getUserData().role === "SUPER_ADMIN" && (employeePaths.includes(path) || adminPaths.includes(path))){
                return component;
            }
        }
    }

    return routeComponent();
}

export default PrivateRoute;