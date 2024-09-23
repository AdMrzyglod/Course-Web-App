package com.application.mainapp.dto.platformorder;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class PlatformOrderCreateDTO {

    private List<OrderDetailsCreateDTO> orderDetailsCreateDTOList;

}
