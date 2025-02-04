package servlet.miscellaneous;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DAO.MemberDAO;
import bean.Member;
import bean.Service;

/**
 * Servlet implementation class VerifyUser
 */
@WebServlet("/VerifyUser")
public class VerifyUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		MemberDAO dao = new MemberDAO();
		//dao.loginMember(email, password, session);
		Member member = dao.loginMember(email, password);
			if (member != null) {
				// set session
				HttpSession session = request.getSession();
				List<Service> cart = new ArrayList<>();
	            session.setAttribute("cart", cart);
				session.setAttribute("member", member);
				session.setAttribute("memberId", member.getId());
				
				response.sendRedirect("./index.jsp");
			} else {
				response.sendRedirect("./public/login.jsp?errorMsg=invalidLogin");
			}
	}
	
	
}
