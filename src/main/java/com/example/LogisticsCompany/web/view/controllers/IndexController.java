package com.example.LogisticsCompany.web.view.controllers;


import com.example.LogisticsCompany.data.entity.User;
import com.example.LogisticsCompany.services.interfaces.UserService;
import com.example.LogisticsCompany.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserService userService;

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
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

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("unauthorized")
    public String unauthorized(Model model) {
        return "unauthorized";
    }
}