package org.sid.ebankingbackend.services.user;
import org.sid.ebankingbackend.dtos.IMapClassWithDto;
import org.sid.ebankingbackend.dtos.user.UserUpdateDto;
import org.sid.ebankingbackend.dtos.user.model.*;
import org.sid.ebankingbackend.dtos.user.model.userInfoDto.UserInfoResponse;
import org.sid.ebankingbackend.dtos.user.services.IUserMapperService;
import org.sid.ebankingbackend.entities.Historique_Login;
import org.sid.ebankingbackend.entities.User;
import org.sid.ebankingbackend.exceptions.exceptions.APIErrorException;
import org.sid.ebankingbackend.exceptions.exceptions.ErrorCode;
import org.sid.ebankingbackend.repositories.Historique_LoginRepository;
import org.sid.ebankingbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.keycloak.OAuth2Constants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Service()
public class UserService implements IUserService {

	private static final String CLIENT_ID = "client_id";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String GRANT_TYPE = "grant_type";

	@Value("${bff_keycloak.token-uri}")
	private String keycloakTokenUri;

	@Value("${keycloak.realm}")
	private String realm;

	@Value("${keycloak.resource}")
	private String clientID;
	@Value("${keycloak.logout.url}")
	private String logoutUrl ;
	@Value("${keyloack.introspection_endpoint}")
	private String userInfoUrl ;
	@Value("${keycloak.admin.secret}")
	private String secret_key ;
	@Value("${keycloak.admin.ressource}")
	private String admin_Client;
	@Value("${keycloack.creat_user.url}")
	private String addUserUrl;
	@Value("${keycloack.update_user.url}")
	private String UpdateUserUrl;
	@Value("${keycloack.email.verifie.url}")
	private String emailVerifie;
	@Value("${keycloack.reset_password.url}")
    private String passwordReset;
	@Value("${keycloack.email.redirect_uri}")
	private String redirect_uri ;
	@Value("${keycloack.role-mappings.url}")
	private String roleMapping;
	@Value("${role-mappings.url}")
	private String roleMappingClients;
	@Value("${keycloack.role-id}")
	private String roleId;
	String bearer = "Bearer " ;

	private static String accessToken ;
	private static String accessTokenAdmin;

	private final RestTemplate restTemplate;
	private final IMapClassWithDto<TokenDto, ReponseTokenDto> mapperToken;
	private final IMapClassWithDto<User, UserDto> userServiceMapper;
	private final IUserMapperService userMapperService;
	private final UserRepository userRepository;
	private final Historique_LoginRepository historiqueLoginRepository;
	//private final ICompanyService companyService;

	@Autowired
    public UserService(RestTemplate restTemplate, IMapClassWithDto<TokenDto, ReponseTokenDto> mapperToken, IMapClassWithDto<User, UserDto> userServiceMapper, IUserMapperService userMapperService, UserRepository userRepository, Historique_LoginRepository historiqueLoginRepository) {
        this.restTemplate = restTemplate;
        this.mapperToken = mapperToken;
        this.userServiceMapper = userServiceMapper;
        this.userMapperService = userMapperService;
        this.userRepository = userRepository;
        this.historiqueLoginRepository = historiqueLoginRepository;
       // this.companyService = companyService;
    }

