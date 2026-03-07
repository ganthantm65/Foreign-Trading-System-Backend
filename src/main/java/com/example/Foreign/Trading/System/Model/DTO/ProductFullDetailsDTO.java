package com.example.Foreign.Trading.System.Model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor // Good practice to have for other mapping tools
public class ProductFullDetailsDTO {

    private Integer productId;
    private String currency;
    private Double unitPrice; // Changed from double to Double

    private String exporterName;
    private String companyName;
    private String country;

    private String bankName;
    private String swiftCode;

    public ProductFullDetailsDTO(
            Integer productId,
            String currency,
            Double unitPrice, // Changed to Double
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