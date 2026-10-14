import { loadStripe } from "@stripe/stripe-js";
import {Elements, useElements, useStripe} from "@stripe/react-stripe-js";
import { PaymentElement } from "@stripe/react-stripe-js";
import { useEffect, useState } from "react";
const stripePromise = loadStripe("pk_test_51UO8DEFV23lnVanvNFlN5QAmL3U0wu73kEJcUpuGuIx6GUQAkC3vHuPZGlQkf5eUPsIdNXSXAlhTzFIYLFB92wyl00r7mp2Mad");

function CheckoutForm() {
    const stripe = useStripe();
    const elements = useElements();
    const [errorMessage, setErrorMessage] = useState("");

    const handleSubmit = async (event) => {
        event.preventDefault();
        setErrorMessage("");
        if (!stripe || !elements) return;
        const { error: submitError } = await elements.submit();
        if (submitError) return setErrorMessage(submitError.message);
        const { error } = await stripe.confirmPayment({
            elements,
            confirmParams: {
                return_url: window.location.href
            },
            redirect: "if_required"
        });
        if (error) setErrorMessage(error.message);
     };

    return (
        <>
        <p>CheckoutForm is rendering</p>
        <form onSubmit={handleSubmit}>
            <PaymentElement />
            {errorMessage && <p>{errorMessage}</p>}
            <button type="submit" disabled={!stripe || !elements}>Pay</button>
        </form>
      </>
    );
}

function PaymentPage() {

    const [clientSecret, setClientSecret] = useState(null);
    console.log("PaymentPage is rendering");

    useEffect(() => {
        fetch("/api/payments/create", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                orderId: 1
            }),
        }).then(response => {
            return response.json();
        }).then(data => {
            setClientSecret(data.clientSecret);
        });
    }, []);
    if (!clientSecret) return <p>Loading payment...</p>;

    return (
        <Elements stripe={stripePromise} options={{ clientSecret }}>
            <h2>Complete Payment</h2>
            <CheckoutForm />
        </Elements>
    );

} export default PaymentPage;