package com.crud.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ErrorNotification {
	
	private List<String> errors = new ArrayList<>();

	public void addError(String message) {
		this.errors.add(message);
	}

	public boolean hasError() {
		return !this.errors.isEmpty();
	}

	public String getAllErrors() {
		var errors = this.errors.stream().collect(Collectors.joining(", "));
		this.errors = new ArrayList<>();
		return errors;
	}
}
