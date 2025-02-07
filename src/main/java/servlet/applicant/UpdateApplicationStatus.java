package servlet.applicant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import DAO.ApplicantDAO;

/**
 * Servlet implementation class UpdateApplicationStatus
 */
@WebServlet("/UpdateApplicationStatus")
public class UpdateApplicationStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateApplicationStatus() {
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
	        // Get applicationId and approval status from the form
	        String applicationIdStr = request.getParameter("applicationId");
	        String approveStatus = request.getParameter("approve");

	        // Convert applicationId to integer
	        int applicationId = Integer.parseInt(applicationIdStr);

	        // Create DAO object
	        ApplicantDAO applicantDAO = new ApplicantDAO();

	        // Check the approval status and call the corresponding method in DAO
	        boolean statusUpdated = false;
	        if ("Yes".equalsIgnoreCase(approveStatus)) {
	            // Approve the application
	            statusUpdated = applicantDAO.approveApplicantApplication(applicationId);
	        } else if ("No".equalsIgnoreCase(approveStatus)) {
	            // Reject the application
	            statusUpdated = applicantDAO.rejectApplicantApplication(applicationId);
	        }

	        // Set response message based on success or failure
	        if (statusUpdated) {
	            // Redirect to a success page or update status message (you can change the location)
	            response.sendRedirect("./shared/applyForStaff.jsp?successMsg=Applicant status updated successfully");
	        } else {
	            // Redirect to an error page or update status message (you can change the location)
	            response.sendRedirect("./shared/applyForStaff.jsp?errorMsg=Updating applicant status error");
	        }
	    }

}
