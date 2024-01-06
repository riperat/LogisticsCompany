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
    public String showCreateShipmentForm(Model model) {
        model.addAttribute("shipments", new CreateShipmentViewModel());
        return "/shipment/create-shipment";
    }

    @PostMapping("/create")
    public String createShipment(@Valid @ModelAttribute("shipments") CreateShipmentViewModel shipment, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/shipment/create-shipment";
        }

        shipmentService.createShipment(modelMapper.map(shipment, CreateShipmentDTO.class));
        return "redirect:/shipment";
    }

    @GetMapping("/edit-shipment/{id}")
    public String showEditShipmentForm(Model model, @PathVariable Long id) {
        model.addAttribute("shipment", shipmentService.getShipmentById(id));
        return "/shipment/edit-shipment";
    }

    @PostMapping("/update/{id}")
    public String updateShipment(@PathVariable long id, @AuthenticationPrincipal User user, @Valid @ModelAttribute("shipment") UpdateShipmentViewModel shipment, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/shipment/edit-shipment";
        }
//        //for some reason shipment cannot be upgraded so delete and create new shipment with same id
        shipmentService.deleteShipment(id);

        shipmentService.createShipment(modelMapper.map(shipment, CreateShipmentDTO.class));
        return "redirect:/shipment";
    }

    @GetMapping("/delete/{id}")
    public String processProgramForm(@PathVariable int id) {
        shipmentService.deleteShipment(id);
        return "redirect:/shipment";
    }
}
