package com.application.mainapp.dto.platformuser.employee;

import com.application.mainapp.dto.platformuser.RegisterUserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RegisterEmployeeDTO extends RegisterUserDTO {

    private LocalDate activationDate;
}
