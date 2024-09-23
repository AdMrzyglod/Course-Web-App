import { createContext, useState, useEffect } from 'react'
import {jwtDecode} from 'jwt-decode';
import axios from "axios";
import {useNavigate} from "react-router-dom";

const AuthContext = createContext()



export const AuthProvider = ({children}) => {

    const navigate = useNavigate()

    const refreshData = () => {
        let localData = localStorage.getItem('tokenData');
        if(localData){
            localData = JSON.parse(localData);
            if(localData.token){
                axios.defaults.headers.common['Authorization'] = `Bearer ${localData.token}`;

                if (Date.now() > jwtDecode(localData.token).exp * 1000) {
                    localStorage.removeItem('tokenData');
                    axios.defaults.headers.common['Authorization'] = null;
                    return null;
                }
            }
        }
        return localData;
    }

    let [tokenData, setTokenData] = useState(refreshData())
    const [authTimeout, setAuthTimeout] = useState(null);

    useEffect(() => {
        setTokenData(refreshData());
    }, [])


    const userTokenCheck = () => {
        let storedToken = localStorage.getItem('tokenData')
        if (!storedToken) {
            return false
        }
        storedToken = jwtDecode(JSON.parse(storedToken).token)

        if (Date.now() > storedToken.exp * 1000) {
            logoutUser(null);
            return false
        }
        return true
    }

    const getUserData = () => {
        if(localStorage.getItem('tokenData')!=null){
            return jwtDecode(JSON.parse(localStorage.getItem('tokenData')).token);
        }
    }

    const loginUser = (data) => {
        localStorage.setItem('tokenData', JSON.stringify(data))
        setTokenData(data)
        if(data && data.token){
            axios.defaults.headers.common['Authorization'] = `Bearer ${data.token}`;
        }
    }

    const logoutUser = (e) => {
        if(e){
            e.preventDefault()
        }
        localStorage.removeItem('tokenData');
        setTokenData(null);
        axios.defaults.headers.common['Authorization'] = null;
    }


    const logoutTimeout = () => {
        let storedToken = localStorage.getItem('tokenData')
        if (!storedToken) {
            return
        }
        storedToken = jwtDecode(JSON.parse(storedToken).token)


        if (Date.now() > storedToken.exp * 1000) {
            logoutUser(null);
            return;
        }

        if(authTimeout){
            clearTimeout(authTimeout);
            setAuthTimeout(null);
        }

        const timeout = setTimeout(() => {
            logoutUser(null);
            navigate("/login");
        }, storedToken.exp*1000 - Date.now());

        setAuthTimeout(timeout);

        return () => clearTimeout(timeout);
    }


    useEffect(() => {

        logoutTimeout();
    }, [tokenData]);



    let contextData = {
        tokenData,
        userTokenCheck,
        loginUser,
        logoutUser,
        getUserData,
    }

    return(
        <AuthContext.Provider value={contextData}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext;
