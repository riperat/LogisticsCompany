package com.example.LogisticsCompany.services.implementations;

import com.example.LogisticsCompany.data.entity.Client;
import com.example.LogisticsCompany.data.entity.Employee;
import com.example.LogisticsCompany.data.entity.Shipment;
import com.example.LogisticsCompany.data.entity.User;
import com.example.LogisticsCompany.data.repository.ShipmentRepository;
import com.example.LogisticsCompany.services.interfaces.ShipmentService;
import com.example.LogisticsCompany.web.dto.CreateShipmentDTO;
import com.example.LogisticsCompany.web.dto.UpdateShipmentDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    @Override
    public Shipment getShipmentById(long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid shop Id:" + id));
    }

    @Override
    public Shipment createShipment(CreateShipmentDTO shipmentDTO) {
        return shipmentRepository.save(modelMapper.map(shipmentDTO, Shipment.class));
    }

    @Override
    public Shipment updateShipment(long id, UpdateShipmentDTO updateShipmentDTO) {
        Shipment Shipment = modelMapper.map(updateShipmentDTO, Shipment.class);
        Shipment.setId(id);
        return shipmentRepository.save(Shipment);
    }

    @Override
    public void deleteShipment(long id) {
        shipmentRepository.deleteById(id);
    }

    @Override
    public List<Shipment> findAllSentByClient(Client client) {
        return shipmentRepository.findAllSentByClient(client);
    }

    @Override
    public List<Shipment> findAllByIsReceived(boolean isReceived) {
        return shipmentRepository.findAllByIsReceived(isReceived);
    }

    @Override
    public List<Shipment> findAllByEmployee(Employee employee) {
        return shipmentRepository.findAllByEmployee(employee);
    }

    @Override
    public List<Shipment> findAllSentBySender(User sender) {
        return shipmentRepository.findAllSentBySender(sender);
    }

    @Override
    public List<Shipment> findAllReceivedByRecipient(String recipient) {
        return shipmentRepository.findAllReceivedByRecipient(recipient);
    }
}
