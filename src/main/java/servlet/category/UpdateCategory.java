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
 * Servlet implementation class UpdateCategory
 */
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2, // 2MB before writing to disk
	    maxFileSize = 1024 * 1024 * 10,      // Max file size of 10MB
	    maxRequestSize = 1024 * 1024 * 50    // Max request size of 50MB
	)
@WebServlet("/UpdateCategory")
public class UpdateCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCategory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newCategoryName = null;
        Part imagePart = null;
        int categoryId = -1;
        String existingImage = null;

        try {
            for (Part part : request.getParts()) {
                if (part.getName().equals("categoryId")) {
                    String cateId = new BufferedReader(new InputStreamReader(part.getInputStream())).readLine();
                    categoryId = Integer.parseInt(cateId.trim());
                } else if (part.getName().equals("serviceCategory")) {
                    newCategoryName = new BufferedReader(new InputStreamReader(part.getInputStream())).readLine();
                } else if (part.getName().equals("categoryImage")) {
                    imagePart = part;
                } else if (part.getName().equals("existingImage")) {
                    existingImage = new BufferedReader(new InputStreamReader(part.getInputStream())).readLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("./public/services.jsp?errorMsg=Error processing form data.");
            return;
        }

        System.out.println("At update servlet - Category ID: " + categoryId + ", Name: " + newCategoryName);

        // Define the upload directory dynamically
        String uploadDir = getServletContext().getRealPath("./assets/images");
        File uploadDirPath = new File(uploadDir);
        if (!uploadDirPath.exists()) {
            uploadDirPath.mkdirs(); // Create directory if it doesn't exist
        }

        // Determine if a new image is uploaded
        boolean isImageUploaded = (imagePart != null && imagePart.getSize() > 0);
        String imageFileName = isImageUploaded ? imagePart.getSubmittedFileName() : existingImage;

        // Save new image if uploaded
        if (isImageUploaded) {
            File imageFile = new File(uploadDirPath, imageFileName);
            imagePart.write(imageFile.getAbsolutePath());
        }

        // Validate input
        if (categoryId == -1 || newCategoryName == null || newCategoryName.trim().isEmpty()) {
            response.sendRedirect("./public/services.jsp?errorMsg=Invalid input. Please fill in all fields.");
            return;
        }

        // Call the appropriate DAO method
        boolean isUpdated;
        if (isImageUploaded) {
            isUpdated = ServiceCategoryDAO.updateServiceCategoryNameAndImage(categoryId, newCategoryName.trim(), imageFileName);
        } else {
            isUpdated = ServiceCategoryDAO.updateServiceCategoryName(categoryId, newCategoryName.trim());
        }

        // Redirect based on update result
        if (isUpdated) {
            response.sendRedirect("./public/services.jsp?successMsg=Category updated successfully.");
        } else {
            response.sendRedirect("./public/services.jsp?errorMsg=Error updating category.");
        }
    }



}
