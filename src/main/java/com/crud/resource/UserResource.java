package com.crud.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crud.resource.projection.UserDTO;
import com.crud.service.UserPhotoService;
import com.crud.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserResource {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserPhotoService userPhotoService;
	
	// the verbs speaks for itself
	@PostMapping
	public ResponseEntity<String> save(
			@RequestBody UserDTO userDTO) {
		
		userService.saveUser(userDTO.getName(), userDTO.getBirthDate());
		
		if (userService.hasError()) {
			return ResponseEntity.badRequest().body(userService.getAllErrors());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping(value = "/photo", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> savePhoto(@RequestParam Long code, @RequestBody MultipartFile file) {
		userPhotoService.fillDataUser(code, file);
		
		if (userPhotoService.hasError()) {
			return ResponseEntity.badRequest().body(userPhotoService.getAllErrors());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	// it could be very slow in swagger interface
	@GetMapping("/{code}")
	public ResponseEntity<Object> getByCode(@PathVariable Long code) {
		var user = userService.findUserByCode(code);
		
		if (user.isPresent() && ! userService.hasError()) 
			return ResponseEntity.ok(user.get());
		
		return ResponseEntity.badRequest().body(userService.getAllErrors());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<Object> getByName(@PathVariable String name) {
		var user = userService.findUserByName(name);
		
		if (user.isPresent() && ! userService.hasError()) 
			return ResponseEntity.ok(user.get());
		
		return ResponseEntity.badRequest().body(userService.getAllErrors());
	}
	
	@DeleteMapping("/{code}")
	public ResponseEntity<String> deleteUser(@PathVariable Long code) {
		var user = userService.findUserByCode(code);
		
		if (user.isPresent()  && ! userService.hasError()) {
			userService.deleteUser(user.get());
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.badRequest().body(userService.getAllErrors());
	}
}
