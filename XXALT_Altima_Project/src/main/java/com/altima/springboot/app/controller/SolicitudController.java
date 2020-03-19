package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SolicitudController {
	
	@GetMapping("/solicitudes") 
	public String listForms() {
		return "solicitudes";
	}

}
