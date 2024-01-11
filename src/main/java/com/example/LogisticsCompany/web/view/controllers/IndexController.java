package com.example.LogisticsCompany.web.view.controllers;

import com.example.LogisticsCompany.data.entity.User;
import com.example.LogisticsCompany.data.repository.RoleRepository;
import com.example.LogisticsCompany.services.interfaces.UserService;
import com.example.LogisticsCompany.web.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for handling User-related operations.
 */
@Controller
@RequestMapping("/")
@AllArgsConstructor
public class IndexController {

    @Autowired
    private UserService userService;

    private RoleRepository roleRepository;

    /**
     * Handles GET request for login page.
     *
     * @return the view for the login page
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Handles GET request to show user registration form.
     *
     * @param model the model to add attributes to
     * @return the view for the user registration form
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    /**
     * Handles POST request to submit user registration form.
     *
     * @param userDto the DTO containing user information
     * @param result  the result of the validation
     * @param model   the model to add attributes to
     * @return the redirect path after user registration
     */
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByUsername(userDto.getUsername());

        if (existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()) {
            result.rejectValue("username", null,
                    "There is already an account registered with the same email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }
        userService.saveUser(userDto, roleRepository.findByAuthority("USER"));
        return "redirect:/register?success";
    }

    /**
     * Handles GET request to retrieve a list of users.
     *
     * @param model the model to add attributes to
     * @return the view for displaying the list of users
     */
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    /**
     * Handles GET request to delete a user.
     *
     * @param name the username of the user being deleted
     * @return the redirect path after deleting the user
     */
    @GetMapping("/delete/{name}")
    public String processProgramForm(@PathVariable String name) {
        userService.deleteUser(userService.findUserByUsername(name).getId());
        return "redirect:/employee";
    }

    /**
     * Handles GET request for unauthorized access.
     *
     * @param model the model to add attributes to
     * @return the view for unauthorized access
     */
    @GetMapping("unauthorized")
    public String unauthorized(Model model) {
        return "unauthorized";
    }
}
