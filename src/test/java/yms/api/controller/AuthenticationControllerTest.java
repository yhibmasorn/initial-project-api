package yms.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.servlet.Filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.notNullValue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import yms.api.filter.TokenAuthenticationFilter;
import yms.api.model.request.LoginRequest;
import yms.api.model.response.LoginResponse;
import yms.api.service.AuthenticationService;

public class AuthenticationControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private AuthenticationService authenticationService;
	
	@InjectMocks
	private AuthenticationController authenticationController;
	
	private Filter tokenAuthenticationFilter = Mockito.mock(TokenAuthenticationFilter.class);
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		doNothing().when(tokenAuthenticationFilter).doFilter(Mockito.any(), Mockito.any(), Mockito.any());
		
		mockMvc = MockMvcBuilders.standaloneSetup(authenticationController)
				.addFilter(tokenAuthenticationFilter)
				.build();
		
	}
	
	@Test
	void testLoginSuccess() throws Exception{
		LoginRequest loginRequest = new LoginRequest();
		
		loginRequest.setUsername("testuser");
		loginRequest.setPassword("testpassword");
		
		LoginResponse loginResponse = new LoginResponse();
		
		loginResponse.setSessionID(any(String.class));
		
		// Mocking
		when(authenticationService.login(loginRequest)).thenReturn(loginResponse);
		
		// When & Then
		String inputRequest = """
					{"username": "testuser", "password": "testpassword"}
				""";
		
		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(inputRequest))
				.andExpect(status().isOk());
			
	}
	
	@Disabled
	@Test
	void testLoginFail() throws Exception{
		// Mocking
		when(authenticationService.login(any(LoginRequest.class))).thenThrow(new Exception("Invalid username or password."));
		
		// When & Then
		String inputRequest = """
					{"username": "testuser1", "password": "testpassword1"}
				""";
		
		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(inputRequest))
				.andExpect(status().isUnauthorized());
			
	}
	
	@Test
	void testLogoutSuccess() throws Exception{
		String sessionID = "614209bf-8b89-458c-b877-9698db8943db";
		
		doNothing().when(authenticationService).logout(sessionID);
		
		mockMvc.perform(get("/auth/logout")
				.header("Authorization", "Bearer " + sessionID))
				.andExpect(status().isOk());
		
	}
	
	@Test
	void testWelcomeSuccess() throws Exception{
		String sessionID = "614209bf-8b89-458c-b877-9698db8943db";

		mockMvc.perform(get("/welcome")
				.header("Authorization", "Bearer " + sessionID))
				.andExpect(status().isOk());
		
	}
	
}
