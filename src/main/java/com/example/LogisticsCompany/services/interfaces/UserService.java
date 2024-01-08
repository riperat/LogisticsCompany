package com.example.LogisticsCompany.services.interfaces;

import com.example.LogisticsCompany.data.entity.Employee;
import com.example.LogisticsCompany.data.entity.Role;
import com.example.LogisticsCompany.data.entity.User;
import com.example.LogisticsCompany.web.dto.UserDto;

import java.util.List;

public interface UserService {

    User getUserById(long id);

    void saveUser(UserDto userDto, Role role);

    User savedUser(UserDto userDto, Role role);

    User findUserByUsername(String username);

    List<UserDto> findAllUsers();

    void deleteUser(long id);
}
