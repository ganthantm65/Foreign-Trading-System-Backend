package com.example.Foreign.Trading.System.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("IMPORTER")
public class Importer extends Users {
    private String companyName;
    private String country;

    @ManyToOne
    @JoinColumn(name= "bank_id")
    private Bank bank;
}
