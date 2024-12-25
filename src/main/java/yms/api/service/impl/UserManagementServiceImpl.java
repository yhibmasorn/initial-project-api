package yms.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import yms.api.constant.CommonConstant;
import yms.api.model.request.SearchUserRequest;
import yms.api.model.response.GetAllUsersResponse;
import yms.api.model.response.SearchUsersResponse;
import yms.api.model.response.User;
import yms.api.repository.EmployeeRepository;
import yms.api.service.UserManagementService;
import yms.api.util.DateUtil;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public GetAllUsersResponse getAllUsers() throws Exception {
		GetAllUsersResponse response = new GetAllUsersResponse();
		
		List<User> userList = employeeRepository.findPaging(new SearchUserRequest());
		
		response.setUserList(userList);
		
		return response;
	}

	@Override
	public SearchUsersResponse searchUsers(@Valid SearchUserRequest request) throws Exception {
		SearchUsersResponse response = new SearchUsersResponse();
		
		if(request.getSortField() != null && !"".equals(request.getSortField()))
			request.setSortField(getFieldDB(request.getSortField()));
		
		if(request.getSize() == null)
			request.setSize(CommonConstant.DEFAULT_PAGING_SIZE);
		
		if(request.getPage() == null)
			request.setPage(CommonConstant.DEFAULT_PAGING_PAGE);
		
		if(request.getWorkStartDateFrom() != null && !"".equals(request.getWorkStartDateFrom())) {
			request.setWorkStartDateFrom(DateUtil.convertDateRequestFormatToDateDBFormat(request.getWorkStartDateFrom()));
			
		}
		
		if(request.getWorkStartDateTo() != null && !"".equals(request.getWorkStartDateTo())) {
			request.setWorkStartDateTo(DateUtil.convertDateRequestFormatToDateDBFormat(request.getWorkStartDateTo()));
			
		}
		
		List<User> userList = employeeRepository.findPaging(request);
		
		int totalItems = employeeRepository.countFindPaging(request);
		int totalPages = (int) Math.ceilDiv(totalItems, request.getSize());
		
		response.setUserList(userList);
		response.setPage(request.getPage());
		response.setSize(request.getSize());
		response.setTotalItems(totalItems);
		response.setTotalPages(totalPages);
		
		return response;
	}

	private String getFieldDB(String sortField) {
		String fieldDB = null;
		
		switch (sortField) {
		case "employeeNo": fieldDB = "EMPLOYEE_NO"; break;
		case "titleTh": fieldDB = "TITLE_TH"; break;
		case "titleEn": fieldDB = "TITLE_EN"; break;
		case "fistnameTh": fieldDB = "FIRSTNAME_TH"; break;
		case "lastnameTh": fieldDB = "LASTNAME_TH"; break;
		case "fistnameEn": fieldDB = "FIRSTNAME_EN"; break;
		case "lastnameEn": fieldDB = "LASTNAME_EN"; break;
		case "username": fieldDB = "USERNAME"; break;
		case "roleName": fieldDB = "ROLE_NAME"; break;
		case "workStartDate": fieldDB = "WORK_START_DATE"; break;
		case "status": fieldDB = "STATUS"; break;
		default : ;
		
		}
		
		return fieldDB;
		
	}
	
}
