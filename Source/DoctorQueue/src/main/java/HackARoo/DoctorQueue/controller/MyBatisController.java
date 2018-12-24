package HackARoo.DoctorQueue.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import HackARoo.DoctorQueue.service.MyBatisService;
import HackARoo.DoctorQueue.sms.SmsSender;
import HackARoo.DoctorQueue.view.DoctorQueueDoctors;
import HackARoo.DoctorQueue.view.DoctorQueueSmsCodes;
import HackARoo.DoctorQueue.view.DoctorQueueUsers;
import HackARoo.DoctorQueue.view.DoctorQueueWaitTimes;

// Rest Controller Annotation [A Specialized version of the @Controller annotation]
// It is a combination of @Controller+ @ResponseBody
// So that we no need to add @ResponseBody annotation to Request Mapping methods.
@RestController
public class MyBatisController {

	// Autowired annotation is a new style of Dependency Injection Drive
	@Autowired
	MyBatisService myBatisService;

	private static final Logger logger = Logger.getLogger(MyBatisController.class);
	private static final String ERROR_PAGE_VIEW = "exceptionpage";
	private static final String DOCTOR_QUEUE_LOGIN_PAGE = "doctorQueueLogin";
	private static final String DOCTOR_QUEUE_HOME_PAGE = "doctorQueueHome";
	@SuppressWarnings("serial")
	private static final List<String> userResponses = new ArrayList<String>() {
		{
			add("Y");
			add("N");
			add("W");
		}
	};

	/**
	 * 
	 * Home Page for the Doctor Queue
	 * 
	 */
	@RequestMapping(path = "/doctorQueueLogin", method = RequestMethod.GET)
	public ModelAndView doctorQueueLoginScreen(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		// Setting Error Page Initially, Later we will be changing it.
		modelAndView.setViewName(ERROR_PAGE_VIEW);
		try {
			HttpSession httpSession = request.getSession();
			if (httpSession != null && httpSession.getAttribute("phoneNumber") != null) {
				// If user session is already present, then navigate to home page
				// Calling Home Screen - Later
				return new ModelAndView("redirect:/doctorQueueHome");
			} else {
				// Leading to Login Page
				modelAndView.setViewName(DOCTOR_QUEUE_LOGIN_PAGE);
			}
		} catch (Exception exception) {
			logger.error("Exception occured while getting doctorQueue Login Page", exception);
		}
		return modelAndView;
	}

