package com.crud.resource.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private String name;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private String birthDate;
}
