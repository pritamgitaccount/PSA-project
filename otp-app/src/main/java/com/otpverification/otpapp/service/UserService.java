package com.otpverification.otpapp.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class for managing user-related data, including OTPs.
 */
@Service
public class UserService {

    // In-memory storage for associating phone numbers with OTPs
    private final Map<String, String> otpStorage = new HashMap<>();

    /**
     * Stores the provided OTP for the given phone number.
     *
     * @param phoneNumber The phone number to associate with the OTP.
     * @param otp         The OTP to store.
     */
    public void storeOtp(String phoneNumber, String otp) {
        otpStorage.put(phoneNumber, otp);
    }

    /**
     * Retrieves the stored OTP associated with the given phone number.
     *
     * @param phoneNumber The phone number for which to retrieve the stored OTP.
     * @return The stored OTP for the given phone number, or null if not found.
     */
    public String getStoredOtp(String phoneNumber) {
        return otpStorage.get(phoneNumber);
    }
}
