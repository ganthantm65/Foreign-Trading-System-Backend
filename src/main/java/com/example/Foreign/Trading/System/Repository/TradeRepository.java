package com.example.Foreign.Trading.System.Repository;

import com.example.Foreign.Trading.System.Model.DTO.TradeFullDetailsDTO;
import com.example.Foreign.Trading.System.Model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Trade t SET t.status = :status WHERE t.tradeId = :id")
    void updateTradeStatus(@Param("id") Integer id,
                           @Param("status") Trade.Status status);

    @Query("""
    SELECT new com.example.Foreign.Trading.System.Model.DTO.TradeFullDetailsDTO(
        t.tradeId,
        t.quantity,
        t.totalAmount,
        t.status,
        p.productName,
        e.userName,
        i.userName,
        b.bankName
    )
    FROM Trade t
    JOIN t.product p
    JOIN p.exporter e
    JOIN e.bank b
    JOIN t.importer i
    WHERE t.status = :status AND e.userId=:user_id
""")
    List<TradeFullDetailsDTO> findTradeDetailsByStatusAndId(
            @Param("status") Trade.Status status,@Param("user_id") int user_id
    );

    @Query("""
        SELECT new com.example.Foreign.Trading.System.Model.DTO.TradeFullDetailsDTO(
            t.tradeId,
            t.quantity,
            t.totalAmount,
            t.status,
            p.productName,
            e.userName,
            i.userName,
            b.bankName
        )
        FROM Trade t
        JOIN t.product p
        JOIN p.exporter e
        JOIN e.bank b
        JOIN t.importer i
        WHERE i.userId = :user_id
        """)
    List<TradeFullDetailsDTO> findTradeDetailsById(
            @Param("user_id") int user_id
    );
}