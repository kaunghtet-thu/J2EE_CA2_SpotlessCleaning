package servlet.spotifact;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

import DAO.MerchandizeSalesDAO;

/**
 * Servlet implementation class SetPaymentStatusToPending
 */
@WebServlet("/SetPaymentStatusToPending")
public class SetPaymentStatusToPending extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetPaymentStatusToPending() {
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
	    // Extract the paymentReferenceId parameter from the request
	    String paymentReferenceId = request.getParameter("paymentReferenceId");

	    // If paymentReferenceId is missing, send a bad request response
	    if (paymentReferenceId == null) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing paymentReferenceId parameter");
	        return;
	    }


	    Client client = ClientBuilder.newClient();
	    String updateUrl = "http://localhost:3000/cleaning-supplies/updatePaymentStatusToPending/" + paymentReferenceId;
	    WebTarget target = client.target(updateUrl);
	    Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

	    try {
	        Response resp = invocationBuilder.put(null);

	        if (resp.getStatus() == HttpServletResponse.SC_OK) {
	        	MerchandizeSalesDAO dao = new MerchandizeSalesDAO();
	        	int id = Integer.parseInt(paymentReferenceId);
	        	boolean updateOwn = dao.setTransferredStatusTrue(id);
	        	if (updateOwn) {
	        		 response.getWriter().write("Payment status updated successfully.");
	 	            RequestDispatcher rd = request.getRequestDispatcher("./admin/spotifact.jsp?successMsg=Status updated successfully");
	 	            rd.forward(request, response);
	        	} else {
	        		 RequestDispatcher rd = request.getRequestDispatcher("./admin/spotifact.jsp?ErrMsg=Status updated to spotifact but failed to update on own server");
		 	            rd.forward(request, response);
	        	}
	           
	        } else {
	            response.sendError(resp.getStatus(), "Failed to update payment status: " + resp.getStatusInfo());
	        }
	    } catch (Exception e) {
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while making the request: " + e.getMessage());
	    }
	}


}
