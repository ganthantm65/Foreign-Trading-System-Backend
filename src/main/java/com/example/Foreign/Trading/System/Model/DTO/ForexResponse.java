package com.example.Foreign.Trading.System.Model.DTO;

import lombok.Data;
import java.util.Map;

@Data
public class ForexResponse {

    private Map<String, Double> rates;
}