package yms.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {

	@GetMapping
	public String welcome() {
		return "Welcome method is called.";
		
	}
	
	@GetMapping("/exception")
	public void testException() throws Exception {
		throw new Exception("Test Exception");
	}
	
}
