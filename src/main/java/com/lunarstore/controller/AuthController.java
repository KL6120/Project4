package com.lunarstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lunarstore.model.Account;
import com.lunarstore.repository.AccountRepository;

@Controller
public class AuthController {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute Account account, @RequestParam("repeatPassword") String repeatPassword) {
		try {
			if(account.getPassword().equals(repeatPassword)) {
				account.setAdmin(false);
				account.setPassword(passwordEncoder.encode(account.getPassword()));
				accountRepository.save(account);
				return "redirect:/login";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "register";
	}
}
