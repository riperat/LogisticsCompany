package com.example.LogisticsCompany.util;

import com.example.LogisticsCompany.data.entity.Office;
import com.example.LogisticsCompany.data.entity.Shipment;
import com.example.LogisticsCompany.web.dto.UpdateOfficeDTO;
import com.example.LogisticsCompany.web.dto.UpdateShipmentDTO;
import lombok.AllArgsConstructor;
import org.hibernate.sql.Update;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DTOConverter {

    public static UpdateOfficeDTO convertOfficeToDTOForUpdate(Office office) {
        UpdateOfficeDTO updateOfficeDTO = new UpdateOfficeDTO();

        // Set the fields of UpdateOfficeDTO from Office entity
        updateOfficeDTO.setName(office.getName());
        updateOfficeDTO.setLocation(office.getLocation());
        updateOfficeDTO.setRevenue(office.getRevenue());
        updateOfficeDTO.setEmployees(office.getEmployees());
        updateOfficeDTO.setShipments(office.getShipments());
        updateOfficeDTO.setClients(office.getClients());
        updateOfficeDTO.setOffice(office); // Not sure if this is needed or fits your use case

        return updateOfficeDTO;
    }

    public static UpdateShipmentDTO convertShipmentToDTOForUpdate(Shipment shipment) {
        UpdateShipmentDTO updateShipmentDTO = new UpdateShipmentDTO();

        // Set the fields of UpdateShipmentDTO from Shipment entity
        updateShipmentDTO.setName(shipment.getName());
        updateShipmentDTO.setWeight(shipment.getWeight());
        updateShipmentDTO.setPrice(shipment.getPrice());
        updateShipmentDTO.setDeliveryAddress(shipment.getDeliveryAddress());
        updateShipmentDTO.setReceived(shipment.isReceived());
        updateShipmentDTO.setSend(shipment.isSend());
        updateShipmentDTO.setRecipient(shipment.getRecipient());
        updateShipmentDTO.setSender(shipment.getSender());

        return updateShipmentDTO;
    }
}
