package com.crud.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.crud.domain.model.User;
import com.crud.domain.model.UserPhoto;
import com.crud.notification.ErrorNotification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserPhotoService extends ErrorNotification {

	@Autowired
	private UserService userService;

	public void fillDataUser(Long code, MultipartFile photo) {
		log.info("Storing user photo");
		Optional<User> user = userService.findUserByCode(code);

		if (user.isEmpty()) {
			super.addError("User code wasn't found");
		}
		
		if ( ! photo.getContentType().matches("^image/(jpeg|png|jpg)$")) {
			super.addError("Extension not allowed");
		}
		
		String fileName = photo.getOriginalFilename();

		Byte[] bytes = fileToBynary(photo);

		user.ifPresent(u -> {
			u.setUserPhoto(
				UserPhoto
				.builder()
				.name(fileName)
				.bytes(bytes)
				.contentType(photo.getContentType())
				.registrationDate(LocalDateTime.now())
				.build()
			);
			userService.saveUser(u);
		});
		
		if (super.hasError())
			log.error(getAllErrors());
	}

	private Byte[] fileToBynary(MultipartFile photo) {
		try {
			byte[] bytes = photo.getInputStream().readAllBytes();

			Byte[] byteObj = new Byte[bytes.length];

			for (int i = 0; i < bytes.length; i++) {
				byteObj[i] = bytes[i];
			}

			return byteObj;

		} catch (IOException e) {
			super.addError(e.getMessage());
			return new Byte[0];
		}
	}
}
