package yms.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yms.api.model.request.UpdatePermissionRequest;
import yms.api.model.response.GetPermissionResponse;
import yms.api.service.PermissionManagementService;

@RestController
@RequestMapping("/permissions")
public class PermissionManagementController {

	@Autowired
	private PermissionManagementService permissionManagementService;
	
	@PostMapping
	@PreAuthorize("hasAnyAuthority(T(yms.api.constant.PermissionConstant).PERMISSION_00501_PERMISSION_MANAGEMENT_CREATE, T(yms.api.constant.PermissionConstant).PERMISSION_00503_PERMISSION_MANAGEMENT_EDIT)")
	public void updatePermission(@RequestBody UpdatePermissionRequest updatePermissionRequest) throws Exception {
		permissionManagementService.updatePermission(updatePermissionRequest.getRoleNo(), updatePermissionRequest.getPermissionNoList());
		
	}
	
	@GetMapping("/{roleNo}")
	@PreAuthorize("hasAuthority(T(yms.api.constant.PermissionConstant).PERMISSION_00502_PERMISSION_MANAGEMENT_VIEW)")
	public ResponseEntity<GetPermissionResponse> getPermission(@PathVariable String roleNo) throws Exception {
		GetPermissionResponse response = permissionManagementService.getPermission(roleNo);
		
		return ResponseEntity.ok(response);
		
	}
	
}
