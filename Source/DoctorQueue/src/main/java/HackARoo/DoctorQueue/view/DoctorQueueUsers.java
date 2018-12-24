package HackARoo.DoctorQueue.view;

import java.util.Date;

public class DoctorQueueUsers {

	private String phoneNumber;
	private Date dateCreated;
	private String receiveAlerts;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getReceiveAlerts() {
		return receiveAlerts;
	}

	public void setReceiveAlerts(String receiveAlerts) {
		this.receiveAlerts = receiveAlerts;
	}

}
