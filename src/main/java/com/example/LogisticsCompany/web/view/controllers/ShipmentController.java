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
import static com.example.LogisticsCompany.util.HibernateUtil.getFieldAsString;

/**
 * Controller class for handling shipment-related operations.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/shipment")
public class ShipmentController {

    private final ModelMapper modelMapper;

    private ShipmentService shipmentService;

    private OfficeService officeService;

    /**
     * Handles GET request to retrieve the list of shipments.
     *
     * @param model the model to add attributes to
     * @param user  the authenticated user
     * @return the view for displaying the list of shipments
     */
    @GetMapping
    public String getShipments(
            Model model,
            @AuthenticationPrincipal User user,
            @RequestParam(name = "search", required = false) String searchTerm,
            @RequestParam(name = "searchFields", required = false) List<String> searchFields
    ) {
        List<Shipment> shipments = user.getAuthorities().iterator().next().getAuthority().equals("USER") ?
                shipmentService.findAllSentBySender(user) : shipmentService.getAllShipments();

        if (searchTerm != null && !searchTerm.isEmpty() && searchFields != null && !searchFields.isEmpty()) {
            shipments = shipments.stream()
                    .filter(shipment -> {
                        String searchTermLowerCase = searchTerm.toLowerCase();
                        return searchFields.stream().anyMatch(field -> {
                            String fieldValue = getFieldAsString(shipment, field);
                            return fieldValue != null && fieldValue.toLowerCase().contains(searchTermLowerCase);
                        });
                    })
                    .collect(Collectors.toList());
        }

        model.addAttribute("isAdmin", !user.getAuthorities().iterator().next().getAuthority().equals("USER"));
        model.addAttribute("sent", shipments.stream().filter(Shipment::isSend).collect(Collectors.toList()));
        model.addAttribute("received", shipments.stream().filter(Shipment::isReceived).collect(Collectors.toList()));
        return "/shipment/shipment.html";
    }

    /**
     * Handles GET request to show the form for creating a new shipment.
     *
     * @param model the model to add attributes to
     * @param id    the ID of the office for which the shipment is being created
     * @return the view for creating a new shipment
     */
    @GetMapping("/create-shipment/{id}")
    public String showCreateShipmentForm(Model model, @PathVariable Long id) {
        model.addAttribute("shipments", new CreateShipmentViewModel());
        model.addAttribute("id", id);
        return "/shipment/create-shipment";
    }

    /**
     * Handles POST request to create a new shipment.
     *
     * @param shipment      the shipment information to be created
     * @param bindingResult the result of the validation
     * @param user          the authenticated user
     * @param id            the ID of the office for which the shipment is being created
     * @return the redirect path after creating the shipment
     */
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

    /**
     * Handles GET request to show the form for editing a shipment.
     *
     * @param model the model to add attributes to
     * @param id    the ID of the shipment being edited
     * @return the view for editing a shipment
     */
    @GetMapping("/edit-shipment/{id}")
    public String showEditShipmentForm(Model model, @PathVariable Long id) {
        model.addAttribute("shipment", shipmentService.getShipmentById(id));
        return "/shipment/edit-shipment";
    }

    /**
     * Handles GET request to mark a shipment as received.
     *
     * @param model the model to add attributes to
     * @param id    the ID of the shipment being marked as received
     * @return the redirect path after marking the shipment as received
     */
    @GetMapping("/receive-shipment/{id}")
    public String showReceiveShipmentForm(Model model, @PathVariable Long id) {
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

    /**
     * Handles POST request to update a shipment.
     *
     * @param id            the ID of the shipment being updated
     * @param user          the authenticated user
     * @param shipment      the updated shipment information
     * @param bindingResult the result of the validation
     * @return the redirect path after updating the shipment
     */
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


    /**
     * Handles GET request to delete an shipment
     *
     * @param id the ID of the shipment being deleted
     * @return the redirect path after deleting the office
     */
    @GetMapping("/delete/{id}")
    public String processProgramForm(@PathVariable int id) {
        shipmentService.deleteShipment(id);
        return "redirect:/shipment";
    }
}
