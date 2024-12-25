package yms.api.model;

import java.util.Date;

public class Employee {

	private Integer employeeID;
	private String employeeNo;
	private String titleTh;
	private String titleEn;
	private String firstnameTh;
	private String lastnameTh;
	private String firstnameEn;
	private String lastnameEn;
	private Role role;
	private Date workStartDate;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String isDeleted;
	
	public Integer getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getTitleTh() {
		return titleTh;
	}
	public void setTitleTh(String titleTh) {
		this.titleTh = titleTh;
	}
	public String getTitleEn() {
		return titleEn;
	}
	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Date getWorkStartDate() {
		return workStartDate;
	}
	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
