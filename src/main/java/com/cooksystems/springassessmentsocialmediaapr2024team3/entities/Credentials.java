package com.cooksystems.springassessmentsocialmediaapr2024team3.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Credentials {
	
		@Column(nullable = false, unique = true)
    private String username;
		
		@Column(nullable = false)
    private String password;

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Credentials other = (Credentials) obj;
			return Objects.equals(password, other.password) && Objects.equals(username, other.username);
		}

		@Override
		public int hashCode() {
			return Objects.hash(password, username);
		}
}
