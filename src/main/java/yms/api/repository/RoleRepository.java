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

import yms.api.model.Role;

@Repository
public class RoleRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Role> find(Role role){
		StringBuilder sql = new StringBuilder();
		ArrayList<String> paramList = new ArrayList<String>();
		
		sql.append("""
				SELECT *
				FROM ENT_ROLE
				WHERE 0=0
				""");
		
		if(role != null) {
			if(role.getRoleID() != null) {
				sql.append(" AND role_id = ? ");
				paramList.add(role.getRoleID().toString());
				
			}
			
			if(role.getRoleNo() != null && !role.getRoleNo().isEmpty()) {
				sql.append(" AND role_no = ? ");
				paramList.add(role.getRoleNo());
				
			}
			
			if(role.getIsDeleted() != null && !role.getIsDeleted().isEmpty()) {
				sql.append(" AND is_deleted = ? ");
				paramList.add(role.getIsDeleted());
				
			}
			
		}
		
		List<Role> result = jdbcTemplate.query(sql.toString(), paramList.toArray(), new RoleMapper());
		
		return result != null && result.size() > 0 ? result : null;
		
	}
	
	public void insert(Role role) {
		String sql = """
				INSERT INTO ENT_ROLE (ROLE_NO, ROLE_NAME, CREATED_BY, CREATED_DATE, IS_DELETED) 
				VALUES(?, ?, ?, ?, ?);
				""";
		
		jdbcTemplate.update(sql.toString(), new Object[] {
			role.getRoleNo(),
			role.getRoleName(),
			role.getCreatedBy(),
			role.getCreatedDate(),
			role.getIsDeleted()
				
		});
		
	}
	
	public boolean isValid(String roleNo) {
		boolean validFlag = false;
		String sql = """
				SELECT count(*) > 0
				FROM ENT_ROLE
				WHERE role_no = ?
				""";
		
		validFlag = jdbcTemplate.queryForObject(sql, Boolean.class, roleNo);
		
		return validFlag;
		
	}
	
	private static class RoleMapper implements RowMapper<Role>{

		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role role = new Role();
			
			role.setRoleID(rs.getInt("ROLE_ID"));
			role.setRoleNo(rs.getString("ROLE_NO"));
			role.setRoleName(rs.getString("ROLE_NAME"));
			role.setCreatedBy(rs.getString("CREATED_BY"));
			role.setCreatedDate(rs.getTimestamp("CREATED_DATE") != null ? new Date(rs.getTimestamp("CREATED_DATE").getTime()) : null);
			role.setUpdatedBy(rs.getString("UPDATED_BY"));
			role.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? new Date(rs.getTimestamp("UPDATED_DATE").getTime()) : null);
			role.setIsDeleted(rs.getString("IS_DELETED"));
			
			return role;
		}
		
		
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
