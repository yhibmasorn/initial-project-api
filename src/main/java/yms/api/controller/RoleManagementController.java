package yms.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yms.api.model.response.ListRoleResponse;
import yms.api.service.RoleManagementService;

@RestController
@RequestMapping("/roles")
public class RoleManagementController {

	@Autowired
	private RoleManagementService roleManagementService;
	
	@GetMapping
	@PreAuthorize("hasAuthority(T(yms.api.constant.PermissionConstant).PERMISSION_00504_PERMISSION_MANAGEMENT_SEARCH)")
	public ResponseEntity<ListRoleResponse> listRole() throws Exception{
		ListRoleResponse response = roleManagementService.listRole();
		
		return ResponseEntity.ok(response);
		
	}
	
}
