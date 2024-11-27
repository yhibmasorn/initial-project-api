package yms.api.model;

import java.util.Date;

public class PermissionAction {

	private Integer permissionActionID;
	private String permissionActionNo;
	private String permissionActionName;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String isDeleted;
	
	public Integer getPermissionActionID() {
		return permissionActionID;
	}
	public void setPermissionActionID(Integer permissionActionID) {
		this.permissionActionID = permissionActionID;
	}
	public String getPermissionActionNo() {
		return permissionActionNo;
	}
	public void setPermissionActionNo(String permissionActionNo) {
		this.permissionActionNo = permissionActionNo;
	}
	public String getPermissionActionName() {
		return permissionActionName;
	}
	public void setPermissionActionName(String permissionActionName) {
		this.permissionActionName = permissionActionName;
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
