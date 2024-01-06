package com.example.LogisticsCompany.data.repository;

import com.example.LogisticsCompany.data.entity.Employee;
import com.example.LogisticsCompany.data.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    List<Shipment> findAllByIsReceived(boolean isReceived);

    List<Shipment> findAllByEmployee(Employee employee);

    List<Shipment> findAllSentBySender(String sender);

    List<Shipment> findAllReceivedByRecipient(String recipient);
}