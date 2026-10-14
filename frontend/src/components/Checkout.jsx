import "./Checkout.css";
import { useState } from "react";
import {useNavigate} from "react-router-dom";

function Checkout(props) {
    const navigate = useNavigate();
    const [fullName, setFullName] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [address, setAddress] = useState("");

    function handleSubmit(event) {

        event.preventDefault();

        const order = {

            customer: {
                fullName: fullName,
                email: email,
                phone: phone,
                address: address
            },

            items: props.cart.map(item => ({
                menuItemId: item.id,
                quantity: item.quantity
            }))

        };

        console.log("Order:", order);
        navigate("/payment");

    }

    return (
        <div className="checkout">

            <h2>Checkout</h2>
            <div className="order-summary">

                <h3>Your Order</h3>

                {props.cart.map(item => (
                    <div key={item.id} className="order-summary-item">

            <span>
                {item.name} × {item.quantity}
            </span>

                        <span>
                ${(item.price * item.quantity).toFixed(2)}
            </span>

                    </div>
                ))}

            </div>
            <form onSubmit={handleSubmit}>

                <label>
                    Full Name
                </label>

                <input
                    type="text"
                    placeholder="Enter your full name"
                    value={fullName}
                    onChange={(event) =>
                        setFullName(event.target.value)
                    }
                />

                <label>
                    Email
                </label>

                <input
                    type="email"
                    placeholder="Enter your email"
                    value={email}
                    onChange={(event) =>
                        setEmail(event.target.value)
                    }
                />

                <label>
                    Phone
                </label>

                <input
                    type="tel"
                    placeholder="Enter your phone number"
                    value={phone}
                    onChange={(event) =>
                        setPhone(event.target.value)
                    }
                />

                <label>
                    Delivery Address
                </label>

                <textarea
                    placeholder="Enter your delivery address"
                    value={address}
                    onChange={(event) =>
                        setAddress(event.target.value)
                    }
                />

                <button type="submit">
                    Place Order
                </button>

            </form>

        </div>
    );
}

export default Checkout;