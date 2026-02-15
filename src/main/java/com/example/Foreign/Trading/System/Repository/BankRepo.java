package com.example.Foreign.Trading.System.Repository;

import com.example.Foreign.Trading.System.Model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepo extends JpaRepository<Bank,Integer> {
    Bank findBankBySwiftCode(String swiftCode);
}
