package com.example.Foreign.Trading.System.Service;

import com.example.Foreign.Trading.System.Exceptions.OTPExpiredException;
import com.example.Foreign.Trading.System.Exceptions.OTPMismatchException;
import com.example.Foreign.Trading.System.Model.Users;
import com.example.Foreign.Trading.System.Model.VerificationOTP;
import com.example.Foreign.Trading.System.Repository.UserRepository;
import com.example.Foreign.Trading.System.Repository.VerifyOtpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class VerifyOtpService {
    @Autowired
    private VerifyOtpRepo verifyOtpRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public void addVerifyOtp(String email){
        try{
            int otp = this.generateOtp();

            String htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                </head>
                <body style="font-family: Arial, sans-serif; background-color:#f4f4f4; padding:20px;">
                    
                    <div style="max-width:600px; margin:auto; background:white; padding:30px; border-radius:10px; box-shadow:0 0 10px rgba(0,0,0,0.1);">
                        
                        <h2 style="color:#2c3e50; text-align:center;">
                            Foreign Trading System
                        </h2>
                        <p style="font-size:16px; color:#555;">
                            Dear User,
                        </p>
                        <p style="font-size:16px; color:#555;">
                            Your One-Time Password (OTP) for email verification is:
                        </p>                
                        <div style="text-align:center; margin:30px 0;">
                            <span style="font-size:28px; letter-spacing:5px; font-weight:bold; color:white; background:#2e86de; padding:12px 25px; border-radius:8px;">
                                """ + otp + """
                            </span>
                        </div>             
                        <p style="font-size:14px; color:#777;">
                            This OTP is valid for 10 minutes. Please do not share it with anyone.
                        </p>                
                        <hr>                
                        <p style="font-size:12px; color:#999; text-align:center;">
                            © 2026 Foreign Trading System. All rights reserved.
                        </p>                
                    </div>  
                </body>
                </html>
                """;

            emailService.sendHtmlMail(email,"OTP Verification",htmlContent);

            VerificationOTP verificationOTP=new VerificationOTP();
            verificationOTP.setEmail(email);
            verificationOTP.setOtp(otp);
            verificationOTP.setLocalDateTime(LocalDateTime.now().plusMinutes(10));

            verifyOtpRepo.save(verificationOTP);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void verifyOTP(String email,int otp) throws OTPMismatchException,OTPExpiredException {
        VerificationOTP verificationOTP=verifyOtpRepo.findByEmailAndOtp(email,otp);

        if (verificationOTP==null){
            throw new OTPMismatchException("OTP is mismatch");
        }

        if(verificationOTP.getLocalDateTime().isBefore(LocalDateTime.now())){
            throw new OTPExpiredException("OTP is expired");
        }
    }

    public int generateOtp(){
        Random random=new Random();

        return 1000+random.nextInt(9000);
    }
}
