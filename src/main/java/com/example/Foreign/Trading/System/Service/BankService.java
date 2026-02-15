package com.example.Foreign.Trading.System.Service;

import com.example.Foreign.Trading.System.Model.Bank;
import com.example.Foreign.Trading.System.Repository.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {
    @Autowired
    private BankRepo bankRepo;

    public Bank findBankBySwiftCode(String swiftCode){
        return bankRepo.findBankBySwiftCode(swiftCode);
    }
}
