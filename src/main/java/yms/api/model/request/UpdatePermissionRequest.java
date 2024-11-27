package yms.api.model.request;

import java.util.List;

public class UpdatePermissionRequest {

	private String roleNo;
	private List<String> permissionNoList;
	
	public String getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}
	public List<String> getPermissionNoList() {
		return permissionNoList;
	}
	public void setPermissionNoList(List<String> permissionNoList) {
		this.permissionNoList = permissionNoList;
	}
	
}
