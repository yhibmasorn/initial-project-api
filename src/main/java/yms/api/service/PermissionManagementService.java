package yms.api.service;

import java.util.List;

import yms.api.model.response.GetPermissionResponse;

public interface PermissionManagementService {

	public void updatePermission(String roleNo, List<String> permissionNoList) throws Exception;
	public GetPermissionResponse getPermission(String roleNo) throws Exception;
	
}
