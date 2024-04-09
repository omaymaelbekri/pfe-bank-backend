package org.sid.ebankingbackend.dtos.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordInfo {
    private String type;
    private String value;
    private boolean temporary;

}
