package com.crud.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class UserResourceTests {

	@Autowired
	public MockMvc mvc;

	@Test
	@Order(1)
	void saveTest() throws Exception {
		var body = new HashMap<String, String>();
		body.put("name", "Foo Bar");
		body.put("birthDate", "10/01/2000");

		var json = new JSONObject(body);

		mvc.perform(
				post("/api/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()))
		.andExpect(status().isCreated());
	}

	@Test
	@Order(2)
	void savePhotoTest() throws Exception {
		MockMultipartFile firstFile = new MockMultipartFile("file", "user_photo.png", MediaType.IMAGE_PNG_VALUE, "data".getBytes());

		mvc.perform(multipart("/api/user/photo")
				.file(firstFile)
				.param("code", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
		.andExpect(status().isCreated());
	}

	@Test
	@Order(3)
	public void getUserByNameTest() throws Exception {

		mvc.perform(get("/api/user/name/Foo"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].code", is(1)))
		.andExpect(jsonPath("$.[0].name", is("Foo Bar")))
		.andExpect(jsonPath("$.[0].birthDate", is("2000-01-10")))
		.andExpect(jsonPath("$.[0].userPhoto.name", is("user_photo.png")))
		.andExpect(jsonPath("$.[0].userPhoto.contentType", is("image/png")))
		.andExpect(jsonPath("$.[0].userPhoto.bytes", is(List.of(100, 97, 116, 97))));

	}
	
	@Test
	@Order(4)
	public void deleteUser() throws Exception {
		mvc.perform(get("/api/user/1")).andExpect(status().isOk());
	}
}
