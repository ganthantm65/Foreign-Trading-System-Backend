package com.example.Foreign.Trading.System.Controller;

import com.example.Foreign.Trading.System.Exceptions.*;
import com.example.Foreign.Trading.System.Model.Banker;
import com.example.Foreign.Trading.System.Model.DTO.LoginDTO;
import com.example.Foreign.Trading.System.Model.DTO.UserDTO;
import com.example.Foreign.Trading.System.Model.Exporter;
import com.example.Foreign.Trading.System.Model.Importer;
import com.example.Foreign.Trading.System.Model.Users;
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

    @PostMapping("/verify/email")
    public ResponseEntity<?> verifyEmail(@RequestBody HashMap<String,String> emailMap){
        HashMap<String,String> map=new HashMap<>();
        try{
            verifyOtpService.addVerifyOtp(emailMap.get("email"));
            map.put("message","OTP sent to your email");
            return ResponseEntity.status(200).body(map);
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
            response.put("message","Invalid Bank is specified");
            return ResponseEntity.status(404).body(response);
        } catch (NoAccountFoundException e){
            HashMap<String,String> response=new HashMap<>();
            response.put("message",e.getMessage());
            return ResponseEntity.status(404).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO){
        try{
            if(loginDTO.getRole().equals("IMPORTER")){
                Importer importer=userService.loginImporter(loginDTO.getEmail(),loginDTO.getPassword());
                importer.setPassword(null);
                return ResponseEntity.status(200).body(importer);
            }else if(loginDTO.getRole().equals("EXPORTER")){
                Exporter exporter=userService.loginExporter(loginDTO.getEmail(),loginDTO.getPassword());
                exporter.setPassword(null);
                return  ResponseEntity.status(200).body(exporter);
            } else if (loginDTO.getRole().equals("BANKER")) {
                Banker banker=userService.loginBanker(loginDTO.getEmail(), loginDTO.getPassword());
                banker.setPassword(null);
                return ResponseEntity.status(200).body(banker);
            }
            return ResponseEntity.status(404).body(null);
        }catch (UnauthorizedUserException e){
            Map<String,String> response=new HashMap<>();
            response.put("message","unauthorized user");
            return ResponseEntity.status(404).body(response);
        } catch (InvalidUserException e) {
            Map<String,String> response=new HashMap<>();
            response.put("message","invalid password");
            return ResponseEntity.status(404).body(response);
        }

    }
}
