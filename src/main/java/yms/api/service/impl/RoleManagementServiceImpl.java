package yms.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yms.api.constant.CommonConstant;
import yms.api.model.Role;
import yms.api.model.response.ListRoleResponse;
import yms.api.repository.RoleRepository;
import yms.api.service.RoleManagementService;

@Service
public class RoleManagementServiceImpl implements RoleManagementService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public ListRoleResponse listRole() throws Exception {
		ListRoleResponse listRoleResponse = new ListRoleResponse();
		
		Role role = new Role();
		role.setIsDeleted(CommonConstant.FLAG_N);
		
		List<Role> roleList = roleRepository.find(role);
		
		if(roleList != null && roleList.size() > 0) {
			List<yms.api.model.response.Role> roleResponseList = new ArrayList<yms.api.model.response.Role>();
			
			for(Role roleDB : roleList) {
				yms.api.model.response.Role roleResponse = new yms.api.model.response.Role();
				roleResponse.setRoleNo(roleDB.getRoleNo());
				roleResponse.setRoleName(roleDB.getRoleName());
				
				roleResponseList.add(roleResponse);
				
			}
			
			listRoleResponse.setRoleList(roleResponseList);
			
		}
		
		return listRoleResponse;
	}

}
