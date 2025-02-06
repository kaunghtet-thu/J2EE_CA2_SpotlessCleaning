<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bean.*" %>
<%@ page import="java.util.*" %>
<%@ page import="DAO.*"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SPOTLESS</title>
  <style>
    h1,h2 {
        text-align: center;
        font-size: 1.5em;
        margin-top: 20px;
        color: #333;
    }
    p{
     text-align: center;
    }

    .center {
    text-align: center;
  
    padding: 20px;
	}
	
	.feedback-list {
	    display: card;
	    flex-direction: column;
	    gap: 10px;
	    max-width: 400px;
	    margin: 0 auto;
	}
	
	.carousel-item {
		text-align: center;
	    border: 1px solid #ddd;
	    border-radius: 5px;
	    background-color:#b3dee5;
	    padding: 10px;
	    transition: background-color 0.3s, transform 0.2s;
	    cursor: pointer;
	}
	
	.feedback-item:hover {
	    transform: translateY(-2px);
	}
	.editErr{
	 	color: red;
	    background-color: #fdecea;
	    border: 1px solid red;
	  }
	  .editSuccess {
	     color: green;
	     background-color: #e7f9e7;
	     border: 1px solid green;
	  }
	 #feedbackCarousel {
	    max-width: 40vw; 
	    margin: 0 auto; 
	}
	.edit-button {
    padding: 5px;
    margin: 0;
    font-size: 14px;
	}
	.container{
	text-align: center;
	margin-top: 10px;
	max-width: 40vw;
	}
	
	.welcome-content {
		max-width: 40vw;
		margin: 0 auto;
	}
	.edit-button {
            font-size: 14px;
            padding: 5px 10px;
            border: 1px solid grey;
            background-color: #c5d1ba;
            cursor: pointer;
            transition: background-color 0.3s, color 0.3s;
        }
        .edit-button:hover {
            background-color: #4cae4c;
            color: white;
        }
        
      .discounts {
        text-align: center;
        margin-top: 10px;
    }
    .discounts-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        gap: 20px;
        padding: 20px;
    }
    .discount-card {
        width: 300px;
        border: 1px solid #ddd;
        border-radius: 10px;
        padding: 15px;
        box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
        text-align: center;
        background: white;
        transition: transform 0.2s ease-in-out;
    }
    .discount-card:hover {
        transform: scale(1.05);
    }
    .discount-title {
        font-size: 18px;
        font-weight: bold;
        color: #333;
    }
    .discount-percent {
        color: red;
        font-size: 22px;
        font-weight: bold;
        margin: 10px 0;
    }
    .service-name {
        font-size: 16px;
        color: #666;
    }
    .card-buttons {
        margin-top: 10px;
    }
    .card-buttons button {
        padding: 8px 12px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 14px;
    }
    .add-to-cart {
        background-color: #ffa101;
        color: white;
    }
    .add-to-cart:hover {
        color: white;
    	background-color: #fae6b1;
    }
  
	

</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
<%@include file="./assets/header.jsp" %>
<%
	IndexDAO indexDao = new IndexDAO();
	ArrayList<String> contents = indexDao.getContent();
	 String editErr = request.getParameter("errorCode");
	    String editSuccess = request.getParameter("successCode");
	    if (editErr != null && editErr.equals("editErr")) {
		
	%>
		<div class="editErr">
			<p>Error editing the selected field</p>
		</div>
		<%} 
		
		if (editSuccess != null && editSuccess.equals("editSuccess")) {

		%>
		<div class="editSuccess">
			<p>Editing the selected field successful</p>
		</div>
		<%} %>

<div class="container">
<h1>
    <%= contents.get(0) %>
    <% if (isAdmin) { %>
        <form action="editContent.jsp" method="post" style="display:inline;">
            <input type="hidden" name="contentId" value="1">
            <input type="hidden" name="content" value ="<%=contents.get(0) %>">
            <button type="submit" class="edit-button">Edit</button>
        </form>
    <% } %>
</h1>


<p class="welcome-content">
    <%= contents.get(1) %>
    <% if (isAdmin) { %>
        <form action="editContent.jsp" method="post" style="display:inline;">
            <input type="hidden" name="contentId" value="2"> <!-- Adjust the index here -->
            <input type="hidden" name="content" value="<%= contents.get(1) %>">
            <button type="submit" class="edit-button" style="float: right;">Edit</button>
        </form>
    <% } %>
