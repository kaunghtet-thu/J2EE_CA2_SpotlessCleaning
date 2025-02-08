package servlet.miscellaneous;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import DAO.BookedServiceDAO;
import DAO.MemberDAO;
import bean.BookedService;

/**
 * Servlet implementation class VerifyCode
 */
@WebServlet("/ConfirmComplete")
public class ConfirmComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmComplete() {
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
        int staffId = (int)session.getAttribute("memberId");

        // Validate the input fields

        if (serviceIdStr == null || bookingIdStr == null) {
            response.sendRedirect("./customer/bookingHistory.jsp?errorMsg=Invalid service or booking ID.");
            return;
        }

        try {
            int serviceId = Integer.parseInt(serviceIdStr);
            int bookingId = Integer.parseInt(bookingIdStr); 

            BookedServiceDAO dao = new BookedServiceDAO();
            if (dao.confirmComplete(bookingId, serviceId)) {
                response.sendRedirect("./customer/bookingHistory.jsp?successMsg=Confirmed! Thank you for booking with us!");
            } else {
                response.sendRedirect("./customer/bookingHistory.jsp?errorMsg=Something went wrong. Please try again.");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("./customer/bookingHistory.jsp?errorMsg=Invalid input. Please try again.");
        }
    }


}
