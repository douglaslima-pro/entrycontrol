package edu.douglaslima.entrycontrol.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.douglaslima.entrycontrol.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = extractTokenFromRequest(request);
		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}
		String username = tokenService.getUsername(token);
		UserDetails user = userDetailsService.loadUserByUsername(username);
		if (tokenService.validateToken(token, user)) {
			UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(userAuthentication);
		}
		filterChain.doFilter(request, response);
	}

	private String extractTokenFromRequest(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		if (authorization != null && authorization.startsWith("Bearer")) {
			return authorization.replace("Bearer ", "");
		}
		return null;
	}

}
