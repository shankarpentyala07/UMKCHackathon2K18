package HackARoo.DoctorQueue.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import HackARoo.DoctorQueue.dao.MyBatisDAO;
import HackARoo.DoctorQueue.view.DoctorQueueDoctors;
import HackARoo.DoctorQueue.view.DoctorQueueSmsCodes;
import HackARoo.DoctorQueue.view.DoctorQueueUsers;
import HackARoo.DoctorQueue.view.DoctorQueueWaitTimes;

@Service
public class MyBatisServiceImpl implements MyBatisService {

	@Autowired
	MyBatisDAO myBatisDao;

	private static final Logger logger = Logger.getLogger(MyBatisServiceImpl.class);

	@Override
	public Boolean deleteSmsCodeIfPresent(String phoneNumber) {
		boolean deleteStatus = false;
		try {
			myBatisDao.deleteSmsCodeIfPresent(phoneNumber);
			deleteStatus = true;
		} catch (DataAccessException dae) {
			// Always log the messages properly
			logger.error("Exception occured while fetching and deleting Sms code", dae);
		}
		return deleteStatus;
	}

	@Override
	public boolean insertSmsCode(String phoneNumber, String randomCode) {
		boolean insertStatus = false;
		try {
			myBatisDao.insertSmsCode(phoneNumber, randomCode);
			insertStatus = true;
		} catch (DataAccessException dae) {
			// Always log the messages properly
			logger.error("Exception occured while inserting Sms code", dae);
		}
		return insertStatus;
	}

	@Override
	public DoctorQueueSmsCodes fetchSmsCodeByNum(String phoneNumber) {
		DoctorQueueSmsCodes doctorQueueSmsCodes = new DoctorQueueSmsCodes();
		try {
			doctorQueueSmsCodes = myBatisDao.fetchSmsCodeByNum(phoneNumber);
		} catch (DataAccessException dae) {
			// Always log the messages properly
			logger.error("Exception occured while fetching Sms code", dae);
		}
		return doctorQueueSmsCodes;
	}

	@Override
	public void insertUser(String phoneNumber, String recieveAlert) {
		try {
			myBatisDao.insertUser(phoneNumber, recieveAlert);
		} catch (DataAccessException dae) {
			// Always log the messages properly
			logger.error("Exception occured while inserting Users", dae);
		}
	}

	@Override
	public DoctorQueueUsers getUserDetails(String phoneNumber) {
		DoctorQueueUsers doctorQueueUsers = new DoctorQueueUsers();
		try {
			doctorQueueUsers = myBatisDao.getUserDetails(phoneNumber);
		} catch (DataAccessException dae) {
			// Always log the messages properly
			logger.error("Exception occured while fetching User Details", dae);
		}
		return doctorQueueUsers;
	}

	@Override
	public void updateUser(String phoneNumber, String recieveAlerts) {
		try {
			myBatisDao.updateUser(phoneNumber, recieveAlerts);
		} catch (DataAccessException dae) {
			// Always log the messages properly
			logger.error("Exception occured while Updating Users", dae);
		}
	}

	@Override
	public List<DoctorQueueDoctors> fetchDoctorsList() {
		List<DoctorQueueDoctors> doctorQueueList = new ArrayList<>();
		try {
			doctorQueueList = myBatisDao.fetchDoctorsList();
		} catch (DataAccessException dae) {
			// Always log the messages properly
			logger.error("Exception occured while Fetching Doctors", dae);
		}
		return doctorQueueList;
	}

	@Override
	public Integer fetchVisitorsCount(Integer doctorId) {
		Integer count = 0;
		try {
			count = myBatisDao.fetchVisitorsCount(doctorId);
		} catch (DataAccessException dae) {
			// Always log the messages properly
			logger.error("Exception occured while Fetching Visitors Count", dae);
		}
		return count;
	}

	@Override
	public Integer fetchWaitTimes(Integer doctorId) {
		Integer waitTimes = 0;
		try {
			waitTimes= myBatisDao.fetchWaitTimes(doctorId);
		} catch (DataAccessException dae) {
			// Always log the messages properly
			logger.error("Exception occured while Fetching Wait Times", dae);
		}
		return waitTimes;
	}

	@Override
	public List<DoctorQueueWaitTimes> fetchDoctorQueueDetails(String phoneNumber) {
		List<DoctorQueueWaitTimes> doctorQueueDetByPhone = new ArrayList<>();
		try {
			doctorQueueDetByPhone= myBatisDao.fetchDoctorQueueDetails(phoneNumber);			
		} catch (DataAccessException dae) {
			// Always log the messages properly
			logger.error("Exception occured while Fetching Wait Times", dae);
		}
		return doctorQueueDetByPhone;
	}

	@Override
	public void insertNewUserWaitTime(String phoneNumber, String doctorId, String doctorName, String reason,
			String relationship, Integer waitTimeNew) {
			try {
				myBatisDao.insertNewUserWaitTime(phoneNumber, Integer.parseInt(doctorId), doctorName, reason, relationship,waitTimeNew);
			} catch (DataAccessException dae) {
				// Always log the messages properly
				logger.error("Exception occured while Fetching Wait Times", dae);
			}
	}

	@Override
	public List<DoctorQueueUsers> getUsersWithRecieveStatus() {
		List<DoctorQueueUsers> queueUsers = new ArrayList<>();
		try {
			queueUsers = myBatisDao.getUsersWithRecieveStatus("Y");
		}catch (DataAccessException dae) {
			// Always log the messages properly
			logger.error("Exception occured while Fetching Queue Users", dae);
		}
		return queueUsers;
	}

}
