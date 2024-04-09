package org.sid.ebankingbackend.dtos.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
public class KeycloakUser {
    private Long createdTimestamp;
    private String username;
    private boolean enabled;
    private boolean totp;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> disableableCredentialTypes;
    private List<String> requiredActions;
    private int notBefore;
    private Map<String, Boolean> access;
    private List<String> realmRoles;
    private List<Object>credentials;
}
