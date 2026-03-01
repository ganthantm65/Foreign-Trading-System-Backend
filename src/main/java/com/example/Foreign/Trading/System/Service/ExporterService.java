package com.example.Foreign.Trading.System.Service;

import com.example.Foreign.Trading.System.Exceptions.ExporterNotFoundException;
import com.example.Foreign.Trading.System.Model.DTO.ProductDTO;
import com.example.Foreign.Trading.System.Model.DTO.ProductFullDetailsDTO;
import com.example.Foreign.Trading.System.Model.Exporter;
import com.example.Foreign.Trading.System.Model.Product;
import com.example.Foreign.Trading.System.Repository.ProductRepo;
import com.example.Foreign.Trading.System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExporterService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepository userRepository;

    public void addProduct(ProductDTO productDTO) throws ExporterNotFoundException {

        Product product = new Product();

        // FIXED: was setting null values before
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
}