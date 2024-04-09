package org.sid.ebankingbackend.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "api_user")
@Where(clause = "is_delete = false and is_statut = true")
public class User extends BaseModel {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = " id_keycloak")
	private String idKeycloak;

	@Column(name = " username")
	private String username;

	@Column(name = " name")
	private String name;

	@Column(name = "famillyName")
	private String famillyName;
	
	@Column(name = " email")
	private String email;
	
	@Column(name = "email_verified",columnDefinition = "boolean default false")
	private Boolean emailVerified;

	//@Column(name = "groups")
	//private List<String> groups;
	
	@Column(name = "telephone", nullable = true,unique=true)
	private String telephone;

	@Column(name = "date_naissance", nullable = true)
	private LocalDate dateNaissance;

	@Column(name = "accept_pub", nullable = true)
	private Boolean acceptPub;
	
	@Column(name = "is_valide_otp", nullable = true, columnDefinition = "boolean default false")
	private boolean isValideOTP;
	
	//@Column(name = "is_active", nullable = true, columnDefinition = "boolean default false")
	//private boolean isActive;
	//private List<String> roles;
	
	
}
