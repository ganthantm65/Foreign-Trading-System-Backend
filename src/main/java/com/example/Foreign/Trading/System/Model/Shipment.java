package com.example.Foreign.Trading.System.Model;

import com.example.Foreign.Trading.System.Repository.TradeRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shipment_id;

    @OneToOne
    @JoinColumn(name = "trade_id")
    private Trade trade;

    private String deliveryDate;
}
