package org.sid.ebankingbackend.exceptions.exceptions.exceptionhandling;

import java.io.Serializable;


public interface ExceptionHandler extends Serializable {

	/**
	 * 
	 * Methode handle : Handle the exception.
	 *
	 * @param th
	 *            a throwable to handle.
	 */
	void handle(Throwable th);

}
