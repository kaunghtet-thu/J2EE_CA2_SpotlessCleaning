<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script async
  src="https://pay.google.com/gp/p/js/pay.js"
  onload="onGooglePayLoaded()"></script>
<body>


  <h1>Google Pay Integration</h1>
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
</script>


</body>
</html>