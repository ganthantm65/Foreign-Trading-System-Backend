package com.example.Foreign.Trading.System.Service;

import com.example.Foreign.Trading.System.Exceptions.*;
import com.example.Foreign.Trading.System.Model.*;
import com.example.Foreign.Trading.System.Model.DTO.UserDTO;
import com.example.Foreign.Trading.System.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankService bankService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ---------------- REGISTER ----------------

    public Users registerUser(UserDTO userDTO)
            throws BankNotFoundException, NoAccountFoundException {

        Bank bank = bankService.findBankBySwiftCode(userDTO.getSwiftCode());

        if(bank == null){
            throw new BankNotFoundException("Bank not found");
        }

        if(bankService.findAccountByAccountId(userDTO.getAccNo()) == null){
            throw new NoAccountFoundException("Account not found in bank");
        }

        Users user;

        if("IMPORTER".equalsIgnoreCase(userDTO.getUserType())){

            Importer importer = new Importer();

            importer.setCompanyName(userDTO.getCompanyName());
            importer.setCountry(userDTO.getCountry());
            importer.setAccNo(userDTO.getAccNo());
            importer.setBank(bank);

            user = importer;

        }
        else if("EXPORTER".equalsIgnoreCase(userDTO.getUserType())){

            Exporter exporter = new Exporter();

            exporter.setCompanyName(userDTO.getCompanyName());
            exporter.setCountry(userDTO.getCountry());
            exporter.setAccNo(userDTO.getAccNo());
            exporter.setBank(bank);

            user = exporter;

        }
        else if("BANKER".equalsIgnoreCase(userDTO.getUserType())){

            Banker banker = new Banker();

            banker.setCountry(userDTO.getCountry());
            banker.setBank(bank);

            user = banker;

        }
        else{
            throw new IllegalArgumentException("Invalid user type");
        }

        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());

        // encrypt password
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userRepository.save(user);
    }

    // ---------------- LOGIN ----------------

    public Users loginUser(String email, String password, String role)
            throws InvalidUserException, UnauthorizedUserException {

        Users user = null;

        if("IMPORTER".equalsIgnoreCase(role)){
            user = userRepository.findImporterByEmail(email);
        }
        else if("EXPORTER".equalsIgnoreCase(role)){
            user = userRepository.findExporterByEmail(email);
        }
        else if("BANKER".equalsIgnoreCase(role)){
            user = userRepository.findBankerByEmail(email);
        }

        if(user == null){
            throw new InvalidUserException("User not found");
        }

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new UnauthorizedUserException("Invalid password");
        }

        return user;
    }
}