package com.application.mainapp.dto.platformuser;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UpdateUserStateDTO {

    private long platformUserID;
    private boolean accountActive;

}
