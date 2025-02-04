package servlet.paymentCheckout;
import java.io.IOException;
import java.util.HashMap;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/StripeAPI")
public class StripeAPI extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set your secret API key (replace with your secret key from Stripe Dashboard)
        Stripe.apiKey = "sk_test_51QlQe5PO5PNshsgdS4c8Gf4CPDZW3MW93QT1HXrL5lfAmZ36lseBr9VWIElBnj8d19Ul8zGCftAl0b3vgPMhuVAa00b4vWmk8U";
        
     // Retrieve the "total" value from the request parameter
//        String totalParam = request.getParameter("total");
        String totalParam = request.getHeader("Total-Amount"); // Read total from request headers
        System.out.println("Total received api: " + totalParam);

        // Parse the "total" parameter into a long
        long total;
        try {
            total = Long.parseLong(totalParam);
        } catch (NumberFormatException e) {
            // Handle invalid number format
            
            return;
        }

        // Process the total value as needed
        System.out.println("Total value: " + total);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String path = baseUrl + request.getContextPath() + "/Checkout";

        // Build checkout session parameters
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(path) // Replace with your success URL
                .setCancelUrl("http://localhost:8080/cancel")   // Replace with your cancel URL
                .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("sgd")
                                .setUnitAmount(total) // Amount in cents ($10.00)
                                .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Product Name")
                                        .build()
                                )
                                .build()
                        )
                        .build()
                )
                .build();

        try {
            // Create session
            Session session = Session.create(params);

            // Send session ID to the frontend
            HashMap<String, String> responseData = new HashMap<>();
            responseData.put("id", session.getId());

            response.setContentType("application/json");
            response.getWriter().write(new com.google.gson.Gson().toJson(responseData));
        } catch (StripeException e) {
            response.setStatus(500);
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { doPost(request, response); }
}