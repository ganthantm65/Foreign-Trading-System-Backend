package com.example.Foreign.Trading.System.Controller;

import com.example.Foreign.Trading.System.Config.JwtUtil;
import com.example.Foreign.Trading.System.Exceptions.*;
import com.example.Foreign.Trading.System.Model.Users;
import com.example.Foreign.Trading.System.Model.DTO.LoginDTO;
import com.example.Foreign.Trading.System.Model.DTO.UserDTO;
import com.example.Foreign.Trading.System.Service.UserService;
import com.example.Foreign.Trading.System.Service.VerifyOtpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerifyOtpService verifyOtpService;

    @Autowired
    private JwtUtil jwtUtil;

    // ---------------- EMAIL VERIFY ----------------

    @PostMapping("/verify/email")
    public ResponseEntity<?> verifyEmail(@RequestBody Map<String,String> emailMap){

        Map<String,String> response = new HashMap<>();

        try{
            verifyOtpService.addVerifyOtp(emailMap.get("email"));

            response.put("message","OTP sent to email");

            return ResponseEntity.ok(response);

        }catch (Exception e){

            response.put("message",e.getMessage());

            return ResponseEntity.badRequest().body(response);
        }
    }

    // ---------------- OTP VERIFY ----------------

    @PostMapping("/verify/otp")
    public ResponseEntity<?> verifyOTP(@RequestBody Map<String,String> otpMap){

        Map<String,String> response = new HashMap<>();

        try{

            verifyOtpService.verifyOTP(
                    otpMap.get("email"),
                    Integer.parseInt(otpMap.get("otp"))
            );

            response.put("message","OTP verified successfully");

            return ResponseEntity.ok(response);

        }catch (OTPMismatchException e){

            response.put("message","OTP mismatch");

            return ResponseEntity.badRequest().body(response);

        }catch (OTPExpiredException e){

            response.put("message","OTP expired");

            return ResponseEntity.badRequest().body(response);
        }
    }

    // ---------------- REGISTER ----------------

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){

        try{

            Users user = userService.registerUser(userDTO);

            user.setPassword(null);

            return ResponseEntity.status(201).body(user);

        }catch (BankNotFoundException e){

            Map<String,String> response = new HashMap<>();
            response.put("message","Invalid bank");

            return ResponseEntity.badRequest().body(response);

        }catch (NoAccountFoundException e){

            Map<String,String> response = new HashMap<>();
            response.put("message",e.getMessage());

            return ResponseEntity.badRequest().body(response);
        }
    }

    // ---------------- LOGIN ----------------

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO){

        try{

            Users user = userService.loginUser(
                    loginDTO.getEmail(),
                    loginDTO.getPassword(),
                    loginDTO.getRole()
            );

            user.setPassword(null);

            String token = jwtUtil.generateToken(
                    user.getEmail(),
                    loginDTO.getRole()
            );

            Map<String,Object> response = new HashMap<>();

            response.put("user",user);
            response.put("token",token);

            return ResponseEntity.ok(response);

        }catch (InvalidUserException | UnauthorizedUserException e){

            Map<String,String> response = new HashMap<>();
            response.put("message",e.getMessage());

            return ResponseEntity.status(401).body(response);
        }
    }
}