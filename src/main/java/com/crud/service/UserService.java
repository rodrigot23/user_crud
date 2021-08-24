package com.crud.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.domain.model.User;
import com.crud.domain.repository.UserRepository;
import com.crud.notification.ErrorNotification;

@Service
public class UserService extends ErrorNotification {
	
	@Autowired
	private UserRepository userRepository;
	
	public void saveUser(String name, String birthDate) {
		
		if ( ! birthDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
			super.addError("Date format must be 'dd/MM/yyyy'");
			return;
		}
		
		var localBirthDate = LocalDate.from(DateTimeFormatter.ofPattern("dd/MM/yyyy").parse(birthDate));
		
		User user = User.builder()
				.name(name)
				.birthDate(localBirthDate)
				.build();
		
		userRepository.save(user);
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	public Optional<User> findUserByCode(Long code) {
		return userRepository.findById(code);
	}

	public Optional<List<User>> findUserByName(String name) {
		return userRepository.findByNameLike("%"+name.replace(" ", "%")+"%");
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}
}
