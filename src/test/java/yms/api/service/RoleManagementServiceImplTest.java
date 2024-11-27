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

import yms.api.model.Role;
import yms.api.model.response.ListRoleResponse;
import yms.api.repository.RoleRepository;
import yms.api.service.impl.RoleManagementServiceImpl;

public class RoleManagementServiceImplTest {

	@Mock
	private RoleRepository roleRepository;
	
	@InjectMocks
	private RoleManagementServiceImpl roleManagementServiceImpl;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
	}
	
	@Test
	void listRole() throws Exception{
		List<Role> roleList = new ArrayList<Role>();
		
		Role role = new Role();
		role.setRoleID(1);
		role.setRoleNo("001");
		role.setRoleName("Maker");
		roleList.add(role);
		
		role = new Role();
		role.setRoleID(2);
		role.setRoleNo("002");
		role.setRoleName("Checker");
		roleList.add(role);
		
		role = new Role();
		role.setRoleID(3);
		role.setRoleNo("003");
		role.setRoleName("Approver");
		roleList.add(role);
		
		when(roleRepository.find(any(Role.class))).thenReturn(roleList);
		
		ListRoleResponse response = roleManagementServiceImpl.listRole();
		
		assertEquals(response.getRoleList().size(), 3);
		
	}
	
}
