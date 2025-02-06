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
 * Servlet implementation class UpdateCategory
 */
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
        try {
            // Retrieve category ID
        	System.out.println("At update servlet " + request.getParameter("categoryId"));
        	String cateId = request.getParameter("categoryId");
            int categoryId = Integer.parseInt(cateId);

            // Retrieve new category name
            String newCategoryName = request.getParameter("serviceCategory");

            // Retrieve uploaded image file
            Part imagePart = request.getPart("categoryImage");

            // Define upload directory dynamically
            String uploadDir = getServletContext().getRealPath("./assets/images");
            File uploadDirPath = new File(uploadDir);
            if (!uploadDirPath.exists()) {
                uploadDirPath.mkdirs(); // Create directory if it doesn't exist
            }

            // Determine if a new image is uploaded
            boolean isImageUploaded = (imagePart != null && imagePart.getSize() > 0);
            String imageFileName = isImageUploaded ? imagePart.getSubmittedFileName() : request.getParameter("existingImage");

            // Save new image if uploaded
            if (isImageUploaded) {
                File imageFile = new File(uploadDirPath, imageFileName);
                imagePart.write(imageFile.getAbsolutePath());
            }

            // Validate input
            if (newCategoryName == null || newCategoryName.trim().isEmpty()) {
                response.sendRedirect("services.jsp?errorMsg=Invalid input. Please fill in all fields.");
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

        } catch (NumberFormatException e) {
            response.sendRedirect("./public/services.jsp?errorMsg=Invalid category ID.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("./public/services.jsp?errorMsg=An unexpected error occurred.");
        }
    }


}
