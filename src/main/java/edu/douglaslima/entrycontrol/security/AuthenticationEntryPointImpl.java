package edu.douglaslima.entrycontrol.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Classe que implementa a interface {@link AuthenticationEntryPoint} e é utilizada para tratar as exceções do tipo {@link AuthenticationException} lançadas quando um usuário não autorizado tenta acessar determinado recurso.
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "Você não possui permissão para acessar o recurso solicitado.");
	}

}
