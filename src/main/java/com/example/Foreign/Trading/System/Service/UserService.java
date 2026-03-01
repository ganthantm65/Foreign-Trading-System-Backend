package com.example.Foreign.Trading.System.Service;

import com.example.Foreign.Trading.System.Exceptions.BankNotFoundException;
import com.example.Foreign.Trading.System.Exceptions.InvalidUserException;
import com.example.Foreign.Trading.System.Exceptions.NoAccountFoundException;
import com.example.Foreign.Trading.System.Exceptions.UnauthorizedUserException;
import com.example.Foreign.Trading.System.Model.*;
import com.example.Foreign.Trading.System.Model.DTO.UserDTO;
import com.example.Foreign.Trading.System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankService bankService;

    public Users registerUser(UserDTO userDTO)
            throws BankNotFoundException, NoAccountFoundException {

        Bank bank = bankService.findBankBySwiftCode(userDTO.getSwiftCode());

        if (bank == null) {
            throw new BankNotFoundException("Bank Not Found");
        }

        if (!bankService.findAccountByAccountId(userDTO.getAccNo())) {
            throw new NoAccountFoundException("Account not found in the bank");
        }

        Users user;

        if ("IMPORTER".equalsIgnoreCase(userDTO.getUserType())) {

            Importer importer = new Importer();
            importer.setCompanyName(userDTO.getCompanyName());
            importer.setCountry(userDTO.getCountry());
            importer.setBank(bank);
            importer.setAccNo(userDTO.getAccNo());
            user = importer;

        } else if ("EXPORTER".equalsIgnoreCase(userDTO.getUserType())) {

            Exporter exporter = new Exporter();
            exporter.setCompanyName(userDTO.getCompanyName());
            exporter.setCountry(userDTO.getCountry());
            exporter.setBank(bank);
            exporter.setAccNo(userDTO.getAccNo());
            user = exporter;

        } else if ("BANKER".equalsIgnoreCase(userDTO.getUserType())) {

            Banker banker = new Banker();
            banker.setCountry(userDTO.getCountry());
            banker.setBank(bank);
            user = banker;

        } else {
            throw new IllegalArgumentException("Invalid User Type");
        }

        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return userRepository.save(user);
    }

    public Importer loginImporter(String email, String password)
            throws InvalidUserException, UnauthorizedUserException {

        Importer importer = userRepository.findImporterByEmail(email);

        if (importer == null) {
            throw new InvalidUserException("Importer not found");
        }

        if (!importer.getPassword().equals(password)) {
            throw new UnauthorizedUserException("Invalid Password");
        }

        return importer;
    }

    public Exporter loginExporter(String email, String password)
            throws InvalidUserException, UnauthorizedUserException {

        Exporter exporter = userRepository.findExporterByEmail(email);

        if (exporter == null) {
            throw new InvalidUserException("Exporter not found");
        }

        if (!exporter.getPassword().equals(password)) {
            throw new UnauthorizedUserException("Invalid Password");
        }

        return exporter;
    }

    public Banker loginBanker(String email, String password)
            throws InvalidUserException, UnauthorizedUserException {

        Banker banker = userRepository.findBankerByEmail(email);

        if (banker == null) {
            throw new InvalidUserException("Banker not found");
        }

        if (!banker.getPassword().equals(password)) {
            throw new UnauthorizedUserException("Invalid Password");
        }

        return banker;
    }
}