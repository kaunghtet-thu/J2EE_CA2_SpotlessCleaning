package servlet.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import DAO.ServiceDAO;

/**
 * Servlet implementation class DeleteService
 */
@WebServlet("/public/DeleteService")
public class DeleteService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteService() {
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
		int serviceId = Integer.parseInt(request.getParameter("serviceId"));

        ServiceDAO dao = new ServiceDAO();
        boolean isDeleted = dao.deleteService(serviceId);

        if (isDeleted) {
            response.sendRedirect("services.jsp?successMsg=Service deleted!");
        } else {
            response.sendRedirect("services.jsp?errorMsg=Failed to delete the service.");
        }
	}

}
