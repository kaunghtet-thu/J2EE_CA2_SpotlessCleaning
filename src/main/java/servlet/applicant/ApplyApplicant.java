package servlet.applicant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import DAO.*;
/**
 * Servlet implementation class ApplyApplicant
 */
@WebServlet("/ApplyApplicant")
public class ApplyApplicant extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyApplicant() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Getting the parameters from the request
		 HttpSession session =  request.getSession();
	        int memberId = (int)session.getAttribute("memberId");

	        String isChecked = request.getParameter("isChecked");
	        boolean agree = (isChecked != null && isChecked.equals("true"));
	            try {
	         
	                boolean success = ApplicantDAO.addApplicant(memberId, agree);

	                if (success) {
	                	 response.sendRedirect("./shared/applyForStaff.jsp?successMsg=Applied successfully");
	                } else {
	                	 response.sendRedirect("./shared/applyForStaff.jsp?errorMsg=Error application.");
	                }
	            } catch (NumberFormatException e) {
	            	 response.sendRedirect("./shared/applyForStaff.jsp?errorMsg=Invalid MemberId or Agree.");
	            }
	      
	    }

}