    @Override
	public TokenDto login(LoginDto loginDto) throws APIErrorException {

		ReponseTokenDto oReponseToken = null;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("username", loginDto.getUsername());
		map.add(PASSWORD, loginDto.getPassword());
		map.add(CLIENT_ID, clientID);
		map.add(GRANT_TYPE, "password");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

//		try {
			oReponseToken = restTemplate.postForEntity(keycloakTokenUri, request, ReponseTokenDto.class).getBody();
			if (oReponseToken != null)
			{
				accessToken = oReponseToken.getAccessToken();
			}
//		} catch (Exception e) {
//			throw new APIErrorException(ErrorCode.E444);
//		}

		return mapperToken.convertToEntity(oReponseToken, TokenDto.class);
	}
	@Override
	public TokenDto refreshAccessToken(String refreshToken) throws APIErrorException {

		ReponseTokenDto oReponseToken = null;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(REFRESH_TOKEN, refreshToken);
		map.add(CLIENT_ID, clientID);
		map.add(GRANT_TYPE, REFRESH_TOKEN);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		try {
			oReponseToken = restTemplate.postForEntity(keycloakTokenUri, request, ReponseTokenDto.class).getBody();
			accessToken = oReponseToken.getAccessToken();
		} catch (Exception e) {
			throw new APIErrorException(ErrorCode.E444);
		}
		return mapperToken.convertToEntity(oReponseToken, TokenDto.class);
	}
	@Override
	public void logout(String refreshToken) throws APIErrorException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(REFRESH_TOKEN, refreshToken);
		map.add(CLIENT_ID, clientID);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		try {
			restTemplate.postForEntity(logoutUrl, request, Void.class);
		} catch (Exception e) {
			throw new APIErrorException(ErrorCode.E444);
		}
	}

	@Override
	public void getRefreshToken() throws APIErrorException {
		LoginDto loginDto = new LoginDto() ;
		loginDto.setPassword("3ichamohamed@22@ahmed");
		loginDto.setUsername("admin");
		TokenDto tokenDto = login(loginDto);
		accessTokenAdmin =tokenDto.getAccessToken();
	}
	@Override
	public UserDto getInfo() throws APIErrorException {
		UserInfoResponse userInfoResponse = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(TOKEN, accessToken);
		map.add(CLIENT_ID, admin_Client);
		map.add(CLIENT_SECRET, secret_key);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		try {
			userInfoResponse = restTemplate.postForEntity(userInfoUrl, request, UserInfoResponse.class,realm).getBody();

		} catch (Exception e) {
			throw new APIErrorException(ErrorCode.E444);
		}
		UserKeycloak userKeycloak = userMapperService.fromUserInfoResponse(userInfoResponse);
		return userMapperService.convertKeycloakToDto(userKeycloak);
	}

	@Override
	public User getUserProfileByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	@Override
	public User insertUser(UserKeycloak oUserKeycloak){
		User ouserResponse ;
		User ouser = userRepository.findByIdKeycloak(oUserKeycloak.getSub());

		if (ouser != null){
			//ouser.setGroups(oUserKeycloak.getAgences());
			ouser.setEmail(oUserKeycloak.getEmail());
			ouser.setName(oUserKeycloak.getGiven_name());
			ouser.setFamillyName(oUserKeycloak.getFamily_name());
			ouser.setEmailVerified(oUserKeycloak.getEmail_verified());
			ouser.setUsername(oUserKeycloak.getPreferred_username());
		//	ouser.setRoles(oUserKeycloak.getRoles());
			ouser.setIdKeycloak(oUserKeycloak.getSub());
			ouserResponse = userRepository.save(ouser);
		}
		else {
			UserDto ouserDto = userMapperService.convertKeycloakToDto(oUserKeycloak);
			ouser = userServiceMapper.convertToEntity(ouserDto , User.class);
			ouserResponse = userRepository.save(ouser);
		}

		return ouserResponse;
	}
