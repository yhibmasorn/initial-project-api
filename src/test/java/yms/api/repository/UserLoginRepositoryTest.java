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
import yms.api.model.Employee;
import yms.api.model.UserLogin;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserLoginRepositoryTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private UserLoginRepository userLoginRepository;
	
	@BeforeEach
	void setUp() {
		userLoginRepository = new UserLoginRepository();
		userLoginRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("INSERT INTO ENT_ROLE (ROLE_ID, ROLE_NO, ROLE_NAME, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(9999, '999', 'Test Role', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, FIRSTNAME, LASTNAME, ROLE_ID, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(9999, '999999', 'Test 1', 'Test Lastname 1', 1, 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_USER_LOGIN (USER_LOGIN_ID, USERNAME, PASSWORD, EMPLOYEE_ID, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(9999, 'Test_999', '$2a$10$/skGQbcBuTIrscH8L8vX3OhdSwNqBNsP.nNf1T9dStpalqq6vY7eO', 9999, 'SYSTEM', current_timestamp, null, null, 'N')");
		
	}
	
	@AfterEach
	void tearDown() {
		userLoginRepository = new UserLoginRepository();
		userLoginRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("DELETE FROM ENT_USER_LOGIN WHERE USER_LOGIN_ID = 9999");
		jdbcTemplate.update("DELETE FROM ENT_EMPLOYEE WHERE EMPLOYEE_ID = 9999");	
		jdbcTemplate.update("DELETE FROM ENT_ROLE WHERE ROLE_ID = 9999");	
		
	}
	
	@Test
	void testFindUserLogin() {
		UserLogin userLogin = new UserLogin();
		userLogin.setUserLoginID(9999);
		userLogin.setUsername("Test_999");
		userLogin.setIsDeleted(CommonConstant.FLAG_N);
		
		List<UserLogin> userLoginList = userLoginRepository.find(userLogin);
		
		assertEquals(userLoginList.get(0).getUserLoginID(), 9999);
		
	}
	
	@Test
	void testInsertUserLogin() {
		UserLogin userLogin = new UserLogin();
		userLogin.setUsername("Test_123456");
		
		Employee employee = new Employee();
		employee.setEmployeeID(9999);
		
		userLogin.setEmployee(employee);
		
		userLogin.setPassword("$2a$10$/skGQbcBuTIrscH8L8vX3OhdSwNqBNsP.nNf1T9dStpalqq6vY7eO");
		userLogin.setCreatedBy("Test_System");
		userLogin.setCreatedDate(new Date());
		userLogin.setIsDeleted(CommonConstant.FLAG_N);
		
		userLoginRepository.insert(userLogin);
		
		userLogin = new UserLogin();
		userLogin.setUsername("Test_123456");
		userLogin.setIsDeleted(CommonConstant.FLAG_N);
		
		List<UserLogin> userLoginList = userLoginRepository.find(userLogin);
		
		assertEquals(userLoginList.get(0).getUsername(), "Test_123456");
		
		jdbcTemplate.update("DELETE FROM ENT_USER_LOGIN WHERE USERNAME = 'Test_123456'");
		
	}
	
}
