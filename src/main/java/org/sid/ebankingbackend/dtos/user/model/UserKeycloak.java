package org.sid.ebankingbackend.dtos.user.model;

import lombok.Data;

import java.util.List;

@Data
public class UserKeycloak {
	private String sub;
	private Boolean email_verified;
	private String name;
	private String preferred_username;
	private String given_name;
	private String family_name;
	private String email;
	private List<String> agences;
	private List<String> roles;
}