@Override
public void addHistorique(User user){
		Historique_Login historiqueLogin = new Historique_Login();
		historiqueLogin.setOuser(user);
		historiqueLoginRepository.save(historiqueLogin);
	}
	@Override
	public void addUser(KeycloakUser loginDto) throws APIErrorException {
		getRefreshToken();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(AUTHORIZATION, bearer + accessTokenAdmin);
		HttpEntity<KeycloakUser> request = new HttpEntity<>(loginDto, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				addUserUrl,
				HttpMethod.POST,
				request,
				String.class);
		if (response.getStatusCode() != HttpStatus.CREATED) {
			throw new APIErrorException(ErrorCode.E444);
		} else {
			System.out.println("user added successfully");
		}
	}
	/**
	 * Handles the signup process for a new user, including user creation in Keycloak, local user creation, role assignment, and company creation.
	 * This method performs several steps to ensure a new user is properly set up:
	 * 1. Checks if the provided password and confirmation password match.
	 * 2. Maps the {@link UserSignup} DTO to a {@link KeycloakUser} object for Keycloak.
	 * 3. Adds the new user to Keycloak using the {@code addUser} method.
	 * 4. Logs in the new user to obtain access tokens.
	 * 5. Retrieves the new user's information from Keycloak.
	 * 6. Assigns the specified role to the new user in Keycloak.
	 * 7. Converts the Keycloak user information to a local {@link User} entity and saves it.
	 * 8. Creates a new company associated with the user.
	 *
	 * @param userSignup The {@link UserSignup} DTO containing information for the new user's signup process.
	 * @throws APIErrorException If any step in the process fails, an {@link APIErrorException} is thrown with the appropriate error code.
	 */
	@Override
	public void signup(UserSignup userSignup) throws APIErrorException {
		if (userSignup == null){
			throw new APIErrorException(ErrorCode.A333);
		}
		else {
			passwordCheck(userSignup.getPassword(), userSignup.getConfirmPassword());
			KeycloakUser keycloakUser = userMapperService.mapToKeycloakUser(userSignup);
			PasswordInfo passwordInfo = new PasswordInfo();
			passwordInfo.setValue(userSignup.getPassword());
			passwordInfo.setTemporary(false);
			passwordInfo.setType("password");
			List<Object> list = new ArrayList<>();
			list.add(passwordInfo);
			keycloakUser.setCredentials(list);
			keycloakUser.setEnabled(true);
			addUser(keycloakUser);
			LoginDto loginDto = new LoginDto();
			loginDto.setPassword(userSignup.getPassword());
			loginDto.setUsername(userSignup.getEmail());
			login(loginDto);
			UserDto userDto = getInfo();
			RoleAssignment roleAssignment = new RoleAssignment();
			roleAssignment.setId(roleId);
			roleAssignment.setName("responsable");
			List<RoleAssignment> roleAssignments = new ArrayList<>();
			roleAssignments.add(roleAssignment);
			roleMappings(userDto.getIdKeycloak(), roleAssignments);
			User user = userServiceMapper.convertToEntity(userDto, User.class);
			user.setTelephone(userSignup.getTelephone());
			userRepository.save(user);

		}
	}
	@Override
	public boolean passwordCheck(String password , String passwordCofirmation) throws APIErrorException {
		if (!password.equals(passwordCofirmation)){
			throw new APIErrorException(ErrorCode.A020);
		}
		return true;
	}
	@Override
	public UserDto updateUser(String idKeycloak, UserUpdateDto userUpdateDto) throws APIErrorException {
     User user =  userRepository.findByIdKeycloak(idKeycloak);
	 if (user==null){
		 throw new APIErrorException(ErrorCode.A044);
	 }
	 else {
		 KeycloakUser keycloakUser = valideInsert(userUpdateDto);
		 updateUserKeycloack( keycloakUser, user.getIdKeycloak());
		 user.setEmail(userUpdateDto.getEmail());
		 user.setName(userUpdateDto.getName());
		 user.setFamillyName(userUpdateDto.getFamillyName());
		 userRepository.save(user);
		 return userServiceMapper.convertToDto(userRepository.save(user),UserDto.class);
    }
	}
	@Override
 public void updateUserKeycloack(KeycloakUser keycloakUser, String id){
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(AUTHORIZATION, bearer + accessTokenAdmin);
			HttpEntity<KeycloakUser> request = new HttpEntity<>(keycloakUser, headers);
			 restTemplate.exchange(UpdateUserUrl+"/"+id, HttpMethod.PUT, request, String.class);
		}

		@Override
		public KeycloakUser valideInsert(UserUpdateDto userUpdateDto) throws APIErrorException {
			KeycloakUser keycloakUser = new KeycloakUser();
		if (userUpdateDto==null){
			throw new APIErrorException(ErrorCode.A044);
		}
		else if (userUpdateDto.getFamillyName()!=null){
			keycloakUser.setLastName(userUpdateDto.getFamillyName());
			}
		else if (userUpdateDto.getName()!=null){
			keycloakUser.setFirstName(userUpdateDto.getName());			}
		else if (userUpdateDto.getEmail()!=null){
			keycloakUser.setEmail(userUpdateDto.getEmail());			}

			return keycloakUser;
	}
	@Override
	public void emailVerifie(String id) throws APIErrorException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set(AUTHORIZATION, bearer + accessTokenAdmin);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(CLIENT_ID, admin_Client);
		map.add("redirect_uri", redirect_uri);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		try {
		restTemplate.postForEntity(emailVerifie +id+"/send-verify-email", request, String.class).getBody();
		} catch (Exception e) {
			throw new APIErrorException(ErrorCode.E444);
		}
	}
	@Override
	public void resetPassword(ResetPassword resetPassword) throws APIErrorException {
		UserDto userDto = getInfo();
		LoginDto loginDto = new LoginDto();
		loginDto.setPassword(resetPassword.getOldPassword());
		loginDto.setUsername(userDto.getEmail());
		login(loginDto);
		System.out.println(userDto.getIdKeycloak());
		passwordCheck(resetPassword.getConfirmPassword(),resetPassword.getNewPassword());
		PasswordInfo passwordInfo = new PasswordInfo();
		passwordInfo.setType("password");
		passwordInfo.setValue(resetPassword.getNewPassword());
		passwordInfo.setTemporary(false);
		resetPasswordKeycloack(userDto.getIdKeycloak(), passwordInfo);
	}

	public void resetPasswordKeycloack(String id, PasswordInfo passwordInfo) throws APIErrorException {
		HttpHeaders headers = new HttpHeaders();
		getRefreshToken();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(AUTHORIZATION, bearer + accessTokenAdmin);
		HttpEntity<PasswordInfo> request = new HttpEntity<>(passwordInfo, headers);
//		try {
			restTemplate.exchange(passwordReset +"/"+id+"/reset-password",HttpMethod.PUT, request, String.class).getBody();
//		} catch (Exception e) {
//			throw new APIErrorException(ErrorCode.E444);
//		}
	}
	@Override
	public void roleMappings(String id, List<RoleAssignment> roleAssignment) throws APIErrorException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(AUTHORIZATION, bearer + accessTokenAdmin);
		HttpEntity<List<RoleAssignment>> request = new HttpEntity<>(roleAssignment, headers);
		try {
			restTemplate.postForEntity(roleMapping +"/"+id+"/"+roleMappingClients, request, String.class).getBody();
		} catch (Exception e) {
			throw new APIErrorException(ErrorCode.E444);
		}
	}


}