	/**
	 * 
	 * Home Page for the Doctor Queue
	 * 
	 */
	@RequestMapping(path = "/doctorQueueHome", method = RequestMethod.GET)
	public ModelAndView doctorQueueHomeScreen(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		// Setting Error Page Initially, Later we will be changing it.
		modelAndView.setViewName(ERROR_PAGE_VIEW);
		try {
			HttpSession httpSession = request.getSession();
			if (httpSession != null && httpSession.getAttribute("phoneNumber") != null) {
				// If user session is already present, then navigate to home page
				modelAndView.addObject("phoneNumber", (String) httpSession.getAttribute("phoneNumber"));
				String phoneNumber = (String) httpSession.getAttribute("phoneNumber");
				
				// Check if User already Exists - If Exists, Do Nothing
				// Else, Insert It.
				DoctorQueueUsers doctorQueueUsers = myBatisService.getUserDetails(phoneNumber);

				if (doctorQueueUsers == null) {
					// Insert to Queue Users Table
					myBatisService.insertUser(phoneNumber, "Y");
					String[] list = new String[2];
					list[0] = "Congrats, You are registered for DoctorQueue and you will be getting Queue Alerts";
					list[1] = phoneNumber;
					SmsSender.main(list);
				}

				// Get Inserted User Details again.
				doctorQueueUsers = myBatisService.getUserDetails(phoneNumber);
				modelAndView.addObject("alertStatus", doctorQueueUsers.getReceiveAlerts());
				
				// Fetch Doctor Details
				List<DoctorQueueDoctors> doctorQueueDoctors = new ArrayList<>();
				doctorQueueDoctors = myBatisService.fetchDoctorsList();
				
				// Iterate Doctor List and Fetch Each Doctors Wait Time
				// & Number of Visitors
				List<DoctorQueueWaitTimes> allDoctorQueueWaitTimes = new ArrayList<>();
				allDoctorQueueWaitTimes = myBatisService.fetchDoctorQueueDetails(phoneNumber);
				modelAndView.addObject("allDoctorQueueWaitTimesByPhn", allDoctorQueueWaitTimes);
				
				
				List<DoctorQueueDoctors> doctorQueuesFinal = new ArrayList<>();
				for(DoctorQueueDoctors doct: doctorQueueDoctors) {
					 // Fetch the Visitors for the Doctor
					Integer visitorCount = myBatisService.fetchVisitorsCount(doct.getDoctorId());
					// Fetch the total waiting time.
					Integer waitTime = myBatisService.fetchWaitTimes(doct.getDoctorId());
					
					doct.setNumberOfVisitors(visitorCount);
					doct.setVisitorsWaitTime(waitTime);
					doctorQueuesFinal.add(doct);
				}
				
				modelAndView.addObject("doctorQueueDoctors", doctorQueuesFinal);
				
				modelAndView.setViewName(DOCTOR_QUEUE_HOME_PAGE);
			} else if (request.getParameter("phoneNumber") != null
					&& request.getParameter("verificationCode") != null) {
				String phoneNumber = (String) request.getParameter("phoneNumber");
				String verficationCode = (String) request.getParameter("verificationCode");

				// Fetch Existing SmsCode Details that are inserted before by phone Number.
				DoctorQueueSmsCodes doctorQueueSmsCodes = myBatisService.fetchSmsCodeByNum(phoneNumber);

				if (doctorQueueSmsCodes != null && doctorQueueSmsCodes.getSmsCode().equals(verficationCode)) {
					httpSession.setAttribute("phoneNumber", phoneNumber);
					modelAndView.addObject("phoneNumber", (String) httpSession.getAttribute("phoneNumber"));

					// Check if User already Exists - If Exists, Do Nothing
					// Else, Insert It.
					DoctorQueueUsers doctorQueueUsers = myBatisService.getUserDetails(phoneNumber);

					if (doctorQueueUsers == null) {
						// Insert to Queue Users Table
						myBatisService.insertUser(phoneNumber, "Y");
						String[] list = new String[2];
						list[0] = "Congrats, You are registered for DoctorQueue and you will be getting Queue Alerts";
						list[1] = phoneNumber;
						SmsSender.main(list);
					}

					// Get Inserted User Details again.
					doctorQueueUsers = myBatisService.getUserDetails(phoneNumber);
					modelAndView.addObject("alertStatus", doctorQueueUsers.getReceiveAlerts());
					
					// Fetch Doctor Details
					List<DoctorQueueDoctors> doctorQueueDoctors = new ArrayList<>();
					doctorQueueDoctors = myBatisService.fetchDoctorsList();
					
					// Iterate Doctor List and Fetch Each Doctors Wait Time
					// & Number of Visitors
					List<DoctorQueueWaitTimes> allDoctorQueueWaitTimesByPhn = new ArrayList<>();
					allDoctorQueueWaitTimesByPhn = myBatisService.fetchDoctorQueueDetails(phoneNumber);
					modelAndView.addObject("allDoctorQueueWaitTimesByPhn", allDoctorQueueWaitTimesByPhn);
					
					List<DoctorQueueDoctors> doctorQueuesFinal = new ArrayList<>();
					for(DoctorQueueDoctors doct: doctorQueueDoctors) {
						 // Fetch the Visitors for the Doctor
						Integer visitorCount = myBatisService.fetchVisitorsCount(doct.getDoctorId());
						// Fetch the total waiting time.
						Integer waitTime = myBatisService.fetchWaitTimes(doct.getDoctorId());
						
						doct.setNumberOfVisitors(visitorCount);
						doct.setVisitorsWaitTime(waitTime);
						doctorQueuesFinal.add(doct);
					}
					
					modelAndView.addObject("doctorQueueDoctors", doctorQueuesFinal);
					
					modelAndView.setViewName(DOCTOR_QUEUE_HOME_PAGE);
				} else {
					modelAndView.addObject("isSmsVerify", true);
					modelAndView.addObject("smsVerifyNumber", phoneNumber);
					modelAndView.addObject("codeWrong", true);
					modelAndView.setViewName(DOCTOR_QUEUE_LOGIN_PAGE);
					return modelAndView;
				}
			} else {
				// Leading to Login Page
				return new ModelAndView("redirect:/doctorQueueLogin");
			}
		} catch (Exception exception) {
			logger.error("Exception occured while getting doctorQueue Home Page", exception);
		}
		return modelAndView;
	}

