package yms.api.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import yms.api.constant.CommonConstant;
import yms.api.model.Session;
import yms.api.model.UserLogin;
import yms.api.model.request.LoginRequest;
import yms.api.model.response.LoginResponse;
import yms.api.repository.SessionRepository;
import yms.api.repository.UserLoginRepository;
import yms.api.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private UserLoginRepository userLoginRepository;
	
	@Override
	public LoginResponse login(LoginRequest loginRequest) throws Exception {
		LoginResponse response = new LoginResponse();
		Date currentDate = new Date();
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		String sessionID = UUID.randomUUID().toString();
		
		UserLogin userLogin = new UserLogin();
		userLogin.setUsername(loginRequest.getUsername());
		userLogin.setIsDeleted(CommonConstant.FLAG_N);
		
		List<UserLogin> userLoginList = userLoginRepository.find(userLogin);
		
		if(userLoginList == null || userLoginList.isEmpty()) {
			throw new Exception("Not found username.");
			
		}
		
		userLogin = userLoginList.get(0);
		
		Calendar expireCal = Calendar.getInstance();
		expireCal.setTime(currentDate);
		expireCal.add(Calendar.MINUTE, 10);
		
		Session session = new Session();
		session.setSessionID(sessionID);
		session.setUserLogin(userLogin);
		session.setCreatedBy("SYSTEM");
		session.setCreatedDate(new Date());
		session.setExpireDate(expireCal.getTime());
		session.setIsDeleted(CommonConstant.FLAG_N);
		
		sessionRepository.insert(session);
		
		response.setSessionID(sessionID);
		
		return response;
	}

	@Override
	public void logout(String sessionID) throws Exception {
		sessionRepository.delete(sessionID, "SYSTEM", new Date());
		
	}

}
