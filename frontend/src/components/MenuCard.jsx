import "./MenuCard.css";

function MenuCard(props) {

    return (
        <div className="menu-card">

            <img
                src={props.image}
                alt={props.item.name}
            />

            <h3>{props.item.name}</h3>

            <p className="menu-price">
                ${props.item.price}
            </p>

            <p>{props.item.description}</p>

            <p className="menu-category">
                {props.item.category}
            </p>

            <button
                onClick={() =>
                    props.onAddToCart(props.item)
                }
            >
                Add to Cart
            </button>

        </div>
    );
}

export default MenuCard;