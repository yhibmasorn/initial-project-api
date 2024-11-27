package yms.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import yms.api.model.Session;
import yms.api.model.UserLogin;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class SessionRepositoryTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private SessionRepository sessionRepository;
	
	@BeforeEach
	void setUp() {
		sessionRepository = new SessionRepository();
		sessionRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("INSERT INTO ENT_ROLE (ROLE_ID, ROLE_NO, ROLE_NAME, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(9999, '999', 'Test Role', 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NO, FIRSTNAME, LASTNAME, ROLE_ID, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(9999, '999999', 'Test 1', 'Test Lastname 1', 9999, 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_USER_LOGIN (USER_LOGIN_ID, USERNAME, PASSWORD, EMPLOYEE_ID, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(9999, 'Test_999', '$2a$10$/skGQbcBuTIrscH8L8vX3OhdSwNqBNsP.nNf1T9dStpalqq6vY7eO', 9999, 'SYSTEM', current_timestamp, null, null, 'N')");
		jdbcTemplate.update("INSERT INTO ENT_SESSION (SESSION_ID, USER_LOGIN_ID, EXPIRE_DATE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES('Test_9999', 9999, current_timestamp, 'SYSTEM', current_timestamp, null, null, 'N')");
		
	}
	
	@AfterEach
	void tearDown() {
		sessionRepository = new SessionRepository();
		sessionRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("DELETE FROM ENT_SESSION WHERE SESSION_ID = 'Test_9999'");
		jdbcTemplate.update("DELETE FROM ENT_USER_LOGIN WHERE USER_LOGIN_ID = 9999");
		jdbcTemplate.update("DELETE FROM ENT_EMPLOYEE WHERE EMPLOYEE_ID = 9999");	
		jdbcTemplate.update("DELETE FROM ENT_ROLE WHERE ROLE_ID = 9999");	
		
	}
	
	@Test
	void testFindSession() {
		Session session = new Session();
		session.setSessionID("Test_9999");
		
		UserLogin userLogin = new UserLogin();
		userLogin.setUserLoginID(9999);

		session.setUserLogin(userLogin);
		session.setIsDeleted(CommonConstant.FLAG_N);
		
		List<Session> sessionList = sessionRepository.find(session);
		
		assertEquals(sessionList.get(0).getSessionID(), "Test_9999");
		assertEquals(sessionList.get(0).getUserLogin().getUserLoginID(), 9999);
		assertEquals(sessionList.get(0).getUserLogin().getEmployee().getEmployeeID(), 9999);
		assertEquals(sessionList.get(0).getUserLogin().getEmployee().getEmployeeNo(), "999999");
		assertEquals(sessionList.get(0).getUserLogin().getEmployee().getRole().getRoleID(), 9999);
		assertEquals(sessionList.get(0).getUserLogin().getEmployee().getRole().getRoleNo(), "999");
		
	}
	
	@Test
	void testInsertSession() {
		Session session = new Session();
		session.setSessionID("Test_123456");
		
		UserLogin userLogin = new UserLogin();
		userLogin.setUserLoginID(9999);
		
		session.setUserLogin(userLogin);
		session.setExpireDate(new Date());
		
		sessionRepository.insert(session);
		
		List<Session> sessionList = sessionRepository.find(session);
		
		assertEquals(sessionList.get(0).getSessionID(), "Test_123456");
		
		jdbcTemplate.update("DELETE FROM ENT_SESSION WHERE SESSION_ID = 'Test_123456'");
		
	}

	@Test
	void testUpdateExpireDate() {
		Date currentDate = new Date();
		
		sessionRepository.update("Test_9999", currentDate, "SYSTEM", currentDate);
		
		Session session = new Session();
		session.setSessionID("Test_9999");
		
		UserLogin userLogin = new UserLogin();
		userLogin.setUserLoginID(9999);
		
		session.setUserLogin(userLogin);
		session.setIsDeleted(CommonConstant.FLAG_N);
		
		List<Session> sessionList = sessionRepository.find(session);
		
		assertNotNull(sessionList.get(0).getExpireDate());
		
	}
	
	@Test
	void testDeleteSession() {
		sessionRepository.delete("Test_9999", "SYSTEM", new Date());
		
		Session session = new Session();
		session.setSessionID("Test_9999");
		
		UserLogin userLogin = new UserLogin();
		userLogin.setUserLoginID(9999);
		
		session.setUserLogin(userLogin);
		
		List<Session> sessionList = sessionRepository.find(session);
		
		assertEquals(sessionList.get(0).getIsDeleted(), "Y");
		
	}
	
}
