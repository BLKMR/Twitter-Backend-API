package com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {

    /**
	 *
	 */
	private static final long serialVersionUID = -6831669551120149681L;
		private String message;

}
