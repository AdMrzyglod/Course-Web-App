package com.application.mainapp.service;


import com.application.mainapp.dto.platformuser.UserResponseDTO;
import com.application.mainapp.dto.platformuser.individualuser.IndividualUserAccountResponseDTO;
import com.application.mainapp.model.IndividualUser;
import com.application.mainapp.repository.IndividualUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IndividualUserService {

    private final IndividualUserRepository individualUserRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public IndividualUserService(IndividualUserRepository individualUserRepository, ModelMapper modelMapper) {
        this.individualUserRepository = individualUserRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserResponseDTO> getAllIndividualUsers(){
        List<IndividualUser> individualUsers = this.individualUserRepository.findAll();
        return individualUsers.stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    public IndividualUserAccountResponseDTO getIndividualUserById(Long id){
        Optional<IndividualUser> userOptional = this.individualUserRepository.findById(id);

        if (userOptional.isPresent()) {
            IndividualUser individualUser = userOptional.get();
            return modelMapper.map(individualUser, IndividualUserAccountResponseDTO.class);
        } else {
            throw new RuntimeException("User not exists");
        }
    }

    public IndividualUserAccountResponseDTO getIndividualUserAccountResponseDTO(Long platformUserID){
        Optional<IndividualUser> individualUserOptional = this.individualUserRepository.findById(platformUserID);
        if(individualUserOptional.isPresent()){
            return this.modelMapper.map(individualUserOptional, IndividualUserAccountResponseDTO.class);
        }
        throw new RuntimeException("User not exists");
    }

}
