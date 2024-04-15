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


    void getRefreshToken() throws APIErrorException;

    UserDto getInfo() throws APIErrorException;


    User getUserProfileByUsername(String username);

    User insertUser (UserKeycloak oUserKeycloak);

    void addHistorique(User Ouser);

    void signup(UserSignup userSignup) throws APIErrorException;



    void addUser(KeycloakUser loginDto) throws APIErrorException;

    boolean passwordCheck(String password, String passwordCofirmation) throws APIErrorException;

    UserDto updateUser(String IdKeycloak, UserUpdateDto userUpdateDto) throws APIErrorException;

    List<User> getAllUser();

    void updateUserKeycloack(KeycloakUser keycloakUser, String id);

    KeycloakUser valideInsert(UserUpdateDto userUpdateDto) throws APIErrorException;

    void emailVerifie(String id) throws APIErrorException;


    void resetPassword(ResetPassword resetPassword) throws APIErrorException;

    void roleMappings(String id, List<RoleAssignment> roleAssignment) throws APIErrorException;
}
