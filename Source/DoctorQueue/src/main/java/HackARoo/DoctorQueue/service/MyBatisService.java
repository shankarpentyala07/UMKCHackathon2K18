package HackARoo.DoctorQueue.service;

import java.util.List;

import HackARoo.DoctorQueue.view.DoctorQueueDoctors;
import HackARoo.DoctorQueue.view.DoctorQueueSmsCodes;
import HackARoo.DoctorQueue.view.DoctorQueueUsers;
import HackARoo.DoctorQueue.view.DoctorQueueWaitTimes;

public interface MyBatisService {
	
	Boolean deleteSmsCodeIfPresent(String phoneNumber);

	boolean insertSmsCode(String phoneNumber, String randomCode);

	DoctorQueueSmsCodes fetchSmsCodeByNum(String phoneNumber);

	void insertUser(String phoneNumber, String recieveAlert);

	DoctorQueueUsers getUserDetails(String phoneNumber);

	void updateUser(String phoneNumber, String recieveAlerts);

	List<DoctorQueueDoctors> fetchDoctorsList();

	Integer fetchVisitorsCount(Integer doctorId);

	Integer fetchWaitTimes(Integer doctorId);

	List<DoctorQueueWaitTimes> fetchDoctorQueueDetails(String phoneNumber);

	void insertNewUserWaitTime(String phoneNumber, String doctorId, String doctorName, String reason,
			String relationship, Integer waitTimeNew);

	List<DoctorQueueUsers> getUsersWithRecieveStatus();
	
	
}
