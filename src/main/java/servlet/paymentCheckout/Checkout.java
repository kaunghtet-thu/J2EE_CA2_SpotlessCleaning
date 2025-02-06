package servlet.paymentCheckout;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import DAO.BookingDAO;
import DAO.BookingServiceDAO;
import DAO.MemberDAO;
import bean.Address;
import bean.BookingService;
import bean.Invoice;
import bean.InvoiceItem;
import bean.Service;


/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        int memberId = (int)session.getAttribute("memberId");
        List<BookingService> bookingServices =(List<BookingService>)session.getAttribute("bookingServices");
        List<Service> cart = (List<Service>)session.getAttribute("cart");   
		// Create the booking
		MemberDAO memberDAO = new MemberDAO();
        BookingDAO bookingDAO = new BookingDAO();
        
        // create booking
        int bookingId = bookingDAO.createBooking(memberId);
        session.setAttribute("bookingId", bookingId);
        
        // Create the booking services
        BookingServiceDAO bookingServiceDAO = new BookingServiceDAO();
        boolean success = bookingServiceDAO.createBookingServices(bookingId, bookingServices);     
        boolean emptyCart = (boolean)session.getAttribute("emptyCart");
        if (success) {
        	if(emptyCart) {
        		cart.clear();
        	}
        	else {
        		cart.removeIf(service -> service.getId() == (int)session.getAttribute("singleId"));
        	}
            session.setAttribute("cart", cart);
            response.sendRedirect(request.getContextPath() + "/GenerateReceipt");
        } else {
        	response.sendRedirect(request.getContextPath() + "/customer/cart.jsp?errorMsg=Booking Failed!");
        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
