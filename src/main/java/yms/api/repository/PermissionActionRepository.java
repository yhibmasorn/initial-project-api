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

import yms.api.model.PermissionAction;

@Repository
public class PermissionActionRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<PermissionAction> find(PermissionAction permissionAction){
		StringBuilder sql = new StringBuilder();
		ArrayList<String> paramList = new ArrayList<String>();
		
		sql.append("SELECT * FROM MAS_PERMISSION_ACTION WHERE 0=0 ");
		
		if(permissionAction != null) {
			if(permissionAction.getPermissionActionID() != null) {
				sql.append(" AND permission_action_id = ? ");
				paramList.add(permissionAction.getPermissionActionID().toString());
				
			}
			
			if(permissionAction.getPermissionActionNo() != null && !permissionAction.getPermissionActionNo().isEmpty()) {
				sql.append(" AND permission_action_no = ? ");
				paramList.add(permissionAction.getPermissionActionNo().toString());
				
			}
			
			if(permissionAction.getIsDeleted() != null && !permissionAction.getIsDeleted().isEmpty()) {
				sql.append(" AND is_deleted = ? ");
				paramList.add(permissionAction.getIsDeleted());
				
			}
			
		}
		
		List<PermissionAction> result = jdbcTemplate.query(sql.toString(), paramList.toArray(), new PermissionRowMapper());
		
		return result != null && result.size() > 0 ? result : null;
		
	}
	
	private static final class PermissionRowMapper implements RowMapper<PermissionAction>{

		@Override
		public PermissionAction mapRow(ResultSet rs, int rowNum) throws SQLException {
			PermissionAction permissionAction = new PermissionAction();

			permissionAction.setPermissionActionID(rs.getInt("PERMISSION_ACTION_ID"));
			permissionAction.setPermissionActionNo(rs.getString("PERMISSION_ACTION_NO"));
			permissionAction.setPermissionActionName(rs.getString("PERMISSION_ACTION_NAME"));
			permissionAction.setCreatedBy(rs.getString("CREATED_BY"));
			permissionAction.setCreatedDate(rs.getTimestamp("CREATED_DATE") != null ? new Date(rs.getTimestamp("CREATED_DATE").getTime()) : null);
			permissionAction.setUpdatedBy(rs.getString("UPDATED_BY"));
			permissionAction.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? new Date(rs.getTimestamp("UPDATED_DATE").getTime()) : null);
			permissionAction.setIsDeleted(rs.getString("IS_DELETED"));
			
			return permissionAction;
		}
		
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
