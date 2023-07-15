package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDto {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}

// Il client dovrà inviare un oggetto JSON nel body con questa forma
/*
 * { "fullname": "Francesca Neri", "username": "francescaneri", "email":
 * "f.neri@example.com", "password": "qwerty", "roles": ["MODERATOR", "USER"] }
 */
