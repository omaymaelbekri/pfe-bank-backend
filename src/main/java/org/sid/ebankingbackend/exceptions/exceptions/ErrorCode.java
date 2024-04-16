package org.sid.ebankingbackend.exceptions.exceptions;

public enum ErrorCode {
	
	E("Error Code"),//just for swagger documentation
	A044("user not found"),
	A045("field is empty"),

	A004("not found"),


	A205("Vous avez dépassé la taille maximale permise"),


	A003("no agence asigned to this company"),

	A005("athribut can not be null"),

	A020(" password incorrect"),
	A333("user info can not be null"),
	A209("Une erreur système s'est produite"),
	A500("Une erreur système s'est produite"),
	E444("Error sur keycloak"),;
	
	

	private String code = "";

	ErrorCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
