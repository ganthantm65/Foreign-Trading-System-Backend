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

    // ------------------- Update trade status -------------------
    @Transactional
    @Modifying
    @Query("UPDATE Trade t SET t.status = :status WHERE t.tradeId = :id")
    void updateTradeStatus(@Param("id") Integer id,
                           @Param("status") Trade.Status status);

    // ------------------- Fetch ALL trades for an exporter -------------------
    @Query("""
    SELECT new com.example.Foreign.Trading.System.Model.DTO.TradeFullDetailsDTO(
        t.tradeId,
        t.quantity,
        t.totalAmount,
        t.status,
        p.productName,
        e.userName,
        i.userName,
        b.bankName,
        i.accNo,
        e.accNo
    )
    FROM Trade t
    JOIN t.product p
    JOIN p.exporter e
    JOIN e.bank b
    JOIN t.importer i
    WHERE e.userId = :user_id
    """)
    List<TradeFullDetailsDTO> findAllTradesByExporterId(@Param("user_id") int user_id);

    // ------------------- Fetch trades for importer -------------------
    @Query("""
    SELECT new com.example.Foreign.Trading.System.Model.DTO.TradeFullDetailsDTO(
        t.tradeId,
        t.quantity,
        t.totalAmount,
        t.status,
        p.productName,
        e.userName,
        i.userName,
        b.bankName,
        i.accNo,
        e.accNo
    )
    FROM Trade t
    JOIN t.product p
    JOIN p.exporter e
    JOIN e.bank b
    JOIN t.importer i
    WHERE i.userId = :user_id
    """)
    List<TradeFullDetailsDTO> findTradesByImporterId(@Param("user_id") int user_id);

    // ------------------- Fetch accepted trades for a banker -------------------
    @Query("""
    SELECT new com.example.Foreign.Trading.System.Model.DTO.TradeFullDetailsDTO(
        t.tradeId,
        t.quantity,
        t.totalAmount,
        t.status,
        p.productName,
        e.userName,
        i.userName,
        b.bankName,
        i.accNo,
        e.accNo
    )
    FROM Trade t
    JOIN t.product p
    JOIN p.exporter e
    JOIN t.importer i
    JOIN i.bank b
    JOIN com.example.Foreign.Trading.System.Model.Banker bk
      ON bk.bank = b
    WHERE t.status = :status AND bk.userId = :bankerId
    """)
    List<TradeFullDetailsDTO> findAcceptedTradesForBank(
            @Param("status") Trade.Status status,
            @Param("bankerId") int bankerId
    );
}