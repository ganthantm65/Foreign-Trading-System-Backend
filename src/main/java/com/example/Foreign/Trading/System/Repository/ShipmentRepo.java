package com.example.Foreign.Trading.System.Repository;

import com.example.Foreign.Trading.System.Model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepo extends JpaRepository<Shipment,Integer> {
}
