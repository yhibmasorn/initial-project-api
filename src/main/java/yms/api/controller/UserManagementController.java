package yms.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import yms.api.model.request.SearchUserRequest;
import yms.api.model.response.GetAllUsersResponse;
import yms.api.model.response.SearchUsersResponse;
import yms.api.service.UserManagementService;
import yms.api.validator.UserManagementValidator;

@RestController
@RequestMapping("/users")
@Validated
public class UserManagementController {

	@Autowired
	private UserManagementValidator userManagementValidator;
	
	@Autowired
	private UserManagementService userManagementService;
	
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
	
	@GetMapping("/search")
	@PreAuthorize("hasAuthority(T(yms.api.constant.PermissionConstant).PERMISSION_00304_USER_MANAGEMENT_SEARCH)")
	public ResponseEntity<?> searchUsers(@Valid SearchUserRequest request) throws Exception {
		Errors errors = new BindException(request, "searchUserRequest");
		userManagementValidator.validate(request, errors);
		
		if(errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
			
		}
		
		SearchUsersResponse response = userManagementService.searchUsers(request);
		
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority(T(yms.api.constant.PermissionConstant).PERMISSION_00304_USER_MANAGEMENT_SEARCH)")
	public ResponseEntity<GetAllUsersResponse> getAllUsers() throws Exception {
		GetAllUsersResponse response = userManagementService.getAllUsers();
		
		return ResponseEntity.ok(response);
		
	}
	
}
