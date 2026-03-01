package com.example.Foreign.Trading.System.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@DiscriminatorValue("BANKER")
public class Banker extends Users {
    private String country;

    @ManyToOne
    @JoinColumn(name= "bank_id")
    private Bank bank;
}
