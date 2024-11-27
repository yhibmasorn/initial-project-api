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

import yms.api.model.Employee;
import yms.api.model.Role;
import yms.api.model.UserLogin;

@Repository
public class UserLoginRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<UserLogin> find(UserLogin userLogin){
		StringBuilder sql = new StringBuilder();
		ArrayList<String> paramList = new ArrayList<String>();
		
		sql.append("""
				SELECT emp.*, r.*, u.*
				FROM ENT_USER_LOGIN u
				INNER JOIN ENT_EMPLOYEE emp ON u.employee_id = emp.employee_id
				LEFT OUTER JOIN ENT_ROLE r ON r.role_id = emp.role_id
				WHERE 0=0
				""");
		
		if(userLogin != null) {
			if(userLogin.getUserLoginID() != null) {
				sql.append(" AND u.user_login_id = ? ");
				paramList.add(userLogin.getUserLoginID().toString());
				
			}
			
			if(userLogin.getUsername() != null && !userLogin.getUsername().isEmpty()) {
				sql.append(" AND u.username = ? ");
				paramList.add(userLogin.getUsername());
				
			}
			
			if(userLogin.getPassword() != null && !userLogin.getPassword().isEmpty()) {
				sql.append(" AND u.password = ? ");
				paramList.add(userLogin.getPassword());
				
			}
			
			if(userLogin.getEmployee() != null && userLogin.getEmployee().getEmployeeID() != null) {
				sql.append(" AND u.employee_id = ? ");
				paramList.add(userLogin.getEmployee().getEmployeeID().toString());
				
			}
			
			if(userLogin.getIsDeleted() != null && !userLogin.getIsDeleted().isEmpty()) {
				sql.append(" AND u.is_deleted = ? ");
				paramList.add(userLogin.getIsDeleted());
				
			}
			
		}
		
		List<UserLogin> result = jdbcTemplate.query(sql.toString(), paramList.toArray(), new UserLoginMapper());
		
		return result != null && result.size() > 0 ? result : null;
		
	}
	
	public void insert(UserLogin userLogin) {
		String sql = """
				INSERT INTO ENT_USER_LOGIN (USERNAME, PASSWORD, EMPLOYEE_ID, CREATED_BY, CREATED_DATE, IS_DELETED) 
				VALUES(?, ?, ?, ?, ?, ?)
				""";
		jdbcTemplate.update(sql.toString(), new Object[] {
				userLogin.getUsername(),
				userLogin.getPassword(),
				userLogin.getEmployee().getEmployeeID(),
				userLogin.getCreatedBy(),
				userLogin.getCreatedDate(),
				userLogin.getIsDeleted()
				
		});
		
	}
	
	private static class UserLoginMapper implements RowMapper<UserLogin>{

		@Override
		public UserLogin mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserLogin userLogin = new UserLogin();
			
			userLogin.setUserLoginID(rs.getInt("USER_LOGIN_ID"));
			userLogin.setUsername(rs.getString("USERNAME"));
			userLogin.setPassword(rs.getString("PASSWORD"));
			
			Employee employee = new Employee();
			
			employee.setEmployeeID(rs.getInt("EMPLOYEE_ID"));
			employee.setEmployeeNo(rs.getString("EMPLOYEE_NO"));
			employee.setFirstname(rs.getString("FIRSTNAME"));
			employee.setLastname(rs.getString("LASTNAME"));
			
			Role role = new Role();
			role.setRoleID(rs.getInt("ROLE_ID"));
			role.setRoleNo(rs.getString("ROLE_NO"));
			role.setRoleName(rs.getString("ROLE_NAME"));
			role.setCreatedBy(rs.getString("CREATED_BY"));
			role.setCreatedDate(rs.getTimestamp("CREATED_DATE") != null ? new Date(rs.getTimestamp("CREATED_DATE").getTime()) : null);
			role.setUpdatedBy(rs.getString("UPDATED_BY"));
			role.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? new Date(rs.getTimestamp("UPDATED_DATE").getTime()) : null);
			role.setIsDeleted(rs.getString("IS_DELETED"));
			
			employee.setRole(role);
			
			userLogin.setEmployee(employee);
			
			return userLogin;
		}
		
		
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	} 
	
}
