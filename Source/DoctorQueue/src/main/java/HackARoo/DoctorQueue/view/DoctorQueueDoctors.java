package HackARoo.DoctorQueue.view;

public class DoctorQueueDoctors {

	private Integer doctorId;
	private String doctorName;
	private String doctorSpecialization;
	private String doctorHospital;
	private String doctorURL;
	private String doctorPhone;
	private Integer numberOfVisitors;
	private Integer visitorsWaitTime;

	public String getDoctorPhone() {
		return doctorPhone;
	}

	public void setDoctorPhone(String doctorPhone) {
		this.doctorPhone = doctorPhone;
	}

	public Integer getVisitorsWaitTime() {
		return visitorsWaitTime;
	}

	public void setVisitorsWaitTime(Integer visitorsWaitTime) {
		this.visitorsWaitTime = visitorsWaitTime;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public Integer getNumberOfVisitors() {
		return numberOfVisitors;
	}

	public void setNumberOfVisitors(Integer numberOfVisitors) {
		this.numberOfVisitors = numberOfVisitors;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorSpecialization() {
		return doctorSpecialization;
	}

	public void setDoctorSpecialization(String doctorSpecialization) {
		this.doctorSpecialization = doctorSpecialization;
	}

	public String getDoctorHospital() {
		return doctorHospital;
	}

	public void setDoctorHospital(String doctorHospital) {
		this.doctorHospital = doctorHospital;
	}

	public String getDoctorURL() {
		return doctorURL;
	}

	public void setDoctorURL(String doctorURL) {
		this.doctorURL = doctorURL;
	}

}
