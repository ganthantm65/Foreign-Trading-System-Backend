package com.example.Foreign.Trading.System.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String productName;

    private String currency;

    private double unitPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Exporter exporter;
}
