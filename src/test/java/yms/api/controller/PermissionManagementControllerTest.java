package yms.api.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.servlet.Filter;
import yms.api.filter.TokenAuthenticationFilter;
import yms.api.model.response.GetPermissionResponse;
import yms.api.service.PermissionManagementService;

public class PermissionManagementControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private PermissionManagementService permissionManagementService;
	
	@InjectMocks
	private PermissionManagementController permissionManagementController;
	
	private Filter tokenAuthenticationFilter = Mockito.mock(TokenAuthenticationFilter.class);
	
	@BeforeEach
	void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);
		
		doNothing().when(tokenAuthenticationFilter).doFilter(Mockito.any(), Mockito.any(), Mockito.any());
		
		
		mockMvc = MockMvcBuilders.standaloneSetup(permissionManagementController)
				.addFilter(tokenAuthenticationFilter)
				.build();
		
	}
	
	@Test
	void updatePermission() throws Exception{
		String sessionID = "614209bf-8b89-458c-b877-9698db8943db";
		
		doNothing().when(permissionManagementService).updatePermission(anyString(), anyList());
		
		mockMvc.perform(post("/permissions")
				.header("Authorization", "Bearer " + sessionID))
				.andExpect(status().isOk());
		
	}
	
	@Test
	void getPermission() throws Exception{
		String sessionID = "614209bf-8b89-458c-b877-9698db8943db";
		
		GetPermissionResponse response = new GetPermissionResponse();
		response.setPermissionNoList(List.of("00101"));
		
		when(permissionManagementService.getPermission(anyString())).thenReturn(response);
		
		mockMvc.perform(get("/permissions/001")
				.header("Authorization", "Bearer " + sessionID))
				.andExpect(status().isOk());
		
	}
	
}
