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

import yms.api.constant.CommonConstant;
import yms.api.model.Employee;
import yms.api.model.Role;
import yms.api.model.Session;
import yms.api.model.UserLogin;

@Repository
public class SessionRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Session> find(Session session){
		StringBuilder sql = new StringBuilder();
		ArrayList<String> paramList = new ArrayList<String>();
		
		sql.append("""
				SELECT s.*, u.*, e.*, r.*
				FROM ENT_SESSION s
				INNER JOIN ENT_USER_LOGIN u ON u.USER_LOGIN_ID = s.USER_LOGIN_ID
				INNER JOIN ENT_EMPLOYEE e ON e.EMPLOYEE_ID = u.EMPLOYEE_ID
				INNER JOIN ENT_ROLE r on r.ROLE_ID = e.ROLE_ID
				WHERE 0=0
				""");
		
		if(session != null) {
			if(session.getSessionID() != null) {
				sql.append(" AND s.session_id = ? ");
				paramList.add(session.getSessionID().toString());
				
			}
			
			if(session.getUserLogin() != null && session.getUserLogin().getUserLoginID() != null) {
				sql.append(" AND s.user_login_id = ? ");
				paramList.add(session.getUserLogin().getUserLoginID().toString());
				
			}
			
			if(session.getIsDeleted() != null && !session.getIsDeleted().isEmpty()) {
				sql.append(" AND s.is_deleted = ? ");
				paramList.add(session.getIsDeleted());
				
			}
			
		}
		
		List<Session> result = jdbcTemplate.query(sql.toString(), paramList.toArray(), new SessionMapper());
		
		return result != null && result.size() > 0 ? result : null;
		
	}
	
	public void insert(Session session) {
		String sql = """
				INSERT INTO ENT_SESSION (SESSION_ID, USER_LOGIN_ID, EXPIRE_DATE, CREATED_BY, CREATED_DATE, IS_DELETED)
				VALUES(?, ?, ?, ?, ?, ?)
				""";
		
		jdbcTemplate.update(sql.toString(), new Object[] {
			session.getSessionID(),
			session.getUserLogin().getUserLoginID(),
			session.getExpireDate(),
			session.getCreatedBy(),
			session.getCreatedDate(),
			session.getIsDeleted()
				
		});
		
	}
	
	public void update(String sessionID, Date expireDate, String updatedBy, Date updatedDate) {
		String sql = """
				UPDATE ENT_SESSION SET EXPIRE_DATE = ?, UPDATED_BY = ?, UPDATED_DATE = ? WHERE SESSION_ID = ? 
				""";
		
		jdbcTemplate.update(sql.toString(), new Object[] {
				expireDate,
				updatedBy,
				updatedDate,
				sessionID
				
		});
		
	}
	
	public void delete(String sessionID, String updatedBy, Date updatedDate) {
		String sql = """
				UPDATE ENT_SESSION SET IS_DELETED = ?, UPDATED_BY = ?, UPDATED_DATE = ? WHERE SESSION_ID = ?
				""";
		
		jdbcTemplate.update(sql.toString(), new Object[] {
			CommonConstant.FLAG_Y,
			updatedBy,
			updatedDate,
			sessionID
				
		});
		
	}
	
	private static class SessionMapper implements RowMapper<Session>{

		@Override
		public Session mapRow(ResultSet rs, int rowNum) throws SQLException {
			Session session = new Session();
			
			session.setSessionID(rs.getString("SESSION_ID"));
			
			UserLogin userLogin = new UserLogin();
			userLogin.setUserLoginID(rs.getInt("USER_LOGIN_ID"));
			userLogin.setUsername(rs.getString("USERNAME"));
			
			Employee employee = new Employee();
			employee.setEmployeeID(rs.getInt("EMPLOYEE_ID"));
			employee.setEmployeeNo(rs.getString("EMPLOYEE_NO"));
			
			Role role = new Role();
			role.setRoleID(rs.getInt("ROLE_ID"));
			role.setRoleNo(rs.getString("ROLE_NO"));
			role.setRoleName(rs.getString("ROLE_NAME"));
			
			employee.setRole(role);
			
			userLogin.setEmployee(employee);
			
			session.setUserLogin(userLogin);
			session.setExpireDate(rs.getTimestamp("EXPIRE_DATE") != null ? new Date(rs.getTimestamp("EXPIRE_DATE").getTime()) : null);
			session.setCreatedBy(rs.getString("CREATED_BY"));
			session.setCreatedDate(rs.getTimestamp("CREATED_DATE") != null ? new Date(rs.getTimestamp("CREATED_DATE").getTime()) : null);
			session.setUpdatedBy(rs.getString("UPDATED_BY"));
			session.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? new Date(rs.getTimestamp("UPDATED_DATE").getTime()) : null);
			session.setIsDeleted(rs.getString("IS_DELETED"));
			
			return session;
		}
		
		
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
