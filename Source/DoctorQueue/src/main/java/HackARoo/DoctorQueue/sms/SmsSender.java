package HackARoo.DoctorQueue.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class SmsSender {
    // Find your Account Sid and Auth Token at twilio.com/console
	public static final String ACCOUNT_SID = "AC302c04901dcf0ce889b3a6cd6cf962d8";
    public static final String AUTH_TOKEN = "d857a22cec95e39648eca73558b572f5";

    public static void main(String[] args) {
        
    	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    	
    	if(args != null && args.length > 0) {
    		Message.creator(new PhoneNumber(args[1]),
    	            new PhoneNumber("+19412138572"), args[0]).create();
    	}else {
    	    Message message = Message.creator(new PhoneNumber("+19167559527"),
            new PhoneNumber("+19412138572"), 
            "Some Unknown Number tried to login").create();

        System.out.println(message.getSid());
    	}
    }
}
