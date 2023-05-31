package com.bus.service;

import com.bus.dto.UserDto;
import com.bus.model.User;

import java.util.List;

public interface UserService {
	
    void saveUser(UserDto userDto);
    User findByEmail(String email);
    User getUserById(Long id);
    List<UserDto> findAllUsers();
}
