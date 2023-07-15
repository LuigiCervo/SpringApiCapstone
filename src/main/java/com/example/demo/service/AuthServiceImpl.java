package com.example.demo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.RegisterDto;
import com.example.demo.model.ERole;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public LoginResult login(LoginDto loginDto) {

		if (loginDto.getEmail() == null || loginDto.getEmail().isEmpty()) {
			return new LoginResult(false, "Email field cannot be null!.", null);
		}

		if (loginDto.getPassword() == null || loginDto.getPassword().isEmpty()) {
			return new LoginResult(false, "Password field cannot be null!.", null);
		}

		// Effettua un tentativo di connessione con i dati passati da DTO.+
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			String token = jwtTokenProvider.generateToken(authentication);
			return new LoginResult(true, null, token);
		} catch (BadCredentialsException e) {
			return new LoginResult(false, "Email or password are incorrect.", null);
		}
	}

	@Override
	public RegisterResult register(RegisterDto registerDto) {

		if (registerDto.getEmail() == null || registerDto.getEmail().isEmpty()) {
			return new RegisterResult(false, "Email field cannot be null!.");
		}
		
		// add check for email exists in database
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			return new RegisterResult(false, "Email already exists!.");
		}
		
		if (registerDto.getPassword() == null || registerDto.getPassword().isEmpty()) {
			return new RegisterResult(false, "Password field cannot be null!.");
		}
		
		if (registerDto.getFirstName() == null || registerDto.getFirstName().isEmpty()) {
			return new RegisterResult(false, "First name field cannot be null!.");
		}
		
		if (registerDto.getLastName() == null || registerDto.getLastName().isEmpty()) {
			return new RegisterResult(false, "Last name field cannot be null!.");
		}

		Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.findByRoleName(ERole.ROLE_USER).get());
	
		User user = new User();
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setFirstName(registerDto.getFirstName());
		user.setLastName(registerDto.getLastName());
		user.setRoles(roles);
		
		userRepository.save(user);

		return new RegisterResult(true, "User registered successfully!.");
	}
}
