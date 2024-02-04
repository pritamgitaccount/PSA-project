package com.otpverification.otpapp.controller;

import com.otpverification.otpapp.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestParam String phoneNumber) {
        System.out.println("+" + phoneNumber);
        String otp = otpService.generateOtp( phoneNumber);
        // Store the otp and associate it with the user in your db
        return ResponseEntity.ok("OTP sent successfully to: " + phoneNumber);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp) {
        // Retrieve the stored OTP for the user from the db and compare it with the submitted otp
        // If they match, the otp is valid
        try {
            boolean isVerified = otpService.verifyOTP(phoneNumber, otp);
            if (isVerified) {
                return ResponseEntity.ok("OTP verified successfully.");
            } else {
                return ResponseEntity.badRequest().body("OTP verification failed.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("OTP verification failed.");
        }
    }
}
