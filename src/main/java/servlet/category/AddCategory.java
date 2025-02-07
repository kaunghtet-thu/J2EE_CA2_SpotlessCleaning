package servlet.category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.io.IOException;

import DAO.ServiceCategoryDAO;

/**
 * Servlet implementation class AddCategory
 */
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2, // 2MB before writing to disk
	    maxFileSize = 1024 * 1024 * 10,      // Max file size of 10MB
	    maxRequestSize = 1024 * 1024 * 50    // Max request size of 50MB
	)
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
        // Parse the multipart request
        String newCategoryName = null;
        Part imagePart = null;

        try {
            for (Part part : request.getParts()) {
                if (part.getName().equals("serviceCategory")) {
                    newCategoryName = new BufferedReader(new InputStreamReader(part.getInputStream())).readLine();
                } else if (part.getName().equals("categoryImage")) {
                    imagePart = part;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("./public/services.jsp?errorMsg=Error processing form data.");
            return;
        }

        System.out.println("At add servlet " + newCategoryName); // Debugging

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
