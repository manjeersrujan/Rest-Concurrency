package com.agoda.exercise.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.agoda.exercise.model.GenericServiceResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice("com.agoda")
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	public ExceptionControllerAdvice() throws JsonParseException, JsonMappingException, IOException {
		super();
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("errorConfig.json");
		HashMap<String, LinkedHashMap> errorHashMapMap = (HashMap<String, LinkedHashMap>) new ObjectMapper()
				.readValue(in, HashMap.class);

		for (String key : errorHashMapMap.keySet()) {
			LinkedHashMap<String, Object> errorMessageHashMap = errorHashMapMap.get(key);
			errorCodeMap.put(key, new AgodaServiceError((Integer) errorMessageHashMap.get("status"),
					(String) errorMessageHashMap.get("errorMessage")));
		}
	}

	Map<String, AgodaServiceError> errorCodeMap = new HashMap<>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(AgodaServiceException.class)
	@ResponseBody
	ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
		AgodaServiceError agodaServiceError = null;
		if (ex != null) {
			ex.printStackTrace();
			agodaServiceError = errorCodeMap.get(ex.getMessage());
			if (agodaServiceError == null) {
				agodaServiceError = AgodaServiceError.getGenericError();
			} else if (ex instanceof AgodaServiceException) {
				if (ex.getMessage() == null) {
					agodaServiceError = AgodaServiceError.getGenericError();
				} else {
					agodaServiceError = errorCodeMap.get(ex.getMessage());
					if (agodaServiceError == null) {
						agodaServiceError = AgodaServiceError.getGenericError();
					}
				}

			} else {
				agodaServiceError = AgodaServiceError.getGenericError();
			}
		} else {
			agodaServiceError = AgodaServiceError.getGenericError();
		}
		return new ResponseEntity<GenericServiceResponse>(
				new GenericServiceResponse("FAIL", agodaServiceError.errorMessage, null),
				HttpStatus.valueOf(agodaServiceError.status));
	}
}