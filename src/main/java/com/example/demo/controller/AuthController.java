package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.RegisterDto;
import com.example.demo.service.AuthService;
import com.example.demo.service.LoginResult;
import com.example.demo.service.RegisterResult;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authenticationService;

	// Build Register REST API
	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {

		RegisterResult result = authenticationService.register(registerDto);
		if (result.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(result.getError(), HttpStatus.BAD_REQUEST);
		}
	}

	// Build Login REST API
	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {

		LoginResult result = authenticationService.login(loginDto);
		if(result.isSuccess()) {
			return ResponseEntity.ok(result.getToken());
		}
		return new ResponseEntity<>(result.getError(), HttpStatus.BAD_REQUEST);
	}
	
//	@GetMapping(value = "/whoami")
//	@PreAuthorize("isAuthenticated()")
//	public String personalInfo() {
//		authenticationService.accountInfo("")
//	}
}
