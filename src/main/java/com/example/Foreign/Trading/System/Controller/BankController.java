package com.example.Foreign.Trading.System.Controller;

import com.example.Foreign.Trading.System.Exceptions.BalanceDeclinedException;
import com.example.Foreign.Trading.System.Model.DTO.FundTransferRequest;
import com.example.Foreign.Trading.System.Model.DTO.TradeFullDetailsDTO;
import com.example.Foreign.Trading.System.Service.BankService;
import com.example.Foreign.Trading.System.Service.ExporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/bank")
@CrossOrigin
public class BankController {

    @Autowired
    private BankService bankService;


    // Transfer funds and update trade status
    @PostMapping("/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody FundTransferRequest request) {
        HashMap<String, String> response = new HashMap<>();
        try {
            bankService.verifyAndTransferFunds(
                    request.getImporterAccNo(),
                    request.getExporterAccNo(),
                    request.getFund(),
                    request.getTradeId()
            );
            response.put("message", "Fund transfer successful");
            return ResponseEntity.ok(response);
        } catch (BalanceDeclinedException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(400).body(response);
        } catch (Exception e) {
            response.put("message", "Something went wrong: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/trade/accepted")
    public List<TradeFullDetailsDTO> getAcceptedTrades(@RequestParam int user_id){
        return bankService.getAcceptedDTO(user_id);
    }
}