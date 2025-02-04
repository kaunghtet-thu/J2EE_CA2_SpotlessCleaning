<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stripe Checkout</title>
    
    <script async
	  src="https://pay.google.com/gp/p/js/pay.js"
	  onload="onGooglePayLoaded()">
    
    </script>
    <!-- Stripe.js -->
    <script src="https://js.stripe.com/v3/"></script>

    <!-- Button Styling -->
    <style>
        #checkout-button {
            background-color: #6772e5;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        #checkout-button:hover {
            background-color: #5469d4;
        }
    </style>
</head>

<body>
<%
    //HttpSession session = request.getSession();
    //String totalStr = (String) session.getAttribute("total");
    String totalStrr = request.getParameter("total");
   totalStrr = "9999";

%>
    <p>Total Amount: <%= totalStrr != null ? totalStrr : "N/A" %></p>
<button id="checkout-button">Pay with STRIPE</button>
<div id="container"></div>
<script>
  function onGooglePayLoaded() {
    const paymentsClient = new google.payments.api.PaymentsClient({ environment: 'TEST' });
    const isReadyToPayRequest = {
      apiVersion: 2,
      apiVersionMinor: 0,
      allowedPaymentMethods: [{
        type: 'CARD',
        parameters: {
          allowedAuthMethods: ['PAN_ONLY', 'CRYPTOGRAM_3DS'],
          allowedCardNetworks: ['MASTERCARD', 'VISA']
        }
      }]
    };

    paymentsClient.isReadyToPay(isReadyToPayRequest)
      .then(function(response) {
        if (response.result) {
          const button = paymentsClient.createButton({ onClick: onGooglePayButtonClicked });
          document.getElementById('container').appendChild(button);
        }
      })
      .catch(function(err) {
        console.error('Error checking readiness to pay:', err);
      });
  }

  function onGooglePayButtonClicked() {
    const paymentDataRequest = {
      apiVersion: 2,
      apiVersionMinor: 0,
      allowedPaymentMethods: [{
        type: 'CARD',
        parameters: {
          allowedAuthMethods: ['PAN_ONLY', 'CRYPTOGRAM_3DS'],
          allowedCardNetworks: ['MASTERCARD', 'VISA']
        },
        tokenizationSpecification: {
          type: 'PAYMENT_GATEWAY',
          parameters: {
            gateway: 'example',
            gatewayMerchantId: 'your-gateway-merchant-id'
          }
        }
      }],
      transactionInfo: {
        totalPriceStatus: 'FINAL',
        totalPrice: '10.00',
        currencyCode: 'USD'
      },
      merchantInfo: {
        merchantId: 'your-merchant-id',
        merchantName: 'Example Merchant'
      }
    };

    const paymentsClient = new google.payments.api.PaymentsClient({ environment: 'TEST' });
    paymentsClient.loadPaymentData(paymentDataRequest)
      .then(function(paymentData) {
        processPayment(paymentData);
      })
      .catch(function(err) {
        console.error('Error loading payment data:', err);
      });
  }

  function processPayment(paymentData) {
    console.log('Payment processed:', paymentData);
    // Send payment token to the backend for further processing
  }

const stripe = Stripe('pk_test_51QlQe5PO5PNshsgdtUMyjNn6KbZHlgMD4vlkvIHDvXNPwF5rFBASJtnoEWmwRNSCzwL8NsjCllTmNrrU1n5yzidf00PT3TeXJf'); // Replace with your Stripe publishable key

const baseUrl = window.location.origin; // Get the base URL
const endpoint = `${baseUrl}<%= request.getContextPath() %>/StripeAPI`; // No total in URL

const total = '<%= totalStrr %>'; // Get total from JSP
console.log(endpoint);
console.log(total)

document.getElementById('checkout-button').addEventListener('click', () => {
    fetch(endpoint, {
        method: 'POST',
        headers: {
        	'Content-Type': 'application/json',
            'Total-Amount': total // Send total as a custom header
        }
    })
    .then(response => response.json())
    .then(data => {
        return stripe.redirectToCheckout({ sessionId: data.id });
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred during checkout. Please try again.');
    });
});

</script>

</body>
</html>