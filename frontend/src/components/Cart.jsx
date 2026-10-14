import CartItem from "./CartItem";
import "./Cart.css";
import { Link } from "react-router-dom";

function Cart(props) {

    const totalQuantity = props.cart.reduce(
        (total, item) => total + item.quantity,
        0
    );

    const totalPrice = props.cart.reduce(
        (total, item) =>
            total + item.price * item.quantity,
        0
    );

    return (
        <div className="cart">

            <h2>Your Cart</h2>

            {props.cart.length === 0 ? (

                <p className="cart-empty">
                    Your cart is empty. Add something delicious!
                </p>

            ) : (

                <>

                    {props.cart.map(item => (
                        <CartItem
                            key={item.id}
                            item={item}
                            onRemoveFromCart={props.onRemoveFromCart}
                            onIncreaseQuantity={props.onIncreaseQuantity}
                            onDecreaseQuantity={props.onDecreaseQuantity}
                        />
                    ))}

                    <hr />

                    <p>
                        Items: {totalQuantity}
                    </p>

                    <p className="cart-total">
                        Total: ${totalPrice.toFixed(2)}
                    </p>
                    {props.cart.length > 0 && (
                        <Link
                            to="/checkout"
                            className="checkout-button"
                        >
                            Proceed to Checkout
                        </Link>
                    )}

                </>

            )}

        </div>
    );
}

export default Cart;