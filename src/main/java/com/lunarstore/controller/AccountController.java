package com.lunarstore.controller;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import com.lunarstore.model.Category;
import com.lunarstore.repository.AccountRepository;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;

@Controller
@RequestMapping("")
public class AccountController {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ServletContext servletContext;
	public static String UPLOAD_DIRECTORY = Paths.get("src", "main", "resources", "static", "images").toFile()
			.getAbsolutePath();

	@GetMapping("/myProfile/{id}")
	public String index(Model model, @PathVariable Integer id) {
		model.addAttribute("account", accountRepository.findById(id).orElse(null));
		return "my-profile";
	}

	@GetMapping("/editProfile/{id}")
	public String showFormEditProfile(Model model, @PathVariable Integer id) {
		Account accounts = accountRepository.findById(id).orElse(null);
		model.addAttribute("account", accounts);
		return "edit-profile";
	}

	@PostMapping("/editProfile/update")
	public String actionEditProfile(Model model, @ModelAttribute Account account,
			@RequestParam("imageAccount") MultipartFile image, @RequestParam Integer id) {

		try {

			if (!image.isEmpty()) {
				StringBuilder fileNames = new StringBuilder();
				Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, image.getOriginalFilename());
				fileNames.append(image.getOriginalFilename());
				Files.write(fileNameAndPath, image.getBytes());
				model.addAttribute("account", "Uploaded images: " + fileNames.toString());
			}

			accountRepository.save(account);

			return "redirect:/myProfile/" + id;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "edit-profile";
	}
}
