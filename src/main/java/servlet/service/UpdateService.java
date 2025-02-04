package servlet.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import DAO.ServiceDAO;

/**
 * Servlet implementation class UpdateService
 */
@WebServlet("/public/UpdateService")
public class UpdateService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateService() {
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
		int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        String name = request.getParameter("serviceName");
        String description = request.getParameter("serviceDescription");
        double price = Double.parseDouble(request.getParameter("servicePrice"));
       System.out.print(serviceId + name + description + price + "  at servlet");

        ServiceDAO dao = new ServiceDAO();
        boolean isUpdated = dao.updateService(serviceId, name, description, price);

        if (isUpdated) {
            response.sendRedirect("services.jsp?successMsg=Service updated successfully");
        } else {
            response.sendRedirect("services.jsp?errorMsg=Failed to update the service.");
        }
    }
	
}
