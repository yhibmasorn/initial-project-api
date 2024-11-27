package yms.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
public class LoanController {

	@PostMapping
	@PreAuthorize("hasAuthority(T(yms.api.constant.PermissionConstant).PERMISSION_00101_CREATE_LOAN_CREATE)")
	public String createLoan() {
		return "Calling Create Loan";
		
	}
	
	@GetMapping("/{loanNo}")
	@PreAuthorize("hasAuthority(T(yms.api.constant.PermissionConstant).PERMISSION_00102_CREATE_LOAN_VIEW)")
	public String viewLoan(@PathVariable String loanNo) {
		return "Calling View Loan";
		
	}
	
	@PutMapping("/{loanNo}")
	@PreAuthorize("hasAuthority(T(yms.api.constant.PermissionConstant).PERMISSION_00103_CREATE_LOAN_EDIT)")
	public String editLoan(@PathVariable String loanNo) {
		return "Calling View Loan";
		
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority(T(yms.api.constant.PermissionConstant).PERMISSION_00104_CREATE_LOAN_SEARCH)")
	public String searchLoan() {
		return "Calling Search Loan";
		
	}
	
}
