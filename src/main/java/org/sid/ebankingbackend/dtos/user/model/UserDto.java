package org.sid.ebankingbackend.dtos.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "User")
public class UserDto {
	
	@JsonProperty("uuid")
	@Schema(name = "uuid", example = "uuid", required = true )
	private String uuid;

	@JsonProperty("username")
	@Schema(name = "username", example = "User_2001", required = true )
	private String username;

	private String name;


	private String famillyName;


	@JsonProperty("email")
	@Schema(name = "email", example = "user@Eqdom.co.ma", required = true )
	private String email;

	@JsonProperty("email_verified")
	@Schema(name = "email_verified", example = "true", required = true )
	private Boolean emailVerified;

	@JsonProperty("groups")
	@Schema(name = "groups", example = "CASA", required = true )
	//private List<String> groups;
    private String idKeycloak;
	//private List<List<String>> roles;
}
