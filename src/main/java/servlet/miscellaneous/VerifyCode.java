package servlet.miscellaneous;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import DAO.BookedServiceDAO;

/**
 * Servlet implementation class VerifyCode
 */
@WebServlet("/VerifyCode")
public class VerifyCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyCode() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the form fields
        String codeStr = request.getParameter("code");
        String serviceIdStr = request.getParameter("serviceId");
        String bookingIdStr = request.getParameter("bookingId");

        // Validate the input fields
        if (codeStr == null || codeStr.length() != 6 || !isNumeric(codeStr)) {
            response.sendRedirect("./staff/acceptedJobs.jsp?errorMsg=Code must be a 6-digit number.");
            return;
        }

        if (serviceIdStr == null || bookingIdStr == null) {
            response.sendRedirect("./staff/acceptedJobs.jsp?errorMsg=Invalid service or booking ID.");
            return;
        }

        try {
            int code = Integer.parseInt(codeStr);
            int serviceId = Integer.parseInt(serviceIdStr);
            int bookingId = Integer.parseInt(bookingIdStr);

            BookedServiceDAO dao = new BookedServiceDAO();
            if (dao.verifyCode(bookingId, serviceId, code)) {
                response.sendRedirect("./staff/acceptedJobs.jsp?successMsg=Key in success! Status updated!");
            } else {
                response.sendRedirect("./staff/acceptedJobs.jsp?errorMsg=Wrong code! Try again.");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("./staff/acceptedJobs.jsp?errorMsg=Invalid input. Please try again.");
        }
    }

    private static boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }

}
