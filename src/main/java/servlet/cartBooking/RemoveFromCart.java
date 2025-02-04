package servlet.cartBooking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import bean.Service;

/**
 * Servlet implementation class RemoveFromCart
 */
@WebServlet("/public/RemoveFromCart")
public class RemoveFromCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFromCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		// Retrieve the service ID to delete
        String serviceId = request.getParameter("serviceId");

        // Parse the service ID
        int id;
        try {
            id = Integer.parseInt(serviceId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid service ID format");
            return;
        }

        // Retrieve the session
        HttpSession session = request.getSession(false); // Avoid creating a new session
        if (session == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Session not found");
            return;
        }

        // Retrieve the cart from the session
        @SuppressWarnings("unchecked")
		List<Service> cart = (List<Service>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cart is empty");
            return;
        }

        // Find and remove the service from the cart
        boolean removed = cart.removeIf(service -> service.getId() == id);

        if (!removed) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Service not found in cart");
            return;
        }

        // Redirect back to the cart page
        response.sendRedirect("cart.jsp");
    }
	

}
