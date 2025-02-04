package servlet.category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import DAO.ServiceCategoryDAO;

/**
 * Servlet implementation class UpdateCategoryName
 */
@WebServlet("/UpdateCategoryName")
public class UpdateCategoryName extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCategoryName() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		 String newName = request.getParameter("newName");
	        
	        ServiceCategoryDAO dao = new ServiceCategoryDAO();
	        boolean isDeleted = dao.updateServiceCategory(categoryId, newName);
	        
	        if (isDeleted) {
	            // Redirect to the page with a success message
	            response.sendRedirect("services.jsp?successMsg=Category+deleted+successfully");
	        } else {
	            // Redirect to the page with an error message
	            response.sendRedirect("services.jsp?errorMsg=Failed+to+delete+category");
	        }
	}

}
