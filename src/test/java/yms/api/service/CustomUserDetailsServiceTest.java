package yms.api.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import yms.api.model.Employee;
import yms.api.model.Role;
import yms.api.model.UserLogin;
import yms.api.repository.RolePermissionRepository;
import yms.api.repository.UserLoginRepository;

public class CustomUserDetailsServiceTest {

	@Mock
	private UserLoginRepository userLoginRepository;
	
	@Mock
	private RolePermissionRepository rolePermissionRepository;
	
	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
	}
	
	@Test
	void testLoadUserByUsername_UserFound() {
		// Given
		UserLogin userLogin = new UserLogin();
		userLogin.setUsername("testuser");
		userLogin.setPassword("hashedpassword");
		
		Employee employee = new Employee();
		employee.setEmployeeID(9999);
		employee.setEmployeeNo("9999");
		
		Role role = new Role();
		role.setRoleID(999);
		role.setRoleNo("999");
		
		employee.setRole(role);
		userLogin.setEmployee(employee);
		
		List<UserLogin> userLoginList = new ArrayList<UserLogin>();
		userLoginList.add(userLogin);
		
		List<String> permissionList = new ArrayList<String>();
		
		permissionList.add("00101");
		permissionList.add("00102");
		permissionList.add("00203");
		permissionList.add("00204");
		permissionList.add("00305");
		
		given(userLoginRepository.find(any(UserLogin.class))).willReturn(userLoginList);
		given(rolePermissionRepository.getPermisionNoList(anyString())).willReturn(permissionList);
		
		UserDetails userDetials = customUserDetailsService.loadUserByUsername("testuser");
		
		assertNotNull(userDetials);
		assertNotNull(userDetials.getUsername());
		assertNotNull(userDetials.getPassword());
		assertNotNull(userDetials.getAuthorities());
		
	}
	
	@Test
	public void testLoadUserByUsername_UserNotFound() {
		given(userLoginRepository.find(any(UserLogin.class))).willReturn(null);
		
		assertThrows(UsernameNotFoundException.class, () -> {
			customUserDetailsService.loadUserByUsername(anyString());
			
		});
		
	}
	
}
