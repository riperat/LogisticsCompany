package com.example.LogisticsCompany.web.view.controllers;

import com.example.LogisticsCompany.data.entity.Shipment;
import com.example.LogisticsCompany.data.entity.User;
import com.example.LogisticsCompany.services.interfaces.ShipmentService;
import com.example.LogisticsCompany.web.dto.CreateShipmentDTO;
import com.example.LogisticsCompany.web.view.model.CreateShipmentViewModel;
import com.example.LogisticsCompany.web.view.model.UpdateShipmentViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@Controller
@AllArgsConstructor
@RequestMapping("/shipment")
public class ShipmentController {

    private final ModelMapper modelMapper;

    private ShipmentService shipmentService;

    @GetMapping
    public String getShipments(Model model, @AuthenticationPrincipal User user) {

        //gets a list of shipments either by user or all if the user is != client
        final List<Shipment> shipments = user.getAuthorities().iterator().next().getAuthority().equals("USER") ?
                shipmentService.findAllSentByClient(user.getClient()) : shipmentService.getAllShipments();
        model.addAttribute("shipments", shipments);
        return "/shipment/shipment.html";
    }

    @GetMapping("/create-shipment")
    public String showCreateCarsForm(Model model) {
        model.addAttribute("shipment", new CreateShipmentViewModel());
        return "/cars/create-shipment";
    }

    @PostMapping("/create")
    public String createCars(@Valid @ModelAttribute("shipment") CreateShipmentViewModel shipment, BindingResult bindingResult, @AuthenticationPrincipal User user) {

        if (bindingResult.hasErrors()) {
            return "/cars/create-shipment";
        }
        shipment.setSender(Set.of(user));
        shipmentService.createShipment(modelMapper.map(shipment, CreateShipmentDTO.class));
        return "redirect:/Shipment";
    }

    @GetMapping("/edit-shipment/{id}")
    public String showEditCarsForm(Model model, @PathVariable Long id) {
//        model.addAttribute("shipment", shipmentService.getCar(id));
        return "/cars/edit-shipment";
    }

    @PostMapping("/update/{id}")
    public String updateShipment(@PathVariable long id, @AuthenticationPrincipal User user, @Valid @ModelAttribute("shipment") UpdateShipmentViewModel shipment, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return "/cars/edit-shipment";
        }
        //for some reason cars cannot be upgraded so delete and create new shipment with same id
        shipmentService.deleteShipment(id);

        shipment.setSender(Set.of(user));
        shipmentService.createShipment(modelMapper.map(shipment, CreateShipmentDTO.class));
        return "redirect:/cars";
    }

    @GetMapping("/delete/{id}")
    public String processProgramForm(@PathVariable int id) {
        shipmentService.deleteShipment(id);
        return "redirect:/cars";
    }
}
