package com.example.Foreign.Trading.System.Service;

import com.example.Foreign.Trading.System.Exceptions.BankNotFoundException;
import com.example.Foreign.Trading.System.Model.*;
import com.example.Foreign.Trading.System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankService bankService;

    public Users registerUser(UserDTO userDTO) throws BankNotFoundException {

        Bank bank = bankService.findBankBySwiftCode(userDTO.getSwiftCode());

        if (bank == null) {
            throw new BankNotFoundException("Bank Not Found");
        }

        Users user;

        if ("IMPORTER".equalsIgnoreCase(userDTO.getUserType())) {

            Importer importer = new Importer();
            importer.setCompanyName(userDTO.getCompanyName());
            importer.setCountry(userDTO.getCountry());
            importer.setBank(bank);

            user = importer;

        } else {

            Exporter exporter = new Exporter();
            exporter.setCompanyName(userDTO.getCompanyName());
            exporter.setCountry(userDTO.getCountry());
            exporter.setBank(bank);

            user = exporter;
        }

        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());// important for verification

        return userRepository.save(user);
    }

}
