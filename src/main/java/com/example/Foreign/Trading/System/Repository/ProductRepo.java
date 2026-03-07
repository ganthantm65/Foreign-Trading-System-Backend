package com.example.Foreign.Trading.System.Repository;

import com.example.Foreign.Trading.System.Model.DTO.ProductFullDetailsDTO;
import com.example.Foreign.Trading.System.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("""
    SELECT new com.example.Foreign.Trading.System.Model.DTO.ProductFullDetailsDTO(
        p.productId, 
        p.currency, 
        p.unitPrice, 
        e.userName, 
        e.companyName, 
        e.country, 
        b.bankName, 
        b.swiftCode
    )
    FROM Product p
    JOIN p.exporter e
    JOIN e.bank b
""")
    List<ProductFullDetailsDTO> getCompleteProductDetails();

    // ProductRepo.java
    @Query("""
    SELECT new com.example.Foreign.Trading.System.Model.DTO.ProductFullDetailsDTO(
        p.productId, 
        p.currency, 
        p.unitPrice, 
        e.userName, 
        e.companyName, 
        e.country, 
        b.bankName, 
        b.swiftCode
    )
    FROM Product p
    JOIN p.exporter e
    JOIN e.bank b
    WHERE e.userId = :exporterId
""")
    List<ProductFullDetailsDTO> findFullDetailsByExporterId(@Param("exporterId") Integer exporterId);
}