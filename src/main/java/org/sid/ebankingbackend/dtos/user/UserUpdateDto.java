package org.sid.ebankingbackend.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link org.sid.ebankingbackend.entities.User}
 */
@Data
@NoArgsConstructor
public class UserUpdateDto implements Serializable {
    String name;
    String famillyName;
    String email;
    String telephone;
}
