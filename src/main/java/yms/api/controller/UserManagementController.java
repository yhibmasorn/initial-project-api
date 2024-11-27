package yms.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserManagementController {

	@PostMapping
	@PreAuthorize("hasAuthority(T(yms.api.constant.PermissionConstant).PERMISSION_00301_USER_MANAGEMENT_CREATE)")
	public String createUser() {
		return "Calling Create User";
		
	}
	
	@GetMapping("/{userID}")
	@PreAuthorize("hasAuthority(T(yms.api.constant.PermissionConstant).PERMISSION_00302_USER_MANAGEMENT_VIEW)")
	public String viewUser(@PathVariable String loanNo) {
		return "Calling View User";
		
	}
	
	@PutMapping("/{userID}")
	@PreAuthorize("hasAuthority(T(yms.api.constant.PermissionConstant).PERMISSION_00303_USER_MANAGEMENT_EDIT)")
	public String editUser(@PathVariable String loanNo) {
		return "Calling Edit User";
		
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority(T(yms.api.constant.PermissionConstant).PERMISSION_00304_USER_MANAGEMENT_SEARCH)")
	public String searchUser() {
		return "Calling Search User";
		
	}
	
}
