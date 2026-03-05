package com.example.Foreign.Trading.System.Controller;

import com.example.Foreign.Trading.System.Model.DTO.TradeFullDetailsDTO;
import com.example.Foreign.Trading.System.Model.DTO.TradeRequestDTO;
import com.example.Foreign.Trading.System.Model.Trade;
import com.example.Foreign.Trading.System.Service.ImporterService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ImporterController {
    @Autowired
    private ImporterService importerService;

    @PostMapping("/create/trade")
    public ResponseEntity<?> createTradeOrder(@RequestBody TradeRequestDTO trade){
        try{
            importerService.createTrade(trade);
            HashMap<String,String> map=new HashMap<>();
            map.put("message","Trade Created Successfully");
            return ResponseEntity.status(201).body(map);
        } catch (Exception e) {
            HashMap<String,String> map=new HashMap<>();
            map.put("message",e.getMessage());
            return ResponseEntity.status(404).body(map);
        }
    }

    @GetMapping("/get/trade")
    public List<TradeFullDetailsDTO> getCompleteTrades(@RequestParam int user_id){
        return importerService.getCompleteTradeRequests(user_id);
    }
}
