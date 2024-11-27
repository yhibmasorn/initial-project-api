package yms.api.service;

import yms.api.model.request.LoginRequest;
import yms.api.model.response.LoginResponse;

public interface AuthenticationService {

	public LoginResponse login(LoginRequest loginRequest) throws Exception;
	public void logout(String sessionID) throws Exception;
	
}
