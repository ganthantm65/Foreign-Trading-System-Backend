package com.example.Foreign.Trading.System.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("IMPORTER")
public class Importer extends Users {
    private String companyName;
    private String country;
    private String accNo;

    @ManyToOne
    @JoinColumn(name= "bank_id")
    private Bank bank;
}
