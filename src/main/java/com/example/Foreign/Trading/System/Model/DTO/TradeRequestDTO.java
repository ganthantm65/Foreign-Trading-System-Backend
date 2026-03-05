package com.example.Foreign.Trading.System.Model.DTO;

import lombok.Data;

@Data
public class TradeRequestDTO {
    private int productId;
    private int quantity;
    private int importerId;

    public TradeRequestDTO(int productId,int quantity,int importerId){
        this.productId=productId;
        this.quantity=quantity;
        this.importerId=importerId;
    }
}
