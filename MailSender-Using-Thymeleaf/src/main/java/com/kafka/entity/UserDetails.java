package com.kafka.entity;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
	
	private String subject;
	private MultipartFile file;
	private String toMail;
	private MultipartFile attachements;
	private String text;

}
