package com.otpverification.otpapp.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    @Value("${twilio.accountSid}")
    public String accountSID;

    @Value("${twilio.authToken}")
    public String authToken;

    @Value("${twilio.phoneNumber}")
    public String twilioPhoneNo;

    @Autowired
    private UserService userService; // Inject your user service or repository here

    public String generateOtp(String to) {
        int otp = new Random().nextInt(900000) + 100000;
        sendOtp(to, String.valueOf(otp));
        // Store the generated OTP in your database (using userService or repository)
        userService.storeOtp(to, String.valueOf(otp));
        return String.valueOf(otp);
    }

    public boolean sendOtp(String phoneNumber, String otp) {
        Twilio.init(accountSID, authToken);

        Message message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(twilioPhoneNo),
                "Your OTP is: " + otp
        ).create();

        return message.getStatus().equals(Message.Status.SENT);
    }

    public boolean verifyOTP(String phoneNumber, String enteredOTP) {
        // Retrieve stored OTP for the given phone number from the database
        String storedOTP = userService.getStoredOtp(phoneNumber);

        // Compare entered OTP with stored OTP
        return enteredOTP.equals(storedOTP);
    }
}
