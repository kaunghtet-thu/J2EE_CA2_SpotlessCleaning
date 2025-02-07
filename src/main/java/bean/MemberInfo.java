package bean;

import java.util.ArrayList;

public class MemberInfo extends Member {
	private String email;
	private String phone;
	private ArrayList<Address> address;
	private char gender;
	
	public MemberInfo() {
		super();
		phone = "";	
		address = new ArrayList<Address>();
	}
	
	public MemberInfo(int id, String name, int role, String email, String phone, ArrayList<Address> address, char gender) {
		super(id, name, role);
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
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
	public char getGender() {
		return this.gender;
	}
	
}
