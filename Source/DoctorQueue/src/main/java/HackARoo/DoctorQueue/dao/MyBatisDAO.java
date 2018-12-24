package HackARoo.DoctorQueue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import HackARoo.DoctorQueue.view.DoctorQueueDoctors;
import HackARoo.DoctorQueue.view.DoctorQueueSmsCodes;
import HackARoo.DoctorQueue.view.DoctorQueueUsers;
import HackARoo.DoctorQueue.view.DoctorQueueWaitTimes;

@Mapper
public interface MyBatisDAO {

	void deleteSmsCodeIfPresent(@Param("smsCodePhoneNumber")String phoneNumber) throws DataAccessException;

	void insertSmsCode(@Param("smsCodePhoneNumberInsert")String phoneNumber, @Param("randomCode")String randomCode) throws DataAccessException;

	DoctorQueueSmsCodes fetchSmsCodeByNum(@Param("smsCodePhoneNumberFetch")String phoneNumber) throws DataAccessException;

	void insertUser(@Param("insertUserNumber")String phoneNumber, @Param("recieveAlerts")String recieveAlert) throws DataAccessException;

	DoctorQueueUsers getUserDetails(@Param("getUserByNumber")String phoneNumber) throws DataAccessException;

	void updateUser(@Param("updateUserNumber")String phoneNumber, @Param("recieveAlerts")String recieveAlerts) throws DataAccessException;

	List<DoctorQueueDoctors> fetchDoctorsList() throws DataAccessException;

	Integer fetchVisitorsCount(@Param("doctorId")Integer doctorId) throws DataAccessException;

	Integer fetchWaitTimes(@Param("doctorId")Integer doctorId) throws DataAccessException;

	List<DoctorQueueWaitTimes> fetchDoctorQueueDetails(@Param("queuePhoneNumber")String phoneNumber);

	void insertNewUserWaitTime(@Param("phoneNumber")String phoneNumber, @Param("doctorId")Integer doctorId,
			@Param("doctorName")String doctorName, @Param("reason")String reason,
			@Param("relationship")String relationship,@Param("waitTimeNew")Integer waitTimeNew) throws DataAccessException;

	List<DoctorQueueUsers> getUsersWithRecieveStatus(@Param("recieveStatus")String recieveStatus);

}
