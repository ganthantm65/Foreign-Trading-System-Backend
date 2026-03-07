package com.example.Foreign.Trading.System.Service;

import com.example.Foreign.Trading.System.Exceptions.ExporterNotFoundException;
import com.example.Foreign.Trading.System.Model.DTO.ProductDTO;
import com.example.Foreign.Trading.System.Model.DTO.ProductFullDetailsDTO;
import com.example.Foreign.Trading.System.Model.DTO.TradeFullDetailsDTO;
import com.example.Foreign.Trading.System.Model.Exporter;
import com.example.Foreign.Trading.System.Model.Product;
import com.example.Foreign.Trading.System.Model.Shipment;
import com.example.Foreign.Trading.System.Model.Trade;
import com.example.Foreign.Trading.System.Repository.ProductRepo;
import com.example.Foreign.Trading.System.Repository.ShipmentRepo;
import com.example.Foreign.Trading.System.Repository.TradeRepository;
import com.example.Foreign.Trading.System.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExporterService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private ShipmentRepo shipmentRepo;

    public void addProduct(ProductDTO productDTO) throws ExporterNotFoundException {

        Product product = new Product();

        product.setProductName(productDTO.getProductName());
        product.setCurrency(productDTO.getCurrency());
        product.setUnitPrice(productDTO.getUnitPrice());

        Exporter exporter = findExporterIdByNameAndCompany(
                productDTO.getExporter_name(),
                productDTO.getCompany()
        );

        product.setExporter(exporter);

        productRepo.save(product);
    }

    public List<ProductFullDetailsDTO> getAllProduct() {
        return productRepo.getCompleteProductDetails();
    }

    @Transactional
    public void shipProduct(int trade_id) {

        Trade trade = tradeRepository.findById(trade_id).orElseThrow();

        trade.setStatus(Trade.Status.SHIPPED);
        tradeRepository.save(trade);

        LocalDate deliveryDate = LocalDate.now().plusDays(3);

        Shipment shipment = new Shipment();
        shipment.setTrade(trade);
        shipment.setDeliveryDate(deliveryDate.toString());

        shipmentRepo.save(shipment);
    }

    public void deliverProduct(int trade_id){
        tradeRepository.updateTradeStatus(trade_id, Trade.Status.DELIVERED);
    }

    public Exporter findExporterIdByNameAndCompany(
            String userName,
            String companyName
    ) throws ExporterNotFoundException {

        Exporter exporter =
                userRepository.findExporterByNameAndCompany(userName, companyName);

        if (exporter == null) {
            throw new ExporterNotFoundException("Exporter not found");
        }

        return exporter;
    }

    public List<TradeFullDetailsDTO> getRequestedDTO(int user_id){
        return tradeRepository.findAllTradesByExporterId(user_id);
    }


    public void acceptTrade(int trade_id, Trade.Status status){
        tradeRepository.updateTradeStatus(trade_id,status);
    }
}