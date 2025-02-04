package servlet.cartBooking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import DAO.ServiceDAO;
import bean.Service;

/**
 * Servlet implementation class AddToCart
 */
@WebServlet("/AddToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCart() {
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
		// Get the current session
		HttpSession session = request.getSession();
		
		String serviceId = request.getParameter("serviceId");
		int id = Integer.parseInt(serviceId);

	
	    ServiceDAO dao = new ServiceDAO();
	    Service service = dao.getServiceById(id); // Implement this method to get the service


        // Retrieve the cart from the session, or create a new one if it doesn't exist
        @SuppressWarnings("unchecked")
		List<Service> cart = (List<Service>) session.getAttribute("cart");
        if(cart == null) {
        	String error = "cart null";
            response.sendRedirect("./customer/services.jsp?errorMsg=" + error);
            return;
        }
        
        	boolean alreadyInCart = false;
        	
        	alreadyInCart = cart.stream().anyMatch(item -> item.getId() == id);
        	
            if (alreadyInCart) {
            	String error = "Already in the cart!";
                response.sendRedirect("./customer/services.jsp?errorMsg=" + error);
                return;
            }

        // Add the service to the cart
        cart.add(service);
        response.sendRedirect("./customer/cart.jsp");
	}
}
