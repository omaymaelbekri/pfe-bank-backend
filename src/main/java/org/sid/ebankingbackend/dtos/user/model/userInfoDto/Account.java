package org.sid.ebankingbackend.dtos.user.model.userInfoDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Account {
    private List<String> roles;

}
