package yms.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {

	@GetMapping
	@PreAuthorize("hasAnyRole(T(yms.api.constant.RoleConstant).ROLE_NO_MAKER, T(yms.api.constant.RoleConstant).ROLE_NO_CHECKER,"
			+ " T(yms.api.constant.RoleConstant).ROLE_NO_APPROVER, T(yms.api.constant.RoleConstant).ROLE_NO_ADMINISTRATOR)")
	//@PreAuthorize("hasRole(T(yms.api.constant.RoleConstant).ROLE_NO_MAKER)")
	//@PreAuthorize("hasRole('ROLE_001')")
	public String welcome() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return "Weclome " + authentication.getPrincipal().toString() + ". You are authenticate.";
		
	}
	
}
