package yms.api.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import yms.api.model.Menu;

@Repository
public class MenuRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Menu> find(Menu menu){
		StringBuilder sql = new StringBuilder();
		ArrayList<String> paramList = new ArrayList<String>();
		
		sql.append("SELECT * FROM MAS_MENU WHERE 0=0 ");
		
		if(menu != null) {
			if(menu.getMenuID() != null) {
				sql.append(" AND menu_id = ? ");
				paramList.add(menu.getMenuID().toString());
				
			}
		
			if(menu.getMenuNo() != null && !menu.getMenuNo().isEmpty()) {
				sql.append(" AND menu_no = ? ");
				paramList.add(menu.getMenuNo());
				
			}
			
			if(menu.getIsDeleted() != null && !menu.getIsDeleted().isEmpty()) {
				sql.append(" AND is_deleted = ? ");
				paramList.add(menu.getIsDeleted());
				
			}
			
		}
		
		List<Menu> result = jdbcTemplate.query(sql.toString(), paramList.toArray(), new MenuRowMapper());
		
		return result != null && result.size() > 0 ? result : null;
		
	}
	
	private static final class MenuRowMapper implements RowMapper<Menu>{

		@Override
		public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
			Menu menu = new Menu();
			
			menu.setMenuID(rs.getInt("MENU_ID"));
			menu.setMenuNo(rs.getString("MENU_NO"));
			menu.setMenuName(rs.getString("MENU_NAME"));
			menu.setCreatedBy(rs.getString("CREATED_BY"));
			menu.setCreatedDate(rs.getTimestamp("CREATED_DATE") != null ? new Date(rs.getTimestamp("CREATED_DATE").getTime()) : null);
			menu.setUpdatedBy(rs.getString("UPDATED_BY"));
			menu.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? new Date(rs.getTimestamp("UPDATED_DATE").getTime()) : null);
			menu.setIsDeleted(rs.getString("IS_DELETED"));
			
			return menu;
		}
		
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
