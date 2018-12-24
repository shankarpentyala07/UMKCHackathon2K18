package HackARoo.DoctorQueue.Scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import HackARoo.DoctorQueue.service.MyBatisService;
import HackARoo.DoctorQueue.sms.SmsSender;
import HackARoo.DoctorQueue.view.DoctorQueueUsers;
import HackARoo.DoctorQueue.view.DoctorQueueWaitTimes;

@Configuration
@EnableScheduling
public class QueueScheduler {

	@Autowired
	MyBatisService myBatisService;
	
	// Cron Job Scheduler
	@Scheduled(cron="0 0/1 9-18 * * MON-FRI")
	public void scheduleFixedRateTask() {
		// Fetch all the users who wants to receive the alerts
		List<DoctorQueueUsers> docQueueUsers= myBatisService.getUsersWithRecieveStatus();
		
		for(DoctorQueueUsers docQue: docQueueUsers) {
			if(docQue.getPhoneNumber() != null) {
				// Check if wait times are present and send the messages
				List<DoctorQueueWaitTimes> allDoctorQueueWaitTimesByPhn = new ArrayList<>();
				allDoctorQueueWaitTimesByPhn = myBatisService.fetchDoctorQueueDetails(docQue.getPhoneNumber());
				if(allDoctorQueueWaitTimesByPhn != null && allDoctorQueueWaitTimesByPhn.size()>0) {
					String[] list = new String[2];
					StringBuilder sb = new StringBuilder();
					sb.append("Your Waiting List Details :");
					// Iterate Wait List and send them in a Message for the respective user
					// who have wait times present
					for(DoctorQueueWaitTimes doct: allDoctorQueueWaitTimesByPhn) {
						if(doct.getWaitTime() > 0) {
							sb.append("Doctor: "+doct.getDoctorName()+ ", Wait Time: "+doct.getWaitTime()+" ");	
						}						
					}
					list[0] = sb.toString();
					list[1] = docQue.getPhoneNumber();
					SmsSender.main(list);
				}
			}
		}
	}
	
}
