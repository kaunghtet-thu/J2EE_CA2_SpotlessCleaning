package bean;

import java.util.ArrayList;

public class MemberInfo extends Member {
	private String email;
	private String phone;
	private ArrayList<Address> address;
	
	public MemberInfo() {
		super();
		email = "";
		phone = "";	
		address = new ArrayList<Address>();
	}
	
	public MemberInfo(int id, String name, int role, String email, String phone, ArrayList<Address> address) {
		super(id, name, role);
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}
	
	public ArrayList<Address> getAddress () {
		return address;
	}
	
}
