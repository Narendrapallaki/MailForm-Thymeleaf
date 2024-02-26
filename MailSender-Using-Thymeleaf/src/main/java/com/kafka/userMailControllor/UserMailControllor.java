package com.kafka.userMailControllor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import com.kafka.entity.User;

import com.kafka.userMailService.UserMailService;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class UserMailControllor {
	@Autowired
	private UserMailService mailService;

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/mailbox-page")
	public String submit() {
		return "mailBox";
	}

	@GetMapping("/test")
	public String testing() {
		return "test";

	}

	@PostMapping("/send-email")
	// @ResponseBody
	public String sendEmail(@ModelAttribute User user,
			@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestParam(name = "attFile", required = false) MultipartFile[] attFile)
			throws IOException, MessagingException {

		if (!file.isEmpty() && user.getToMail() != null) {

			log.info("If block execution...");
			mailService.excelFileSend(file, attFile, user);
			log.info("User controllor excelFile output {}", user);
		} else {

			log.info("else block execution.....!");

			mailService.singleMailWithAttach(attFile, user);

			log.info("User controllor singleFile output :{}");
			log.info("single with attached file {}", attFile.length);
		}

		log.info("email in controllor {}", user.getToMail());
		log.info("file in controllor {}", file.getOriginalFilename());

		return "redirect:/send";
	}

	@GetMapping("/send")
	public String result() {
		return "success";
	}

}
