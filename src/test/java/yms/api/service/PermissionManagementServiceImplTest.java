package yms.api.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import yms.api.model.Menu;
import yms.api.model.PermissionAction;
import yms.api.model.Role;
import yms.api.model.response.GetPermissionResponse;
import yms.api.repository.MenuRepository;
import yms.api.repository.PermissionActionRepository;
import yms.api.repository.RolePermissionRepository;
import yms.api.repository.RoleRepository;
import yms.api.service.impl.PermissionManagementServiceImpl;

public class PermissionManagementServiceImplTest {

	@Mock
	private RoleRepository roleRepository;
	
	@Mock
	private MenuRepository menuRepository;
	
	@Mock
	private PermissionActionRepository permissionActionRepository;
	
	@Mock
	private RolePermissionRepository rolePermissionRepository;
	
	@InjectMocks
	private PermissionManagementServiceImpl permissionManagementServiceImpl;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
	}
	
	@Test
	void updatePermission() throws Exception{
		String roleNo = "001";
		List<String> permissionList = new ArrayList<String>();
		
		permissionList.add("00101");
		permissionList.add("00102");
		permissionList.add("00203");
		permissionList.add("00204");
		permissionList.add("00305");
		
		Role role = new Role();
		role.setRoleID(1);
		role.setRoleNo("001");
		role.setRoleName("Maker");
		
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(role);
		
		when(roleRepository.find(any(Role.class))).thenReturn(roleList);
	
		when(menuRepository.find(any(Menu.class))).thenAnswer((Answer<List<Menu>>) invocation ->{
			Menu arg = (Menu) invocation.getArgument(0);
			List<Menu> menuList = new ArrayList<Menu>();
			
			if("001".equals(arg.getMenuNo())) {
				Menu menu = new Menu();
				menu.setMenuID(1);
				menu.setMenuNo(arg.getMenuNo());
				menu.setMenuName("Create Loan");
				
				menuList.add(menu);
				
			}else if("002".equals(arg.getMenuNo())) {
				Menu menu = new Menu();
				menu.setMenuID(2);
				menu.setMenuNo(arg.getMenuNo());
				menu.setMenuName("Approve Loan");
				
				menuList.add(menu);
				
			}else if("003".equals(arg.getMenuNo())) {
				Menu menu = new Menu();
				menu.setMenuID(3);
				menu.setMenuNo(arg.getMenuNo());
				menu.setMenuName("User Management");
				
				menuList.add(menu);
				
			}
			
			return menuList;
			
		});
		
		when(permissionActionRepository.find(any(PermissionAction.class))).thenAnswer((Answer<List<PermissionAction>>) invocation ->{
			PermissionAction arg = (PermissionAction) invocation.getArgument(0);
			List<PermissionAction> permissionActionList = new ArrayList<PermissionAction>();
			
			if("01".equals(arg.getPermissionActionNo())) {
				PermissionAction permissionAction = new PermissionAction();
				permissionAction.setPermissionActionID(1);
				permissionAction.setPermissionActionNo(arg.getPermissionActionNo());
				permissionAction.setPermissionActionName("Create");
				
				permissionActionList.add(permissionAction);
				
			}else if("02".equals(arg.getPermissionActionNo())) {
				PermissionAction permissionAction = new PermissionAction();
				permissionAction.setPermissionActionID(2);
				permissionAction.setPermissionActionNo(arg.getPermissionActionNo());
				permissionAction.setPermissionActionName("View");
				
				permissionActionList.add(permissionAction);
				
			}else if("03".equals(arg.getPermissionActionNo())) {
				PermissionAction permissionAction = new PermissionAction();
				permissionAction.setPermissionActionID(3);
				permissionAction.setPermissionActionNo(arg.getPermissionActionNo());
				permissionAction.setPermissionActionName("Edit");
				
				permissionActionList.add(permissionAction);
				
			}else if("04".equals(arg.getPermissionActionNo())) {
				PermissionAction permissionAction = new PermissionAction();
				permissionAction.setPermissionActionID(4);
				permissionAction.setPermissionActionNo(arg.getPermissionActionNo());
				permissionAction.setPermissionActionName("Search");
				
				permissionActionList.add(permissionAction);
				
			}else if("05".equals(arg.getPermissionActionNo())) {
				PermissionAction permissionAction = new PermissionAction();
				permissionAction.setPermissionActionID(5);
				permissionAction.setPermissionActionNo(arg.getPermissionActionNo());
				permissionAction.setPermissionActionName("Approve");
				
				permissionActionList.add(permissionAction);
				
			}
			
			return permissionActionList;
			
		});
		
		doNothing().when(rolePermissionRepository).delete(anyInt(), anyString(), any(Date.class));
		doNothing().when(rolePermissionRepository).insert(anyList());
		
		assertDoesNotThrow(() -> permissionManagementServiceImpl.updatePermission(roleNo, permissionList));

	}
	
	@Test
	public void updatePermissionInvalidRole() throws Exception{
		String roleNo = "999";
		
		List<String> permissionList = new ArrayList<String>();
		
		permissionList.add("00101");
		permissionList.add("00102");
		permissionList.add("00203");
		permissionList.add("00204");
		permissionList.add("00305");
		
		when(roleRepository.find(any(Role.class))).thenReturn(new ArrayList<Role>());
		
		assertThrows(Exception.class, () -> permissionManagementServiceImpl.updatePermission(roleNo, permissionList));

	}
	
	@Test
	public void updatePermissionInvalidMenu() throws Exception{
		String roleNo = "001";
		List<String> permissionList = new ArrayList<String>();
		
		permissionList.add("99901");
		
		Role role = new Role();
		role.setRoleID(1);
		role.setRoleNo("001");
		role.setRoleName("Maker");
		
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(role);
		
		when(roleRepository.find(any(Role.class))).thenReturn(roleList);
		
		when(menuRepository.find(any(Menu.class))).thenReturn(new ArrayList<Menu>());
		
		assertThrows(Exception.class, () -> permissionManagementServiceImpl.updatePermission(roleNo, permissionList));
	
	}
	
	@Test
	public void updatePermissionInvalidPermissionAction() throws Exception{
		String roleNo = "001";
		List<String> permissionList = new ArrayList<String>();
		
		permissionList.add("00199");
		
		Role role = new Role();
		role.setRoleID(1);
		role.setRoleNo("001");
		role.setRoleName("Maker");
		
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(role);
		
		when(roleRepository.find(any(Role.class))).thenReturn(roleList);
	
		when(menuRepository.find(any(Menu.class))).thenAnswer((Answer<List<Menu>>) invocation ->{
			Menu arg = (Menu) invocation.getArgument(0);
			List<Menu> menuList = new ArrayList<Menu>();
			
			if("001".equals(arg.getMenuNo())) {
				Menu menu = new Menu();
				menu.setMenuID(1);
				menu.setMenuNo(arg.getMenuNo());
				menu.setMenuName("Create Loan");
				
				menuList.add(menu);
				
			}else if("002".equals(arg.getMenuNo())) {
				Menu menu = new Menu();
				menu.setMenuID(2);
				menu.setMenuNo(arg.getMenuNo());
				menu.setMenuName("Approve Loan");
				
				menuList.add(menu);
				
			}else if("003".equals(arg.getMenuNo())) {
				Menu menu = new Menu();
				menu.setMenuID(3);
				menu.setMenuNo(arg.getMenuNo());
				menu.setMenuName("User Management");
				
				menuList.add(menu);
				
			}
			
			return menuList;
			
		});
		
		when(permissionActionRepository.find(any(PermissionAction.class))).thenReturn(new ArrayList<PermissionAction>());
		
		assertThrows(Exception.class, () -> permissionManagementServiceImpl.updatePermission(roleNo, permissionList));
	
	}
	
	@Test
	public void getPermission() throws Exception{
		when(roleRepository.isValid(anyString())).thenReturn(true);
		
		List<String> permissionNoList = List.of("00101","00102");
		
		when(rolePermissionRepository.getPermisionNoList(anyString())).thenReturn(permissionNoList);
		
		GetPermissionResponse response = permissionManagementServiceImpl.getPermission("001");
		
		assertEquals(response.getPermissionNoList(), permissionNoList);
		
	}
	
	@Test
	public void getPermissionInvalidRole() throws Exception{
		when(roleRepository.isValid(anyString())).thenReturn(false);
		
		assertThrows(Exception.class, () -> permissionManagementServiceImpl.getPermission("001"));
	}
	
}
