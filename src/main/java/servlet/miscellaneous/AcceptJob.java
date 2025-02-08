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

/**
 * Servlet implementation class VerifyCode
 */
@WebServlet("/AcceptJob")
public class AcceptJob extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceptJob() {
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
            response.sendRedirect("./staff/availableJobs.jsp?errorMsg=Invalid service or booking ID.");
            return;
        }

        try {
            int serviceId = Integer.parseInt(serviceIdStr);
            int bookingId = Integer.parseInt(bookingIdStr);

            BookedServiceDAO dao = new BookedServiceDAO();
            if (dao.acceptJob(bookingId, serviceId, staffId)) {
                response.sendRedirect("./staff/availableJobs.jsp?successMsg=You just accepted a job!");
            } else {
                response.sendRedirect("./staff/availableJobs.jsp?errorMsg=Something went wrong. Please try again.");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("./staff/availableJobs.jsp?errorMsg=Invalid input. Please try again.");
        }
    }


}