</p>

 <div class="center">
	<h2>
	    <%= contents.get(2) %>
	    <% if (isAdmin) { %>
	        <form action="editContent.jsp" method="post" style="display:inline;">
	            <input type="hidden" name="contentId" value="3"> <!-- Adjust the index here -->
	            <input type="hidden" name="content" value="<%= contents.get(2) %>">
	            <button type="submit" class="edit-button">Edit</button>
	        </form>
	    <% } %>
	</h2>
    <%
    
        FeedbackDAO feedbackDao = new FeedbackDAO();
        ArrayList<Feedback> feedbacks = feedbackDao.getFeedBackByDisplayTrue();
        
        if (!feedbacks.isEmpty()) {
    %>
		<div id="feedbackCarousel" class="carousel slide" data-bs-ride="carousel">
			  <div class="carousel-inner">
			    <% 
			      // Check if the feedback list is empty to prevent errors
			      if (feedbacks != null && !feedbacks.isEmpty()) {
			        int index = 0; // For tracking the active class for the first item
			        for (Feedback feedback : feedbacks) { 
			    %>
			      <div class="carousel-item <%= index == 0 ? "active" : "" %>">
			        <div class="feedback-item">
			         <p>
			          <b>
			            <%= feedbackDao.getServiceNameOfaFeedback(feedback.getId())[0] %>
			          </b><br>
			         
			            <%
			                int rating = feedback.getRating();
			                for (int i = 0; i < rating; i++) {
			                    out.print("★");  // Filled star
			                }
			                for (int i = rating; i < 5; i++) {
			                    out.print("☆");  // Empty star
			                }
			            %><br>
			         
			          Service taken: <%= feedbackDao.getServiceNameOfaFeedback(feedback.getId())[1] %></p>
			          <h2>"<%= feedback.getComments() %>"</h2>
			          <% if (isAdmin){ %>
				       <div>
						    <form action="DisplayFalse" method="POST">
						         <input type="hidden" name="feedbackId" value="<%=feedback.getId() %>">
						        <button type="submit" class="edit-button">Remove this from display</button>
						    </form>
						</div>
						<%} %>
			        </div>
			        
			      </div>
			    <% 
			        index++; 
			      }
			        if (isAdmin) { 
			        	%>
			        	    <div class="carousel-item">
			        	        <div class="feedback-item admin-feedback-item">
			        	            <h3>Add More Feedbacks</h3>
			        	            <a href="<%=request.getContextPath()%>/admin/feedback.jsp" class="edit-button">Add More Feedback</a>
			        	        </div>
			        	    </div>
			        	<% } 
			    } else { 
			    %>
			      <p>No feedback available.</p>
			    <% } %>
			  </div>

			  <button class="carousel-control-prev" type="button" data-bs-target="#feedbackCarousel" data-bs-slide="prev">
			    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
			    <span class="visually-hidden">Previous</span>
			  </button>
			  <button class="carousel-control-next" type="button" data-bs-target="#feedbackCarousel" data-bs-slide="next">
			    <span class="carousel-control-next-icon" aria-hidden="true"></span>
			    <span class="visually-hidden">Next</span>
			  </button>
			</div>

    
    <%
        } else {
    %>
    <i style="text-align: center;">No Feedbacks to display</i>
    <%
        }
    %>
    
     <% 
    DiscountDAO disDao = new DiscountDAO();
    ServiceDAO serviceDao = new ServiceDAO();
    List<Discount> discounts =  disDao.getAllDiscounts();
    double mostAmount = 0.0;
    for (Discount discount : discounts) {
        if (discount.getDiscount() > mostAmount) {
            mostAmount = discount.getDiscount();
        }
    }
    int upTo = (int)(mostAmount*100);

   if (mostAmount > 0.0) {%>
	 <div class="discounts">
	    <h2 style="color: red; font-weight: bold; text-align: center;">
	        Up to <%= upTo %>% OFF!!
	    </h2>
	    <div class="discounts-container">
	        <%  
	            for (Discount discount : discounts) {
	                Service service = serviceDao.getServiceById(discount.getServiceId());
	        %>
	        <div class="discount-card">
	            <p class="discount-title"><%= discount.getDiscountName() %></p>
	            <p class="discount-percent"><%= (int)(discount.getDiscount()*100) %>% OFF</p>
	            <p class="service-name">On <%= service.getName() %></p>
	            <div class="card-buttons">
	            <%if (isMember){ %>
	                <form action="./AddToCart" method="post" style="display:inline;">
	                    <input type="hidden" name="serviceId" value="<%= service.getId() %>">
	                    <button type="submit" class="add-to-cart">Add to Cart</button>
	                </form>
	            <%} %>
	            </div>
	        </div>
	        <% } %>
	    </div>
	</div>
	   
<%	   
   }
%>
    
</div>
<div>
</div>
</div>
<%@ include file="./assets/footer.html" %>
</body>
</html>