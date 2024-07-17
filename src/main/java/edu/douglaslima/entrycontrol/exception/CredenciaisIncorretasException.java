package edu.douglaslima.entrycontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "O nome de usuário ou senha estão incorretos!")
public class CredenciaisIncorretasException extends RuntimeException {

	public CredenciaisIncorretasException() {
		
	}
	
	public CredenciaisIncorretasException(String errorMessage) {
		super(errorMessage);
	}
	
	public CredenciaisIncorretasException(String errorMessage, Object... params) {
		super(String.format(errorMessage, params));
	}

	public CredenciaisIncorretasException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public CredenciaisIncorretasException(String errorMessage, Throwable cause, Object... params) {
		super(String.format(errorMessage, params), cause);
	}
	
}
