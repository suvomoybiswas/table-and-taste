import "./CartItem.css";

function CartItem(props) {

    const subtotal =
        props.item.price * props.item.quantity;

    return (
        <div className="cart-item">

            <h3>{props.item.name}</h3>

            <p className="item-price">
                ${props.item.price} each
            </p>

            <div className="quantity-section">

                <span>Quantity:</span>

                <div className="quantity-controls">

                    <button
                        onClick={() =>
                            props.onDecreaseQuantity(props.item.id)
                        }
                    >
                        -
                    </button>

                    <span>{props.item.quantity}</span>

                    <button
                        onClick={() =>
                            props.onIncreaseQuantity(props.item.id)
                        }
                    >
                        +
                    </button>

                </div>

            </div>

            <p className="item-subtotal">
                Subtotal: ${subtotal.toFixed(2)}
            </p>

            <button
                className="remove-button"
                onClick={() =>
                    props.onRemoveFromCart(props.item.id)
                }
            >
                Remove
            </button>

        </div>
    );
}

export default CartItem;