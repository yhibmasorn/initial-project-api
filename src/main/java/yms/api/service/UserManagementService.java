package yms.api.service;

import yms.api.model.request.SearchUserRequest;
import yms.api.model.response.GetAllUsersResponse;
import yms.api.model.response.SearchUsersResponse;

public interface UserManagementService {

	public GetAllUsersResponse getAllUsers() throws Exception;
	public SearchUsersResponse searchUsers(SearchUserRequest request) throws Exception;
	
}
