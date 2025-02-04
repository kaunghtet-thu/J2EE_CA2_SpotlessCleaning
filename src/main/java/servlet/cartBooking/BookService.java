package servlet.cartBooking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import DAO.BookingDAO;
import bean.BookingItems;
import bean.Member;
import bean.Service;

/**
 * Servlet implementation class BookService
 */
@WebServlet("/public/BookService")
public class BookService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookService() {
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
		
		try {
			HttpSession session = request.getSession();
			
			Member member = (Member) session.getAttribute("member");
			String serviceIdStr = (String) session.getAttribute("serviceId");
			String serviceName = (String) session.getAttribute("serviceName");
			int serviceId = Integer.parseInt(serviceIdStr);
			LocalDate bookingDate = LocalDate.parse(request.getParameter("serviceDate"));
	        LocalTime bookingTime = LocalTime.parse(request.getParameter("serviceTime"));
	        int cleaningHour = Integer.parseInt(request.getParameter("cleaningHour"));
	        int addressId = Integer.parseInt(request.getParameter("addressId"));
	        String servicePrice = (String)session.getAttribute("servicePrice");
	        
	        
	        String action = request.getParameter("action");

	        if ("update".equals(action)) {
	        	
	        	String redirectURL = "bookAService.jsp"
	        	        + "?serviceDate=" + request.getParameter("serviceDate")
	        	        + "&serviceTime=" + request.getParameter("serviceTime")
	        	        + "&cleaningHour=" + cleaningHour
	        	        + "&addressId=" + addressId
	        	        + "&serviceName=" + serviceName
	        	        + "&servicePrice=" + servicePrice;

	        	    response.sendRedirect(redirectURL);
	        	    return;
	        	
	        } else {
	        	BookingDAO dao = new BookingDAO();
	        	boolean success = dao.addBooking(new BookingItems(member.getId(), serviceId, addressId, 1, null, bookingDate, bookingTime, cleaningHour, LocalDateTime.now()));
	        	if(success) {
	        		List<Service> cart = (List<Service>) session.getAttribute("cart");
	        		
	        		if (cart != null) {
	        			int serviceIdToRemove = serviceId; // Replace with actual ID or logic to get it
	        			cart.removeIf(service -> service.getId() == serviceIdToRemove);
	        			
	        			// Update the cart in the session
	        			session.setAttribute("cart", cart);
	        		}
	        		response.sendRedirect("bookAService.jsp?successMsg=Booked successfully!");
	        	}
	        	else
	        		response.sendRedirect("bookAService.jsp");
	        }
	        	
		} catch (NullPointerException e) {
	        // Log the error and send an appropriate response
	        e.printStackTrace(); // Optionally log to a file or logging system
	        response.sendRedirect("bookAService.jsp?errormsg=An error occurred: " + e.getMessage());
	     }
		catch (Exception e) {
	        // Catch any other unexpected exceptions
	        e.printStackTrace(); // Optionally log to a file or logging system
	        System.out.print(e.getMessage());
	        response.sendRedirect("bookAService.jsp?errMsg=exception");
	    }
		
	}

}
