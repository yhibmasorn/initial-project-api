package yms.api.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import yms.api.model.Session;
import yms.api.model.UserLogin;
import yms.api.model.request.LoginRequest;
import yms.api.model.response.LoginResponse;
import yms.api.repository.SessionRepository;
import yms.api.repository.UserLoginRepository;
import yms.api.service.impl.AuthenticationServiceImpl;

public class AuthenticationServiceImplTest {

	@Mock
	private AuthenticationManager authenticationManager;
	
	@Mock
	private SessionRepository sessionRepository;
	
	@Mock
	private UserLoginRepository userLoginRepository;
	
	@InjectMocks
	private AuthenticationServiceImpl authenticationServiceImpl;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

	}
	
	@Test
	void testLoginSuccess() throws Exception{
		// Given
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("testusername");
		loginRequest.setPassword("testpassword");
		
		Authentication auth = new UsernamePasswordAuthenticationToken("testusername", "testpassword");
		
		UserLogin userLogin = new UserLogin();
		userLogin.setUserLoginID(1234);
		userLogin.setUsername("testusername");
		
		List<UserLogin> userLoginList = new ArrayList<UserLogin>();
		userLoginList.add(userLogin);
		
		Session session = new Session();
		session.setSessionID("12345");
		session.setExpireDate(new Date());
		session.setCreatedBy("SYSTEM");
		session.setCreatedDate(new Date());
		
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
		when(userLoginRepository.find(any(UserLogin.class))).thenReturn(userLoginList);
		doNothing().when(sessionRepository).insert(session);
		
		LoginResponse loginResponse = authenticationServiceImpl.login(loginRequest);
		
		assertNotNull(loginResponse);
		assertNotNull(loginResponse.getSessionID());
		
	}
	
	@Test
	void testLoginFailure() throws Exception{
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new AuthenticationException("Invalid credentials") {});
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("testusername");
		loginRequest.setPassword("wrongpassword");
		
		assertThrows(AuthenticationException.class, () -> {
			authenticationServiceImpl.login(loginRequest);
		});
		
	}
	
	@Test
	void testLogout() throws Exception {
		String sessionID = "1234";

		authenticationServiceImpl.logout(sessionID);
		
		verify(sessionRepository).delete(eq(sessionID), eq("SYSTEM"), any(Date.class));
		
	}
	
}
