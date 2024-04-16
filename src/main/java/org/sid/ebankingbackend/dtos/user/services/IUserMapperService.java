package org.sid.ebankingbackend.dtos.user.services;


import org.sid.ebankingbackend.dtos.user.model.KeycloakUser;
import org.sid.ebankingbackend.dtos.user.model.UserDto;
import org.sid.ebankingbackend.dtos.user.model.UserKeycloak;
import org.sid.ebankingbackend.dtos.user.model.userInfoDto.UserInfoResponse;
import org.sid.ebankingbackend.entities.User;

public interface IUserMapperService {
    
	UserDto convertKeycloakToDto(UserKeycloak oUserDto);


    UserKeycloak fromUserInfoResponse(UserInfoResponse userInfo);

    User mapFromKeycloakUser(KeycloakUser keycloakUser);


}
