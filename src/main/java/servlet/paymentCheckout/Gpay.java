package servlet.paymentCheckout;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/processPayment")
public class PaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentToken = request.getParameter("paymentToken");

        // Process the token (e.g., send it to your payment gateway)
        System.out.println("Payment Token: " + paymentToken);

        // Return a response
        response.setContentType("application/json");
        response.getWriter().write("{\"status\":\"success\"}");
    }
}
