package bean;

public class Role {
	private int id;
	private String name;
	
	public Role() {
		this.id=0;
		this.name="";
	}
	
	public Role(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	
}
