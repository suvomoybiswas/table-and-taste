import { useState, useEffect } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import pizza from "./assets/margarita.jpg";
import burger from "./assets/burger.jpg";
import pasta from "./assets/pasta.jpg";
import PaymentPage from "./PaymentPage";
import Header from "./components/Header";
import MenuCard from "./components/MenuCard";
import Cart from "./components/Cart";
import Checkout from "./components/Checkout";
import Admin from "./components/Admin";

import "./App.css";

const categoryImages = {
    Pizza: pizza,
    Burger: burger,
    Pasta: pasta
};

function App() {

    const [cart, setCart] = useState([]);

    const [menuItems, setMenuItems] = useState([]);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");

    const [selectedCategory, setSelectedCategory] = useState("All");

    const categories = ["All", "Pizza", "Burger", "Pasta"];


    useEffect(() => {

        fetch("/api/menu")
            .then(response => {

                if (!response.ok) {
                    throw new Error("Failed to load menu");
                }

                return response.json();
            })
            .then(data => {

                setMenuItems(data);
                setLoading(false);

            })
            .catch(error => {

                console.error(error);

                setError(
                    "Unable to load the menu. Please try again."
                );

                setLoading(false);

            });

    }, []);


    function handleAddToCart(item) {

        setCart(previousCart => {

            const existingItem = previousCart.find(
                cartItem => cartItem.id === item.id
            );

            if (existingItem) {

                return previousCart.map(cartItem => {

                    if (cartItem.id === item.id) {

                        return {
                            ...cartItem,
                            quantity: cartItem.quantity + 1
                        };

                    }

                    return cartItem;

                });

            }

            return [
                ...previousCart,
                {
                    ...item,
                    quantity: 1
                }
            ];

        });

    }


    function handleRemoveFromCart(itemId) {

        setCart(previousCart =>
            previousCart.filter(
                item => item.id !== itemId
            )
        );

    }


    function handleIncreaseQuantity(itemId) {

        setCart(previousCart =>
            previousCart.map(cartItem => {

                if (cartItem.id === itemId) {

                    return {
                        ...cartItem,
                        quantity: cartItem.quantity + 1
                    };

                }

                return cartItem;

            })
        );

    }


    function handleDecreaseQuantity(itemId) {

        setCart(previousCart =>
            previousCart.map(cartItem => {

                if (cartItem.id === itemId) {

                    return {
                        ...cartItem,
                        quantity: Math.max(
                            1,
                            cartItem.quantity - 1
                        )
                    };

                }

                return cartItem;

            })
        );

    }


    const filteredMenuItems =
        selectedCategory === "All"
            ? menuItems
            : menuItems.filter(
                item =>
                    item.category === selectedCategory
            );


    const cartTotal = cart.reduce(
        (total, item) =>
            total + item.price * item.quantity,
        0
    );


    return (

        <BrowserRouter>

            <Routes>

                {/* HOME / MENU PAGE */}

                <Route
                    path="/"
                    element={

                        <div className="main-content">

                            <Header
                                name="Table & Taste"
                                tagline="Fresh food. Great taste."
                            />

                            <div className="menu-section">

                                <div>

                                    <h2>Our Menu</h2>

                                    <div className="category-buttons">

                                        {categories.map(category => (

                                            <button
                                                key={category}
                                                onClick={() =>
                                                    setSelectedCategory(category)
                                                }
                                            >
                                                {category}
                                            </button>

                                        ))}

                                    </div>


                                    <div className="menu-container">

                                        {loading && (
                                            <p>
                                                Loading menu...
                                            </p>
                                        )}


                                        {error && (
                                            <p>
                                                {error}
                                            </p>
                                        )}


                                        {!loading &&
                                            !error &&
                                            filteredMenuItems.map(item => (

                                                <MenuCard
                                                    key={item.id}
                                                    item={item}
                                                    image={
                                                        categoryImages[
                                                            item.category
                                                            ]
                                                    }
                                                    onAddToCart={
                                                        handleAddToCart
                                                    }
                                                />

                                            ))
                                        }

                                    </div>

                                </div>


                                <Cart
                                    cart={cart}
                                    cartTotal={cartTotal}
                                    onRemoveFromCart={
                                        handleRemoveFromCart
                                    }
                                    onIncreaseQuantity={
                                        handleIncreaseQuantity
                                    }
                                    onDecreaseQuantity={
                                        handleDecreaseQuantity
                                    }
                                />

                            </div>

                        </div>

                    }
                />


                {/* CHECKOUT PAGE */}

                <Route
                    path="/checkout"
                    element={

                        <div className="main-content">

                            <Checkout
                                cart={cart}
                            />

                        </div>

                    }
                />
                <Route
                    path="/payment"
                    element={<PaymentPage />}
                />
                <Route
                    path="/admin"
                    element={
                        <div className="main-content">
                            <Admin />
                        </div>
                    }
                />

            </Routes>

        </BrowserRouter>

    );
}

export default App;