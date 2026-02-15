package com.example.Foreign.Trading.System.Repository;

import com.example.Foreign.Trading.System.Model.VerificationOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifyOtpRepo extends JpaRepository<VerificationOTP,Long> {
    VerificationOTP findByEmailAndOtp(String email, int otp);
}
