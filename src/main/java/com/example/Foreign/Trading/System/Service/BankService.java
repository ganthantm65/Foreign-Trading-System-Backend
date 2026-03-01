package com.example.Foreign.Trading.System.Service;

import com.example.Foreign.Trading.System.Model.Bank;
import com.example.Foreign.Trading.System.Repository.BankAccountRepo;
import com.example.Foreign.Trading.System.Repository.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {
    @Autowired
    private BankRepo bankRepo;

    @Autowired
    private BankAccountRepo bankAccountRepo;

    public Bank findBankBySwiftCode(String swiftCode){
        return bankRepo.findBankBySwiftCode(swiftCode);
    }

    public boolean findAccountByAccountId(String accNo){
        return bankAccountRepo.existsByAccNo(accNo);
    }
}
