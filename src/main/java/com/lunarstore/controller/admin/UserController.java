package com.lunarstore.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lunarstore.model.Account;
import com.lunarstore.model.Product;
import com.lunarstore.repository.AccountRepository;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/manageUser")
public class UserController {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	ServletContext servletContext;

	@GetMapping("")
	public String index(Model model) {
		model.addAttribute("users", accountRepository.findAll());
		return "admin/accounts/list";
	}

	@GetMapping("/edit/{id}")
	public String editUser(Model model, @PathVariable Integer id) {
		model.addAttribute("users", accountRepository.findById(id).orElse(null));
		return "admin/accounts/edit";
	}

	@PostMapping("/update")
	public String updateUser(Model model, @ModelAttribute Account account,
			@RequestParam("imageAccount") MultipartFile image) {

		try {

			if (!image.isEmpty()) {

				String fileName = image.getOriginalFilename();

				String uploadDir = servletContext.getRealPath("/images/");

				Files.createDirectories(Path.of(uploadDir));

				File file = new File(uploadDir + fileName);

				image.transferTo(file);

				account.setAvatar(fileName);
			}

			accountRepository.save(account);

			return "redirect:/admin/manageUser";

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "admin/accounts/edit";
	}
}
