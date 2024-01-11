package com.example.LogisticsCompany.web.view.controllers;

import com.example.LogisticsCompany.data.entity.Employee;
import com.example.LogisticsCompany.data.entity.User;
import com.example.LogisticsCompany.data.repository.RoleRepository;
import com.example.LogisticsCompany.services.interfaces.EmployeeService;
import com.example.LogisticsCompany.services.interfaces.OfficeService;
import com.example.LogisticsCompany.services.interfaces.UserService;
import com.example.LogisticsCompany.web.dto.CreateEmployeeDTO;
import com.example.LogisticsCompany.web.dto.UserDto;
import com.example.LogisticsCompany.web.view.model.UpdateEmployeeViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for handling employee-related operations.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    private EmployeeService employeeService;

    private OfficeService officeService;

    private RoleRepository roleRepository;

    /**
     * Handles GET request to retrieve the list of employees.
     *
     * @param model the model to add attributes to
     * @return the view for displaying the list of employees
     */
    @GetMapping
    public String getEmployees(Model model) {
        // gets a list of employees either by user or all if the user is != client
        final List<String> employees = employeeService.getEmployees().stream()
                .map(Employee::getName)
                .collect(Collectors.toList());

        final List<String> users = userService.findAllUsers().stream()
                .map(UserDto::getUsername)
                .collect(Collectors.toList());

        users.removeAll(employees);
        users.removeIf(s -> s.equals("admin"));

        model.addAttribute("employees", employees);
        model.addAttribute("users", users);
        return "/employee/employee.html";
    }

    /**
     * Handles GET request to show the form for creating a new employee.
     *
     * @param model the model to add attributes to
     * @param id    the ID of the office for which the employee is being created
     * @return the view for creating a new employee
     */
    @GetMapping("/create-employee/{id}")
    public String showCreateEmployeeForm(Model model, @PathVariable Long id) {
        model.addAttribute("employees", new UserDto());
        model.addAttribute("id", id);
        return "/employee/create-employee";
    }

    /**
     * Handles POST request to create a new employee.
     *
     * @param userDto       the DTO containing user information
     * @param bindingResult the result of the validation
     * @param model         the model to add attributes to
     * @param id            the ID of the office for which the employee is being created
     * @return the redirect path after creating the employee
     */
    @PostMapping("/create")
    public String createEmployee(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, Model model, @RequestParam("id") Long id) {
        User existingUser = userService.findUserByUsername(userDto.getUsername());

        if (existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()) {
            bindingResult.rejectValue("username", null,
                    "There is already an account registered with the same email");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            model.addAttribute("id", id);
            return "/employee/create-employee/";
        }

        Employee employee = new Employee();
        employee.setName(userDto.getUsername());
        employee.setOffice(officeService.getOfficeById(id));

        User user = userService.savedUser(userDto, roleRepository.findByAuthority("EMPL"));
        employee.setUser(user);

        employeeService.createEmployee(modelMapper.map(employee, CreateEmployeeDTO.class));
        return "redirect:/office";
    }

    /**
     * Handles GET request to show the form for editing an employee.
     *
     * @param model the model to add attributes to
     * @param id    the ID of the employee being edited
     * @return the view for editing an employee
     */
    @GetMapping("/edit-employee/{id}")
    public String showEditEmployeeForm(Model model, @PathVariable Long id) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "/employee/edit-employee";
    }

    /**
     * Handles POST request to update an employee.
     *
     * @param id            the ID of the employee being updated
     * @param employee      the updated employee information
     * @param bindingResult the result of the validation
     * @return the redirect path after updating the employee
     */
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable long id, @Valid @ModelAttribute("employee") UpdateEmployeeViewModel employee, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/employee/edit-employee";
        }

        employeeService.deleteEmployee(id);
        employeeService.createEmployee(modelMapper.map(employee, CreateEmployeeDTO.class));
        return "redirect:/office";
    }

    /**
     * Handles GET request to delete an employee.
     *
     * @param id the ID of the employee being deleted
     * @return the redirect path after deleting the employee
     */
    @GetMapping("/delete/{id}")
    public String processProgramForm(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return "redirect:/office";
    }
}
