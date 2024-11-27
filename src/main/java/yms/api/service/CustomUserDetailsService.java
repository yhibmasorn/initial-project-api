package yms.api.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import yms.api.constant.CommonConstant;
import yms.api.model.UserLogin;
import yms.api.repository.RolePermissionRepository;
import yms.api.repository.UserLoginRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserLoginRepository userLoginRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserLogin userLogin = new UserLogin();
		userLogin.setUsername(username);
		userLogin.setIsDeleted(CommonConstant.FLAG_N);
		
		List<UserLogin> userLoginList = userLoginRepository.find(userLogin);
		
		if(userLoginList == null || userLoginList.isEmpty()) {
			throw new UsernameNotFoundException("Invalid username or password are is invalid.");
			
		}
		
		userLogin = userLoginList.get(0);
		
		List<String> pemissionNoList = rolePermissionRepository.getPermisionNoList(userLogin.getEmployee().getRole().getRoleNo());
		
		Collection<? extends GrantedAuthority> authorities = mapRolesToAuthorities(pemissionNoList);
		
		return new User(userLogin.getUsername(), userLogin.getPassword(), authorities);
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<String> permissionNoList){
		return permissionNoList.stream()
				.map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
		
	}
	
}
