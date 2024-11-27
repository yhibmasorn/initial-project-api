package yms.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import yms.api.constant.CommonConstant;
import yms.api.model.Role;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class RoleRepositoryTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RoleRepository roleRepository;
	
	@BeforeEach
	void setUp() {
		roleRepository = new RoleRepository();
		roleRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("INSERT INTO ENT_ROLE (ROLE_ID, ROLE_NO, ROLE_NAME, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(9999, '999', 'Test Role', 'SYSTEM', current_timestamp, null, null, 'N')");
		
	}
	
	@AfterEach
	void tearDown() {
		roleRepository = new RoleRepository();
		roleRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("DELETE FROM ENT_ROLE WHERE ROLE_ID = 9999");
		
	}
	
	@Test
	void testFindRole() {
		Role role = new Role();
		role.setRoleID(9999);
		role.setRoleNo("999");
		role.setIsDeleted(CommonConstant.FLAG_N);
		
		List<Role> roleList = roleRepository.find(role);
		
		assertEquals(roleList.get(0).getRoleID(), 9999);
		
	}
	
	@Test
	void testInsertRole() {
		Role role = new Role();
		role.setRoleNo("Test_123456");
		role.setRoleName("Test_RolenName");
		role.setCreatedBy("Test_System");
		role.setCreatedDate(new Date());
		role.setIsDeleted(CommonConstant.FLAG_N);
		
		roleRepository.insert(role);
		
		role = new Role();
		role.setRoleNo("Test_123456");
		role.setIsDeleted(CommonConstant.FLAG_N);
		
		List<Role> roleList = roleRepository.find(role);
		
		assertEquals(roleList.get(0).getRoleNo(), "Test_123456");
		
		jdbcTemplate.update("DELETE FROM ENT_ROLE WHERE ROLE_NO = 'Test_123456'");
		
	}
	
	@Test 
	void testIsValid(){
		boolean valid = roleRepository.isValid("999");
		
		assertEquals(valid, true);
		
	}
	
}
