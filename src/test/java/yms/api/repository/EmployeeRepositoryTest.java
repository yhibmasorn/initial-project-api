package yms.api.repository;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import yms.api.constant.CommonConstant;
import yms.api.model.Employee;
import yms.api.model.Role;
import yms.api.model.request.SearchUserRequest;
import yms.api.model.response.SearchUsersResponse;
import yms.api.model.response.User;	

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class EmployeeRepositoryTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private EmployeeRepository employeeRepository;
	
	@BeforeEach
	void setUp() {
		employeeRepository = new EmployeeRepository();
		employeeRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("INSERT INTO ENT_ROLE (ROLE_ID, ROLE_NO, ROLE_NAME, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(9999, '999', 'Test Role', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, TITLE_TH, TITLE_EN, FIRSTNAME_TH, LASTNAME_TH, FIRSTNAME_EN, LASTNAME_EN, ROLE_ID, WORK_START_DATE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(9999, '999999', 'Mr.', 'Mr.', 'Test 1', 'Test Lastname 1', 'Test 1', 'Test Lastname 1', 9999, '2020-03-01', 'SYSTEM', current_timestamp, null, null, 'N')");
		
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, TITLE_TH, TITLE_EN, FIRSTNAME_TH, LASTNAME_TH, FIRSTNAME_EN, LASTNAME_EN, ROLE_ID, WORK_START_DATE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99999, '9999999', 'Mr.', 'Mr.', 'Test Employee 11', 'Test Employee Lastname 11', 'Test Employee 11', 'Test Employee Lastname 11', 9999, '2020-03-01', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, TITLE_TH, TITLE_EN, FIRSTNAME_TH, LASTNAME_TH, FIRSTNAME_EN, LASTNAME_EN, ROLE_ID, WORK_START_DATE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99998, '9999998', 'Mr.', 'Mr.', 'Test Employee 12', 'Test Employee Lastname 12', 'Test Employee 12', 'Test Employee Lastname 12', 9999, '2020-03-01', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, TITLE_TH, TITLE_EN, FIRSTNAME_TH, LASTNAME_TH, FIRSTNAME_EN, LASTNAME_EN, ROLE_ID, WORK_START_DATE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99997, '9999997', 'Mr.', 'Mr.', 'Test Employee 13', 'Test Employee Lastname 13', 'Test Employee 13', 'Test Employee Lastname 13', 9999, '2020-03-01', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, TITLE_TH, TITLE_EN, FIRSTNAME_TH, LASTNAME_TH, FIRSTNAME_EN, LASTNAME_EN, ROLE_ID, WORK_START_DATE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99996, '9999996', 'Mr.', 'Mr.', 'Test Employee 14', 'Test Employee Lastname 14', 'Test Employee 14', 'Test Employee Lastname 14', 9999, '2020-03-01', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, TITLE_TH, TITLE_EN, FIRSTNAME_TH, LASTNAME_TH, FIRSTNAME_EN, LASTNAME_EN, ROLE_ID, WORK_START_DATE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99995, '9999995', 'Mr.', 'Mr.', 'Test Employee 15', 'Test Employee Lastname 15', 'Test Employee 15', 'Test Employee Lastname 15', 9999, '2020-03-01', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, TITLE_TH, TITLE_EN, FIRSTNAME_TH, LASTNAME_TH, FIRSTNAME_EN, LASTNAME_EN, ROLE_ID, WORK_START_DATE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99994, '9999994', 'Mr.', 'Mr.', 'Test Employee 16', 'Test Employee Lastname 16', 'Test Employee 16', 'Test Employee Lastname 16', 9999, '2020-03-01', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, TITLE_TH, TITLE_EN, FIRSTNAME_TH, LASTNAME_TH, FIRSTNAME_EN, LASTNAME_EN, ROLE_ID, WORK_START_DATE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99993, '9999993', 'Mr.', 'Mr.', 'Test Employee 17', 'Test Employee Lastname 17', 'Test Employee 17', 'Test Employee Lastname 17', 9999, '2020-03-01', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, TITLE_TH, TITLE_EN, FIRSTNAME_TH, LASTNAME_TH, FIRSTNAME_EN, LASTNAME_EN, ROLE_ID, WORK_START_DATE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99992, '9999992', 'Mr.', 'Mr.', 'Test Employee 18', 'Test Employee Lastname 18', 'Test Employee 18', 'Test Employee Lastname 18', 9999, '2020-03-01', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, TITLE_TH, TITLE_EN, FIRSTNAME_TH, LASTNAME_TH, FIRSTNAME_EN, LASTNAME_EN, ROLE_ID, WORK_START_DATE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99991, '9999991', 'Mr.', 'Mr.', 'Test Employee 19', 'Test Employee Lastname 19', 'Test Employee 19', 'Test Employee Lastname 19', 9999, '2020-03-01', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, TITLE_TH, TITLE_EN, FIRSTNAME_TH, LASTNAME_TH, FIRSTNAME_EN, LASTNAME_EN, ROLE_ID, WORK_START_DATE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99990, '9999990', 'Mr.', 'Mr.', 'Test Employee 20', 'Test Employee Lastname 20', 'Test Employee 20', 'Test Employee Lastname 20', 9999, '2020-03-01', 'SYSTEM', current_timestamp, null, null, 'N')");
		
		jdbcTemplate.update("INSERT INTO ENT_USER_LOGIN (USER_LOGIN_ID, USERNAME, PASSWORD, EMPLOYEE_ID, STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99999, 'employee11', '$2a$10$/skGQbcBuTIrscH8L8vX3OhdSwNqBNsP.nNf1T9dStpalqq6vY7eO', 99999, 'Active', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_USER_LOGIN (USER_LOGIN_ID, USERNAME, PASSWORD, EMPLOYEE_ID, STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99998, 'employee12', '$2a$10$/skGQbcBuTIrscH8L8vX3OhdSwNqBNsP.nNf1T9dStpalqq6vY7eO', 99998, 'Lock', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_USER_LOGIN (USER_LOGIN_ID, USERNAME, PASSWORD, EMPLOYEE_ID, STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99997, 'employee13', '$2a$10$/skGQbcBuTIrscH8L8vX3OhdSwNqBNsP.nNf1T9dStpalqq6vY7eO', 99997, 'Active', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_USER_LOGIN (USER_LOGIN_ID, USERNAME, PASSWORD, EMPLOYEE_ID, STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99996, 'employee14', '$2a$10$/skGQbcBuTIrscH8L8vX3OhdSwNqBNsP.nNf1T9dStpalqq6vY7eO', 99996, 'Lock', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_USER_LOGIN (USER_LOGIN_ID, USERNAME, PASSWORD, EMPLOYEE_ID, STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99995, 'employee15', '$2a$10$/skGQbcBuTIrscH8L8vX3OhdSwNqBNsP.nNf1T9dStpalqq6vY7eO', 99995, 'Active', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_USER_LOGIN (USER_LOGIN_ID, USERNAME, PASSWORD, EMPLOYEE_ID, STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99994, 'employee16', '$2a$10$/skGQbcBuTIrscH8L8vX3OhdSwNqBNsP.nNf1T9dStpalqq6vY7eO', 99994, 'Lock', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_USER_LOGIN (USER_LOGIN_ID, USERNAME, PASSWORD, EMPLOYEE_ID, STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99993, 'employee17', '$2a$10$/skGQbcBuTIrscH8L8vX3OhdSwNqBNsP.nNf1T9dStpalqq6vY7eO', 99993, 'Active', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_USER_LOGIN (USER_LOGIN_ID, USERNAME, PASSWORD, EMPLOYEE_ID, STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99992, 'employee18', '$2a$10$/skGQbcBuTIrscH8L8vX3OhdSwNqBNsP.nNf1T9dStpalqq6vY7eO', 99992, 'Lock', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_USER_LOGIN (USER_LOGIN_ID, USERNAME, PASSWORD, EMPLOYEE_ID, STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99991, 'employee19', '$2a$10$/skGQbcBuTIrscH8L8vX3OhdSwNqBNsP.nNf1T9dStpalqq6vY7eO', 99991, 'Active', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_USER_LOGIN (USER_LOGIN_ID, USERNAME, PASSWORD, EMPLOYEE_ID, STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(99990, 'employee20', '$2a$10$/skGQbcBuTIrscH8L8vX3OhdSwNqBNsP.nNf1T9dStpalqq6vY7eO', 99990, 'Lock', 'SYSTEM', current_timestamp, null, null, 'N')");
		
	}
	
	@AfterEach
	void tearDown() {
		employeeRepository = new EmployeeRepository();
		employeeRepository.setJdbcTemplate(jdbcTemplate);
		
		
		jdbcTemplate.update("DELETE FROM ENT_USER_LOGIN WHERE USERNAME LIKE 'employee%'");
		
		jdbcTemplate.update("DELETE FROM ENT_EMPLOYEE WHERE EMPLOYEE_ID = 9999");
		jdbcTemplate.update("DELETE FROM ENT_EMPLOYEE WHERE FIRSTNAME_EN LIKE 'Test Employee%'");
		
		jdbcTemplate.update("DELETE FROM ENT_ROLE WHERE ROLE_ID = 9999");
		
	}
	
	@Test
	void testFindEmployee() {
		Employee employee = new Employee();
		employee.setEmployeeID(9999);
		employee.setEmployeeNo("999999");
		employee.setIsDeleted(CommonConstant.FLAG_N);
		
		List<Employee> employeeList = employeeRepository.find(employee);
		
		assertEquals(9999, employeeList.get(0).getEmployeeID());
		
	}
	
	@Test
	void testInsertEmployee() {
		Employee employee = new Employee();
		employee.setEmployeeNo("Test_123456");
		employee.setTitleTh("Mr.");
		employee.setTitleEn("Mr.");
		employee.setFirstnameTh("Test_Firstname");
		employee.setLastnameTh("Test_Lastname");
		employee.setLastnameEn("Test_Lastname");
		
		Role role = new Role();
		role.setRoleID(1);
		
		employee.setRole(role);
		employee.setWorkStartDate(new Date());
		employee.setCreatedBy("Test_System");
		employee.setCreatedDate(new Date());
		employee.setIsDeleted(CommonConstant.FLAG_N);
		
		employeeRepository.insert(employee);
		
		employee = new Employee();
		employee.setEmployeeNo("Test_123456");
		employee.setIsDeleted(CommonConstant.FLAG_N);
		
		List<Employee> employeeList = employeeRepository.find(employee);
		
		assertEquals("Test_123456", employeeList.get(0).getEmployeeNo());
		
		jdbcTemplate.update("DELETE FROM ENT_EMPLOYEE WHERE EMPLOYEE_NO = 'Test_123456'");
		
	}
	
	@Test
	void testFindPaging() {
		SearchUserRequest user = new SearchUserRequest();
		user.setFirstnameEn("Test Employee");
		user.setStatus("Active");
		user.setPage(2);
		user.setSize(3);
		user.setSortField("EMPLOYEE_NO");
		user.setSortMode("DESC");
		
		List<User> resultList = employeeRepository.findPaging(user);
		
		assertEquals(2, resultList.size());
		assertEquals("9999993", resultList.get(0).getEmployeeNo());
		assertEquals("9999991", resultList.get(1).getEmployeeNo());
		
	}
	
	@Test
	void testCountFindPaging() {
		SearchUserRequest user = new SearchUserRequest();
		user.setFirstnameEn("Test Employee");
		
		int count = employeeRepository.countFindPaging(user);
		
		assertEquals(10, count);
		
	}
	
}
