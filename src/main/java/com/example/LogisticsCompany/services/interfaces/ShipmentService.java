package com.example.LogisticsCompany.services.interfaces;

import com.example.LogisticsCompany.data.entity.Client;
import com.example.LogisticsCompany.data.entity.Employee;
import com.example.LogisticsCompany.data.entity.Shipment;
import com.example.LogisticsCompany.web.dto.CreateShipmentDTO;
import com.example.LogisticsCompany.web.dto.UpdateShipmentDTO;

import java.util.List;

public interface ShipmentService {

    List<Shipment> getAllShipments();

    Shipment getShipmentById(long id);

    Shipment createShipment(CreateShipmentDTO shipmentDTO);

    Shipment updateShipment(long id, UpdateShipmentDTO updateShipmentDTO);

    void deleteShipment(long id);

    List<Shipment> findAllByReceivedStatus(boolean isReceived);

    List<Shipment> findAllByEmployee(Employee employee);

    List<Shipment> findAllSentByClient(Client client);

    List<Shipment> findAllReceivedByClient(Client client);
}
