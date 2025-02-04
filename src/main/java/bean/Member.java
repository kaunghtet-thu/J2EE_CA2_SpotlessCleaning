package bean;

public class Member {
	
	private int id;
	private String name;
	private int role;
	
	public Member() {
		this.id = 0;
		this.name = "";
		this.role = 0;
	}
	public Member(int id, String name, int role) {
		this.id = id;
		this.name = name;
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getRole() {
		return role;
	}



	
}
