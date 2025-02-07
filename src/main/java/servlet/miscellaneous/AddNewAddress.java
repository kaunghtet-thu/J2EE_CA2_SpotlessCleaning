package servlet.miscellaneous;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import DAO.MemberDAO;

/**
 * Servlet implementation class AddNewAddress
 */
@WebServlet("/AddNewAddress")
public class AddNewAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewAddress() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
        int memberId = (int)session.getAttribute("memberId");
        String newAddress = request.getParameter("newAddress");
        MemberDAO dao = new MemberDAO();
        if(dao.addMemberAddress(memberId, newAddress)) {
        	response.sendRedirect("./customer/bookCart.jsp?successMsg="+"New Address Added!");
        } else
        	response.sendRedirect("./customer/bookCart.jsp?errorMsg="+"New Address Add Failed!");

        
	}

}
