package DAO;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import bean.Invoice;
import bean.InvoiceItem;
import bean.MerchandizeInvoiceItem;
import bean.ServiceInvoiceItem;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.DataHandler;
import jakarta.mail.util.ByteArrayDataSource;

import java.io.*;

import java.util.List;
import java.util.Properties;

public class Invoicing {

    // Method to generate PDF receipt for a single invoice and return it as byte array
    public byte[] generatePdfReceipt(Invoice invoice) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Create a PDF writer linked to the byte array output stream
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // Add heading
        document.add(new Paragraph("INVOICE").setFontSize(20));
        document.add(new Paragraph("SPOTLESS CLEANING SERVICES").setFontSize(18));
        document.add(new Paragraph("Booking Receipt").setFontSize(14));
        document.add(new Paragraph("\n"));

        // Add booking details
        document.add(new Paragraph("Booking ID: " + invoice.getBookingid()));
        document.add(new Paragraph("Customer Name: " + invoice.getCustomerName()));
        document.add(new Paragraph("Booked At: " + invoice.getBookedAt().toString()));
        document.add(new Paragraph("\n"));
        
        
        // Create a table for service details
     // Create a table with 4 columns
        Table serviceTable = new Table(new float[] { 4, 4, 3, 3, 2 })  // Added one more column of width 4
                .useAllAvailableWidth();

        // Add table headers
        serviceTable.addCell("Service Taken");
        serviceTable.addCell("Address");  // New column
        serviceTable.addCell("Service Date");
        serviceTable.addCell("Time Slot");
        serviceTable.addCell("Price");
        
        Table merchandizeTable = new Table(new float[] { 5, 2, 2, 2 }) // Column widths
                .useAllAvailableWidth();

        // Add table headers
        merchandizeTable.addCell("Product Name");
        merchandizeTable.addCell("Unit Price");
        merchandizeTable.addCell("Quantity");
        merchandizeTable.addCell("Total Price");

       

        List<InvoiceItem> invoiceItems = invoice.getInvoiceItem();
        double totalprice = 0.0;
        double serviceTotalPrice = 0.0;
        double merchandizeTotalPrice = 0.0;
        boolean hasServiceItems = false;
        boolean hasMerchandizeItems = false;
        
        for (InvoiceItem item : invoiceItems) {
         
            if (item instanceof ServiceInvoiceItem) { // Check if it's a ServiceInvoiceItem
                ServiceInvoiceItem serviceItem = (ServiceInvoiceItem) item; // Cast to subclass
                serviceTable.addCell(serviceItem.getName()); 
                serviceTable.addCell(serviceItem.getAddress());
                serviceTable.addCell(serviceItem.getBookingDate().toString());
                serviceTable.addCell(serviceItem.getBookingTime().toString());
                serviceTable.addCell("$" + String.format("%.2f", serviceItem.getPrice())); 
                serviceTotalPrice += serviceItem.getPrice();
                totalprice += serviceItem.getPrice();
                hasServiceItems = true;
            } else  {
            	MerchandizeInvoiceItem merchandizeItem = (MerchandizeInvoiceItem) item;
            	merchandizeTable.addCell(merchandizeItem.getName());
            	merchandizeTable.addCell("$" + String.format("%.2f", merchandizeItem.getUnitPrice()));
            	merchandizeTable.addCell(String.format("%d", merchandizeItem.getQuantity()));
            	merchandizeTable.addCell("$" + String.format("%.2f", merchandizeItem.getPrice()));
            	merchandizeTotalPrice += merchandizeItem.getPrice();
            	totalprice += merchandizeItem.getPrice();
            	hasMerchandizeItems = true;
            }
          
        }

        if (hasServiceItems) {
        	document.add(new Paragraph("Services Taken"));
            serviceTable.setFontSize(12);
            document.add(serviceTable);
        }

        if (hasMerchandizeItems) {
        	document.add(new Paragraph("\n"));
        	document.add(new Paragraph("Merchandize Purchased"));
            merchandizeTable.setFontSize(12);
            document.add(merchandizeTable);
        }

        document.add(new Paragraph("\n"));

        double gstAmount = totalprice * 0.09; 
        totalprice += gstAmount;
        double discountAmount=0;
        if (invoice.getDiscount() < 1) {
        	 discountAmount = totalprice * invoice.getDiscount();
        	 totalprice -= discountAmount;
        } 
        
        Table summary = new Table(new float[] { 2, 2 });
        
        
        summary.useAllAvailableWidth();

     // Add key-value pairs
     summary.addCell(new Cell().add(new Paragraph("Service Total:")));
     summary.addCell(new Cell().add(new Paragraph("$" + String.format("%.2f", serviceTotalPrice))));

     summary.addCell(new Cell().add(new Paragraph("Merchandize Total:")));
     summary.addCell(new Cell().add(new Paragraph("$" + String.format("%.2f", merchandizeTotalPrice))));

     summary.addCell(new Cell().add(new Paragraph("Original Price:")));
     summary.addCell(new Cell().add(new Paragraph("$" + String.format("%.2f", totalprice))));

     summary.addCell(new Cell().add(new Paragraph("GST (9%):")));
     summary.addCell(new Cell().add(new Paragraph("$" + String.format("%.2f", gstAmount))));
     
     if(invoice.getDiscount()<1) {
     summary.addCell(new Cell().add(new Paragraph(invoice.getDiscountString() +" Discount:")));
     summary.addCell(new Cell().add(new Paragraph("$" + String.format("%.2f", discountAmount))));
     }
     
     summary.addCell(new Cell().add(new Paragraph("Grand Total:")));
     summary.addCell(new Cell().add(new Paragraph("$" + String.format("%.2f", totalprice))).setFontSize(14));

     // Add the summary table to the document
     document.add(summary);
        // Add auto-generated invoice disclaimer
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("This is an auto-generated invoice, no need for signature.").setFontSize(10));

        // Close the document (flushes the content to the byte array)
        document.close();

        return baos.toByteArray();
    }

    // Method to send email with PDF attachment
    public void sendEmailWithAttachment(String recipientEmail, String subject, String body, byte[] pdfBytes) throws MessagingException {
        // Set up email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Set up the session
        Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("spotlesscleaningservices.jad@gmail.com", "bnrd xzmf fwob pcrn"); 
            }
        });

        // Create the email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("spotlesscleaningservices.jad@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject(subject);

        // Create a MimeBodyPart to hold the email body
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(body);  // Set the body text here

        // Create a MimeBodyPart to hold the PDF attachment
        MimeBodyPart attachmentPart = new MimeBodyPart();
        ByteArrayDataSource source = new ByteArrayDataSource(pdfBytes, "application/pdf");
        attachmentPart.setDataHandler(new DataHandler(source));
        attachmentPart.setFileName("receipt_" + System.currentTimeMillis() + ".pdf");

        // Create a multipart message and add both the body text and the attachment
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);  // Add the body part
        multipart.addBodyPart(attachmentPart);  // Add the attachment part
        message.setContent(multipart);  // Set the content of the message

        // Send the email
        Transport.send(message);
    }
}
