package com.example.Foreign.Trading.System.Model.DTO;

import lombok.Data;

@Data
public class ProductDTO {
    private String productName;

    private String currency;

    private double unitPrice;

    private String exporter_name;

    private String company;
}
