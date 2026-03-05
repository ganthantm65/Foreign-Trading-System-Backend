package com.example.Foreign.Trading.System.Model.DTO;

import com.example.Foreign.Trading.System.Model.Trade;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradeFullDetailsDTO {

    private Integer tradeId;
    private Integer quantity;
    private Double totalAmount;
    private Trade.Status status;

    private String productName;

    private String exporterName;
    private String importerName;

    private String bankName;
}