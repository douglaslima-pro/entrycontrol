package edu.douglaslima.entrycontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "O token é inválido ou está em um formato incorreto!")
public class TokenInvalidoException extends RuntimeException {

	public TokenInvalidoException() {
		
	}
	
	public TokenInvalidoException(String errorMessage) {
		super(errorMessage);
	}
	
	public TokenInvalidoException(String errorMessage, Object... params) {
		super(String.format(errorMessage, params));
	}

	public TokenInvalidoException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public TokenInvalidoException(String errorMessage, Throwable cause, Object... params) {
		super(String.format(errorMessage, params), cause);
	}
	
}
