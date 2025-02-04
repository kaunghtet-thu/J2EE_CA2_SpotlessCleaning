package servlet.miscellaneous;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


import DAO.MemberDAO;
import bean.Member;

/**
 * Servlet implementation class Register
 */
@WebServlet("/public/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
		// TODO Auto-generated method stub

		doGet(request, response);

		String name = request.getParameter("name");
		String email= request.getParameter("email");
		String password= request.getParameter("password");
		String phone= request.getParameter("phone");
        // Check if the fields are not empty
        if (name == null || email == null || password == null || phone == null ||
            name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            response.sendRedirect("register.jsp?errCode=registerError");
            return;
        }

        // Hash the password (using SHA-256 in this case, you can adjust it)
        String hashedPassword = MemberDAO.hashPassword(password);
        if (hashedPassword == null) {
            response.sendRedirect("register.jsp?errCode=registerError");
            return;
        }

        // Create the MemberDAO instance
        MemberDAO memberDAO = new MemberDAO();

        // Call the createMember method to insert into the database
        Member newMember = memberDAO.createMember(name, email, hashedPassword, phone);

        if (newMember != null) {
            // Successfully registered, redirect to login page or success page
            response.sendRedirect("login.jsp?successMessage=Registration successful, please login.");
        } else {
            // Registration failed, redirect back to the register form with an error message
            response.sendRedirect("register.jsp?errCode=registerError");
        }
	}
	
}




