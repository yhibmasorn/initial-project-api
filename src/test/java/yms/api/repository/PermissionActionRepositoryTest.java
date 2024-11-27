package yms.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import yms.api.model.PermissionAction;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PermissionActionRepositoryTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private PermissionActionRepository permissionActionRepository;
	
	@BeforeEach
	void setUp() {
		permissionActionRepository = new PermissionActionRepository();
		permissionActionRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("INSERT INTO MAS_PERMISSION_ACTION (PERMISSION_ACTION_ID, PERMISSION_ACTION_NO, PERMISSION_ACTION_NAME, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(999, '999', 'Test_Permission', 'SYSTEM', current_timestamp, null, null, 'N')");
		
	}
	
	@AfterEach
	void tearDown() {
		permissionActionRepository = new PermissionActionRepository();
		permissionActionRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("DELETE FROM MAS_PERMISSION_ACTION WHERE PERMISSION_ACTION_ID = 999");
		
	}

	@Test
	void testFindPermissionAction() {
		PermissionAction permissionAction = new PermissionAction();
		permissionAction.setPermissionActionID(999);
		permissionAction.setPermissionActionNo("999");
		permissionAction.setIsDeleted("N");
		
		List<PermissionAction> permissionActionList = permissionActionRepository.find(permissionAction);
		
		assertEquals(permissionActionList.get(0).getPermissionActionID(), 999);
		assertEquals(permissionActionList.get(0).getPermissionActionNo(), "999");
		
	}
	
}
