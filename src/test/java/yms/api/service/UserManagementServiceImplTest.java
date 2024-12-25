package yms.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import yms.api.model.request.SearchUserRequest;
import yms.api.model.response.GetAllUsersResponse;
import yms.api.model.response.SearchUsersResponse;
import yms.api.model.response.User;
import yms.api.repository.EmployeeRepository;
import yms.api.service.impl.UserManagementServiceImpl;

public class UserManagementServiceImplTest {

	@Mock
	private EmployeeRepository employeeRepository;
	
	@InjectMocks
	private UserManagementServiceImpl userManagementServiceImpl;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
	}
	
	@Test
	void getAllUsers() throws Exception{
		List<User> userList = new ArrayList<User>();
		
		User user = new User();
		user.setEmployeeNo("9999999");
		user.setTitleTh("Mr.");
		user.setTitleEn("Mr.");
		user.setFirstnameTh("Test Employee 11");
		user.setLastnameTh("Test Employee Lastname 11");
		user.setFirstnameEn("Test Employee 11");
		user.setLastnameEn("Test Employee Lastname 11");
		user.setUsername("Test Username");
		user.setRoleName("Test Role Name");
		user.setWorkStartDate("01/01/2020");
		user.setStatus("Active");
		
		userList.add(user);
		
		user = new User();
		user.setEmployeeNo("9999998");
		user.setTitleTh("Mr.");
		user.setTitleEn("Mr.");
		user.setFirstnameTh("Test Employee 12");
		user.setLastnameTh("Test Employee Lastname 12");
		user.setFirstnameEn("Test Employee 12");
		user.setLastnameEn("Test Employee Lastname 12");
		user.setUsername("Test Username");
		user.setRoleName("Test Role Name");
		user.setWorkStartDate("01/01/2020");
		user.setStatus("Active");
		
		userList.add(user);
		
		user = new User();
		user.setEmployeeNo("9999997");
		user.setTitleTh("Mr.");
		user.setTitleEn("Mr.");
		user.setFirstnameTh("Test Employee 13");
		user.setLastnameTh("Test Employee Lastname 13");
		user.setFirstnameEn("Test Employee 13");
		user.setLastnameEn("Test Employee Lastname 13");
		user.setUsername("Test Username");
		user.setRoleName("Test Role Name");
		user.setWorkStartDate("01/01/2020");
		user.setStatus("Active");
		
		userList.add(user);
		
		user = new User();
		user.setEmployeeNo("9999996");
		user.setTitleTh("Mr.");
		user.setTitleEn("Mr.");
		user.setFirstnameTh("Test Employee 14");
		user.setLastnameTh("Test Employee Lastname 14");
		user.setFirstnameEn("Test Employee 14");
		user.setLastnameEn("Test Employee Lastname 14");
		user.setUsername("Test Username");
		user.setRoleName("Test Role Name");
		user.setWorkStartDate("01/01/2020");
		user.setStatus("Active");
		
		userList.add(user);
		
		when(employeeRepository.findPaging(any(SearchUserRequest.class))).thenReturn(userList);
		
		GetAllUsersResponse response = userManagementServiceImpl.getAllUsers();
		
		assertEquals(4, response.getUserList().size());
		
	}
	
	@Test
	void testSearchUsers() throws Exception{
		List<User> userList = new ArrayList<User>();
		
		User user = new User();
		user.setEmployeeNo("9999999");
		user.setTitleTh("Mr.");
		user.setTitleEn("Mr.");
		user.setFirstnameTh("Test Employee 11");
		user.setLastnameTh("Test Employee Lastname 11");
		user.setFirstnameEn("Test Employee 11");
		user.setLastnameEn("Test Employee Lastname 11");
		user.setUsername("Test Username");
		user.setRoleName("Test Role Name");
		user.setWorkStartDate("01/01/2020");
		user.setStatus("Active");
		
		userList.add(user);
		
		user = new User();
		user.setEmployeeNo("9999998");
		user.setTitleTh("Mr.");
		user.setTitleEn("Mr.");
		user.setFirstnameTh("Test Employee 12");
		user.setLastnameTh("Test Employee Lastname 12");
		user.setFirstnameEn("Test Employee 12");
		user.setLastnameEn("Test Employee Lastname 12");
		user.setUsername("Test Username");
		user.setRoleName("Test Role Name");
		user.setWorkStartDate("01/01/2020");
		user.setStatus("Lock");
		
		userList.add(user);
		
		user = new User();
		user.setEmployeeNo("9999997");
		user.setTitleTh("Mr.");
		user.setTitleEn("Mr.");
		user.setFirstnameTh("Test Employee 13");
		user.setLastnameTh("Test Employee Lastname 13");
		user.setFirstnameEn("Test Employee 13");
		user.setLastnameEn("Test Employee Lastname 13");
		user.setUsername("Test Username");
		user.setRoleName("Test Role Name");
		user.setWorkStartDate("01/01/2020");
		user.setStatus("Active");
		
		userList.add(user);
		
		user = new User();
		user.setEmployeeNo("9999996");
		user.setTitleTh("Mr.");
		user.setTitleEn("Mr.");
		user.setFirstnameTh("Test Employee 14");
		user.setLastnameTh("Test Employee Lastname 14");
		user.setFirstnameEn("Test Employee 14");
		user.setLastnameEn("Test Employee Lastname 14");
		user.setUsername("Test Username");
		user.setRoleName("Test Role Name");
		user.setWorkStartDate("01/01/2020");
		user.setStatus("Lock");
		
		userList.add(user);
		
		user = new User();
		user.setEmployeeNo("9999995");
		user.setTitleTh("Mr.");
		user.setTitleEn("Mr.");
		user.setFirstnameTh("Test Employee 15");
		user.setLastnameTh("Test Employee Lastname 15");
		user.setFirstnameEn("Test Employee 15");
		user.setLastnameEn("Test Employee Lastname 15");
		user.setUsername("Test Username");
		user.setRoleName("Test Role Name");
		user.setWorkStartDate("01/01/2020");
		user.setStatus("Active");
		
		userList.add(user);
		
		user = new User();
		user.setEmployeeNo("9999994");
		user.setTitleTh("Mr.");
		user.setTitleEn("Mr.");
		user.setFirstnameTh("Test Employee 16");
		user.setLastnameTh("Test Employee Lastname 16");
		user.setFirstnameEn("Test Employee 16");
		user.setLastnameEn("Test Employee Lastname 16");
		user.setUsername("Test Username");
		user.setRoleName("Test Role Name");
		user.setWorkStartDate("01/01/2020");
		user.setStatus("Lock");
		
		userList.add(user);
		
		when(employeeRepository.findPaging(any(SearchUserRequest.class))).thenReturn(userList);
		when(employeeRepository.countFindPaging(any(SearchUserRequest.class))).thenReturn(userList.size());
		
		SearchUsersResponse response = userManagementServiceImpl.searchUsers(new SearchUserRequest());
		
		assertEquals(6, response.getTotalItems());
		assertEquals(1, response.getPage());
		assertEquals(1, response.getTotalPages());
		
	}
	
}
