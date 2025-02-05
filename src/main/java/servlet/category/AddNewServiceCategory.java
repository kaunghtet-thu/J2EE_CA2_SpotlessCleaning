package servlet.category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.itextpdf.io.source.ByteArrayOutputStream;

import DB.DatabaseUtil;

@WebServlet("/public/AddNewServiceCategory")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
	    maxFileSize = 1024 * 1024 * 10,      // 10 MB
	    maxRequestSize = 1024 * 1024 * 100   // 100 MB
	)
public class AddNewServiceCategory extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddNewServiceCategory() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method not allowed for this operation.");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the category name
        Part categoryNamePart = request.getPart("serviceCategory");
        String newCategoryName = getValueFromPart(categoryNamePart);
        System.out.println(newCategoryName + " - This is the new category name");

        // Retrieve the uploaded image file
        Part imagePart = request.getPart("categoryImage");

        // Define the upload directory dynamically
        String uploadDir = getServletContext().getRealPath("./assets/images");
        File uploadDirPath = new File(uploadDir);
        if (!uploadDirPath.exists()) {
            uploadDirPath.mkdirs();  // Create the directory if it doesn't exist
        }

        String imageFileName;
        
        // If no image is uploaded, use the default image
        if (imagePart == null || imagePart.getSize() == 0) {
            imageFileName = "default.png"; // Default image filename
        } else {
            // Get the submitted file name
            imageFileName = imagePart.getSubmittedFileName();
            System.out.println("Uploaded file name: " + imageFileName);

            // Save the uploaded image
            File imageFile = new File(uploadDirPath, imageFileName);
            imagePart.write(imageFile.getAbsolutePath());
        }

        // Validate inputs
        if (newCategoryName == null || newCategoryName.trim().isEmpty()) {
            response.sendRedirect("services.jsp?errorMsg=Invalid input. Please fill in all fields.");
            return;
        }

        // Insert the category into the database
        String sql = "INSERT INTO category (name, image) VALUES (?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setString(1, newCategoryName.trim());
            stmt.setString(2, imageFileName);

            int result = stmt.executeUpdate();
            if (result > 0) {
                response.sendRedirect("services.jsp?successMsg=Category added successfully.");
            } else {
                response.sendRedirect("services.jsp?errorMsg=Error adding category.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while adding the category.");
        }
    }

    // Helper method to extract the value from a Part object
    private String getValueFromPart(Part part) throws IOException {
        if (part == null) {
            return null;
        }
        InputStream inputStream = part.getInputStream();
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }
}
