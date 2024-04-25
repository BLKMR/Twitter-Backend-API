package com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException {

    /**
	 *
	 */
	private static final long serialVersionUID = -680859584886471904L;
		private String message;

}
