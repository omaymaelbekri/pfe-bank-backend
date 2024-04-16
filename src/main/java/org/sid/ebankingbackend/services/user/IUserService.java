package org.sid.ebankingbackend.services.user;



import org.sid.ebankingbackend.dtos.user.UserUpdateDto;
import org.sid.ebankingbackend.dtos.user.model.*;
import org.sid.ebankingbackend.entities.User;
import org.sid.ebankingbackend.exceptions.exceptions.APIErrorException;

import java.util.List;

public interface IUserService {


	TokenDto login(LoginDto loginDto) throws APIErrorException;

    TokenDto refreshAccessToken(String refreshToken) throws APIErrorException;

    void logout(String refreshToken) throws APIErrorException;


    UserDto getInfo() throws APIErrorException;


    User getUserProfileByUsername(String username);

    User insertUser (UserKeycloak oUserKeycloak);

    List<User> getAllUser();



}
