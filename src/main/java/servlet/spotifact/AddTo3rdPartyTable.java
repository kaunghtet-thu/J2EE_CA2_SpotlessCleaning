package servlet.spotifact;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import bean.RetailerSales;


/**
 * Servlet implementation class AddToLee
 */
@WebServlet("/AddTo3rdPartyTable")
public class AddTo3rdPartyTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTo3rdPartyTable() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve form data from the request
					HttpSession session = request.getSession();
			        List<RetailerSales> retailerSales = (List<RetailerSales>)session.getAttribute("retailerSales");
			        List<Integer> newIds = (List<Integer>)session.getAttribute("newIds");
			        int count = 0;
			      for(RetailerSales rs : retailerSales) {
			    	  try {
							rs.setPaymentReferencingId(newIds.get(count));
							Client client = ClientBuilder.newClient();
					        String restUrl = "http://localhost:3000/cleaning-supplies/addRetailerSale";
					        WebTarget target = client.target(restUrl);
					        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					        Response resp = invocationBuilder.post(Entity.entity(rs, MediaType.APPLICATION_JSON));

					        if (resp.getStatus() != 200) {
					            request.setAttribute("message", "3rd party add failed!");
					            response.getWriter().println("<h1>Failed to insert user. Please try again.</h1>");
					        } 
				            
				        } catch (NumberFormatException e) {
				            response.getWriter().println("<h1>Error: NumberFormatException</h1>");
				        } 			
			    	  count++;
			      }

		     response.sendRedirect(request.getContextPath() + "/GenerateReceipt");

	}
}


