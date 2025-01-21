package nl.humaninference.person.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// I always start with a statuscontroller so that I can quickly test that my spring boot project is running
@RestController
public class StatusController {

	@GetMapping("/api/status")
	public String status() {
		return "OK: " + LocalDateTime.now();
	}
	
}
