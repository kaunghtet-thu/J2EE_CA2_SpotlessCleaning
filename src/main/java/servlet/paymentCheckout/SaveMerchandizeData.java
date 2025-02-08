package servlet.paymentCheckout;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.MerchandizeInvoiceItem;
import bean.Product;

/**
 * Servlet implementation class SaveMerchandizeData
 */
@WebServlet("/SaveMerchandizeData")
public class SaveMerchandizeData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
        List<Product> itemList = (List<Product>) session.getAttribute("products");
        List<MerchandizeInvoiceItem> invoiceItems = new ArrayList<>();

        if (itemList != null) {
            for (Product item : itemList) {
                // Get quantity from request parameters
                String quantityParam = request.getParameter("quantity_" + item.getId());
                
                if (quantityParam != null) {
                    int quantity = Integer.parseInt(quantityParam);

                    if (quantity > 0) { // Only include items with quantity > 0
                        MerchandizeInvoiceItem invoiceItem = new MerchandizeInvoiceItem(
                            item.getName(),
                            item.getPrice(),
                            quantity
                        );
                        invoiceItems.add(invoiceItem);
                    }
                }
            }
        }

        // Store invoiceItems list in session or request scope
        session.setAttribute("merchandizeInvoiceItems", invoiceItems);
        
        String mTotalstr = (String)request.getParameter("mTotalInput");
        String totalstr = (String)session.getAttribute("total");
     // Convert to double and perform addition
        double total = Double.parseDouble(totalstr);
        double mTotal = Double.parseDouble(mTotalstr);
        double sum = total + mTotal;

        // Convert back to string if needed
        String finalGrandTotal = String.valueOf(sum);
        session.setAttribute("finalGrandTotal", finalGrandTotal);

       
        System.out.println("Total Amount: $" + finalGrandTotal);

        // Forward or redirect to confirmation page
        request.setAttribute("finalGrandTotal", finalGrandTotal);
        request.getRequestDispatcher("./customer/payments.jsp").forward(request, response);
    }
}
