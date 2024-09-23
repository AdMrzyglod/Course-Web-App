package com.application.mainapp.dto.platformuser.individualuser;


import com.application.mainapp.dto.platformuser.UserResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class IndividualUserAccountResponseDTO extends UserResponseDTO {

    private BigDecimal money;

}
