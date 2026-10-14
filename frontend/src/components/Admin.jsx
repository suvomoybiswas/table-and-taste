import { useEffect, useState } from "react";

function Admin() {

    const [menuItems, setMenuItems] = useState([]);

    useEffect(() => {

        fetch("/api/menu")
            .then(response => response.json())
            .then(data => {
                setMenuItems(data);
            });

    }, []);

    function handleDelete(itemId) {

        fetch(`/api/menu/${itemId}`, {
            method: "DELETE"
        })
            .then(response => {

                if (!response.ok) {
                    throw new Error("Failed to delete menu item");
                }

                setMenuItems(previousItems =>
                    previousItems.filter(
                        item => item.id !== itemId
                    )
                );

            })
            .catch(error => {
                console.error(error);
            });

    }

    return (
        <div>

            <h1>Table & Taste Admin</h1>

            <h2>Menu Management</h2>

            {menuItems.map(item => (

                <div key={item.id}>

                    <h3>{item.name}</h3>

                    <p>
                        Price: ${item.price}
                    </p>

                    <p>
                        Category: {item.category}
                    </p>

                    <button>
                        Edit
                    </button>

                    <button
                        onClick={() => handleDelete(item.id)}
                    >
                        Delete
                    </button>

                </div>

            ))}

        </div>
    );
}

export default Admin;