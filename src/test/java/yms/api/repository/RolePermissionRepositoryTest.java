package yms.api.repository;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import yms.api.model.RolePermission;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class RolePermissionRepositoryTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RolePermissionRepository rolePermissionRepository;
	
	@BeforeEach
	void setUp() {
		rolePermissionRepository = new RolePermissionRepository();
		rolePermissionRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("INSERT INTO ENT_ROLE (ROLE_ID, ROLE_NO, ROLE_NAME, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(999, '999', 'Test Role', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO MAS_MENU (MENU_ID, MENU_NO, MENU_NAME, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(999, '999', 'Test Menu', 'SYSTEM', current_timestamp, null, null, 'N')");
		
		jdbcTemplate.update("INSERT INTO MAS_PERMISSION_ACTION (PERMISSION_ACTION_ID, PERMISSION_ACTION_NO, PERMISSION_ACTION_NAME, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(997, '997', 'Test_Permission 1', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO MAS_PERMISSION_ACTION (PERMISSION_ACTION_ID, PERMISSION_ACTION_NO, PERMISSION_ACTION_NAME, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(998, '998', 'Test_Permission 2', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO MAS_PERMISSION_ACTION (PERMISSION_ACTION_ID, PERMISSION_ACTION_NO, PERMISSION_ACTION_NAME, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(999, '999', 'Test_Permission 3', 'SYSTEM', current_timestamp, null, null, 'N')");
		
		jdbcTemplate.update("INSERT INTO MAP_ROLE_PERMISSION (ROLE_PERMISSION_ID, ROLE_ID, MENU_ID, PERMISSION_ACTION_ID, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(997, 999, 999, 997, 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO MAP_ROLE_PERMISSION (ROLE_PERMISSION_ID, ROLE_ID, MENU_ID, PERMISSION_ACTION_ID, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(998, 999, 999, 998, 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO MAP_ROLE_PERMISSION (ROLE_PERMISSION_ID, ROLE_ID, MENU_ID, PERMISSION_ACTION_ID, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(999, 999, 999, 999, 'SYSTEM', current_timestamp, null, null, 'N')");
		
	}
	
	@AfterEach
	void tearDown() {
		rolePermissionRepository = new RolePermissionRepository();
		rolePermissionRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("DELETE FROM MAP_ROLE_PERMISSION WHERE ROLE_PERMISSION_ID in(997,998,999)");
		jdbcTemplate.update("DELETE FROM MAS_PERMISSION_ACTION WHERE PERMISSION_ACTION_ID in(997,998,999)");
		jdbcTemplate.update("DELETE FROM MAS_MENU WHERE MENU_ID = 999");
		jdbcTemplate.update("DELETE FROM ENT_ROLE WHERE ROLE_ID = 999");
		
	}
	
	@Test
	void testInsertRolePermission() {
		List<RolePermission> rolePermissionList = new ArrayList<RolePermission>();
		Date currentDate = new Date();
		
		RolePermission rolePermission = new RolePermission();
		rolePermission.setRoleID(999);
		rolePermission.setMenuID(999);
		rolePermission.setPermissionActionID(997);
		rolePermission.setCreatedBy("SYSTEM");
		rolePermission.setCreatedDate(currentDate);
		rolePermission.setIsDeleted("N");
		rolePermissionList.add(rolePermission);
		
		rolePermission = new RolePermission();
		rolePermission.setRoleID(999);
		rolePermission.setMenuID(999);
		rolePermission.setPermissionActionID(998);
		rolePermission.setCreatedBy("SYSTEM");
		rolePermission.setCreatedDate(currentDate);
		rolePermission.setIsDeleted("N");
		rolePermissionList.add(rolePermission);
		
		rolePermission = new RolePermission();
		rolePermission.setRoleID(999);
		rolePermission.setMenuID(999);
		rolePermission.setPermissionActionID(999);
		rolePermission.setCreatedBy("SYSTEM");
		rolePermission.setCreatedDate(currentDate);
		rolePermission.setIsDeleted("N");
		rolePermissionList.add(rolePermission);
		
		rolePermissionRepository.insert(rolePermissionList);
		
		assertThatNoException();
		
		jdbcTemplate.update("DELETE FROM MAP_ROLE_PERMISSION WHERE PERMISSION_ACTION_ID in(997,998,999)");
		
	}
	
	@Test
	void testDeleteRolePermission() {
		rolePermissionRepository.delete(999, "SYSTEM", new Date());
		
		assertThatNoException();
		
	}
	
	@Test
	void testGetPermissionRepository() {
		List<String> permissionNoList = rolePermissionRepository.getPermisionNoList("999");
		
		assertArrayEquals(permissionNoList.toArray(), new String[] {"999997", "999998", "999999"});
		
	}
	
}
