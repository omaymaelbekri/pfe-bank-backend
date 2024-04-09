package org.sid.ebankingbackend.dtos.user.model.userInfoDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RealmAccess {
    private List<String> roles;

    // Getters and Setters
}
