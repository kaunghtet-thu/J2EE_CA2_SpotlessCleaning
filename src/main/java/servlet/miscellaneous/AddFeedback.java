package servlet.miscellaneous;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import DAO.BookedServiceDAO;
import DAO.FeedbackDAO;
import DAO.MemberDAO;
import bean.BookedService;

/**
 * Servlet implementation class VerifyCode
 */
@WebServlet("/AddFeedback")
public class AddFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFeedback() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the form fields
    	HttpSession session = request.getSession();
        String serviceIdStr = request.getParameter("serviceId");
        String bookingIdStr = request.getParameter("bookingId");
        String ratingStr = request.getParameter("rating");
        String feedback = request.getParameter("feedback");

        // Validate the input fields
        if (serviceIdStr == null || bookingIdStr == null) {
            response.sendRedirect("./customer/bookingHistory.jsp?errorMsg=Invalid service or booking ID.");
            return;
        }

        try {
            int serviceId = Integer.parseInt(serviceIdStr);
            int bookingId = Integer.parseInt(bookingIdStr); 
            int rating = Integer.parseInt(ratingStr); 

            FeedbackDAO dao = new FeedbackDAO();
            if (dao.addFeedback(bookingId, serviceId, rating, feedback)) {
                response.sendRedirect("./customer/bookingHistory.jsp?successMsg=Thank you for your feedback!");
            } else {
                response.sendRedirect("./customer/bookingHistory.jsp?errorMsg=Something went wrong. Please try again.");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("./customer/bookingHistory.jsp?errorMsg=Invalid input. Please try again.");
        }
    }


}
