package servlet.cartBooking;

import jakarta.servlet.RequestDispatcher;
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
import DAO.FeedbackDAO;
import DAO.StatusDAO;
import bean.BookingItems;

/**
 * Servlet implementation class ManageBooking
 */
@WebServlet("/public/ManageBooking")
public class ManageBooking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageBooking() {
        super();
        // TODO Auto-generated constructor stub
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();
	        BookingDAO bookingDAO = new BookingDAO();
	        FeedbackDAO feedbackDAO = new FeedbackDAO();
	        StatusDAO statusDAO = new StatusDAO();

	        int memberId = (int) session.getAttribute("memberId"); // Assuming memberId is stored in session
	        String action = request.getParameter("action");
	        String message = null;

	        try {
	            if ("feedback".equals(action)) {
	                int bookingId = Integer.parseInt(request.getParameter("feedback_id"));
	                request.setAttribute("feedbackId", bookingId);
	            } 
	            else if ("edit".equals(action)) {
	                int bookingId = Integer.parseInt(request.getParameter("edit_id"));
	                request.setAttribute("editId", bookingId);
	            } 
	            else if ("submitFeedback".equals(action)) {
	                int bookingId = Integer.parseInt(request.getParameter("booking_id"));
	                int rating = Integer.parseInt(request.getParameter("rating"));
	                String comments = request.getParameter("comments");
	                boolean feedbackAdded = feedbackDAO.addFeedback(bookingId, rating, comments);
	                message = feedbackAdded ? "Feedback added successfully!" : "Failed to add feedback.";
	            } 
	            else if ("editFeedback".equals(action)) {
	            	int bookingId = Integer.parseInt(request.getParameter("booking_id"));
	            	int rating = Integer.parseInt(request.getParameter("rating"));
	            	String comments = request.getParameter("comments");
	            	boolean feedbackUpdated = feedbackDAO.updateFeedback(bookingId, rating, comments);
	            	message = feedbackUpdated ? "Feedback updated successfully!" : "Failed to update feedback.";
	            } 
	            else if ("delete".equals(action)) {
	                int bookingId = Integer.parseInt(request.getParameter("delete_id"));
	                boolean feedbackDeleted = feedbackDAO.deleteFeedback(bookingId);
	                message = feedbackDeleted ? "Feedback deleted successfully!" : "Failed to delete feedback.";
	            } 
	            else if ("updateStatus".equals(action)) {
	                int id = Integer.parseInt(request.getParameter("id"));
	                int statusId = Integer.parseInt(request.getParameter("status_id"));
	                boolean updated = bookingDAO.updateBookingStatus(id, statusId);
	                message = updated ? "Booking updated successfully!" : "Failed to update booking.";
	            } 
	        } catch (Exception e) {
	            message = "Error: " + e.getMessage();
	        }


	        RequestDispatcher dispatcher = request.getRequestDispatcher("bookings.jsp");
	        dispatcher.forward(request, response);
	    }
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
}
