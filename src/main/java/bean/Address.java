package bean;

public class Address {
	private int id;
	private String address;
	private int memberId;
	
	public Address () {
		this.id = 0;
		this.memberId = 0;
		this.address = "";
	}
	
	public Address(int id, String address, int memberId) {
        this.id = id;
        this.address = address;
        this.memberId = memberId;
    }
	
	public Address(int id, String address) {
		this.id = id;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}
	
	
}