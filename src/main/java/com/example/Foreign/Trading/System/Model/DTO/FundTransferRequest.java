package com.example.Foreign.Trading.System.Model.DTO;

import lombok.Data;

@Data
public class FundTransferRequest {
    private String importerAccNo;
    private String exporterAccNo;
    private double fund;
    private int tradeId;
}