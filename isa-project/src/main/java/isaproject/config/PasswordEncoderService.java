package isaproject.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface PasswordEncoderService {

	// Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
	// BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
	PasswordEncoder passwordEncoder();

}