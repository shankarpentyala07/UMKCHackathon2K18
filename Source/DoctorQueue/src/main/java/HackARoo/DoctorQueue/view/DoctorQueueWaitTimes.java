package HackARoo.DoctorQueue.view;

public class DoctorQueueWaitTimes {

	private String phoneNumber;
	private Integer waitTime;
	private String reason;
	private String relationship;
	private String doctorName;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(Integer waitTime) {
		this.waitTime = waitTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

}
