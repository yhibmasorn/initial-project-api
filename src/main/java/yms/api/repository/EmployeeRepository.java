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
import yms.api.model.request.SearchUserRequest;
import yms.api.model.response.User;
import yms.api.util.DateUtil;

@Repository
public class EmployeeRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Employee> find(Employee employee){
		StringBuilder sql = new StringBuilder();
		ArrayList<String> paramList = new ArrayList<String>();
		
		sql.append("""
				SELECT emp.*, r.*
				FROM ENT_EMPLOYEE emp
				LEFT OUTER JOIN ENT_ROLE r ON r.role_id = emp.role_id
				WHERE 0=0
				""");
		
		if(employee != null) {
			if(employee.getEmployeeID() != null) {
				sql.append(" AND emp.employee_id = ? ");
				paramList.add(employee.getEmployeeID().toString());
				
			}
			
			if(employee.getEmployeeNo() != null && !employee.getEmployeeNo().isEmpty()) {
				sql.append(" AND emp.employee_no = ? ");
				paramList.add(employee.getEmployeeNo());
				
			}

			if(employee.getIsDeleted() != null && !employee.getIsDeleted().isEmpty()) {
				sql.append(" AND emp.is_deleted = ? ");
				paramList.add(employee.getIsDeleted());
				
			}
			
		}
		
		List<Employee> result = jdbcTemplate.query(sql.toString(), paramList.toArray(), new EmployeeRowMapper());
		
