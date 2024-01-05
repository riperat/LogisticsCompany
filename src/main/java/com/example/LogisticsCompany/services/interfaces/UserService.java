package com.example.LogisticsCompany.services.interfaces;

import com.example.LogisticsCompany.data.entity.User;
import com.example.LogisticsCompany.web.dto.UserDto;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByUsername(String username);

    List<UserDto> findAllUsers();
}
