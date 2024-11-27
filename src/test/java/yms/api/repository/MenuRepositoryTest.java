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

import yms.api.model.Menu;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class MenuRepositoryTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private MenuRepository menuRepository;
	
	@BeforeEach
	void setUp() {
		menuRepository = new MenuRepository();
		menuRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("INSERT INTO MAS_MENU (MENU_ID, MENU_NO, MENU_NAME, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, IS_DELETED) VALUES(9999, '999', 'Test Menu', 'SYSTEM', current_timestamp, null, null, 'N')");
		
	}
	
	@AfterEach
	void tearDown() {
		menuRepository = new MenuRepository();
		menuRepository.setJdbcTemplate(jdbcTemplate);
		
		jdbcTemplate.update("DELETE FROM MAS_MENU WHERE MENU_ID = 9999");
		
		
	}
	
	@Test
	void testFindMenu() {
		Menu menu = new Menu();
		menu.setMenuID(9999);
		menu.setMenuNo("999");
		menu.setIsDeleted("N");
		
		List<Menu> menuList = menuRepository.find(menu);
		
		assertEquals(menuList.get(0).getMenuID(), 9999);
		assertEquals(menuList.get(0).getMenuNo(), "999");
		
	}
	
}
