package com.httq.system.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.httq.dto.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorAPI implements ErrorController {
	@Autowired
	private ObjectMapper objectMapper;

	@RequestMapping(value = "/error", produces = "application/json")
	public String handleError(HttpServletRequest request) throws JsonProcessingException {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		BaseResponse<String> response = new BaseResponse<String>();

		if (status != null) {
			int        statusCode = Integer.parseInt(status.toString());
			HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
			response.setStatus(statusCode);
			response.setMsg(httpStatus.toString());
		}else{
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
		return objectMapper.writeValueAsString(response);
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
