package org.sid.ebankingbackend.exceptions.exceptions.exceptionhandling;



abstract class DefaultExceptionHandler implements ExceptionHandler {

	private static final long serialVersionUID = 2270868033669621899L;

	private static final String USER = "UNKNOWN-USER";

	public void handle(Throwable th) {
		String message = th.getMessage();
		String user = getUser();

		log(message, user, th);
	}

	/**
	 * Get authenticated user.
	 * 
	 * @return
	 */
	private String getUser() {

		// For the moment return a common one.
		return USER;
	}

	protected abstract void log(String message, String user, Throwable th);

}
