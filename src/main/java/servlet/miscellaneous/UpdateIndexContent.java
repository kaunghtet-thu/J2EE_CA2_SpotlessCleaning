package servlet.miscellaneous;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import DAO.IndexDAO;

/**
 * Servlet implementation class UpdateIndexContent
 */
@WebServlet("/public/UpdateIndexContent")
public class UpdateIndexContent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateIndexContent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		   String contentIdStr = request.getParameter("contentId");
	        String content = request.getParameter("content");
	        String isAdminStr = request.getParameter("isAdmin"); 
	        
	        System.out.print(contentIdStr + content+ isAdminStr + "at servlet" );
	        
	        // Convert contentId to an integer
	        int contentId = -1;
	        boolean isAdmin = false;
	        
	        try {
	            contentId = Integer.parseInt(contentIdStr);
	            isAdmin = Boolean.parseBoolean(isAdminStr);
	        } catch (NumberFormatException e) {
	            // Handle error for invalid contentId or isAdmin value
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters.");
	            return;
	        }

	        // Validate content parameter
	        if (content == null || content.trim().isEmpty()) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Content cannot be empty.");
	            return;
	        }

	        // Call the DAO method to update the content
	        boolean success = IndexDAO.editContentById(contentId, content, isAdmin);

	        // Handle the response based on success or failure
	        if (success) {
	            response.sendRedirect("index.jsp?successCode=editSuccess"); 
	        } else {
	            response.sendRedirect("index.jsp?errorCode=editErr");
	        }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
