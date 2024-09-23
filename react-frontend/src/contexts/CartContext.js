import {createContext, useEffect, useState} from 'react'

export const CartContext = createContext()

export const CartProvider = ({ children }) => {
    const [cartCourseList, setCartCourseList] = useState(localStorage.getItem('cartCourseList') ? JSON.parse(localStorage.getItem('cartCourseList')) : [])

    const addOrUpdateCourseToCart = (course) => {
        if(cartCourseList.find((item)=>item.courseID===course.courseID)){
            setCartCourseList(cartCourseList.map((item)=>
                item.courseID===course.courseID ?
                    {...course} : item
            ))
        } else {
            setCartCourseList([...cartCourseList, {... course}])
        }
    };

    const deleteCourseFromCart = (courseID) => {
        courseID = parseInt(courseID)
        if(cartCourseList.find((item)=>item.courseID===courseID)){
            setCartCourseList(cartCourseList.filter((item)=>item.courseID!==courseID))
        }
    };

    const clearCart = () => {
        setCartCourseList([]);
    };

    const getCartTotal = () => {
        return cartCourseList.reduce((total, item) => total + item.price * item.quantity, 0).toFixed(2);
    };

    const findCourse = (courseID) => {
        return cartCourseList.find((item) => item.courseID === parseInt(courseID));
    }

    useEffect(() => {
        localStorage.setItem("cartCourseList", JSON.stringify(cartCourseList));
    }, [cartCourseList]);

    useEffect(() => {
        const cartCourseList = localStorage.getItem("cartCourseList");
        if (cartCourseList) {
            setCartCourseList(JSON.parse(cartCourseList));
        }
    }, []);

    return (
        <CartContext.Provider
            value={{
                cartCourseList,
                addOrUpdateCourseToCart,
                deleteCourseFromCart,
                clearCart,
                getCartTotal,
                findCourse
            }}
        >
            {children}
        </CartContext.Provider>
    );
};
