 package com.openjr.myfinances.configurations.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.openjr.myfinances.model.User;
import com.openjr.myfinances.repository.UserRepository;

public class AuthenticationByTokenFilter extends OncePerRequestFilter{
	
	private TokenService tokenService;
	private UserRepository userRepository;
	
	public AuthenticationByTokenFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recoveryToken(request);
		boolean isValidToken = tokenService.isValidToken(token);
		if (isValidToken) {
			authenticateUser(token);
		}
		filterChain.doFilter(request, response);
		
	}

	private void authenticateUser(String token) {
		Long idUser = this.tokenService.getIdUser(token);
		Optional<User> user = userRepository.findById(idUser);
		
		if (user.isPresent()) {
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
	}

	private String recoveryToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
