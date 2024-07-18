package edu.douglaslima.entrycontrol.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.douglaslima.entrycontrol.exception.ResponseError;
import edu.douglaslima.entrycontrol.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>
 * Realiza o tratamento das exceções lançadas pela classe {@link TokenService}.
 * </p>
 * <p>
 * <strong>Obs.:</strong> Esse filtro deve ser adicionado antes do filtro {@link TokenFilter} na classe {@link WebSecurityConfig}.
 * </p>
 * <strong>Catches:</strong>
 * <ul>
 * <li>{@link ExpiredJwtException}</li>
 * <li>{@link JwtException}</li>
 * </ul>
 */
@Component
public class TokenExceptionHandlerFilter extends OncePerRequestFilter {
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			long milissegundos = new Date().getTime() - e.getClaims().getExpiration().getTime();
			LocalDateTime dataExpiracao = e.getClaims()
					.getExpiration()
					.toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDateTime();
			String mensagem = String.format("O token expirou há %d milissegundos atrás em %s.", milissegundos, dataExpiracao.toString());
			ResponseError responseBody = new ResponseError(HttpServletResponse.SC_UNAUTHORIZED, "error-token-expirado", mensagem);
			String path = ServletUriComponentsBuilder
					.fromRequest(request)
					.toUriString();
			responseBody.setPath(path);
			responseBody.setDetail("Faça login novamente e utilize o token gerado para autenticar o acesso.");
			objectMapper.writeValue(response.getOutputStream(), responseBody);
		} catch (JwtException e) {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ResponseError responseBody = new ResponseError(HttpServletResponse.SC_BAD_REQUEST, "error-token-invalido", "Não foi possível ler o token informado no cabeçalho 'Authorization'. O token é inválido ou está em um formato incorreto.");
			String path = ServletUriComponentsBuilder
					.fromRequest(request)
					.toUriString();
			responseBody.setPath(path);
			responseBody.setDetail("Verifique se o token informado no cabeçalho 'Authorization' é um token Bearer válido.");
			objectMapper.writeValue(response.getOutputStream(), responseBody);
		}
	}

}
