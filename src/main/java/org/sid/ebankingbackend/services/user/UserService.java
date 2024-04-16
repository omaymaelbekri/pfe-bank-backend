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
		insertUser(userKeycloak);
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
	public List<User> getAllUser() {
		//  try {
		List<User> users = userRepository.findAll();

		return users;
		// } catch (Exception e) {
		//      throw new APIErrorException(ErrorCode.E500);
		// }
	}





}





