package com.kafka.userMailService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.kafka.entity.EmailData;
import com.kafka.entity.User;
import com.kafka.excelReader.ExcelReader;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserMailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine engine;

	public void excelFileSend(MultipartFile file, MultipartFile[] attach,User user)
			throws IOException, MessagingException {

		log.info("UserService running...!");
		List<EmailData> excelData = ExcelReader.readExcelData(file);

		log.info("Excel reader result : {}", excelData);

		String exMail = "";
		String exName = "";

		for (EmailData emailData : excelData) {

			exMail = emailData.getEmail();
			exName = emailData.getName();

			// }
			log.info("After ForEach exMail :{}", exMail);
			log.info("excel exName : {}", exName);

			MimeMessage mimeMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setTo(exMail);
			helper.setSubject(user.getSubject());

			helper.setText(user.getText());

			Context con = new Context();
			con.setVariable("name", exName);

			con.setVariable("text", user.getText());

			String process = engine.process("mail-template.html", con);

			helper.setText(process, true);

			for (MultipartFile mf : attach) {// this line of code is for sending multiple file at a time

				ByteArrayResource iss = new ByteArrayResource(mf.getBytes());

				helper.addAttachment(mf.getOriginalFilename(), iss);

			}
			mailSender.send(mimeMessage);
		}
		log.info("mail sended.....!");

	}

	public void singleMailWithAttach(MultipartFile[] attach, User user) throws IOException, MessagingException {

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setTo(user.getToMail());
		helper.setSubject(user.getSubject());

		helper.setText(user.getText());

		Context con = new Context();
		String name = "All";
		con.setVariable("user", name);

		con.setVariable("text", user.getText());

		String process = engine.process("home-template.html", con);

		helper.setText(process, true);

		for (MultipartFile mf : attach) {// this line of code is for sending multiple file at a time

			ByteArrayResource iss = new ByteArrayResource(mf.getBytes());

			helper.addAttachment(mf.getOriginalFilename(), iss);

		}
		mailSender.send(mimeMessage);
		log.info("mail sended.....!");
	}

}
