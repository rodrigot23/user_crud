package com.crud.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserPhoto {

	@Column(name = "photo_name")
	private String name;
	
	@Column(name = "photo_content_type")
	private String contentType;

	@Column(name = "photo_bytes", length = 2000000)
	private Byte[] bytes;
	
	@Column(name = "photo_registration_date")
	private LocalDateTime registrationDate;
}
