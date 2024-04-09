package org.sid.ebankingbackend.dtos.user.services;


import org.sid.ebankingbackend.dtos.user.model.KeycloakUser;
import org.sid.ebankingbackend.dtos.user.model.UserDto;
import org.sid.ebankingbackend.dtos.user.model.UserKeycloak;
import org.sid.ebankingbackend.dtos.user.model.UserSignup;
import org.sid.ebankingbackend.dtos.user.model.userInfoDto.UserInfoResponse;
import org.sid.ebankingbackend.entities.User;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserMapperService implements IUserMapperService {
    
    @Override
    public UserDto convertKeycloakToDto(UserKeycloak oUserKeycloak) {
        UserDto user = new UserDto();
      //  user.setGroups(oUserKeycloak.getAgences());
        user.setEmail(oUserKeycloak.getEmail());
        user.setName(oUserKeycloak.getGiven_name());
        user.setFamillyName(oUserKeycloak.getFamily_name());
        user.setEmailVerified(oUserKeycloak.getEmail_verified());
        user.setUsername(oUserKeycloak.getPreferred_username());
       // user.setRoles(Collections.singletonList(oUserKeycloak.getRoles()));
        user.setIdKeycloak(oUserKeycloak.getSub());
        return user;
    }
    @Override
    public  UserKeycloak fromUserInfoResponse(UserInfoResponse userInfo) {
        UserKeycloak userKeycloak = new UserKeycloak();
        userKeycloak.setSub(userInfo.getSub());
        userKeycloak.setEmail_verified(userInfo.isEmail_verified());
        userKeycloak.setName(userInfo.getName());
        userKeycloak.setPreferred_username(userInfo.getPreferred_username());
        userKeycloak.setGiven_name(userInfo.getGiven_name());
        userKeycloak.setFamily_name(userInfo.getFamily_name());
        userKeycloak.setEmail(userInfo.getEmail());
        userKeycloak.setAgences(userInfo.getRealm_access().getRoles());
        userKeycloak.setRoles(userInfo.getResource_access().getAccount().getRoles());
        return userKeycloak;
    }
    @Override
    public  User mapFromKeycloakUser(KeycloakUser keycloakUser) {
        User user = new User();
        user.setUsername(keycloakUser.getUsername());
        user.setEmail(keycloakUser.getEmail());
        user.setName(keycloakUser.getFirstName());
        user.setFamillyName(keycloakUser.getLastName());
        user.setEmailVerified(keycloakUser.isEmailVerified());
        return user;
    }
    @Override
    public KeycloakUser mapToKeycloakUser(UserSignup user) {
        KeycloakUser keycloakUser = new KeycloakUser();
        keycloakUser.setUsername(user.getEmail());
        keycloakUser.setEmail(user.getEmail());
        keycloakUser.setFirstName(user.getName());
        keycloakUser.setLastName(user.getFamillyName());
        return keycloakUser;
    }

}
