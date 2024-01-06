package com.example.LogisticsCompany.web.api;

import com.example.LogisticsCompany.data.entity.Shipment;
import com.example.LogisticsCompany.services.interfaces.ShipmentService;
import com.example.LogisticsCompany.web.dto.CreateShipmentDTO;
import com.example.LogisticsCompany.web.dto.UpdateShipmentDTO;
import com.example.LogisticsCompany.web.view.model.CreateShipmentViewModel;
import com.example.LogisticsCompany.web.view.model.UpdateShipmentViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class ShipmentApiController {
    private final ModelMapper modelMapper;

    @Autowired
    private final ShipmentService shipmentService;

    @GetMapping(value = "/api/shipment")
    public List<Shipment> getShipment() {
        return shipmentService.getAllShipments();
    }

    @GetMapping(value = "/api/shipment/{id}")
    public Shipment getShipment(@PathVariable("id") int id) {
        return shipmentService.getShipmentById(id);
    }

    @PostMapping(value = "/api/shipment")
    public Shipment createShipment(@RequestBody CreateShipmentViewModel Shipment) {
        return shipmentService.createShipment(modelMapper.map(Shipment, CreateShipmentDTO.class));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/api/shipment/{id}")
    public Shipment updateSchool(@PathVariable("id") long id, @RequestBody UpdateShipmentViewModel Shipment) {
        return shipmentService.updateShipment(id, modelMapper.map(Shipment, UpdateShipmentDTO.class));
    }

    @GetMapping(value = "/api/shipment/delete/{id}")
    public void deleteShipment(@PathVariable long id) {
        shipmentService.deleteShipment(id);
    }

}
