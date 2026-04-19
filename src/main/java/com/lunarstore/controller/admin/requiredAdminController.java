package com.lunarstore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/requiredAdmin")
public class requiredAdminController {
	@GetMapping("")
	public String index() {
		return "admin/requiredAdmin";
	}
}
