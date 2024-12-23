package com.example.teaDelivery.service;

import com.example.teaDelivery.dto.UserDto;
import com.example.teaDelivery.models.entity.User;
import com.example.teaDelivery.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements BaseService<UserDto, User> {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserByName(String name){
        return convertToDto(userRepository.findByUsername(name).orElseThrow());
    }

    public UserDto getUserById(Long id){
        return convertToDto(userRepository.findById(id).orElseThrow());
    }

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getFullName());
        userDto.setRegistrationDate(user.getRegistrationDate());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setLoyaltyPoints(user.getLoyaltyPoints());
        return userDto;
    }
}
