package servlet.paymentCheckout;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.MemberDAO;
import DAO.ServiceDAO;
import bean.BookingService;
import bean.Invoice;
import bean.InvoiceItem;
import bean.Service;
import bean.ServiceInvoiceItem;
import bean.Address;

@WebServlet("/SaveCheckoutData")
public class SaveCheckoutData extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        HttpSession session = request.getSession();
        int memberId = (int)session.getAttribute("memberId");
        MemberDAO memberDAO = new MemberDAO();
        ServiceDAO serviceDAO = new ServiceDAO();

        // Get the common address, date, and time if selected
        Integer commonAddressId = null;
        String commonAddress = null;
        if (request.getParameter("commonAddress") != null && !request.getParameter("commonAddress").isEmpty()) {
            commonAddressId = Integer.parseInt(request.getParameter("commonAddress"));
            if (commonAddressId == -1) {
                commonAddress = request.getParameter("newCommonAddress");
            } else {
                Address commonAddressObj = memberDAO.getAddressById(commonAddressId);
                commonAddress = commonAddressObj.getAddress();
            }
        }
        String commonDate = request.getParameter("commonDate");
        String commonTime = request.getParameter("commonTime");
        

        // Get the selected services from the session
        List<Service> cart = (List<Service>) session.getAttribute("cart");

        // Process the form data
        List<Map<String, Object>> bookingDetails = new ArrayList<>();
        List<BookingService> bookingServices = new ArrayList<>();
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        for (Service service : cart) {
            int serviceId = service.getId();

            // Get the address, date, and time for the current service
            String addressParam = "address_" + serviceId;
            String dateParam = "serviceDate_" + serviceId;
            String timeParam = "serviceTime_" + serviceId;

            Integer addressId = null;
            String address = null;
            String date = null;
            String time = null;

            // Check if the user selected a different address, date, or time for the current service
            boolean differentAddresses = request.getParameter("differentAddresses") != null;
            boolean differentDates = request.getParameter("differentDates") != null;
            boolean differentTimes = request.getParameter("differentTimes") != null;

            if (differentAddresses) {
                if (request.getParameter(addressParam) != null && !request.getParameter(addressParam).isEmpty()) {
                    addressId = Integer.parseInt(request.getParameter(addressParam));
                    if (addressId == -1) {
                        address = request.getParameter("newAddress_" + serviceId);
                    } else {
                        Address selectedAddress = memberDAO.getAddressById(addressId);
                        address = selectedAddress.getAddress();
                    }
                } else {
                    address = null;
                }
            } else {
                addressId = commonAddressId;
                address = commonAddress;
            }

            if (differentDates) {
                date = request.getParameter(dateParam);
            } else {
                date = commonDate;
            }

            if (differentTimes) {
                time = request.getParameter(timeParam);
            } else {
                time = commonTime;
            }
            
            LocalDate serviceDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalTime serviceTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            // Process the booking and save the data
            BookingService bookingService = new BookingService();
            bookingService.setServiceId(serviceId);
            bookingService.setQuantity(1);
            bookingService.setAddressId(addressId);
            bookingService.setBookingDate(serviceDate);
            bookingService.setBookingTime(serviceTime);
            bookingServices.add(bookingService);
            
            //Create invoice item
            Service iService = serviceDAO.getServiceById(serviceId);
            ServiceInvoiceItem item = new ServiceInvoiceItem(iService.getName(), memberDAO.getAddressNameById(addressId), serviceDate, serviceTime, iService.getPrice());
            invoiceItems.add(item);
            
            Map<String, Object> bookingInfo = new HashMap<>();
            bookingInfo.put("serviceId", serviceId);
            bookingInfo.put("address", address);
            bookingInfo.put("date", date);
            bookingInfo.put("time", time);
            bookingDetails.add(bookingInfo);
        }
        
        Invoice invoice = new Invoice(memberDAO.getMemberName(memberId), LocalDateTime.now(), invoiceItems);
        
        // Retrieve the totalAmount parameter from the request
        String totalAmount = request.getParameter("totalAmount");
        System.out.print("mmspspsspps" + totalAmount);
        session.setAttribute("total", totalAmount);
        session.setAttribute("invoice", invoice);
        session.setAttribute("bookingServices", bookingServices);

        response.sendRedirect(request.getContextPath() + "/customer/payments.jsp?total=" + totalAmount);
    
//        request.setAttribute("errorMessage", "Booking failed. Please try again.");
//        request.getRequestDispatcher(request.getContextPath() + "/customer/cart.jsp").forward(request, response);        
    }
}