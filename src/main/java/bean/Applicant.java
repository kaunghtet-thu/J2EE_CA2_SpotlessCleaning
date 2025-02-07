package bean;

public class Applicant extends Member{

	private int applicationId;
	private boolean agree;
	private boolean approve;
	
	public Applicant(int id, String name, int role, int applicationId, boolean agree, boolean approve) {
		super(id, name, role);
		this.applicationId = applicationId;
		this.agree = agree;
		this.approve = approve;
	}

	public int getApplicationId() {
		return applicationId;
	}

	public boolean isAgree() {
		return agree;
	}

	public boolean isApprove() {
		return approve;
	}
	
}
