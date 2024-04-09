package org.sid.ebankingbackend.dtos.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignup {
    String name;
    String famillyName;
    String email;
    String telephone;
    String password;
    String confirmPassword;
    String companyName;
}
