package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TraspasosController {

	@GetMapping("/traspasos")
	public String listTraspasos() {
		return "traspasos";
	}
}
