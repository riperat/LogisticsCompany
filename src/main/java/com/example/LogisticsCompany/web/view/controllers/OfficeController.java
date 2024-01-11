package com.example.LogisticsCompany.web.view.controllers;

import com.example.LogisticsCompany.data.entity.Office;
import com.example.LogisticsCompany.data.entity.User;
import com.example.LogisticsCompany.services.interfaces.EmployeeService;
import com.example.LogisticsCompany.services.interfaces.OfficeService;
import com.example.LogisticsCompany.web.dto.CreateOfficeDTO;
import com.example.LogisticsCompany.web.view.model.CreateOfficeViewModel;
import com.example.LogisticsCompany.web.view.model.UpdateOfficeViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for handling office-related operations.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/office")
public class OfficeController {

    private final ModelMapper modelMapper;

    private OfficeService officeService;
    private EmployeeService employeeService;

    /**
     * Handles GET request to retrieve the list of offices.
     *
     * @param model the model to add attributes to
     * @param user  the authenticated user
     * @return the view for displaying the list of offices
     */
    @GetMapping
    public String getOffices(Model model, @AuthenticationPrincipal User user) {
        final List<Office> office = officeService.getOffices();
        model.addAttribute("isAdmin", !user.getAuthorities().iterator().next().getAuthority().equals("USER"));
        model.addAttribute("offices", office);
        return "/office/office.html";
    }

    /**
     * Handles GET request to show the form for creating a new office.
     *
     * @param model the model to add attributes to
     * @return the view for creating a new office
     */
    @GetMapping("/create-office")
    public String showCreateOfficesForm(Model model) {
        model.addAttribute("offices", new CreateOfficeViewModel());
        return "/office/create-office";
    }

    /**
     * Handles POST request to create a new office.
     *
     * @param office         the office information to be created
     * @param bindingResult  the result of the validation
     * @return the redirect path after creating the office
     */
    @PostMapping("/create")
    public String createOffice(@Valid @ModelAttribute("offices") CreateOfficeViewModel office, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/office/create-office";
        }
        office.setRevenue(0.0);
        officeService.createOffice(modelMapper.map(office, CreateOfficeDTO.class));
        return "redirect:/office";
    }

    /**
     * Handles GET request to show the form for editing an office.
     *
     * @param model the model to add attributes to
     * @param id    the ID of the office being edited
     * @return the view for editing an office
     */
    @GetMapping("/edit-office/{id}")
    public String showEditOfficeForm(Model model, @PathVariable Long id) {
        model.addAttribute("office", officeService.getOfficeById(id));
        return "/office/edit-office";
    }

    /**
     * Handles GET request to view the details of an office.
     *
     * @param model the model to add attributes to
     * @param id    the ID of the office being viewed
     * @return the view for displaying the details of an office
     */
    @GetMapping("/office-view/{id}")
    public String officeView(Model model, @PathVariable Long id) {
        model.addAttribute("employees", employeeService.getAllEmployeesByOffice(officeService.getOfficeById(id)));
        model.addAttribute("id", id);
        return "/office/office-view";
    }

    /**
     * Handles POST request to update an office.
     *
     * @param id             the ID of the office being updated
     * @param user           the authenticated user
     * @param office         the updated office information
     * @param bindingResult  the result of the validation
     * @return the redirect path after updating the office
     */
    @PostMapping("/update/{id}")
    public String updateOffice(@PathVariable long id, @AuthenticationPrincipal User user, @Valid @ModelAttribute("office") UpdateOfficeViewModel office, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/office/edit-office";
        }

        officeService.deleteOffice(id);
        officeService.createOffice(modelMapper.map(office, CreateOfficeDTO.class));
        return "redirect:/office";
    }

    /**
     * Handles GET request to delete an office.
     *
     * @param id the ID of the office being deleted
     * @return the redirect path after deleting the office
     */
    @GetMapping("/delete/{id}")
    public String processProgramForm(@PathVariable int id) {
        officeService.deleteOffice(id);
        return "redirect:/office";
    }
}
