package servlet.paymentCheckout;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import DAO.Invoicing;
import bean.Invoice;
import bean.MerchandizeInvoiceItem;
import bean.ServiceInvoiceItem;
import bean.InvoiceItem;

@WebServlet("/GenerateReceipt")
public class InvoicingServlet extends HttpServlet {

    private Invoicing bookingReceiptService = new Invoicing();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	Invoice invoice = (Invoice) session.getAttribute("invoice");
    	
    	// Hard-code email data to test //
//    	ServiceInvoiceItem item1 = new ServiceInvoiceItem("Plumbing Service", "123 Main St, Singapore", LocalDate.of(2024, 2, 3), LocalTime.of(14, 30), 150.75);
//    	ServiceInvoiceItem item2 = new ServiceInvoiceItem("Electrical Repair", "456 Orchard Rd, Singapore", LocalDate.of(2024, 2, 10), LocalTime.of(10, 15), 200.50);
//    	ServiceInvoiceItem item3 = new ServiceInvoiceItem("House Cleaning", "789 Marina Bay, Singapore", LocalDate.of(2024, 2, 15), LocalTime.of(9, 0), 120.00);
//    	List<InvoiceItem> items= new ArrayList<InvoiceItem>();
//    	items.add(item1);
//    	items.add(item2);
//    	items.add(item3);
//    	MerchandizeInvoiceItem item4 = new MerchandizeInvoiceItem("Air Fresher", 10.0, 3);
//    	items.add(item4);
//    	Invoice invoice = new Invoice(101, "John Doe", LocalDateTime.of(2024, 2, 1, 12, 0), items);

    	try {
            byte[] pdfBytes = bookingReceiptService.generatePdfReceipt(invoice);

            // Send the email with the PDF attachment
//            String recipientEmail = "b660360@gmail.com";
            String recipientEmail = "kaunghsetaung8@gmail.com";
            String subject = "Your Booking Receipt";
            String body = "Thank you for your booking. Please find your receipt attached.\n\n" +
                    "Best regards,\n" +
                    "Spotless Cleaning Services Team\n";
            bookingReceiptService.sendEmailWithAttachment(recipientEmail, subject, body, pdfBytes);

            // Respond to the client
            response.getWriter().write("Receipt generated and emailed successfully!");
        } catch (Exception e) {
            // Handle exceptions (e.g., log and send error response)
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred: " + e.getMessage());
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}
}
