package servlet.Discount;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import DAO.DiscountDAO;

/**
 * Servlet implementation class AddDiscount
 */
@WebServlet("/AddDiscount")
public class AddDiscount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddDiscount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String serviceId = request.getParameter("serviceId");

	    if (serviceId == null || serviceId.isEmpty()) {
	        serviceId = request.getParameter("serviceName");
	    }

	    int serviceIdInt;
	    try {
	        serviceIdInt = Integer.parseInt(serviceId);
	    } catch (NumberFormatException e) {
	        response.sendRedirect("./admin/promoteService.jsp?errorMsg=Invalid service ID");
	        return;
	    }

	    String discountName = request.getParameter("discountName");
	    double discountPercentage;
	    try {
	        discountPercentage = Double.parseDouble(request.getParameter("discount"));
	    } catch (NumberFormatException e) {
	        response.sendRedirect("./admin/promoteService.jsp?errorMsg=Invalid discount percentage");
	        return;
	    }

	    DiscountDAO discountDAO = new DiscountDAO();
	    boolean isInserted = discountDAO.createDiscount(serviceIdInt, discountName, discountPercentage);

	    if (isInserted) {
	        response.sendRedirect("./admin/promoteService.jsp?successMsg=Discount added successfully");
	    } else {
	        response.sendRedirect("./admin/promoteService.jsp?errorMsg=Failed to add discount");
	    }
	}


}