		return result != null && result.size() > 0 ? result : null;
		
	}
	
	public List<User> findPaging(SearchUserRequest user){
		StringBuilder sql = new StringBuilder();
		ArrayList<Object> paramList = new ArrayList<Object>();
		
		sql.append("""
				SELECT e.EMPLOYEE_NO, e.TITLE_TH, e.TITLE_EN, e.FIRSTNAME_TH, e.FIRSTNAME_EN, e.LASTNAME_TH, e.LASTNAME_EN, u.USERNAME, r.ROLE_NAME, e.WORK_START_DATE, u.STATUS
				FROM ENT_EMPLOYEE e
				INNER JOIN ENT_USER_LOGIN u ON u.EMPLOYEE_ID = e.EMPLOYEE_ID
				INNER JOIN ENT_ROLE r ON r.ROLE_ID = e.ROLE_ID
				WHERE 0=0 AND e.IS_DELETED = 'N' AND u.IS_DELETED = 'N' AND r.IS_DELETED = 'N'
				""");
		
		if(user != null) {
			if(user.getEmployeeNo() != null && !"".equals(user.getEmployeeNo())) {
				sql.append(" AND  e.EMPLOYEE_NO = ? ");
				paramList.add(user.getEmployeeNo());
				
			}
			
			if(user.getFirstnameTh() != null && !"".equals(user.getFirstnameTh())) {
				sql.append(" AND  e.FIRSTNAME_TH like ? ");
				paramList.add("%" + user.getFirstnameTh() + "%");
			}
			
			if(user.getLastnameTh() != null && !"".equals(user.getLastnameTh())) {
				sql.append(" AND  e.LASTNAME_TH like ? ");
				paramList.add("%" + user.getLastnameTh() + "%");
				
			}
			
			if(user.getFirstnameEn() != null && !"".equals(user.getFirstnameEn())) {
				sql.append(" AND  e.FIRSTNAME_EN like ? ");
				paramList.add("%" + user.getFirstnameEn() + "%");
				
			}
			
			if(user.getLastnameEn() != null && !"".equals(user.getLastnameEn())) {
				sql.append(" AND  e.LASTNAME_EN like ? ");
				paramList.add("%" + user.getLastnameEn() + "%");
				
			}
			
			if(user.getUsername() != null && !"".equals(user.getUsername())) {
				sql.append(" AND  u.USERNAME = ? ");
				paramList.add(user.getUsername());
				
			}
			
			if(user.getRoleName() != null && !"".equals(user.getRoleName())) {
				sql.append(" AND  r.ROLE_NAME = ? ");
				paramList.add(user.getRoleName());
				
			}
			
			if(user.getWorkStartDateFrom() != null && !"".equals(user.getWorkStartDateFrom())) {
				sql.append(" AND e.WORK_START_DATE >= ?");
				paramList.add(user.getWorkStartDateFrom());
				
			}
			
			if(user.getWorkStartDateTo() != null && !"".equals(user.getWorkStartDateTo())) {
				sql.append(" AND e.WORK_START_DATE <= ?");
				paramList.add(user.getWorkStartDateTo());
				
			}
			
			if(user.getStatus() != null && !"".equals(user.getStatus())) {
				if(user.getStatus().indexOf(",") > -1) {
					String[] statusArr = user.getStatus().split(",");
					StringBuilder str = new StringBuilder();
					
					sql.append(" AND  u.STATUS in (");
					
					for(int i = 0; i < statusArr.length; i++) {
						if(i == 0) {
							str.append("?");

						}else {
							str.append(",");
							str.append("?");
							
						}
						
						paramList.add(statusArr[i]);
						
					}
					
					sql.append(str.toString());
					sql.append(")");
					
				}else {
					sql.append(" AND  u.STATUS = ? ");
					paramList.add(user.getStatus());
					
				}
				
				
				
			}
			
		}
		
		if((user.getSortField() != null && !"".equals(user.getSortField()))) {
			sql.append(" ORDER BY ");
			sql.append(user.getSortField());
			
			if((user.getSortMode() != null) && !"".equals(user.getSortMode())) {
				sql.append(" ");
				sql.append(user.getSortMode());
				
			}
			
		}
		
		if(user.getPage() != null && user.getSize() != null) {
			sql.append(" LIMIT ? OFFSET ?");
			paramList.add(user.getSize());
			paramList.add((user.getPage() - 1) * user.getSize());
			
		}
		
		List<User> result = jdbcTemplate.query(sql.toString(), paramList.toArray(), new SearchUserRowMapper());
		
		return result != null && result.size() > 0 ? result : null;
		
	}
	
	public int countFindPaging(SearchUserRequest user) {
		StringBuilder sql = new StringBuilder();
		ArrayList<String> paramList = new ArrayList<String>();
		
		sql.append("""
				SELECT COUNT(*)
				FROM ENT_EMPLOYEE e
				INNER JOIN ENT_USER_LOGIN u ON u.EMPLOYEE_ID = e.EMPLOYEE_ID
				INNER JOIN ENT_ROLE r ON r.ROLE_ID = e.ROLE_ID
				WHERE 0=0 AND e.IS_DELETED = 'N' AND u.IS_DELETED = 'N' AND r.IS_DELETED = 'N'
				""");
		
		if(user != null) {
			if(user.getEmployeeNo() != null && !"".equals(user.getEmployeeNo())) {
				sql.append(" AND  e.EMPLOYEE_NO = ? ");
				paramList.add(user.getEmployeeNo());
				
			}
			
			if(user.getFirstnameTh() != null && !"".equals(user.getFirstnameTh())) {
				sql.append(" AND  e.FIRSTNAME_TH like ? ");
				paramList.add("%" + user.getFirstnameTh() + "%");
			}
			
			if(user.getLastnameTh() != null && !"".equals(user.getLastnameTh())) {
				sql.append(" AND  e.LASTNAME_TH like ? ");
				paramList.add("%" + user.getLastnameTh() + "%");
				
			}
			
			if(user.getFirstnameEn() != null && !"".equals(user.getFirstnameEn())) {
				sql.append(" AND  e.FIRSTNAME_EN like ? ");
				paramList.add("%" + user.getFirstnameEn() + "%");
				
			}
			
			if(user.getLastnameEn() != null && !"".equals(user.getLastnameEn())) {
				sql.append(" AND  e.LASTNAME_EN like ? ");
				paramList.add("%" + user.getLastnameEn() + "%");
				
			}
			
			if(user.getUsername() != null && !"".equals(user.getUsername())) {
				sql.append(" AND  u.USERNAME = ? ");
				paramList.add(user.getUsername());
				
			}
			
			if(user.getRoleName() != null && !"".equals(user.getRoleName())) {
				sql.append(" AND  r.ROLE_NAME = ? ");
				paramList.add(user.getRoleName());
				
			}
			
			if(user.getWorkStartDateFrom() != null && !"".equals(user.getWorkStartDateFrom())) {
				sql.append(" AND e.WORK_START_DATE >= ?");
				paramList.add(user.getWorkStartDateFrom());
				
			}
			
			if(user.getWorkStartDateTo() != null && !"".equals(user.getWorkStartDateTo())) {
				sql.append(" AND e.WORK_START_DATE <= ?");
				paramList.add(user.getWorkStartDateTo());
				
			}
			
			if(user.getStatus() != null && !"".equals(user.getStatus())) {
				sql.append(" AND  u.STATUS = ? ");
				paramList.add(user.getStatus());
				
			}
			
		}
		
		int count = jdbcTemplate.queryForObject(sql.toString(), paramList.toArray(), Integer.class);
		
		return count;
		
	}
	
	public void insert(Employee employee) {
		String sql = """
				INSERT INTO ENT_EMPLOYEE (EMPLOYEE_NO, TITLE_TH, TITLE_EN, FIRSTNAME_TH, LASTNAME_TH, FIRSTNAME_EN, LASTNAME_EN, ROLE_ID, WORK_START_DATE, CREATED_BY, CREATED_DATE, IS_DELETED) 
				VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";
		
		jdbcTemplate.update(sql.toString(), new Object[] {
				employee.getEmployeeNo(),
				employee.getTitleTh(),
				employee.getTitleEn(),
				employee.getFirstnameTh(),
				employee.getLastnameTh(),
				employee.getFirstnameEn(),
				employee.getLastnameEn(),
				employee.getRole() != null ? employee.getRole().getRoleID().intValue() : null,
			    employee.getWorkStartDate(),
				employee.getCreatedBy(),
				employee.getCreatedDate(),
				employee.getIsDeleted()
		});
		
	}
	
	private static class EmployeeRowMapper implements RowMapper<Employee>{

		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee employee = new Employee();
			
			employee.setEmployeeID(rs.getInt("EMPLOYEE_ID"));
			employee.setEmployeeNo(rs.getString("EMPLOYEE_NO"));
			employee.setTitleTh(rs.getString("TITLE_TH"));
			employee.setTitleEn(rs.getString("TITLE_EN"));
			employee.setFirstnameTh(rs.getString("FIRSTNAME_TH"));
			employee.setLastnameTh(rs.getString("LASTNAME_TH"));
			employee.setFirstnameEn(rs.getString("FIRSTNAME_EN"));
			employee.setLastnameEn(rs.getString("LASTNAME_EN"));
			
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
			
			employee.setWorkStartDate(rs.getDate("WORK_START_DATE"));
			employee.setCreatedBy(rs.getString("CREATED_BY"));
			employee.setCreatedDate(rs.getTimestamp("CREATED_DATE") != null ? new Date(rs.getTimestamp("CREATED_DATE").getTime()) : null);
			employee.setUpdatedBy(rs.getString("UPDATED_BY"));
			employee.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? new Date(rs.getTimestamp("UPDATED_DATE").getTime()) : null);
			employee.setIsDeleted(rs.getString("IS_DELETED"));
			
			return employee;
		}
		
	}

	private static class SearchUserRowMapper implements RowMapper<User>{
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			
			user.setEmployeeNo(rs.getString("EMPLOYEE_NO"));
			user.setTitleTh(rs.getString("TITLE_TH"));
			user.setTitleEn(rs.getString("TITLE_EN"));
			user.setFirstnameTh(rs.getString("FIRSTNAME_TH"));
			user.setLastnameTh(rs.getString("LASTNAME_TH"));
			user.setFirstnameEn(rs.getString("FIRSTNAME_EN"));
			user.setLastnameEn(rs.getString("LASTNAME_EN"));
			user.setUsername(rs.getString("USERNAME"));
			user.setRoleName(rs.getString("ROLE_NAME"));
			
			try {
				user.setWorkStartDate(rs.getDate("WORK_START_DATE") != null ? DateUtil.convertDateToString(rs.getDate("WORK_START_DATE"), DateUtil.DATE_FORMAT_DDMMYY_SLASH) : null);
				
			} catch (Exception e) {
				e.printStackTrace();
				user.setWorkStartDate(null);
				
			}
			
			user.setStatus(rs.getString("STATUS"));
			
			return user;
			
		}
		
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
