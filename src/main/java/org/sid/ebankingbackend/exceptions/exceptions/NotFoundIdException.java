package org.sid.ebankingbackend.exceptions.exceptions;

public class NotFoundIdException extends APIErrorException {


    private static final long serialVersionUID = 1L;

	public NotFoundIdException(ErrorCode code) {
        super(code);
    }


}
