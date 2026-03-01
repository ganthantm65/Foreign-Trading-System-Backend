package com.example.Foreign.Trading.System.Controller;

import com.example.Foreign.Trading.System.Exceptions.ExporterNotFoundException;
import com.example.Foreign.Trading.System.Model.DTO.ProductDTO;
import com.example.Foreign.Trading.System.Model.DTO.ProductFullDetailsDTO;
import com.example.Foreign.Trading.System.Service.ExporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ExporterController {
    @Autowired
    private ExporterService exporterService;

    @PostMapping("/exporter/product/add")
    public ResponseEntity<?> addExporterProduct(@RequestBody ProductDTO productDTO){
        HashMap<String,String> response=new HashMap<>();
        try{
            exporterService.addProduct(productDTO);
            response.put("message","Product Added Successfully");
            return ResponseEntity.status(200).body(response);
        } catch (ExporterNotFoundException e) {
            response.put("message",e.getMessage());
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/exporter/product/get")
    public List<ProductFullDetailsDTO> getProductDetails(){
        return exporterService.getAllProduct();
    }
}
