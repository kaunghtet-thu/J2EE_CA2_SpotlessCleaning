package servlet.category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import DAO.ServiceCategoryDAO;

/**
 * Servlet implementation class DeleteCategory
 */
@WebServlet("/public/DeleteCategory")
public class DeleteCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCategory() {
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
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        
        ServiceCategoryDAO dao = new ServiceCategoryDAO();
        boolean isDeleted = dao.deleteServiceCategory(categoryId);
        
        if (isDeleted) {
            // Redirect to the page with a success message
            response.sendRedirect("services.jsp?successMsg=Category+deleted+successfully");
        } else {
            // Redirect to the page with an error message
            response.sendRedirect("services.jsp?errorMsg=Failed+to+delete+category");
        }
    }

}
