package com.lunarstore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lunarstore.model.Account;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		Account account = (Account) session.getAttribute("account");
		if(account.getAdmin() == null && account.getAdmin() != true) {
			return "admin/requiredAdmin";
		}
		model.addAttribute("active", "index");
		return "admin/index";
	}
	
	@GetMapping("/requiredAdmin")
	public String requiredAdmin() {
		return "admin/requiredAdmin";
	}
	
	@PostMapping("/admin/login")
	public String login(@RequestParam String email, @RequestParam String password) {
		return "redirect:/admin/index";
	}
}
