package yms.api.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import yms.api.model.RolePermission;

@Repository
public class RolePermissionRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<String> getPermisionNoList(String roleNo){
		String sql = """
				SELECT CONCAT(m.menu_no, p.permission_action_no) as permission_no
				FROM MAP_ROLE_PERMISSION rp
				INNER JOIN ENT_ROLE r ON r.role_id = rp.role_id
				INNER JOIN MAS_MENU m ON m.menu_id = rp.menu_id
				INNER JOIN MAS_PERMISSION_ACTION p ON p.permission_action_id = rp.permission_action_id
				WHERE r.role_no = ? and rp.is_deleted = 'N' and r.is_deleted = 'N' and m.is_deleted = 'N' and p.is_deleted = 'N'
				""";
		
		List<String> result = jdbcTemplate.queryForList(sql.toString(), String.class, roleNo);
		
		return result != null && result.size() > 0 ? result : null;
		
	}
	
	public void insert(List<RolePermission> rolePermissionList) {
		String sql = "INSERT INTO MAP_ROLE_PERMISSION(ROLE_ID, MENU_ID, PERMISSION_ACTION_ID, CREATED_BY, CREATED_DATE, IS_DELETED) VALUES(?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, rolePermissionList.get(i).getRoleID());
				ps.setInt(2, rolePermissionList.get(i).getMenuID());
				ps.setInt(3, rolePermissionList.get(i).getPermissionActionID());
				ps.setString(4, rolePermissionList.get(i).getCreatedBy());
				ps.setTimestamp(5, rolePermissionList.get(i).getCreatedDate() != null ? new Timestamp(rolePermissionList.get(i).getCreatedDate().getTime()) : null);
				ps.setString(6, rolePermissionList.get(i).getIsDeleted());
				
			}
			
			@Override
			public int getBatchSize() {
				return rolePermissionList.size();
				
			}
		});
		
	}
	
	public void delete(Integer roleID, String updatedBy, Date updatedDate) {
		String sql = "UPDATE MAP_ROLE_PERMISSION SET IS_DELETED = 'Y', UPDATED_BY = ?, UPDATED_DATE = ? WHERE ROLE_ID = ?";
		
		jdbcTemplate.update(sql, updatedBy, updatedDate, roleID);
		
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
