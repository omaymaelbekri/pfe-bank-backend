package org.sid.ebankingbackend.dtos.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReponseTokenDto {
	
	public ReponseTokenDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	@JsonProperty("access_token")
	public String accessToken;
	
	@JsonProperty("expires_in")
    public Long expiresIn;
	
	@JsonProperty("refresh_expires_in")
    public Long refreshExpiresIn;
	
	@JsonProperty("refresh_token")
    public String refreshToken;
	
	@JsonProperty("token_type")
    public String tokenType;
    
    @JsonProperty("not_before_policy")
    public Long notBeforePolicy;
    
    @JsonProperty("session_state")
    public String sessionState;
    
    @JsonProperty("scope")
    public String scope;


}
