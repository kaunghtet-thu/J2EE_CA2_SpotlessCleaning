package servlet.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import DAO.ServiceDAO;
import bean.Service;

/**
 * Servlet implementation class AddNewService
 */
@WebServlet("/public/AddNewService")
public class AddNewService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewService() {
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
		String name = request.getParameter("serviceName");
        String description = request.getParameter("serviceDescription");
        double price = Double.parseDouble(request.getParameter("servicePrice"));
        String image = request.getParameter("image");
        String catId = request.getParameter("categoryId");
        Integer categoryId = Integer.parseInt(catId);
        

        // Save service to database
        Service newService = new Service(name, description, categoryId, price, image);
        ServiceDAO serviceDao = new ServiceDAO();
        boolean added = serviceDao.addService(newService);
        if(added)
        	response.sendRedirect("services.jsp?successMsg=Service added successfully&categoryId="+ categoryId);
        else
        	response.sendRedirect("services.jsp?successMsg=Service add failed");
        	
    }

}
