package com.example.LogisticsCompany.web.view.controllers;

import com.example.LogisticsCompany.data.entity.Shipment;
import com.example.LogisticsCompany.data.entity.User;
import com.example.LogisticsCompany.services.interfaces.OfficeService;
import com.example.LogisticsCompany.services.interfaces.ShipmentService;
import com.example.LogisticsCompany.util.HibernateUtil;
import com.example.LogisticsCompany.web.dto.CreateShipmentDTO;
import com.example.LogisticsCompany.web.dto.UpdateOfficeDTO;
import com.example.LogisticsCompany.web.dto.UpdateShipmentDTO;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.LogisticsCompany.util.DTOConverter.convertOfficeToDTOForUpdate;
import static com.example.LogisticsCompany.util.DTOConverter.convertShipmentToDTOForUpdate;


@Controller
@AllArgsConstructor
@RequestMapping("/shipment")
public class ShipmentController {

    private final ModelMapper modelMapper;

    private ShipmentService shipmentService;

    private OfficeService officeService;

    @GetMapping
    public String getShipments(Model model, @AuthenticationPrincipal User user) {
        //gets a list of shipments either by user or all if the user is != client
        final List<Shipment> shipments = user.getAuthorities().iterator().next().getAuthority().equals("USER") ?
                shipmentService.findAllSentBySender(user) : shipmentService.getAllShipments();
        model.addAttribute("isAdmin", !user.getAuthorities().iterator().next().getAuthority().equals("USER"));

        List<Shipment> sent = shipments.stream()
                .filter(Shipment::isSend)
                .collect(Collectors.toList());

        List<Shipment> received = shipments.stream()
                .filter(Shipment::isReceived)
                .collect(Collectors.toList());

        model.addAttribute("sent", sent);
        model.addAttribute("received", received);
        return "/shipment/shipment.html";
    }

    @GetMapping("/create-shipment/{id}")
    public String showCreateShipmentForm(Model model, @PathVariable Long id) {
        model.addAttribute("shipments", new CreateShipmentViewModel());
        model.addAttribute("id", id);
        return "/shipment/create-shipment";
    }

    @PostMapping("/create")
    public String createShipment(@Valid @ModelAttribute("shipments") CreateShipmentViewModel shipment, BindingResult bindingResult, @AuthenticationPrincipal User user, @RequestParam("id") Long id) {

        if (bindingResult.hasErrors()) {
            return "/shipment/create-shipment";
        }

        shipment.setOffice(officeService.getOfficeById(id));
        shipment.setShipmentDateTime(LocalDateTime.now());
        shipment.setSender(user);
        shipment.setSend(true);
        shipment.setPrice(HibernateUtil.calculatePrice(shipment.getWeight()));
        shipmentService.createShipment(modelMapper.map(shipment, CreateShipmentDTO.class));
        return "redirect:/shipment";
    }

    @GetMapping("/edit-shipment/{id}")
    public String showEditShipmentForm(Model model, @PathVariable Long id) {
        model.addAttribute("shipment", shipmentService.getShipmentById(id));
        return "/shipment/edit-shipment";
    }

    @GetMapping("/receive-shipment/{id}")
    public String showReveiveShipmentForm(Model model, @PathVariable Long id) {
        Shipment shipment = shipmentService.getShipmentById(id);
        shipment.setSend(false);
        shipment.setReceived(true);
        shipment.setReceiveDateTime(LocalDateTime.now());
        Double rev = shipment.getOffice().getRevenue() + shipment.getPrice();
        shipment.getOffice().setRevenue(rev);

        UpdateOfficeDTO officeDTO = convertOfficeToDTOForUpdate(shipment.getOffice());
        officeService.updateOffice(shipment.getOffice().getId(), officeDTO);

        UpdateShipmentDTO shipmentDTO = convertShipmentToDTOForUpdate(shipment);
        officeService.updateOffice(shipment.getOffice().getId(), officeDTO);
        return "redirect:/shipment";
    }


    @PostMapping("/update/{id}")
    public String updateShipment(@PathVariable long id, @AuthenticationPrincipal User user, @Valid @ModelAttribute("shipment") UpdateShipmentViewModel shipment, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/shipment/edit-shipment";
        }

        shipmentService.deleteShipment(id);
        shipment.setSender(user);
        shipmentService.createShipment(modelMapper.map(shipment, CreateShipmentDTO.class));
        return "redirect:/shipment";
    }

    @GetMapping("/delete/{id}")
    public String processProgramForm(@PathVariable int id) {
        shipmentService.deleteShipment(id);
        return "redirect:/shipment";
    }
}
