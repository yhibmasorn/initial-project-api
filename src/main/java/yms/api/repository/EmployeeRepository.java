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
	
	public void insert(Employee employee) {
		String sql = """
				INSERT INTO ENT_EMPLOYEE (EMPLOYEE_NO, FIRSTNAME, LASTNAME, ROLE_ID, CREATED_BY, CREATED_DATE, IS_DELETED) 
				VALUES(?, ?, ?, ?, ?, ?, ?)
				""";
		
		jdbcTemplate.update(sql.toString(), new Object[] {
				employee.getEmployeeNo(),
				employee.getFirstname(),
				employee.getLastname(),
				employee.getRole() != null ? employee.getRole().getRoleID().intValue() : null,
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
			
			employee.setCreatedBy(rs.getString("CREATED_BY"));
			employee.setCreatedDate(rs.getTimestamp("CREATED_DATE") != null ? new Date(rs.getTimestamp("CREATED_DATE").getTime()) : null);
			employee.setUpdatedBy(rs.getString("UPDATED_BY"));
			employee.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? new Date(rs.getTimestamp("UPDATED_DATE").getTime()) : null);
			employee.setIsDeleted(rs.getString("IS_DELETED"));
			
			return employee;
		}
		
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
