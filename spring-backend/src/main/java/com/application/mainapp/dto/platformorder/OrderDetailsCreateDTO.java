package com.application.mainapp.dto.platformorder;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailsCreateDTO {

    private long courseID;

    private int quantity;

}
