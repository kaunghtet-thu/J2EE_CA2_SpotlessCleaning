package servlet.merchandize;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import bean.Product;

/**
 * Servlet implementation class GetAllProducts
 */
@WebServlet("/GetAllProducts")
public class GetAllProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllProducts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
        Client client = ClientBuilder.newClient();
        String restUrl = "http://localhost:3000/cleaning-supplies/getAllProducts/2";
        WebTarget target = client.target(restUrl);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response resp = invocationBuilder.get();
        System.out.println("status: " + resp.getStatus());

        // https://stackoverflow.com/questions/18086621/read-response-body-in-jax-rs-client-from-a-post-request
        if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
            System.out.println("success");

            // https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/generic-entity.html
            List<Product> al = resp.readEntity(new GenericType<List<Product>>() {}); // needs empty body to preserve generic type

            // Write to request object for forwarding to target page
            request.setAttribute("productArray", al);
            session.setAttribute("products", al);
            System.out.println("......requestObj set...forwarding..");
            String url = "./customer/merchandize.jsp";
//            String url = "testWeb.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(url);
             rd.forward(request, response);
        } else {
            System.out.println("failed");
            String url = "./customer/merchandize.jsp";
//            String url = "testWeb.jsp";
            request.setAttribute("err", "NotFound");
            RequestDispatcher rd = request.getRequestDispatcher(url);
             rd.forward(request, response);
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
