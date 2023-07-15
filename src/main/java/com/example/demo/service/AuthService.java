package com.example.demo.service;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.RegisterDto;

public interface AuthService {
	RegisterResult register(RegisterDto registerDto);

	LoginResult login(LoginDto loginDto);
}
