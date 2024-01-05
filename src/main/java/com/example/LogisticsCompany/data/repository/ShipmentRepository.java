package com.example.LogisticsCompany.data.repository;

import com.example.LogisticsCompany.data.entity.Client;
import com.example.LogisticsCompany.data.entity.Employee;
import com.example.LogisticsCompany.data.entity.Role;
import com.example.LogisticsCompany.data.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    List<Shipment> findAllByReceivedStatus(boolean isReceived);

    List<Shipment> findAllByEmployee(Employee employee);

    List<Shipment> findAllSentByClient(Client client);

    List<Shipment> findAllReceivedByClient(Client client);
}