	/**
	 * 
	 * Send Sms verification code
	 * 
	 */
	@RequestMapping(path = "/getSmsVerificationCode", method = RequestMethod.GET)
	public ModelAndView sendSmsVerificationCode(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		// Setting Error Page Initially, Later we will be changing it.
		modelAndView.setViewName(ERROR_PAGE_VIEW);
		try {
			HttpSession httpSession = request.getSession();
			if (httpSession != null && httpSession.getAttribute("phoneNumber") != null) {
				// If user session is already present, then navigate to home page
				// Calling Home Screen - Later
				return new ModelAndView("redirect:/doctorQueueHome");
			} else if (request.getParameter("phoneNumber") != null) {
				String phoneNumber = (String) request.getParameter("phoneNumber");
				modelAndView.addObject("isSmsVerify", true);
				modelAndView.addObject("smsVerifyNumber", phoneNumber);

				// Check if an existing record is present & If present delete it.
				boolean isDelete = myBatisService.deleteSmsCodeIfPresent(phoneNumber);
				if (isDelete) {
					logger.info("Sms Code delete Status for Phone Number -> " + phoneNumber + " is : " + isDelete);
				}

				String randomCode = String.valueOf((int) (Math.random() * 9000) + 1000);
				String[] list = new String[2];
				list[0] = "Your Verification Code is : " + randomCode;
				list[1] = phoneNumber;
				boolean isInsert = myBatisService.insertSmsCode(phoneNumber, randomCode);
				if (isInsert) {
					logger.info("Sms Code Insert Status for Phone Number -> " + phoneNumber + " is : " + isInsert);
				}
				SmsSender.main(list);
				modelAndView.setViewName(DOCTOR_QUEUE_LOGIN_PAGE);
			} else {
				return new ModelAndView("redirect:/doctorQueueLogin");
			}
		} catch (Exception exception) {
			logger.error("Exception occured while getting doctorQueue Login Page", exception);
		}
		return modelAndView;
	}

