package com.application.mainapp.config;


import com.application.mainapp.dto.course.CourseSubsectionDetailsDTO;
import com.application.mainapp.dto.payment.PaymentResponseDTO;
import com.application.mainapp.model.FileData;
import com.application.mainapp.model.Payment;
import com.application.mainapp.model.Subsection;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureDTO {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Payment, PaymentResponseDTO> propertyPaymentMapper = modelMapper.createTypeMap(Payment.class, PaymentResponseDTO.class);
        propertyPaymentMapper.addMappings(
                mapper -> mapper.map(payment -> payment.getIndividualUser().getEmail(), PaymentResponseDTO::setUserEmail)
        );


        TypeMap<Subsection, CourseSubsectionDetailsDTO> propertyMapper = modelMapper.createTypeMap(Subsection.class, CourseSubsectionDetailsDTO.class);
        Converter<FileData, Boolean> fileToBoolean = c -> c.getSource()!=null;
        propertyMapper.addMappings(
                mapper -> mapper.using(fileToBoolean).map(Subsection::getFile, CourseSubsectionDetailsDTO::setFileExists)
        );

        return modelMapper;
    }
}
