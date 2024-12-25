package yms.api.validator;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import yms.api.model.request.SearchUserRequest;
import yms.api.util.DateUtil;

@Component
public class UserManagementValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SearchUserRequest.class.equals(clazz);
		
	}

	@Override
	public void validate(Object target, Errors errors) {
		SearchUserRequest request = (SearchUserRequest) target;
		
		try {
			if((request.getWorkStartDateFrom() != null && !"".equals(request.getWorkStartDateFrom())) && (request.getWorkStartDateTo() != null && !"".equals(request.getWorkStartDateTo()))){
				String dateFromDB = DateUtil.convertDateRequestFormatToDateDBFormat(request.getWorkStartDateFrom());
				String dateToDB = DateUtil.convertDateRequestFormatToDateDBFormat(request.getWorkStartDateTo());
				
				if(dateFromDB.compareTo(dateToDB) > 0) {
					errors.rejectValue("workStartDateFrom", "workStartDateFrom must be less than or equal workStartDateTo.");
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			errors.rejectValue("workStartDateFrom", "Invalid format");
			
		}

		
	}

}
