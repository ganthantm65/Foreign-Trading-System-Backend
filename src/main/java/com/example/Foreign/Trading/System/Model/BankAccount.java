package com.example.Foreign.Trading.System.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String accNo;
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;
    private String balance;
}
