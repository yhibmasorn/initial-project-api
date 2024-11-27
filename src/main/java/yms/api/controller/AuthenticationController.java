package yms.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import yms.api.model.request.LoginRequest;
import yms.api.model.response.LoginResponse;
import yms.api.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) throws Exception {
		LoginResponse response = new LoginResponse();
		
		response = authenticationService.login(loginRequest);

		return response;
		
	}
	
	@GetMapping("/logout")
	public void logout(HttpServletRequest request) throws Exception{
		String sessionID = request.getHeader("Authorization").substring(7);

		authenticationService.logout(sessionID);
		
	}
	
}
