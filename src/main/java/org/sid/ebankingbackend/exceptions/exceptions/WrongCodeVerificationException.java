 package org.sid.ebankingbackend.exceptions.exceptions;

public class WrongCodeVerificationException extends APIErrorException {

    private static final long serialVersionUID = 1L;

	public WrongCodeVerificationException(ErrorCode code) {
        super(code);
    }


}
