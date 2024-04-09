package org.sid.ebankingbackend.exceptions.exceptions;

public enum ErrorCode {
	
	E("Error Code"),//just for swagger documentation
	A001("Agence not found"),
	A0021("Client not found"),
	A0031("Depot not found"),
	A041("Agence not found"),
	A044("user not found"),
	A045("field is empty"),
	A002("Client not found"),
	A004("not found"),


	A205("Vous avez dépassé la taille maximale permise"),


	A003("no agence asigned to this company"),

	A005("athribut can not be null"),
	A006("no agence found"),
	A007("produit not found"),
	A008("Categorie not found"),
	A010("no company found"),
	A020(" password incorrect"),
	A333("user info can not be null"),
	A009("no product found for ths categorie"),
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
