package yms.api.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public static String hashPassword(String plainPassword) {
		return passwordEncoder.encode(plainPassword);
		
	}
	
	public static void main(String[] args) {
		String rawPassword = "P@ssw0rd";
		String hashedPassword = hashPassword(rawPassword);
		
		System.out.println("Raw Password: " + rawPassword);
		System.out.println("Hashed Password: " + hashedPassword);
		
	}
	
}
