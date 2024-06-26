package org.sid.ebankingbackend.exceptions.exceptions;


public class APIErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4865725609895358409L;
	
	
	private final ApiError apiError;

	public APIErrorException(ErrorCode code) {
		super();
		this.apiError = new ApiError(code, code.toString());
	}


}
