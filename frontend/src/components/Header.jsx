import "./Header.css";

function Header(props) {

    return (
        <header className="site-header">

            <h1>{props.name}</h1>

            <p>{props.tagline}</p>

        </header>
    );
}

export default Header;