package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LoginDto {
	private String email;
	private String password;
}

//Il client dovrà inviare un oggetto JSON nel body con questa forma
/*
 * { "username": "francescaneri", "password": "qwerty" }
 */
