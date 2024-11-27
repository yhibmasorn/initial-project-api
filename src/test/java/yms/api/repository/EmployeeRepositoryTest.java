package yms.api.repository;

import static org.assertj.core.api.Assertions.assertThatNoException;
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
import yms.api.model.Role;

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
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, FIRSTNAME, LASTNAME, ROLE_ID, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(9999, '999999', 'Test 1', 'Test Lastname 1', 1, 'SYSTEM', current_timestamp, null, null, 'N')");
		
	}
	
	@AfterEach
	void tearDown() {
		employeeRepository = new EmployeeRepository();
		employeeRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("DELETE FROM ENT_EMPLOYEE WHERE EMPLOYEE_ID = 9999");
		jdbcTemplate.update("DELETE FROM ENT_ROLE WHERE ROLE_ID = 9999");
		
	}
	
	@Test
	void testFindEmployee() {
		Employee employee = new Employee();
		employee.setEmployeeID(9999);
		employee.setEmployeeNo("999999");
		employee.setIsDeleted(CommonConstant.FLAG_N);
		
		List<Employee> employeeList = employeeRepository.find(employee);
		
		assertEquals(employeeList.get(0).getEmployeeID(), 9999);
		
	}
	
	@Test
	void testInsertEmployee() {
		Employee employee = new Employee();
		employee.setEmployeeNo("Test_123456");
		employee.setFirstname("Test_Firstname");
		employee.setLastname("Test_Lastname");
		
		Role role = new Role();
		role.setRoleID(1);
		
		employee.setRole(role);
		employee.setCreatedBy("Test_System");
		employee.setCreatedDate(new Date());
		employee.setIsDeleted(CommonConstant.FLAG_N);
		
		employeeRepository.insert(employee);
		
		employee = new Employee();
		employee.setEmployeeNo("Test_123456");
		employee.setIsDeleted(CommonConstant.FLAG_N);
		
		List<Employee> employeeList = employeeRepository.find(employee);
		
		assertEquals(employeeList.get(0).getEmployeeNo(), "Test_123456");
		
		jdbcTemplate.update("DELETE FROM ENT_EMPLOYEE WHERE EMPLOYEE_NO = 'Test_123456'");
		
	}
	
}
