package yms.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yms.api.model.Menu;
import yms.api.model.PermissionAction;
import yms.api.model.Role;
import yms.api.model.RolePermission;
import yms.api.model.response.GetPermissionResponse;
import yms.api.repository.MenuRepository;
import yms.api.repository.PermissionActionRepository;
import yms.api.repository.RolePermissionRepository;
import yms.api.repository.RoleRepository;
import yms.api.service.PermissionManagementService;

@Service
public class PermissionManagementServiceImpl implements PermissionManagementService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private PermissionActionRepository permissionActionRepository;
	
	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Override
	public void updatePermission(String roleNo, List<String> permissionList) throws Exception {
		Date currentDate = new Date();
		
		Role role = new Role();
		role.setRoleNo(roleNo);
		role.setIsDeleted("N");
		
		List<Role> roleList = roleRepository.find(role);
		
		if(roleList == null || roleList.isEmpty()) {
			throw new Exception("Not found roleNo " + roleNo);
			
		}
		
		role = roleList.get(0);
		
		// Permission pattern : <menu_no><permission_no>
		List<String> menuNoList = new ArrayList<String>();
		List<String> permissionActionNoList = new ArrayList<String>();
		
		Map<String, Integer> menuMap = new HashMap<String, Integer>();
		Map<String, Integer> permissionActionMap = new HashMap<String, Integer>();

		for(String permission : permissionList) {
			String menuNo = permission.substring(0,3);
			String permissionActionNo = permission.substring(3,5);
			
			menuNoList.add(menuNo);
			permissionActionNoList.add(permissionActionNo);
			
		}
		
		for(String menuNo : menuNoList) {
			Menu menu = new Menu();
			menu.setMenuNo(menuNo);
			menu.setIsDeleted("N");
			
			List<Menu> menuList = menuRepository.find(menu);
			
			if(menuList == null || menuList.isEmpty()) {
				throw new Exception("Not found menuNo " + menuNo);
				
			}
			
			menuMap.put(menuNo, menuList.get(0).getMenuID());
			
		}
		
		for(String permissionActionNo : permissionActionNoList) {
			PermissionAction permissionAction = new PermissionAction();
			permissionAction.setPermissionActionNo(permissionActionNo);
			permissionAction.setIsDeleted("N");
			
			List<PermissionAction> permissionActionList = permissionActionRepository.find(permissionAction);
			
			if(permissionActionList == null || permissionActionList.isEmpty()) {
				throw new Exception("Not found permissionActionNo " + permissionActionNo);
				
			}
			
			permissionActionMap.put(permissionActionNo, permissionActionList.get(0).getPermissionActionID());
			
		}
		
		List<RolePermission> rolePermissionDBList = new ArrayList<RolePermission>();
		
		for(String permission : permissionList) {
			String menuNo = permission.substring(0,3);
			String permissionActionNo = permission.substring(3,5);
			
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRoleID(role.getRoleID());
			rolePermission.setMenuID(menuMap.get(menuNo));
			rolePermission.setPermissionActionID(permissionActionMap.get(permissionActionNo));
			rolePermission.setCreatedBy("SYSTEM");
			rolePermission.setCreatedDate(currentDate);
			rolePermission.setIsDeleted("N");
			
			rolePermissionDBList.add(rolePermission);
			
		}
		
		rolePermissionRepository.delete(role.getRoleID(), "SYSTEM", currentDate);
		rolePermissionRepository.insert(rolePermissionDBList);
		
	}

	@Override
	public GetPermissionResponse getPermission(String roleNo) throws Exception {
		GetPermissionResponse getPermissionResponse = new GetPermissionResponse();
		
		if(!roleRepository.isValid(roleNo)) {
			throw new Exception("Not found roleNo " + roleNo);
			
		}
		
		List<String> permissionNoList = rolePermissionRepository.getPermisionNoList(roleNo);
		
		getPermissionResponse.setPermissionNoList(permissionNoList);

		return getPermissionResponse;
	}
	
}
