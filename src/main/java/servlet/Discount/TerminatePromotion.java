package servlet.Discount;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import DAO.DiscountDAO;

/**
 * Servlet implementation class TerminatePromotion
 */
@WebServlet("/TerminatePromotion")
public class TerminatePromotion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TerminatePromotion() {
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
	        // Get the serviceId from the form
	        String serviceIdParam = request.getParameter("serviceId");
	        boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));
	       
	        
	        // Check if serviceId is valid
	        if (serviceIdParam != null && !serviceIdParam.isEmpty()) {
	            try {
	                int serviceId = Integer.parseInt(serviceIdParam);
	                
	                // Call the method to update the discount status in the database
	                DiscountDAO discountDao = new DiscountDAO(); // Assuming you have a DiscountDao class for DB operations
	                discountDao.updateDiscountStatus(serviceId, isAdmin); 

	                // Redirect to a page (could be the promoteService.jsp or any other page)
	                response.sendRedirect("./admin/serviceDashboard.jsp?serviceId=" + serviceId + "&successMsg=Promotion Terminated Successfully");
	                
	            } catch (NumberFormatException e) {
	                // If serviceId is invalid, you can log the error or handle it gracefully
	                response.sendRedirect("./admin/serviceDashboard.jsp?errorMsg=Invalid Service ID");
	            }
	        } else {
	            // If no serviceId is provided, redirect with an error message
	            response.sendRedirect("./admin/serviceDashboard.jsp?errorMsg=Service ID Missing");
	        }
	    }

}
