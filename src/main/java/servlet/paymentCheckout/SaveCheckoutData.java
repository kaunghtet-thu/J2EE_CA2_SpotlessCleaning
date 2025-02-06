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
        int memberId = (int) session.getAttribute("memberId");

        MemberDAO memberDAO = new MemberDAO();
        ServiceDAO serviceDAO = new ServiceDAO();

        // Retrieve common selections
        Integer commonAddressId = null;
        String commonAddress = null;
        if (request.getParameter("commonAddress") != null && !request.getParameter("commonAddress").isEmpty()) {
            commonAddressId = Integer.parseInt(request.getParameter("commonAddress"));
            Address commonAddressObj = memberDAO.getAddressById(commonAddressId);
            commonAddress = commonAddressObj.getAddress();
        }
        String commonDate = request.getParameter("commonDate");
        String commonTime = request.getParameter("commonTime");

        boolean differentAddresses = request.getParameter("differentAddresses") != null;
        boolean differentDates = request.getParameter("differentDates") != null;
        boolean differentTimes = request.getParameter("differentTimes") != null;

        List<Service> cart = (List<Service>) session.getAttribute("cart");
        List<BookingService> bookingServices = new ArrayList<>();
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        for (Service service : cart) {
            int serviceId = service.getId();

            // Determine address
            Integer addressId = commonAddressId;
            String address = commonAddress;
            if (differentAddresses) {
                String addressParam = request.getParameter("address_" + serviceId);
                if (addressParam != null && !addressParam.isEmpty()) {
                    addressId = Integer.parseInt(addressParam);
                    address = memberDAO.getAddressById(addressId).getAddress();
                }
            }

            // Determine date
            String date = commonDate;
            if (differentDates) {
                String dateParam = request.getParameter("serviceDate_" + serviceId);
                if (dateParam != null && !dateParam.isEmpty()) {
                    date = dateParam;
                }
            }

            // Determine time
            String time = commonTime;
            if (differentTimes) {
                String timeParam = request.getParameter("serviceTime_" + serviceId);
                if (timeParam != null && !timeParam.isEmpty()) {
                    time = timeParam;
                }
            }

            if (date != null && !date.isEmpty() && time != null && !time.isEmpty()) {
                LocalDate serviceDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalTime serviceTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));

                BookingService bookingService = new BookingService();
                bookingService.setServiceId(serviceId);
                bookingService.setQuantity(1);
                bookingService.setAddressId(addressId);
                bookingService.setBookingDate(serviceDate);
                bookingService.setBookingTime(serviceTime);
                bookingServices.add(bookingService);

//                Service iService = serviceDAO.getServiceById(serviceId);
                ServiceInvoiceItem item = new ServiceInvoiceItem(
                        service.getName(),
                        memberDAO.getAddressNameById(addressId),
                        serviceDate, serviceTime,
                        service.getPrice()
                );
                invoiceItems.add(item);
            } else {
                System.out.println("Invalid date or time for service ID: " + serviceId);
            }
        }

        Invoice invoice = new Invoice(memberDAO.getMemberName(memberId), LocalDateTime.now(), invoiceItems);
        String totalAmount = request.getParameter("totalAmount");
        session.setAttribute("total", totalAmount);
        session.setAttribute("invoice", invoice);
        session.setAttribute("bookingServices", bookingServices);

        String userEmail = request.getParameter("email");
        String recipientEmail = request.getParameter("recipientEmail");
        session.setAttribute("recipientEmail", recipientEmail == null || recipientEmail.trim().isEmpty() ? userEmail : recipientEmail);
        
        response.sendRedirect(request.getContextPath() + "/customer/payments.jsp?total=" + totalAmount);
    }
}