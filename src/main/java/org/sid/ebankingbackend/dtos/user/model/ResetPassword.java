package org.sid.ebankingbackend.dtos.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPassword {
    String oldPassword ;
    String confirmPassword ;
    String newPassword ;
}
