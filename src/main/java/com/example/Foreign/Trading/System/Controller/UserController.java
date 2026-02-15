package com.example.Foreign.Trading.System.Controller;

import com.example.Foreign.Trading.System.Exceptions.BankNotFoundException;
import com.example.Foreign.Trading.System.Exceptions.OTPExpiredException;
import com.example.Foreign.Trading.System.Exceptions.OTPMismatchException;
import com.example.Foreign.Trading.System.Model.UserDTO;
import com.example.Foreign.Trading.System.Model.Users;
import com.example.Foreign.Trading.System.Service.UserService;
import com.example.Foreign.Trading.System.Service.VerifyOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private VerifyOtpService verifyOtpService;

    @PostMapping("/verify/email")
    public ResponseEntity<?> verifyEmail(@RequestBody HashMap<String,String> emailMap){
        HashMap<String,String> map=new HashMap<>();
        try{
            verifyOtpService.addVerifyOtp(emailMap.get("email"));
            return ResponseEntity.status(200).body(map.put("message","OTP sent to your email"));
        }  catch (Exception e) {
            map.put("message",e.getMessage());
            return ResponseEntity.status(404).body(map);
        }
    }

    @PostMapping("/verify/otp")
    public ResponseEntity<?> verifyOTP(@RequestBody HashMap<String,String> otpMap){
        HashMap<String,String> map=new HashMap<>();
        try{
            verifyOtpService.verifyOTP(
                    otpMap.get("email"),
                    Integer.parseInt(otpMap.get("otp"))
            );
            map.put("message","OTP verified successfully");
            return ResponseEntity.status(200).body(map);
        }catch (OTPMismatchException e){
            map.put("message","OTP is mismatch");
            return ResponseEntity.status(404).body(map);
        } catch (OTPExpiredException e) {
            map.put("message","OTP is expired");
            return ResponseEntity.status(404).body(map);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try {
            Users users=userService.registerUser(userDTO);
            return ResponseEntity.status(201).body(users);
        } catch (BankNotFoundException e) {
            HashMap<String,String> response=new HashMap<>();
            return ResponseEntity.status(404).body(response.put("message","Invalid Bank is specified"));
        }
    }
}
