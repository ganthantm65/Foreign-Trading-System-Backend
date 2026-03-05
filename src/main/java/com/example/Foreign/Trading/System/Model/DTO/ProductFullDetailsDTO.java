package com.example.Foreign.Trading.System.Model.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductFullDetailsDTO {

    private Integer productId;
    private String currency;
    private double unitPrice;

    private String exporterName;
    private String companyName;
    private String country;

    private String bankName;
    private String swiftCode;

    // ✅ Constructor MUST match JPQL exactly (order + type)
    public ProductFullDetailsDTO(
            Integer productId,
            String currency,
            double unitPrice,
            String exporterName,
            String companyName,
            String country,
            String bankName,
            String swiftCode
    ) {
        this.productId = productId;
        this.currency = currency;
        this.unitPrice = unitPrice;
        this.exporterName = exporterName;
        this.companyName = companyName;
        this.country = country;
        this.bankName = bankName;
        this.swiftCode = swiftCode;
    }
}