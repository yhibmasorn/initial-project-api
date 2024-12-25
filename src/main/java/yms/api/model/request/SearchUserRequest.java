package yms.api.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class SearchUserRequest {

	private String employeeNo;
	private String firstnameTh;
	private String lastnameTh;
	private String firstnameEn;
	private String lastnameEn;
	private String username;
	private String roleName;
	
	@Pattern(
		regexp = "^\\d{2}/\\d{2}/\\d{4}$",
		message = "workStartDateFrom must be in the format dd/MM/yyyy"
	)
	private String workStartDateFrom;
	
	@Pattern(
		regexp = "^\\d{2}/\\d{2}/\\d{4}$",
		message = "workStartDateTo must be in the format dd/MM/yyyy"
	)
	private String workStartDateTo;
	private String status;
	
	@Min(value = 1, message = "Page size must be greater than 0")
	private Integer size;
	
	@Min(value = 1, message = "Page number must be greater than 0")
	private Integer page;
	private String sortField;
	
	@Pattern(
		regexp = "asc|desc",
		message = "sortMode must be either asc or desc"
	)
	private String sortMode;
	
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getFirstnameTh() {
		return firstnameTh;
	}
	public void setFirstnameTh(String firstnameTh) {
		this.firstnameTh = firstnameTh;
	}
	public String getLastnameTh() {
		return lastnameTh;
	}
	public void setLastnameTh(String lastnameTh) {
		this.lastnameTh = lastnameTh;
	}
	public String getFirstnameEn() {
		return firstnameEn;
	}
	public void setFirstnameEn(String firstnameEn) {
		this.firstnameEn = firstnameEn;
	}
	public String getLastnameEn() {
		return lastnameEn;
	}
	public void setLastnameEn(String lastnameEn) {
		this.lastnameEn = lastnameEn;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getWorkStartDateFrom() {
		return workStartDateFrom;
	}
	public void setWorkStartDateFrom(String workStartDateFrom) {
		this.workStartDateFrom = workStartDateFrom;
	}
	public String getWorkStartDateTo() {
		return workStartDateTo;
	}
	public void setWorkStartDateTo(String workStartDateTo) {
		this.workStartDateTo = workStartDateTo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortMode() {
		return sortMode;
	}
	public void setSortMode(String sortMode) {
		this.sortMode = sortMode;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	
}
