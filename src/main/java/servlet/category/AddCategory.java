package servlet.category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

import DAO.ServiceCategoryDAO;

/**
 * Servlet implementation class AddCategory
 */
@WebServlet("/AddCategory")
public class AddCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCategory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method not allowed for this operation.");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newCategoryName = request.getParameter("serviceCategory");
        System.out.println("At add servlet " + request.getParameter("serviceCategory"));
        Part imagePart = request.getPart("categoryImage");

        // Define the upload directory dynamically
        String uploadDir = getServletContext().getRealPath("./assets/images");
        File uploadDirPath = new File(uploadDir);
        if (!uploadDirPath.exists()) {
            uploadDirPath.mkdirs(); // Create directory if it doesn't exist
        }

        String imageFileName = (imagePart == null || imagePart.getSize() == 0) ? "default.png" : imagePart.getSubmittedFileName();

        // Save the uploaded image if provided
        if (!imageFileName.equals("default.png")) {
            File imageFile = new File(uploadDirPath, imageFileName);
            imagePart.write(imageFile.getAbsolutePath());
        }

        // Validate input
        if (newCategoryName == null || newCategoryName.trim().isEmpty()) {
            response.sendRedirect("./public/services.jsp?errorMsg=Invalid input. Please fill in all fields.");
            return;
        }

        // Delegate database insertion to DAO
        boolean isInserted = ServiceCategoryDAO.insertCategory(newCategoryName.trim(), imageFileName);

        if (isInserted) {
            response.sendRedirect("./public/services.jsp?successMsg=Category added successfully.");
        } else {
            response.sendRedirect("./public/services.jsp?errorMsg=Error adding category.");
        }
    }
}