	// Logout
	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpSession = request.getSession();
		if (httpSession != null && httpSession.getAttribute("phoneNumber") != null) {
			// Log User Out & remove Session
			httpSession.removeAttribute("phoneNumber");
		}
		return new ModelAndView("redirect:/doctorQueueLogin");
	}

	// Change Alert Status
	@RequestMapping(path = "/changeAlertStatus", method = RequestMethod.GET)
	public ModelAndView changeAlertStatus(HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpSession = request.getSession();
		if (httpSession != null && httpSession.getAttribute("phoneNumber") != null
				&& request.getParameter("alert_yes_no") != null) {
			String alertStatus = (String)request.getParameter("alert_yes_no");
			String phoneNumber = (String)httpSession.getAttribute("phoneNumber");
			
			String[] list = new String[2];
			list[0] = "You have Changed your Alert Status, Now your alert status is - "+alertStatus;
			list[1] = phoneNumber;
			SmsSender.main(list);
			
			myBatisService.updateUser(phoneNumber, alertStatus);
		}
		return new ModelAndView("redirect:/doctorQueueHome");
	}

	
	@RequestMapping(path = "/joinQueue", method = RequestMethod.GET)
	public ModelAndView joinQueue(HttpServletRequest request, HttpServletResponse response) {		
		ModelAndView modelAndView = new ModelAndView();
		// Setting Error Page Initially, Later we will be changing it.
		modelAndView.setViewName(ERROR_PAGE_VIEW);
		try {
			HttpSession httpSession = request.getSession();
			if (httpSession != null && httpSession.getAttribute("phoneNumber") != null) {
				String phoneNumber = (String)httpSession.getAttribute("phoneNumber");
				String doctorPhoneNumber = (String)request.getParameter("doctorNumber");
				String doctorId = (String)request.getParameter("doctorId");
				String doctorName = (String)request.getParameter("doctorName");				
				String reason = (String)request.getParameter("queueReason");
				String isYou = (String)request.getParameter("optradio");
				String relationship = (String)request.getParameter("relationShip");
				if(isYou != null && isYou.equalsIgnoreCase("Y")) {
					relationship = "Self";
				}
				
				Integer currentMaxWaitTime = myBatisService.fetchWaitTimes(Integer.parseInt(doctorId));
				Integer waitTimeNew = 0;
				if(currentMaxWaitTime == null) {
					waitTimeNew = 5;	
				} else {
					waitTimeNew = currentMaxWaitTime+5;
				}
				//Insert new User
				myBatisService.insertNewUserWaitTime(phoneNumber, doctorId, doctorName, reason, relationship,waitTimeNew);
				
				String[] listNew = new String[2];
				listNew[0] = "Hey Doctor : "+doctorName+ " A new patient joined the Queue with Id : "
						+ phoneNumber +"& Current Wait Time is : "+waitTimeNew;
				listNew[1] = doctorPhoneNumber;
				SmsSender.main(listNew);				
				
				DoctorQueueUsers doctorQueueUsers = myBatisService.getUserDetails(phoneNumber);
				if(doctorQueueUsers != null && doctorQueueUsers.getReceiveAlerts() != null
						&& doctorQueueUsers.getReceiveAlerts().equalsIgnoreCase("Y")) {
					String[] list = new String[2];
					list[0] = "You are added to Queue request, Doctor : "+doctorName+ " & Wait Time : "+waitTimeNew;
					list[1] = phoneNumber;
					SmsSender.main(list);	
				}				
				return new ModelAndView("redirect:/doctorQueueHome");
			} else {
				// Leading to Login Page
				modelAndView.setViewName(DOCTOR_QUEUE_LOGIN_PAGE);
			}
		} catch (Exception exception) {
			logger.error("Exception occured while getting Joining the Queue", exception);
		}
		return modelAndView;
	}
	
	@RequestMapping(path = "/sms", method = RequestMethod.POST)
	public void reciveSms(HttpServletRequest request, HttpServletResponse response) {
		String message = (String) request.getParameter("Body");
		String phoneNumber = (String) request.getParameter("From");

		String[] list = new String[2];
		if (message != null && userResponses.contains(message.toUpperCase())) {
			if (message.equalsIgnoreCase("Y")) {
				// Check if User already Exists - If Exists, Do Nothing
				// Else, Insert It and Opt in for Alerts
				DoctorQueueUsers doctorQueueUsers = myBatisService.getUserDetails(phoneNumber);

				if (doctorQueueUsers == null) {
					list[0] = "Congrats, You are registered for DoctorQueue and you will be getting Queue Alerts";
					// Insert to Queue Users Table
					myBatisService.insertUser(phoneNumber, "Y");
				} else if (doctorQueueUsers.getReceiveAlerts() != null) {
					if (doctorQueueUsers.getReceiveAlerts().equalsIgnoreCase("N")) {
						// Opt-In now
						myBatisService.updateUser(phoneNumber, "Y");
						list[0] = "Congrats, You will be recieving the alerts from now.";
					} else if (doctorQueueUsers.getReceiveAlerts().equalsIgnoreCase("Y")) {
						// Send a Msg
						list[0] = "Hey, You are already Opted-In to recieve alerts.";
					}
				} else {
					// Opt-In
					myBatisService.updateUser(phoneNumber, "Y");
					list[0] = "Congrats, You will be recieving the alerts from now.";
				}
			} else if (message.equalsIgnoreCase("N")) {
				// Opt out him from Alerts
				// Check if User already Exists - If Exists, Do Nothing
				// Else, Insert It and Opt in for Alerts
				DoctorQueueUsers doctorQueueUsers = myBatisService.getUserDetails(phoneNumber);

				if (doctorQueueUsers == null) {
					list[0] = "Congrats, You are registered for DoctorQueue and you will not be getting Queue Alerts";
					// Insert to Queue Users Table
					myBatisService.insertUser(phoneNumber, "N");
				} else if (doctorQueueUsers.getReceiveAlerts() != null) {
					if (doctorQueueUsers.getReceiveAlerts().equalsIgnoreCase("N")) {
						list[0] = "Hey, You are already Opted-Out to recieve alerts.";
					} else if (doctorQueueUsers.getReceiveAlerts().equalsIgnoreCase("Y")) {
						myBatisService.updateUser(phoneNumber, "N");
						list[0] = "You will stop recieving the alerts from now.";
					}
				} else {
					// Opt-In
					myBatisService.updateUser(phoneNumber, "N");
					list[0] = "You will stop recieving the alerts from now.";
				}

			} else if (message.equalsIgnoreCase("W")) {
				// Get his Current waiting time if any, else say no waiting time
				List<DoctorQueueWaitTimes> allDoctorQueueWaitTimesByPhn = new ArrayList<>();
				allDoctorQueueWaitTimesByPhn = myBatisService.fetchDoctorQueueDetails(phoneNumber);
				if(!(allDoctorQueueWaitTimesByPhn != null && allDoctorQueueWaitTimesByPhn.size()>0)) {
					list[0] = "You don't have any Waiting Requests.";
				} else {
					StringBuilder sb = new StringBuilder();
					sb.append("Your Waiting List Details :");
					for(DoctorQueueWaitTimes doct: allDoctorQueueWaitTimesByPhn) {
						sb.append("Doctor: "+doct.getDoctorName()+ ", Wait Time: "+doct.getWaitTime()+" ");
					}
					list[0] = sb.toString();
				}
			}
		} else {
			list[0] = "Invalid Response "
					+ "Please try below.Register and Opt for Alerts - Y, Opt out of Alerts - N, Get Current Waiting Time - W";
		}

		list[1] = phoneNumber;
		SmsSender.main(list);
	}

}
