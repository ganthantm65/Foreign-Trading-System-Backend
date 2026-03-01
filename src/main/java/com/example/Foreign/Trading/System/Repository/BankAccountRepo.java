package com.example.Foreign.Trading.System.Repository;

import com.example.Foreign.Trading.System.Model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepo extends JpaRepository<BankAccount,Integer> {

    boolean existsByAccNo(String accNo);

}