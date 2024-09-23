package com.application.mainapp.dto.platformuser.employee;


import com.application.mainapp.dto.platformuser.UserResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EmployeeAccountResponseDTO extends UserResponseDTO {

    private LocalDate activationDate;
    private boolean isAccountActive;

}
