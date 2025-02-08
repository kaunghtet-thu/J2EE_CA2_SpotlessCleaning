package bean;

public class Service {
	private int id;
    private String name;
    private String description;
    private int categoryId;
    private double price;


    // Constructor
    public Service(int id, String name, String description, int categoryId, double price) {
    	this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;

    }
    public Service(String name, String description, int categoryId, double price) {
    	this.name = name;
    	this.description = description;
    	this.categoryId = categoryId;
    	this.price = price;
    
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public String getName() {
    	return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

  
}
