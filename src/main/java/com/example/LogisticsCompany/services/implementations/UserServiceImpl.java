package com.example.LogisticsCompany.services.implementations;

import com.example.LogisticsCompany.data.entity.Role;
import com.example.LogisticsCompany.data.entity.User;
import com.example.LogisticsCompany.data.repository.RoleRepository;
import com.example.LogisticsCompany.data.repository.UserRepository;
import com.example.LogisticsCompany.security.PasswordEncoder;
import com.example.LogisticsCompany.services.interfaces.UserService;
import com.example.LogisticsCompany.web.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    @Override
    public void saveUser(UserDto userDto, Role role) {
        savedUser(userDto, role);
    }

    @Override
    public User savedUser(UserDto userDto, Role role) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(userDto.getPassword()));

        if (role == null) {
            role = checkRoleExist();
        }

        user.setAuthorities(Set.of(role));
        userRepository.save(user);
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setAuthority("USER");
        return roleRepository.save(role);
    }
}
