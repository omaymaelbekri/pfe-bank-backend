package org.sid.ebankingbackend.exceptions.exceptions.exceptionhandling;

import org.sid.ebankingbackend.exceptions.exceptions.APIErrorException;
import org.sid.ebankingbackend.exceptions.exceptions.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Logger l'exception dans un fichier de log.
 *
 * @author HRAIDA El Mehdi
 *
 */
@Service
class LoggerExceptionHandler extends DefaultExceptionHandler {

	/**
	 *
	 */
	private static final long serialVersionUID = -4903737957069437751L;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerExceptionHandler.class);

	@Override
	protected void log(String message, String user, Throwable th) {

		StringBuilder messageToLogBuilder = new StringBuilder("  ");
		if(message != null) {
			messageToLogBuilder.append(message).append(" , ");
		}
		
		if (user != null) {
			messageToLogBuilder.append(" Logged In User : [" + user + "] , ");
		}
		
		if(th instanceof APIErrorException) {
			// Ne pas tracer la stacktrace
			
			ApiError apiError = ((APIErrorException) th).getApiError();
			if(apiError != null) {
				messageToLogBuilder.append("A functional exception occured. error code : ").append(apiError.getCode()).append(" , error description : ").append(apiError.getDescription());
			}
			StackTraceElement stackTraceElement = th.getStackTrace()[0];
			
			messageToLogBuilder.append(System.lineSeparator());
			messageToLogBuilder.append("Source Class : [" + stackTraceElement.getClassName() + "]").append(" , Method Name : [" + stackTraceElement.getMethodName() + "]").append(" , Line Number : [" + stackTraceElement.getLineNumber() + "]");
			
			String messageToLog = messageToLogBuilder.toString();
			LOGGER.error(messageToLog);
		}
		else {
			String messageToLog = messageToLogBuilder.toString();
			LOGGER.error(messageToLog, th);
		}
		
	}

}
