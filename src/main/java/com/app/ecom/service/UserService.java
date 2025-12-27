package com.app.ecom.service;

import com.app.ecom.dto.AddressDto;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<UserResponse> findUserById(long id)
    {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }
    public List<UserResponse> fetchAllUsers(){

        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public String createUser(UserRequest userRequest)
    {
        User user = new User();
        updateUserRequest(user , userRequest);
        userRepository.save(user);
        return "user added successfully";
    }

    private void updateUserRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        System.out.println(userRequest.getAddressDto());
        if(userRequest.getAddressDto() != null)
        {
            Address address = new Address();
            address.setStreet(userRequest.getAddressDto().getStreet());
            address.setCity(userRequest.getAddressDto().getCity());
            address.setState(userRequest.getAddressDto().getState());
            address.setCountry(userRequest.getAddressDto().getCountry());
            address.setZipcode(userRequest.getAddressDto().getZipcode());
            user.setAddress(address);
        }
    }

    public String updateUser(long id , UserRequest updatedUserRequest)
    {
        User user = userRepository.findById(id).map(
                existingUser ->{
                    updateUserRequest(existingUser , updatedUserRequest);
                    userRepository.save(existingUser);
                    return existingUser;
                }
        ).orElse(null);
        return "user updated successfully";
    }

private UserResponse mapToUserResponse(User user)
{
    UserResponse response = new UserResponse();
    response.setId(String.valueOf(user.getId()));
    response.setFirstName(user.getFirstName());
    response.setLastName(user.getLastName());
    response.setEmail(user.getEmail());
    response.setPhone(user.getPhone());
    response.setRole(user.getRole());
    if(user.getAddress() != null)
    {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreet(user.getAddress().getStreet());
        addressDto.setCity(user.getAddress().getCity());
        addressDto.setState(user.getAddress().getState());
        addressDto.setCountry(user.getAddress().getCountry());
        addressDto.setZipcode(user.getAddress().getZipcode());
        response.setAddressDto(addressDto);
    }
    return response;
}
}

























