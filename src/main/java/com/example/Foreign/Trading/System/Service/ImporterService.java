package com.example.Foreign.Trading.System.Service;

import com.example.Foreign.Trading.System.Model.*;
import com.example.Foreign.Trading.System.Model.DTO.TradeFullDetailsDTO;
import com.example.Foreign.Trading.System.Model.DTO.TradeRequestDTO;
import com.example.Foreign.Trading.System.Repository.ProductRepo;
import com.example.Foreign.Trading.System.Repository.TradeRepository;
import com.example.Foreign.Trading.System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImporterService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepository userRepository;

    public void createTrade(TradeRequestDTO dto) {

        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Importer importer = (Importer) userRepository.findById(dto.getImporterId())
                .orElseThrow(() -> new RuntimeException("Importer not found"));

        double totalAmount = product.getUnitPrice() * dto.getQuantity();

        Trade trade = new Trade();
        trade.setProduct(product);
        trade.setQuantity(dto.getQuantity());
        trade.setTotalAmount(totalAmount);
        trade.setStatus(Trade.Status.PENDING);
        trade.setImporter(importer);

        tradeRepository.save(trade);
    }

    public List<TradeFullDetailsDTO> getCompleteTradeRequests(int user_id){
        return tradeRepository.findTradeDetailsById(user_id);
    }
}