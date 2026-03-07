package com.example.Foreign.Trading.System.Controller;

import com.example.Foreign.Trading.System.Exceptions.ExporterNotFoundException;
import com.example.Foreign.Trading.System.Model.DTO.ProductDTO;
import com.example.Foreign.Trading.System.Model.DTO.ProductFullDetailsDTO;
import com.example.Foreign.Trading.System.Model.DTO.TradeFullDetailsDTO;
import com.example.Foreign.Trading.System.Model.Trade;
import com.example.Foreign.Trading.System.Service.ExporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/exporter")
public class ExporterController {

    @Autowired
    private ExporterService exporterService;

    @PostMapping("/product/add")
    public ResponseEntity<?> addExporterProduct(@RequestBody ProductDTO productDTO){
        HashMap<String,String> response=new HashMap<>();

        try{
            exporterService.addProduct(productDTO);
            response.put("message","Product Added Successfully");
            return ResponseEntity.status(200).body(response);
        }
        catch (ExporterNotFoundException e){
            System.out.println(e);
            response.put("message",e.getMessage());
            return ResponseEntity.status(404).body(response);
        }
    }



    @GetMapping("/product/get")
    public List<ProductFullDetailsDTO> getProductDetails(){
        return exporterService.getAllProduct();
    }

    @GetMapping("/trade/get")
    public List<TradeFullDetailsDTO> getAllTradesForExporter(@RequestParam int user_id){
        return exporterService.getRequestedDTO(user_id);
    }

    @PutMapping("/accept/{trade_id}")
    public ResponseEntity<?> acceptTrade(@PathVariable int trade_id){

        exporterService.acceptTrade(trade_id, Trade.Status.ACCEPTED);

        HashMap<String,String> response=new HashMap<>();
        response.put("message","Trade Accepted Successfully");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/ship/{trade_id}")
    public ResponseEntity<?> shipProduct(@PathVariable int trade_id){

        exporterService.shipProduct(trade_id);

        HashMap<String,String> response=new HashMap<>();
        response.put("message","Product Shipped Successfully");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/deliver/{trade_id}")
    public ResponseEntity<?> deliverProduct(@PathVariable int trade_id){

        exporterService.deliverProduct(trade_id);

        HashMap<String,String> response=new HashMap<>();
        response.put("message","Product Delivered Successfully");

        return ResponseEntity.ok(response);
    }
}